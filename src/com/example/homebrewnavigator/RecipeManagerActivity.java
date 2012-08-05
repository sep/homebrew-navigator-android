package com.example.homebrewnavigator;

import java.util.ArrayList;
import java.util.List;

import beerxml.RECIPE;
import beerxml.RecipeRepository;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

		ViewPager viewPager = new ViewPager(this);
		viewPager.setId(R.id.pager);
		setContentView(viewPager);

		TabsAdapter tabsAdapter = new TabsAdapter(this, viewPager);

		for (String category : new RecipeRepository().getCategories()) {
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
		// registerReceiver(br);
		// repository needs to send this broadcast

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog
				.setMessage("Please wait while we load recipes for your enjoyment.");
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
		Bundle mBundledArguments;
		Context mContext;
		RecipeRepository mRepository;

		public void setContext(Context context) {
			mContext = context;
		}

		public RecipeCategoryListFragment() {
			super();
			mBundledArguments = getArguments();
			mRepository = new RecipeRepository();
		}

		static RecipeCategoryListFragment newInstance(Bundle bundle,
				Context context) {
			RecipeCategoryListFragment newFrag = new RecipeCategoryListFragment();
			newFrag.setContext(context);
			newFrag.mBundledArguments = bundle;
			return newFrag;
		}

		String fagmentTitle = "truth";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			if (mBundledArguments != null
					&& mBundledArguments.containsKey("category")) {
				fagmentTitle = mBundledArguments.getString("category");
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.category_recipe_list, container,
					false);
			View tv = v.findViewById(R.id.category);
			((TextView) tv).setText(fagmentTitle);

			final List<RECIPE> recipes = mRepository.recipesForCategory(fagmentTitle);
			ListView lvRecipes = (ListView) v.findViewById(R.id.recipeList);

			RecipeListAdapter recipeAdapter = new RecipeListAdapter(mContext,
					recipes);
			lvRecipes.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//					((TextView) view.findViewById(R.id.yourTextViewId))
//							.getText();
					
					startActivity(new Intent()
						.putExtra("recipeName", recipes.get(position).getNAME())
						.setClassName(mContext, RecipeActivity.class.getName()));
				}
			});
			lvRecipes.setAdapter(recipeAdapter);

			return v;
		}

		public class RecipeListAdapter extends ArrayAdapter<RECIPE> {
			private final Context context;
			private final List<RECIPE> values;

			public RecipeListAdapter(Context context, List<RECIPE> recipes) {
				super(context, android.R.layout.simple_list_item_1, recipes);

				this.context = context;
				this.values = recipes;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// TODO: go make row view
				// TODO: set details on that row view
				View rowView = inflater.inflate(
						android.R.layout.simple_list_item_1, parent, false);
				TextView textView = (TextView) rowView
						.findViewById(android.R.id.text1);
				textView.setText(values.get(position).getNAME());

				return rowView;
			}
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
	 * details of connecting a ViewPager with associated TabHost. It relies on a
	 * trick. Normally a tab host has a simple API for supplying a View or
	 * Intent that each tab will show. This is not sufficient for switching
	 * between pages. So instead we make the content part of the tab host 0dp
	 * high (it is not shown) and the TabsAdapter supplies its own dummy view to
	 * show as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct paged in the ViewPager whenever the selected tab
	 * changes.
	 */
	public static class TabsAdapter extends FragmentPagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {
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
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
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
			for (int i = 0; i < mTabs.size(); i++) {
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
