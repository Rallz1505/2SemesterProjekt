package GUI;

import Controller.Controller;
import Model.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FadoversigtPane extends GridPane {

    private TextField txfSøgId, txfMængde, txfAnsvarlig;
    private ListView<Fad> lvwFade;
    private ListView<Påfyldning> lvwPåfyldninger;
    private ComboBox<String> cbIndhold;
    private ComboBox<Fad> cbFade;
    private ComboBox<Destillering> cbDest;
    private DatePicker dpDato;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Fadoversigt og påfyldning");

        initContent();

        Scene scene = new Scene(this, 1100, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        // Fadoversigt
        this.add(new Label("Fadoversigt"), 0, 0);

        this.add(new Label("Søg efter fad ID:"), 0, 1);
        txfSøgId = new TextField();
        this.add(txfSøgId, 0, 2);

        Button btnSøgId = new Button("Søg ID");
        this.add(btnSøgId, 1, 2);
        btnSøgId.setOnAction(e -> soegFad());

        this.add(new Label("Søg efter tidligere indhold:"), 0, 3);
        cbIndhold = new ComboBox<>();
        cbIndhold.getItems().addAll("BOURBON", "SHERRY", "RØDVIN", "PORTVIN");
        this.add(cbIndhold, 0, 4);

        Button btnSøgIndhold = new Button("Søg indhold");
        this.add(btnSøgIndhold, 1, 4);
        btnSøgIndhold.setOnAction(e -> soegTidligereIndhold());

        Button btnVisAlle = new Button("Vis alle fade");
        this.add(btnVisAlle, 0, 5);
        btnVisAlle.setOnAction(e -> visAlle());

        Button btnVisTomme = new Button("Vis tomme fade");
        this.add(btnVisTomme, 0, 6);
        btnVisTomme.setOnAction(e -> visTomme());

        Button btnVisFyldte = new Button("Vis fyldte fade");
        this.add(btnVisFyldte, 0, 7);
        btnVisFyldte.setOnAction(e -> visFyldte());

        Button btnVisKlar = new Button("Vis fade klar til aftapning");
        this.add(btnVisKlar, 0, 8);
        btnVisKlar.setOnAction(e -> visKlarTilAftapning());

        Button btnVisBeskrivelse = new Button("Vis beskrivelse");
        this.add(btnVisBeskrivelse, 0, 9);
        btnVisBeskrivelse.setOnAction(e -> visBeskrivelse());

        lvwFade = new ListView<>();
        lvwFade.setPrefWidth(420);
        lvwFade.setPrefHeight(500);
        this.add(lvwFade, 2, 1, 1, 12);

        lvwFade.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cbFade.setValue(newVal);
                visPåfyldningerForFad();
            }
        });

        // Påfyldning
        this.add(new Label("Registrer påfyldning"), 3, 0);

        this.add(new Label("Vælg fad:"), 3, 1);
        cbFade = new ComboBox<>();
        cbFade.setPrefWidth(250);
        this.add(cbFade, 3, 2);
        cbFade.setOnAction(e -> visPåfyldningerForFad());

        this.add(new Label("Vælg destillering:"), 3, 3);
        cbDest = new ComboBox<>();
        cbDest.setPrefWidth(250);
        this.add(cbDest, 3, 4);

        this.add(new Label("Mængde (L):"), 3, 5);
        txfMængde = new TextField();
        this.add(txfMængde, 3, 6);

        this.add(new Label("Dato:"), 3, 7);
        dpDato = new DatePicker();
        this.add(dpDato, 3, 8);

        this.add(new Label("Ansvarlig:"), 3, 9);
        txfAnsvarlig = new TextField();
        this.add(txfAnsvarlig, 3, 10);

        Button btnOpretPåfyldning = new Button("Registrer påfyldning");
        this.add(btnOpretPåfyldning, 3, 11);
        btnOpretPåfyldning.setOnAction(e -> opretPåfyldning());

        this.add(new Label("Påfyldninger for valgt fad:"), 4, 0);

        lvwPåfyldninger = new ListView<>();
        lvwPåfyldninger.setPrefWidth(350);
        lvwPåfyldninger.setPrefHeight(500);
        this.add(lvwPåfyldninger, 4, 1, 1, 12);

        updateContent();
    }

    private void updateContent() {
        lvwFade.getItems().setAll(Controller.getFade());
        cbFade.getItems().setAll(Controller.getFade());
        cbDest.getItems().setAll(Controller.getDestilleringer());
    }

    private void soegFad() {
        String tekst = txfSøgId.getText().trim();

        if (tekst.isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(tekst);
            Fad fad = Controller.findFad(id);

            if (fad != null) {
                lvwFade.getItems().setAll(fad);
            } else {
                lvwFade.getItems().clear();
                visAlert("Der findes ikke et fad med ID: " + id);
            }

        } catch (NumberFormatException e) {
            visAlert("Indtast et gyldigt tal.");
        }
    }

    private void soegTidligereIndhold() {
        String valgt = cbIndhold.getValue();

        if (valgt == null) {
            return;
        }

        List<Fad> resultat = new ArrayList<>();

        for (Fad f : Controller.getFade()) {
            if (f.getTidligereIndhold().equalsIgnoreCase(valgt)) {
                resultat.add(f);
            }
        }

        lvwFade.getItems().setAll(resultat);
    }

    private void visBeskrivelse() {
        Fad valgt = lvwFade.getSelectionModel().getSelectedItem();

        if (valgt == null) {
            visAlert("Vælg et fad først.");
            return;
        }

        String placering = "Ingen placering";

        if (valgt.getLagerPlads() != null) {
            placering = valgt.getLagerPlads().getPlacering();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fad beskrivelse");
        alert.setHeaderText(valgt.toString());
        alert.setContentText(
                "Tidligere indhold: " + valgt.getTidligereIndhold() +
                        "\nNuværende mængde: " + valgt.getNuværendeMængde() + " L" +
                        "\nLedig kapacitet: " + valgt.ledigKapacitet() + " L" +
                        "\nPlacering: " + placering
        );
        alert.showAndWait();
    }

    private void visTomme() {
        List<Fad> resultat = new ArrayList<>();

        for (Fad f : Controller.getFade()) {
            if (f.getNuværendeMængde() == 0) {
                resultat.add(f);
            }
        }

        lvwFade.getItems().setAll(resultat);
    }

    private void visFyldte() {
        List<Fad> resultat = new ArrayList<>();

        for (Fad f : Controller.getFade()) {
            if (f.getNuværendeMængde() > 0) {
                resultat.add(f);
            }
        }

        lvwFade.getItems().setAll(resultat);
    }

    private void visAlle() {
        lvwFade.getItems().setAll(Controller.getFade());
    }

    private void visKlarTilAftapning() {
        try {
            lvwFade.getItems().setAll(Controller.getFadeKlarTilAftapning());
        } catch (IllegalArgumentException e) {
            lvwFade.getItems().clear();
            visAlert(e.getMessage());
        }
    }

    private void opretPåfyldning() {
        Fad fad = cbFade.getValue();
        Destillering dest = cbDest.getValue();
        String mængdeTxt = txfMængde.getText().trim();
        LocalDate dato = dpDato.getValue();
        String ansvarlig = txfAnsvarlig.getText().trim();

        if (fad == null || dest == null || mængdeTxt.isEmpty() || dato == null || ansvarlig.isEmpty()) {
            visAlert("Udfyld venligst alle felter.");
            return;
        }

        try {
            double mængde = Double.parseDouble(mængdeTxt);
            VæskeMængde vm = new VæskeMængde(mængde, dest);

            Controller.createPåfyldning(dato, ansvarlig, vm, fad);

            lvwPåfyldninger.getItems().setAll(fad.getPåfyldninger());
            lvwFade.refresh();

            txfMængde.clear();
            txfAnsvarlig.clear();
            dpDato.setValue(null);

            visAlert("Påfyldning registreret.");

        } catch (NumberFormatException e) {
            visAlert("Mængde skal være et tal.");
        } catch (IllegalArgumentException e) {
            visAlert(e.getMessage());
        }
    }

    private void visPåfyldningerForFad() {
        Fad fad = cbFade.getValue();

        if (fad == null) {
            lvwPåfyldninger.getItems().clear();
            return;
        }

        lvwPåfyldninger.getItems().setAll(fad.getPåfyldninger());
    }

    private void visAlert(String besked) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(besked);
        alert.showAndWait();
    }
}