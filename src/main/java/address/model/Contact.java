package address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact {

    private Long id;

    private StringProperty contactName;

    private StringProperty contactNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName.get();
    }

    public StringProperty contactNameProperty() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    public String getContactNumber() {
        return contactNumber.get();
    }

    public StringProperty contactNumberProperty() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }

    public Contact() {
        this(null, null);
    }

    public Contact(String contactName, String contactNumber) {
        this.contactName = new SimpleStringProperty(contactName);
        this.contactNumber = new SimpleStringProperty(contactNumber);
    }

    @Override
    public String toString() {
        return "\nContact Name: " + contactName + "\n" +
                "Contact Number: " + contactNumber + "\n";
    }

}
