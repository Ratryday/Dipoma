package address.view;

import address.hibernate.DataBaseConnector;
import address.sendMessage.SendSMS;
import address.model.dataBaseModel.ContactTable;
import address.sendMessage.SendToTelegram;
import javafx.scene.control.*;
import address.model.Contact;
import javafx.fxml.FXML;
import address.MainApp;

import java.util.HashSet;
import java.util.Optional;

public class ContactOverviewController {

    @FXML
    private TableColumn<Contact, String> contactsNumberTableColumn;
    @FXML
    private TableColumn<Contact, String> contactsNameTableColumn;
    @FXML
    private TableView<Contact> contactTableView;
    @FXML
    private Label contactNumberLabel;
    @FXML
    private TextField messageTextField;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private TextArea contactNumberTextArea;
    @FXML
    private RadioButton isSMSRadioButtonOk;

    private MainApp mainApp;


    public ContactOverviewController() {
    }

    @FXML
    private void initialize() {
        // Сюда передавать информацию из БД

        contactsNumberTableColumn.setCellValueFactory(cellData -> cellData.getValue().contactNameProperty());
        contactsNameTableColumn.setCellValueFactory(cellData -> cellData.getValue().contactNumberProperty());

        contactTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showContactName(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        contactTableView.setItems(mainApp.getContactsData());
    }

    private void showContactName(Contact contact) {
        if (contact != null) {
            contactNumberLabel.setText(contact.getContactName());
            contactNumberTextArea.setText("+" + contact.getContactNumber());
        } else {
            contactNumberLabel.setText("");
        }
    }

    @FXML
    void handleDeletePerson() {
        int selectedIndex = contactTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure about that?");
            alert.setContentText("Don't do it");
            alert.setContentText("Ok, let's kill da hol");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(null) == ButtonType.OK) contactTableView.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }

    }

    @FXML
    private void handleNewContact() {
        Contact newContact = new Contact();
        ContactTable newContactTable = new ContactTable();
        DataBaseConnector dataBaseConnector = new DataBaseConnector();
        boolean okClicked = mainApp.showContactControlWindow(newContact);
        if (okClicked) {
            try {
                dataBaseConnector.addContactsToDataBase(newContactTable);
                mainApp.getContactsData().add(newContact);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleEditContact() {
        Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            boolean okClicked = mainApp.showContactControlWindow(selectedContact);
            if (okClicked) {
                showContactName(selectedContact);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Contact Selected");
            alert.setContentText("Please select a contact in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleSendMessage() {
        Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();

        if (!contactNumberTextArea.getText().equals("")) {
            SendSMS sendSMS = new SendSMS();
            SendToTelegram sendToTelegram = new SendToTelegram();
            if (!isSMSRadioButtonOk.isSelected()) {
                 sendToTelegram.botExample(messageTextField.getText());
            } else {
                if (selectedContact != null) {
                    System.out.println("SMS Ok");
                     // sendSMS.sendOneMessage(selectedContact.getContactNumber(), messageTextField.getText());
                } else {
                     // sendSMS.sendManyMessage(mainApp.getContactImportSet(), messageTextField.getText());
                }
                showSentContactMessage();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Contact Selected");
            alert.setContentText("Please select a contact in the table.");

            alert.showAndWait();
        }
    }

    private void showSentContactMessage() {
        if (messageTextArea.getText().equals("") && !messageTextField.getText().equals("")) {
            messageTextArea.setText(messageTextField.getText());
        } else {
            if (!messageTextField.getText().equals("")) {
                messageTextArea.setText(messageTextArea.getText() + "\n" + messageTextField.getText());
            }
        }
        messageTextField.setText("");
    }

    private static String convertHashSetInText(HashSet<String> contacts) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : contacts) {
            stringBuilder.append(string);
            stringBuilder.append("; ");
        }
        return stringBuilder.toString().trim();
    }

    @FXML
    private void handleImportContactsNumbersFromExcel() {
        mainApp.importContactsNumbersFromExcel();
        contactNumberTextArea.setText(convertHashSetInText(mainApp.getContactImportSet()));
        contactNumberLabel.setText("Group");
    }
}
