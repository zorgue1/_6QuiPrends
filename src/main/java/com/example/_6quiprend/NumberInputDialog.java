package com.example._6quiprend;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class NumberInputDialog extends Dialog<Integer> {

    private ComboBox<Integer> comboBox;

    public NumberInputDialog() {
        initComponents();
        setDialogProperties();
    }

    private void initComponents() {
        comboBox = new ComboBox<>();
        comboBox.getItems().addAll(1, 2, 3, 4);

        VBox vbox = new VBox(comboBox);
        vbox.setPadding(new Insets(10));

        getDialogPane().setContent(vbox);
    }

    private void setDialogProperties() {
        setTitle("Choisissez un chiffre");
        setHeaderText(null);
    }

    public int getSelectedNumber() {
        return comboBox.getValue();
    }
}
