package nl.tudelft.oopp.demo.entities.reservation;

import java.util.Objects;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;

public class BikeReservation extends Reservation {

    private Building pickUpBuilding;
    private Building dropOffBuilding;


    /**
     * Makes a new BikeReservation object.
     * @param user The user that reserved
     * @param pickUpBuilding the building from where the bike is picked up
     * @param dropOffBuilding the building where the bike is dropped off
     * @param date The date of the reservation
     * @param timeSlot The timeslot of the reservation
     */
    public BikeReservation(User user,
                           Building pickUpBuilding,
                           Building dropOffBuilding,
                           String date,
                           String timeSlot)  {
        super(user, date, timeSlot);
        this.pickUpBuilding = pickUpBuilding;
        this.dropOffBuilding = dropOffBuilding;

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
        if (!super.equals(o)) {
            return false;
        }
        BikeReservation that = (BikeReservation) o;
        return Objects.equals(pickUpBuilding, that.pickUpBuilding)
                && Objects.equals(dropOffBuilding, that.dropOffBuilding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pickUpBuilding, dropOffBuilding);
    }

    @Override
    public String toString() {
        return "bike reservation{" + super.toString()
                + ", pickup building: " + this.pickUpBuilding
                + ", dropoff building: " + this.dropOffBuilding
                + "}";
    }

    @Override
    public String toDisplayString() {
        return "Bike reservation | Pickup location: " + pickUpBuilding.getName()
                + " | Dropoff location: " + dropOffBuilding.getName()
                + " | Timeslot: " + getTimeSlot();
    }

}

