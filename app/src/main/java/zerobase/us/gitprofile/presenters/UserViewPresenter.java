package zerobase.us.gitprofile.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;

import zerobase.us.gitprofile.R;
import zerobase.us.gitprofile.api.UsersApi;
import zerobase.us.gitprofile.events.GitProfileEventBusProvider;
import zerobase.us.gitprofile.events.SearchUserEvent;
import zerobase.us.gitprofile.events.UserSelectedEvent;
import zerobase.us.gitprofile.model.GitProfileModel;
import zerobase.us.gitprofile.model.GitProfileStoredUsersProvider;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class UserViewPresenter extends GitProfileBasePresenter<UsersApi.UsersView>
        implements UsersApi.UsersViewPresenter {

    private ArrayList<GitProfileModel> mUsersList = new ArrayList<>();
    private Context mContext;

    public UserViewPresenter(Context context){
        mContext = context;
    }

    @Override
    public void attachView(UsersApi.UsersView view) {
        super.attachView(view);
        GitProfileEventBusProvider.getInstance().register(this);

        mUsersList = GitProfileStoredUsersProvider.getAllSearchedUsers();
        Collections.sort(mUsersList);
        showSearchedUsers(mUsersList);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (viewIsAttached()) {
            GitProfileEventBusProvider.getInstance().unregister(this);
        }
    }

    @Override
    public boolean viewIsAttached() {
        return super.viewIsAttached();
    }

    public void showSearchedUsers(ArrayList<GitProfileModel> users) {
        if (viewIsAttached()) {
            if (!users.isEmpty()) {
                mView.showSearchedUsers(users);
            } else {
                mView.showMessage(mContext.getString(R.string.no_searched_users));
            }
        }
    }

    @Override
    public void onUserProfileSelected(GitProfileModel user){
        GitProfileEventBusProvider.getInstance().post(new UserSelectedEvent(user));
    }

    @Subscribe
    public void onSearchUserFocusChangeEvent(SearchUserEvent.FocusChangeEvent event) {

        if (viewIsAttached()) {
            if (event.hasFocus()) {
                mView.showMessage(mContext.getString(R.string.type_in_tosearch));

            } else {

                if (!mUsersList.isEmpty()) {
                    mView.showSearchedUsers(mUsersList);
                } else {
                    mView.showMessage(mContext.getString(R.string.no_searched_users));
                }
            }
        }
    }

    @Subscribe
    public void onSearchQueryChangeEvent(SearchUserEvent.QueryChangeEvent event) {

        String mCurrentQuery = event.getQuery();

        if (viewIsAttached()) {
            if (!TextUtils.isEmpty(mCurrentQuery)) {
                mView.showSearchUserSuggestions(getUserSuggestions(mCurrentQuery), mCurrentQuery);
            } else {
                mView.showMessage(mContext.getString(R.string.no_search_suggestions));
            }
        }
    }

    @Subscribe
    public void onSearchQuerySubmitEvent(SearchUserEvent.QuerySubmitEvent event) {

        String mCurrentQuery = event.getQuery();

        if (viewIsAttached()) {
            mView.showNewUserSearchDownloading();
        }
    }

    @Subscribe
    public void onShowMessageEvent(SearchUserEvent.ShowMessageEvent event) {

        String mMessage = event.mMessage;

        if (viewIsAttached()) {
            mView.showMessage(mMessage);
        }
    }

    private ArrayList<GitProfileModel> getUserSuggestions(String query) {
        String keyword = query.toLowerCase();
        ArrayList<GitProfileModel> suggestionList = new ArrayList<>();

        if (!mUsersList.isEmpty()) {
            for (GitProfileModel user : mUsersList) {

                String login = user.getLogin().toLowerCase();
                String name = user.getName().toLowerCase();

                if (login.contains(keyword) || name.contains(keyword)) {
                    suggestionList.add(user);
                }
            }
        }

        return suggestionList;
    }
}
