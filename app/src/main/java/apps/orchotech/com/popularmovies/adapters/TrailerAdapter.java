package apps.orchotech.com.popularmovies.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.network.TrailerBean;
import apps.orchotech.com.popularmovies.utils.AppConstants;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private static final String TAG = TrailerAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<TrailerBean> mTrailerBean;

    public TrailerAdapter(Context context, ArrayList<TrailerBean> trailerBean) {
        mContext = context;
        mTrailerBean = trailerBean;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_trailer, null);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        final TrailerBean item = mTrailerBean.get(position);
        holder.tv_trailer_name.setText(item.getName());
        holder.ll_item_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideoInYoutube(item.getKey());
            }
        });
    }

    public void playVideoInYoutube(String key) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format(AppConstants.YOUTUBE_LINK, key)));
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return mTrailerBean.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_trailer_name)
        TextView tv_trailer_name;
        @Bind(R.id.ll_item_trailer)
        LinearLayout ll_item_trailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
