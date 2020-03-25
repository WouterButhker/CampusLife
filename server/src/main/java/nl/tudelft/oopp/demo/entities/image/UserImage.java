package nl.tudelft.oopp.demo.entities.image;

import javax.persistence.*;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.Reservation;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "user_image")
public class UserImage extends Image {

    @OneToOne
    @JoinColumn(name = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public UserImage() {

    }

    public UserImage(String fileName,
                     String fileType,
                     byte[] data,
                     User user) {
        super(fileName, fileType, data);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return null;
    }
}
