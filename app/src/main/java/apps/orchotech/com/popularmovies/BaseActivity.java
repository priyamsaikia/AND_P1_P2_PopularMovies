package apps.orchotech.com.popularmovies;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class BaseActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;

    protected void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage("Please wait while loading");
        mProgressDialog.setTitle("Downloading...");
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

}
