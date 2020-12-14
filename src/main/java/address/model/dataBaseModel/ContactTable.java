package address.model.dataBaseModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class ContactTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public ContactTable() {
        this(null, null);
    }

    public ContactTable(String contactName, String contactNumber) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "\nContact Name: " + contactName + "\n" +
                "Contact Number: " + contactNumber + "\n";
    }

}
