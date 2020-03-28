package nl.tudelft.oopp.demo.entities;

public class BikeReservation extends Reservation {

    private Building pickUpBuilding;
    private Building dropOffBuilding;

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
        super(id, user, date, timeSlot);
        this.pickUpBuilding = pickUpBuilding;
        this.dropOffBuilding = dropOffBuilding;

    }

    @Override
    public String toString() {
        return "BikeReservation{" + "id=" + id + ", user=" + userId
                + ", pickUpBuildingCode=" + pickUpBuilding.getCode()
                + ", dropOffBuildingCode=" + dropOffBuilding.getCode()
                + ", date='" + date + '\'' + ", timeSlot='"
                + timeSlot + '\'' + '}';
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
                && userId.equals(that.userId)
                && pickUpBuilding.equals(that.pickUpBuilding)
                && dropOffBuilding.equals(that.dropOffBuilding)
                && date.equals(that.date)
                && timeSlot.equals(that.timeSlot);
    }
}
