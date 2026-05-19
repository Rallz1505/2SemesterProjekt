package GUI;

import Controller.Controller;
import Model.Leverandør;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegistrerFadPane extends GridPane {

    private TextField txfId, txfStorrelse, txfTidligereIndhold, txfNuvMaengde;
    private ComboBox<Leverandør> cbhLeverandør;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Registrer Fad");

        initContent();

        Scene scene = new Scene(this, 440, 430);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        this.add(new Label("ID:"), 0, 0);
        txfId = new TextField();
        this.add(txfId, 1, 0);

        this.add(new Label("Størrelse (L):"), 0, 1);
        txfStorrelse = new TextField();
        this.add(txfStorrelse, 1, 1);

        this.add(new Label("Leverandør:"), 0, 2);
        cbhLeverandør = new ComboBox<>();
        cbhLeverandør.getItems().addAll(Controller.getLeverandører());
        this.add(cbhLeverandør, 1, 2);

        this.add(new Label("Tidligere indhold:"), 0, 3);
        txfTidligereIndhold = new TextField();
        this.add(txfTidligereIndhold, 1, 3);

        this.add(new Label("Nuværende mængde (liter):"), 0, 4);
        txfNuvMaengde = new TextField();
        this.add(txfNuvMaengde, 1, 4);

        Button btnSave = new Button("Gem fad");
        this.add(btnSave, 1, 5);
        btnSave.setOnAction(e -> gemFad());
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