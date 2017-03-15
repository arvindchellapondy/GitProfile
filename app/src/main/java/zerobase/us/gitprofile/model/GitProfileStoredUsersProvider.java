package zerobase.us.gitprofile.model;

import java.util.ArrayList;

/**
 * Created by arvindchellapondy on 3/12/17.
 */

public class GitProfileStoredUsersProvider {

    public GitProfileStoredUsersProvider(){
    }

    public static ArrayList<GitProfileModel> getAllSearchedUsers(){
        return (ArrayList<GitProfileModel>) GitProfileModel.listAll(GitProfileModel.class);
    }

}
