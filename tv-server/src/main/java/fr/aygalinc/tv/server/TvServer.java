package fr.aygalinc.tv.server;

import fr.aygalinc.tv.server.show.ShowController;
import fr.aygalinc.tv.server.util.ServerPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.debug.DebugScreen.enableDebugScreen;

public class TvServer {

    private static final Logger LOG = LoggerFactory.getLogger(TvServer.class);

    private static final ShowController showController = new ShowController();

    public static void main( String[] args) {
        port(getHerokuAssignedPort());
        LOG.info("Tv-Server starts on port : "+ getHerokuAssignedPort());
        enableDebugScreen();

        get(ServerPath.SHOW+":id", showController.getShow);

        get(ServerPath.SHOWS, showController.getShows);

        get(ServerPath.SHOW +":id"+ ServerPath.IMAGE, showController.getShowImage);

    }


    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    //TODO : CleanTemp file when server stop
}
