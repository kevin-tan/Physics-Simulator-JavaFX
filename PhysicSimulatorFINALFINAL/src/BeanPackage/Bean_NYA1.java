package BeanPackage;

import InterfacePackage.ComLayout;
import static InterfacePackage.InterfConst.GRAV_ACC;
import MainPackage.MainClass;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Bean_NYA1 extends ComLayout {

    int rectSize;
    int x1;
    int x2;
    int y1;
    IntegerProperty mass = new SimpleIntegerProperty();
    DoubleProperty angle = new SimpleDoubleProperty();
    double radAngle;
    double y2;
    double rectShiftX;
    double rectShiftY;
    double rectInitX;
    double rectInitY;
    double duration;
    Label titleLabel1;
    Label titleLabel2;
    Line groundLine;
    Line inclinedLine;
    Line verticalLine;
    Rectangle square;
    Label fgLabel;
    Label nLabel;
    Label ffLabel;
    ParallelTransition mainAnimation;
    Tab graphTab;
    Tab tableTab;

    public Bean_NYA1(int mass, double angle) {
        this.mass.set(mass);
        this.angle.set(angle);
        this.radAngle = this.angle.get() * Math.PI / 180;
        this.rectSize = 45;
        this.x1 = 250;
        this.x2 = 750;
        this.y1 = 500;
        this.y2 = y1 - (Math.tan(radAngle) * (x2 - x1));
        this.rectShiftX = rectSize * Math.cos(radAngle);
        this.rectShiftY = rectSize * Math.sin(radAngle);
        this.rectInitX = x2 - rectSize;
        this.rectInitY = y2 - rectSize;
        this.duration = ((1/radAngle) + (1/getMass())) * 1.5;
    }

    public void showResetPopup() {
        Button yesButton = new Button(YES_STRING);
        Button noButton = new Button(NO_STRING);
        Button cancelButton1 = new Button(CANCEL_STRING);
        Button cancelButton2 = new Button(CANCEL_STRING);
        Button okButton = new Button(OK_STRING);
        Label questionLabel = new Label(QUESTION_STRING);
        Label instructionsLabel = new Label(INSTRUCTION_STRING);
        Spinner massField = new Spinner(5, 1000, mass.get(), 5);
        massField.setEditable(true);
        Spinner angleField = new Spinner(5.0, 45.0, angle.get(), 1.0);
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
                MainClass.resetNYA1(mass.get(), angle.get());
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
        titleLabel1 = new Label(NYA_TITLE + LINE_BREAK + NYA_1);
        titleLabel2 = new Label(NYA_1_FORMULA);
        groundLine = new Line(x1, y1, x2, y1);
        inclinedLine = new Line(x1, y1, x2, y2);
        verticalLine = new Line(x2, y1, x2, y2);
        square = new Rectangle(rectInitX, rectInitY, rectSize, rectSize);
        square.getTransforms().add(new Rotate(-angle.get(), x2, y2));
        square.setFill(Color.LIGHTSTEELBLUE);
        square.setStrokeWidth(5);
        Label massLabel = new Label(Integer.toString(mass.get()) + "g");
        massLabel.setTranslateX(square.getX());
        massLabel.setTranslateY(square.getY() + rectSize/2);

        TranslateTransition squareAnimation = new TranslateTransition(Duration.seconds(duration), square);
        squareAnimation.setFromX(0);
        squareAnimation.setFromY(0);
        squareAnimation.setToX((-x2 + x1) + rectShiftX);
        squareAnimation.setToY((y1 - y2) - rectShiftY);
        
        FadeTransition massFade = new FadeTransition(Duration.seconds(1), massLabel);
        massFade.setFromValue(1);
        massFade.setToValue(0);

        mainAnimation = new ParallelTransition();
        mainAnimation.getChildren().addAll(squareAnimation, massFade);

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
                if (mainAnimation.getStatus().equals(Animation.Status.RUNNING)) {
                    mainAnimation.pause();
                }
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

        Alert helpMsg = new Alert(Alert.AlertType.INFORMATION, NYB_1_HELP_MESSAGE, ButtonType.OK);
        helpMsg.setTitle(HELP_STRING);
        helpMsg.setHeaderText(INFORMATION_STRING_SHORT);
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                helpMsg.show();
            }
        });

        VBox topPane = new VBox();
        topPane.getChildren().addAll(titleLabel1, titleLabel2);

        Pane centerPane = new Pane();
        centerPane.getChildren().addAll(groundLine, inclinedLine, verticalLine, square, massLabel);

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
        root.setBottom(bottomPane);
        root.setCenter(centerPane);
        root.setTop(topPane);

        return root;
    }
    public int getMass(){
        return mass.get();
    }
    public double getAngle(){
        return angle.get();
    }
    public void setMass(int mass){
        this.mass.set(mass);
    }
    public void setAngle(double angle){
        this.angle.set(angle);
    }
    public void tableGraphTab() {
        int[] xGraphPoints = new int[10];
        int temp = 1;
        VBox xTabPoints = new VBox();
        xTabPoints.setAlignment(Pos.CENTER);
        Label xLabel = new Label("Mass (in kg)");
        xTabPoints.getChildren().add(xLabel);
        for (int i = 0; i < xGraphPoints.length; i++) {
            xGraphPoints[i] = temp;
            xTabPoints.getChildren().add(new Label(Integer.toString(xGraphPoints[i])));
            temp++;
        }
        double[] yGraphPoints = new double[10];
        VBox yTabPoints = new VBox();
        yTabPoints.setAlignment(Pos.CENTER);
        Label yLabel = new Label("Force (in N)");
        yTabPoints.getChildren().add(yLabel);
        for (int i = 0; i < yGraphPoints.length; i++) {
            yGraphPoints[i] = GRAV_ACC * xGraphPoints[i];
            yTabPoints.getChildren().add(new Label(String.format("%.2f", yGraphPoints[i])));
        }

        HBox xyTable = new HBox();
        xyTable.setSpacing(5);
        xyTable.setAlignment(Pos.CENTER);
        xyTable.getChildren().addAll(xTabPoints, yTabPoints);
        tableTab.setContent(xyTable);

        //Graphing
        NumberAxis xAxis = new NumberAxis(0, 10, 1);
        NumberAxis yAxis = new NumberAxis(0, 98, 5);

        ScatterChart<Number, Number> scatterGraph = new ScatterChart<>(xAxis, yAxis);
        xAxis.setLabel("Mass (in g)");
        yAxis.setLabel("Force (in N)");
        scatterGraph.setTitle(NYA_1);
        scatterGraph.setLegendVisible(false);
        XYChart.Series points = new XYChart.Series();
        for (int i = 0; i < xGraphPoints.length; i++) {
            points.getData().add(new XYChart.Data(xGraphPoints[i], yGraphPoints[i]));
        }

        scatterGraph.getData().addAll(points);
        graphTab.setContent(scatterGraph);

    }
}
