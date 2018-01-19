package fr.aygalinc.tv.server.util.client.adapter;

import com.google.api.client.util.Key;
import fr.aygalinc.tv.server.show.model.ShowExtraInformation;

/**
 * Extra class, can certainly be avoid if i have a better knoledge of JSON serialisation
 */
public class ShowMainInformationArrayAdapter {

    @Key("show")
    public ShowExtraInformation show;

    public ShowExtraInformation getShowExtraInformation(){
        return show;
    }


}
