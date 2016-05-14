package apps.orchotech.com.popularmovies.network;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import apps.orchotech.com.popularmovies.utils.AppConstants;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class ImageLoader {
    public void loadImage(Context context, String url, ImageView imageView) {
        String imageUrl = AppConstants.BASE_IMAGE_LINK + url;
        Picasso.with(context)
                .load(imageUrl)
                .into(imageView);
    }
}
