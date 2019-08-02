package com.hktstudio.thibanglaixe.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;

import com.hktstudio.thibanglaixe.R;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by Hiep on 11/27/2017.
 */

public class KinhNghiemThiAActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinhnghiemthia);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ScrollView scrollView = findViewById(R.id.scrollView);
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
    }
}
