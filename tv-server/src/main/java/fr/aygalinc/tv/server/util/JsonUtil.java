package fr.aygalinc.tv.server.util;


import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import fr.aygalinc.tv.server.show.model.ShowItem;
import fr.aygalinc.tv.server.show.model.ShowMainInformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {

    public static  JsonFactory JSON_FACTORY = new JacksonFactory();

    //Json generic serializer
    public static String serialize(Object obj) throws IOException {
        JsonHttpContent content = new JsonHttpContent(JSON_FACTORY, obj);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            content.writeTo(out);
            return out.toString("UTF-8");
        }finally {
            out.close();
        }
    }

    //Json specific serializer
    //Can normaly be avoid by using a good JSON lib...
    public static String serializeItemsId(List<ShowMainInformation> showMainInformation) throws IOException {

        List<ShowId> showIds = showMainInformation.stream().map(item -> {
            ShowId showId = new ShowId();
            showId.showId = item.getId();
            return showId;
        }).collect(Collectors.toList());

        JsonHttpContent content = new JsonHttpContent(JSON_FACTORY, showIds);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            content.writeTo(out);
            return out.toString("UTF-8");
        }finally {
            out.close();
        }
    }

    static public class ShowId{
        @Key("id")
        Long showId;
    }
}