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
import sea.cpsc233projectgui.util.BookRecords;
import sea.cpsc233projectgui.util.BookType;
import sea.cpsc233projectgui.util.MemberRecords;
import sea.cpsc233projectgui.util.MemberType;

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

    private Data data = new Data();
    private void setData(Data data) {
        this.data = data;
    }

    /**
     * Allows the user to save a file using a file chooser dialog
     * When button pressed , it displays a success or error message depending on the outcome
     * @param event The ActionEvent that triggered the save action
     */
    @FXML
    protected void save(ActionEvent event) {
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Choose Member File");
        a.showAndWait();
        File memberFile = getFile(event);
        // Show alert
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Choose Book File");
        a.showAndWait();
        File bookFile = getFile(event);
        save(memberFile,bookFile);
    }
    /**
     * loads the new files int Data... can be called on from command line arguments as well
     * @param memberFile is file containing member info
     * @param bookFile is file containing book info
     */
    @FXML
    void save (File memberFile, File bookFile){
        if (memberFile!=null&&bookFile!=null){
            if (MemberRecords.save(memberFile,data)){
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Member file saved!");
                a.showAndWait();
            } else {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("File Saving Failed");
                a.show();
            }
            if (BookRecords.save(bookFile,data)){
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Book file saved!");
                a.showAndWait();
            } else {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("File Saving Failed");
                a.show();
            }


        } else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("File Saving Failed");
            a.show();
        }

    }


    /**
     * Allows the user to load a file using a file chooser dialog
     * When button pressed , it displays a success or error message depending on the outcome
     * @param event The ActionEvent that triggered the load action
     */
    @FXML
    void load(ActionEvent event){
        // Show alert
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Choose Member File");
        a.showAndWait();
        File memberFile = getFile(event);
        // Show alert
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Choose Book File");
        a.showAndWait();
        File bookFile = getFile(event);
        load(memberFile,bookFile);
    }

    /**
     * loads the new files int Data... can be called on from command line arguments as well
     * @param memberFile is file containing member info
     * @param bookFile is file containing book info
     */
    @FXML
    void load (File memberFile, File bookFile){
        if (memberFile!=null && bookFile!=null){
            Data newData = MemberRecords.load(memberFile,data);
            if (newData!=null){
                setData(newData);
            } else {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Invalid format for member file");
                a.show();
            }
            newData = BookRecords.load(bookFile,data);
            if (newData!=null){
                setData(newData);
            } else {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Invalid format for book file");
                a.show();
            }
        } else {
            //a.setAlertType(Alert.AlertType.ERROR);
            //a.setContentText("File Load Failed");
            //a.show();
        }

    }

    /**
     * uses filechooser to get file from user
     * @return the selected file
     */

    @FXML
    File getFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        MenuItem menuItem = (MenuItem) event.getSource();
        loadedFile = fileChooser.showOpenDialog(menuItem.getParentPopup().getOwnerWindow());

        if (loadedFile != null) {
            return loadedFile;
        } else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("File Load Failed");
            a.show();
            return null;
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

        for (String type:memType){ //creates member types and adds them to the memType menu
            MenuItem menuItem = new MenuItem(type);
            menuMemberType.getItems().add(menuItem);
            setMemAction(menuMemberType,menuItem);
        }

        Button btnAddMem = new Button("Add Member");

        // creates member when "Add Member" is clicked
        btnAddMem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String id = txtID.getText();
                String name = txtName.getText();
                boolean error = false;
                if (!menuMemberType.getText().equals("Member Type:") && !id.isEmpty() && !name.isEmpty()){
                    String type = menuMemberType.getText();
                    Integer id1 = Integer.parseInt(txtID.getText());
                    if (type.equals("ADULT")){
                        error = !data.storeNewAdultMember(id1,name);
                    } else if (type.equals("CHILD")){
                        error = !data.storeNewChildMember(id1,name);
                    }
                    else {
                        error = true;
                    }
                }
                else {
                    error = true;
                }
                if (error){
                    lblDisplay.setText("ERROR: Invalid Member Information");
                } else {
                    displayMembers(Integer.parseInt(id),name);
                }
            }
        });

        //add all these items to the display box...
        vboxUserInput.setSpacing(10); // Set spacing to 10 pixels (adjust as needed)
        vboxUserInput.getChildren().addAll(lblID,txtID,lblName,txtName,menuMemberType,btnAddMem);

    }

    /**
     * When a member type is selected, changes the menuButton to reflect the change
     * @param menuMemberType is the MenuButton that is being changed
     * @param menuItem  is the item represented a member type that has been selected
     */
    void setMemAction(MenuButton menuMemberType, MenuItem menuItem){
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuMemberType.setText(menuItem.getText());
            }
        });
    }

    /**
     * Displays a member's .toString in lblDisplay
     * @param id of the member
     * @param name of the member
     */
    void displayMembers (int id, String name){
        ArrayList<Member> memberIds = data.getMembersById(id);
        for (Member member : memberIds){
            if (member.getName().equals(name)){
                lblDisplay.setText("Added Member! "+member.toString());
            }
        }
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

        vboxUserInput.setSpacing(10); // Set spacing to 10 pixels (adjust as needed)
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

    /**
     * Edits the vboxUserInput to have the correct fields for a user to search for a member
     * When button pressed, it searches for the member based on the criteria, and displays it on the display panel
     * @param event The ActionEvent triggering the method call
     */
    @FXML
    public void searchMember(ActionEvent event){
        vboxUserInput.getChildren().clear(); // clear existing content

        MenuButton menuSearch = new MenuButton("Search by...");

        MenuItem searchId = new MenuItem("Search Id");
        menuSearch.getItems().add(searchId);
        MenuItem searchName = new MenuItem("Search Name");
        menuSearch.getItems().add(searchName);
        MenuItem searchType = new MenuItem("Search Member Type");
        menuSearch.getItems().add(searchType);

//        vboxUserInput.setSpacing(10); // Set spacing to 10 pixels (adjust as needed)
//        vboxUserInput.getChildren().addAll(lblID, txtSearchID, lblName, txtSearchName, adult, child, btnSearch);
    }

    @FXML
    void searchId(MenuItem searchId, VBox vboxSearch,MenuButton menuSearch){
        //when search by id is selected, the following happens
        searchId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vboxSearch.getChildren().clear();
                menuSearch.setText(searchId.getText());
                Label lblId = new Label("Id");
                TextField txtId = new TextField();
                int memberId = Integer.parseInt(txtId.getText());
                Button btnSearch = new Button("Search");

                btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ArrayList<Member> members = data.getMembersById(memberId);
                        String display = "Members with that id:\n";
                        for (Member member:members){
                            display += member.toString();
                        }
                        lblDisplay.setText(display);
                    }
                });

                vboxSearch.setSpacing(10);
                vboxSearch.getChildren().addAll(lblId, txtId, btnSearch);
            }
        });
    }

    @FXML
    void searchName(MenuItem searchName, VBox vboxSearch,MenuButton menuSearch){
        //when search by name is selected, the following happens
        searchName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vboxSearch.getChildren().clear();
                menuSearch.setText(searchName.getText());
                Label lblName = new Label("Name");
                TextField txtName = new TextField();
                Button btnSearch = new Button("Search");

                btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ArrayList<Member> members = data.getMembersByName(txtName.getText());
                        String display = "Members with that id:\n";
                        for (Member member:members){
                            display += member.toString();
                        }
                        lblDisplay.setText(display);
                    }
                });

                vboxSearch.setSpacing(10);
                vboxSearch.getChildren().addAll(lblName, txtName, btnSearch);
            }
        });
    }

    @FXML
    void searchMemberType(MenuItem searchMemberType, VBox vboxSearch,MenuButton menuSearch){
        //when search by member type is selected, the following happens
        searchMemberType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuSearch.setText(searchMemberType.getText());
                vboxSearch.getChildren().clear();
                MenuButton menuType = new MenuButton("Type");
                String[] types = new String[]{MemberType.CHILD.toString(), MemberType.ADULT.toString()};

                for (String type:types){
                    MenuItem menuItem = new MenuItem(type);
                    menuType.getItems().add(menuItem);
                    setGenreAction(menuType,menuItem);
                }

                Button btnSearch = new Button("Search");
                btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (!menuType.getText().equals("Member Type")) {
                            ArrayList<Member> members = data.getMembersByMemberType(menuType.getText());
                            String display = menuType.getText() + " members:\n";
                            for (Member member : members) {
                                display += member.toString();
                            }
                            lblDisplay.setText(display);
                        } else {
                            lblDisplay.setText("ERROR: type not selected");
                        }
                    }
                });
                vboxSearch.setSpacing(10);
                vboxSearch.getChildren().addAll(menuType,btnSearch);
            }
        });
    }


    /**
     * Allows user to select from the list of user IDs, then displays the selected user
     * When the button is clicked, fines are paid
     */
    @FXML
    public void payFines(){
        vboxUserInput.getChildren().clear();
        MenuButton menuIDs = new MenuButton("Select Member ID");
        ArrayList<Member> members = data.getAllMembers();
        for (Member member:members){
            MenuItem menuMember = new MenuItem(String.valueOf(member.getID()));
            menuIDs.getItems().add(menuMember);
            setMemberAction(menuIDs,menuMember);
        }
        Label lblPayment = new Label("Payment amount");
        TextField txtPayment = new TextField();
        Button btnPayFines = new Button("Pay Fines");
        btnPayFines.setOnMouseClicked(new EventHandler<MouseEvent>() { //what happens when the button is pressed
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int ID = Integer.parseInt(menuIDs.getText());
                    double payment = Double.parseDouble(txtPayment.getText());
                    if (data.payFines(ID,payment)){
                        Member member = data.getMembersById(Integer.parseInt(menuIDs.getText())).getFirst();
                        lblDisplay.setText("Payment Successful!\n"+member.toString());
                    } else {
                        lblDisplay.setText("ERROR: invalid payment");
                    }
                } catch (NumberFormatException e){
                    lblDisplay.setText("ERROR: invalid inputs");
                }

            }
        });

        vboxUserInput.setSpacing(10); // Set spacing to 10 pixels (adjust as needed)
        vboxUserInput.getChildren().addAll(menuIDs,lblPayment,txtPayment,btnPayFines);

    }

    /**
     * called on by "payFines" to make a complete MenuButton list of the member's IDs
     * when an option is clicked, the information is displayed
     * @param menuButton to change the text of
     * @param menuItem that is being clicked
     */
    @FXML
    void setMemberAction(MenuButton menuButton,MenuItem menuItem){
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuButton.setText(menuItem.getText());
                Member member = data.getMembersById(Integer.parseInt(menuButton.getText())).getFirst();
                lblDisplay.setText("Member Information:\n"+member.toString());
            }
        });
    }

    /**
     * Goes through the number of days overdue for each book that is returned and displays
     * the average time it takes for a member to return a book to the library
     * If there is no data regarding checkout and returning of books, returns 0
     */
    @FXML
    public void averageDaysOverdue(){
        vboxUserInput.getChildren().clear();

        String display = "Average Days Overdue:\n--------------------\n" + data.getAverageDaysOverdue();
        lblDisplay.setText(display);
    }

    /**
     * Goes through all the child members in the library and congratulates the one that
     * has borrowed the most books at any given period of time
     * If there is no data to be collected, displays "No children found"
     */
    @FXML
    public void mostActiveChild(){
        vboxUserInput.getChildren().clear();

        String display = "Most Active Child\n--------------------\n";

        if (data.getMostActiveChild() == null){
            display += "No children found!";
        } else {
            display +=  data.getMostActiveChild() + "\n--------------------\nCongratulations! Happy Reading!";
        }
        lblDisplay.setText(display);
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
                    lblDisplay.setText("ERROR: Invalid Book Information");
                } else {
                    displayBook(title,author);
                }
            }
        });

        //add all these items to the display box...
        vboxUserInput.setSpacing(10); // Set spacing to 10 pixels (adjust as needed)
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
                lblDisplay.setText("Added Book! "+book.toString());
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
                    lblDisplay.setText("ERROR: Could Not Remove Book");
                } else {
                    displayBook(title,author);
                }
            }
        });

        vboxUserInput.setSpacing(10); // Set spacing to 10 pixels (adjust as needed)
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

        vboxUserInput.setSpacing(10); // Set spacing to 10 pixels (adjust as needed)
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

        vboxUserInput.setSpacing(10); // Set spacing to 10 pixels (adjust as needed)
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

        MenuButton menuSearch = new MenuButton("Search by...");

        MenuItem searchTitle = new MenuItem("Search Title");
        menuSearch.getItems().add(searchTitle);
        MenuItem searchAuthor = new MenuItem("Search Author");
        menuSearch.getItems().add(searchAuthor);
        MenuItem searchGenre = new MenuItem("Search Genre");
        menuSearch.getItems().add(searchGenre);
        MenuItem searchType = new MenuItem("Search Type");
        menuSearch.getItems().add(searchType);

        VBox vboxSearch = new VBox(); // displays the search items depending on type of search

        searchTitle(searchTitle,vboxSearch,menuSearch);
        searchAuthor(searchAuthor,vboxSearch,menuSearch);
        searchGenre(searchGenre,vboxSearch,menuSearch);
        searchType(searchType,vboxSearch,menuSearch);

        vboxUserInput.setSpacing(10);
        vboxUserInput.getChildren().addAll(menuSearch, vboxSearch);
    }

    /**
     * called on by searchBook when search by title is selected
     * @param searchTitle is the MenuItem selected
     * @param vboxSearch is the VBox being displayed to the user
     */
    @FXML
    void searchTitle(MenuItem searchTitle, VBox vboxSearch,MenuButton menuSearch){
        //when search by title is selected, the following happens
        searchTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vboxSearch.getChildren().clear();
                menuSearch.setText(searchTitle.getText());
                Label lblTitle = new Label("Title");
                TextField txtTitle = new TextField();
                Button btnSearch = new Button("Search");
                btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ArrayList<Books> books = data.getBooksByTitle(txtTitle.getText());
                        String display = "Books with that title:\n";
                        for (Books book:books){
                            display+=book.toString();
                        }
                        lblDisplay.setText(display);
                    }
                });
                vboxSearch.setSpacing(10);
                vboxSearch.getChildren().addAll(lblTitle,txtTitle,btnSearch);
            }
        });
    }


    /**
     * called on by searchBook when search by author is selected
     * @param searchAuthor is the MenuItem selected
     * @param vboxSearch is the VBox being displayed to the user
     */
    @FXML
    void searchAuthor(MenuItem searchAuthor, VBox vboxSearch,MenuButton menuSearch){
        //when search by author is selected, the following happens
        searchAuthor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vboxSearch.getChildren().clear();
                menuSearch.setText(searchAuthor.getText());
                Label lblAuthor = new Label("Author");
                TextField txtAuthor = new TextField();
                Button btnSearch = new Button("Search");
                btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ArrayList<Books> books = data.getBooksByAuthor(txtAuthor.getText());
                        String display = "Books by "+txtAuthor.getText()+":\n";
                        for (Books book:books){
                            display+=book.toString();
                        }
                        lblDisplay.setText(display);
                    }
                });
                vboxSearch.setSpacing(10);
                vboxSearch.getChildren().addAll(lblAuthor,txtAuthor,btnSearch);
            }
        });
    }


    /**
     * called on by searchBook when search by genre is selected
     * @param searchGenre is the MenuItem selected
     * @param vboxSearch is the VBox being displayed to the user
     */
    @FXML
    void searchGenre(MenuItem searchGenre, VBox vboxSearch,MenuButton menuSearch){
        //when search by author is selected, the following happens
        searchGenre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuSearch.setText(searchGenre.getText());
                vboxSearch.getChildren().clear();
                MenuButton menuGenre = new MenuButton("Genre");
                String[] genres = new String[]{"Fantasy","General Fiction","Historical Fiction","Horror","Literary","Mystery",
                        "Non-fiction","Poetry","Romance","Science Fiction","Thriller"};

                for (String genre:genres){ //creates genres and adds them to the genre menu
                    MenuItem menuItem = new MenuItem(genre);
                    menuGenre.getItems().add(menuItem);
                    setGenreAction(menuGenre,menuItem);
                }

                Button btnSearch = new Button("Search");
                btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (!menuGenre.getText().equals("Genre")) {
                            ArrayList<Books> books = data.getBooksByGenre(menuGenre.getText());
                            String display = menuGenre.getText() + " books:\n";
                            for (Books book : books) {
                                display += book.toString();
                            }
                            lblDisplay.setText(display);
                        } else {
                            lblDisplay.setText("ERROR: genre not selected");
                        }
                    }
                });
                vboxSearch.setSpacing(10);
                vboxSearch.getChildren().addAll(menuGenre,btnSearch);
            }
        });
    }

    /**
     * called on by searchBook when search by type is selected
     * @param searchType is the MenuItem selected
     * @param vboxSearch is the VBox being displayed to the user
     */
    @FXML
    void searchType(MenuItem searchType, VBox vboxSearch,MenuButton menuSearch){
        //when search by type is selected, the following happens
        searchType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuSearch.setText(searchType.getText());
                vboxSearch.getChildren().clear();
                MenuButton menuType = new MenuButton("Type");
                String[] types = new String[]{BookType.PHYSICAL.toString(),BookType.AUDIO.toString()};

                for (String type:types){ // using genre code, it adds them to the type menu
                    MenuItem menuItem = new MenuItem(type);
                    menuType.getItems().add(menuItem);
                    setGenreAction(menuType,menuItem);
                }

                Button btnSearch = new Button("Search");
                btnSearch.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (!menuType.getText().equals("Type")) {
                            ArrayList<Books> books = data.getBooksByBookType(menuType.getText());
                            String display = menuType.getText() + " books:\n";
                            for (Books book : books) {
                                display += book.toString();
                            }
                            lblDisplay.setText(display);
                        } else {
                            lblDisplay.setText("ERROR: type not selected");
                        }
                    }
                });
                vboxSearch.setSpacing(10);
                vboxSearch.getChildren().addAll(menuType,btnSearch);
            }
        });
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
        String aboutMessage = "Welcome to our library management system!\n" +
                "Developers:\nAnna Littkemann, Emily Ng Kwong Sang, Srijita Marick\n" +
                "Emails:\nanna.littkemann@ucalgary.ca, emily.ngkwongsang@ucalgary.ca," +
                "  srijita.marick@ucalgary.ca";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("SEA Library management system");
        alert.setContentText(aboutMessage);
        alert.showAndWait();
    }
}
