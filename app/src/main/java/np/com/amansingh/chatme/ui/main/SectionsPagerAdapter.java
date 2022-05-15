package np.com.amansingh.chatme.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.ui.main.fragments.PlaceholderFragment;
import np.com.amansingh.chatme.ui.main.fragments.callsFragment;
import np.com.amansingh.chatme.ui.main.fragments.chatListFragment;
import np.com.amansingh.chatme.ui.main.fragments.statusFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position==0)
             return chatListFragment.newInstance();
        else if(position==1)
            return  statusFragment.newInstance();
        else
            return callsFragment.newInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.

        return 3;
    }
}