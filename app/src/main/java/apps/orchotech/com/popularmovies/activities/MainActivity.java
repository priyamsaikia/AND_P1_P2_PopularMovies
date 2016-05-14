package apps.orchotech.com.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.adapters.PosterAdapter;
import apps.orchotech.com.popularmovies.network.AllMoviesBean;
import apps.orchotech.com.popularmovies.network.MyConnection;
import apps.orchotech.com.popularmovies.network.Parser;
import apps.orchotech.com.popularmovies.network.VolleyRequest;
import apps.orchotech.com.popularmovies.utils.AppConstants;
import apps.orchotech.com.popularmovies.utils.AppPreferences;

public class MainActivity extends AppCompatActivity implements MyConnection.IMyConnection {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.gridview);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(final String response, int requestId) {
        Parser parser = new Parser();
        final ArrayList<AllMoviesBean> arrayList = parser.parseAllMovies(response);

        gridView.setAdapter(new PosterAdapter(this, arrayList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(AppConstants.INTENT_MOVIE_DETAIL, arrayList.get(position).getId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onFailure(String error, int requestId) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        AppPreferences appPreferences = new AppPreferences();

        String url = String.format(AppConstants.REQUEST_URL, appPreferences.getSettingsSP(MainActivity.this));
        VolleyRequest.sendRequest(this, url, this, AppConstants.POSTER_REQUEST_ID);
    }
}
