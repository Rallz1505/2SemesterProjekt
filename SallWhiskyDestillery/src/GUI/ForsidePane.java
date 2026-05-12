package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class ForsidePane extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 400, 200);
        initContent(gridPane);
        stage.setScene(scene);
        stage.setTitle("Produktion & Fadlager");
        stage.show();
    }

    private Label lblAdmin, lblDestillatør;
    private Button btnRegistrerFad, btnFadoversigt, btnRegistrerDestillering, btnlager;



    public void initContent(GridPane pane){
        pane.setHgap(20);
        pane.setVgap(20);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);


        // Admin
        lblAdmin = new Label("Admin");
        pane.add(lblAdmin, 0,0);

        btnRegistrerFad = new Button("Registrer fad");
        pane.add(btnRegistrerFad,0,1);
        btnRegistrerFad.setOnAction(e-> new RegistrerFadPane().open());

        btnFadoversigt = new Button("Fadoversigt");
        pane.add(btnFadoversigt,0,2);
        btnFadoversigt.setOnAction(e-> new FadoversigtPane().open());

        btnlager = new Button("Lager administration");
        pane.add(btnlager,0,3);
        btnlager.setOnAction(e-> new LagerPane().open());




        //Destillatør
        lblDestillatør = new Label("Destillatør");
        pane.add(lblDestillatør,1,0);
        btnRegistrerDestillering = new Button("Registrer destillering");
        pane.add(btnRegistrerDestillering,1,1);
        btnRegistrerDestillering.setOnAction(e-> new RegistreDES().open());





    }




}
