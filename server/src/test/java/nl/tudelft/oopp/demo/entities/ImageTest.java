package nl.tudelft.oopp.demo.entities;

import nl.tudelft.oopp.demo.entities.image.BuildingImage;
import nl.tudelft.oopp.demo.entities.image.Image;
import nl.tudelft.oopp.demo.entities.image.RoomImage;
import nl.tudelft.oopp.demo.entities.image.UserImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ImageTest {

    Image image;
    String imageId;
    String fileName;
    String fileType;
    byte[] data;

    Integer code;
    String name;
    String location;
    String openingHours;
    Integer bikes;
    Building building;
    BuildingImage buildingImage;

    String roomCode;
    String roomName;
    Integer capacity;
    boolean hasWhiteboard;
    boolean hasTV;
    Integer rights;
    Room room;

    @BeforeEach
    void before() {
        code = 42069;
        name = "The Arena";
        location = "CityStreetRoute";
        openingHours = "08:00-22:00";
        bikes = 5;
        building = new Building(code, name, location, openingHours, bikes);

        imageId = "abc";
        fileName = "abc";
        fileType = "jpg";

        data = new byte[]{};
        image = new BuildingImage(fileName, fileType, data, building);
    }

    @Test
    public void contructorTest() {
        assertNotNull(image);
    }

    @Test
    public void imageIdTest() {
        image.setImageId(imageId);
        assertEquals(imageId, image.getImageId());
    }

    @Test
    public void fileNameTest() {
        image.setFileName("abcd");
        assertEquals("abcd", image.getFileName());
    }

    @Test
    public void fileTypeTest() {
        image.setFileType("png");
        assertEquals("png", image.getFileType());
    }

    @Test
    public void dataTest() {
        byte b = 1;
        byte[] data2 = new byte[]{b};
        image.setData(data2);
        assertEquals(data2, image.getData());
    }

    @Test
    public void buildingTest() {
        buildingImage = new BuildingImage(fileName, fileType, data, building);
        Building building2 = new Building(code, name, location, openingHours, bikes);
        buildingImage.setBuilding(building2);
        assertEquals(building2, buildingImage.getBuilding());
    }

    @Test
    public void buildingToStringTest() {
        buildingImage = new BuildingImage(fileName, fileType, data, building);
        assertNull(buildingImage.toString());
    }

    @Test
    public void roomTest() {
        roomCode = "69";
        roomName = "TestRoom";
        capacity = 69;
        hasWhiteboard = true;
        hasTV = true;
        rights = 2;
        room = new Room(roomCode, roomName, capacity, hasWhiteboard, hasTV, rights, building);
        RoomImage roomImage = new RoomImage(fileName, fileType, data, room);
        Room room2 = new Room(roomCode, roomName, capacity, hasWhiteboard, hasTV, rights, building);
        roomImage.setRoom(room2);
        assertEquals(room2, roomImage.getRoom());
    }

    @Test
    public void roomToStringTest() {
        RoomImage roomImage = new RoomImage(fileName, fileType, data, room);
        assertNull(roomImage.toString());
    }

    @Test
    public void userTest() {
        User user = new User();
        UserImage userImage = new UserImage(fileName, fileType, data, user);
        User user2 = new User("abc", "123");
        userImage.setUser(user2);
        assertEquals(user2, userImage.getUser());
    }

    @Test
    public void userToStringTest() {
        User user = new User();
        UserImage userImage = new UserImage(fileName, fileType, data, user);
        assertNull(userImage.toString());
    }

}