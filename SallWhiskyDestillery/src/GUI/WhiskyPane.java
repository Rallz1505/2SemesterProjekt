package GUI;

import Controller.Controller;
import Model.Fad;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WhiskyPane extends GridPane {

    private TextField txfAntal, txfStoerrelse;

    private Label lblTotal, lblAftappet, lblResterende;

    private Button btnOpretFlasker, btnVisHistorik;

    private ListView<Fad> lvwFade;
//    private ListView<Flaske> lvwFlasker;

    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Registrer Lager & Lagerplads");

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
        Label lblLiterDerBruges = new Label("-");
        this.add(lblLiterDerBruges, 3, 3);

        this.add(new Label("Liter tilbage:"), 2, 4);
        Label lblEfterTap = new Label("-");
        this.add(lblEfterTap, 3, 4);

        btnOpretFlasker = new Button("Opret flasker");
        this.add(btnOpretFlasker,3,5);

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

//        lvwFlasker = new ListView<>();
//        lvwFlasker.setPrefHeight(200);
//        this.add(lvwFlasker, 2, 7, 2, 1);


    }

    private void opdaterFadInfo(){}

    private void opdaterBeregning(){}

    private void opretFlasker(){}

    private void visHistorik(){}


}
