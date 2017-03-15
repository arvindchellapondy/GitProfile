package zerobase.us.gitprofile.presenters;

import zerobase.us.gitprofile.views.GitProfileView;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class GitProfileBasePresenter<V extends GitProfileView> implements GitProfilePresenter<V> {

    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean viewIsAttached() {
        return (mView != null);
    }
}
