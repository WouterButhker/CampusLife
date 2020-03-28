package nl.tudelft.oopp.demo.entities;

public class BikeReservation {

    private Integer id;
    private Integer user;
    private Building pickUpBuilding;
    private Building dropOffBuilding;
    private String date;
    private String timeSlot;

    /**
     * Makes a new BikeReservation object.
     * @param id The number of the bike reservation
     * @param user The id of the user that made the bike reservation
     * @param pickUpBuilding The building where the bike is picked up
     * @param dropOffBuilding The building where the bike is dropped off
     * @param date The date of the bike reservation
     * @param timeSlot - The timeslot of the bike reservation
     */
    public BikeReservation(Integer id, Integer user,
                           Building pickUpBuilding, Building dropOffBuilding,
                           String date, String timeSlot) {
        this.id = id;
        this.user = user;
        this.pickUpBuilding = pickUpBuilding;
        this.dropOffBuilding = dropOffBuilding;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return "BikeReservation{" + "id=" + id + ", user=" + user
                + ", pickUpBuildingCode=" + pickUpBuilding.getCode()
                + ", dropOffBuildingCode=" + dropOffBuilding.getCode()
                + ", date='" + date + '\'' + ", timeSlot='"
                + timeSlot + '\'' + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Building getPickUpBuilding() {
        return pickUpBuilding;
    }

    public void setPickUpBuilding(Building pickUpBuilding) {
        this.pickUpBuilding = pickUpBuilding;
    }

    public Building getDropOffBuilding() {
        return dropOffBuilding;
    }

    public void setDropOffBuilding(Building dropOffBuilding) {
        this.dropOffBuilding = dropOffBuilding;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BikeReservation)) {
            return false;
        }
        BikeReservation that = (BikeReservation) o;
        return id.equals(that.id)
                && user.equals(that.user)
                && pickUpBuilding.equals(that.pickUpBuilding)
                && dropOffBuilding.equals(that.dropOffBuilding)
                && date.equals(that.date)
                && timeSlot.equals(that.timeSlot);
    }
}
