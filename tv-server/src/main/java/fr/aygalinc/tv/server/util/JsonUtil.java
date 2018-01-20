package fr.aygalinc.tv.server.util;


import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonUtil {

    public static  JsonFactory JSON_FACTORY = new JacksonFactory();

    //Json generic parser
    public static String serialize(Object obj) throws IOException {
        System.out.println("qsd " + obj);
        JsonHttpContent content = new JsonHttpContent(JSON_FACTORY, obj);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
           content.writeTo(out);
           return out.toString("UTF-8");
       }finally {
           out.close();
       }
    }
}