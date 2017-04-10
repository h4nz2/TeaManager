package tea_manager.com.example.honza.tea_manager.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tea_manager.com.example.honza.tea_manager.Objects.Shop;

/**
 * Created by Honza on 24/03/2017.
 */

public class DBshopCRUD {
    private final DBHelper dbHelper;

    public DBshopCRUD(Context context){
        dbHelper = new DBHelper(context);
    }

    public void addShop(Shop shop){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Shop.KEY_NAME, shop.getName());
        values.put(Shop.KEY_OPEN_FROM_HOUR, shop.getOpeningHours().getFromHour());
        values.put(Shop.KEY_OPEN_FROM_MINUTE, shop.getOpeningHours().getFromMinute());
        values.put(Shop.KEY_OPEN_TO_HOUR, shop.getOpeningHours().getToHour());
        values.put(Shop.KEY_OPEN_TO_MINUTE, shop.getOpeningHours().getToMinute());
        values.put(Shop.KEY_LATITUDE, shop.getLatitude());
        values.put(Shop.KEY_LONGITUDE, shop.getLongitude());

        db.insert(Shop.TABLE, null, values);
        db.close();
    }

    public List<Shop> getAllShops(){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Shop.KEY_ID + ',' +
                Shop.KEY_NAME + ',' +
                Shop.KEY_OPEN_FROM_HOUR + ',' +
                Shop.KEY_OPEN_FROM_MINUTE + ',' +
                Shop.KEY_OPEN_TO_HOUR + ',' +
                Shop.KEY_OPEN_TO_MINUTE + ',' +
                Shop.KEY_LATITUDE + ',' +
                Shop.KEY_LONGITUDE +
                " FROM " + Shop.TABLE;
        List<Shop> shopList = new ArrayList<Shop>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Shop shop = new Shop(
                        cursor.getInt(cursor.getColumnIndex(Shop.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Shop.KEY_NAME)),
                        new Shop.OpeningHours(
                                cursor.getInt(cursor.getColumnIndex(Shop.KEY_OPEN_FROM_HOUR)),
                                cursor.getInt(cursor.getColumnIndex(Shop.KEY_OPEN_FROM_MINUTE)),
                                cursor.getInt(cursor.getColumnIndex(Shop.KEY_OPEN_TO_HOUR)),
                                cursor.getInt(cursor.getColumnIndex(Shop.KEY_OPEN_TO_MINUTE))
                        ),
                        cursor.getDouble(cursor.getColumnIndex(Shop.KEY_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(Shop.KEY_LONGITUDE))
                );
                shopList.add(shop);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return shopList;
    }

    public void updateShop(Shop shop){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Shop.KEY_NAME, shop.getName());
        values.put(Shop.KEY_OPEN_FROM_HOUR, shop.getOpeningHours().getFromHour());
        values.put(Shop.KEY_OPEN_FROM_MINUTE, shop.getOpeningHours().getFromMinute());
        values.put(Shop.KEY_OPEN_TO_HOUR, shop.getOpeningHours().getToHour());
        values.put(Shop.KEY_OPEN_TO_MINUTE, shop.getOpeningHours().getToMinute());
        values.put(Shop.KEY_LATITUDE, shop.getLatitude());
        values.put(Shop.KEY_LONGITUDE, shop.getLongitude());
        db.update(Shop.TABLE, values, Shop.KEY_ID + "= ?", new String[]{String.valueOf(shop.getID())});
        db.close();
    }

    public void deleteShop(Shop shop){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Shop.TABLE, Shop.KEY_ID + "= ?", new String[]{String.valueOf(shop.getID())});
        db.close();
    }
}
