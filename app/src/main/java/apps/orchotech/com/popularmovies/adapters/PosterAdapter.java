package apps.orchotech.com.popularmovies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.network.AllMoviesBean;
import apps.orchotech.com.popularmovies.network.ImageLoader;
import apps.orchotech.com.popularmovies.network.PopularMoviesBean;
import apps.orchotech.com.popularmovies.utils.AppConstants;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class PosterAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<AllMoviesBean> mArrayList;

    public PosterAdapter(Context context, ArrayList<AllMoviesBean> arrayList) {
        this.mContext = context;
        mArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(mContext, mArrayList.get(position).getPoster_link(), imageView);
        return imageView;
    }
}
