package apps.orchotech.com.popularmovies.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.activities.MainActivity;
import apps.orchotech.com.popularmovies.adapters.GridLayoutAdapter;
import apps.orchotech.com.popularmovies.data.MoviesContract;
import apps.orchotech.com.popularmovies.network.AllMoviesBean;
import apps.orchotech.com.popularmovies.network.MyConnection;
import apps.orchotech.com.popularmovies.network.Parser;
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
    private Boolean mIsTwoPane;

    @Override
    public void onStart() {
        super.onStart();
        mIsTwoPane = ((MainActivity) getActivity()).mIsTwoPane;
        loadMovies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        setHasOptionsMenu(false);
        return view;
    }

    private void loadMovies() {
        AppPreferences appPreferences = new AppPreferences();
        String preference = appPreferences.getSettingsSP(getActivity());
        if (preference.equals(getText(R.string.favourites))) {
            ArrayList<AllMoviesBean> arrayList = new ArrayList<AllMoviesBean>();

            Cursor cursor = getActivity().getContentResolver().query(MoviesContract.FavouritesEntry.CONTENT_URI_FAVOURITES_ENTRY,
                    null,
                    null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    AllMoviesBean bean = new AllMoviesBean();
                    bean.setId(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID)));
                    bean.setName(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_MOVIE_NAME)));
                    bean.setPoster_link(cursor.getString(cursor.getColumnIndex(MoviesContract.FavouritesEntry.COLUMN_POSTER_LINK)));
                    arrayList.add(bean);
                } while (cursor.moveToNext());
            }
            cursor.close();
            if (arrayList.size() > 0)
                recyclerView.setAdapter(new GridLayoutAdapter(getActivity(), arrayList, mIsTwoPane));
            else {
                Toast.makeText(getActivity(),"You do not have any favourites yet!",Toast.LENGTH_SHORT).show();
            }
        } else {
            String url = String.format(AppConstants.REQUEST_URL, preference);
            VolleyRequest.sendRequest(getActivity(), url, this, AppConstants.POSTER_REQUEST_ID);
        }
    }

    @Override
    public void onSuccess(String response, int requestId) {
        Log.i(TAG, "onSuccess");
        if (!isAdded())
            return;
        Parser parser = new Parser();
        final ArrayList<AllMoviesBean> arrayList = parser.parseAllMovies(response);
//        if (arrayList.size() > 0)
        recyclerView.invalidate();
        recyclerView.setAdapter(new GridLayoutAdapter(getActivity(), arrayList, mIsTwoPane));
        Log.i(TAG, "onSuccess-Setting Adapter");
//        else {
//            //todo: zero state
//        }
    }

    @Override
    public void onFailure(String error, int requestId) {

    }

}
