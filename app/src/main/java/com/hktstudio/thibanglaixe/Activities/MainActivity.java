package com.hktstudio.thibanglaixe.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hktstudio.thibanglaixe.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable{
    LinearLayout bt_thiSatHach, bt_bienBao, bt_lyThuyet, bt_meoGhiNho, bt_meoThucHanh, bt_lichSuBaiThi;
    Button bt_a121, bt_b121, bt_cancel1, bt_a122, bt_b122, bt_cancel2;
    Dialog dialogThiSatHach, dialogMeoThucHanh;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    int dem1 = 0,dem2=0, dem3 = 0, dem4 = 0, dem5=0,dem6=0;
    //dem act thi sat hach, bien bao, ly thuyet, meo ghi nho, meo thuc hanh, lichsubaithi to load ad
    public static int dem7=0,dem8=0,dem9=0,dem10=0;
    //dem act ketquathi, xemlaidapan, kinhnghiema, kinhnghiemb to load ad;
    Thread t;
    int time = 0;
    public static boolean checkTime = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        t = new Thread(this);
        t.start();
    }

    public void setControl(){
        mAdView =(AdView)findViewById(R.id.adView);
        //Load ads
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Nếu quảng cáo đã tắt tiến hành load quảng cáo
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadInterstitialAd();
            }
        });
        //Load sẵn quảng cáo khi ứng dụng mở
        loadInterstitialAd();
        bt_thiSatHach = findViewById(R.id.bt_thiSatHach);
        bt_bienBao = findViewById(R.id.bt_bienBao);
        bt_lyThuyet = findViewById(R.id.bt_lyThuyet);
        bt_meoGhiNho = findViewById(R.id.bt_meoGhiNho);
        bt_meoThucHanh = findViewById(R.id.bt_meoThucHanh);
        bt_lichSuBaiThi = findViewById(R.id.bt_lichSuBaiThi);
        bt_thiSatHach.setOnClickListener(this);
        bt_bienBao.setOnClickListener(this);
        bt_lyThuyet.setOnClickListener(this);
        bt_meoGhiNho.setOnClickListener(this);
        bt_meoThucHanh.setOnClickListener(this);
        bt_lichSuBaiThi.setOnClickListener(this);
    }

    //Load InterstitialAd
    private void loadInterstitialAd() {
        if (mInterstitialAd != null) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            mInterstitialAd.loadAd(adRequest);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mInterstitialAd.isLoaded())loadInterstitialAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        if (!mInterstitialAd.isLoaded())loadInterstitialAd();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

    public void setDialogThiSatHach(){
        dialogThiSatHach = new Dialog(this);
        dialogThiSatHach.setContentView(R.layout.custom_dialog_thisathach);
        dialogThiSatHach.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogThiSatHach.setCanceledOnTouchOutside(true);
        dialogThiSatHach.show();
        bt_a121 = dialogThiSatHach.findViewById(R.id.bt_a121);
        bt_b121 = dialogThiSatHach.findViewById(R.id.bt_b121);
        bt_cancel1 = dialogThiSatHach.findViewById(R.id.bt_cancel1);
        bt_a121.setOnClickListener(this);
        bt_b121.setOnClickListener(this);
        bt_cancel1.setOnClickListener(this);
    }

    public void setDialogMeoThucHanh(){
        dialogMeoThucHanh = new Dialog(this);
        dialogMeoThucHanh.setContentView(R.layout.custom_dialog_meothuchanh);
        dialogMeoThucHanh.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogMeoThucHanh.setCanceledOnTouchOutside(true);
        dialogMeoThucHanh.show();
        bt_a122 = dialogMeoThucHanh.findViewById(R.id.bt_a122);
        bt_b122 = dialogMeoThucHanh.findViewById(R.id.bt_b122);
        bt_cancel2 = dialogMeoThucHanh.findViewById(R.id.bt_cancel2);
        bt_a122.setOnClickListener(this);
        bt_b122.setOnClickListener(this);
        bt_cancel2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_thiSatHach:
                setDialogThiSatHach();
                break;
            case R.id.bt_bienBao:
                Intent intentBienBao = new Intent(this,BienBaoActivity.class);
                startActivity(intentBienBao);
                dem2++;
                if (dem2==3 || checkTime){
                    dem2 = 0;
                    checkTime = false;
                    mInterstitialAd.show();
                }
                break;
            case R.id.bt_lyThuyet:
                Intent intentLyThuyet = new Intent(this,LyThuyetActivity.class);
                startActivity(intentLyThuyet);
                dem3++;
                if (dem3==3 || checkTime){
                    dem3 = 0;
                    checkTime = false;
                    mInterstitialAd.show();
                }
                break;
            case R.id.bt_meoGhiNho:
                Intent intentMeoGhiNho = new Intent(this,MeoGhiNhoActivity.class);
                startActivity(intentMeoGhiNho);
                dem4++;
                if (dem4==3 || checkTime){
                    dem4 = 0;
                    checkTime = false;
                    mInterstitialAd.show();
                }
                break;
            case R.id.bt_meoThucHanh:
                setDialogMeoThucHanh();
                break;
            case R.id.bt_lichSuBaiThi:
                Intent intent_lichsu = new Intent(MainActivity.this,LichSuBaiThiActivity.class);
                startActivity(intent_lichsu);
                dem6++;
                if (dem6==3 || checkTime){
                    dem6 = 0;
                    checkTime = false;
                    mInterstitialAd.show();
                }
                break;
            case R.id.bt_a121:
                Intent intentThiSatHachA = new Intent(MainActivity.this,ThiSatHachActivity.class);
                intentThiSatHachA.putExtra("tenBaiThi",'a');
                startActivity(intentThiSatHachA);
                dialogThiSatHach.dismiss();
                dem1++;
                if (dem1==3 || checkTime){
                    dem1 = 0;
                    checkTime = false;
                    mInterstitialAd.show();
                }
                break;
            case R.id.bt_b121:
                Intent intentThiSatHachB = new Intent(MainActivity.this,ThiSatHachActivity.class);
                intentThiSatHachB.putExtra("tenBaiThi",'b');
                startActivity(intentThiSatHachB);
                dialogThiSatHach.dismiss();
                dem1++;
                if (dem1==3 || checkTime){
                    dem1 = 0;
                    checkTime = false;
                    mInterstitialAd.show();
                }
                break;
            case R.id.bt_cancel1:
                dialogThiSatHach.dismiss();
                break;
            case R.id.bt_a122:
                Intent intentThucHanhA = new Intent(this,MeoThucHanhAActivity.class);
                startActivity(intentThucHanhA);
                dialogMeoThucHanh.dismiss();
                dem5++;
                if (dem5==3 || checkTime){
                    dem5 = 0;
                    checkTime = false;
                    mInterstitialAd.show();
                }
                break;
            case R.id.bt_b122:
                Intent intentThucHanhB = new Intent(this,MeoThucHanhBActivity.class);
                startActivity(intentThucHanhB);
                dialogMeoThucHanh.dismiss();
                if (dem5==3 || checkTime){
                    dem5 = 0;
                    checkTime = false;
                    mInterstitialAd.show();
                }
                break;
            case R.id.bt_cancel2:
                dialogMeoThucHanh.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            time+=1;
            if (time== 180) {
                checkTime = true;
                time = 0;
                Log.d("Checktime","OK");
            }
            try {
                t.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
