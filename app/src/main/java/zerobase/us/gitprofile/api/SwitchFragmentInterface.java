package zerobase.us.gitprofile.api;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public interface SwitchFragmentInterface {

    public void switchToFragment(Fragment targetFragment);

    public void switchToFragment(Fragment targetFragment, boolean addToBackstack);

    public void switchToFragment(Fragment targetFragment, boolean addToBackStack, Bundle bundle);

}
