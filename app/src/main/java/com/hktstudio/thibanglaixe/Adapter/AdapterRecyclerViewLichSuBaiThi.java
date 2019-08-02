package com.hktstudio.thibanglaixe.Adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.hktstudio.thibanglaixe.Activities.MainActivity;
import com.hktstudio.thibanglaixe.Activities.XemLaiDapAnActivity;
import com.hktstudio.thibanglaixe.Object.CauHoi;
import com.hktstudio.thibanglaixe.Object.DeThi;
import com.hktstudio.thibanglaixe.R;
import java.util.ArrayList;
import java.util.List;
import static com.hktstudio.thibanglaixe.Activities.ThiSatHachActivity.SIZE;

/**
 * Created by Hiep on 9/13/2017.
 */


public class AdapterRecyclerViewLichSuBaiThi extends RecyclerView.Adapter<AdapterRecyclerViewLichSuBaiThi.RecyclerViewHolder> {
    public static List<DeThi> listDeThi = new ArrayList<DeThi>();
    Context context;
    private LayoutInflater mInflater;
    public static int pos;
    private InterstitialAd mInterstitialAd;
    int dem = 0;
    public AdapterRecyclerViewLichSuBaiThi(Context context,List<DeThi> listDeThi) {
        this.listDeThi = listDeThi;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        //Nếu quảng cáo đã tắt tiến hành load quảng cáo
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadInterstitialAd();
            }
        });
        //Load sẵn quảng cáo khi ứng dụng mở
        loadInterstitialAd();
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
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.layout_recyclerview_lichsubaithi,parent,false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.bt_deThi.setText("Đề "+(position+1));
        holder.bt_deThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                Intent intent_xemDapAn = new Intent(context, XemLaiDapAnActivity.class);
                SIZE = listDeThi.get(position).getListCauHoi().size();
                intent_xemDapAn.putExtra("from",'l');
                context.startActivity(intent_xemDapAn);
                MainActivity.dem8++;
                if (MainActivity.dem8==3||MainActivity.checkTime){
                    MainActivity.dem8 = 0;
                    MainActivity.checkTime = false;
                    mInterstitialAd.show();
                }

            }
        });
    }

    public static List<CauHoi> getListCauHoi(){
        return listDeThi.get(pos).getListCauHoi();
    }

    @Override
    public int getItemCount() {
        return listDeThi.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        Button bt_deThi;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            bt_deThi = itemView.findViewById(R.id.bt_deThi);
            // Calculate ActionBar height
            TypedValue tv = new TypedValue();
            float actionBarHeight = 0;
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,context.getResources().getDisplayMetrics());
            }
            float mWidth = (getWidthScreen(context)-convertDpToPixel(100,context))/4;
            float mHeight = (getHeightScreen(context)-actionBarHeight-getStatusBarHeight()-convertDpToPixel(120,context))/5;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)mWidth,(int)mHeight);
            bt_deThi.setLayoutParams(layoutParams);
        }
    }

    public static int getWidthScreen(Context context){
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int mWidthScreen = display.getWidth();
        return mWidthScreen;
    }

    public static int getHeightScreen(Context context){
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int mHeightScreen = display.getHeight();
        return mHeightScreen;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
