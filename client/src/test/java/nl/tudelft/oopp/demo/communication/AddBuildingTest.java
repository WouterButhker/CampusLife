package nl.tudelft.oopp.demo.communication;

import org.junit.jupiter.api.Test;


public class AddBuildingTest {

    @Test
    public void testAddBuildingToDatabaseForDeletion() {
        Integer buildingCode = 1;
        String name = "Delete Me";
        String location = "Please";
        String openingHours = "08:00-22:00";
        ServerCommunication.addBuildingToDatabase(buildingCode, name, location, openingHours);
    }

}
