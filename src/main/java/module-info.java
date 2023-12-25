module com.example.myowngui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.datatransfer;

    opens com.example.myowngui to javafx.fxml;
    exports com.example.myowngui;
}