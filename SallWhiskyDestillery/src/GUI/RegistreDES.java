package GUI;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class RegistreDES extends GridPane {

    //Brugt AI til at gøre det pænt så det var lidt mere behageligt at kigge på :), men ikk ebrugt det til andet.

    private DatePicker dpStartdato, dpSlutdato;
    private TextField txfMaltBatch, txfKornsort, txfMaengde, txfAlkohol, txfRygemateriale;
    private TextArea txaKommentar;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Registrer destillering");

        initContent();

        Scene scene = new Scene(this, 650, 640);
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
        c1.setMinWidth(560);
        this.getColumnConstraints().add(c1);

        Label lblTitle = new Label("Registrer destillering");
        lblTitle.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #f3d7a3;"
        );
        this.add(lblTitle, 0, 0);

        VBox formBox = createBox();

        Label lblFormTitle = createSectionLabel("Ny destillering");

        dpStartdato = new DatePicker();
        dpStartdato.setPrefWidth(300);

        dpSlutdato = new DatePicker();
        dpSlutdato.setPrefWidth(300);

        txfMaltBatch = createTextField();
        txfKornsort = createTextField();
        txfMaengde = createTextField();
        txfAlkohol = createTextField();
        txfRygemateriale = createTextField();

        txaKommentar = createTextArea();

        Button btnSave = createButton("Gem destillering");
        btnSave.setOnAction(e -> gemDestillering());

        formBox.getChildren().addAll(
                lblFormTitle,

                createLabel("Startdato:"),
                dpStartdato,

                createLabel("Slutdato:"),
                dpSlutdato,

                createLabel("Malt batch:"),
                txfMaltBatch,

                createLabel("Kornsort:"),
                txfKornsort,

                createLabel("Mængde (liter):"),
                txfMaengde,

                createLabel("Alkohol %:"),
                txfAlkohol,

                createLabel("Rygemateriale:"),
                txfRygemateriale,

                createLabel("Kommentar:"),
                txaKommentar,

                btnSave
        );

        this.add(formBox, 0, 1);
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
        tf.setPrefWidth(300);
        tf.setStyle(
                "-fx-background-radius: 10;" +
                        "-fx-font-size: 13px;"
        );
        return tf;
    }

    private TextArea createTextArea() {
        TextArea ta = new TextArea();
        ta.setPrefWidth(300);
        ta.setPrefHeight(75);
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

    private void gemDestillering() {
        if (dpStartdato.getValue() == null ||
                dpSlutdato.getValue() == null ||
                txfMaltBatch.getText().trim().isEmpty() ||
                txfKornsort.getText().trim().isEmpty() ||
                txfMaengde.getText().trim().isEmpty() ||
                txfAlkohol.getText().trim().isEmpty()) {

            visAlert("Udfyld alle nødvendige felter.");
            return;
        }

        try {
            LocalDate startdato = dpStartdato.getValue();
            LocalDate slutdato = dpSlutdato.getValue();

            int maltBatch = Integer.parseInt(txfMaltBatch.getText().trim());
            String kornsort = txfKornsort.getText().trim();
            double maengde = Double.parseDouble(txfMaengde.getText().trim());
            double alkohol = Double.parseDouble(txfAlkohol.getText().trim());
            String rygemateriale = txfRygemateriale.getText().trim();
            String kommentar = txaKommentar.getText().trim();

            Controller.createDestillering(startdato, slutdato, maltBatch, kornsort, maengde, alkohol, rygemateriale, kommentar);

            visAlert("Destillering oprettet.");

            dpStartdato.setValue(null);
            dpSlutdato.setValue(null);
            txfMaltBatch.clear();
            txfKornsort.clear();
            txfMaengde.clear();
            txfAlkohol.clear();
            txfRygemateriale.clear();
            txaKommentar.clear();

        } catch (NumberFormatException e) {
            visAlert("Batchnummer, mængde og alkoholprocent skal være tal.");
        } catch (IllegalArgumentException e) {
            visAlert(e.getMessage());
        }
    }

    private void visAlert(String besked) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(besked);
        alert.showAndWait();
    }
}