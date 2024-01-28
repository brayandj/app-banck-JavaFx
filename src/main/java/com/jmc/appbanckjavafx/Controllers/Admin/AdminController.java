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
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            //Add Switch statement
            switch (newValue) {
                case CLIENTS:
                    admin_parent.setCenter(Model.getInstance().getViewFactory().getClientsView());
                    break;
                case DEPOSIT:
                    admin_parent.setCenter(Model.getInstance().getViewFactory().getDepositView());
                    break;
                default:
                    admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }
        });
    }
}
