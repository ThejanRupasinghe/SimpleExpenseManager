package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by thejan on 11/20/16.
 */

public class PersistentAccountDAO implements AccountDAO {
    @Override
    public List<String> getAccountNumbersList() {
        SQLiteDatabase db=PersistentExpenseManager.getDb().getWritableDatabase();
        Cursor res=db.rawQuery("select ACCNO from account",null);
        List<String> accno_list=new ArrayList<String>();
        while (res.moveToNext()){
            accno_list.add(res.getString(0));
        }
        return accno_list;
    }

    @Override
    public List<Account> getAccountsList() {
        SQLiteDatabase db=PersistentExpenseManager.getDb().getWritableDatabase();
        Cursor res=db.rawQuery("select * from account",null);
        List<Account> acc_list=new ArrayList<Account>();
        while (res.moveToNext()){
            Account account=new Account(res.getString(0),res.getString(1),res.getString(2),res.getDouble(3));
            acc_list.add(account);
        }
        return acc_list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db=PersistentExpenseManager.getDb().getWritableDatabase();
        Cursor res=db.rawQuery("select * from account where ACCNO="+accountNo,null);
        Account account=new Account(accountNo,res.getString(1),res.getString(2),res.getDouble(3));
        return account;
    }

    @Override
    public void addAccount(Account account) throws SQLiteConstraintException {
        SQLiteDatabase db=PersistentExpenseManager.getDb().getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ACCNO",account.getAccountNo());
        contentValues.put("BANK",account.getBankName());
        contentValues.put("HOLDER",account.getAccountHolderName());
        contentValues.put("BALANCE",account.getBalance());
        long result=db.insert("account", null,contentValues);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db=PersistentExpenseManager.getDb().getWritableDatabase();
        db.delete("account","ACCNO = ?",new String[] {accountNo});
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase db=PersistentExpenseManager.getDb().getWritableDatabase();
        String sql = "update account set BALANCE = BALANCE + ? where ACCNO = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        if(expenseType == ExpenseType.EXPENSE){
            statement.bindDouble(1,-amount);
        }else{
            statement.bindDouble(1,amount);
        }
        statement.bindString(2,accountNo);
        statement.executeUpdateDelete();
    }
}
