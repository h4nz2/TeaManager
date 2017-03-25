package tea_manager.com.example.honza.tea_manager.Objects;


/**
 * Created by Honza on 23/03/2017.
 */

public class Shop {
    // Labels table name
    public static final String TABLE = "shops";
    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_OPEN_FROM_HOUR = "openFromHour";
    public static final String KEY_OPEN_FROM_MINUTE = "openFromMinute";
    public static final String KEY_OPEN_TO_HOUR = "openToHour";
    public static final String KEY_OPEN_TO_MINUTE = "openToMinute";

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private String name;
    private OpeningHours openingHours;

    public Shop(){};
    public Shop(int ID, String name, OpeningHours openingHours){
        this.ID = ID;
        this.name = name;
        this.openingHours = openingHours;
    }





    /**
     * This class stores opening and closing time; hours and minutes.
     */
    public static class OpeningHours{
        private int fromHour;
        private int fromMinute;
        private int toHour;
        private int toMinute;

        public OpeningHours(){}

        public OpeningHours(int fromHour, int fromMinute, int toHour, int toMinute){
            this.fromHour = fromHour;
            this.fromMinute = fromMinute;
            this.toHour = toHour;
            this.toMinute = toMinute;
        }

        /**
         * Returns string in format hh:mm - hh:mm.
         * */
        @Override
        public String toString(){
            return new String(
                    Integer.toString(getFromHour()) + ":" +
                    Integer.toString(getFromMinute()) + " - " +
                    Integer.toString(getToHour()) + ":" +
                    Integer.toString(getToMinute())
            );
        }

        public int getFromHour() {
            return fromHour;
        }

        public void setFromHour(int fromHour) {
            this.fromHour = fromHour;
        }

        public int getFromMinute() {
            return fromMinute;
        }

        public void setFromMinute(int fromMinute) {
            this.fromMinute = fromMinute;
        }

        public int getToHour() {
            return toHour;
        }

        public void setToHour(int toHour) {
            this.toHour = toHour;
        }

        public int getToMinute() {
            return toMinute;
        }

        public void setToMinute(int toMinute) {
            this.toMinute = toMinute;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }
}
