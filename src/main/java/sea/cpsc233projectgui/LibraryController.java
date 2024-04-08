package sea.cpsc233projectgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LibraryController {
    @FXML
    private File loadedFile;

    private Alert a = new Alert(Alert.AlertType.NONE);

    private Data data;
    public void setData(Data data) {
        this.data = data;
    }

    @FXML
    protected void save(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
//        fileChooser.setInitialFileName(".txt");

        Node node = menuItem.getParentPopup().getOwnerNode();
        File file = fileChooser.showSaveDialog(node.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("Saved!"); // Write your content here
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("File saved successfully!");
                a.show();
            } catch (IOException e) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Error saving file: " + e.getMessage());
                a.show();
            }
        }
    }

    @FXML
    protected void load(ActionEvent event){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        MenuItem menuItem = (MenuItem) event.getSource();
        loadedFile = fileChooser.showOpenDialog(menuItem.getParentPopup().getOwnerWindow());

        if (!(loadedFile == null)){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("File Load Successful" + loadedFile.getName());
            a.show();
        } else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("File Load Failed");
            a.show();
        }
    }

    @FXML
    void quit(ActionEvent event) {

    }

    @FXML
    public void addMember(){

    }

    @FXML
    public void removeMember(){

    }

    @FXML
    public void viewAllMembers(){

    }

    @FXML
    public void searchMember(){

    }

    @FXML
    public void payFines(){

    }

    @FXML
    public void averageDaysOverdue(){

    }

    @FXML
    public void mostActiveChild(){

    }

    @FXML
    public void addBook(){

    }

    @FXML
    public void removeBook(){

    }

    @FXML
    public void checkoutBook(){

    }

    @FXML
    public void returnBook(){

    }

    @FXML
    public void viewBook(){

    }

    @FXML
    public void searchBook(){

    }

    @FXML
    public void mostPopularBook(){

    }

    @FXML
    private void about(){
        String aboutMessage = "This is a library management system\n" +
                "Developers:\n Anna Littkemann, Emily Ng Kwong Sang, Srijita Marick\n" +
                "Emails:\nlittkemann@ucalgary.ca, emily.ngkwongsang@ucalgary.ca," +
                "  srijita.marick@ucalgary.ca";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("SEA Library management system");
        alert.setContentText(aboutMessage);
        alert.showAndWait();
    }
}
