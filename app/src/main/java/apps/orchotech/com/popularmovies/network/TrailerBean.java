package apps.orchotech.com.popularmovies.network;

import java.io.Serializable;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class TrailerBean implements Serializable{
    public TrailerBean(){

    }
    String id, name;
    String key, site, type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
