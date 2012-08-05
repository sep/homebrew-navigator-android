package com.example.homebrewnavigator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This demonstrates the use of action bar tabs and how they interact with other
 * action bar features.
 */
public class RecipeManagerActivity extends Activity {

	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.getFragmentManager();

		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
//		bar.setTitle("Recipe Manager");
		
		ViewPager viewPager = new ViewPager(this);
		viewPager.setId(R.id.pager);
		setContentView(viewPager);
		TabsAdapter tabsAdapter = new TabsAdapter(this, viewPager);
		
		
		ArrayList<String> categories = new ArrayList<String>();
		categories.add("Stout");
		categories.add("IPA");
		categories.add("Porter");
		categories.add("Wheat");

		for (String category : categories) {
			Bundle bundle = new Bundle();
			bundle.putString("category", category);

			Tab newTab = bar.newTab();
			newTab.setTag(category);
			newTab.setText(category);

			tabsAdapter.addTab(newTab, RecipeCategoryListFragment.class, bundle);
		}

		if (savedInstanceState != null) {
			bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
		}
		
//		setContentView(R.layout.recipe_manager);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		BroadcastReceiver br = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				closeProgressDialog();
			}
			
		};
		//registerReceiver(br);
		//repository needs to send this broadcast
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Please wait while we load recipes for your enjoyment.");
		mProgressDialog.show();
	}
	
	

	protected void closeProgressDialog() {
		mProgressDialog.dismiss();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

	public static class RecipeCategoryListFragment extends Fragment {
		Bundle bundledArguments;
		
		public RecipeCategoryListFragment(){
			super();
			bundledArguments = getArguments();
			
		}
		
		static RecipeCategoryListFragment newInstance(Bundle bundleBitches, Context context) {
			
			RecipeCategoryListFragment newFrag = new RecipeCategoryListFragment();
			newFrag.bundledArguments = bundleBitches;
			return newFrag;
		}
        
        String bobIsAwesome = "truth";
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			if (bundledArguments != null && bundledArguments.containsKey("category")){
				bobIsAwesome = bundledArguments.getString("category");
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.category_recipe_list, container,
					false);
			View tv = v.findViewById(R.id.category);
			((TextView) tv).setText(bobIsAwesome);
			tv.setBackgroundDrawable(getResources().getDrawable(
					android.R.drawable.gallery_thumb));
			return v;
		}

		public static class TabListener<T extends Fragment> implements
				ActionBar.TabListener {
			private final Activity mActivity;
			private final String mTag;
			private final Class<T> mClass;
			private final Bundle mArgs;
			private Fragment mFragment;

			public TabListener(Activity activity, String tag, Class<T> clz) {
				this(activity, tag, clz, null);
			}

			@SuppressLint({ "NewApi", "NewApi" })
			public TabListener(Activity activity, String tag, Class<T> clz,
					Bundle args) {
				mActivity = activity;
				mTag = tag;
				mClass = clz;
				mArgs = args;

				// Check to see if we already have a fragment for this tab,
				// probably
				// from a previously saved state. If so, deactivate it, because
				// our
				// initial state is that a tab isn't shown.
				mFragment = mActivity.getFragmentManager().findFragmentByTag(
						mTag);
				if (mFragment != null && !mFragment.isDetached()) {
					FragmentTransaction ft = mActivity.getFragmentManager()
							.beginTransaction();
					ft.detach(mFragment);
					ft.commit();
				}
			}

			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT)
						.show();
			}

			public void onTabUnselected(Tab arg0,
					android.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}

			public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
		}
	}
	
	/**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentPagerAdapter
            implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }

        public TabsAdapter(Activity activity, ViewPager pager) {
        	super(activity.getFragmentManager());
        	mContext = activity;
            mActionBar = activity.getActionBar();
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return RecipeCategoryListFragment.newInstance(info.args, mContext);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mActionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            Object tag = tab.getTag();
            for (int i=0; i<mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }
    }
}
