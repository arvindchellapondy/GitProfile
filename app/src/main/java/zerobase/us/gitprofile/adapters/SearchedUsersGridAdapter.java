package zerobase.us.gitprofile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zerobase.us.gitprofile.R;
import zerobase.us.gitprofile.model.GitProfileModel;
import zerobase.us.gitprofile.views.RoundedImageView;


/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class SearchedUsersGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<GitProfileModel> mSearchedUsersList;
    private LayoutInflater mLayoutInflater;

    public SearchedUsersGridAdapter(Context context){
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSearchedUsersList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mSearchedUsersList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSearchedUsersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        GitProfileModel user = mSearchedUsersList.get(position);

        SearchedUserGridItemViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.searched_user_grid_item_layout, parent, false);
            viewHolder = new SearchedUserGridItemViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (SearchedUserGridItemViewHolder) convertView.getTag();
        }

        Picasso.with(convertView.getContext())
                .load(user.getAvatarUrl())
                .into(viewHolder.mImageView);

        viewHolder.mTextView.setText(user.getLogin());

        return convertView;
    }

    public void addUsers(ArrayList<GitProfileModel> users){
        this.mSearchedUsersList.clear();
        this.mSearchedUsersList.addAll(users);
        notifyDataSetChanged();
    }

    static class SearchedUserGridItemViewHolder {

        @BindView(R.id.searched_user_grid_item_image)
        RoundedImageView mImageView;

        @BindView(R.id.searched_user_grid_item_name)
        TextView mTextView;

        SearchedUserGridItemViewHolder(View view) {
            ButterKnife.bind(this,view);

        }

    }
}
