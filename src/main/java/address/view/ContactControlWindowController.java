package address.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import address.model.Contact;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ContactControlWindowController {

    @FXML
    private TextField contactNameField;
    @FXML
    private TextField contactNumberField;

    private Stage dialogStage;
    private Contact contact;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setContact(Contact contact){
        this.contact = contact;

        contactNameField.setText(contact.getContactName());
        contactNumberField.setText(contact.getContactNumber());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            contact.setContactName(contactNameField.getText());
            contact.setContactNumber(contactNumberField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (contactNameField.getText() == null || contactNameField.getText().length() ==0) {
            errorMessage += "No valid contact name!\n";
        }
        if (contactNumberField.getText() == null || contactNumberField.getText().length() ==0) {
            errorMessage += "No valid contact number!\n";
        } else {
            try {
                Integer.parseInt(contactNumberField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid contact number!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
