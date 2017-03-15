package zerobase.us.gitprofile.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import zerobase.us.gitprofile.R;
import zerobase.us.gitprofile.model.GitProfileModel;

/**
 * Created by arvindchellapondy on 3/12/17.
 */

public class SearchUserRecyclerAdapter extends RecyclerView.Adapter<SearchUserRecyclerAdapter.SearchUserItemViewHolder> {

    public interface SuggestedUserSelectioninterface {
        void onSuggestedUserItemSelection(GitProfileModel selectedUser);
    }

    private Context mContext;
    private ArrayList<GitProfileModel> mUserSuggestionList;
    private String mKeyword;
    private SuggestedUserSelectioninterface mListener;

    public SearchUserRecyclerAdapter(Context context, SuggestedUserSelectioninterface listener) {
        mContext = context;
        mUserSuggestionList = new ArrayList<>();
        mKeyword = "";
        this.mListener = listener;
    }

    @Override
    public SearchUserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_suggested_user_item_layout, parent, false);
        return new SearchUserItemViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(SearchUserItemViewHolder holder, int position) {
        final SearchUserRecyclerAdapter.SearchUserItemViewHolder viewHolder = (SearchUserRecyclerAdapter.SearchUserItemViewHolder) holder;
        final GitProfileModel userProfile = mUserSuggestionList.get(position);

        if (!TextUtils.isEmpty(userProfile.getAvatarUrl())) {
            Picasso.with(mContext)
                    .load(userProfile.getAvatarUrl())
                    .into(viewHolder.mUserImageView);
        } else {

        }

        if (!TextUtils.isEmpty(userProfile.getLogin())) {
            viewHolder.mUserLogin.setText(userProfile.getLogin());
            String userLogin = userProfile.getLogin().toLowerCase(Locale.getDefault());

            if (userLogin.contains(mKeyword)) {
                int startPos = userLogin.indexOf(mKeyword);
                int endPos = startPos + mKeyword.length();

                Spannable spanText = Spannable.Factory.getInstance().newSpannable(viewHolder.mUserLogin.getText());
                int highlightTextColor = mContext.getResources().getColor(R.color.colorPrimaryDark, null);
                spanText.setSpan(new ForegroundColorSpan(highlightTextColor), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                viewHolder.mUserLogin.setText(spanText, TextView.BufferType.SPANNABLE);
            }
        } else {
            viewHolder.mUserLogin.setText("");
        }

        if (!TextUtils.isEmpty(userProfile.getName())) {

            Log.d("recycelr", userProfile.getName());

            viewHolder.mUserName.setText(userProfile.getName());
            String userName = userProfile.getName().toLowerCase(Locale.getDefault());

            if (userName.contains(mKeyword)) {
                int startPos = userName.indexOf(mKeyword);
                int endPos = startPos + mKeyword.length();

                Spannable spanText = Spannable.Factory.getInstance().newSpannable(viewHolder.mUserName.getText());
                int highlightTextColor = mContext.getResources().getColor(R.color.colorPrimaryDark, null);
                spanText.setSpan(new ForegroundColorSpan(highlightTextColor), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                viewHolder.mUserName.setText(spanText, TextView.BufferType.SPANNABLE);
            }
        } else {
            viewHolder.mUserName.setText("");
        }

        viewHolder.mUserItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSuggestedUserItemSelection(userProfile);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserSuggestionList.size();
    }

    public void addUserSuggestionList(ArrayList<GitProfileModel> users, String keyword) {
        this.mUserSuggestionList.clear();
        this.mUserSuggestionList.addAll(users);
        this.mKeyword = keyword;
        notifyDataSetChanged();
    }

    public static class SearchUserItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.suggested_user_item_layout)
        LinearLayout mUserItemLayout;

        @BindView(R.id.suggested_user_profile_image)
        ImageView mUserImageView;

        @BindView(R.id.suggested_user_login)
        TextView mUserLogin;

        @BindView(R.id.suggested_user_name)
        TextView mUserName;

        public SearchUserItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
