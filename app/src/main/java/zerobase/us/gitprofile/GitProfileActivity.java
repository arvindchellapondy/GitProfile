package zerobase.us.gitprofile;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import zerobase.us.gitprofile.api.SwitchFragmentInterface;
import zerobase.us.gitprofile.events.GitProfileEventBusProvider;
import zerobase.us.gitprofile.events.SearchUserEvent;
import zerobase.us.gitprofile.events.UserSelectedEvent;
import zerobase.us.gitprofile.fragments.HomeFragment;
import zerobase.us.gitprofile.fragments.UserProfileFragment;
import zerobase.us.gitprofile.model.GitProfileModel;
import zerobase.us.gitprofile.services.GitProfileRequest;
import zerobase.us.gitprofile.services.GitProfileRequestQueue;
import zerobase.us.gitprofile.util.GitProfileSharedPreferences;


public class GitProfileActivity extends AppCompatActivity implements SwitchFragmentInterface {

    private static final String LOG_TAG = GitProfileActivity.class.getName();

    @BindView(R.id.activity_git_profile)
    RelativeLayout mLayout;

    // Search view and menu item for search
    private MenuItem mSearchMenuItem;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_profile);
        ButterKnife.bind(this);

        // setting up the home fragment
        if(savedInstanceState == null){
            switchToFragment(new HomeFragment(), false);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // registering event bus
        GitProfileEventBusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregistering event bus
        GitProfileEventBusProvider.getInstance().unregister(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        mSearchMenuItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) mSearchMenuItem.getActionView();

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setupSearch();
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Sets up the search view and handles all the event
     * trigger when search view focus, query changed / submitted
     */
    private void setupSearch() {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView =
                (SearchView) mSearchMenuItem.getActionView();
        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        MenuItemCompat.setOnActionExpandListener(mSearchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                GitProfileEventBusProvider.getInstance().post(new SearchUserEvent.FocusChangeEvent(true));
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                GitProfileEventBusProvider.getInstance().post(new SearchUserEvent.FocusChangeEvent(false));
                return true;
            }
        });

        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                GitProfileEventBusProvider.getInstance().post(new SearchUserEvent.FocusChangeEvent(hasFocus));
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sendRequest(query);
                mSearchMenuItem.collapseActionView();
                GitProfileEventBusProvider.getInstance().post(new SearchUserEvent.QuerySubmitEvent(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                GitProfileEventBusProvider.getInstance().post(new SearchUserEvent.QueryChangeEvent(newText));
                return false;
            }
        });
    }


    /**
     * sends the request to retrieve a user profile from github api
     *
     * @param query - username in String
     */
    @SuppressWarnings("unchecked")
    private void sendRequest(String query) {

        String url = GitProfileSharedPreferences.INSTANCE.getGitApiUrl()
                + GitProfileSharedPreferences.INSTANCE.getGitApiUsersPath()
                + query;

        HashMap<String, String> headersMap = new HashMap<>();

        GitProfileRequestQueue requestQueue = GitProfileRequestQueue.getInstance(this);
        GitProfileRequest request = new GitProfileRequest(url,
                GitProfileModel.class,
                headersMap,
                requestSuccessListener(),
                requestFailureListener());

        requestQueue.getRequestQueue().add(request);

    }

    /**
     * listener for the response from the services
     * @return
     */
    private Response.Listener<GitProfileModel> requestSuccessListener() {
        return new Response.Listener<GitProfileModel>() {
            @Override
            public void onResponse(GitProfileModel response) {

                // once the response is successfully received,
                // it is saved using sugar record
                response.save();
                openUserProfile(response);
            }
        };
    }

    private Response.ErrorListener requestFailureListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // triggers event when an error occurs / service failure
                GitProfileEventBusProvider.getInstance().post(new SearchUserEvent.ShowMessageEvent(getString(R.string.service_failure_message)));
            }
        };
    }

    /**
     *  Event listens to selection of already searched user
     * @param event
     */
    @Subscribe
    public void onSearchedUserProfileSelectEvent(UserSelectedEvent event) {
        if(mSearchMenuItem.isActionViewExpanded()){
            mSearchMenuItem.collapseActionView();
        }
        openUserProfile(event.mSelectedUser);
    }

    /**
     * Opens the {@link UserProfileFragment} with user profile
     *
     * @param user
     */
    private void openUserProfile(GitProfileModel user){
        Bundle bundle = new Bundle();
        bundle.putParcelable(UserProfileFragment.USER_PROFILE, user);
        switchToFragment(new UserProfileFragment(), true, bundle);
    }

    /**
     * Implementation for {@link SwitchFragmentInterface}
     * to handle all different types of fragment transactions.
     *
     */

    public void switchToFragment(Fragment targetFragment) {
        switchToFragment(targetFragment, false);
    }


    @Override
    public void switchToFragment(Fragment targetFragment, boolean addToBackstack) {
        switchToFragment(targetFragment, addToBackstack, null);
    }

    @Override
    public void switchToFragment(Fragment targetFragment, boolean addToBackStack, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (bundle != null) {
            targetFragment.setArguments(bundle);
        } else {
            if (targetFragment.getArguments() != null) {
                targetFragment.getArguments().clear();
            }
            targetFragment.setArguments(null);
        }
        fragmentTransaction.replace(R.id.fragment_container, targetFragment);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }
}
