package com.jmc.appbanckjavafx.Views;

import com.jmc.appbanckjavafx.Controllers.Admin.ClientCellController;
import com.jmc.appbanckjavafx.Models.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ClientCellFactory extends ListCell<Client> {
    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        if (empty) {
            setText(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
            ClientCellController controller = new ClientCellController(client);
            loader.setController(client);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
