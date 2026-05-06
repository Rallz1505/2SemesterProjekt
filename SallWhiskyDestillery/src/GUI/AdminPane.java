package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdminPane extends GridPane {

    public AdminPane() {
       initContent();
    }

    public void initContent() {
        this.setHgap(20);
        this.setVgap(20);
        this.setPadding(new Insets(20));

        Label lblLoggedIn = new Label("LOGGET IND SOM Admin");
        this.add(lblLoggedIn, 0,0);

        Label lblTitle = new Label("Admin dashboard");
        this.add(lblTitle,1,1);

        Label lblSubtitle = new Label("1 tilgængelige handlinger");
        this.add(lblSubtitle,0,2);

        Button btnSkiftRolle = new Button("Skift rolle");
        this.add(btnSkiftRolle, 3, 0);
        btnSkiftRolle.setOnAction(e-> skiftRolleAction());

        Button btnRegistrerFad = new Button("Registrer fad");
        this.add(btnRegistrerFad,0,3);
        btnRegistrerFad.setOnAction(e-> registrerFadAction());
    }

    public void registrerFadAction(){

        Stage stage = new Stage();
        stage.setTitle("Registrer fad");

        RegistrerFadPane pane = new RegistrerFadPane();
        Scene scene = new Scene(pane, 600, 400);

        stage.setScene(scene);
        stage.show();

    }

    public void skiftRolleAction(){
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();


        Stage newStage = new Stage();
        ForsidePane forsidePane = new ForsidePane();

        try {
            forsidePane.start(newStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
