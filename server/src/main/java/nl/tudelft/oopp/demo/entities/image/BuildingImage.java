package nl.tudelft.oopp.demo.entities.image;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "building_image")
public class BuildingImage extends Image {

    @OneToOne
    @JoinColumn(name = "building")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Building building;

    public BuildingImage() {

    }

    public BuildingImage(String fileName,
                         String fileType,
                         byte[] data,
                         Building building) {
        super(fileName, fileType, data);
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return null;
    }
}
