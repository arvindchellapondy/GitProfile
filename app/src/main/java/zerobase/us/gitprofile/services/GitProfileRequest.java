package zerobase.us.gitprofile.services;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

/**
 * Customised request for Volley
 */
public class GitProfileRequest<T> extends Request<T> {

    private final Gson gson = new Gson();
    private Response.Listener<T> listener;
    private final Class<T> clazz;
    private final Map<String, String> headers;

    private static final String PROTOCOL_CHARSET = "utf-8";


    public GitProfileRequest(String url, Class<T> clazz, Map<String, String> headers,
                          Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        return headers;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json = null;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response));
    }

}
