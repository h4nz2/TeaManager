package tea_manager.com.example.honza.tea_manager.Objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.BitmapConvert;

/**
 * Created by Honza on 23/03/2017.
 */

public class Tea implements Serializable{
    // Labels table name
    public static final String TABLE = "teas";
    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_INFUSIONS = "infusions";
    public static final String KEY_IMAGE = "image";

    public static final String[] ALL_COLUMNS =
            {KEY_ID, KEY_NAME, KEY_TYPE, KEY_INFUSIONS, KEY_IMAGE};

    public enum teaType{
        Black,
        Green,
        Other
    }

    private String name;
    private teaType type;
    private int infusions;
    private int ID;
    private byte[] image;

    public Tea(){}

    public Tea(int ID, String name, teaType type, int infusions, byte[] image){
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.infusions = infusions;
        this.image = image;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public teaType getType() {
        return type;
    }

    public void setType(teaType type) {
        this.type = type;
    }

    public int getInfusions() {
        return infusions;
    }

    public void setInfusions(int infusions) {
        this.infusions = infusions;
    }

    public byte[] getImageByte() {
        return image;
    }

    public Bitmap getImageBitmap() {
        return BitmapConvert.byteToBitmap(image);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImage(Bitmap image) {
        this.image = BitmapConvert.bitmapToByte(image);
    }

}
