package tea_manager.com.example.honza.tea_manager.Objects;

import java.io.Serializable;

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

    public static final String[] ALL_COLUMNS =
            {KEY_ID, KEY_NAME, KEY_TYPE, KEY_INFUSIONS};

    public enum teaType{
        Black,
        Green,
        Other
    }
    private String name;
    private teaType type;
    private int infusions;
    private int ID;

    public Tea(){}

    public Tea(int ID, String name, teaType type, int infusions){
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.infusions = infusions;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
}
