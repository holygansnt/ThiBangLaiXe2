package com.hktstudio.thibanglaixe.Databases;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Hiep on 11/10/2017.
 */

public class BienBaoDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "BIENBAO.sqlite";
    private static final int DATABASE_VERSION = 1;
    public BienBaoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
