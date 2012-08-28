package com.example.homebrewnavigator;


import datamodel.RecipeRepository;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

public class RecipeManagerActivity extends Activity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		this.getFragmentManager();

		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ViewPager viewPager = new ViewPager(this);
		viewPager.setId(R.id.pager);
		setContentView(viewPager);

		TabsAdapter tabsAdapter = new TabsAdapter(this, viewPager);

		Tab importTab = bar.newTab();
		importTab.setTag("import");
		importTab.setText("Import");
		tabsAdapter.addTab(importTab, ImportTabFragment.class, new Bundle());
		
		for (String category : new RecipeRepository(MyContext.getDb()).getCategories()) {
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
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}
}
