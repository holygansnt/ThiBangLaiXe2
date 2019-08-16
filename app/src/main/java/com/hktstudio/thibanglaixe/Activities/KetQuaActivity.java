package com.hktstudio.thibanglaixe.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.hktstudio.thibanglaixe.R;
import static com.hktstudio.thibanglaixe.Activities.ThiSatHachActivity.SIZE;
import static com.hktstudio.thibanglaixe.Activities.ThiSatHachActivity.soCauDung;


public class KetQuaActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv_diem;
    ImageView img_ketQua;
    Button btXemDapAn, btDeThiKhac;
    android.support.v7.widget.Toolbar toolbar;
    private InterstitialAd mInterstitialAd;
    int dem = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketquathi);
        setControl();
    }
    public void setControl(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tv_diem = findViewById(R.id.tv_diem);
        img_ketQua = findViewById(R.id.img_ketQua);
        btXemDapAn = findViewById(R.id.btXemDapAn);
        btDeThiKhac = findViewById(R.id.btDeThiKhac);
        btXemDapAn.setOnClickListener(this);
        btDeThiKhac.setOnClickListener(this);
        if (SIZE == 20){
            tv_diem.setText(soCauDung+"/20");
            if (soCauDung<16){
                img_ketQua.setImageDrawable(getDrawable(this,"truotchot"));
            } else {
                img_ketQua.setImageDrawable(getDrawable(this,"dochot"));
            }
        } else if (SIZE == 30){
            tv_diem.setText(soCauDung+"/30");
            if (soCauDung<26){
                img_ketQua.setImageDrawable(getDrawable(this,"truotchot"));
            } else {
                img_ketQua.setImageDrawable(getDrawable(this,"dochot"));
            }
        }

    }

    public Drawable getDrawable(Context context,String name){
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        Drawable drawable = resources.getDrawable(resourceId);
        return drawable;
    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        if (view==btXemDapAn){
            Intent intent_xemDapAn = new Intent(KetQuaActivity.this,XemLaiDapAnActivity.class);
            startActivity(intent_xemDapAn);
            MainActivity.dem7++;
            if (MainActivity.dem7==3||MainActivity.checkTime ){
                MainActivity.dem7 = 0;
                MainActivity.checkTime = false;
                mInterstitialAd.show();
            }
        } else if (view==btDeThiKhac){
            Intent intent_thiSatHach = new Intent(KetQuaActivity.this,ThiSatHachActivity.class);
            if (SIZE == 20){
                intent_thiSatHach.putExtra("tenBaiThi",'a');
            } else{
                intent_thiSatHach.putExtra("tenBaiThi",'b');
            }
            startActivity(intent_thiSatHach);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(KetQuaActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
