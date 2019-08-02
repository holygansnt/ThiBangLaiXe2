package com.hktstudio.thibanglaixe.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.hktstudio.thibanglaixe.Adapter.AdapterRecyclerViewLyThuyet;
import com.hktstudio.thibanglaixe.DAO.CauHoiDAO;
import com.hktstudio.thibanglaixe.R;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by Hiep on 11/12/2017.
 */

public class LyThuyetActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView rcv_lyTHuyet;
    Button bt_truoc, bt_sau;
    AdapterRecyclerViewLyThuyet adapterRecyclerViewLyThuyet;
    int dem = 1;
    CauHoiDAO cauHoiDAO;
    Toolbar toolbar;
    TextView toolbar_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lythuyet);
        setControl();
    }
    public void setControl(){
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        bt_truoc = findViewById(R.id.bt_truoc);
        bt_sau = findViewById(R.id.bt_sau);
        rcv_lyTHuyet = findViewById(R.id.rcv_lyThuyet);
        cauHoiDAO = new CauHoiDAO(this);
        adapterRecyclerViewLyThuyet = new AdapterRecyclerViewLyThuyet(this,cauHoiDAO.getListCauHoi(dem));
        rcv_lyTHuyet.setAdapter(adapterRecyclerViewLyThuyet);
        rcv_lyTHuyet.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rcv_lyTHuyet, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        bt_truoc.setOnClickListener(this);
        bt_sau.setOnClickListener(this);
        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);
        header.attachTo(rcv_lyTHuyet);
    }

    @Override
    public void onClick(View view) {
        if (view==bt_truoc){
            if (dem!=1){
                dem--;
                adapterRecyclerViewLyThuyet
                        = new AdapterRecyclerViewLyThuyet(LyThuyetActivity.this,cauHoiDAO.getListCauHoi(dem));
                rcv_lyTHuyet.setAdapter(adapterRecyclerViewLyThuyet);
            }
        } else {
            if (dem!=18){
                dem++;
                adapterRecyclerViewLyThuyet
                        = new AdapterRecyclerViewLyThuyet(LyThuyetActivity.this,cauHoiDAO.getListCauHoi(dem));
                rcv_lyTHuyet.setAdapter(adapterRecyclerViewLyThuyet);
            }
        }
        toolbar_title.setText(dem+"/18");
    }
}
