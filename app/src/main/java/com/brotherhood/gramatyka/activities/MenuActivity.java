package com.brotherhood.gramatyka.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brotherhood.gramatyka.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final View quizView, categoriesView, aboutView;

        quizView = findViewById(R.id.quizView);
        categoriesView = findViewById(R.id.categoriesView);
        aboutView = findViewById(R.id.aboutView);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.equals(quizView))
                    startActivity(new Intent(getApplicationContext(),GameActivity.class));
                if(v.equals(categoriesView))
                    startActivity(new Intent(getApplicationContext(),CategoriesActivity.class));
                if(v.equals(aboutView))
                    startActivity(new Intent(getApplicationContext(),AboutActivity.class));
            }
        };

        quizView.setOnClickListener(clickListener);
        categoriesView.setOnClickListener(clickListener);
        aboutView.setOnClickListener(clickListener);
    }
}
