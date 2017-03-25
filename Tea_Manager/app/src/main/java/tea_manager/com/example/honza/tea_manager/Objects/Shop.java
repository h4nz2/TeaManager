package tea_manager.com.example.honza.tea_manager.Objects;


/**
 * Created by Honza on 23/03/2017.
 */

public class Shop {
    private int ID;
    private String name;
    private OpeningHours openingHours;

    public Shop(){};
    public Shop(int ID, String name, OpeningHours openingHours){
        this.ID = ID;
        this.name = name;
        this.openingHours = openingHours;
    }

    //this class stores opening and closing time; hours and minutes
    public class OpeningHours{
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
