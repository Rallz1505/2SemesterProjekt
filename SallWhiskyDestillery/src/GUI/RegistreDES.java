package GUI;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class RegistreDES extends GridPane {

    private DatePicker dpStartdato, dpSlutdato;

    private TextField txfMaltBatch, txfKornsort,
            txfMaengde, txfAlkohol, txfRygemateriale;

    private TextArea txaKommentar;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Registrer destillering");

        initContent();

        Scene scene = new Scene(this, 450, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(12);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);

        this.getColumnConstraints().addAll(col1, col2);

        this.add(new Label("Startdato:"), 0, 0);
        dpStartdato = new DatePicker();
        this.add(dpStartdato, 1, 0);

        this.add(new Label("Slutdato:"), 0, 1);
        dpSlutdato = new DatePicker();
        this.add(dpSlutdato, 1, 1);

        this.add(new Label("Malt batch:"), 0, 2);
        txfMaltBatch = new TextField();
        this.add(txfMaltBatch, 1, 2);

        this.add(new Label("Kornsort:"), 0, 3);
        txfKornsort = new TextField();
        this.add(txfKornsort, 1, 3);

        this.add(new Label("Mængde (liter):"), 0, 4);
        txfMaengde = new TextField();
        this.add(txfMaengde, 1, 4);

        this.add(new Label("Alkohol %:"), 0, 5);
        txfAlkohol = new TextField();
        this.add(txfAlkohol, 1, 5);

        this.add(new Label("Rygemateriale:"), 0, 6);
        txfRygemateriale = new TextField();
        this.add(txfRygemateriale, 1, 6);

        this.add(new Label("Kommentar:"), 0, 7);
        txaKommentar = new TextArea();
        txaKommentar.setPrefRowCount(4);
        this.add(txaKommentar, 1, 7);

        Button btnSave = new Button("Gem destillering");
        this.add(btnSave, 1, 8);
        btnSave.setOnAction(e -> gemDestillering());
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

            Controller.createDestillering(
                    startdato,
                    slutdato,
                    maltBatch,
                    kornsort,
                    maengde,
                    alkohol,
                    rygemateriale,
                    kommentar
            );

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