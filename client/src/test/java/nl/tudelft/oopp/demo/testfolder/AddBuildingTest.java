package nl.tudelft.oopp.demo.testfolder;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AddBuildingTest {

    @Test
    public void testAddBuildingToDatabaseForDeletion() {
        Integer buildingCode = 1;
        String name = "Delete Me";
        String location = "Please";
        String openingHours = "08:00-22:00, 08:00-22:00, 08:00-22:00, 08:00-22:00,"
                + " 08:00-22:00, 08:00-22:00, 08:00-22:00";
        Integer bikes = 42;
        Building building = new Building(buildingCode, name, location, openingHours, bikes);
        BuildingCommunication.saveBuilding(building);
    }

}
