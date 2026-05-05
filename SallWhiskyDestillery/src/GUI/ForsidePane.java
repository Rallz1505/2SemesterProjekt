package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.awt.*;

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

    public void initContent(GridPane pane){
        pane.setHgap(20);
        pane.setVgap(20);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        Button btnAdmin = new Button("Admin");
        btnAdmin.setPrefWidth(150);

        Button btnDest = new Button("Destillatør");
        btnDest.setPrefWidth(150);

        pane.add(btnAdmin, 0, 0);
        pane.add(btnDest, 1, 0);

        btnAdmin.setOnAction(e -> openAdminWindow());
        btnDest.setOnAction(e -> openDestWindow());
    }

    private void openAdminWindow() {
        Stage stage = new Stage();
        stage.setTitle("Admin panel");
        AdminPane pane = new AdminPane();
        Scene scene = new Scene(pane, 600, 400);

        stage.setScene(scene);
        stage.show();
    }

    private void openDestWindow() {
        Stage stage = new Stage();
        stage.setTitle("Destillatør panel");
        DestillatørPane pane = new DestillatørPane();
        Scene scene = new Scene(pane, 600, 400);

        stage.setScene(scene);
        stage.show();
    }


}
