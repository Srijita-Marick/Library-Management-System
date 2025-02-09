package sea.cpsc233projectgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LibraryApplication extends Application {

    /**
     * Runs on startup
     * @param stage is the stage (visual component) of the program that will be edited as actions are taken
     * @throws IOException if startup fails
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("library.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        LibraryController cont = fxmlLoader.getController();
        cont.load(memberFile, bookFile);
        stage.setTitle("Welcome to Library");
        stage.setScene(scene);
        stage.show();
    }

    private static File bookFile = null;

    private static File memberFile = null;

    /**
     * launches the program and handles command line arguments
     * @param args are the command line arguments from the terminal
     */
    public static void main(String[] args) {
        if(args.length >2){
            System.err.println("Expected two command line argument for filename to load from");
        }
        if(args.length == 2){
            String bookFilename = args[0];
            String memberFilename = args[1];
            bookFile = new File(args[0]);
            memberFile = new File(args[1]);
            if(!bookFile.exists()||!bookFile.canRead()){
                System.err.println("Cannot load from the file " + bookFilename);
                System.exit(2);
            }
            if(!memberFile.exists()||!memberFile.canRead()){
                System.err.println("Cannot load from the file " + memberFilename);
                System.exit(2);
            }
        }

        launch();
    }
}