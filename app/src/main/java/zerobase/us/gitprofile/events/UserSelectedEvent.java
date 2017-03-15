package zerobase.us.gitprofile.events;


import zerobase.us.gitprofile.model.GitProfileModel;

/**
 * Created by arvindchellapondy on 3/12/17.
 */

public class UserSelectedEvent {

    public GitProfileModel mSelectedUser;

    public UserSelectedEvent(GitProfileModel user) {
        this.mSelectedUser = user;
    }

}
