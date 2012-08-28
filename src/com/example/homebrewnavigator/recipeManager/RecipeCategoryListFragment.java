package com.example.homebrewnavigator.recipeManager;

import java.util.List;

import com.example.homebrewnavigator.MyContext;
import com.example.homebrewnavigator.R;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import datamodel.RecipeManagerViewModel;
import datamodel.RecipeRepository;

public class RecipeCategoryListFragment extends Fragment {
		Bundle mBundledArguments;
		Context mContext;
		RecipeRepository mRepository;

		public void setContext(Context context) {
			mContext = context;
		}

		public RecipeCategoryListFragment() {
			super();
			mBundledArguments = getArguments();
			mRepository = new RecipeRepository(MyContext.getDb());
		}

		public static RecipeCategoryListFragment newInstance(Bundle bundle, Context context) {
			RecipeCategoryListFragment newFrag = new RecipeCategoryListFragment();
			newFrag.setContext(context);
			newFrag.mBundledArguments = bundle;
			return newFrag;
		}

		String fragmentTitle = "truth";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			if (mBundledArguments != null
					&& mBundledArguments.containsKey("category")) {
				fragmentTitle = mBundledArguments.getString("category");
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.category_recipe_list, container,
					false);
//			View tv = v.findViewById(R.id.category);
//			((TextView) tv).setText(fagmentTitle);

			final List<RecipeManagerViewModel> recipes = mRepository.recipesForCategory(fragmentTitle);
			ListView lvRecipes = (ListView) v.findViewById(R.id.recipeList);

			RecipeListAdapter recipeAdapter = new RecipeListAdapter(mContext,
					recipes);
			lvRecipes.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//					((TextView) view.findViewById(R.id.yourTextViewId))
//							.getText();
					
					startActivity(new Intent()
						.putExtra("recipeName", recipes.get(position).Name)
						.setClassName(mContext, RecipeActivity.class.getName()));
				}
			});
			lvRecipes.setAdapter(recipeAdapter);

			return v;
		}

		public class RecipeListAdapter extends ArrayAdapter<RecipeManagerViewModel> {
			private final Context context;
			private final List<RecipeManagerViewModel> values;

			public RecipeListAdapter(Context context, List<RecipeManagerViewModel> recipes) {
				super(context, R.layout.recipe_row_layout, recipes);

				this.context = context;
				this.values = recipes;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				View rowView = inflater.inflate(R.layout.recipe_row_layout, parent, false);
				TextView textView = (TextView) rowView.findViewById(R.id.recipeName);
				textView.setText(values.get(position).Name);
				
				RecipeManagerViewModel recipe = values.get(position);

				TextView ibu = (TextView) rowView.findViewById(R.id.textIBU);
				TextView abv = (TextView) rowView.findViewById(R.id.textABV);
				ibu.setText("IBU: " + recipe.IbuMax + " - " + recipe.IbuMin);
				abv.setText("ABV: " + recipe.AbvMax + " - " + recipe.AbvMin);

				return rowView;
			}
		}
	}