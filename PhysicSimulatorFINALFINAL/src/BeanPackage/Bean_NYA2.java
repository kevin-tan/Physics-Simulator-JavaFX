package BeanPackage;

import InterfacePackage.ComLayout;
import MainPackage.MainClass;
import java.io.InputStream;
import static java.lang.Math.*;
import java.util.Optional;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Bean_NYA2 extends ComLayout {

    Button start;
    Button done;
    Button pause;
    Button reset;
    Button help;
    Image grass;
    ImageView grassImageView;
    Rectangle cannon;
    Circle cannonPiece;
    Circle wheel;
    Circle bullet;
    Path animationPath;
    PathTransition pathTransition;
    TextInputDialog chooseAngle;
    Optional<String> result;
    IntegerProperty angleValue;
    double speedX;
    double speedY;
    double horizontalDistance;
    double verticalDistance;
    double time;
    ArcTo finalPosition;
    NumberAxis xAxis;
    NumberAxis yAxis;
    LineChart<Number, Number> lineChart;
    XYChart.Series series;
    Tab graphTab;
    Tab tableTab;
    Spinner varInput;
    Label formulaLabel;
    
    InputStream GRASS_URL = getClass().getResourceAsStream("/Grass.png");

    @Override

    public BorderPane getPane() {

        angleValue = new SimpleIntegerProperty(45);
        start = new Button(START_STRING);
        done = new Button(DONE_STRING);
        pause = new Button(PAUSE_STRING);
        reset = new Button(RESET_STRING);
        help = new Button(HELP_STRING);

        BorderPane root = new BorderPane();

        Alert helpMsg = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.OK);
        helpMsg.setTitle(HELP_STRING);
        helpMsg.setContentText(NYA_2_HELP_MESSAGE);
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                helpMsg.show();

            }
        });
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(start, done, pause, reset, help);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);

        TabPane graphPane = new TabPane();
        graphTab = new Tab(GRAPH_TAB_STRING);
        tableTab = new Tab(TABLE_TAB_STRING);
        graphTab.setClosable(false);
        tableTab.setClosable(false);
        graphPane.getTabs().addAll(graphTab, tableTab);

        SplitPane bottomPane = new SplitPane();
        bottomPane.getItems().addAll(buttonPane, graphPane);
        bottomPane.setDividerPositions(0.5f);
        bottomPane.setPrefHeight(160);
        bottomPane.setPrefWidth(200);

        root.setBottom(bottomPane);

        Pane animation = new Pane();

        createBackground();
        buildCannon();
        createAnimation();
        
        animation.getChildren().addAll(grassImageView, bullet, cannon, cannonPiece, wheel, formulaLabel);
        root.setCenter(animation);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {

                start.setDisable(true);
                pause.setDisable(false);
                bullet.setVisible(true);
                getChosenAngle();
                tableGraphTab();

                pathTransition.play();

            }
        });

        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent Event) {

                MainClass.returnToMain();
                start.setDisable(false);

            }
        });
        pause.setDisable(true);
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {

                start.setDisable(false);
                bullet.setVisible(true);
                pathTransition.pause();

            }
        });

        Alert resetPopUp = new Alert(Alert.AlertType.CONFIRMATION, "Choose your option: ", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        resetPopUp.setTitle(CHANGING_ANGLES);
        resetPopUp.setHeaderText(CHANGE_ANGLE_QUESTION);
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent Event) {
                pathTransition.jumpTo(Duration.ZERO);
                pathTransition.stop();
                Optional<ButtonType> result = resetPopUp.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.YES) {
                    pause.fire();
                    showResetPopup();

                } else if (result.isPresent() && result.get() == ButtonType.NO) {
                    pause.fire();

                } else {

                }

            }
        });

        cannon.getTransforms().clear();
        cannon.getTransforms().add(new Rotate(-1 * getChosenAngle(), 0, 0));
        start.setDisable(false);

        return root;
    }

    public void createBackground() {

        formulaLabel = new Label(NYA_2_FORMULA);
        formulaLabel.setLayoutX(0);
        formulaLabel.setLayoutY(0);
        //File file = new File(GRASS_URL);
        //grass = new Image(file.toURI().toString());
        grass = new Image(GRASS_URL);
        grassImageView = new ImageView();
        grassImageView.setFitHeight(60);
        grassImageView.setFitWidth(1100);
        grassImageView.setImage(grass);
        grassImageView.setLayoutX(0);
        grassImageView.setLayoutY(665);

    }

    public void createAnimation() {
        animationPath = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(0);
        moveTo.setY(0);

        finalPosition = new ArcTo();
        finalPosition.setX(getHorizontalDistance());
        finalPosition.setY(0);
        finalPosition.setRadiusX(getHorizontalDistance() / 20);
        finalPosition.setRadiusY(getVerticalDistance() / 20);

        finalPosition.setLargeArcFlag(false);
        finalPosition.setSweepFlag(true);

        animationPath.getElements().add(moveTo);

        animationPath.getElements().add(finalPosition);

        pathTransition = new PathTransition(Duration.millis(getTime() * 300), animationPath, bullet);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(false);

    }

    public void buildCannon() {

        cannon = new Rectangle(0, 0, 100, 40);
        cannon.setFill(Color.DIMGRAY);
        cannon.setLayoutX(20);
        cannon.setLayoutY(677);
        cannon.setStroke(Color.BLACK);
        cannon.setStrokeWidth(3);

        cannonPiece = new Circle();
        cannonPiece.setFill(Color.DIMGRAY);
        cannonPiece.setRadius(30);
        cannonPiece.setLayoutX(36);
        cannonPiece.setLayoutY(693);
        cannonPiece.setStroke(Color.BLACK);
        cannonPiece.setStrokeWidth(3);

        wheel = new Circle();
        wheel.setRadius(25);
        wheel.setFill(Color.PERU);
        wheel.setLayoutX(50);
        wheel.setLayoutY(705);
        wheel.setStroke(Color.BLACK);
        wheel.setStrokeWidth(3);

        bullet = new Circle();
        bullet.setLayoutX(38);
        bullet.setLayoutY(685);
        bullet.setRadius(10);
        bullet.setFill(Color.BLACK);
        bullet.setVisible(false);

    }

    public void showResetPopup() {
        Dialog changeValues = new Dialog();
        changeValues.setContentText(CHANGING_ANGLES);
        changeValues.setHeaderText(CHANGE_ANGLE);
        changeValues.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        HBox hbLabels = new HBox();
        VBox vbPane = new VBox();
        Label angleLabel = new Label(ANGLE_RANGE);

        varInput = new Spinner(5, 90, getChosenAngle(), 5);

        Label angleUnit = new Label(DEGREES);
        hbLabels.getChildren().addAll(angleLabel, varInput, angleUnit);
        hbLabels.setSpacing(5);
        vbPane.getChildren().add(hbLabels);

        changeValues.getDialogPane().setContent(vbPane);
        Optional<ButtonType> result = changeValues.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            setChosenAngle(Integer.parseInt(varInput.getValue().toString()));
            finalPosition.setX(getHorizontalDistance());
            finalPosition.setY(0);
            finalPosition.setRadiusX(getHorizontalDistance() / 20);
            finalPosition.setRadiusY(getVerticalDistance() / 20);
            cannon.getTransforms().clear();
            tableGraphTab();
            cannon.getTransforms().add(new Rotate(-1 * getChosenAngle(), 0, 0));
        } else {
            changeValues.close();
        }

    }

    private int getChosenAngle() {
        return angleValue.getValue();
    }

    private void setChosenAngle(int userInput) {
        angleValue.setValue(userInput);
    }

    public double getHorizontalDistance() {

        speedX = speed * cos(getChosenAngle() * (PI / 180));
        horizontalDistance = speedX * getTime();

        return horizontalDistance;
    }

    public double getVerticalDistance() {

        speedY = speed * sin(getChosenAngle() * (PI / 180));
        verticalDistance = Math.pow(speedY, 2) / (2 * GRAV_ACC);

        return verticalDistance;
    }

    public double getTime() {
        time = (2 * speed * sin(getChosenAngle() * (PI / 180))) / GRAV_ACC;
        return time;
    }

    public void tableGraphTab() {

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        xAxis.setLabel(X_TIME);
        yAxis.setLabel(Y_HEIGHT);

        lineChart = new LineChart<>(xAxis, yAxis);
        series = new XYChart.Series();
        series.getData().add(new XYChart.Data(0, 0));
        series.getData().add(new XYChart.Data((double) getHorizontalDistance() / 2, (double) getVerticalDistance()));
        series.getData().add(new XYChart.Data((double) getHorizontalDistance(), 0));
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(series);
        graphTab.setContent(lineChart);

        VBox xTabPoints = new VBox();
        xTabPoints.setAlignment(Pos.CENTER);
        xTabPoints.setSpacing(5);
        xTabPoints.getChildren().addAll(new Label(X_TIME), new Label(ZERO), new Label(String.format("%.4f", (double) getHorizontalDistance() / 2)), new Label(String.format("%.4f", (double) getHorizontalDistance())));
        VBox yTabPoints = new VBox();
        yTabPoints.setAlignment(Pos.CENTER);
        yTabPoints.setSpacing(5);
        yTabPoints.getChildren().addAll(new Label(Y_HEIGHT), new Label(ZERO), new Label(String.format("%.4f", (double) getVerticalDistance())), new Label(ZERO));
        HBox table = new HBox();
        table.setSpacing(5);
        table.getChildren().addAll(xTabPoints, yTabPoints);
        table.setAlignment(Pos.CENTER);
        tableTab.setContent(table);
    }
}