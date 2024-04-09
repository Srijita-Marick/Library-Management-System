package sea.cpsc233projectgui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import sea.cpsc233projectgui.objects.Books;
import sea.cpsc233projectgui.objects.Member;

import java.io.*;
import java.util.ArrayList;

public class LibraryController {
    @FXML
    private File loadedFile;

    @FXML
    private VBox vboxUserInput;

    @FXML
    private Menu menuBook;

    @FXML
    private Label lblDisplay;

    @FXML
    private MenuItem menuitemBookSearch;
    @FXML
    private MenuItem menuitemAddBook;

    private Alert a = new Alert(Alert.AlertType.NONE);

    private Data data;
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * Allows the user to save a file using a file chooser dialog
     * When button pressed , it displays a success or error message depending on the outcome
     * @param event The ActionEvent that triggered the save action
     */
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

    /**
     * Allows the user to load a file using a file chooser dialog
     * When button pressed , it displays a success or error message depending on the outcome
     * @param event The ActionEvent that triggered the load action
     */
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

    /**
     * Allows the user to exit the application
     * When button pressed , it displays a confirmation message to exit
     * @param event The ActionEvent that triggered the quit action
     */
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

    /**
     * Edits the vboxUserInput to have the correct fields to add a member to library
     * When button pressed, it adds the member and displays it on the display panel
     */
    @FXML
    public void addMember(){
        vboxUserInput.getChildren().clear();
        Label lblID = new Label("ID:");
        TextField txtID = new TextField();
        Label lblName = new Label("Name:");
        TextField txtName = new TextField();
        MenuButton menuMemberType = new MenuButton("Member Type:");

        String[] memType = new String[]{"ADULT","CHILD"};

        for (String type:memType){ //creates genres and adds them to the genre menu
            MenuItem menuItem = new MenuItem(type);
            menuMemberType.getItems().add(menuItem);
            setGenreAction(menuMemberType,menuItem);
        }

        Button btnAddMem = new Button("Add Member");

        // creates book when "Add Book" is clicked
        btnAddMem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer id = Integer.parseInt(txtID.getText());
                String name = txtName.getText();
                boolean error = false;
                if (!menuMemberType.getText().equals("Member Type:") && id!=null && name!=null){
                    String type = menuMemberType.getText();
                    if (type.equals("ADULT")){
                        error = !data.storeNewAdultMember(id,name);
                    } else if (type.equals("CHILD")){
                        error = !data.storeNewChildMember(id,name);
                    }
                    else {
                        error = true;
                    }
                }
                else {
                    error = true;
                }
                if (error){
                    lblDisplay.setText("ERROR: could not add member");
                }
            }
        });

        //add all these items to the display box...
        vboxUserInput.getChildren().addAll(lblID,txtID,lblName,txtName,menuMemberType);

    }

    /**
     * Edits the vboxUserInput to have the correct fields to remove a member to library
     * When button pressed, it removes & deletes the member and displays it on the display panel
     */
    @FXML
    public void removeMember(){
        vboxUserInput.getChildren().clear();
        Label lblID = new Label("ID:");
        TextField txtID = new TextField();
        Label lblName = new Label("Name:");
        TextField txtName = new TextField();

        Button btnRemoveMember = new Button("Remove Member");

        // deletes member when "Remove Member" button is clicked

        btnRemoveMember.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean error = false;
                try {
                    int id = Integer.parseInt(txtID.getText());
                    String name = txtName.getText();
                    error = data.removeMember(id,name);
                } catch (NumberFormatException e){
                    error = true;
                }

                if (error){
                    lblDisplay.setText("ERROR: could not remove member");
                }
            }
        });
        vboxUserInput.getChildren().addAll(lblID,txtID,lblName,txtName,btnRemoveMember);
    }

    /**
     * Displays all members on lblDisplay
     */
    @FXML
    public void viewAllMembers(){
        vboxUserInput.getChildren().clear();

        String display ="All Members:"; // text to be displayed
        ArrayList<Member> members = data.getAllMembers();
        for (Member member: members){
            display += member.toString();
        }
        lblDisplay.setText(display);
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

    /**
     * Edits the vboxUserInput to have the correct fields to add a book to library
     * When button pressed, it adds the book and displays it on the display panel
     */
    @FXML
    public void addBook(ActionEvent event){

        vboxUserInput.getChildren().clear();
        Label lblTitle = new Label("Title:");
        TextField txtTitle = new TextField();
        Label lblAuthor = new Label("Author:");
        TextField txtAuthor = new TextField();
        MenuButton menuGenre = new MenuButton("Genre:");

        String[] genres = new String[]{"Fantasy","General Fiction","Historical Fiction","Horror","Literary","Mystery",
                "Non-fiction","Poetry","Romance","Science Fiction","Thriller"};

        for (String genre:genres){ //creates genres and adds them to the genre menu
            MenuItem menuItem = new MenuItem(genre);
            menuGenre.getItems().add(menuItem);
            setGenreAction(menuGenre,menuItem);
        }

        RadioButton radioPhysical = new RadioButton("Physical");
        radioPhysical.setSelected(true);
        RadioButton radioAudio = new RadioButton("Audiobook");

        //narrator fields should only be visible if the user is making an audiobook
        Label lblNarrator = new Label("Narrator:");
        lblNarrator.setVisible(false);
        TextField txtNarrator = new TextField();
        txtNarrator.setVisible(false);

        radioPhysical.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                radioAudio.setSelected(false);
                lblNarrator.setVisible(false);
                txtNarrator.setVisible(false);
            }
        });

        radioAudio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                radioPhysical.setSelected(false);
                lblNarrator.setVisible(true);
                txtNarrator.setVisible(true);
            }
        });

        Button btnAddBook = new Button("Add Book");

        // creates book when "Add Book" is clicked
        btnAddBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String title = txtTitle.getText();
                String author = txtAuthor.getText();
                boolean error = false;
                if (!menuGenre.getText().equals("Genre:")&&title!=null&&author!=null){
                    String genre = menuGenre.getText();
                    if (radioPhysical.isSelected()){
                        error = !data.storeNewPhysicalBook(title,author,genre,"Available");
                    } else if (radioAudio.isSelected()&&txtNarrator.getText()!=null){
                        String narrator = txtNarrator.getText();
                        error = !data.storeNewAudioBook(title,author,narrator,genre,"Available");
                    }
                    else {
                        error = true;
                    }
                }
                else {
                    error = true;
                }
                if (error){
                    lblDisplay.setText("ERROR: invalid book information");
                } else {
                    displayBook(title,author);
                }
            }
        });

        //add all these items to the display box...
        vboxUserInput.getChildren().addAll(lblTitle,txtTitle,lblAuthor,txtAuthor,menuGenre,radioPhysical,radioAudio,lblNarrator,txtNarrator,btnAddBook);

    }

    /**
     * When a genre is selected, changes the menuButton to reflect the change
     * @param menuGenre is the MenuButton that is being changed
     * @param menuItem  is the item represented a genre that has been selected
     */
    void setGenreAction(MenuButton menuGenre, MenuItem menuItem){
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuGenre.setText(menuItem.getText());
            }
        });
    }

    /**
     * Displays a book's .toString in lblDisplay
     * @param title of the book
     * @param author of the book
     */
    void displayBook (String title, String author){
        ArrayList<Books> titleBooks = data.getBooksByTitle(title);
        for (Books book : titleBooks){
            if (book.getAuthor().equals(author)){
                lblDisplay.setText("Added Book!"+book.toString());
            }
        }
    }

    /**
     * Edits the vboxUserInput to have the correct fields to remove a book from library
     * When button pressed, it removes the book and displays it on the display panel
     */
    @FXML
    public void removeBook(){
        vboxUserInput.getChildren().clear();
        Label lblTitle = new Label("Title:");
        TextField txtTitle = new TextField();
        Label lblAuthor = new Label("Author:");
        TextField txtAuthor = new TextField();

        Button btnRemoveBook = new Button("Remove Book");

        // removes book when "Remove Book" is clicked
        btnRemoveBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String title = txtTitle.getText();
                String author = txtAuthor.getText();
                boolean error = data.removeBook(title,author);
                if (error){
                    lblDisplay.setText("ERROR: could not remove book");
                } else {
                    displayBook(title,author);
                }
            }
        });
        vboxUserInput.getChildren().addAll(lblTitle,txtTitle,lblAuthor,txtAuthor,btnRemoveBook);
    }

    /**
     * Edits the vboxUserInput to have the correct fields for a user to check out a book
     * When button pressed, it checks out the book and displays the member's updated information on the display panel
     */
    @FXML
    public void checkoutBook(){
        vboxUserInput.getChildren().clear();
        Label lblID = new Label("Member ID");
        TextField txtID = new TextField();
        Label lblTitle = new Label("Book Title");
        TextField txtTitle = new TextField();
        Label lblAuthor = new Label("Book Author");
        TextField txtAuthor = new TextField();
        Button btnCheckout = new Button("Checkout");

        btnCheckout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(txtID.getText());
                    String title = txtTitle.getText();
                    String author = txtAuthor.getText();
                    if (!data.checkExistMember(id)){
                        lblDisplay.setText("ERROR: that member does not exist");
                    } else if (!data.checkExistBook(title,author)){
                        lblDisplay.setText("ERROR: the library does not own that book");
                    } else if (!data.checkBookAvailable(title,author)){
                        lblDisplay.setText("ERROR: that book is currently unavailable");
                    } else {
                        data.checkoutBook(id,title,author);
                        lblDisplay.setText(title+" by "+author +"was checked out by...\n"+ data.getMembersById(id).getFirst().toString());
                    }

                    data.checkoutBook(id,txtTitle.getText(),txtAuthor.getText());
                } catch (NumberFormatException e){
                    lblDisplay.setText("ERROR: invalid member ID");
                }
            }
        });
        vboxUserInput.getChildren().addAll(lblID,txtID,lblTitle,txtTitle,lblAuthor,txtAuthor,btnCheckout);

    }

    /**
     * Edits the vboxUserInput to have the correct fields for a user to return a book
     * When button pressed, it returns the book and displays the member's updated information on the display panel
     */
    @FXML
    public void returnBook(){
        vboxUserInput.getChildren().clear();
        Label lblID = new Label("Member ID");
        TextField txtID = new TextField();
        Label lblTitle = new Label("Book Title");
        TextField txtTitle = new TextField();
        Label lblAuthor = new Label("Book Author");
        TextField txtAuthor = new TextField();
        Button btnReturn = new Button("Return");
        Label lblDaysOverdue = new Label("Days Overdue");
        TextField txtDaysOverdue = new TextField();


        btnReturn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(txtID.getText());
                    String title = txtTitle.getText();
                    String author = txtAuthor.getText();
                    int days = Integer.parseInt(txtDaysOverdue.getText());

                    if (!data.checkExistMember(id)){
                        lblDisplay.setText("ERROR: that member does not exist");
                    } else if (!data.checkExistBook(title,author)){
                        lblDisplay.setText("ERROR: the library does not own that book");
                    } else if (!data.checkBookAvailable(title,author)){
                        lblDisplay.setText("ERROR: that book is currently unavailable");
                    } else if (days<0){
                        lblDisplay.setText("ERROR: invalid numerical input");
                    }
                    else {
                        data.checkoutBook(id,title,author);
                        lblDisplay.setText(title+" by "+author +"was return by...\n"+ data.getMembersById(id).getFirst().toString());
                    }

                    data.returnBook(id,txtTitle.getText(),txtAuthor.getText(),days);
                } catch (NumberFormatException e){
                    lblDisplay.setText("ERROR: invalid numerical input");
                }
            }
        });
        vboxUserInput.getChildren().addAll(lblID,txtID,lblTitle,txtTitle,lblAuthor,txtAuthor,lblDaysOverdue,txtDaysOverdue,btnReturn);
    }

    /**
     * Displays all books on lblDisplay
     */
    @FXML
    public void viewBook(){
        vboxUserInput.getChildren().clear();

        String display="All Books:"; // text to be displayed
        ArrayList<Books> books = data.getAllBooks();
        for (Books book: books){
            display += book.toString();
        }
        lblDisplay.setText(display);
    }

    /**
     * Edits the vboxUserInput to have the correct fields for a user to search a book
     * When button pressed, it searches the book based on the criteria ,and displays it on the display panel
     * @param event The ActionEvent triggering the method call
     */
    @FXML
    public void searchBook(ActionEvent event){
        vboxUserInput.getChildren().clear(); // clear existing content
        Label lblTitle = new Label("Search Title:");
        TextField txtSearchTitle = new TextField();
        Label lblAuthor = new Label("Search Author:");

        TextField txtSearchAuthor = new TextField();

        RadioButton physical = new RadioButton("Physical");
        RadioButton audio = new RadioButton("Audiobook");

        Button btnSearch = new Button("Search");

        btnSearch.setOnAction(searchEvent -> {
            String searchTitle = txtSearchTitle.getText().trim();
            String searchAuthor = txtSearchAuthor.getText().trim();
        });

        vboxUserInput.getChildren().addAll(lblTitle, txtSearchTitle, lblAuthor, txtSearchAuthor, physical, audio, btnSearch);
    }

    /**
     * Goes through each genre and displays the most popular book in that genre
     * If there are no books in that genre, it doesn't display any book there
     */
    @FXML
    public void mostPopularBook(){
        vboxUserInput.getChildren().clear();
        String[] genres = new String[]{"Fantasy","General Fiction","Historical Fiction","Horror","Literary","Mystery",
                "Non-fiction","Poetry","Romance","Science Fiction","Thriller"};

        String display = "Most Popular Book in Each Genre\n--------------------\n";

        for (String genre:genres){ //creates genres and adds them to the genre menu
            if (!data.getBooksByGenre(genre).isEmpty()){
                display += genre + data.mostPopularBookByGenre(genre).toString() +"\n--------------------\n";
            }

        }
        lblDisplay.setText(display);
    }

    /**
     * Displays information about the library management system
     * Shows a message with details about the application, developers, and their emails
     */
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
