package apps.orchotech.com.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.network.ImageLoader;
import apps.orchotech.com.popularmovies.network.MyConnection;
import apps.orchotech.com.popularmovies.network.Parser;
import apps.orchotech.com.popularmovies.network.PopularMoviesBean;
import apps.orchotech.com.popularmovies.network.VolleyRequest;
import apps.orchotech.com.popularmovies.utils.AppConstants;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class DetailsActivity extends AppCompatActivity implements MyConnection.IMyConnection {
    String movieId;
    private TextView tv_overview, tv_movie_name, tv_release_date, tv_duration, tv_rating;
    ImageView imv_poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        movieId = getIntent().getStringExtra(AppConstants.INTENT_MOVIE_DETAIL);
        tv_movie_name = (TextView) findViewById(R.id.tv_movie_name);
        tv_release_date = (TextView) findViewById(R.id.tv_release_date);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        tv_rating = (TextView) findViewById(R.id.tv_rating);
        tv_overview = (TextView) findViewById(R.id.tv_overview);
        imv_poster = (ImageView) findViewById(R.id.imv_detail);
        getMovieDetails(movieId);
    }

    private void getMovieDetails(String movieId) {
        String url = String.format(AppConstants.MOVIE_DETAIL_LINK, movieId);
        VolleyRequest.sendRequest(this, url, this, AppConstants.DETAIL_REQUEST_ID);
    }

    @Override
    public void onSuccess(String response, int requestId) {
        Parser parser = new Parser();
        PopularMoviesBean details = parser.parseMovieDetails(response);
        String[] formattedDate = details.getRelease_date().split("-");
        tv_duration.setText(details.getRuntime()+" mins");
        tv_movie_name.setText(details.getTitle());
        tv_rating.setText(details.getVote_average()+"/10");
        tv_overview.setText(details.getOverview());
        tv_release_date.setText(formattedDate[0]);
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(DetailsActivity.this, details.getPoster_path(), imv_poster);
    }

    @Override
    public void onFailure(String error, int requestId) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (movieId != null)
            getMovieDetails(movieId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
