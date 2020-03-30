package nl.tudelft.oopp.demo.entities.image;

import javax.persistence.*;
import nl.tudelft.oopp.demo.entities.Room;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "room_image")
public class RoomImage extends Image {

    @ManyToOne
    @JoinColumn(name = "room")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    public RoomImage() {

    }

    public RoomImage(String fileName,
                     String fileType,
                     byte[] data,
                     Room room) {
        super(fileName, fileType, data);
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return null;
    }
}
