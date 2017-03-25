package BeanPackage;

import InterfacePackage.ComLayout;

import MainPackage.MainClass;
import java.io.InputStream;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.util.Duration;

public class Bean_NYB1 extends ComLayout {

    DoubleProperty resistance = new SimpleDoubleProperty(10);
    DoubleProperty voltage = new SimpleDoubleProperty(4);

    Button start;
    Button done;
    Button pause;
    Button reset;
    Button help;
    Label chooseResistance;
    Label chooseVoltage;
    ToggleGroup resistanceGroup;
    RadioButton resistance1;
    RadioButton resistance2;
    RadioButton resistance3;
    ToggleGroup voltageGroup;
    RadioButton voltage1;
    RadioButton voltage2;
    RadioButton voltage3;
    Label formulaInfo;
    Rectangle circuit;
    Rectangle circuitDesign1;
    Rectangle circuitDesign2;
    Circle[] electrons;
    Polygon animationPath;
    PathTransition[] pathTransition;
    LineChart<Number, Number> lineChart;
    XYChart.Series series;
    Image lightBulb;
    ImageView lightBulbImageView;
    Image noLightBulb;
    ImageView noLightBulbImageView;
    Image battery;
    ImageView batteryImageView;
    ScatterChart<Number, Number> scatterGraph;
    Tab graphTab;
    Tab tableTab;
    boolean isAnimationReady;

    InputStream BATTERY_URL = getClass().getResourceAsStream("/battery.png");
    InputStream LIGHTED_LIGHT_BULB_URL = getClass().getResourceAsStream("/lightBulb_Light.png");
    InputStream UNLIGHTED_LIGHT_BULB_URL = getClass().getResourceAsStream("/lightBulb_NoLight.jpg");

    @Override
    public BorderPane getPane() {
        start = new Button(START_STRING);
        done = new Button(DONE_STRING);
        pause = new Button(PAUSE_STRING);
        reset = new Button(RESET_STRING);
        help = new Button(HELP_STRING);

        BorderPane root = new BorderPane();

        Alert helpMsg = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.OK);
        helpMsg.setTitle(HELP_STRING);
        helpMsg.setContentText(HELP_MESSAGE);
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

        GridPane radioButtonsGroup = new GridPane();

        chooseResistance = new Label(CHOOSE_RESISTANCE);
        chooseResistance.setFont(Font.font("IMPACT", FontPosture.REGULAR, 15));

        chooseVoltage = new Label(CHOOSE_VOLTAGE);
        chooseVoltage.setFont(Font.font("IMPACT", FontPosture.REGULAR, 15));

        resistanceGroup = new ToggleGroup();
        resistance1 = new RadioButton(RESISTANCE_1);
        resistance1.setToggleGroup(resistanceGroup);

        resistance2 = new RadioButton(RESISTANCE_2);
        resistance2.setToggleGroup(resistanceGroup);

        resistance3 = new RadioButton(RESISTANCE_3);
        resistance3.setToggleGroup(resistanceGroup);

        voltageGroup = new ToggleGroup();
        voltage1 = new RadioButton(VOLTAGE_1);
        voltage1.setToggleGroup(voltageGroup);

        voltage2 = new RadioButton(VOLTAGE_2);
        voltage2.setToggleGroup(voltageGroup);

        voltage3 = new RadioButton(VOLTAGE_3);
        voltage3.setToggleGroup(voltageGroup);

        formulaInfo = new Label(NYB_1 + NYB_1_FORMULA);
        formulaInfo.setFont(Font.font("IMPACT", FontPosture.REGULAR, 15));

        radioButtonsGroup.addColumn(0, formulaInfo);
        radioButtonsGroup.addColumn(1, chooseResistance, resistance1, resistance2, resistance3);
        radioButtonsGroup.addColumn(2, chooseVoltage, voltage1, voltage2, voltage3);
        radioButtonsGroup.hgapProperty().setValue(50);
        radioButtonsGroup.vgapProperty().setValue(10);

        root.setTop(radioButtonsGroup);

        StackPane animation = new StackPane();

        showingImages();

        createCircuit();
        initiateAnimation();
        StackPane.setAlignment(circuitDesign2, Pos.TOP_CENTER);

        electrons = new Circle[NUMBER_OF_ELECTRONS];

        createAnimationPath();

        Pane electronsPane = new Pane();
        pathTransition = new PathTransition[NUMBER_OF_ELECTRONS];

        for (int i = 0; i < electrons.length; i++) {
            electrons[i] = new Circle();
            electrons[i].setRadius(RADIUS);
            electrons[i].setFill(Color.YELLOW);

            electrons[i].setLayoutX(492.5);
            electrons[i].setLayoutY(30.0);
            electrons[i].setVisible(false);

            pathTransition[i] = new PathTransition(Duration.millis(ELECTRONS_ANIM_DURATION), animationPath, electrons[i]);
            pathTransition[i].setInterpolator(Interpolator.LINEAR);
            pathTransition[i].setCycleCount(Timeline.INDEFINITE);
            pathTransition[i].setAutoReverse(false);
            pathTransition[i].setDelay(Duration.millis(ANIMATION_DELAY * i));

            electronsPane.getChildren().addAll(electrons[i]);

        }

        animation.getChildren().addAll(circuit, circuitDesign1, circuitDesign2, lightBulbImageView, noLightBulbImageView, batteryImageView, electronsPane);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {
                pause.setDisable(false);
                isAnimationReady = false;
                tableGraphTab();
                startAnimation();

            }
        });

        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent Event) {

                MainClass.returnToMain();

            }
        });

        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {

                pauseAnimation();

            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Event) {

                resetAnimation();
                initiateAnimation();

            }
        });

        root.setLeft(animation);

        return root;

    }

    public void showingImages() {

        //File file = new File(BATTERY_URL);
        //battery = new Image(file.toURI().toString());
        battery = new Image(BATTERY_URL);
        batteryImageView = new ImageView();
        batteryImageView.setFitHeight(75);
        batteryImageView.setFitWidth(75);
        batteryImageView.setImage(battery);
        StackPane.setAlignment(batteryImageView, Pos.TOP_CENTER);

        //File file1 = new File(LIGHTED_LIGHT_BULB_URL);
        //lightBulb = new Image(file1.toURI().toString());
        lightBulb = new Image(LIGHTED_LIGHT_BULB_URL);
        lightBulbImageView = new ImageView();
        lightBulbImageView.setFitHeight(80);
        lightBulbImageView.setFitWidth(50);
        lightBulbImageView.setImage(lightBulb);
        StackPane.setAlignment(lightBulbImageView, Pos.BOTTOM_CENTER);
        lightBulbImageView.setVisible(false);

        //File file2 = new File(UNLIGHTED_LIGHT_BULB_URL);
        //noLightBulb = new Image(file2.toURI().toString());
        noLightBulb = new Image(UNLIGHTED_LIGHT_BULB_URL);
        noLightBulbImageView = new ImageView();
        noLightBulbImageView.setFitHeight(75);
        noLightBulbImageView.setFitWidth(75);
        noLightBulbImageView.setImage(noLightBulb);
        StackPane.setAlignment(noLightBulbImageView, Pos.BOTTOM_CENTER);
        noLightBulbImageView.setVisible(true);

    }

    public void createCircuit() {

        circuit = new Rectangle(0, 0, 1000, 625);
        circuit.setFill(Color.DIMGRAY);

        circuitDesign1 = new Rectangle(0, 0, 970, 595);
        circuitDesign1.setFill(Color.WHITESMOKE);

        circuitDesign2 = new Rectangle(0, 0, 35, 20);
        circuitDesign2.setFill(Color.WHITESMOKE);

    }

    public void createAnimationPath() {

        animationPath = new Polygon();
        animationPath.getPoints().addAll(new Double[]{
            6.0, 10.0,
            -16.0, 10.0,
            -16.0, -20.0,
            -486.5, -20.0,
            -486.5, 590.0,
            0.0, 590.0,
            0.0, 540.0,
            16.0, 540.0,
            16.0, 590.0,
            498.5, 590.0,
            498.5, -20.0,
            31.0, -20.0,
            31.0, 10.0
        });

    }

    public void initiateAnimation() {
        start.setDisable(true);
        pause.setDisable(true);
        reset.setDisable(true);
        resistanceGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                RadioButton resistanceRadioButton = (RadioButton) new_toggle.getToggleGroup().getSelectedToggle();
                String choosenResistance = resistanceRadioButton.getText();
                double resistance = Double.parseDouble(choosenResistance);
                setResistance(resistance);

                if (resistanceGroup.getSelectedToggle().isSelected() == true) {

                    resistance1.setDisable(true);
                    resistance2.setDisable(true);
                    resistance3.setDisable(true);
                }
                voltageGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                    @Override
                    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                        RadioButton voltageRadioButton = (RadioButton) new_toggle.getToggleGroup().getSelectedToggle();
                        String choosenVoltage = voltageRadioButton.getText();
                        double voltage = Double.parseDouble(choosenVoltage);
                        setVoltage(voltage);

                        if (voltageGroup.getSelectedToggle().isSelected() == true) {

                            voltage1.setDisable(true);
                            voltage2.setDisable(true);
                            voltage3.setDisable(true);
                        }

                        if ((voltageGroup.getSelectedToggle().isSelected() == true) && (resistanceGroup.getSelectedToggle().isSelected() == true)) {
                            isAnimationReady = true;
                            start.setDisable(false);
                        }

                    }
                });
            }
        });
    }

    public void tableGraphTab() {

        double[] xGraphPoints = new double[10];
        double temp = 2;
        VBox xTabPoints = new VBox();
        xTabPoints.setAlignment(Pos.CENTER);
        Label xLabel = new Label(VOLTAGE);
        xTabPoints.getChildren().add(xLabel);
        for (int i = 0; i < xGraphPoints.length; i++) {
            xGraphPoints[i] = temp;
            xTabPoints.getChildren().add(new Label(Double.toString(xGraphPoints[i])));
            temp += 2;
        }
        double[] yGraphPoints = new double[10];
        VBox yTabPoints = new VBox();
        yTabPoints.setAlignment(Pos.CENTER);
        Label yLabel = new Label(CURRENT_IN_AMPS);
        yTabPoints.getChildren().add(yLabel);
        for (int i = 0; i < xGraphPoints.length; i++) {
            yGraphPoints[i] = (double) (xGraphPoints[i] / getResistance());
            yTabPoints.getChildren().add(new Label(Double.toString(yGraphPoints[i])));
        }
        HBox xyTable = new HBox();
        xyTable.setSpacing(5);
        Label result = new Label(YOUR_CHOICES + getVoltage() / getResistance());
        xyTable.setAlignment(Pos.CENTER);
        xyTable.getChildren().addAll(xTabPoints, yTabPoints, result);
        tableTab.setContent(xyTable);

        //Graphing
        NumberAxis xAxis = new NumberAxis(0, 20, 1);
        NumberAxis yAxis = new NumberAxis(0, 1, 0.1);

        scatterGraph = new ScatterChart<>(xAxis, yAxis);
        xAxis.setLabel(VOLTAGE);
        yAxis.setLabel(CURRENT);
        scatterGraph.setTitle(NYB_1);
        scatterGraph.setLegendVisible(false);
        XYChart.Series points = new XYChart.Series();
        for (int i = 0; i < xGraphPoints.length; i++) {
            points.getData().add(new XYChart.Data(xGraphPoints[i], yGraphPoints[i]));
        }

        scatterGraph.getData().addAll(points);
        graphTab.setContent(scatterGraph);
    }

    private double getVoltage() {
        return voltage.getValue();
    }

    private void setVoltage(double voltageValue) {
        voltage.setValue(voltageValue);
    }

    private double getResistance() {
        return resistance.getValue();
    }

    private void setResistance(double voltageValue) {
        resistance.setValue(voltageValue);
    }

    public void startAnimation() {

        for (int i = 0; i < electrons.length; i++) {

            pathTransition[i].play();
            electrons[i].setVisible(true);
        }

        noLightBulbImageView.setVisible(false);
        lightBulbImageView.setVisible(true);
        start.setDisable(true);
        reset.setDisable(false);
    }

    public void pauseAnimation() {

        for (int i = 0; i < electrons.length; i++) {

            pathTransition[i].pause();
            start.setDisable(false);
        }
    }

    public void resetAnimation() {

        for (int i = 0; i < electrons.length; i++) {

            pathTransition[i].jumpTo(Duration.ZERO);
            pathTransition[i].stop();

            electrons[i].setVisible(false);
        }
        noLightBulbImageView.setVisible(true);
        lightBulbImageView.setVisible(false);
        resistance1.setDisable(false);
        resistance2.setDisable(false);
        resistance3.setDisable(false);
        resistance1.setSelected(false);
        resistance2.setSelected(false);
        resistance3.setSelected(false);
        voltage1.setDisable(false);
        voltage2.setDisable(false);
        voltage3.setDisable(false);
        voltage1.setSelected(false);
        voltage2.setSelected(false);
        voltage3.setSelected(false);
        reset.setDisable(true);
        start.setDisable(false);
        scatterGraph.getData().clear();
        if (isAnimationReady == true) {
            tableGraphTab();
        }
    }

    public void showResetPopup() {

    }
}
