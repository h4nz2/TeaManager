package tea_manager.com.example.honza.tea_manager.Objects;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Honza on 23/03/2017.
 */

public class Shop implements Serializable {
    // Labels table name
    public static final String TABLE = "shops";
    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_OPEN_FROM_HOUR = "openFromHour";
    public static final String KEY_OPEN_FROM_MINUTE = "openFromMinute";
    public static final String KEY_OPEN_TO_HOUR = "openToHour";
    public static final String KEY_OPEN_TO_MINUTE = "openToMinute";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private String name;
    private OpeningHours openingHours;
    private double latitude;
    private double longitude;



    public Shop(){
        /*sets time to 0, timePicker will crash without this,
        because of NullPointerEception when getting opening time */
        openingHours = new OpeningHours();
    };
    public Shop(int ID, String name, OpeningHours openingHours, double latitude, double longitude){
        this.ID = ID;
        this.name = name;
        this.openingHours = openingHours;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * This class stores opening and closing time; hours and minutes.
     * It also contains methods for transforming opening hours to string.
     */
    public static class OpeningHours implements Serializable{
        private int fromHour;
        private int fromMinute;
        private int toHour;
        private int toMinute;

        public OpeningHours(){
            fromHour = 0;
            fromMinute = 0;
            fromHour = 0;
            fromMinute = 0;
        }

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
                    String.format("%02d", fromHour) + ":" +
                    String.format("%02d", fromMinute) + " - " +
                    String.format("%02d", toHour) + ":" +
                    String.format("%02d", toMinute)
            );
        }

        /**
         * Returns opening time in format hh:mm
         * */
        public String openFromToString(){
            return new String(
                    String.format("%02d", fromHour) + ":" +
                    String.format("%02d", fromMinute)
            );
        }

        /**
         * Returns closing time in format hh:mm
         * */
        public String openToToString(){
            return new String(
                    String.format("%02d", toHour) + ":" +
                    String.format("%02d", toMinute)
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
