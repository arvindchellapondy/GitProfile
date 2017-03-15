package zerobase.us.gitprofile.presenters;

import zerobase.us.gitprofile.views.GitProfileView;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public interface GitProfilePresenter<V extends GitProfileView> {

    void attachView(V view);
    void detachView();
    boolean viewIsAttached();

}
