package com.brotherhood.gramatyka.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.activities.enums.GameMode;
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
        final CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this);

        for(CategoryModel categoryModel : getDatabaseHelper().getAllCategories())
        {
            categoryModel.setRate(getSharedPrefsHelper().getFloat(categoryModel.getName()+"_rate"));
            categoriesAdapter.addCategoryModel(categoryModel);
        }

        listView.setAdapter(categoriesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                GameActivity.setGameMode(GameMode.CATEGORY_MODE);
                final Intent i = new Intent(getApplicationContext(), GameActivity.class);
                i.putExtra("category", categoriesAdapter.getItem(position).getName());
                if(getSharedPrefsHelper().getString(categoriesAdapter.getItem(position).getName() + "_status") == null)
                {
                    startActivity(i);
                    return;
                }

                showMsgBox("", "Wznowić poprzednio zakończoną sesję czy zacząć od nowa?","Wznów", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        i.putExtra("session", true);
                        startActivity(i);
                    }
                }, "Zacznij od nowa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        i.putExtra("session", false);
                        getSharedPrefsHelper().putString(categoriesAdapter.getItem(position).getName() + "_status", null);
                        getSharedPrefsHelper().putString(categoriesAdapter.getItem(position).getName() + "_goodAnswers", null);
                        startActivity(i);
                    }
                });



            }
        });
    }
}
