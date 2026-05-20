package GUI;

import Controller.Controller;
import Model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WhiskyPane extends GridPane {

    //Brugt AI til at gøre det pænt så det var lidt mere behageligt at kigge på :), men ikk ebrugt det til andet.

    private TextField txfId, txfNavn, txfBeskrivelse, txfAlkohol, txfVand;
    private TextField txfWhiskyMængde, txfAntalFlasker, txfFlaskeVolumen;
    private ComboBox<WhiskyProdukt> cbWhiskyProdukt;
    private ComboBox<Fad> cbFad;
    private ListView<WhiskyMængde> lvwWhiskyMængder;
    private ListView<Flaske> lvwFlasker;
    private TextArea txaHistorik;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Opret whiskyprodukt");

        initContent();

        Scene scene = new Scene(this, 1300, 720);
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

        ColumnConstraints c4 = new ColumnConstraints();
        c4.setMinWidth(320);

        this.getColumnConstraints().addAll(c1, c2, c3, c4);

        Label lblTitle = new Label("Opret whiskyprodukt");
        lblTitle.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #f3d7a3;"
        );
        this.add(lblTitle, 0, 0, 4, 1);

        VBox produktBox = createBox();

        Label lblProduktTitle = createSectionLabel("Nyt whiskyprodukt");

        txfId = createTextField();
        txfNavn = createTextField();
        txfBeskrivelse = createTextField();
        txfAlkohol = createTextField();
        txfVand = createTextField();

        Button btnOpretWhisky = createButton("Opret whiskyprodukt");
        btnOpretWhisky.setOnAction(e -> opretWhiskyProdukt());

        produktBox.getChildren().addAll(lblProduktTitle, createLabel("ID:"), txfId, createLabel("Navn:"), txfNavn, createLabel("Beskrivelse:"), txfBeskrivelse, createLabel("Alkohol %:"), txfAlkohol, createLabel("Vandmængde:"), txfVand, btnOpretWhisky);

        this.add(produktBox, 0, 1);

        VBox mængdeBox = createBox();

        Label lblMængdeTitle = createSectionLabel("Tilføj whisky fra fad");

        cbWhiskyProdukt = new ComboBox<>();
        cbWhiskyProdukt.setPrefWidth(250);
        cbWhiskyProdukt.setOnAction(e -> opdaterValgtWhiskyProdukt());

        cbFad = new ComboBox<>();
        cbFad.setPrefWidth(250);

        txfWhiskyMængde = createTextField();

        Button btnTilføjMængde = createButton("Tilføj mængde fra fad");
        btnTilføjMængde.setOnAction(e -> tilføjWhiskyMængde());

        mængdeBox.getChildren().addAll(lblMængdeTitle, createLabel("Vælg whiskyprodukt:"), cbWhiskyProdukt, createLabel("Vælg fad:"), cbFad, createLabel("Mængde fra fad:"), txfWhiskyMængde, btnTilføjMængde);

        this.add(mængdeBox, 1, 1);

        VBox flaskerBox = createBox();

        Label lblFlaskerTitle = createSectionLabel("Registrer flasker");

        txfAntalFlasker = createTextField();
        txfFlaskeVolumen = createTextField();
        txfFlaskeVolumen.setText("0.7");

        Button btnOpretFlasker = createButton("Opret flasker");
        btnOpretFlasker.setOnAction(e -> opretFlasker());

        flaskerBox.getChildren().addAll(lblFlaskerTitle, createLabel("Antal flasker:"), txfAntalFlasker, createLabel("Flaskevolumen:"), txfFlaskeVolumen, btnOpretFlasker);

        this.add(flaskerBox, 2, 1);

        VBox oversigtBox = createBox();

        Label lblOversigtTitle = createSectionLabel("Oversigt");

        lvwWhiskyMængder = new ListView<>();
        lvwWhiskyMængder.setPrefWidth(280);
        lvwWhiskyMængder.setPrefHeight(160);
        lvwWhiskyMængder.setStyle(
                "-fx-control-inner-background: #f5e6d0;" +
                        "-fx-font-size: 13px;"
        );

        lvwFlasker = new ListView<>();
        lvwFlasker.setPrefWidth(280);
        lvwFlasker.setPrefHeight(160);
        lvwFlasker.setStyle(
                "-fx-control-inner-background: #f5e6d0;" +
                        "-fx-font-size: 13px;"
        );

        Button btnVisHistorik = createButton("Vis historik");
        btnVisHistorik.setOnAction(e -> visHistorik());

        oversigtBox.getChildren().addAll(lblOversigtTitle, createLabel("Whiskymængder:"), lvwWhiskyMængder, createLabel("Flasker:"), lvwFlasker, btnVisHistorik);

        this.add(oversigtBox, 3, 1);

        txaHistorik = new TextArea();
        txaHistorik.setEditable(false);
        txaHistorik.setPrefHeight(180);
        txaHistorik.setStyle(
                "-fx-control-inner-background: #f5e6d0;" +
                        "-fx-font-size: 13px;"
        );

        this.add(txaHistorik, 0, 2, 4, 1);

        updateContent();
    }

    private void updateContent() {
        WhiskyProdukt valgtProdukt = cbWhiskyProdukt.getValue();
        Fad valgtFad = cbFad.getValue();

        cbWhiskyProdukt.getItems().setAll(Controller.getWhiskyProdukter());
        cbFad.getItems().setAll(Controller.getFade());

        cbWhiskyProdukt.setValue(valgtProdukt);
        cbFad.setValue(valgtFad);
    }

    private void opretWhiskyProdukt() {
        try {
            int id = Integer.parseInt(txfId.getText().trim());
            String navn = txfNavn.getText().trim();
            String beskrivelse = txfBeskrivelse.getText().trim();
            double alkohol = Double.parseDouble(txfAlkohol.getText().trim());
            double vand = Double.parseDouble(txfVand.getText().trim());

            WhiskyProdukt whiskyProdukt = Controller.createWhiskyProdukt(id, navn, beskrivelse, alkohol, vand);

            updateContent();
            cbWhiskyProdukt.setValue(whiskyProdukt);
            opdaterValgtWhiskyProdukt();

            txfId.clear();
            txfNavn.clear();
            txfBeskrivelse.clear();
            txfAlkohol.clear();
            txfVand.clear();

            visAlert("Whiskyprodukt oprettet.");

        } catch (NumberFormatException e) {
            visAlert("ID, alkoholprocent og vandmængde skal være tal.");
        } catch (IllegalArgumentException e) {
            visAlert(e.getMessage());
        }
    }

    private void tilføjWhiskyMængde() {
        WhiskyProdukt whiskyProdukt = cbWhiskyProdukt.getValue();
        Fad fad = cbFad.getValue();

        if (whiskyProdukt == null || fad == null || txfWhiskyMængde.getText().trim().isEmpty()) {
            visAlert("Vælg whiskyprodukt, fad og indtast mængde.");
            return;
        }

        try {
            double mængde = Double.parseDouble(txfWhiskyMængde.getText().trim());

            Controller.createWhiskyMængde(whiskyProdukt, fad, mængde);

            updateContent();
            cbWhiskyProdukt.setValue(whiskyProdukt);
            cbFad.setValue(fad);
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

            updateContent();
            cbWhiskyProdukt.setValue(whiskyProdukt);
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

        txaHistorik.setText(whiskyProdukt.getHistorik());
    }

    private void visHistorik() {
        WhiskyProdukt whiskyProdukt = cbWhiskyProdukt.getValue();

        if (whiskyProdukt == null) {
            visAlert("Vælg et whiskyprodukt først.");
            return;
        }

        txaHistorik.setText(whiskyProdukt.getHistorik());
    }

    private VBox createBox() {
        VBox box = new VBox(9);
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
        tf.setPrefWidth(250);
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