package tea_manager.com.example.honza.tea_manager.Utility;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Base64;

import java.sql.Blob;

import tea_manager.com.example.honza.tea_manager.Objects.Tea;

/**
 * Created by Honza on 01/04/2017.
 */

public class TeaCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TeaCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Tea getTea() {
        int ID = getInt(getColumnIndex(Tea.KEY_ID));
        String name = getString(getColumnIndex(Tea.KEY_NAME));
        int type = getInt(getColumnIndex(Tea.KEY_TYPE));
        int infusions = getInt(getColumnIndex(Tea.KEY_INFUSIONS));
        byte[] imageBlob = getBlob(getColumnIndex(Tea.KEY_IMAGE));

        return new Tea(
                ID,
                name,
                Tea.teaType.values()[type],
                infusions,
                imageBlob
        );
    }
}
