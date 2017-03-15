package zerobase.us.gitprofile.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class GitProfileModel extends SugarRecord implements Parcelable, Comparable {

    @SerializedName("login")
    private String mLogin;

    @SerializedName("avatar_url")
    private String mAvatarUrl;

    @SerializedName("name")
    private String mName;

    @SerializedName("location")
    private String mLocation;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("public_repos")
    private int mPublicRepos;

    @SerializedName("followers")
    private int mFollowers;

    @SerializedName("following")
    private int mFollowing;

    public GitProfileModel() {
    }

    public String getLogin() {
        return (!TextUtils.isEmpty(mLogin) ? mLogin : "");
    }

    public String getAvatarUrl() {
        return (!TextUtils.isEmpty(mAvatarUrl) ? mAvatarUrl : "");
    }

    public String getName() {
        return (!TextUtils.isEmpty(mName) ? mName : "");
    }

    public String getLocation() {
        return (!TextUtils.isEmpty(mLocation) ? mLocation : "");
    }

    public String getEmail() {
        return (!TextUtils.isEmpty(mEmail) ? mEmail : "");
    }

    public int getPublicRepos() {
        return mPublicRepos;
    }

    public int getFollowers() {
        return mFollowers;
    }

    public int getFollowing() {
        return mFollowing;
    }

    public void setLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.mAvatarUrl = avatarUrl;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public void setPublicRepos(int publicRepos) {
        this.mPublicRepos = publicRepos;
    }

    public void setFollowers(int followers) {
        this.mFollowers = followers;
    }

    public void setFollowing(int following) {
        this.mFollowing = following;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mLogin);
        dest.writeString(this.mAvatarUrl);
        dest.writeString(this.mName);
        dest.writeString(this.mLocation);
        dest.writeString(this.mEmail);
        dest.writeInt(this.mPublicRepos);
        dest.writeInt(this.mFollowers);
        dest.writeInt(this.mFollowing);
    }

    protected GitProfileModel(Parcel in) {
        this.mLogin = in.readString();
        this.mAvatarUrl = in.readString();
        this.mName = in.readString();
        this.mLocation = in.readString();
        this.mEmail = in.readString();
        this.mPublicRepos = in.readInt();
        this.mFollowers = in.readInt();
        this.mFollowing = in.readInt();
    }

    public static final Parcelable.Creator<GitProfileModel> CREATOR = new Parcelable.Creator<GitProfileModel>() {
        @Override
        public GitProfileModel createFromParcel(Parcel source) {
            return new GitProfileModel(source);
        }

        @Override
        public GitProfileModel[] newArray(int size) {
            return new GitProfileModel[size];
        }
    };


    @Override
    public int compareTo(Object o) {
        GitProfileModel that = (GitProfileModel) o;
        return this.getLogin().compareTo(that.getLogin());
    }
}
