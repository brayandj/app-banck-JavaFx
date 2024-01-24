package com.jmc.appbanckjavafx.Models;

import com.jmc.appbanckjavafx.Views.ViewFactory;

public class Model {

    private static volatile Model model;
    private final ViewFactory viewFactory;

    private Model() {
        this.viewFactory = new ViewFactory();
    }

    public static Model getInstance() {
        if (model == null) {
            synchronized (Model.class) {
                if (model == null) {
                    model = new Model();
                }
            }
        }
        return model;
    }
    public ViewFactory getViewFactory() {
        return  viewFactory;
    }
}

