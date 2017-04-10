package tea_manager.com.example.honza.tea_manager.Utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tea_manager.com.example.honza.tea_manager.Objects.Shop;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;

/**
 * Created by Honza on 24/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "TeaManager.db";
    private final Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TEAS = "CREATE TABLE " + Tea.TABLE + '('
                + Tea.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Tea.KEY_NAME + " TEXT, "
                + Tea.KEY_TYPE + " INTEGER, "
                + Tea.KEY_INFUSIONS + " INTEGER, "
                + Tea.KEY_IMAGE + " BLOB )";
        db.execSQL(CREATE_TABLE_TEAS);

        String CREATE_TABLE_SHOPS = "CREATE TABLE " + Shop.TABLE + '('
                + Shop.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Shop.KEY_NAME + " TEXT, "
                + Shop.KEY_OPEN_FROM_HOUR + " INTEGER, "
                + Shop.KEY_OPEN_FROM_MINUTE + " INTEGER, "
                + Shop.KEY_OPEN_TO_HOUR + " INTEGER, "
                + Shop.KEY_OPEN_TO_MINUTE + " INTEGER, "
                + Shop.KEY_LATITUDE + " REAL, "
                + Shop.KEY_LONGITUDE + " REAL )";
        db.execSQL(CREATE_TABLE_SHOPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tea.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Shop.TABLE);
        onCreate(db);
    }
}
