package apps.orchotech.com.popularmovies.network;

import java.io.Serializable;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class AllMoviesBean implements Serializable {
    String id;
    String poster_link;

    public AllMoviesBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster_link() {
        return poster_link;
    }

    public void setPoster_link(String poster_link) {
        this.poster_link = poster_link;
    }
}
