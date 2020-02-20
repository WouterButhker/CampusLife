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
        for (int i = 0; i < buildingsCodeAndName.length; i++)
            System.out.println(buildingsCodeAndName[i]);
        assertNotNull(buildingsCodeAndName);
    }
}
