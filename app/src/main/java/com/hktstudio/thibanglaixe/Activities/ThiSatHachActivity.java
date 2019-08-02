package com.hktstudio.thibanglaixe.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.hktstudio.thibanglaixe.Adapter.AdapterRecyclerViewThiSatHach;
import com.hktstudio.thibanglaixe.DAO.CauHoiDAO;
import com.hktstudio.thibanglaixe.Object.CauHoi;
import com.hktstudio.thibanglaixe.Object.DeThi;
import com.hktstudio.thibanglaixe.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by Hiep on 12/4/2017.
 */

public class ThiSatHachActivity extends AppCompatActivity implements View.OnClickListener, Runnable{
    int dem = 1;
    Button bt_truoc, bt_sau,bt_huyBo, bt_dongY,bt_cancel;
    CauHoiDAO cauHoiDAO;
    public static List<CauHoi> list;
    List<CauHoi> listCauHoi = new ArrayList<CauHoi>();
    RecyclerView rcv_thiSatHach;
    AdapterRecyclerViewThiSatHach adapterRecyclerViewThiSatHach;
    Dialog dialogFinish, dialogHetGio;
    public static int soCauDung = 0;
    StringBuilder [] dapAnLuaChon = new StringBuilder[35];
    ArrayList a;
    public static int sttCauHoi [] = new int[100];
    public static boolean checkDungSai[] = new boolean[100];
    public static int SIZE = 0;
    List<DeThi> listDeThi = new ArrayList<>();
    Toolbar toolbar;
    boolean ok = false;
    int time  = 1200; //thoi gian lam bai
    TextView tv_time;
    Thread t;
    int FLAG=0;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        if (savedInstanceState!=null) savedInstanceState.clear();
        setContentView(R.layout.activity_thisathach);
        if (getIntent().getCharExtra("tenBaiThi",' ')=='a') SIZE = 20;
        else SIZE = 30;
        ranDomCauHoi();
        setControl();
        listDeThi = docFile("lichsu.txt");
        if (listDeThi.size()<20){
            listDeThi.add(new DeThi(list));
        } else {
            for (int i=19;i>=1;i--) listDeThi.set(i,listDeThi.get(i-1));
            listDeThi.set(0,new DeThi(list));
        }
        ghiFile(listDeThi);
        t = new Thread(this);
        FLAG = 1;
        t.start();
    }

    public List<DeThi> docFile(String fileName){
        List<DeThi> listDeThi = new ArrayList<>();
        try {
            File file = getFileStreamPath(fileName);
            if (file==null||!file.exists()){
                file = new File(fileName);
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listDeThi = (List<DeThi>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listDeThi;
    }

    public void ghiFile(List<DeThi> listDeThi){
        try {
            File file = getFileStreamPath("lichsu.txt");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listDeThi);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (!mInterstitialAd.isLoaded())loadInterstitialAd();
    }

    public void setControl() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
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
        tv_time = findViewById(R.id.tv_time);
        setTime();
        bt_truoc = findViewById(R.id.bt_truoc);
        bt_sau = findViewById(R.id.bt_sau);
        dialogFinish = new Dialog(this);
        dialogFinish.setContentView(R.layout.custom_dialog_finish);
        dialogFinish.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFinish.setCanceledOnTouchOutside(false);
        dialogHetGio = new Dialog(this);
        dialogHetGio.setContentView(R.layout.custom_dialog_hetgio);
        dialogHetGio.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogHetGio.setCanceledOnTouchOutside(false);
        bt_huyBo = dialogFinish.findViewById(R.id.bt_huyBo);
        bt_dongY = dialogFinish.findViewById(R.id.bt_dongY);
        bt_cancel = dialogHetGio.findViewById(R.id.bt_cancel);
        bt_huyBo.setOnClickListener(this);
        bt_dongY.setOnClickListener(this);
        bt_truoc.setOnClickListener(this);
        bt_sau.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        for (int i=0;i<SIZE;i++) dapAnLuaChon[i] = new StringBuilder();
        rcv_thiSatHach = findViewById(R.id.rcv_thiSatHach);
        rcv_thiSatHach.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterRecyclerViewThiSatHach = new AdapterRecyclerViewThiSatHach(this,list.get(0),0);
        rcv_thiSatHach.setAdapter(adapterRecyclerViewThiSatHach);
        OverScrollDecoratorHelper.setUpOverScroll(rcv_thiSatHach, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        RecyclerViewHeader header = findViewById(R.id.header);
        header.attachTo(rcv_thiSatHach);
    }

    public void ranDomCauHoi() {
        list = new ArrayList<CauHoi>();
        Random rd = new Random();
        cauHoiDAO = new CauHoiDAO(this);
        listCauHoi = cauHoiDAO.getListCauHoi();
        a = new ArrayList();
        int x;
        for (int i = 0; i < SIZE; i++) {
            do {
                int j = 450 - SIZE + i;
                x = rd.nextInt(j);
            }
            while (a.contains(x));
            a.add(x);
            list.add(listCauHoi.get((int) a.get(i)));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_truoc:
                dapAnLuaChon[dem-1] = adapterRecyclerViewThiSatHach.getDA();
                if (dem != 1) {
                    dem--;
                    resetCauHoi();
                }
                break;
            case R.id.bt_sau:
                dapAnLuaChon[dem-1] = adapterRecyclerViewThiSatHach.getDA();
                if (dem != SIZE) {
                    dem++;
                    resetCauHoi();
                } else {
                    ok = true;
                    dialogFinish.show();
                }
                break;
            case R.id.bt_huyBo:
                dapAnLuaChon[dem-1] = adapterRecyclerViewThiSatHach.getDA();
                dialogFinish.dismiss();
                break;
            case R.id.bt_dongY: case  R.id.bt_cancel:
                FLAG = 0;
                if (ok){
                    dapAnLuaChon[dem-1] = adapterRecyclerViewThiSatHach.getDA();
                    soCauDung = 0;
                    for (int i=0;i<SIZE;i++){
                        Log.d("Dap an dung cau "+(i+1),list.get(i).getDapAn());
                        Log.d("Dap an lua chon cau "+(i+1),dapAnLuaChon[i].toString());
                        if (list.get(i).getDapAn().compareTo(dapAnLuaChon[i].toString())==0){
                            soCauDung++;
                            checkDungSai[i] = true;
                        } else {
                            checkDungSai[i] = false;
                        }
                    }
                    Intent intent_ketQua = new Intent(ThiSatHachActivity.this,KetQuaActivity.class);
                    intent_ketQua.putExtra("soCauDung",soCauDung);
                    MainActivity.dem7++;
                    if (MainActivity.dem7==5){
                        MainActivity.dem7 = 0;
                        mInterstitialAd.show();
                    }
                    for (int i=0;i<a.size();i++) sttCauHoi[i]= (int) a.get(i);
                    startActivity(intent_ketQua);
                    t.interrupt();
                } else {
                    Intent intent = new Intent(ThiSatHachActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
                dialogFinish.dismiss();
                break;
        }
    }

    public void resetCauHoi() {
        adapterRecyclerViewThiSatHach = new AdapterRecyclerViewThiSatHach(this,list.get(dem-1),dem-1);
        rcv_thiSatHach.setAdapter(adapterRecyclerViewThiSatHach);
        Log.d("Dap an dung:",list.get(dem-1).getDapAn());
    }

    @Override
    public void onBackPressed() {
        ok = false;
        dialogFinish.show();
    }

    public void setTime(){
        if (time/60<10 && time%60<10){
            tv_time.setText("0"+(time/60)+":0"+(time%60));
        } else if (time/60<10 && time%60>=10){
            tv_time.setText("0"+(time/60)+":"+(time%60));
        } else if (time/60>=10 && time%60<10){
            tv_time.setText((time/60)+":0"+(time%60));
        } else {
            tv_time.setText((time/60)+":"+(time%60));
        }
    }

    @Override
    public void run() {
        while (time > 0 && FLAG==1) {
            time--;
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setTime();
                    if (time==0){
                        ok = true;
                        dialogHetGio.show();
                    }
                }
            });
            try {
                t.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

