package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ForsidePane extends Application {

    //Brugt AI til at gøre det pænt så det var lidt mere behageligt at kigge på :), men ikk ebrugt det til andet.

    private Label lblAdmin, lblDestillatør;

    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();

        initContent(gridPane);

        Scene scene = new Scene(gridPane, 650, 450);

        stage.setScene(scene);
        stage.setTitle("Produktion & Fadlager");
        stage.show();
    }

    private void initContent(GridPane pane) {
        pane.setHgap(35);
        pane.setVgap(20);
        pane.setPadding(new Insets(35));
        pane.setAlignment(Pos.CENTER);

        pane.setStyle("-fx-background-color: linear-gradient(to bottom, #3b2415, #1f120a);");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(250);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(250);

        pane.getColumnConstraints().addAll(col1, col2);

        Label lblTitle = new Label("Sall Whisky");
        lblTitle.setStyle("-fx-font-size: 34px; -fx-font-weight: bold; -fx-text-fill: #f3d7a3;");
        pane.add(lblTitle, 0, 0, 2, 1);
        GridPane.setHalignment(lblTitle, javafx.geometry.HPos.CENTER);

        Label lblSubtitle = new Label("Produktion & Fadlager");
        lblSubtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #d8b77a;");
        pane.add(lblSubtitle, 0, 1, 2, 1);
        GridPane.setHalignment(lblSubtitle, javafx.geometry.HPos.CENTER);

        VBox adminBox = createBox();

        lblAdmin = new Label("Admin");
        lblAdmin.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #f3d7a3;");

        Button btnRegistrerFad = createWhiskyButton("Registrer fad");
        btnRegistrerFad.setOnAction(e -> new RegistrerFadPane().open());

        Button btnLeverandør = createWhiskyButton("Registrer leverandør");
        btnLeverandør.setOnAction(e -> new LeverandørPane().open());

        Button btnLager = createWhiskyButton("Lager administration");
        btnLager.setOnAction(e -> new LagerPane().open());

        adminBox.getChildren().addAll(lblAdmin, btnRegistrerFad, btnLeverandør, btnLager);

        VBox destillatørBox = createBox();

        lblDestillatør = new Label("Destillatør");
        lblDestillatør.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #f3d7a3;");

        Button btnRegistrerDestillering = createWhiskyButton("Registrer destillering");
        btnRegistrerDestillering.setOnAction(e -> new RegistreDES().open());

        Button btnOpretWhisky = createWhiskyButton("Opret whisky");
        btnOpretWhisky.setOnAction(e -> new WhiskyPane().open());

        Button btnFadoversigt = createWhiskyButton("Fadoversigt");
        btnFadoversigt.setOnAction(e -> new FadoversigtPane().open());

        Button btnPlacerFad = createWhiskyButton("Placer fad på lager");
        btnPlacerFad.setOnAction(e -> new PlacerFadPåLagerPane().open());

        Button btnPåfyldning = createWhiskyButton("Registrer påfyldning");
        btnPåfyldning.setOnAction(e -> new PåfyldningPane().open());

        destillatørBox.getChildren().addAll(lblDestillatør, btnRegistrerDestillering, btnOpretWhisky, btnFadoversigt, btnPåfyldning, btnPlacerFad);

        pane.add(adminBox, 0, 2);
        pane.add(destillatørBox, 1, 2);
    }

    private VBox createBox() {
        VBox box = new VBox(14);
        box.setPadding(new Insets(22));
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle(
                "-fx-background-color: #5a321b;" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-color: #b88746;" +
                        "-fx-border-radius: 18;" +
                        "-fx-border-width: 1.5;"
        );
        return box;
    }

    private Button createWhiskyButton(String text) {
        Button button = new Button(text);

        button.setPrefWidth(210);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color: #b88746;" +
                        "-fx-text-fill: #1f120a;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #d6a85f;" +
                                "-fx-text-fill: #1f120a;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 12;" +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: #b88746;" +
                                "-fx-text-fill: #1f120a;" +
                                "-fx-font-size: 14px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 12;" +
                                "-fx-cursor: hand;"
                )
        );

        return button;
    }
}