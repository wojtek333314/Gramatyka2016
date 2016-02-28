package com.brotherhood.gramatyka.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.adapters.CategoriesAdapter;
import com.brotherhood.gramatyka.utils.BaseActivity;

/**
 * Created by Wojtek on 2016-02-25.
 */
public class SummaryActivity extends BaseActivity {
    @Override
    protected void customOnCreate() {
        setContentView(R.layout.summary_activity);
        TextView mark = (TextView)findViewById(R.id.textView10);
        TextView sumup = (TextView)findViewById(R.id.textView11);

        int goodAnswers = getIntent().getExtras().getInt("goodAnswers");
        int allAnswers = getIntent().getExtras().getInt("allAnswers");
        String category = getIntent().getExtras().getString("category");
        if(category!=null)
        {
            getSharedPrefsHelper().putFloat(category + "_rate", ((float) goodAnswers / (float) allAnswers));
        }

        sumup.setText(getString(R.string.sumup)+" "+String.valueOf(goodAnswers)+"/"+String.valueOf(allAnswers)+" zadań.");
        mark.setText(getMark(goodAnswers,allAnswers));

        Button ok = (Button)findViewById(R.id.button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });
    }

    private String getMark(int goodAnswers,int allAnswers){
        float mark = (float)goodAnswers/(float)allAnswers;
        mark*=6;
        System.out.println("RR:"+mark);
        return CategoriesAdapter.floatMarkToString(this,mark).replaceAll(getString(R.string.yourMark),"");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }
}
