package nl.tudelft.oopp.demo.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "users")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;          // user id

    @Column(name = "date")
    private String date;

    @Column(name = "time_slot")
    private String timeSlot;

    public Reservation() {

    }

    public Reservation(User user, String date, String timeSlot) {
        this.user = user;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return "Reservation: " +
                "id=" + id +
                ", user=" + user +
                ", date='" + date + '\'' +
                ", timeSlot='" + timeSlot + '\'';
    }
}
