package tea_manager.com.example.honza.tea_manager.Utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tea_manager.com.example.honza.tea_manager.Objects.Tea;

/**
 * Created by Honza on 24/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TeaManager.db";
    private final Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        String CREATE_TABLE_GAME = "CREATE TABLE " + Tea.TABLE + '('
                + Tea.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Tea.KEY_NAME + " TEXT, "
                + Tea.KEY_TYPE + " INTEGER, "
                + Tea.KEY_INFUSIONS + " INTEGER )";
        db.execSQL(CREATE_TABLE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Tea.TABLE);
        // Create tables again
        onCreate(db);
    }
}
