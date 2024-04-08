package sea.cpsc233projectgui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;

public class LibraryController {
    @FXML
    private File loadedFile;

    @FXML
    private VBox vboxUserInput;

    @FXML
    private Menu menuBook;

    @FXML
    private MenuItem menuitemBookSearch;
    @FXML
    private MenuItem menuitemAddBook;

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

        Node node = menuItem.getParentPopup().getOwnerNode();
        File file = fileChooser.showSaveDialog(node.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("Saved!");
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

        if (loadedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(loadedFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                // Show alert
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("File Load Successful: " + loadedFile.getName());
                a.show();
            } catch (IOException e) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Error loading file: " + e.getMessage());
                a.show();
            }
        } else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("File Load Failed");
            a.show();
        }
    }


    @FXML
    void quit(ActionEvent event) {
        // Display confirmation dialog before quitting
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Exit");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Are you sure you want to exit?");
        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                // Close the application
                Platform.exit();
            }
        });
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
    public void addBook(ActionEvent event){
        Label lblTitle = new Label("Title:");
        TextField txtTitle = new TextField();
        Label lblAuthor = new Label("Author:");
        TextField txtAuthor = new TextField();
        MenuButton menuGenre = new MenuButton("Genre:");
        MenuItem fantasy = new MenuItem("Fantasy");
        fantasy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuGenre.setText("Fantasy");
            }
        });
        menuGenre.getItems().add(fantasy);
        RadioButton physical = new RadioButton("Physical");
        RadioButton audio = new RadioButton("Audiobook");

        Label lblNarrator = new Label("Narrator:");
        lblNarrator.setVisible(false);
        TextField txtNarrator = new TextField();
        txtNarrator.setVisible(false);

        Button btnAddBook = new Button("Add Book");

        vboxUserInput.getChildren().addAll(lblTitle,txtTitle,lblAuthor,txtAuthor,menuGenre,physical,audio,lblNarrator,txtNarrator,btnAddBook);

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
