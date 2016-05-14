package apps.orchotech.com.popularmovies.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;

/**
 * Created by PriyamSaikia on 3/22/2016.
 */
public class VolleyRequest {
    private static final String TAG = VolleyRequest.class.getSimpleName();

    public static String encode(String url) {
        return URLEncoder.encode(url);
    }

    public static void sendRequest(final Context context, String url, final MyConnection.IMyConnection iMyConnection, final int reqId) {
        Log.i(TAG, url);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i(TAG, response);
                        iMyConnection.onSuccess(response, reqId);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iMyConnection.onFailure(error.getMessage(), reqId);
                if (error.getMessage() != null)
                    Log.i(TAG, error.getMessage());
            }
        });
        //Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
