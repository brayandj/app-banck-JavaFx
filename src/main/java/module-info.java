module com.jmc.appbanckjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.jmc.appbanckjavafx to javafx.fxml;
    exports com.jmc.appbanckjavafx;
    exports com.jmc.appbanckjavafx.Controllers;
    exports com.jmc.appbanckjavafx.Controllers.Admin;
    exports com.jmc.appbanckjavafx.Controllers.Client;
    exports com.jmc.appbanckjavafx.Models;
    exports com.jmc.appbanckjavafx.Views;
}