package apps.orchotech.com.popularmovies.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import apps.orchotech.com.popularmovies.BaseActivity;
import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.fragments.DetailFragment;
import apps.orchotech.com.popularmovies.utils.AppConstants;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class DetailsActivity extends BaseActivity {
    String movieId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(savedInstanceState==null) {
            movieId = getIntent().getStringExtra(AppConstants.MOVIE_ID);
            Bundle b = new Bundle();
            b.putString(AppConstants.MOVIE_ID, movieId);
            DetailFragment f = new DetailFragment();
            f.setArguments(b);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_detail, f);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
