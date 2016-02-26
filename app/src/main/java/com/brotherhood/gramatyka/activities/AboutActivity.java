package com.brotherhood.gramatyka.activities;

import android.widget.TextView;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.utils.BaseActivity;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class AboutActivity extends BaseActivity {
    @Override
    protected void customOnCreate() {
        setContentView(R.layout.about_activity);
        TextView textView = (TextView)findViewById(R.id.textView7);
        textView.setText(getString(R.string.about_content)
                .replaceAll("SIZE_OF_DATABASE"
                        ,Integer.toString(getDatabaseHelper().getAllTaskModels().size())));
    }
}
