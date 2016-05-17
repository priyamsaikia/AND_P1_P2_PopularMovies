package apps.orchotech.com.popularmovies.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.adapters.FavouritesListAdapter;
import apps.orchotech.com.popularmovies.data.MoviesContract;
import apps.orchotech.com.popularmovies.data.MoviesDBHelper;
import apps.orchotech.com.popularmovies.network.PopularMoviesBean;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PriyamSaikia on 17-05-2016.
 */
public class FavouritesActivity extends AppCompatActivity {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    ArrayList<PopularMoviesBean> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        MoviesDBHelper moviesDBHelper = new MoviesDBHelper(this);
        SQLiteDatabase sqLiteDatabase = moviesDBHelper.getReadableDatabase();
        arrayList = new ArrayList<PopularMoviesBean>();
        Cursor cursor = sqLiteDatabase.query(MoviesContract.FavouritesEntry.TABLE_NAME, null, null, null, null, null, null);
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
        recyclerView.setAdapter(new FavouritesListAdapter(this, arrayList));
    }
}
