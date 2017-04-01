package tea_manager.com.example.honza.tea_manager.Utility;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import tea_manager.com.example.honza.tea_manager.Objects.Tea;

/**
 * Created by Honza on 01/04/2017.
 */

public class TeaContentProvider extends ContentProvider {
    private SQLiteDatabase database;
    private DBHelper mDbHelper;

    private static final String AUTHORITY = "com.example.honza.tea_manager.teacontentprovider";
    private static final String BASE_PATH = "teas";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int TEAS = 1;
    private static final int TEAS_ID = 2;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, TEAS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TEAS_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Tea.TABLE);
        int uriType = uriMatcher.match(uri);

        switch (uriType) {
            case TEAS_ID:
                queryBuilder.appendWhere(TEAS_ID + "="
                        + uri.getLastPathSegment());
                break;
            case TEAS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(mDbHelper.getReadableDatabase(),projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = uriMatcher.match(uri);
        database = mDbHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case  TEAS:
                id = database.insert(Tea.TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int rowsDeleted = 0;
        database = mDbHelper.getWritableDatabase();

        switch (uriType) {
            case TEAS:
                rowsDeleted = database.delete(Tea.TABLE, selection, selectionArgs);
                break;
            case TEAS_ID:
                String id = uri.getLastPathSegment();

                rowsDeleted = database.delete(Tea.TABLE, Tea.KEY_ID + " = " + id, selectionArgs);
                break;
            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int count = 0;
        database = mDbHelper.getWritableDatabase();
        switch (uriType){
            case TEAS:
                count = database.update(Tea.TABLE, values, selection, selectionArgs);
                break;

            case TEAS_ID:
                count = database.update(Tea.TABLE, values, Tea.KEY_ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
