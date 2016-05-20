package apps.orchotech.com.popularmovies.network;

import java.io.Serializable;

/**
 * Created by PriyamSaikia on 16-05-2016.
 */
public class ReviewsBean implements Serializable {

    String id, author, content;

    public ReviewsBean() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
