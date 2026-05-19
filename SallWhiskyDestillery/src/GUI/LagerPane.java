package GUI;

import Controller.Controller;
import Model.Lager;
import Model.LagerPlads;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LagerPane extends GridPane {

    private Lager ingenValgt;

    private TextField txfLagerNavn, txfAdresse, txfKapacitet;
    private TextArea txaLagerBeskrivelse;

    private ComboBox<Lager> cbLager;
    private TextField txfReol, txfHylde, txfPlads;
    private TextArea txaPladsBeskrivelse;

    private ListView<LagerPlads> lvwPladser;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Registrer Lager & Lagerplads");

        initContent();

        Scene scene = new Scene(this, 900, 450);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(12);

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPrefWidth(120);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(180);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(120);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(180);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPrefWidth(250);

        this.getColumnConstraints().addAll(col0, col1, col2, col3, col4);

        // Lager
        Label lblLagerTitle = new Label("Registrer Lager");
        lblLagerTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblLagerTitle, 1, 0);

        this.add(new Label("Lagernavn:"), 0, 1);
        txfLagerNavn = new TextField();
        this.add(txfLagerNavn, 1, 1);

        this.add(new Label("Adresse:"), 0, 2);
        txfAdresse = new TextField();
        this.add(txfAdresse, 1, 2);

        this.add(new Label("Lager ID:"), 0, 3);
        txfKapacitet = new TextField();
        this.add(txfKapacitet, 1, 3);

        this.add(new Label("Beskrivelse:"), 0, 4);
        txaLagerBeskrivelse = new TextArea();
        txaLagerBeskrivelse.setPrefRowCount(3);
        this.add(txaLagerBeskrivelse, 1, 4);

        Button btnSaveLager = new Button("Opret Lager");
        this.add(btnSaveLager, 1, 5);
        btnSaveLager.setOnAction(e -> gemLager());

        // Lagerplads
        Label lblPladsTitle = new Label("Registrer Lagerplads");
        lblPladsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblPladsTitle, 3, 0);

        this.add(new Label("Vælg Lager:"), 2, 1);

        cbLager = new ComboBox<>();
        cbLager.setPrefWidth(200);

        ingenValgt = new Lager(0, "Ikke valgt", "", "");

        cbLager.getItems().add(ingenValgt);
        cbLager.getItems().addAll(Controller.getLagre());

        cbLager.getSelectionModel().select(ingenValgt);

        this.add(cbLager, 3, 1);

        cbLager.setOnAction(e -> valgtLagerChanged());

        this.add(new Label("Reol nr:"), 2, 2);
        txfReol = new TextField();
        this.add(txfReol, 3, 2);

        this.add(new Label("Hylde nr:"), 2, 3);
        txfHylde = new TextField();
        this.add(txfHylde, 3, 3);

        this.add(new Label("Plads nr:"), 2, 4);
        txfPlads = new TextField();
        this.add(txfPlads, 3, 4);

        this.add(new Label("Beskrivelse:"), 2, 5);
        txaPladsBeskrivelse = new TextArea();
        txaPladsBeskrivelse.setPrefRowCount(3);
        this.add(txaPladsBeskrivelse, 3, 5);

        Button btnOpretLagerplads = new Button("Opret lagerplads");
        this.add(btnOpretLagerplads, 3, 6);
        btnOpretLagerplads.setOnAction(e -> gemLagerPlads());

        Label lblLagerpladser = new Label("Lagerpladser");
        lblLagerpladser.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblLagerpladser, 4, 0);

        lvwPladser = new ListView<>();
        lvwPladser.setPrefWidth(250);
        lvwPladser.setPrefHeight(250);
        this.add(lvwPladser, 4, 1, 1, 6);

        disableLagerpladsFields(true);
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