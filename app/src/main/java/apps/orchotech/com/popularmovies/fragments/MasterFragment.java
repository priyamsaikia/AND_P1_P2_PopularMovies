package apps.orchotech.com.popularmovies.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import apps.orchotech.com.popularmovies.network.AllMoviesBean;
import apps.orchotech.com.popularmovies.network.MyConnection;
import apps.orchotech.com.popularmovies.network.Parser;
import apps.orchotech.com.popularmovies.network.VolleyRequest;
import apps.orchotech.com.popularmovies.utils.AppConstants;
import apps.orchotech.com.popularmovies.utils.AppPreferences;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class MasterFragment extends Fragment implements MyConnection.IMyConnection {
    GridView gridView;
    private static final String TAG = MasterFragment.class.getSimpleName();

    @Override
    public void onStart() {
        super.onStart();
        loadMovies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.master_layout, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview);
        return view;
    }

    private void loadMovies() {
        AppPreferences appPreferences = new AppPreferences();
        String url = String.format(AppConstants.REQUEST_URL, appPreferences.getSettingsSP(getActivity()));
        VolleyRequest.sendRequest(getActivity(), url, this, AppConstants.POSTER_REQUEST_ID);
    }

    @Override
    public void onSuccess(String response, int requestId) {
        Log.d(TAG, response);
        //        hideProgress();
        Parser parser = new Parser();
        final ArrayList<AllMoviesBean> arrayList = parser.parseAllMovies(response);

        gridView.setAdapter(new PosterAdapter(getActivity(), arrayList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                intent.putExtra(AppConstants.MOVIE_ID, arrayList.get(position).getId());
                //startActivity(intent);
                ((CallBack)getActivity()).onItemSelected(position,arrayList.get(position).getId());
            }
        });

    }

    @Override
    public void onFailure(String error, int requestId) {

    }

    public interface CallBack {
        void onItemSelected(int position,String movieId);
    }
}
