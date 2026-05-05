package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ForsidePane pane = new ForsidePane();
        Scene scene = new Scene(pane, 400, 200);

        stage.setScene(scene);
        stage.setTitle("Produktion & Fadlager");
        stage.show();
    }

    public static void main(String[] args)  {
        Application.launch(args);


    }


}
