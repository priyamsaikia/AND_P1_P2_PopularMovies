package apps.orchotech.com.popularmovies.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.activities.DetailsActivity;
import apps.orchotech.com.popularmovies.adapters.PosterAdapter;
import apps.orchotech.com.popularmovies.adapters.StaggeredListAdapter;
import apps.orchotech.com.popularmovies.data.MoviesContract;
import apps.orchotech.com.popularmovies.network.AllMoviesBean;
import apps.orchotech.com.popularmovies.network.MyConnection;
import apps.orchotech.com.popularmovies.network.Parser;
import apps.orchotech.com.popularmovies.network.PopularMoviesBean;
import apps.orchotech.com.popularmovies.network.VolleyRequest;
import apps.orchotech.com.popularmovies.utils.AppConstants;
import apps.orchotech.com.popularmovies.utils.AppPreferences;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class MasterFragment extends Fragment implements MyConnection.IMyConnection {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private static final String TAG = MasterFragment.class.getSimpleName();

    @Override
    public void onStart() {
        super.onStart();
        loadMovies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        setHasOptionsMenu(false);
        return view;
    }

    private void loadMovies() {
        AppPreferences appPreferences = new AppPreferences();
        String preference = appPreferences.getSettingsSP(getActivity());
        if (preference.equals(getText(R.string.favourites))) {
            ArrayList<PopularMoviesBean> arrayList = new ArrayList<PopularMoviesBean>();

            Cursor cursor = getActivity().getContentResolver().query(MoviesContract.FavouritesEntry.CONTENT_URI_FAVOURITES_ENTRY,
                    null,
                    null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    PopularMoviesBean bean = new PopularMoviesBean();
                    bean.setId(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID)));
                    bean.setRuntime(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_RUN_TIME)));
                    bean.setTitle(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_MOVIE_NAME)));
                    bean.setOverview(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_OVERVIEW)));
                    bean.setVote_average(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_VOTE_AVERAGE)));
                    bean.setRelease_date(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_RELEASE_DATE)));
                    bean.setPoster_path(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_POSTER_LINK)));
                    bean.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_BANNER_LINK)));
                    arrayList.add(bean);
                    // do what ever you want here
                } while (cursor.moveToNext());
            }
            cursor.close();
            if (arrayList.size() > 0)
                recyclerView.setAdapter(new StaggeredListAdapter(getActivity(), arrayList));
            else {

            }
        } else {
            String url = String.format(AppConstants.REQUEST_URL, preference);
            VolleyRequest.sendRequest(getActivity(), url, this, AppConstants.POSTER_REQUEST_ID);
        }
    }

    @Override
    public void onSuccess(String response, int requestId) {
        if (!isAdded())
            return;
        Parser parser = new Parser();
        final ArrayList<AllMoviesBean> arrayList = parser.parseAllMovies(response);
        if (arrayList.size() > 0)
            recyclerView.setAdapter(new StaggeredListAdapter(getActivity(), arrayList));
        else {
            //todo: zero state
        }
    }

    @Override
    public void onFailure(String error, int requestId) {

    }

}
