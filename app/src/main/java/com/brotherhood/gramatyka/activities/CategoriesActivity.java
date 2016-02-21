package com.brotherhood.gramatyka.activities;

import android.widget.ListView;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.adapters.CategoriesAdapter;
import com.brotherhood.gramatyka.models.CategoryModel;
import com.brotherhood.gramatyka.utils.BaseActivity;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class CategoriesActivity extends BaseActivity {


    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_categories);

        ListView listView = (ListView)findViewById(R.id.categoriesListView);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this);

        CategoryModel categoryModel1 = new CategoryModel("test");
        categoryModel1.setRate(3.6f);
        categoriesAdapter.addCategoryModel(categoryModel1);

        listView.setAdapter(categoriesAdapter);
    }
}
