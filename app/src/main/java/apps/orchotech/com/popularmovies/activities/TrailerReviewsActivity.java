package apps.orchotech.com.popularmovies.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.adapters.StaggeredListAdapter;
import apps.orchotech.com.popularmovies.adapters.TrailerAdapter;
import apps.orchotech.com.popularmovies.network.MyConnection;
import apps.orchotech.com.popularmovies.network.Parser;
import apps.orchotech.com.popularmovies.network.ReviewsBean;
import apps.orchotech.com.popularmovies.network.TrailerBean;
import apps.orchotech.com.popularmovies.network.VolleyRequest;
import apps.orchotech.com.popularmovies.utils.AppConstants;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PriyamSaikia on 17-05-2016.
 */
public class TrailerReviewsActivity extends AppCompatActivity implements MyConnection.IMyConnection {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    String mMovieId;
    String list_type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        mMovieId = getIntent().getStringExtra(AppConstants.INTENT_MOVIE_ID);
        list_type = getIntent().getStringExtra(AppConstants.INTENT_LIST_TYPE);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (list_type.equals(AppConstants.TYPE_REVIEWS)) {
            getMovieReviews(mMovieId);
        } else {
            getMovieTrailers(mMovieId);
        }

    }

    public void getMovieReviews(String mMovieId) {
        String url = String.format(AppConstants.REVIEW_LINKS, mMovieId);
        VolleyRequest.sendRequest(TrailerReviewsActivity.this, url, this, AppConstants.REVIEW_REQUEST_ID);
    }

    public void getMovieTrailers(String mMovieId) {
        String url = String.format(AppConstants.TRAILER_LINKS, mMovieId);
        VolleyRequest.sendRequest(TrailerReviewsActivity.this, url, this, AppConstants.TRAILER_REQUEST_ID);
    }

    @Override
    public void onSuccess(String response, int requestId) {

        Parser parser = new Parser();
        if (requestId == AppConstants.TRAILER_REQUEST_ID) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                ArrayList<TrailerBean> trailerList = parser.parseAllTrailers(jsonObject.getString("results"));
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new TrailerAdapter(TrailerReviewsActivity.this, trailerList));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (requestId == AppConstants.REVIEW_REQUEST_ID) {
            ArrayList<ReviewsBean> reviewList = parser.parseAllReviews(response);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(new StaggeredListAdapter(TrailerReviewsActivity.this,reviewList));
        }
    }

    @Override
    public void onFailure(String error, int requestId) {

    }
}
