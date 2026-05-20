package GUI;

import Controller.Controller;
import Model.Leverandør;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrerFadPane extends GridPane {

    //Brugt AI til at gøre det pænt så det var lidt mere behageligt at kigge på :), men ikk ebrugt det til andet.

    private TextField txfId, txfStorrelse, txfTidligereIndhold, txfNuvMaengde;
    private ComboBox<Leverandør> cbhLeverandør;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Registrer fad");

        initContent();

        Scene scene = new Scene(this, 650, 540);
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

        Label lblTitle = new Label("Registrer fad");
        lblTitle.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #f3d7a3;"
        );
        this.add(lblTitle, 0, 0);

        VBox formBox = createBox();

        Label lblFormTitle = createSectionLabel("Nyt fad");

        txfId = createTextField();
        txfStorrelse = createTextField();

        cbhLeverandør = new ComboBox<>();
        cbhLeverandør.setPrefWidth(300);
        cbhLeverandør.getItems().addAll(Controller.getLeverandører());

        txfTidligereIndhold = createTextField();
        txfNuvMaengde = createTextField();

        Button btnSave = createButton("Gem fad");
        btnSave.setOnAction(e -> gemFad());

        formBox.getChildren().addAll(
                lblFormTitle,

                createLabel("ID:"),
                txfId,

                createLabel("Størrelse (L):"),
                txfStorrelse,

                createLabel("Leverandør:"),
                cbhLeverandør,

                createLabel("Tidligere indhold:"),
                txfTidligereIndhold,

                createLabel("Nuværende mængde (liter):"),
                txfNuvMaengde,
                btnSave);

        this.add(formBox, 0, 1);
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

    private void gemFad() {
        if (txfId.getText().trim().isEmpty() ||
                txfStorrelse.getText().trim().isEmpty() ||
                txfTidligereIndhold.getText().trim().isEmpty() ||
                txfNuvMaengde.getText().trim().isEmpty()) {

            visAlert("Udfyld alle felter.");
            return;
        }

        try {
            int id = Integer.parseInt(txfId.getText().trim());
            double storrelse = Double.parseDouble(txfStorrelse.getText().trim());
            Leverandør leverandor = cbhLeverandør.getValue();
            String tidligereIndhold = txfTidligereIndhold.getText().trim();
            double nuvMaengde = Double.parseDouble(txfNuvMaengde.getText().trim());

            Controller.createFad(storrelse, id, leverandor, tidligereIndhold, nuvMaengde, null);

            visAlert("Fad oprettet.");

            txfId.clear();
            txfStorrelse.clear();
            txfTidligereIndhold.clear();
            txfNuvMaengde.clear();
            cbhLeverandør.setValue(null);

        } catch (NumberFormatException e) {
            visAlert("ID, størrelse og nuværende mængde skal være tal.");
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