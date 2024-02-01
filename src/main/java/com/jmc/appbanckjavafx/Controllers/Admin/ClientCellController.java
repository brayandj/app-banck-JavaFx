package com.jmc.appbanckjavafx.Controllers.Admin;

import com.jmc.appbanckjavafx.Models.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {
    public Label flname_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label date_lbl;
    public Button delete_btn;
    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
