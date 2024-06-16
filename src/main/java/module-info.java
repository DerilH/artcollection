module org.main.artcollection {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens org.main.artcollection to javafx.fxml;
    exports org.main.artcollection;
}