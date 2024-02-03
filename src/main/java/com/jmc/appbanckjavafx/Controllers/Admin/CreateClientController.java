package com.jmc.appbanckjavafx.Controllers.Admin;

import com.jmc.appbanckjavafx.Controllers.Client.DBTableColumns;
import com.jmc.appbanckjavafx.Controllers.Client.DBTableNames;
import com.jmc.appbanckjavafx.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public CheckBox pAddress_box;
    public Label pAddress_lbl;
    public CheckBox ch_acc_box;
    public TextField ch_account_fld;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fld;
    public Button create_client_btn;
    public Label error_lbl;
    private String payeeAddress;
    private boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_client_btn.setOnAction(event -> createClient());
        pAddress_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }
        });
        ch_acc_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                createCheckingAccountFlag = true;
            }
        });
    }

    private void createClient() {
        //Create Checking Account
        if (createCheckingAccountFlag) {
            createAccount("Checking");
            // Create Saving Account
            if (createSavingsAccountFlag) {
                createAccount("Savings");
            }
            //Create Client
            String fName = fName_fld.getText();
            String lName = lName_fld.getText();
            String password = password_fld.getText();
            Model.getInstance().getDatabaseDriver().createClient(DBTableNames.CLIENTS.getTableName(), DBTableColumns.CLIENTS.getColumns(),
                    fName, lName, payeeAddress, password, LocalDate.now());
            error_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold;");
            error_lbl.setText("El cliente fue creado con éxito");
            emptyFiels();
        }
    }

    private void createAccount(String accountType) {
        double balance = Double.parseDouble(ch_account_fld.getText());
        //Generate Account Number
        String firstSection = "3201";
        String lastSection = Integer.toString(new Random().nextInt(9999) + 1000);
        String accountNumber = firstSection + " " + lastSection;
        //Create the checking or Savings account
        if (accountType.equals("Checking")) {
            Model.getInstance().getDatabaseDriver().createCheckingAccount(DBTableNames.CHECKING_ACCOUNTS.getTableName(), DBTableColumns.CHECKING_ACCOUNTS.getColumns(),
                    payeeAddress, accountNumber, 10, balance);
        } else {
            Model.getInstance().getDatabaseDriver().createSavingsAccount(DBTableNames.SAVINGS_ACCOUNTS.getTableName(), DBTableColumns.SAVINGS_ACCOUNTS.getColumns(),
                    payeeAddress, accountNumber, 2000, balance);
        }
    }

    private void onCreatePayeeAddress() {
        if (fName_fld.getText() != null & lName_fld.getText() != null) {
            pAddress_lbl.setText(payeeAddress);
        }
    }

    private String createPayeeAddress() {
        int id = Model.getInstance().getDatabaseDriver().getListClientsId() + 1;
        char fChar = Character.toLowerCase(fName_fld.getText().charAt(0));
        return "@" + fChar + lName_fld.getText() + id;
    }

    private void emptyFiels() {
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        pAddress_box.setSelected(false);
        pAddress_lbl.setText("");
    }
}
