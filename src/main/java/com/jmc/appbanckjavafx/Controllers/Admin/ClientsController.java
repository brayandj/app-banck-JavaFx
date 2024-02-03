package com.jmc.appbanckjavafx.Controllers.Admin;

import com.jmc.appbanckjavafx.Models.Client;
import com.jmc.appbanckjavafx.Models.Model;
import com.jmc.appbanckjavafx.Views.ClientCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    public ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(param -> new ClientCellFactory());
    }

    private void initData() {
        if (Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }
    }
}
