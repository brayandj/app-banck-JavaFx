package com.jmc.appbanckjavafx.Controllers.Client;

import com.jmc.appbanckjavafx.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class ClientController implements Initializable {
    public BorderPane client_parent;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case TRANSACTIONS:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                    break;
                case ACCOUNTS:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                    break;
                default:
                    client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
