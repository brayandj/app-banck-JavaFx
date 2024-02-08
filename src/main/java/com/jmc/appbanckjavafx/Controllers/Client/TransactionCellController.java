package com.jmc.appbanckjavafx.Controllers.Client;

import com.jmc.appbanckjavafx.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Label amount_lbl;
    public final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sender_lbl.textProperty().bind(transaction.senderProperty());
        receiver_lbl.textProperty().bind(transaction.receiverProperty());
        trans_date_lbl.textProperty().bind(transaction.dateProperty().asString());

        // Configuración de la animación de desvanecimiento
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), amount_lbl);
        amount_lbl.setOpacity(0); // Establecer la opacidad inicial a 0
        fadeTransition.setToValue(1); // Establecer la opacidad final a 1
        fadeTransition.play(); // Reproducir la animación de desvanecimiento
    }
}
