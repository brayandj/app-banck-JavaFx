package com.jmc.appbanckjavafx.Controllers.Client;

import com.jmc.appbanckjavafx.Models.Model;
import com.jmc.appbanckjavafx.Views.ClientMenuOptions;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuControlller implements Initializable {
    public Button dashboard_btn;
    public Button transaction_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListeners();
    }
    private void addListeners() {
        dashboard_btn.setOnAction(event -> {
            onDashboard();
            Model.getInstance().getViewFactory().handleButtonClick(dashboard_btn);
        });
        transaction_btn.setOnAction(event -> {
            onTransactinos();
            Model.getInstance().getViewFactory().handleButtonClick(transaction_btn);
        });
        accounts_btn.setOnAction(event -> {
            onAccounts();
            Model.getInstance().getViewFactory().handleButtonClick(accounts_btn);
        });
        logout_btn.setOnAction(event -> {
            onLogout();
            Model.getInstance().getViewFactory().handleButtonClick(logout_btn);
        });
    }
    private void onDashboard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);

    }
    private void onTransactinos() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
    }
    private void onAccounts() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS);
    }
    private void onLogout() {
        Stage stage = (Stage) dashboard_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
        //Establecer el indicador de inicio de sesión del cliente en false
        Model.getInstance().setClientLoginSuccessFlag(false);
        Model.getInstance().getLatestTransactions().clear();
        Model.getInstance().getAllTransactions().clear();

    }

}
