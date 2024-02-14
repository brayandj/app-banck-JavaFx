package com.jmc.appbanckjavafx.Controllers.Client;

import com.jmc.appbanckjavafx.Models.Model;
import com.jmc.appbanckjavafx.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Button message_btn;
    public Label amount_lbl;
    public final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sender_lbl.textProperty().bind(transaction.senderProperty());
        receiver_lbl.textProperty().bind(transaction.receiverProperty());
        amount_lbl.textProperty().bind(transaction.amountProperty().asString());
        trans_date_lbl.textProperty().bind(transaction.dateProperty().asString());
        message_btn.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindows(transaction.senderProperty().get(), transaction.messageProperty().get()));
        transactionIcons();

        //Configurar la animación de escala
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), amount_lbl);
        scaleTransition.setFromX(1.0); // Escala inicial en X
        scaleTransition.setFromY(1.0); // Escala inicial en Y
        scaleTransition.setToX(1.5); // Escala final en X
        scaleTransition.setToY(1.5); // Escala final en Y
        scaleTransition.setAutoReverse(true); // Permitir reversión de la animación
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE); // Repetir la animación indefinidamente
        scaleTransition.play(); // Reproducir la animación de escala
    }

    private void transactionIcons() {
        if (transaction.senderProperty().get().equals(Model.getInstance().getClient().pAddressProperty().get())) {
            in_icon.setFill(Color.rgb(240, 240, 240));
            out_icon.setFill(Color.RED);
        } else {
            out_icon.setFill(Color.rgb(240, 240, 240));
            in_icon.setFill(Color.GREEN);
        }
    }
}
