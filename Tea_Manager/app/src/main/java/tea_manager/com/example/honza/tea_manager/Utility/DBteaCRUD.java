package tea_manager.com.example.honza.tea_manager.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tea_manager.com.example.honza.tea_manager.Objects.Tea;

/**
 * Created by Honza on 24/03/2017.
 */

public class DBteaCRUD {
    private final DBHelper dbHelper;

    public DBteaCRUD(Context context){
        dbHelper = new DBHelper(context);
    }

    public void addTea(Tea tea){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Tea.KEY_NAME, tea.getName());
        values.put(Tea.KEY_TYPE, tea.getType().ordinal());
        values.put(Tea.KEY_INFUSIONS, tea.getInfusions());

        db.insert(Tea.TABLE, null, values);
        db.close();
    }

    public List<Tea> getAllTeas(){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tea.KEY_ID + ',' +
                Tea.KEY_NAME + ',' +
                Tea.KEY_TYPE + ',' +
                Tea.KEY_INFUSIONS +
                " FROM " + Tea.TABLE;
        List<Tea> teaList = new ArrayList<Tea>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Tea tea = new Tea(
                        cursor.getInt(cursor.getColumnIndex(Tea.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Tea.KEY_NAME)),
                        /*get enum ordinal from the DB and turn it back to enum
                        "COUGH" "COUGH" it was hard to write, it should be hard to read...*/
                        Tea.teaType.values()[cursor.getInt(cursor.getColumnIndex(Tea.KEY_TYPE))],
                        cursor.getInt(cursor.getColumnIndex(Tea.KEY_INFUSIONS))
                );
                teaList.add(tea);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return teaList;
    }

    public void updateTea(Tea tea){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Tea.KEY_NAME, tea.getName());
        values.put(Tea.KEY_TYPE, tea.getType().ordinal());
        values.put(Tea.KEY_INFUSIONS, tea.getInfusions());
        db.update(Tea.TABLE, values, Tea.KEY_ID + "= ?", new String[]{String.valueOf(tea.getID())});
        db.close();
    }

    public void deleteTea(Tea tea){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Tea.TABLE, Tea.KEY_ID + "= ?", new String[]{String.valueOf(tea.getID())});
        db.close();
    }

    public List<Tea> getTeaGroup(Tea.teaType teaType){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tea.KEY_ID + ',' +
                Tea.KEY_NAME + ',' +
                Tea.KEY_TYPE + ',' +
                Tea.KEY_INFUSIONS +
                " FROM " + Tea.TABLE +
                " WHERE " + Tea.KEY_TYPE +
                " = " + teaType.ordinal();
        List<Tea> teaList = new ArrayList<Tea>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Tea tea = new Tea(
                        cursor.getInt(cursor.getColumnIndex(Tea.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Tea.KEY_NAME)),
                        /*get enum ordinal from the DB and turn it back to enum
                        "COUGH" "COUGH" it was hard to write, it should be hard to read...*/
                        Tea.teaType.values()[cursor.getInt(cursor.getColumnIndex(Tea.KEY_TYPE))],
                        cursor.getInt(cursor.getColumnIndex(Tea.KEY_INFUSIONS))
                );
                teaList.add(tea);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return teaList;
    }
}
