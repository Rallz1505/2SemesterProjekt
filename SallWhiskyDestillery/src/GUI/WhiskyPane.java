package GUI;

import Controller.Controller;
import Model.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class WhiskyPane extends GridPane {

    private TextField txfAntal, txfStoerrelse;
    private Label lblTotal, lblAftappet, lblResterende, lblLiterDerBruges, lblEfterTap;
    private Button btnOpretFlasker, btnVisHistorik;

    private ListView<Fad> lvwFade;
    private ListView<Flaske> lvwFlasker;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("opret whisky");

        initContent();

        Scene scene = new Scene(this, 900, 450);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent(){
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(12);

        ColumnConstraints col0 = new ColumnConstraints(150);
        ColumnConstraints col1 = new ColumnConstraints(200);
        ColumnConstraints col2 = new ColumnConstraints(150);
        ColumnConstraints col3 = new ColumnConstraints(200);
        this.getColumnConstraints().addAll(col0, col1, col2, col3);

        // Fad info
        Label lblFadInfoTitle = new Label("Fad information");
        lblFadInfoTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblFadInfoTitle, 0, 0);

        this.add(new Label("Total liter:"), 0, 1);
        lblTotal = new Label("-");
        this.add(lblTotal, 1, 1);

        this.add(new Label("Aftappet liter:"), 0, 2);
        lblAftappet = new Label("-");
        this.add(lblAftappet, 1, 2);

        this.add(new Label("Resterende liter:"), 0, 3);
        lblResterende = new Label("-");
        this.add(lblResterende, 1, 3);

        // Tapningside
        Label lblTapningTitle = new Label("Tapning");
        lblTapningTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblTapningTitle, 2, 0);

        this.add(new Label("Antal flasker:"), 2, 1);
        txfAntal = new TextField();
        this.add(txfAntal, 3, 1);

        this.add(new Label("Flaskestørrelse (L):"), 2, 2);
        txfStoerrelse = new TextField("0.7");
        this.add(txfStoerrelse, 3, 2);

        this.add(new Label("Liter der bruges:"), 2, 3);
        lblLiterDerBruges = new Label("-");
        this.add(lblLiterDerBruges, 3, 3);

        this.add(new Label("Liter tilbage:"), 2, 4);
        lblEfterTap = new Label("-");
        this.add(lblEfterTap, 3, 4);

        btnOpretFlasker = new Button("Opret flasker");
        this.add(btnOpretFlasker,3,5);
        btnOpretFlasker.setOnAction(e -> opretFlasker());

        // vis historik knap
        btnVisHistorik = new Button("Vis historik");
        this.add(btnVisHistorik, 4, 5);
        btnVisHistorik.setOnAction(e -> visHistorik());

        // Lyttere til beregning
        txfAntal.textProperty().addListener((obs, o, n) -> opdaterBeregning());
        txfStoerrelse.textProperty().addListener((obs, o, n) -> opdaterBeregning());


        // list views

        Label lblFadeTitle = new Label("Fade");
        lblFadeTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblFadeTitle, 0, 6);

        lvwFade = new ListView<>();
        lvwFade.setPrefHeight(200);
        lvwFade.getItems().setAll(Controller.getFade());
        lvwFade.setOnMouseClicked(e -> opdaterFadInfo());
        this.add(lvwFade, 0, 7, 2, 1);


        Label lblFlaskerTitle = new Label("Flasker");
        lblFlaskerTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.add(lblFlaskerTitle, 2, 6);

        lvwFlasker = new ListView<>();
        lvwFlasker.setPrefHeight(200);
        this.add(lvwFlasker, 2, 7, 2, 1);


    }

    private void opdaterFadInfo(){
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();
        if(fad == null){
            return;
        }
        WhiskyMængde wm = fad.getWhiskyMængde();
        if (wm == null){
            lblTotal.setText("0 L");
            lblAftappet.setText("0 L");
            lblResterende.setText("0 L");
            lvwFlasker.getItems().clear();
            return;
        }
        double total = wm.getMængde();
        WhiskyProdukt wp = wm.getWhiskyProdukt();

        double aftappet = 0;
        for (Flaske f : wp.getFlasker()){
            aftappet += f.getVolumen();
        }
        double resterende = total - aftappet;

        lblTotal.setText(String.format("%.2f L", total));
        lblAftappet.setText(String.format("%.2f L", aftappet));
        lblResterende.setText(String.format("%.2f L", resterende));

        lvwFlasker.getItems().setAll(wp.getFlasker());

        opdaterBeregning();

    }

    private void opdaterBeregning(){
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();
        if (fad == null){
            return;
        }
        WhiskyMængde wm = fad.getWhiskyMængde();
        if (wm == null) return;

        if (txfAntal.getText().isEmpty() || txfStoerrelse.getText().isEmpty()) {
            lblLiterDerBruges.setText("-");
            lblEfterTap.setText("-");
            return;
        }
        try {
            int antal = Integer.parseInt(txfAntal.getText().trim());
            double stoerrelse = Double.parseDouble(txfStoerrelse.getText().trim());

            double literDerBruges = antal * stoerrelse;
            lblLiterDerBruges.setText(String.format("%.2f L", literDerBruges));

            WhiskyProdukt wp = wm.getWhiskyProdukt();
            double aftappet = 0;

            for (Flaske f : wp.getFlasker()){
                aftappet += f.getVolumen();
            }
            double resterende = wm.getMængde() - aftappet - literDerBruges;
            lblEfterTap.setText(String.format("%.2f L", resterende));
        } catch (NumberFormatException e) {
            lblLiterDerBruges.setText("-");
            lblEfterTap.setText("-");
        }
    }

    private void opretFlasker(){
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();
        if (fad == null) {
            visAlert("Vælg et fad først.");
            return;
        }

        WhiskyMængde wm = fad.getWhiskyMængde();
        if (wm == null) {
            visAlert("Dette fad har ingen whiskymængde.");
            return;
        }

        WhiskyProdukt wp = wm.getWhiskyProdukt();

        if (txfAntal.getText().isEmpty() || txfStoerrelse.getText().isEmpty()) {
            visAlert("Udfyld antal og størrelse.");
            return;
        }

        try {
            int antal = Integer.parseInt(txfAntal.getText().trim());
            double stoerrelse = Double.parseDouble(txfStoerrelse.getText().trim());

            double literDerBruges = antal * stoerrelse;

            double aftappet = 0;
            for (Flaske f : wp.getFlasker()){
                aftappet += f.getVolumen();
            }
            double resterende = wm.getMængde() - aftappet;

            if (literDerBruges > resterende){
                visAlert("Der er ikke nok whisky tilbage på fadet");
                return;
            }
            for (int i = 0; i < antal; i++) {
                int flaskeNr = wp.getFlasker().size() + 1;
                wp.createFlaske(flaskeNr, stoerrelse);
            }
            wm.setMængde(wm.getMængde() - literDerBruges);

            opdaterFadInfo();
            txfAntal.clear();
        } catch (NumberFormatException e){
            visAlert("Ugyldig indtastning. Brug tal og punktum");
        }
    }

    private void visHistorik(){
        Flaske flaske = lvwFlasker.getSelectionModel().getSelectedItem();
        if (flaske == null) {
            visAlert("Vælg en flaske for at se historik.");
            return;
        }

        WhiskyProdukt wp = flaske.getWhiskyProdukt();
        WhiskyMængde wm = null;
        Fad fad = null;

        for (Fad f : Controller.getFade()) {
            if (f.getWhiskyMængde() != null && f.getWhiskyMængde().getWhiskyProdukt() == wp) {
                wm = f.getWhiskyMængde();
                fad = f;
            }
        }

        if (wm == null || fad == null) {
            visAlert("Kunne ikke finde historik for denne flaske.");
            return;
        }

        Destillering d = wm.getDestillering();

        int antalPåfyldninger = fad.getPåfyldninger().size();
        String kornsort = d.getKornSort();
        int batch = d.getMaltBatch();
        int lagringsaar = fad.getAlder();

        StringBuilder sb = new StringBuilder();
        sb.append("Flaske nr: ").append(flaske.getFlaskeNr()).append("\n");
        sb.append("Volumen: ").append(flaske.getVolumen()).append(" L\n\n");

        sb.append("Batch: ").append(batch).append("\n");
        sb.append("Antal påfyldninger: ").append(antalPåfyldninger).append("\n");
        sb.append("Kornsort: ").append(kornsort).append("\n");
        sb.append("Lagringstid: ").append(lagringsaar).append(" år\n");

        visAlert(sb.toString());
    }


    // hjælpemetode
    private void visAlert(String besked) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(besked);
        alert.showAndWait();
    }


}
