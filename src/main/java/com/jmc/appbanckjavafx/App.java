package com.jmc.appbanckjavafx;

import com.jmc.appbanckjavafx.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Model.getInstance().getDatabaseDriver().closeConnection();
    }
}
