package nl.tudelft.oopp.demo.testfolder;

import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import org.junit.jupiter.api.Test;


public class AddBuildingTest {

    @Test
    public void testAddBuildingToDatabaseForDeletion() {
        Integer buildingCode = 1;
        String name = "Delete Me";
        String location = "Please";
        String openingHours = "08:00-22:00";
        Integer bikes = 42;
        RestaurantCommunication.addBuildingToDatabase(buildingCode,
                name, location, openingHours, bikes);
    }

}
