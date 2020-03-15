package nl.tudelft.oopp.demo.entities;

public class BikeReservation {

    private Integer id;
    private Integer user;
    private Integer pickUpBuildingCode;
    private Integer dropOffBuildingCode;
    private String date;
    private String timeSlot;

    /**
     * Makes a new BikeReservation object.
     * @param id The number of the bike reservation
     * @param user The id of the user that made the bike reservation
     * @param pickUpBuildingCode The id of the building where the bike is picked up
     * @param dropOffBuildingCode The id of the building where the bike is dropped off
     * @param timeSlot - The timeslot of the bike reservation
     */
    public BikeReservation(Integer id, Integer user,
                           Integer pickUpBuildingCode, Integer dropOffBuildingCode,
                           String date, String timeSlot) {
        this.id = id;
        this.user = user;
        this.pickUpBuildingCode = pickUpBuildingCode;
        this.dropOffBuildingCode = dropOffBuildingCode;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return "BikeReservation{" + "id=" + id + ", user=" + user
                + ", pickUpBuildingCode=" + pickUpBuildingCode
                + ", dropOffBuildingCode=" + dropOffBuildingCode
                + ", date='" + date + '\'' + ", timeSlot='"
                + timeSlot + '\'' + '}';
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

    public Integer getPickUpBuildingCode() {
        return pickUpBuildingCode;
    }

    public void setPickUpBuildingCode(Integer pickUpBuildingCode) {
        this.pickUpBuildingCode = pickUpBuildingCode;
    }

    public Integer getDropOffBuildingCode() {
        return dropOffBuildingCode;
    }

    public void setDropOffBuildingCode(Integer dropOffBuildingCode) {
        this.dropOffBuildingCode = dropOffBuildingCode;
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
