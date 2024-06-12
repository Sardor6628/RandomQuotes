package com.example.randomquotes;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class ApiViewModel extends AndroidViewModel {

    public ApiViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadQuote(final OnSuccessListener onSuccess) {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        String url = "https://api.quotable.io/random";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onSuccess.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        );
        // Add the request to the request queue
        queue.add(jsonObjectRequest);
    }

    public interface OnSuccessListener {
        void onSuccess(JSONObject response);
    }
}
