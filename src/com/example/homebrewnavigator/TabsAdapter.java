package com.example.homebrewnavigator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

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
public class TabsAdapter extends FragmentPagerAdapter implements
		ActionBar.TabListener, ViewPager.OnPageChangeListener {
	private final Context mContext;
	private final ActionBar mActionBar;
	private final ViewPager mViewPager;
	private final ArrayList<TabsAdapter.TabInfo> mTabs = new ArrayList<TabsAdapter.TabInfo>();

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
		TabsAdapter.TabInfo info = new TabInfo(clss, args);
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
		TabsAdapter.TabInfo info = mTabs.get(position);
		try {
			Method m = info.clss.getMethod("newInstance", new Class[]{Bundle.class, Context.class});
			return (Fragment)m.invoke(null, info.args, mContext);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException i) {
			i.printStackTrace();
			return null;
		} catch (IllegalArgumentException ia) {
			ia.printStackTrace();
			return null;
		} catch (IllegalAccessException iac) {
			iac.printStackTrace();
			return null;
		}
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