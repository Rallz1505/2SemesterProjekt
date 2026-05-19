package GUI;

import Controller.Controller;
import Model.Leverandør;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LeverandørPane extends GridPane {

    private TextField txfId, txfNavn, txfLand, txfKontaktPerson, txfTelefon, txfEmail;
    private TextArea txaKommentar;
    private ListView<Leverandør> lvwLeverandører;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Registrer leverandør");

        initContent();

        Scene scene = new Scene(this, 780, 430);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {
        this.setPadding(new Insets(20));
        this.setHgap(15);
        this.setVgap(10);

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPrefWidth(120);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(260);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(330);

        this.getColumnConstraints().addAll(col0, col1, col2);

        this.add(new Label("ID:"), 0, 0);
        txfId = new TextField();
        txfId.setPrefWidth(250);
        this.add(txfId, 1, 0);

        this.add(new Label("Navn:"), 0, 1);
        txfNavn = new TextField();
        txfNavn.setPrefWidth(250);
        this.add(txfNavn, 1, 1);

        this.add(new Label("Land:"), 0, 2);
        txfLand = new TextField();
        txfLand.setPrefWidth(250);
        this.add(txfLand, 1, 2);

        this.add(new Label("Kontaktperson:"), 0, 3);
        txfKontaktPerson = new TextField();
        txfKontaktPerson.setPrefWidth(250);
        this.add(txfKontaktPerson, 1, 3);

        this.add(new Label("Telefon:"), 0, 4);
        txfTelefon = new TextField();
        txfTelefon.setPrefWidth(250);
        this.add(txfTelefon, 1, 4);

        this.add(new Label("Email:"), 0, 5);
        txfEmail = new TextField();
        txfEmail.setPrefWidth(250);
        this.add(txfEmail, 1, 5);

        this.add(new Label("Kommentar:"), 0, 6);
        txaKommentar = new TextArea();
        txaKommentar.setPrefWidth(250);
        txaKommentar.setPrefHeight(90);
        this.add(txaKommentar, 1, 6);

        Button btnOpret = new Button("Opret leverandør");
        this.add(btnOpret, 1, 7);
        btnOpret.setOnAction(e -> opretLeverandør());

        this.add(new Label("Leverandører:"), 2, 0);

        lvwLeverandører = new ListView<>();
        lvwLeverandører.setPrefWidth(330);
        lvwLeverandører.setPrefHeight(330);
        this.add(lvwLeverandører, 2, 1, 1, 7);

        updateContent();
    }

    private void opretLeverandør() {
        if (txfId.getText().trim().isEmpty() ||
                txfNavn.getText().trim().isEmpty() ||
                txfLand.getText().trim().isEmpty()) {

            visAlert("Udfyld minimum ID, navn og land.");
            return;
        }

        try {
            int id = Integer.parseInt(txfId.getText().trim());
            String navn = txfNavn.getText().trim();
            String land = txfLand.getText().trim();
            String kontaktPerson = txfKontaktPerson.getText().trim();
            String telefon = txfTelefon.getText().trim();
            String email = txfEmail.getText().trim();
            String kommentar = txaKommentar.getText().trim();

            Controller.createLeverandør(
                    id,
                    navn,
                    land,
                    kontaktPerson,
                    telefon,
                    email,
                    kommentar
            );

            updateContent();

            txfId.clear();
            txfNavn.clear();
            txfLand.clear();
            txfKontaktPerson.clear();
            txfTelefon.clear();
            txfEmail.clear();
            txaKommentar.clear();

            visAlert("Leverandør oprettet.");

        } catch (NumberFormatException e) {
            visAlert("ID skal være et tal.");
        } catch (IllegalArgumentException e) {
            visAlert(e.getMessage());
        }
    }

    private void updateContent() {
        lvwLeverandører.getItems().setAll(Controller.getLeverandører());
    }

    private void visAlert(String besked) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(besked);
        alert.showAndWait();
    }
}