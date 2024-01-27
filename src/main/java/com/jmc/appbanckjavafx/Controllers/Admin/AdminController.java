package com.jmc.appbanckjavafx.Controllers.Admin;

import com.jmc.appbanckjavafx.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController  implements Initializable {
    public BorderPane admin_parent;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            //Add Switch statement
        });
    }
}
