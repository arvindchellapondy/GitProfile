package zerobase.us.gitprofile.events;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class GitProfileEventBusProvider {

    private static final GitProfileEventBus kBUS = new GitProfileEventBus();

    public static GitProfileEventBus getInstance() {
        return kBUS;
    }

    public static class GitProfileEventBus extends Bus {
        public GitProfileEventBus() {
            super(ThreadEnforcer.ANY);
        }

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mUiThread.post(new Runnable() {
                    @Override
                    public void run() {
                        GitProfileEventBus.super.post(event);
                    }
                });
            }
        }

        private final Handler mUiThread = new Handler(Looper.getMainLooper());
    }

    private GitProfileEventBusProvider() {
    }

}
