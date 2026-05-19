package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ForsidePane extends Application {

    private Label lblAdmin, lblDestillatør;

    private Button btnRegistrerFad,
            btnFadoversigt,
            btnRegistrerDestillering,
            btnLager,
            btnOpretWhisky,
            btnLeverandør;

    @Override
    public void start(Stage stage) {

        GridPane gridPane = new GridPane();

        initContent(gridPane);

        Scene scene = new Scene(gridPane, 550, 300);

        stage.setScene(scene);
        stage.setTitle("Produktion & Fadlager");
        stage.show();
    }

    private void initContent(GridPane pane) {

        pane.setHgap(40);
        pane.setVgap(20);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(220);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(220);

        pane.getColumnConstraints().addAll(col1, col2);

        // ================= ADMIN =================

        lblAdmin = new Label("Admin");
        pane.add(lblAdmin, 0, 0);

        btnRegistrerFad = new Button("Registrer fad");
        btnRegistrerFad.setPrefWidth(180);
        pane.add(btnRegistrerFad, 0, 1);

        btnRegistrerFad.setOnAction(e ->
                new RegistrerFadPane().open());

        btnLeverandør = new Button("Registrer leverandør");
        btnLeverandør.setPrefWidth(180);
        pane.add(btnLeverandør, 0, 2);

        btnLeverandør.setOnAction(e ->
                new LeverandørPane().open());

        btnLager = new Button("Lager administration");
        btnLager.setPrefWidth(180);
        pane.add(btnLager, 0, 3);

        btnLager.setOnAction(e ->
                new LagerPane().open());

        // ================= DESTILLATØR =================

        lblDestillatør = new Label("Destillatør");
        pane.add(lblDestillatør, 1, 0);

        btnRegistrerDestillering = new Button("Registrer destillering");
        btnRegistrerDestillering.setPrefWidth(180);
        pane.add(btnRegistrerDestillering, 1, 1);

        btnRegistrerDestillering.setOnAction(e ->
                new RegistreDES().open());

        btnOpretWhisky = new Button("Opret whisky");
        btnOpretWhisky.setPrefWidth(180);
        pane.add(btnOpretWhisky, 1, 2);

        btnOpretWhisky.setOnAction(e ->
                new WhiskyPane().open());

        btnFadoversigt = new Button("Fadoversigt");
        btnFadoversigt.setPrefWidth(180);
        pane.add(btnFadoversigt, 1, 3);

        btnFadoversigt.setOnAction(e ->
                new FadoversigtPane().open());
    }
}