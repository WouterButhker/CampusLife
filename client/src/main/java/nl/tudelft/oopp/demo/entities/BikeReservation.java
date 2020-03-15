package nl.tudelft.oopp.demo.entities;

public class BikeReservation {

    private Integer id;
    private Integer user;
    private Integer buildingCode;
    private String date;
    private String timeSlot;

    /**
     * Makes a new BikeReservation object.
     * @param id The number of the bike reservation
     * @param user The id of the user that made the bike reservation
     * @param buildingCode - The code of the building where the bike is rented from
     * @param timeSlot - The timeslot of the bike reservation
     */
    public BikeReservation(Integer id, Integer user,
                           Integer buildingCode, String date,
                           String timeSlot) {
        this.id = id;
        this.user = user;
        this.buildingCode = buildingCode;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
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
}
