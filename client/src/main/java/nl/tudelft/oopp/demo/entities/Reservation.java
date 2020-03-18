package nl.tudelft.oopp.demo.entities;




public abstract class Reservation {

    private int id;


    private UserDtO user;

    private String date;

    private String timeSlot;

    public Reservation() {

    }

    public Reservation(UserDtO user, String date, String timeSlot) {
        this.user = user;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public UserDtO getUser() {
        return user;
    }

    public void setUser(UserDtO user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public abstract String toString();
}
