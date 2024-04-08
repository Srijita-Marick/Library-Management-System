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

    @FXML
    public void initialize() {

    }

    @FXML
    protected void save(ActionEvent event) {
        //not functional yet

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
//        fileChooser.setInitialFileName(".txt");

        Node node = (Node) event.getSource();
        File file = fileChooser.showSaveDialog(node.getScene().getWindow());
        System.out.println(file);

        if (file != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath()))) {
                bw.write("Saved!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void load(ActionEvent event){
        // not functional yet

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File");

        Node node = (Node) event.getSource();
        loadedFile = fileChooser.showOpenDialog(node.getScene().getWindow());

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
        System.exit(0);
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
