package MainPackage;

import BeanPackage.*;
import InterfacePackage.InterfConst;
import java.time.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.WindowEvent;

public class MainClass extends Application implements InterfConst {

    static BorderPane root = new BorderPane();
    static StackPane welcomePane = new StackPane();
    //http://stackoverflow.com/questions/6845231/how-to-correctly-get-image-from-resources-folder-in-netbeans
    AudioClip exitSound = new AudioClip(getClass().getResource("/exitSound.mp3").toString());

    @Override
    public void start(Stage stage) {
        //Start of Menu Bar
        MenuBar menuBar = new MenuBar();

        Menu coursesMenu = new Menu(COURSE_STRING);

        Menu mechanicsItems = new Menu(NYA_TITLE);
        MenuItem mechAnimation1 = new MenuItem(ANIMATION_1);
        mechAnimation1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(new Bean_NYA1(500, 30).getPane());
            }
        });
        MenuItem mechAnimation2 = new MenuItem(ANIMATION_2);
        mechAnimation2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(new Bean_NYA2().getPane());
            }
        });
        MenuItem mechInConstruction = new MenuItem(IN_CONSTRUCTION);
        mechInConstruction.setOnAction(getInConstructionEvent());
        mechanicsItems.getItems().addAll(mechAnimation1, mechAnimation2, mechInConstruction);

        Menu electricityItems = new Menu(NYB_TITLE);
        MenuItem electAnimation1 = new MenuItem(ANIMATION_1);
        electAnimation1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(new Bean_NYB1().getPane());
            }
        });
        MenuItem electAnimation2 = new MenuItem(ANIMATION_2);
        electAnimation2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(new Bean_NYB2().getPane());
            }
        });
        MenuItem electInConstruction = new MenuItem(IN_CONSTRUCTION);
        electInConstruction.setOnAction(getInConstructionEvent());
        electricityItems.getItems().addAll(electAnimation1, electAnimation2, electInConstruction);

        Menu wavesItems = new Menu(NYC_TITLE);
        MenuItem wavesAnimation1 = new MenuItem(ANIMATION_1);
        wavesAnimation1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(new Bean_NYC1(2, 120).getPane());
            }
        });
        MenuItem wavesAnimation2 = new MenuItem(ANIMATION_2);
        wavesAnimation2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(new Bean_NYC2().getPane());
            }
        });
        MenuItem wavesInConstruction = new MenuItem(IN_CONSTRUCTION);
        wavesInConstruction.setOnAction(getInConstructionEvent());
        wavesItems.getItems().addAll(wavesAnimation1, wavesAnimation2, wavesInConstruction);

        MenuItem exit = new MenuItem(EXIT_STRING);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                exit();
            }
        });

        coursesMenu.getItems().addAll(mechanicsItems, electricityItems, wavesItems, exit);
        menuBar.getMenus().add(coursesMenu);
        //End of Menu Bar

        Label welcome = new Label(WELCOME_MESSAGE);
        welcome.setFont(Font.font("IMPACT", FontPosture.REGULAR, 35));
        welcome.setTextFill(Color.BLUE);
        BorderPane.setAlignment(welcome, Pos.CENTER);
        welcomePane.getChildren().add(welcome);
        welcomePane.setBackground(Background.EMPTY);

        VBox topVBox = new VBox();
        topVBox.setSpacing(10);
        topVBox.getChildren().add(menuBar);

        root.setTop(topVBox);
        root.setCenter(welcomePane);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                exit();
            }

        });
        stage.setScene(new Scene(root, 1000, 900));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle(MAIN_TITLE);
        stage.show();
    }

    public static BorderPane getInConstructionPane() {
        BorderPane bp = new BorderPane();
        Label inConstruction = new Label(IN_CONSTRUCTION_MESSAGE);
        inConstruction.setFont(Font.font("IMPACT", FontPosture.REGULAR, 35));
        bp.setCenter(inConstruction);
        return bp;
    }

    public static void returnToMain() {
        root.setCenter(welcomePane);
    }

    public static void resetNYA1(int mass, double angle) {
        root.setCenter(new Bean_NYA1(mass, angle).getPane());
    }

    public static void resetNYC1(int mass, double angle) {
        root.setCenter(new Bean_NYC1(mass, angle).getPane());
    }

    public static EventHandler<ActionEvent> getInConstructionEvent() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                root.setCenter(getInConstructionPane());
            }
        };
        return event;
    }

    public void exit() {
        exitSound.play();
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
        }
        System.exit(0);
    }
}
