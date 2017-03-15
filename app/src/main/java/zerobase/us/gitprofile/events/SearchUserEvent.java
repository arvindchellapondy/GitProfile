package zerobase.us.gitprofile.events;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public abstract class SearchUserEvent {

    public SearchUserEvent() {
    }

    public static class FocusChangeEvent extends SearchUserEvent {
        private boolean mHasFocus;

        public FocusChangeEvent(boolean hasFocus) {
            this.mHasFocus = hasFocus;
        }

        public boolean hasFocus() {
            return mHasFocus;
        }
    }

    public static class QueryChangeEvent extends SearchUserEvent {
        private String mQuery = "";

        public QueryChangeEvent(String queryText) {
            this.mQuery = (!TextUtils.isEmpty(queryText) ? queryText : "");
        }

        @NonNull
        public String getQuery() {
            return mQuery;
        }
    }

    public static class QuerySubmitEvent extends SearchUserEvent {
        private String mQuery = "";

        public QuerySubmitEvent(String queryText) {
            this.mQuery = (!TextUtils.isEmpty(queryText) ? queryText : "");
        }

        @NonNull
        public String getQuery() {
            return mQuery;
        }
    }

    public static class ShowMessageEvent extends SearchUserEvent {
        public String mMessage = "";

        public ShowMessageEvent(String mMessage) {
            this.mMessage = (!TextUtils.isEmpty(mMessage) ? mMessage : "");
        }
    }


}


