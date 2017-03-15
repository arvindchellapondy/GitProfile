package zerobase.us.gitprofile.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

/**
 *  Enum to hold all the shared preferences
 */
public enum GitProfileSharedPreferences {

    INSTANCE;

    private static final String SHARED_PREFS_NAME = "git_profile_shared_prefs";
    private static final String GIT_API_URL_KEY = "git_api_url_key";
    private static final String GIT_API_USERS_PATH_KEY = "git_api_users_path_key";

    private static final String GIT_API_URL = "https://api.github.com";
    private static final String GIT_API_USERS_PATH = "/users/";

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        this.mSharedPreferences = this.mContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);

    }

    public void setGitApiUrl(String url){
        mSharedPreferences.edit().putString(GIT_API_URL_KEY, url).apply();
    }

    public String getGitApiUrl() {
        return mSharedPreferences.getString(GIT_API_URL_KEY, GIT_API_URL);
    }

    public void setGitApiUsersPath(String path){
        mSharedPreferences.edit().putString(GIT_API_USERS_PATH_KEY, path).apply();
    }

    public String getGitApiUsersPath() {
        return mSharedPreferences.getString(GIT_API_USERS_PATH_KEY, GIT_API_USERS_PATH);
    }
}
