package zerobase.us.gitprofile.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import zerobase.us.gitprofile.R;
import zerobase.us.gitprofile.adapters.SearchUserRecyclerAdapter;
import zerobase.us.gitprofile.adapters.SearchedUsersGridAdapter;
import zerobase.us.gitprofile.api.UsersApi;
import zerobase.us.gitprofile.events.GitProfileEventBusProvider;
import zerobase.us.gitprofile.model.GitProfileModel;
import zerobase.us.gitprofile.presenters.UserViewPresenter;


/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class UsersView extends GitProfileRelativeContentView implements UsersApi.UsersView, SearchUserRecyclerAdapter.SuggestedUserSelectioninterface {

    @BindView(R.id.users_view_message_text_view)
    TextView mMessageTextView;

    @BindView(R.id.search_user_recycler_view)
    RecyclerView mSearchUserRecyclerView;

    @BindView(R.id.searched_users_grid_view)
    GridView mSearchedUsersGridView;

    @BindView(R.id.searched_users_loading_progress)
    ProgressBar mSearchedUsersLoadingView;

    private SearchedUsersGridAdapter mSearchedUsersGridAdapter;
    private SearchUserRecyclerAdapter mSearchUserRecyclerAdapter;

    private UserViewPresenter mPresenter = new UserViewPresenter(getContext());

    private ArrayList<GitProfileModel> mSearchedUsers = new ArrayList<>();
    private ArrayList<GitProfileModel> mSuggestedUsers = new ArrayList<>();


    public UsersView(Context context) {
        super(context);
    }

    public UsersView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UsersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UsersView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mPresenter.attachView(this);
        GitProfileEventBusProvider.getInstance().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        mPresenter.detachView();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSearchedUsersLoadingView.setVisibility(View.VISIBLE);

        mSearchedUsersGridAdapter = new SearchedUsersGridAdapter(getContext());
        mSearchedUsersGridView.setAdapter(mSearchedUsersGridAdapter);

        mSearchedUsersGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.onUserProfileSelected(mSearchedUsers.get(position));
            }
        });

        mSearchUserRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchUserRecyclerView.setLayoutManager(llm);
        mSearchUserRecyclerAdapter = new SearchUserRecyclerAdapter(getContext(), this);
        mSearchUserRecyclerView.setAdapter(mSearchUserRecyclerAdapter);
    }

    @Override
    public void showSearchedUsers(ArrayList<GitProfileModel> users) {

        mSearchedUsersLoadingView.setVisibility(View.GONE);
        mMessageTextView.setVisibility(View.GONE);
        mSearchUserRecyclerView.setVisibility(View.GONE);
        mSearchedUsersGridView.setVisibility(View.VISIBLE);

        this.mSearchedUsers.clear();
        this.mSearchedUsers.addAll(users);
        mSearchedUsersGridAdapter.addUsers(users);
    }

    @Override
    public void showSearchUserSuggestions(ArrayList<GitProfileModel> users, String keyword) {
        mSearchedUsersLoadingView.setVisibility(View.GONE);
        mMessageTextView.setVisibility(View.GONE);
        mSearchedUsersGridView.setVisibility(View.GONE);
        mSearchUserRecyclerView.setVisibility(View.VISIBLE);

        this.mSuggestedUsers.clear();
        this.mSuggestedUsers.addAll(users);
        mSearchUserRecyclerAdapter.addUserSuggestionList(users, keyword);
    }

    @Override
    public void showNewUserSearchDownloading() {
        Log.d("view","showNewUserSearchDownloading");
        mSearchedUsersLoadingView.setVisibility(View.GONE);
        mSearchedUsersGridView.setVisibility(View.GONE);
        mSearchUserRecyclerView.setVisibility(View.GONE);
        mMessageTextView.setVisibility(View.GONE);

        mSearchedUsersLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        mSearchedUsersLoadingView.setVisibility(View.GONE);
        mSearchedUsersGridView.setVisibility(View.GONE);
        mSearchUserRecyclerView.setVisibility(View.GONE);
        mMessageTextView.setVisibility(View.VISIBLE);

        mMessageTextView.setText(message);
    }

    @Override
    public void onSuggestedUserItemSelection(GitProfileModel user){
        mPresenter.onUserProfileSelected(user);
    }

}
