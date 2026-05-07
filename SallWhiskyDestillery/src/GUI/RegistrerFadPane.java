package GUI;

import Controller.Controller;
import Model.Leverandør;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegistrerFadPane extends GridPane {

    private TextField txfId, txfStorrelse, txfTidligereIndhold, txfNuvMaengde;

    private ComboBox<Leverandør> cbhLeverandør;
    private Stage stage;


    public void open() {
        stage = new Stage();
        stage.setTitle("Registrer Fad");

        initContent();

        Scene scene = new Scene(this, 440, 430);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public void initContent(){
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblId = new Label("ID:");
        this.add(lblId,0,0);
        txfId = new TextField();
        this.add(txfId,1,0);


        Label lblStorrelse = new Label("Størrelse (L):");
        this.add(lblStorrelse, 0,1);
        txfStorrelse = new TextField();
        this.add(txfStorrelse, 1,1);

        Label lblLeverandor = new Label("Leverandør:");
        this.add(lblLeverandor,0,2);
        cbhLeverandør = new ComboBox<>();
        this.add(cbhLeverandør,1,2);

        cbhLeverandør.getItems().addAll(Controller.getLeverandører());

        Label lblTidligereIndhold = new Label("Tidligere indhold:");
        this.add(lblTidligereIndhold,0,3);
        txfTidligereIndhold = new TextField();
        this.add(txfTidligereIndhold,1,3);

        Label lblNuvMaengde = new Label("Nuværende mængde (liter):");
        this.add(lblNuvMaengde,0,4);
        txfNuvMaengde = new TextField();
        this.add(txfNuvMaengde,1,4);



        Button btnSave = new Button("Gem fad");
        this.add(btnSave, 1, 5);
        btnSave.setOnAction(e -> gemFad());

    }


    public void gemFad(){
        int id = Integer.parseInt(txfId.getText().trim());
        int storrelse = Integer.parseInt(txfStorrelse.getText().trim());
        Leverandør leverandor = cbhLeverandør.getValue();
        String tidligereIndhold = txfTidligereIndhold.getText().trim();
        int nuvMaengde = Integer.parseInt(txfNuvMaengde.getText().trim());

        Controller.createFad(storrelse,id,leverandor,tidligereIndhold,nuvMaengde, null);

    }



}
