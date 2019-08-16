package com.hktstudio.thibanglaixe.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hktstudio.thibanglaixe.R;

public class InformationActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void btn_infor(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
