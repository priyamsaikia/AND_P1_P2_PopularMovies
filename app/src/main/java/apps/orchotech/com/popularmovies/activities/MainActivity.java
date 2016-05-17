package apps.orchotech.com.popularmovies.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import apps.orchotech.com.popularmovies.BaseActivity;
import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.fragments.DetailFragment;
import apps.orchotech.com.popularmovies.fragments.MasterFragment;
import apps.orchotech.com.popularmovies.utils.AppConstants;

public class MainActivity extends BaseActivity implements MasterFragment.CallBack {
    Boolean mIsTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (findViewById(R.id.fragment_detail) != null) {
            mIsTwoPane = true;
        } else {
            mIsTwoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.menu_favourites) {
            startActivity(new Intent(this, FavouritesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onItemSelected(int position, String movieId) {
        if (mIsTwoPane) {
            Bundle b = new Bundle();
            b.putString(AppConstants.MOVIE_ID, movieId);
            DetailFragment f = new DetailFragment();
            f.setArguments(b);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_detail, f, AppConstants.TAG);
            fragmentTransaction.commit();
        } else {
            //startActivity with data
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(AppConstants.MOVIE_ID, movieId);
            startActivity(intent);
        }
    }
}
