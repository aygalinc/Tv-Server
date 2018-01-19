package fr.aygalinc.tv.server.util.client;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import fr.aygalinc.tv.server.show.model.ShowExtraInformation;
import fr.aygalinc.tv.server.show.model.ShowItem;
import fr.aygalinc.tv.server.show.model.ShowMainInformation;
import fr.aygalinc.tv.server.show.model.image.ImageFile;
import fr.aygalinc.tv.server.util.JsonUtil;
import fr.aygalinc.tv.server.util.client.adapter.ShowMainInformationArrayAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by aygalinc on 19/01/18.
 */
public class TvMazeClientUtil {

    private static final Logger LOG = LoggerFactory.getLogger(TvMazeClientUtil.class);

    private static HttpRequestFactory requestFactory
            = new NetHttpTransport().createRequestFactory();


    public static ShowItem getShowFromTvMaze(String id) throws IOException {

        HttpResponse response =request(TvMazeUtil.SHOW_URL+id);


        ShowExtraInformation extraInformation;
        ImageFile imageFile;

        try {
            extraInformation = response.parseAs(ShowExtraInformation.class);
        }catch (IOException e){
            LOG.error("Error occurs during parsing extra information of Maze Tv api ",e);
            return null;
        }

        try {
            imageFile = getShowImageFromTvMaze(extraInformation);
        }catch (IOException e){
            LOG.error("Error occurs during getting image of Maze Tv api ",e);
            return null;
        }

        return new ShowItem(extraInformation,imageFile);
    }


    public static List<ShowItem> getShowsFromTvMaze(String showName) throws IOException {

        HttpResponse response = request(TvMazeUtil.SEARCH_URL+"?q="+showName);

        ShowMainInformationArrayAdapter[] showFeed = response.parseAs(ShowMainInformationArrayAdapter[].class);

        return Arrays.stream(showFeed).filter(Objects::nonNull).map(
                a -> {
                    ShowExtraInformation extraInformation= a.getShowExtraInformation();
                    ImageFile imageFile;

                    try {
                        imageFile = getShowImageFromTvMaze(extraInformation);
                    }catch (IOException e){
                        LOG.error("Error occurs during getting image of Maze Tv api ",e);
                        return null;
                    }

                    return new ShowItem(extraInformation,imageFile);
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static ImageFile getShowImageFromTvMaze(ShowMainInformation show) throws IOException {

        HttpResponse response = request(show.getImageUrl());

        File f = File.createTempFile("SHOW",".jpg");

        OutputStream outputStream = new FileOutputStream(f);

        try {
            response.download(outputStream);
        } finally {
            outputStream.close();
        }

        return new ImageFile(f);

    }

    private static HttpResponse request(String url) throws IOException {

        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url)).setParser(new JsonObjectParser(JsonUtil.JSON_FACTORY));

        return request.execute();
    }
}
