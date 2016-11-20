package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thejan on 11/20/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="140536K.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists account (ACCNO TEXT PRIMARY KEY, BANK TEXT, HOLDER TEXT, BALANCE DOUBLE) ");
//        db.execSQL("create table if not exists transactionLog(ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, ACCNO TEXT, TYPE TEXT, AMOUNT DOUBLE) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS transactionLog(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TRANSDATE DATE," +
                "ACCNO TEXT," +
                "TYPE TEXT," +
                "AMOUNT DOUBLE" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
