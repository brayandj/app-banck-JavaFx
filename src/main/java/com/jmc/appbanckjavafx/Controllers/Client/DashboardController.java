package com.jmc.appbanckjavafx.Controllers.Client;

import com.jmc.appbanckjavafx.Models.Model;
import com.jmc.appbanckjavafx.Models.Transaction;
import com.jmc.appbanckjavafx.Views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView<Transaction> transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        binData();
        initLatestTransactionsList();
        transaction_listview.setItems(Model.getInstance().getLatesTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
        send_money_btn.setOnAction(event -> onSendMoney());
        accountSummary();

    }

    private void binData() {
        user_name.textProperty().bind(Bindings.concat("Hola, ").concat(Model.getInstance().getClient().firstNameProperty()));
        login_date.setText("Hoy es, " + LocalDate.now());
        checking_bal.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        checking_acc_num.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        savings_bal.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
        savings_acc_num.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
    }

    private void initLatestTransactionsList() {
        if (Model.getInstance().getLatesTransactions().isEmpty()) {
            Model.getInstance().setLatesTransactions();
        }
    }

    private void onSendMoney() {
        if (!this.payee_fld.getText().isEmpty() || !this.amount_fld.getText().isEmpty()) {
            String receiver = payee_fld.getText();
            double amount = Double.parseDouble(amount_fld.getText());
            String message = message_fld.getText();
            String sender = Model.getInstance().getClient().pAddressProperty().get();
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(DBTableNames.CLIENTS.getTableName(),
                    "PayeeAddress", message);
            try {
                if (resultSet.isBeforeFirst()) {
                    Model.getInstance().getDatabaseDriver().updateBalance(DBTableNames.SAVINGS_ACCOUNTS.getTableName(), "Owner", receiver, amount, "ADD");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //Restar de la cuenta de ahorros del remitente
            Model.getInstance().getDatabaseDriver().updateBalance(DBTableNames.SAVINGS_ACCOUNTS.getTableName(), "Owner", sender, amount, "SUB");
            //Actualizar el saldo de la cuenta de ahorro en el objeto cliente
            Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(DBTableNames.SAVINGS_ACCOUNTS.getTableName(),
                    "Owner", sender));
                        Model.getInstance().getDatabaseDriver().newTransaction(DBTableNames.TRANSACTIONS.getTableName(), DBTableColumns.TRANSACTIONS.getColumns(), sender, receiver, amount, message);
            payee_fld.setText("");
            amount_fld.setText("");
            message_fld.setText("");
        }
    }

    //El método calcula todos los gastos icone
    private void accountSummary() {
        double icone = 0;
        double expenses = 0;
        if (Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
        for (Transaction transaction : Model.getInstance().getAllTransactions()) {
            if (transaction.senderProperty().get().equals(Model.getInstance().getClient().pAddressProperty().get())) {
                expenses = expenses + transaction.amountProperty().get();
            } else {
                icone = icone + transaction.amountProperty().get();
            }
        }
        income_lbl.setText("+ $" + icone);
        expense_lbl.setText("- $" + expenses);
    }
}

