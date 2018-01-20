package fr.aygalinc.tv.server.util.client.adapter;

import com.google.api.client.util.Key;
import fr.aygalinc.tv.server.show.model.ShowMainInformation;

/**
 * Extra class, can certainly be avoid if i have a better knoledge of JSON serialisation
 */
public class ShowMainInformationArrayAdapter {

    @Key("show")
    public ShowMainInformation show;

    public ShowMainInformation getShowInformation(){
        return show;
    }


}
