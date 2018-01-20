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

    private Map<Long, ShowTimeStampWrapper> shows = new ConcurrentHashMap<>();

    public ShowItem getShowById(Long key){

        if (key == null){
            System.out.println(" key null");
            throw new InvalidParameterException();
        }

        ShowTimeStampWrapper show = shows.get(key);

        if(show != null){
            System.out.println(" Try cache");
            if (show.isValid()) {
                return show.getShowItem();
            }
        }

        try {
            System.out.println(" Try service");
            show = new ShowTimeStampWrapper(TvMazeClientUtil.getShowFromTvMaze(key.toString()));
            System.out.println(" show " + show);
            System.out.println(" show " + show.getShowItem());

            shows.put(show.getShowItem().getId(),show);
            return show.getShowItem();
        } catch (IOException e) {
            LOG.error("Error occurs when demanding",e);
            return null;
        }
    }

    public List<ShowMainInformation> getShowByName(String name){

        if (name == null){
            System.out.println(" Name is null ");
            throw new InvalidParameterException();
        }

        try {
            System.out.println(" request");
            List<ShowItem> showsResponse = TvMazeClientUtil.getShowsFromTvMaze(name);
            for (ShowItem show : showsResponse){
                shows.put(show.getId(),new ShowTimeStampWrapper(show));
            }
            return showsResponse.stream().map(a -> a.getExtraInformation()).collect(Collectors.toList());

        } catch (IOException e) {
            LOG.error("Error occurs when demanding",e);
            return null;
        }

    }

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
    }
}
