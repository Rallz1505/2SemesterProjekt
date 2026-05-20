package GUI;

import Controller.Controller;
import Model.Fad;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FadoversigtPane extends GridPane {

    //Brugt AI til at gøre det pænt så det var lidt mere behageligt at kigge på :), men ikk ebrugt det til andet.

    private TextField txfSøgId;
    private ListView<Fad> lvwFade;
    private ComboBox<String> cbIndhold;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Fadoversigt");

        initContent();

        Scene scene = new Scene(this, 950, 620);
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
        c1.setMinWidth(280);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setMinWidth(580);

        this.getColumnConstraints().addAll(c1, c2);

        Label lblTitle = new Label("Fadoversigt");
        lblTitle.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #f3d7a3;"
        );
        this.add(lblTitle, 0, 0, 2, 1);

        VBox filterBox = createBox();

        txfSøgId = createTextField();

        Button btnSøgId = createButton("Søg ID");
        btnSøgId.setOnAction(e -> soegFad());

        cbIndhold = new ComboBox<>();
        cbIndhold.setPrefWidth(220);
        cbIndhold.getItems().addAll("BOURBON", "SHERRY", "RØDVIN", "PORTVIN");

        Button btnSøgIndhold = createButton("Søg indhold");
        btnSøgIndhold.setOnAction(e -> soegTidligereIndhold());

        Button btnVisAlle = createButton("Vis alle fade");
        btnVisAlle.setOnAction(e -> visAlle());

        Button btnVisTomme = createButton("Vis tomme fade");
        btnVisTomme.setOnAction(e -> visTomme());

        Button btnVisFyldte = createButton("Vis fyldte fade");
        btnVisFyldte.setOnAction(e -> visFyldte());

        Button btnVisKlar = createButton("Klar til aftapning");
        btnVisKlar.setOnAction(e -> visKlarTilAftapning());

        Button btnVisBeskrivelse = createButton("Vis beskrivelse");
        btnVisBeskrivelse.setOnAction(e -> visBeskrivelse());

        filterBox.getChildren().addAll(
                createSectionLabel("Filtrering"),
                createLabel("Søg efter fad ID:"),
                txfSøgId,
                btnSøgId,

                createLabel("Tidligere indhold:"),
                cbIndhold,
                btnSøgIndhold,

                btnVisAlle,
                btnVisTomme,
                btnVisFyldte,
                btnVisKlar,
                btnVisBeskrivelse
        );

        this.add(filterBox, 0, 1);

        VBox listBox = createBox();

        lvwFade = new ListView<>();
        lvwFade.setPrefWidth(540);
        lvwFade.setPrefHeight(470);
        lvwFade.setStyle(
                "-fx-control-inner-background: #f5e6d0;" +
                        "-fx-font-size: 13px;"
        );

        listBox.getChildren().addAll(
                createSectionLabel("Fade"),
                lvwFade
        );

        this.add(listBox, 1, 1);

        updateContent();
    }

    private void updateContent() {
        lvwFade.getItems().setAll(Controller.getFade());
        lvwFade.refresh();
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
        updateContent();
    }

    private void visKlarTilAftapning() {
        try {
            lvwFade.getItems().setAll(Controller.getFadeKlarTilAftapning());
        } catch (IllegalArgumentException e) {
            lvwFade.getItems().clear();
            visAlert(e.getMessage());
        }
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
                        "\nPlacering: " + placering +
                        "\nAntal påfyldninger: " + valgt.getPåfyldninger().size()
        );
        alert.showAndWait();
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
        tf.setPrefWidth(220);
        tf.setStyle(
                "-fx-background-radius: 10;" +
                        "-fx-font-size: 13px;"
        );
        return tf;
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

    private void visAlert(String besked) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(besked);
        alert.showAndWait();
    }
}