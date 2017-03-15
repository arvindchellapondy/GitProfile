package zerobase.us.gitprofile.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import zerobase.us.gitprofile.R;
import zerobase.us.gitprofile.model.GitProfileModel;
import zerobase.us.gitprofile.views.RoundedImageView;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class UserProfileFragment extends Fragment {

    public static final String USER_PROFILE = "user_profile";

    private GitProfileModel mUserProfile = new GitProfileModel();

    @BindView(R.id.user_avatar_background_image)
    ImageView mUserAvatarBackgroundImageView;

    @BindView(R.id.user_avatar_image)
    RoundedImageView mUserAvatarImageView;

    @BindView(R.id.user_login_text_view)
    TextView mUserLoginTextView;

    @BindView(R.id.user_name_text_view)
    TextView mUserNameTextView;

    @BindView(R.id.user_followers_count_text_view)
    TextView mUserFollowersCountTextView;

    @BindView(R.id.user_following_count_text_view)
    TextView mUserFollowingCountTextView;

    @BindView(R.id.user_repository_count_text_view)
    TextView mUserRepositoryCountTextView;

    @BindView(R.id.user_location_icon)
    ImageView mUserLocationIcon;

    @BindView(R.id.user_location_text_view)
    TextView mUserLocationTextView;

    @BindView(R.id.user_email_icon)
    ImageView mUserEmailIcon;

    @BindView(R.id.user_email_text_view)
    TextView mUserEmailTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_search);
        item.setVisible(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            this.mUserProfile = bundle.getParcelable(USER_PROFILE);
        }

        if (!TextUtils.isEmpty(mUserProfile.getAvatarUrl())) {
            Picasso.with(getActivity())
                    .load(mUserProfile.getAvatarUrl())
                    .fit().centerCrop()
                    .into(mUserAvatarBackgroundImageView);

            Picasso.with(getActivity())
                    .load(mUserProfile.getAvatarUrl())
                    .fit().centerCrop()
                    .into(mUserAvatarImageView);
        }

        mUserLoginTextView.setText(mUserProfile.getLogin());
        mUserNameTextView.setText(mUserProfile.getName());
        mUserFollowersCountTextView.setText(Integer.toString(mUserProfile.getFollowers()));
        mUserFollowingCountTextView.setText(Integer.toString(mUserProfile.getFollowing()));
        mUserRepositoryCountTextView.setText(Integer.toString(mUserProfile.getPublicRepos()));
        mUserLocationTextView.setText(mUserProfile.getLocation());
        mUserEmailTextView.setText(mUserProfile.getEmail());

        mUserLocationIcon.setVisibility((!TextUtils.isEmpty(mUserProfile.getLocation())) ?
                View.VISIBLE : View.GONE);

        mUserEmailIcon.setVisibility((!TextUtils.isEmpty(mUserProfile.getEmail())) ?
                View.VISIBLE : View.GONE);

        return view;
    }
}
