package nl.tudelft.oopp.demo.communication;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;


public class ServerCommunicationTest {

    @Test
    public void testRandomQuote() {
        assertNotNull(ServerCommunication.getQuote());
    }

    @Test
    public void testGetBuildingsCodeAndName() {
        String[] buildingsCodeAndName = ServerCommunication.getBuildingsCodeAndName();
        if (buildingsCodeAndName != null) {
            for (int i = 0; i < buildingsCodeAndName.length; i++)
                System.out.println(buildingsCodeAndName[i]);
        }
        else System.out.println("NULL");
    }

    @Test
    public void testAddBuildingToDatabase() {
        Integer buildingCode = 20;
        String name = "Aula";
        String location = "Mekelweg 5";
        String openingHours = "08:00-22:00";
        ServerCommunication.addBuildingToDatabase(buildingCode, name, location, openingHours);
    }
}
