package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdminPane extends GridPane {

    public AdminPane() {
        initContent();
    }

    private void initContent() {

        this.setHgap(20);
        this.setVgap(20);
        this.setPadding(new Insets(20));

        Label lblLoggedIn = new Label("LOGGET IND SOM ADMIN");
        this.add(lblLoggedIn, 0, 0);

        Label lblTitle = new Label("Admin dashboard");
        this.add(lblTitle, 0, 1);

        Button btnRegistrerFad = new Button("Registrer fad");
        this.add(btnRegistrerFad, 0, 2);
        btnRegistrerFad.setOnAction(e -> registrerFadAction());

        Button btnFadoversigt = new Button("Fadoversigt");
        this.add(btnFadoversigt, 0, 3);
        btnFadoversigt.setOnAction(e -> new FadoversigtPane().open());

        Button btnLager = new Button("Lager administration");
        this.add(btnLager, 0, 4);
        btnLager.setOnAction(e -> new LagerPane().open());
    }

    private void registrerFadAction() {

        Stage stage = new Stage();
        stage.setTitle("Registrer fad");

        RegistrerFadPane pane = new RegistrerFadPane();

        Scene scene = new Scene(pane, 500, 400);

        stage.setScene(scene);
        stage.show();
    }
}