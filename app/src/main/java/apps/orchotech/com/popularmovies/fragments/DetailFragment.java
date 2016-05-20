package apps.orchotech.com.popularmovies.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.activities.TrailerReviewsActivity;
import apps.orchotech.com.popularmovies.data.MoviesContract;
import apps.orchotech.com.popularmovies.data.MoviesDBHelper;
import apps.orchotech.com.popularmovies.network.ImageLoader;
import apps.orchotech.com.popularmovies.network.MyConnection;
import apps.orchotech.com.popularmovies.network.Parser;
import apps.orchotech.com.popularmovies.network.PopularMoviesBean;
import apps.orchotech.com.popularmovies.network.ReviewsBean;
import apps.orchotech.com.popularmovies.network.TrailerBean;
import apps.orchotech.com.popularmovies.network.VolleyRequest;
import apps.orchotech.com.popularmovies.utils.AppConstants;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class DetailFragment extends Fragment implements MyConnection.IMyConnection {
    private static final String TAG = DetailFragment.class.getSimpleName();
    @Bind(R.id.tv_overview)
    TextView tv_overview;
    @Bind(R.id.tv_movie_name)
    TextView tv_movie_name;
    @Bind(R.id.tv_release_date)
    TextView tv_release_date;
    @Bind(R.id.tv_duration)
    TextView tv_duration;
    @Bind(R.id.tv_rating)
    TextView tv_rating;
    @Bind(R.id.imv_detail)
    ImageView imv_poster;
    @Bind(R.id.imv_banner)
    ImageView imv_banner;
    @Bind(R.id.ll_reviews)
    LinearLayout ll_reviews;
    @Bind(R.id.ll_trailer)
    LinearLayout ll_trailer;
    @Bind(R.id.cardview_reviews)
    CardView cardview_reviews;
    @Bind(R.id.cardview_trailer)
    CardView cardview_trailer;
    @Bind(R.id.btn_fav)
    ImageView imv_fav;

    private String mMovieId;
    private PopularMoviesBean details;
    String mFirstVideoShareLink;
    String mMovieName;
    private String mIsTwoPane;
    private boolean isAFavourite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null)
            mMovieId = getArguments().getString(AppConstants.MOVIE_ID);
        else mMovieId = "";
        mIsTwoPane = getArguments().getString(AppConstants.IS_TWO_PANE);
        View view = inflater.inflate(R.layout.detail_layout, container, false);
        ButterKnife.bind(this, view);
        isAFavourite = false;

        setHasOptionsMenu(true);
        Cursor c = getActivity().getContentResolver().query(MoviesContract.FavouritesEntry.CONTENT_URI_FAVOURITES_ENTRY,
                new String[]{MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID},
                MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID + "=?", new String[]{mMovieId}, null, null);
        if (c.moveToFirst()) {
            do {
                if (mMovieId.equals(c.getString(c.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID)))) {
                    isAFavourite = true;
                    imv_fav.setImageResource(R.mipmap.ic_already_favourite);
                }

            } while (c.moveToNext());
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {

            if (mFirstVideoShareLink != null) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        String.format(AppConstants.TRAILER_SHARE_LINK, mMovieName, mFirstVideoShareLink));

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), R.string.no_send_intent_handler, Toast.LENGTH_SHORT).show();
                }
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_fav)
    public void onClickFav(View v) {
        saveAsFavourite();
    }

    private void saveAsFavourite() {
        if (!isAFavourite) {
            if (details != null) {
                SQLiteDatabase sqLiteDatabase = new MoviesDBHelper(getActivity()).getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID, details.getId());
                contentValues.put(MoviesContract.FavouritesEntry.COLUMN_MOVIE_NAME, details.getTitle());
                contentValues.put(MoviesContract.FavouritesEntry.COLUMN_OVERVIEW, details.getOverview());
                contentValues.put(MoviesContract.FavouritesEntry.COLUMN_RELEASE_DATE, details.getRelease_date());
                contentValues.put(MoviesContract.FavouritesEntry.COLUMN_VOTE_AVERAGE, details.getVote_average());
                contentValues.put(MoviesContract.FavouritesEntry.COLUMN_RUN_TIME, details.getRuntime());
                contentValues.put(MoviesContract.FavouritesEntry.COLUMN_POSTER_LINK, details.getPoster_path());
                contentValues.put(MoviesContract.FavouritesEntry.COLUMN_BANNER_LINK, details.getBackdrop_path());

                Uri result = getActivity().getContentResolver().insert(MoviesContract.FavouritesEntry.CONTENT_URI_FAVOURITES_ENTRY, contentValues);

//            long insertResult = sqLiteDatabase.insert(MoviesContract.FavouritesEntry.TABLE_NAME, null, contentValues);
                if (result == null)
                    Toast.makeText(getActivity(), R.string.already_favourite, Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getActivity(), R.string.added_favourite, Toast.LENGTH_SHORT).show();
                    imv_fav.setImageResource(R.mipmap.ic_already_favourite);
                    isAFavourite = true;
                }
            } else {
                Toast.makeText(getActivity(), R.string.try_again, Toast.LENGTH_SHORT).show();
            }
        } else {
            int deleteResult = getActivity().getContentResolver().delete(MoviesContract.FavouritesEntry.CONTENT_URI_FAVOURITES_ENTRY,
                    MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID + "=?", new String[]{mMovieId});
            if (deleteResult == 0) {
                Toast.makeText(getActivity(), R.string.try_again, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), R.string.deleted_favourite, Toast.LENGTH_SHORT).show();
                imv_fav.setImageResource(R.mipmap.ic_star);
                isAFavourite = false;
            }
        }
    }

    @OnClick(R.id.cardview_trailer)
    public void onClickCardviewTrailers(View v) {
        Intent intent = new Intent(getActivity(), TrailerReviewsActivity.class);
        intent.putExtra(AppConstants.INTENT_MOVIE_ID, mMovieId);
        intent.putExtra(AppConstants.INTENT_LIST_TYPE, AppConstants.TYPE_TRAILERS);
        startActivity(intent);
    }

    @OnClick(R.id.cardview_reviews)
    public void onClickCardviewReviews(View v) {
        Intent intent = new Intent(getActivity(), TrailerReviewsActivity.class);
        intent.putExtra(AppConstants.INTENT_MOVIE_ID, mMovieId);
        intent.putExtra(AppConstants.INTENT_LIST_TYPE, AppConstants.TYPE_REVIEWS);
        startActivity(intent);
    }

    public void getMovieReviews(String mMovieId) {
        String url = String.format(AppConstants.REVIEW_LINKS, mMovieId);
        VolleyRequest.sendRequest(getActivity(), url, this, AppConstants.REVIEW_REQUEST_ID);
    }

    public void getMovieTrailers(String mMovieId) {
        String url = String.format(AppConstants.TRAILER_LINKS, mMovieId);
        VolleyRequest.sendRequest(getActivity(), url, this, AppConstants.TRAILER_REQUEST_ID);
    }

    public void getMovieDetails(String movieId) {
        String url = String.format(AppConstants.MOVIE_DETAIL_LINK, movieId);
        VolleyRequest.sendRequest(getActivity(), url, this, AppConstants.DETAIL_REQUEST_ID);
    }

    @Override
    public void onSuccess(String response, int requestId) {
        if (!isAdded())
            return;
        Parser parser = new Parser();
        if (requestId == AppConstants.DETAIL_REQUEST_ID) {
            details = parser.parseMovieDetails(response);
            setData(details);
        } else if (requestId == AppConstants.TRAILER_REQUEST_ID) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                ArrayList<TrailerBean> trailerList = parser.parseAllTrailers(jsonObject.getString("results"));
                if (trailerList.size() > 0) {
                    //todo: check adding multiple views
                    ll_trailer.removeAllViews();
                    for (int i = 0; i < trailerList.size(); i++) {
                        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_trailer_list, null);
                        ((TextView) v.findViewById(R.id.tv_trailer_name)).setText(trailerList.get(i).getName());
                        ll_trailer.addView(v);
                        mFirstVideoShareLink = trailerList.get(0).getKey();
                    }
                    ll_trailer.invalidate();
                } else {
                    cardview_trailer.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (requestId == AppConstants.REVIEW_REQUEST_ID) {
            //todo: check adding multiple views
            ArrayList<ReviewsBean> reviewList = parser.parseAllReviews(response);
            if (reviewList.size() > 0) {
                ll_reviews.removeAllViews();
                for (int i = 0; i < reviewList.size(); i++) {
                    View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_review_list, null);
                    ((TextView) v.findViewById(R.id.tv_item_review_list)).setText(reviewList.get(i).getContent());
                    ll_reviews.addView(v);
                }
                ll_reviews.invalidate();
            } else {
                cardview_reviews.setVisibility(View.GONE);
            }
        }
    }

    private void setData(PopularMoviesBean details) {
        mMovieName = details.getTitle();

        String[] formattedDate = details.getRelease_date().split("-");
        tv_duration.setText(details.getRuntime() + " mins");
        tv_movie_name.setText(details.getTitle());
        tv_rating.setText(details.getVote_average() + "/10");
        tv_overview.setText(details.getOverview());
        tv_release_date.setText(formattedDate[0]);
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(getActivity(), details.getPoster_path(), imv_poster);
        imageLoader.loadImage(getActivity(), details.getBackdrop_path(), imv_banner);
    }

    @Override
    public void onFailure(String error, int requestId) {
        if (!isAdded())
            return;
        if (requestId == AppConstants.DETAIL_REQUEST_ID) {
            Cursor cursor = getActivity().getContentResolver().query(MoviesContract.FavouritesEntry.CONTENT_URI_FAVOURITES_ENTRY,
                    null,   //projection
                    MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID + "=?", new String[]{mMovieId}, null, null);
            if (cursor.moveToFirst()) {
                PopularMoviesBean bean = new PopularMoviesBean();
                do {
                    bean.setId(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID)));
                    bean.setRuntime(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_RUN_TIME)));
                    bean.setTitle(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_MOVIE_NAME)));
                    bean.setOverview(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_OVERVIEW)));
                    bean.setVote_average(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_VOTE_AVERAGE)));
                    bean.setRelease_date(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_RELEASE_DATE)));
                    bean.setPoster_path(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_POSTER_LINK)));
                    bean.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_BANNER_LINK)));
                } while (cursor.moveToNext());
                setData(bean);
            }
            cardview_reviews.setVisibility(View.GONE);
            cardview_trailer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMovieId != null) {
            getMovieDetails(mMovieId);
            getMovieReviews(mMovieId);
            getMovieTrailers(mMovieId);
        }
    }
}