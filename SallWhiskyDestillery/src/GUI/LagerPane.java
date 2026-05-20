package GUI;

import Controller.Controller;
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

public class LagerPane extends GridPane {

    //Brugt AI til at gøre det pænt så det var lidt mere behageligt at kigge på :), men ikk ebrugt det til andet.

    private Lager ingenValgt;
    private TextField txfLagerNavn, txfAdresse, txfKapacitet;
    private TextArea txaLagerBeskrivelse;
    private ComboBox<Lager> cbLager;
    private TextField txfReol, txfHylde, txfPlads;
    private TextArea txaPladsBeskrivelse;
    private ListView<LagerPlads> lvwPladser;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Lager administration");

        initContent();

        Scene scene = new Scene(this, 1000, 560);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {
        this.setPadding(new Insets(30));
        this.setHgap(25);
        this.setVgap(15);
        this.setAlignment(Pos.CENTER);

        this.setStyle("-fx-background-color: linear-gradient(to bottom, #3b2415, #1f120a);");

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(300);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setMinWidth(300);

        ColumnConstraints c3 = new ColumnConstraints();
        c3.setMinWidth(300);

        this.getColumnConstraints().addAll(c1, c2, c3);

        Label lblTitle = new Label("Lager administration");
        lblTitle.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #f3d7a3;"
        );
        this.add(lblTitle, 0, 0, 3, 1);

        VBox lagerBox = createBox();
        Label lblLagerTitle = createSectionLabel("Registrer lager");

        txfLagerNavn = createTextField();
        txfAdresse = createTextField();
        txfKapacitet = createTextField();

        txaLagerBeskrivelse = createTextArea();

        Button btnSaveLager = createButton("Opret lager");
        btnSaveLager.setOnAction(e -> gemLager());

        lagerBox.getChildren().addAll(
                lblLagerTitle,
                createLabel("Lagernavn:"),
                txfLagerNavn,
                createLabel("Adresse:"),
                txfAdresse,
                createLabel("Lager ID:"),
                txfKapacitet,
                createLabel("Beskrivelse:"),
                txaLagerBeskrivelse,
                btnSaveLager
        );

        this.add(lagerBox, 0, 1);

        VBox pladsBox = createBox();
        Label lblPladsTitle = createSectionLabel("Registrer lagerplads");

        cbLager = new ComboBox<>();
        cbLager.setPrefWidth(240);

        ingenValgt = new Lager(0, "Ikke valgt", "", "");

        cbLager.getItems().add(ingenValgt);
        cbLager.getItems().addAll(Controller.getLagre());
        cbLager.getSelectionModel().select(ingenValgt);
        cbLager.setOnAction(e -> valgtLagerChanged());

        txfReol = createTextField();
        txfHylde = createTextField();
        txfPlads = createTextField();

        txaPladsBeskrivelse = createTextArea();

        Button btnOpretLagerplads = createButton("Opret lagerplads");
        btnOpretLagerplads.setOnAction(e -> gemLagerPlads());

        pladsBox.getChildren().addAll(
                lblPladsTitle,
                createLabel("Vælg lager:"),
                cbLager,
                createLabel("Reol nr:"),
                txfReol,
                createLabel("Hylde nr:"),
                txfHylde,
                createLabel("Plads nr:"),
                txfPlads,
                createLabel("Beskrivelse:"),
                txaPladsBeskrivelse,
                btnOpretLagerplads
        );

        this.add(pladsBox, 1, 1);

        VBox oversigtBox = createBox();
        Label lblLagerpladser = createSectionLabel("Lagerpladser");

        lvwPladser = new ListView<>();
        lvwPladser.setPrefWidth(280);
        lvwPladser.setPrefHeight(390);
        lvwPladser.setStyle(
                "-fx-control-inner-background: #f5e6d0;" +
                        "-fx-font-size: 13px;"
        );

        oversigtBox.getChildren().addAll(lblLagerpladser, lvwPladser);

        this.add(oversigtBox, 2, 1);

        disableLagerpladsFields(true);
    }

    private VBox createBox() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
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
                "-fx-font-size: 20px;" +
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

    private TextField createTextField() {
        TextField tf = new TextField();
        tf.setPrefWidth(240);
        tf.setStyle(
                "-fx-background-radius: 10;" +
                        "-fx-font-size: 13px;"
        );
        return tf;
    }

    private TextArea createTextArea() {
        TextArea ta = new TextArea();
        ta.setPrefWidth(240);
        ta.setPrefHeight(70);
        ta.setStyle(
                "-fx-background-radius: 10;" +
                        "-fx-font-size: 13px;"
        );
        return ta;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(220);
        button.setPrefHeight(38);
        button.setStyle(
                "-fx-background-color: #b88746;" +
                        "-fx-text-fill: #1f120a;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;"
        );
        return button;
    }

    private void gemLager() {
        if (txfLagerNavn.getText().trim().isEmpty() ||
                txfAdresse.getText().trim().isEmpty() ||
                txfKapacitet.getText().trim().isEmpty()) {

            visAlert("Udfyld alle lagerfelter.");
            return;
        }

        try {
            String navn = txfLagerNavn.getText().trim();
            String adresse = txfAdresse.getText().trim();
            int id = Integer.parseInt(txfKapacitet.getText().trim());
            String beskrivelse = txaLagerBeskrivelse.getText().trim();

            Controller.createLager(id, navn, adresse, beskrivelse);

            opdaterComboBox();

            txfLagerNavn.clear();
            txfAdresse.clear();
            txfKapacitet.clear();
            txaLagerBeskrivelse.clear();

            visAlert("Lager oprettet.");

        } catch (NumberFormatException e) {
            visAlert("Lager ID skal være et tal.");
        }
    }

    private void gemLagerPlads() {
        Lager lager = cbLager.getValue();

        if (lager == null || lager == ingenValgt) {
            visAlert("Vælg et lager.");
            return;
        }

        try {
            int reolNr = Integer.parseInt(txfReol.getText().trim());
            int hyldeNr = Integer.parseInt(txfHylde.getText().trim());
            int pladsNr = Integer.parseInt(txfPlads.getText().trim());
            String beskrivelse = txaPladsBeskrivelse.getText().trim();
            lager.createLagerPlads(reolNr, hyldeNr, pladsNr, beskrivelse);
            lvwPladser.getItems().setAll(lager.getLagerPladser());
            txfReol.clear();
            txfHylde.clear();
            txfPlads.clear();
            txaPladsBeskrivelse.clear();

            visAlert("Lagerplads oprettet.");

        } catch (NumberFormatException e) {
            visAlert("Reol, hylde og plads skal være tal.");
        }
    }

    private void valgtLagerChanged() {
        Lager valgt = cbLager.getValue();

        if (valgt == null || valgt == ingenValgt) {
            disableLagerpladsFields(true);
            lvwPladser.getItems().clear();
            return;
        }

        disableLagerpladsFields(false);
        lvwPladser.getItems().setAll(valgt.getLagerPladser());
    }

    private void opdaterComboBox() {
        cbLager.getItems().clear();

        cbLager.getItems().add(ingenValgt);
        cbLager.getItems().addAll(Controller.getLagre());

        cbLager.getSelectionModel().select(ingenValgt);
    }

    private void disableLagerpladsFields(boolean disable) {
        txfReol.setDisable(disable);
        txfHylde.setDisable(disable);
        txfPlads.setDisable(disable);
        txaPladsBeskrivelse.setDisable(disable);
    }

    private void visAlert(String besked) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(besked);
        alert.showAndWait();
    }
}