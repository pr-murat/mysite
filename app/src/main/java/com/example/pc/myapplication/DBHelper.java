package com.example.pc.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC on 09.11.2017.
 */




public class DBHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DB_NAME = "teacher";

    public static final String TABLE_NAME = "subjects";
    public static final String TABLE_WP = "workprogram";

    public static final String KEY_ID = "id";
    public static final String KEY_DAY = "kday";
    public static final String KEY_TIME = "ktime";
    public static final String KEY_NAME = "kname";
    public static final String KEY_GROUP = "kgroup";
    public static final String KEY_AUDIT = "kauditoria";
    public static final String KEY_COURSE = "kkurs";

    public static final String KEY_WP_ID = "idk";
    public static final String KEY_WP_TEMA = "temak";
    public static final String KEY_WP_RES = "resourcek";






    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("  create table "+DBHelper.TABLE_NAME+"(" +
                               DBHelper.KEY_ID+" integer primary key autoincrement, " +
                               DBHelper.KEY_DAY+" integer, " +
                               DBHelper.KEY_TIME+" integer, " +
                               DBHelper.KEY_NAME+" text, " +
                               DBHelper.KEY_GROUP+" text, " +
                               DBHelper.KEY_AUDIT+" integer, " +
                               DBHelper.KEY_COURSE+" integer " +
                               ");");
        for (int i = 1; i < 7; i++) {//days
            for (int j = 1; j < 12; j++) {//times
                insertIntoValues(sqLiteDatabase, i, j, "okoshka", "nulling", 0, 0);
            }
        }


        sqLiteDatabase.execSQL("create table "+TABLE_WP+" (" +
                KEY_WP_ID+" integer primary key autoincrement, " +
                KEY_WP_TEMA+" text, " +
                KEY_WP_RES+" text " +
                ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static void insertIntoValues(SQLiteDatabase sqLiteDatabase, int day, int time, String name, String group, int auditoria, int kurs) {
        sqLiteDatabase.execSQL("insert into " + DBHelper.TABLE_NAME + " (" + DBHelper.KEY_DAY + ", " + DBHelper.KEY_TIME + ", " + DBHelper.KEY_NAME + ", " + DBHelper.KEY_GROUP + ", " + DBHelper.KEY_AUDIT + ", " + DBHelper.KEY_COURSE + ") values(" + day + "," + time + ",'" + name + "','" + group + "'," + auditoria + "," + kurs + ")");
    }

}
