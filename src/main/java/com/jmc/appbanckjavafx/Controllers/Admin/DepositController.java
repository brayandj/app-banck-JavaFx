package com.jmc.appbanckjavafx.Controllers.Admin;

import com.jmc.appbanckjavafx.Controllers.Client.DBTableNames;
import com.jmc.appbanckjavafx.Models.Client;
import com.jmc.appbanckjavafx.Models.Model;
import com.jmc.appbanckjavafx.Views.ClientCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField pAddress_fld;
    public Button search_btn;
    public ListView<Client> result_listview;
    public TextField amount_fld;
    public Button deposit_btn;
    Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search_btn.setOnAction(event -> onClientSearch());
        deposit_btn.setOnAction(event -> onDeposit());

    }

    private void onClientSearch() {
        ObservableList<Client> searchResult = Model.getInstance().searchClient(pAddress_fld.getText());
        result_listview.setItems(searchResult);
        result_listview.setCellFactory(e -> new ClientCellFactory());
        client = searchResult.get(0);
    }

    private void onDeposit() {
        double amount = Double.parseDouble(amount_fld.getText());
        double newBalance = amount + client.savingsAccountProperty().get().balanceProperty().get();
        if (amount_fld.getText() != null) {
            Model.getInstance().getDatabaseDriver().depositSavings(DBTableNames.SAVINGS_ACCOUNTS.getTableName(),
                    "Balance","Owner", client.pAddressProperty().get(), newBalance);
        }
        emptyFields();
    }

    private void emptyFields() {
        pAddress_fld.setText("");
        amount_fld.setText("");
    }

}
