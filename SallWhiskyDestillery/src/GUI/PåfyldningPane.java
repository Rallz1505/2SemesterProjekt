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

import java.time.LocalDate;

public class PåfyldningPane extends GridPane {

    //Brugt AI til at gøre det pænt så det var lidt mere behageligt at kigge på :), men ikk ebrugt det til andet.

    private ComboBox<Fad> cbFad;
    private ComboBox<Destillering> cbDestillering;
    private TextField txfMængde, txfAnsvarlig;
    private DatePicker dpDato;
    private ListView<Påfyldning> lvwPåfyldninger;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Registrer påfyldning");

        initContent();

        Scene scene = new Scene(this, 900, 560);
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
        c1.setMinWidth(380);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setMinWidth(380);

        this.getColumnConstraints().addAll(c1, c2);

        Label lblTitle = new Label("Registrer påfyldning");
        lblTitle.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #f3d7a3;"
        );
        this.add(lblTitle, 0, 0, 2, 1);

        VBox formBox = createBox();

        cbFad = new ComboBox<>();
        cbFad.setPrefWidth(300);
        cbFad.setOnAction(e -> visPåfyldningerForFad());

        cbDestillering = new ComboBox<>();
        cbDestillering.setPrefWidth(300);

        txfMængde = createTextField();

        dpDato = new DatePicker();
        dpDato.setPrefWidth(300);

        txfAnsvarlig = createTextField();

        Button btnOpret = createButton("Registrer påfyldning");
        btnOpret.setOnAction(e -> opretPåfyldning());

        formBox.getChildren().addAll(
                createSectionLabel("Ny påfyldning"),

                createLabel("Vælg fad:"),
                cbFad,

                createLabel("Vælg destillering:"),
                cbDestillering,

                createLabel("Mængde (L):"),
                txfMængde,

                createLabel("Dato:"),
                dpDato,

                createLabel("Ansvarlig:"),
                txfAnsvarlig,

                btnOpret
        );

        this.add(formBox, 0, 1);

        VBox listBox = createBox();

        lvwPåfyldninger = new ListView<>();
        lvwPåfyldninger.setPrefWidth(340);
        lvwPåfyldninger.setPrefHeight(390);
        lvwPåfyldninger.setStyle(
                "-fx-control-inner-background: #f5e6d0;" +
                        "-fx-font-size: 13px;"
        );

        listBox.getChildren().addAll(
                createSectionLabel("Påfyldninger på valgt fad"),
                lvwPåfyldninger
        );

        this.add(listBox, 1, 1);

        updateContent();
    }

    private void opretPåfyldning() {
        Fad fad = cbFad.getValue();
        Destillering destillering = cbDestillering.getValue();
        LocalDate dato = dpDato.getValue();
        String ansvarlig = txfAnsvarlig.getText().trim();

        if (fad == null || destillering == null || dato == null ||
                txfMængde.getText().trim().isEmpty() || ansvarlig.isEmpty()) {

            visAlert("Udfyld alle felter.");
            return;
        }

        try {
            double mængde = Double.parseDouble(txfMængde.getText().trim());

            VæskeMængde væskeMængde = new VæskeMængde(mængde, destillering);

            Controller.createPåfyldning(dato, ansvarlig, væskeMængde, fad);

            updateContent();
            cbFad.setValue(fad);
            cbDestillering.setValue(destillering);
            visPåfyldningerForFad();
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

    private void updateContent() {
        Fad valgtFad = cbFad.getValue();
        Destillering valgtDestillering = cbDestillering.getValue();

        cbFad.getItems().setAll(Controller.getFade());
        cbDestillering.getItems().setAll(Controller.getDestilleringer());

        cbFad.setValue(valgtFad);
        cbDestillering.setValue(valgtDestillering);

        visPåfyldningerForFad();
    }

    private void visPåfyldningerForFad() {
        Fad fad = cbFad.getValue();

        if (fad == null) {
            lvwPåfyldninger.getItems().clear();
            return;
        }

        lvwPåfyldninger.getItems().setAll(fad.getPåfyldninger());
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
        tf.setPrefWidth(300);
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