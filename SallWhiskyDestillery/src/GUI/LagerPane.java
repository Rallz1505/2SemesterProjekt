package GUI;

import Controller.Controller;
import Model.Lager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LagerPane extends GridPane {
    private TextField txfLagerNavn, txfAdresse, txfKapacitet;
    private TextArea txaLagerBeskrivelse;

    private ComboBox<Lager> cbLager;
    private TextField txfReol, txfHylde, txfPlads;
    private TextArea txaPladsBeskrivelse;

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

        this.getColumnConstraints().addAll(col0, col1, col2, col3);

        //Lagerside

        Label lblLagerTitle = new Label("Registrer Lager");
        lblLagerTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblLagerTitle, 1, 0);

        this.add(new Label("Lagernavn:"), 0, 1);
        txfLagerNavn = new TextField();
        this.add(txfLagerNavn, 1, 1);

        this.add(new Label("Adresse:"), 0, 2);
        txfAdresse = new TextField();
        this.add(txfAdresse, 1, 2);

        this.add(new Label("Kapacitet:"), 0, 3);
        txfKapacitet = new TextField();
        txfKapacitet.setPromptText("Antal fade eller m2");
        this.add(txfKapacitet, 1, 3);

        this.add(new Label("Beskrivelse:"), 0, 4);
        txaLagerBeskrivelse = new TextArea();
        txaLagerBeskrivelse.setPrefRowCount(3);
        this.add(txaLagerBeskrivelse, 1, 4);

        Button btnSaveLager = new Button("Opret Lager");
        this.add(btnSaveLager, 1, 5);
        btnSaveLager.setOnAction(e -> gemLager());



        Label lblVælgLager = new Label("Vælg Lager:");
        this.add(lblVælgLager, 2, 1);

        cbLager = new ComboBox<>();
        cbLager.getItems().addAll(Controller.getLagre());
        cbLager.setPrefWidth(200);
        this.add(cbLager, 3, 1);

        // Lagerplads side

        Label lblPladsTitle = new Label("Registrer Lagerplads");
        lblPladsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblPladsTitle, 3, 0);

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
        this.add(btnOpretLagerplads, 3,6);
        btnOpretLagerplads.setOnAction(e-> gemLagerPlads());
    }



    private void gemLager() {
        String navn = txfLagerNavn.getText().trim();
        String adresse = txfAdresse.getText().trim();
        int id = Integer.parseInt(txfKapacitet.getText().trim());
        String beskrivelse = txaLagerBeskrivelse.getText().trim();

        Lager lager = Controller.createLager(id, navn, adresse, beskrivelse);

        cbLager.getItems().clear();
        cbLager.getItems().addAll(Controller.getLagre());
        cbLager.getSelectionModel().select(lager);

        txfLagerNavn.clear();
        txfAdresse.clear();
        txfKapacitet.clear();
        txaLagerBeskrivelse.clear();


    }
    private void gemLagerPlads(){

    }

}
