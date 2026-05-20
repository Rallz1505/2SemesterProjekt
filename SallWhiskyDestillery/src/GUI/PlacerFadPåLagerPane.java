package GUI;

import Controller.Controller;
import Model.Fad;
import Model.Lager;
import Model.LagerPlads;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlacerFadPåLagerPane extends GridPane {

    //Brugt AI til at gøre det pænt så det var lidt mere behageligt at kigge på :), men ikk ebrugt det til andet.

    private ComboBox<Fad> cbFad;
    private ComboBox<Lager> cbLager;
    private ComboBox<LagerPlads> cbLagerPlads;

    public void open() {

        Stage stage = new Stage();
        stage.setTitle("Placer / fjern fad på lager");

        initContent();

        Scene scene = new Scene(this, 650, 500);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {

        this.setPadding(new Insets(30));
        this.setHgap(20);
        this.setVgap(15);
        this.setAlignment(Pos.CENTER);

        this.setStyle("-fx-background-color: linear-gradient(to bottom, #3b2415, #1f120a);");

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(500);

        this.getColumnConstraints().add(c1);

        VBox box = createBox();

        Label lblTitle = createSectionLabel("Placer / fjern fad på lager");

        cbFad = new ComboBox<>();
        cbFad.setPrefWidth(320);
        cbFad.getItems().setAll(Controller.getFade());

        cbLager = new ComboBox<>();
        cbLager.setPrefWidth(320);
        cbLager.getItems().setAll(Controller.getLagre());

        cbLager.setOnAction(e -> updateLagerPladser());

        cbLagerPlads = new ComboBox<>();
        cbLagerPlads.setPrefWidth(320);

        Button btnPlacer = createButton("Placer fad");
        btnPlacer.setOnAction(e -> placerFad());

        Button btnFjern = createButton("Fjern fad fra plads");
        btnFjern.setOnAction(e -> fjernFad());

        box.getChildren().addAll(
                lblTitle,

                createLabel("Vælg fad:"),
                cbFad,

                createLabel("Vælg lager:"),
                cbLager,

                createLabel("Vælg lagerplads:"),
                cbLagerPlads,

                btnPlacer,
                btnFjern
        );

        this.add(box, 0, 0);
    }

    private void updateLagerPladser() {

        Lager lager = cbLager.getValue();

        cbLagerPlads.getItems().clear();

        if (lager != null) {
            cbLagerPlads.getItems().setAll(lager.getLagerPladser());
        }
    }

    private void placerFad() {

        Fad fad = cbFad.getValue();
        LagerPlads lagerPlads = cbLagerPlads.getValue();

        if (fad == null || lagerPlads == null) {
            visAlert("Vælg både fad og lagerplads.");
            return;
        }

        try {

            Controller.placerFadPåLagerPlads(fad, lagerPlads);

            visAlert("Fad placeret på lagerplads.");

            updateContent();

        } catch (IllegalArgumentException e) {
            visAlert(e.getMessage());
        }
    }

    private void fjernFad() {

        LagerPlads lagerPlads = cbLagerPlads.getValue();

        if (lagerPlads == null) {
            visAlert("Vælg en lagerplads.");
            return;
        }

        try {

            Controller.fjernFadFraLagerPlads(lagerPlads);

            visAlert("Fad fjernet fra lagerplads.");

            updateContent();

        } catch (IllegalArgumentException e) {
            visAlert(e.getMessage());
        }
    }

    private void updateContent() {

        Fad valgtFad = cbFad.getValue();
        Lager valgtLager = cbLager.getValue();

        cbFad.getItems().setAll(Controller.getFade());
        cbLager.getItems().setAll(Controller.getLagre());

        cbFad.setValue(valgtFad);
        cbLager.setValue(valgtLager);

        updateLagerPladser();
    }

    private VBox createBox() {

        VBox box = new VBox(12);

        box.setPadding(new Insets(25));
        box.setAlignment(Pos.TOP_CENTER);

        box.setStyle(
                "-fx-background-color: #5a321b;" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-color: #b88746;" +
                        "-fx-border-radius: 18;" +
                        "-fx-border-width: 1.5;"
        );

        return box;
    }

    private Label createSectionLabel(String text) {

        Label label = new Label(text);

        label.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #f3d7a3;"
        );

        return label;
    }

    private Label createLabel(String text) {

        Label label = new Label(text);

        label.setStyle(
                "-fx-text-fill: #f3d7a3;" +
                        "-fx-font-size: 13px;"
        );

        return label;
    }

    private Button createButton(String text) {

        Button button = new Button(text);

        button.setPrefWidth(220);
        button.setPrefHeight(40);

        button.setStyle(
                "-fx-background-color: #b88746;" +
                        "-fx-text-fill: #1f120a;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;"
        );

        return button;
    }

    private void visAlert(String besked) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(besked);

        alert.showAndWait();
    }
}