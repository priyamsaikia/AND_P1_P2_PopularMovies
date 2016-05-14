package apps.orchotech.com.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */

public class AppPreferences {
    SharedPreferences mSharedPreferences;

    SharedPreferences getSP(Context context) {
        if (mSharedPreferences == null)
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return mSharedPreferences;
    }

    public String getSettingsSP(Context context) {
        if (getSP(context) != null) {
            if (!TextUtils.isEmpty(getSP(context).getString(AppConstants.SETTINGS_KEY, "")))
                return getSP(context).getString(AppConstants.SETTINGS_KEY, "");
            else return "popular";
        } else return "popular";
    }
}
