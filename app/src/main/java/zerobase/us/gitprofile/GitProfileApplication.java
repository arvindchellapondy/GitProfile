package zerobase.us.gitprofile;

import com.orm.SugarApp;

import zerobase.us.gitprofile.util.GitProfileSharedPreferences;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class GitProfileApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();

        GitProfileSharedPreferences.INSTANCE.init(this);
    }
}
