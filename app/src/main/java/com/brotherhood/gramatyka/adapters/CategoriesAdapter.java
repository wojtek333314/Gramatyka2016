package com.brotherhood.gramatyka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.models.CategoryModel;
import com.brotherhood.gramatyka.utils.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class CategoriesAdapter extends BaseAdapter {
    private ArrayList<CategoryModel> data = new ArrayList<>();
    private BaseActivity baseActivity;

    public CategoriesAdapter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CategoryModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = baseActivity.getLayoutInflater();
        View row;
        row = inflater.inflate(R.layout.categories_list_row, parent, false);
        TextView name, mark;

        name = (TextView) row.findViewById(R.id.category);
        mark = (TextView) row.findViewById(R.id.rate);

        name.setText(data.get(position).getName());
        mark.setText(floatMarkToString(baseActivity,data.get(position).getRate()*6));

        return (row);
    }

    public static String floatMarkToString(Context context,float mark){
        String stringMark = context.getString(R.string.yourMark);
        if(mark == 0)
            stringMark += " -";
        if(mark > 0 && mark <2)
            stringMark += " 1";

        for(int i=1;i<6;i++)
            if(mark >= i && mark < i+1)
                stringMark += " "+String.valueOf(i);

        if(mark - (int)mark > 0.5f)
            stringMark+="+";

        return stringMark;
    }

    public void addCategoryModel(CategoryModel categoryModel){
        data.add(categoryModel);
    }
}
