package address;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

import address.view.ContactControlWindowController;
import address.view.ContactOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import address.model.Contact;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainApp extends Application {

    private Stage primaryStage;

    private ObservableList<Contact> contactsData = FXCollections.observableArrayList();
    private HashSet<String> contactImportSet = new HashSet();

    public MainApp() {
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ContactApp");

        showContactsOverview();
    }

    public void showContactsOverview() {
        try {
            initData();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/address/view/ContactOverview.fxml"));
            AnchorPane contactOverview = loader.load();

            ContactOverviewController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(contactOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showContactControlWindow(Contact contact) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/address/view/ContactControlWindow.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            ContactControlWindowController contactControlWindowController = loader.getController();
            contactControlWindowController.setDialogStage(dialogStage);
            contactControlWindowController.setContact(contact);

            dialogStage.showAndWait();

            return contactControlWindowController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Contact> getContactsData() {
        return contactsData;
    }

    public HashSet<String> getContactImportSet(){
        return contactImportSet;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void initData() {
        contactsData.add(new Contact("Yegor", "380505353555"));
        contactsData.add(new Contact("Vika", "380999999999"));
    }

    private File chooseExcelFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File importFile = fileChooser.showOpenDialog(primaryStage);
        if (importFile != null) {
            return importFile;
        }
        return null;
    }

    public void importContactsNumbersFromExcel() {
        File file = chooseExcelFile();
        if (file != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
                XSSFSheet sheet = wb.getSheetAt(0);
                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row != null) {
                        Cell cell = row.getCell(1);
                        if (cell != null) {
                            String cellValue = cell.getStringCellValue();
                            contactImportSet.add(cellValue);
                        }
                    }
                }
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

}

