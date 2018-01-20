package fr.aygalinc.tv.server.show;

import fr.aygalinc.tv.server.show.model.ShowItem;
import fr.aygalinc.tv.server.show.model.ShowMainInformation;
import fr.aygalinc.tv.server.util.client.TvMazeClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by aygalinc on 19/01/18.
 */
public class ShowDao{

    private static final Logger LOG = LoggerFactory.getLogger(ShowDao.class);

    // In case of concurrent request multiple thread can access to the map, so it mus support concurrency
    private Map<Long, ShowTimeStampWrapper> shows = new ConcurrentHashMap<>();

    public ShowItem getShowById(Long key){

        if (key == null){
            throw new InvalidParameterException();
        }

        /**
         * Try cache first
         */
        ShowTimeStampWrapper show = shows.get(key);

        if(show != null){
            if (show.isValid()) {
                return show.getShowItem();
            }
        }

        /**
         * Else try distant server
         */
        try {
            ShowTimeStampWrapper showTimeStampWrapper = new ShowTimeStampWrapper(TvMazeClientUtil.getShowFromTvMaze(key.toString()));

            if (show == null){
                shows.put(showTimeStampWrapper.getShowItem().getId(),showTimeStampWrapper);
            }else {
                show.refreshCache(showTimeStampWrapper);
            }
            return showTimeStampWrapper.getShowItem();
        } catch (IOException e) {
            LOG.error("Error occurs when demanding",e);
            return null;
        }
    }

    public List<ShowMainInformation> getShowByName(String name){

        if (name == null){
            throw new InvalidParameterException();
        }

        try {
            List<ShowItem> showsResponse = TvMazeClientUtil.getShowsFromTvMaze(name);
            for (ShowItem show : showsResponse){
                if (shows.containsKey(show.getId())){
                    // refresh case
                    shows.get(show.getId()).refreshCache(show);
                }else {
                    // new show discover case
                    shows.put(show.getId(),new ShowTimeStampWrapper(show));
                }

            }
            return showsResponse.stream().map(a -> a.getInformation()).collect(Collectors.toList());

        } catch (IOException e) {
            LOG.error("Error occurs when demanding",e);
            //Try to serve on of the cache response;
            return shows.values().stream().filter(
                    (ShowTimeStampWrapper showTimeStampWrapper) -> {
                        if(!showTimeStampWrapper.isValid()){
                            return false;
                        }
                        return showTimeStampWrapper.getShowItem().getInformation().getName().contains(name);
                    }

            ).map(a -> a.getShowItem().getInformation()).collect(Collectors.toList());
        }

    }

    //Wrapper to maintain cache
    private class ShowTimeStampWrapper {

        private LocalDateTime timeStamp;

        private ShowItem showItem;

        ShowTimeStampWrapper(ShowItem show){
            this.showItem = show;
            timeStamp = LocalDateTime.now();
        }

        public boolean isValid(){

            LocalDateTime now = LocalDateTime.now();

            return ((Duration.between(now,timeStamp).compareTo(Duration.ofHours(1)) < 0 ));
        }

        public ShowItem getShowItem() {
            return showItem;
        }

        public void refreshCache(ShowTimeStampWrapper show){
            timeStamp = show.timeStamp;
            showItem.setInformation(show.getShowItem().getInformation());
        }

        public void refreshCache(ShowItem show){
            timeStamp = LocalDateTime.now();
            showItem.setInformation(show.getInformation());
        }
    }
}
