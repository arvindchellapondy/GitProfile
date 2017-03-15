package zerobase.us.gitprofile.api;

import zerobase.us.gitprofile.presenters.GitProfilePresenter;
import zerobase.us.gitprofile.views.GitProfileView;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class UserProfileApi {

    public interface UserProfileView extends GitProfileView {
    }

    public interface UserProfilePresenter extends GitProfilePresenter<UserProfileView> {
    }
}
