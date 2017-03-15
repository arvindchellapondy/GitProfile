package zerobase.us.gitprofile.api;

import java.util.ArrayList;

import zerobase.us.gitprofile.model.GitProfileModel;
import zerobase.us.gitprofile.presenters.GitProfilePresenter;
import zerobase.us.gitprofile.views.GitProfileView;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class UsersApi {

    public interface UsersView extends GitProfileView {

        void showSearchedUsers(ArrayList<GitProfileModel> users);

        void showSearchUserSuggestions(ArrayList<GitProfileModel> users, String keyword);

        void showNewUserSearchDownloading();

        void showMessage(String Message);

    }

    public interface UsersViewPresenter extends GitProfilePresenter<UsersView> {

        void onUserProfileSelected(GitProfileModel selectedUser);
    }

}
