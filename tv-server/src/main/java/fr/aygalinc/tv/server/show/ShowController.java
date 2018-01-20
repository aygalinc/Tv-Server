package fr.aygalinc.tv.server.show;

import fr.aygalinc.tv.server.show.model.ShowItem;
import fr.aygalinc.tv.server.show.model.ShowMainInformation;
import fr.aygalinc.tv.server.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ShowController {

    private static final Logger LOG = LoggerFactory.getLogger(ShowDao.class);

    private ShowDao showDao = new ShowDao();

    public Route getShow = (Request request, Response response) -> {

        String id = request.params(":id");

        if (id == null){
            response.status(404);
            return null;
        }

        try {
            ShowItem show = showDao.getShowById(Long.valueOf(id));
            return JsonUtil.serialize(show.getInformation());
        }catch (NumberFormatException e){
            LOG.error("Invalid parameter form for /showd/:id",e);
            response.status(500);
        }catch (IOException e){
            LOG.error("Error occurs during serialization",e);
            response.status(500);
        }

        return null;
    };

    public Route getShows = (Request request, Response response) -> {

        String id = request.queryParams("search");

        if (id == null){
            return null;
        }

        List<ShowMainInformation> shows = showDao.getShowByName(id);

        return JsonUtil.serialize(shows);
    };

    public Route getShowImage = (Request request, Response response) -> {

        String id = request.params(":id");

        if (id == null){
            response.status(404);
            return null;
        }


        try {
            ShowItem show = showDao.getShowById(Long.valueOf(id));

            response.raw().setContentType("image/jpeg");

            File f = show.getImage().getFile();

            try (OutputStream out = response.raw().getOutputStream()) {
                ImageIO.write(ImageIO.read(f), "jpg", out);
            }finally {
                response.raw().getOutputStream().close();
            }

            return response;

        }catch (NumberFormatException e){
            response.status(500);
            return null;
        }

    };


}
