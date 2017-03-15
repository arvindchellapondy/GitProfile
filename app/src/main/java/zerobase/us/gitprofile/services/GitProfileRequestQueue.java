package zerobase.us.gitprofile.services;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class GitProfileRequestQueue {

    private static GitProfileRequestQueue mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private GitProfileRequestQueue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized GitProfileRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GitProfileRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }
}
