package com.jmc.appbanckjavafx.Controllers.Admin;

import com.jmc.appbanckjavafx.Controllers.Client.ClientMenuControlller;
import com.jmc.appbanckjavafx.Models.Model;
import com.jmc.appbanckjavafx.Views.AdminMenuOptions;
import javafx.animation.RotateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;
    public VBox containerStyleClass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListeners();
    }

    private void addListeners() {
        create_client_btn.setOnAction(event -> {
            onCreateClient();
            Model.getInstance().getViewFactory().handleButtonClick(create_client_btn);
        });
        clients_btn.setOnAction(event -> {
            onClients();
            Model.getInstance().getViewFactory().handleButtonClick(clients_btn);
        });
        deposit_btn.setOnAction(event -> {
            onDeposit();
            Model.getInstance().getViewFactory().handleButtonClick(deposit_btn);
        });
        logout_btn.setOnAction(event -> onLogout());
    }
    private void onCreateClient() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
    }
    private void onClients() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CLIENTS);
    }
    private void onDeposit() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DEPOSIT);
    }
    private void onLogout() {
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLoginWindow();
        Model.getInstance().setAdminLoginSuccessFlag(false);
        Model.getInstance().getLatestTransactions().clear();
        Model.getInstance().getAllTransactions().clear();

    }
}
