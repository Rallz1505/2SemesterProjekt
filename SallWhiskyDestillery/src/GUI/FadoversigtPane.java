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
import java.util.ArrayList;
import java.util.List;

public class FadoversigtPane extends GridPane {

    private TabPane tabPane;
    private TextField txfSøgId, txfMængde, txfAnsvarlig;
    private Button btnSøgId, btnSøgIndhold, btnVisAlle, btnVisTomme,
            btnVisFyldte, btnVisKlar,btnVisBeskrivelse, btnÆndrPlacering;
    private ListView<Fad> lvwFade;
    private ComboBox<String> cbIndhold;
    private ListView<Påfyldning> lvwPåfyldninger;
    private ComboBox<Destillering> cbDest;
    private ComboBox<Fad> cbFade;
    private DatePicker dpDato;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Fadoversigt");

        initContent();

        Scene scene = new Scene(this, 900, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initContent() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(12);

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.add(tabPane, 0, 0);

        // oversigt tab
        Tab tabOversigt = new Tab("Fadoversigt");
        tabOversigt.setContent(lavFadOversigtPane());
        tabPane.getTabs().add(tabOversigt);

        // påfyldningstab
        Tab tabPåfyldning = new Tab("Registrer påfyldning");
        tabPåfyldning.setContent(lavPåfyldningPane());
        tabPane.getTabs().add(tabPåfyldning);
    }
    private GridPane lavFadOversigtPane() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(12);

        // søg efter id
        Label lblSøgId = new Label("Søg efter fad ID:");
        pane.add(lblSøgId, 0, 0);

        txfSøgId = new TextField();
        pane.add(txfSøgId, 0, 1);

        btnSøgId = new Button("Søg ID");
        pane.add(btnSøgId, 1, 1);
        btnSøgId.setOnAction(e-> soegFad());

        // se tidligere indhold
        Label lblIndhold = new Label("Søg efter tidligere indhold:");
        pane.add(lblIndhold, 0, 2);

        cbIndhold = new ComboBox<>();
        cbIndhold.getItems().addAll("BOURBON", "SHERRY", "RØDVIN", "PORTVIN");
        pane.add(cbIndhold, 0, 3);

        btnSøgIndhold = new Button("Søg indhold");
        pane.add(btnSøgIndhold, 1, 3);
        btnSøgIndhold.setOnAction(e-> soegTidligereIndhold());

        // filterings knapper

        btnVisAlle = new Button("Vis alle fade");
        pane.add(btnVisAlle, 0,5);
        btnVisAlle.setOnAction(e-> visAlle());

        btnVisTomme = new Button("Vis tomme fade");
        pane.add(btnVisTomme,0,6);
        btnVisTomme.setOnAction(e-> visTomme());

        btnVisFyldte = new Button("Vis fyldte fade");
        pane.add(btnVisFyldte,0,7);
        btnVisFyldte.setOnAction(e-> visFyldte());

        btnVisKlar = new Button("Vis fade klar til aftapning");
        pane.add(btnVisKlar,0,8);
        btnVisKlar.setOnAction( e-> visKlarTilAftapning());

        // beskrivelse og lagerplacering
        btnVisBeskrivelse = new Button("Vis beskrivelse");
        btnVisBeskrivelse.setOnAction(e-> visBekskrivelse());
        btnÆndrPlacering = new Button("Ændr lagerplacering");
        btnÆndrPlacering.setOnAction(e-> aendrePlacering());

        pane.add(btnVisBeskrivelse, 0, 10);
        pane.add(btnÆndrPlacering, 0, 11);

        // fad liste

        lvwFade = new ListView<>();
        lvwFade.setPrefWidth(350);
        lvwFade.setPrefHeight(350);

        pane.add(lvwFade, 2, 0, 1, 12);

        return pane;
    }

    private GridPane lavPåfyldningPane(){
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(12);

        // Vælg fad
        Label lblFad = new Label("Vælg fad:");
        pane.add(lblFad, 0, 0);

        cbFade = new ComboBox<>();
        cbFade.setPrefWidth(200);
        pane.add(cbFade, 0, 1);

        // Vælg destillering
        Label lblDest = new Label("Vælg destillering:");
        pane.add(lblDest, 0, 2);

        cbDest = new ComboBox<>();
        cbDest.setPrefWidth(200);
        pane.add(cbDest, 0, 3);

        // Mængde
        Label lblMængde = new Label("Mængde (L):");
        pane.add(lblMængde, 0, 4);

        txfMængde = new TextField();
        pane.add(txfMængde, 0, 5);

        // Dato
        Label lblDato = new Label("Dato:");
        pane.add(lblDato, 0, 6);

        dpDato = new DatePicker();
        pane.add(dpDato, 0, 7);

        //  Ansvarlig
        Label lblAnsvarlig = new Label("Ansvarlig:");
        pane.add(lblAnsvarlig, 0, 8);

        txfAnsvarlig = new TextField();
        pane.add(txfAnsvarlig, 0, 9);

        //Knap Registrer påfyldning
        Button btnOpret = new Button("Registrer påfyldning");
        pane.add(btnOpret, 0, 11);

        //ListView til at vise eksisterende påfyldninger
        lvwPåfyldninger = new ListView<>();
        lvwPåfyldninger.setPrefWidth(350);
        lvwPåfyldninger.setPrefHeight(350);
        pane.add(lvwPåfyldninger, 2, 0, 1, 12);

        return pane;
    }

    // Knappe metoder til fadoversigt pane

    private void soegFad(){
        String tekst = txfSøgId.getText().trim();
        if (tekst.isEmpty()){
            return;
        }
        try {
            int id = Integer.parseInt(tekst);
            Fad fad = Controller.findFad(id);

            if (fad != null){
                lvwFade.getItems().setAll(fad);
            } else {
                lvwFade.getItems().clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Intet fad fundet");
                alert.setContentText("Der findes ikke et fad med ID: " + id);
                alert.showAndWait();
            }
        } catch (NumberFormatException e ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Ugyldigt ID");
            alert.setHeaderText("Indtast et gyldigt tal");
            alert.showAndWait();
        }
    }

    private void soegTidligereIndhold(){
        String valgt = cbIndhold.getValue();
        if (valgt == null){
            return;
        }
        List<Fad> resultat = new ArrayList<>();

        for (Fad f : Controller.getFade()){
            if (f.getTidligereIndhold().equalsIgnoreCase(valgt)){
                resultat.add(f);
            }
        }

        lvwFade.getItems().setAll(resultat);

    }

    private void visBekskrivelse(){

        Fad valgt = lvwFade.getSelectionModel().getSelectedItem();
        if (valgt == null){
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fad beskrivelse");
        alert.setHeaderText(valgt.toString());
        alert.setContentText(valgt.getTidligereIndhold());
        alert.showAndWait();

    }
    private void aendrePlacering(){
        Fad valgt = lvwFade.getSelectionModel().getSelectedItem();
        if (valgt == null){
            return;
        }

        // Ændre lagerplads pane
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        TextField txfReol = new TextField();
        TextField txfHylde = new TextField();
        TextField txfPlads = new TextField();
        TextField txfBeskrivelse = new TextField();

        pane.add(new Label("Reol:"), 0, 0);
        pane.add(txfReol, 1, 0);

        pane.add(new Label("Hylde:"), 0, 1);
        pane.add(txfHylde, 1, 1);

        pane.add(new Label("Plads:"), 0, 2);
        pane.add(txfPlads,1,2);

        pane.add(new Label("Beskrivelse:"), 0, 3);
        pane.add(txfBeskrivelse, 1, 3);

        // Aler vindue
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ændr lagerplacering");
        alert.setHeaderText("Indtast ny placering");
        alert.getDialogPane().setContent(pane);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                int reol = Integer.parseInt(txfReol.getText());
                int hylde = Integer.parseInt(txfHylde.getText());
                int plads = Integer.parseInt(txfPlads.getText());
                String beskrivelse = txfBeskrivelse.getText();

                // tjek om den indtastede lagerplads er ledig
                LagerPlads nyPlads = new LagerPlads(reol, hylde, plads, beskrivelse);
                if (!nyPlads.erLedig()){
                    new Alert(Alert.AlertType.WARNING, "Pladsen er optaget").showAndWait();
                    return;
                }
                // her vi ændre vi placering
                Controller.placerFadPåLagerPlads(valgt, nyPlads);

                new Alert(Alert.AlertType.INFORMATION, "Placering opdateret").showAndWait();
            }
        });

    }

    private void visTomme(){
        List<Fad> resultat = new ArrayList<>();

        for (Fad f : Controller.getFade()){
            if (f.getNuværendeMængde() == 0){
                resultat.add(f);
            }
        }
        if (resultat.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Der er ingen tomme fade.").showAndWait();
        } else {
            lvwFade.getItems().setAll(resultat);
        }
    }

    private void visFyldte(){
        List<Fad> resultat = new ArrayList<>();

        for (Fad f : Controller.getFade()){
            if (f.getNuværendeMængde() > 0){
                resultat.add(f);
            }
        }
        if (resultat.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Der er ingen fyldte fade.").showAndWait();
            return;
        }
        lvwFade.getItems().setAll(resultat);

    }
    private void visAlle(){
        lvwFade.getItems().setAll(Controller.getFade());
    }

    private void visKlarTilAftapning(){
        lvwFade.getItems().setAll(Controller.getFadeKlarTilAftapning());

    }

    // Knappe metoder til påfyldningspane

    private void opretPåfyldning(){
        Fad fad = cbFade.getValue();
        Destillering dest = cbDest.getValue();
        String mængdeTxt = txfMængde.getText();
        LocalDate dato = dpDato.getValue();
        String ansvarlig = txfAnsvarlig.getText();

        if (fad == null || dest == null || mængdeTxt.isEmpty() || dato == null || ansvarlig.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Udfyld venligst alle felter.").showAndWait();
            return;
        }
        double mængde;
        try {
            mængde = Double.parseDouble(mængdeTxt);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Mængde skal være et tal.").showAndWait();
            return;
        }
        VæskeMængde vm = new VæskeMængde(mængde, dest);
        Påfyldning p = Controller.createPåfyldning(dato,ansvarlig,vm, fad);

        if (p == null) {
            new Alert(Alert.AlertType.WARNING, "Påfyldning mislykkedes.").showAndWait();
            return;
        }
        lvwPåfyldninger.getItems().add(p);

        new Alert(Alert.AlertType.INFORMATION, "Påfyldning registreret.").showAndWait();

        txfMængde.clear();
        txfAnsvarlig.clear();
        dpDato.setValue(null);
    }

    private void visPåfyldningerForFad() {
        Fad fad = cbFade.getValue();
        if (fad == null) {
            lvwPåfyldninger.getItems().clear();
            return;
        }

        lvwPåfyldninger.getItems().setAll(fad.getPåfyldninger());
    }



}
