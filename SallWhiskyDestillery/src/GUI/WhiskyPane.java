package GUI;

import Controller.Controller;
import Model.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class WhiskyPane extends GridPane {

    private TextField txfId, txfNavn, txfBeskrivelse, txfAlkohol, txfVand;
    private TextField txfWhiskyMængde, txfAntalFlasker, txfFlaskeVolumen;

    private ComboBox<WhiskyProdukt> cbWhiskyProdukt;
    private ComboBox<Påfyldning> cbPåfyldning;

    private ListView<WhiskyMængde> lvwWhiskyMængder;
    private ListView<Flaske> lvwFlasker;

    private TextArea txaHistorik;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Opret whiskyprodukt");

        initContent();

        Scene scene = new Scene(this, 1100, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {
        this.setPadding(new Insets(20));
        this.setHgap(15);
        this.setVgap(10);

        // Opret whiskyprodukt
        this.add(new Label("Opret whiskyprodukt"), 0, 0);

        this.add(new Label("ID:"), 0, 1);
        txfId = new TextField();
        this.add(txfId, 1, 1);

        this.add(new Label("Navn:"), 0, 2);
        txfNavn = new TextField();
        this.add(txfNavn, 1, 2);

        this.add(new Label("Beskrivelse:"), 0, 3);
        txfBeskrivelse = new TextField();
        this.add(txfBeskrivelse, 1, 3);

        this.add(new Label("Alkohol %:"), 0, 4);
        txfAlkohol = new TextField();
        this.add(txfAlkohol, 1, 4);

        this.add(new Label("Vandmængde:"), 0, 5);
        txfVand = new TextField();
        this.add(txfVand, 1, 5);

        Button btnOpretWhisky = new Button("Opret whiskyprodukt");
        this.add(btnOpretWhisky, 1, 6);
        btnOpretWhisky.setOnAction(e -> opretWhiskyProdukt());

        // Tilføj whiskymængde
        this.add(new Label("Vælg whiskyprodukt:"), 2, 1);
        cbWhiskyProdukt = new ComboBox<>();
        cbWhiskyProdukt.setPrefWidth(250);
        this.add(cbWhiskyProdukt, 3, 1);
        cbWhiskyProdukt.setOnAction(e -> opdaterValgtWhiskyProdukt());

        this.add(new Label("Vælg påfyldning:"), 2, 2);
        cbPåfyldning = new ComboBox<>();
        cbPåfyldning.setPrefWidth(250);
        this.add(cbPåfyldning, 3, 2);

        this.add(new Label("Mængde fra påfyldning:"), 2, 3);
        txfWhiskyMængde = new TextField();
        this.add(txfWhiskyMængde, 3, 3);

        Button btnTilføjMængde = new Button("Tilføj whiskymængde");
        this.add(btnTilføjMængde, 3, 4);
        btnTilføjMængde.setOnAction(e -> tilføjWhiskyMængde());

        lvwWhiskyMængder = new ListView<>();
        lvwWhiskyMængder.setPrefWidth(300);
        lvwWhiskyMængder.setPrefHeight(180);
        this.add(lvwWhiskyMængder, 2, 5, 2, 4);

        // Flasker
        this.add(new Label("Antal flasker:"), 4, 1);
        txfAntalFlasker = new TextField();
        this.add(txfAntalFlasker, 5, 1);

        this.add(new Label("Flaskevolumen:"), 4, 2);
        txfFlaskeVolumen = new TextField("0.7");
        this.add(txfFlaskeVolumen, 5, 2);

        Button btnOpretFlasker = new Button("Opret flasker");
        this.add(btnOpretFlasker, 5, 3);
        btnOpretFlasker.setOnAction(e -> opretFlasker());

        lvwFlasker = new ListView<>();
        lvwFlasker.setPrefWidth(300);
        lvwFlasker.setPrefHeight(180);
        this.add(lvwFlasker, 4, 5, 2, 4);

        // Historik
        Button btnVisHistorik = new Button("Vis historik");
        this.add(btnVisHistorik, 0, 9);
        btnVisHistorik.setOnAction(e -> visHistorik());

        txaHistorik = new TextArea();
        txaHistorik.setEditable(false);
        txaHistorik.setPrefWidth(650);
        txaHistorik.setPrefHeight(180);
        this.add(txaHistorik, 1, 9, 5, 1);

        updateContent();
    }

    private void updateContent() {
        cbWhiskyProdukt.getItems().setAll(Controller.getWhiskyProdukter());
        cbPåfyldning.getItems().setAll(getAllePåfyldninger());
    }

    private List<Påfyldning> getAllePåfyldninger() {
        List<Påfyldning> påfyldninger = new ArrayList<>();

        for (Fad fad : Controller.getFade()) {
            påfyldninger.addAll(fad.getPåfyldninger());
        }

        return påfyldninger;
    }

    private void opretWhiskyProdukt() {
        try {
            int id = Integer.parseInt(txfId.getText().trim());
            String navn = txfNavn.getText().trim();
            String beskrivelse = txfBeskrivelse.getText().trim();
            double alkohol = Double.parseDouble(txfAlkohol.getText().trim());
            double vand = Double.parseDouble(txfVand.getText().trim());

            WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(
                    id, navn, beskrivelse, alkohol, vand
            );

            updateContent();
            cbWhiskyProdukt.setValue(whiskyProdukt);

            txfId.clear();
            txfNavn.clear();
            txfBeskrivelse.clear();
            txfAlkohol.clear();
            txfVand.clear();

            visAlert("Whiskyprodukt oprettet.");

        } catch (NumberFormatException e) {
            visAlert("ID, alkoholprocent og vandmængde skal være tal.");
        }
    }

    private void tilføjWhiskyMængde() {
        WhiskyProdukt whiskyProdukt = cbWhiskyProdukt.getValue();
        Påfyldning påfyldning = cbPåfyldning.getValue();

        if (whiskyProdukt == null || påfyldning == null || txfWhiskyMængde.getText().trim().isEmpty()) {
            visAlert("Vælg whiskyprodukt, påfyldning og indtast mængde.");
            return;
        }

        try {
            double mængde = Double.parseDouble(txfWhiskyMængde.getText().trim());

            Controller.createWhiskyMængde(whiskyProdukt, påfyldning, mængde);

            opdaterValgtWhiskyProdukt();
            txfWhiskyMængde.clear();

            visAlert("Whiskymængde tilføjet.");

        } catch (NumberFormatException e) {
            visAlert("Mængde skal være et tal.");
        } catch (IllegalArgumentException e) {
            visAlert(e.getMessage());
        }
    }

    private void opretFlasker() {
        WhiskyProdukt whiskyProdukt = cbWhiskyProdukt.getValue();

        if (whiskyProdukt == null) {
            visAlert("Vælg et whiskyprodukt først.");
            return;
        }

        try {
            int antal = Integer.parseInt(txfAntalFlasker.getText().trim());
            double volumen = Double.parseDouble(txfFlaskeVolumen.getText().trim());

            for (int i = 0; i < antal; i++) {
                int flaskeNr = whiskyProdukt.getFlasker().size() + 1;
                Controller.createFlaske(whiskyProdukt, flaskeNr, volumen);
            }

            opdaterValgtWhiskyProdukt();
            txfAntalFlasker.clear();

            visAlert("Flasker oprettet.");

        } catch (NumberFormatException e) {
            visAlert("Antal og volumen skal være tal.");
        } catch (IllegalArgumentException e) {
            visAlert(e.getMessage());
        }
    }

    private void opdaterValgtWhiskyProdukt() {
        WhiskyProdukt whiskyProdukt = cbWhiskyProdukt.getValue();

        if (whiskyProdukt == null) {
            lvwWhiskyMængder.getItems().clear();
            lvwFlasker.getItems().clear();
            txaHistorik.clear();
            return;
        }

        lvwWhiskyMængder.getItems().setAll(whiskyProdukt.getWhiskyMængder());
        lvwFlasker.getItems().setAll(whiskyProdukt.getFlasker());
    }

    private void visHistorik() {
        WhiskyProdukt whiskyProdukt = cbWhiskyProdukt.getValue();

        if (whiskyProdukt == null) {
            visAlert("Vælg et whiskyprodukt først.");
            return;
        }

        txaHistorik.setText(whiskyProdukt.getHistorik());
    }

    private void visAlert(String besked) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(besked);
        alert.showAndWait();
    }
}