module sea.cpsc233projectgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens sea.cpsc233projectgui to javafx.fxml;
    exports sea.cpsc233projectgui;
}