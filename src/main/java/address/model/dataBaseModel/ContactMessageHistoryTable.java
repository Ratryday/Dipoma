package address.model.dataBaseModel;

public class ContactMessageHistoryTable {
    private Long id;
    private String contactNameTable;
    private Long contactNumberTable;
    private String contactMessageTable;

    public ContactMessageHistoryTable() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactNameTable() {
        return contactNameTable;
    }

    public void setContactNameTable(String contactNameTable) {
        this.contactNameTable = contactNameTable;
    }

    public Long getContactNumberTable() {
        return contactNumberTable;
    }

    public void setContactNumberTable(Long contactNumberTable) {
        this.contactNumberTable = contactNumberTable;
    }

    public String getContactMessageTable() {
        return contactMessageTable;
    }

    public void setContactMessageTable(String contactMessageTable) {
        this.contactMessageTable = contactMessageTable;
    }
}
