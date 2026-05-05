package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.awt.*;

public class ForsidePane extends GridPane {

    public ForsidePane(){
        this.setHgap(20);
        this.setVgap(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        Button btnAdmin = new Button("Admin");
        btnAdmin.setPrefWidth(150);

        Button btnDest = new Button("Destillatør");
        btnDest.setPrefWidth(150);

        this.add(btnAdmin, 0, 0);
        this.add(btnDest, 1, 0);

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
