package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by thejan on 11/20/16.
 */

public class PersistentTransactionDAO implements TransactionDAO {
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db=PersistentExpenseManager.getDb().getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("TRANSDATE",date.toString());
        contentValues.put("ACCNO",accountNo);
        contentValues.put("TYPE",expenseType.toString());
        contentValues.put("AMOUNT",amount);
        long result=db.insert("transactionLog", null,contentValues);
    }

    @Override
    public List<Transaction> getAllTransactionLogs(){
        SQLiteDatabase db= PersistentExpenseManager.getDb().getWritableDatabase();
        Cursor res=db.rawQuery("select * from transactionLog",null);
        List<Transaction> transaction_list= new ArrayList<Transaction>();
        while (res.moveToNext()){
            Transaction transaction=null;
            try {
                transaction = new Transaction(new Date(res.getLong(res.getColumnIndex("TRANSDATE"))), res.getString(2),ExpenseType.valueOf(res.getString(3)), res.getDouble(4));
            } catch (Exception e) {
                e.printStackTrace();
            }
            transaction_list.add(transaction);
        }
        return transaction_list;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        SQLiteDatabase db= PersistentExpenseManager.getDb().getWritableDatabase();
        Cursor res= db.rawQuery("select * from transactionLog limit " + limit,null);
        List<Transaction> transaction_list= new ArrayList<Transaction>();
        while (res.moveToNext()){
            Transaction transaction=null;
            try {
                transaction = new Transaction(new Date(res.getLong(res.getColumnIndex("TRANSDATE"))), res.getString(2),ExpenseType.valueOf(res.getString(3)), res.getDouble(4));
            } catch (Exception e) {
                e.printStackTrace();
            }
            transaction_list.add(transaction);
        }
        return transaction_list;
    }
}
