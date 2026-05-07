package GUI;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistreDES extends GridPane {

    private TextField txfStartdato, txfSlutdato, txfMaltBatch, txfKornsort,
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

        Label lblStart = new Label("Startdato:");
        this.add(lblStart, 0, 0);
        txfStartdato = new TextField();
        txfStartdato.setPromptText("dd.mm.åååå");
        this.add(txfStartdato, 1, 0);

        Label lblslut = new Label("Slutdato:");
        this.add(lblslut,0,1);
        txfSlutdato = new TextField();
        txfSlutdato.setPromptText("dd.mm.åååå");
        this.add(txfSlutdato,1,1);

        Label lblBatch = new Label("Malt batch:");
        this.add(lblBatch, 0, 2);
        txfMaltBatch = new TextField();
        txfMaltBatch.setPromptText("F.eks. MB-2026-04");
        this.add(txfMaltBatch, 1, 2);

        Label lblKorn = new Label("Kornsort:");
        this.add(lblKorn, 0, 3);
        txfKornsort = new TextField();
        txfKornsort.setPromptText("Byg, rug, hvede");
        this.add(txfKornsort, 1, 3);

        Label lblMaengde = new Label("Mængde (liter):");
        this.add(lblMaengde, 0, 4);
        txfMaengde = new TextField();
        this.add(txfMaengde, 1, 4);

        Label lblAlkohol = new Label("Alkohol %:");
        this.add(lblAlkohol, 0, 5);
        txfAlkohol = new TextField();
        this.add(txfAlkohol, 1, 5);

        Label lblRyg = new Label("Rygemateriale:");
        this.add(lblRyg, 0, 6);
        txfRygemateriale = new TextField();
        txfRygemateriale.setPromptText("Tørv, bærtræ...");
        this.add(txfRygemateriale, 1, 6);

        Label lblKommentar = new Label("Kommentar:");
        this.add(lblKommentar, 0, 7);
        txaKommentar = new TextArea();
        txaKommentar.setPrefRowCount(4);
        this.add(txaKommentar, 1, 7);

        Button btnSave = new Button("Gem destillering");
        btnSave.setOnAction(e -> gemDestillering());
        this.add(btnSave, 1, 8);
    }

    private void gemDestillering(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate startdato = LocalDate.parse(txfStartdato.getText().trim(), formatter);
        LocalDate slutdato = LocalDate.parse(txfSlutdato.getText().trim(), formatter);
        int maltBatch = Integer.parseInt(txfMaltBatch.getText());
        String kornsort = txfKornsort.getText().trim();
        double maengde = Double.parseDouble(txfMaengde.getText().trim());
        double alkohol = Double.parseDouble(txfAlkohol.getText().trim());
        String rygemateriale = txfRygemateriale.getText().trim();
        String kommentar = txaKommentar.getText().trim();

        Controller.createDestillering(startdato, slutdato, maltBatch,
                kornsort,maengde,alkohol, rygemateriale,kommentar);
    }
}
