package nl.tudelft.oopp.demo.entities;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Image {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String imageId;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public Image() {

    }

    /**
     * Make a new Image with the given parameters.
     * @param fileName the name of the Image file as saved in the computer
     * @param fileType the type of the file (ex: png)
     * @param data the actual Image
     */
    public Image(String fileName,
                 String fileType,
                 byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String id) {
        this.imageId = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


}
