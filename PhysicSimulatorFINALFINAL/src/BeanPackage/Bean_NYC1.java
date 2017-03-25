package BeanPackage;

import InterfacePackage.ComLayout;
import static InterfacePackage.InterfConst.CANCEL_STRING;
import static InterfacePackage.InterfConst.NO_STRING;
import static InterfacePackage.InterfConst.OK_STRING;
import static InterfacePackage.InterfConst.YES_STRING;
import MainPackage.MainClass;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Bean_NYC1 extends ComLayout {

    IntegerProperty mass = new SimpleIntegerProperty();
    DoubleProperty angle = new SimpleDoubleProperty();
    ParallelTransition mainAnimation;
    Tab graphTab;
    Tab tableTab;

    public Bean_NYC1(int mass, double angle) {
        this.mass.set(mass);
        this.angle.set(angle);
    }

    public void showResetPopup() {
        Button yesButton = new Button(YES_STRING);
        Button noButton = new Button(NO_STRING);
        Button cancelButton1 = new Button(CANCEL_STRING);
        Button cancelButton2 = new Button(CANCEL_STRING);
        Button okButton = new Button(OK_STRING);
        Label questionLabel = new Label(QUESTION_STRING);
        Label instructionsLabel = new Label(INSTRUCTION_STRING);
        Spinner massField = new Spinner(1, 5, mass.get(), 1);
        massField.setEditable(true);
        Spinner angleField = new Spinner(90.0, 120.0, angle.get(), 2.0);
        angleField.setEditable(true);
        Label massLabel = new Label(MASS_STRING);
        Label angleLabel = new Label(ANGLE_STRING);

        //Start First Scene
        HBox questionHBox = new HBox();
        questionHBox.getChildren().addAll(yesButton, noButton, cancelButton1);
        questionHBox.setAlignment(Pos.CENTER_RIGHT);
        questionHBox.setSpacing(8);
        questionHBox.setPadding(new Insets(10));

        BorderPane questionRoot = new BorderPane();
        questionRoot.setCenter(questionLabel);
        questionRoot.setBottom(questionHBox);
        Scene questionScene = new Scene(questionRoot, 400, 100);
        //End First Scene

        //Start Second Scene
        HBox variablesHBox = new HBox();
        variablesHBox.getChildren().addAll(okButton, cancelButton2);
        variablesHBox.setAlignment(Pos.CENTER);
        variablesHBox.setSpacing(8);
        variablesHBox.setPadding(new Insets(10));

        HBox massHBox = new HBox();
        massHBox.getChildren().addAll(massLabel, massField);
        HBox angleHBox = new HBox();
        angleHBox.getChildren().addAll(angleLabel, angleField);
        VBox fieldsBox = new VBox();
        fieldsBox.getChildren().addAll(massHBox, angleHBox);
        fieldsBox.setSpacing(8);

        BorderPane variablesRoot = new BorderPane();
        variablesRoot.setTop(instructionsLabel);
        variablesRoot.setCenter(fieldsBox);
        variablesRoot.setBottom(variablesHBox);
        Scene variablesScene = new Scene(variablesRoot, 500, 150);
        //End Second Scene

        Stage stage = new Stage();
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(variablesScene);
            }
        });
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainAnimation.playFromStart();
                mainAnimation.pause();
                stage.close();
            }
        });
        cancelButton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        cancelButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mass.set((int) massField.getValue());
                angle.set((double) angleField.getValue());
                stage.close();
                MainClass.resetNYC1(mass.get(), angle.get());
            }
        });
        stage.setScene(questionScene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle(RESET_TITLE);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public BorderPane getPane() {
        double endX = 500 - (400 * Math.abs(Math.sin((180 - getAngle()) * Math.PI / 180)));
        double endY = 100 + (400 * Math.abs(Math.cos((180 - getAngle()) * Math.PI / 180)));
        double duration = 5 / getMass();
        Label titleLabel = new Label(NYC_TITLE + LINE_BREAK + NYC_1 + LINE_BREAK + NYC_1_FORMULA);
        Line middleLine = new Line(500, 100, 500, 500);
        middleLine.getStrokeDashArray().addAll(2d, 7d);
        Line topLine = new Line(300, 100, 700, 100);
        Line[] lines = new Line[5];
        for (int i = 0; i < 5; i++) {
            int temp = (i * 100);
            lines[i] = new Line((300 + temp), 100, (340 + temp), 70);
        }
        Line stringLine = new Line(500, 100, endX, endY);
        Rotate stringRotate = new Rotate();
        stringRotate.setPivotX(500);
        stringRotate.setPivotY(100);
        stringLine.getTransforms().add(stringRotate);

        KeyValue kv1 = new KeyValue(stringRotate.angleProperty(), -getAngle(), Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(duration), kv1);
        Timeline stringAnimation = new Timeline(kf);
        stringAnimation.setCycleCount(Animation.INDEFINITE);
        stringAnimation.setAutoReverse(true);

        Circle bob = new Circle(endX, endY, mass.get() * 8);
        Arc arcPath = new Arc(500, 100, 400, 400, 270 - (getAngle() / 2), getAngle());
        arcPath.setFill(null);
        arcPath.setStroke(Color.BLACK);
        arcPath.setStrokeWidth(2);

        PathTransition bobTransition = new PathTransition(Duration.seconds(duration), arcPath, bob);
        bobTransition.setCycleCount(Animation.INDEFINITE);
        bobTransition.setAutoReverse(true);

        mainAnimation = new ParallelTransition(stringAnimation, bobTransition);

        Button start = new Button(START_STRING);
        Button done = new Button(DONE_STRING);
        Button pause = new Button(PAUSE_STRING);
        Button reset = new Button(RESET_STRING);
        Button help = new Button(HELP_STRING);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainAnimation.play();
                start.setDisable(true);
                pause.setDisable(false);
            }
        });
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainClass.returnToMain();
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainAnimation.pause();
                pause.setDisable(true);
                start.setDisable(false);
            }
        });
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pause.fire();
                showResetPopup();
            }
        });

        Alert helpMsg = new Alert(Alert.AlertType.INFORMATION, NYC_1_HELP_MESSAGE, ButtonType.OK);
        helpMsg.setTitle(HELP_STRING);
        helpMsg.setHeaderText(INFORMATION_STRING_SHORT);
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                helpMsg.show();

            }
        });
        
        

        VBox topPane = new VBox();
        topPane.getChildren().add(titleLabel);

        Pane centerPane = new Pane();
        centerPane.getChildren().addAll(middleLine, topLine, stringLine, bob);
        centerPane.getChildren().addAll(lines);

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
        
        tableGraphTab();

        SplitPane bottomPane = new SplitPane();
        bottomPane.getItems().addAll(buttonPane, graphPane);
        bottomPane.setDividerPositions(0.5f);
        bottomPane.setPrefHeight(160);
        bottomPane.setPrefWidth(200);
        bottomPane.setMinHeight(250);

        BorderPane root = new BorderPane();
        root.setTop(topPane);
        root.setBottom(bottomPane);
        root.setCenter(centerPane);
        return root;
    }

    public int getMass() {
        return mass.get();
    }

    public double getAngle() {
        return angle.get();
    }

    public void setMass(int mass) {
        this.mass.set(mass);
    }

    public void setAngle(double angle) {
        this.angle.set(angle);
    }

    public void tableGraphTab() {
        int[] xGraphPoints = new int[10];
        int temp = 1;
        VBox xTabPoints = new VBox();
        xTabPoints.setAlignment(Pos.CENTER);
        Label xLabel = new Label(LENGTH_STRING);
        xTabPoints.getChildren().add(xLabel);
        for (int i = 0; i < xGraphPoints.length; i++) {
            xGraphPoints[i] = temp;
            xTabPoints.getChildren().add(new Label(Integer.toString(xGraphPoints[i])));
            temp++;
        }

        double[] yGraphPoints = new double[10];
        VBox yTabPoints = new VBox();
        yTabPoints.setAlignment(Pos.CENTER);
        Label yLabel = new Label(ANGULAR_VELOCITY_STRING);
        yTabPoints.getChildren().add(yLabel);
        for (int i = 0; i < yGraphPoints.length; i++) {
            yGraphPoints[i] = Math.sqrt(GRAV_ACC / xGraphPoints[i]);
            yTabPoints.getChildren().add(new Label(String.format("%.4f", yGraphPoints[i])));
        }

        HBox xyTable = new HBox();
        xyTable.setSpacing(5);
        xyTable.setAlignment(Pos.CENTER);
        xyTable.getChildren().addAll(xTabPoints, yTabPoints);
        tableTab.setContent(xyTable);

        //Graphing
        NumberAxis xAxis = new NumberAxis(0, 10, 1);
        NumberAxis yAxis = new NumberAxis(0, 3.5, 0.5);

        ScatterChart<Number, Number> scatterGraph = new ScatterChart<>(xAxis, yAxis);
        xAxis.setLabel(LENGTH_STRING);
        yAxis.setLabel(ANGULAR_VELOCITY_STRING);
        scatterGraph.setTitle(NYC_1);
        scatterGraph.setLegendVisible(false);
        XYChart.Series points = new XYChart.Series();
        for (int i = 0; i < xGraphPoints.length; i++) {
            points.getData().add(new XYChart.Data(xGraphPoints[i], yGraphPoints[i]));
        }

        scatterGraph.getData().addAll(points);
        graphTab.setContent(scatterGraph);

    }
}
