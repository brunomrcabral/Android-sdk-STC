package com.example.hugo.stc_android.Model.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hugo
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "STC.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void onCreate(SQLiteDatabase db) {}

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insertRecord(String table, Map<String, String> record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        StringBuilder recordBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : record.entrySet()) {
            values.put(entry.getKey(), entry.getValue());
            recordBuilder.append(entry.getKey()).append(" - ").append(entry.getValue()).append("; ");
        }
        db.insert(table, null, values);
        db.close();
        Log.i("REGISTO INSERIDO:", recordBuilder.toString());
    }

    public void updateRecord(String table, Map<String, String> values, String restrictionExpression, String[] restrictionValues){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesToUpdate = new ContentValues();

        for (Map.Entry<String, String> entry : values.entrySet()) {
            valuesToUpdate.put(entry.getKey(), entry.getValue());
        }

        db.update(table, valuesToUpdate, restrictionExpression, restrictionValues);
        db.close();
    }

    public void deleteRecord(String table, String restrictionExpression, String[] restrictionValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, restrictionExpression, restrictionValues);
        db.close();
    }

    public List<String> getDistinctColumnRecordsFromTable(String table, String[] columns, String restrictionExpression,
                                                          String[] restrictionValues, String order) {
        List<String> resultList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, table, columns, restrictionExpression, restrictionValues, null, null, columns[0] + " " + order, null);
        if (cursor.moveToFirst()) {
            do {
                resultList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return resultList;
    }

    public Cursor getOrderedRecordsByFilter(String table, String[] columns, String restrictionExpression,
                                            String[] restrictionValues, String order) {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(table, columns, restrictionExpression, restrictionValues, null, null, order);
    }

    public String getSingleColumnValueFomTable(String table, String[] columns, String restrictionExpression, String[] restrictionValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        String result = null;
        Cursor cursor = db.query(table, columns, restrictionExpression, restrictionValues, null, null, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean checkTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = 'table' AND name = ?", new String[] {tableName});
        if(!cursor.moveToFirst()) {
            count = 0;
        } else {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count > 0;
    }

    public int getCountRecordsFromTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return count;
    }

    public int getCountRecordsFromTableByFilter(String tableName, String filterExpression, String[] filterValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, tableName, filterExpression, filterValues);
        db.close();
        return count;
    }

    public boolean executeSqlStatement(String sqlStatement) {
        boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(sqlStatement);
            Log.i("EXECUTOU SQL:" , sqlStatement);
        } catch(Exception e) {
            Log.e("Erro a executar SQL", e.getMessage());
            success = false;
        } finally {
            db.close();
        }
        return success;
    }

    public boolean executeBatchInserts(List<String> listStatements) {
        boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            for(String sqlStatement : listStatements) {
                db.execSQL(sqlStatement);
                Log.i("EXECUTOU INSERT:", sqlStatement);
            }
            db.setTransactionSuccessful();
        } catch(Exception e) {
            Log.e("Erro a executar insert", e.getMessage());
            success = false;
        } finally {
            db.endTransaction();
            db.close();
        }
        return success;
    }
}
