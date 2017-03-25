package BeanPackage;

import InterfacePackage.ComLayout;
import static InterfacePackage.InterfConst.*;
import MainPackage.MainClass;
import java.util.Optional;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Bean_NYC2 extends ComLayout {

    IntegerProperty userInputYears = new SimpleIntegerProperty(DEFAULT_YEARS);
    StringProperty isotopeProperty = new SimpleStringProperty();
    Label mainTitle = new Label(NYC_TITLE + LINE_BREAK + NYC_2 + SPACE_STRING + "with" + SPACE_STRING + INITIAL_SAMPLE + SPACE_STRING + G_UNITS);
    Label formulaLabel = new Label(NYC_2_FORMULA);
    Label infoLabel = new Label(INFO_LABEL);
    Label initialDecayLabel = new Label(CARBON_ISOTOPE + SPACE_STRING + "initially at" + SPACE_STRING + INITIAL_SAMPLE + SPACE_STRING + G_UNITS);
    Label halfLifeLabel = new Label(HALF_LIFE_STRING + SPACE_STRING + CARBON_ISOTOPE + SPACE_STRING + "is:" + SPACE_STRING + CARBON_HALF_LIFE
            + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand" + SPACE_STRING + HALF_LIFE_GRAMS + SPACE_STRING + G_UNITS);
    Label userDecayLabel;
    Pane animationPane = new Pane();
    VBox titlePane = new VBox();
    int halfLifeYears = CARBON_HALF_LIFE;
    double lambda;
    int projectedDecay;
    int projectedParticlesAmount;
    int particleRow;
    Circle[] halfLifeParticles;
    Circle[] initialParticles;
    Circle[] userParticles;
    double halfLifeXPoint;
    double initialXPoint;
    double yPoint;
    double userXPoint;
    Rectangle halfLifeRect;
    Rectangle userDecayRect;
    Rectangle initialDecayRect;
    ToggleGroup isotopeGroup = new ToggleGroup();
    RadioButton carbonIsotope;
    RadioButton niobiumIsotope;
    RadioButton thoriumIsotope;
    RadioButton molybdenumIsotope;
    RadioButton curiumIsotope;
    ComboBox usersChoices;
    Tab graphTab;
    Tab tableTab;
    String isotope = CARBON_ISOTOPE;

    @Override
    public BorderPane getPane() {

        carbonIsotope = new RadioButton(CARBON_ISOTOPE + SPACE_STRING);
        carbonIsotope.setToggleGroup(isotopeGroup);
        carbonIsotope.setSelected(true);
        niobiumIsotope = new RadioButton(NIOBIUM_ISOTOPE + SPACE_STRING);
        niobiumIsotope.setToggleGroup(isotopeGroup);
        thoriumIsotope = new RadioButton(THORIUM_ISOTOPE + SPACE_STRING);
        thoriumIsotope.setToggleGroup(isotopeGroup);
        molybdenumIsotope = new RadioButton(MOLYBDENUM_ISOTOPE + SPACE_STRING);
        molybdenumIsotope.setToggleGroup(isotopeGroup);
        curiumIsotope = new RadioButton(CURIUM_ISOTOPE + SPACE_STRING);
        curiumIsotope.setToggleGroup(isotopeGroup);

        //Rectangle Panes
        VBox leftBox = new VBox();
        Group leftGroup = new Group();
        leftGroup.getChildren().add(leftBox);
        VBox centerBox = new VBox();
        Group centerGroup = new Group();
        centerGroup.getChildren().add(centerBox);
        VBox rightBox = new VBox();
        Group rightGroup = new Group();
        rightGroup.getChildren().add(rightBox);

        //Display particles
        initialParticles();
        halfLifeParticles();
        calculations();
        userInputs();

        //Set label for user default decay
        userDecayLabel = new Label(DEFAULT_USER_INPUT + SPACE_STRING + getUserInputYears() + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand"
                + SPACE_STRING + projectedParticlesAmount + SPACE_STRING + G_UNITS);

        //Set labels invisible
        initialDecayLabel.setVisible(false);
        halfLifeLabel.setVisible(false);
        userDecayLabel.setVisible(false);

        //Defining the rectangles
        initialDecayRect = new Rectangle(SQUARE_ONE_POS_X , SQUARE_Y, SQUARE_SIZE, SQUARE_SIZE);
        leftBox.getChildren().addAll(initialDecayRect, initialDecayLabel);
        leftGroup.setLayoutX(SQUARE_ONE_POS_X);
        leftGroup.setLayoutY(SQUARE_Y);
        initialDecayRect.setStroke(Color.GREEN);
        initialDecayRect.setStrokeWidth(STROKE_WIDTH);
        initialDecayRect.setFill(null);
        halfLifeRect = new Rectangle(SQUARE_TWO_POS_X, SQUARE_Y, SQUARE_SIZE, SQUARE_SIZE);
        centerBox.getChildren().addAll(halfLifeRect, halfLifeLabel);
        centerGroup.setLayoutX(SQUARE_TWO_POS_X);
        centerGroup.setLayoutY(SQUARE_Y);
        halfLifeRect.setStroke(Color.YELLOW);
        halfLifeRect.setStrokeWidth(STROKE_WIDTH);
        halfLifeRect.setFill(null);
        userDecayRect = new Rectangle(SQUARE_THREE_POS_X, SQUARE_Y, SQUARE_SIZE, SQUARE_SIZE);
        rightBox.getChildren().addAll(userDecayRect, userDecayLabel);
        rightGroup.setLayoutX(SQUARE_THREE_POS_X);
        rightGroup.setLayoutY(SQUARE_Y);
        userDecayRect.setStroke(Color.RED);
        userDecayRect.setStrokeWidth(STROKE_WIDTH);
        userDecayRect.setFill(null);

        //Initially Rectangle animation
        hideInitial();
        FadeTransition fadeInitialRect = new FadeTransition(TIME, initialDecayRect);
        fadeInitialRect.setFromValue(0);
        fadeInitialRect.setToValue(1);
        fadeInitialRect.setAutoReverse(false);
        fadeInitialRect.setCycleCount(1);
        ParallelTransition parallelInitialRect = new ParallelTransition();
        FadeTransition[] fadeInitialParticles = new FadeTransition[100];
        for (int i = 0; i < 100; i++) {
            fadeInitialParticles[i] = new FadeTransition(TIME, initialParticles[i]);
            fadeInitialParticles[i].setAutoReverse(false);
            fadeInitialParticles[i].setFromValue(0);
            fadeInitialParticles[i].setToValue(1);
            fadeInitialParticles[i].setCycleCount(1);
            parallelInitialRect.getChildren().add(fadeInitialParticles[i]);
        }
        parallelInitialRect.getChildren().add(fadeInitialRect);

        //Half life Rectangle angimation
        hideHalfLife();
        FadeTransition fadeHalfLifeRect = new FadeTransition(TIME, halfLifeRect);
        fadeHalfLifeRect.setFromValue(0);
        fadeHalfLifeRect.setToValue(1);
        fadeHalfLifeRect.setAutoReverse(false);
        fadeHalfLifeRect.setCycleCount(1);
        ParallelTransition parallelHalfLifeRect = new ParallelTransition();
        FadeTransition[] fadeHalfLifeParticles = new FadeTransition[50];
        for (int i = 0; i < 50; i++) {
            fadeHalfLifeParticles[i] = new FadeTransition(TIME, halfLifeParticles[i]);
            fadeHalfLifeParticles[i].setAutoReverse(false);
            fadeHalfLifeParticles[i].setFromValue(0);
            fadeHalfLifeParticles[i].setToValue(1);
            fadeHalfLifeParticles[i].setCycleCount(1);
            parallelHalfLifeRect.getChildren().add(fadeHalfLifeParticles[i]);
        }
        parallelHalfLifeRect.getChildren().add(fadeHalfLifeRect);

        //User Decay Rectangle animation
        hideUserDecay();
        FadeTransition fadeUserRect = new FadeTransition(TIME, userDecayRect);
        fadeUserRect.setFromValue(0);
        fadeUserRect.setToValue(1);
        fadeUserRect.setAutoReverse(false);
        fadeUserRect.setCycleCount(1);
        ParallelTransition parallelUserRect = new ParallelTransition();
        FadeTransition[] fadeUserParticles = new FadeTransition[projectedParticlesAmount];
        for (int i = 0; i < projectedParticlesAmount; i++) {
            fadeUserParticles[i] = new FadeTransition(TIME, userParticles[i]);
            fadeUserParticles[i].setAutoReverse(false);
            fadeUserParticles[i].setFromValue(0);
            fadeUserParticles[i].setToValue(1);
            fadeUserParticles[i].setCycleCount(1);
            parallelUserRect.getChildren().add(fadeUserParticles[i]);
        }
        parallelUserRect.getChildren().add(fadeUserRect);

        //Declare and define button actions
        Button pause = new Button(PAUSE_STRING);
        pause.setDisable(true);

        Button start = new Button(START_STRING);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initialDecayLabel.setVisible(true);
                initialDecayRect.setVisible(true);
                for (int i = 0; i < initialParticles.length; i++) {
                    initialParticles[i].setVisible(true);
                }
                parallelInitialRect.play();
                parallelInitialRect.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        halfLifeLabel.setVisible(true);
                        halfLifeRect.setVisible(true);
                        for (int i = 0; i < 50; i++) {
                            halfLifeParticles[i].setVisible(true);
                        }
                        parallelHalfLifeRect.play();
                    }
                });
                parallelHalfLifeRect.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        userDecayLabel.setVisible(true);
                        userDecayRect.setVisible(true);
                        for (int i = 0; i < projectedParticlesAmount; i++) {
                            userParticles[i].setVisible(true);
                        }
                        parallelUserRect.play();
                    }
                });
                tableGraphTab();
                start.setDisable(true);
                pause.setDisable(false);
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start.setDisable(false);
                pause.setDisable(true);
            }
        });
        Button done = new Button(DONE_STRING);
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainClass.returnToMain();
                start.setDisable(false);
            }
        });

        Button reset = new Button(RESET_STRING);
        Alert resetPopUp = new Alert(Alert.AlertType.CONFIRMATION, "Choose your option: ", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        resetPopUp.setTitle("Changing Sample Isotopes");
        resetPopUp.setHeaderText("Would you like to change the radioactive isotopes?");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initialDecayLabel.setVisible(false);
                halfLifeLabel.setVisible(false);
                userDecayLabel.setVisible(false);
                parallelHalfLifeRect.playFromStart();
                parallelHalfLifeRect.stop();
                parallelInitialRect.playFromStart();
                parallelInitialRect.stop();
                parallelUserRect.playFromStart();
                parallelUserRect.stop();
                hideUserDecay();
                tableTab.setContent(null);
                graphTab.setContent(null);

                Optional<ButtonType> result = resetPopUp.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    pause.fire();
                    removeParticles();
                    resetPopUp.close();
                    showResetPopup();
                    hideUserDecay();

                } else if (result.isPresent() && result.get() == ButtonType.NO) {
                    pause.fire();
                    hideUserDecay();
                } else {

                }

            }
        });

        Button help = new Button(HELP_STRING);
        Alert helpMsg = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.OK);
        helpMsg.setTitle(HELP_STRING);
        helpMsg.setContentText(HELP_MESSAGE_NYC_2);
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                helpMsg.show();

            }
        });
        //Add to pane
        titlePane.getChildren().addAll(mainTitle, formulaLabel, infoLabel);
        animationPane.getChildren().addAll(leftGroup, centerGroup, rightGroup);

        //Add panes to main pane
        BorderPane root = new BorderPane();
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(start, done, pause, reset, help);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);

        //Declare and define graphs and tab Pane
        TabPane graphPane = new TabPane();
        graphTab = new Tab("Graph Tab");
        tableTab = new Tab("Table Tab");
        graphTab.setClosable(false);
        tableTab.setClosable(false);
        graphPane.getTabs().addAll(graphTab, tableTab);

        //Bottom pane containing button and graphs
        SplitPane bottomPane = new SplitPane();
        bottomPane.setMinHeight(250);
        bottomPane.getItems().addAll(buttonPane, graphPane);
        bottomPane.setDividerPositions(0.5f);
        bottomPane.setPrefHeight(160);
        bottomPane.setPrefWidth(200);

        //Set positions
        root.setBottom(bottomPane);
        root.setCenter(animationPane);
        root.setTop(titlePane);

        return root;
    }

    public void showResetPopup() {
        Dialog changeValues = new Dialog();
        changeValues.setContentText("Pick Isotopes");
        changeValues.setHeaderText("Choose an isotope to see its radioactive decay: ");
        changeValues.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        HBox radioActiveIsotopes = new HBox();
        HBox inputYears = new HBox();
        VBox alignmentPane = new VBox();

        Label finalYears = new Label(LABEL_FINAL_YEARS_INFO);
        usersChoices = new ComboBox();
        usersChoices.getItems().addAll(1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000);
        usersChoices.setValue(1000);
        Label units = new Label(YEARS_UNIT);

        radioActiveIsotopes.getChildren().addAll(carbonIsotope, niobiumIsotope, thoriumIsotope, molybdenumIsotope, curiumIsotope);
        radioActiveIsotopes.setSpacing(5.0);
        inputYears.getChildren().addAll(finalYears, usersChoices, units);
        inputYears.setSpacing(5.0);
        alignmentPane.getChildren().addAll(radioActiveIsotopes, inputYears);
        alignmentPane.setSpacing(5);

        changeValues.getDialogPane().setContent(alignmentPane);
        Optional<ButtonType> result = changeValues.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            setUserInputYears(Integer.parseInt(usersChoices.getValue().toString()));
            changeValues.close();
            if (carbonIsotope.isSelected()) {
                setIsotopeProperty(CARBON_ISOTOPE);
                initialDecayLabel.setText(getIsotopeProperty() + SPACE_STRING + "initially at" + SPACE_STRING + INITIAL_SAMPLE + SPACE_STRING + G_UNITS);
                halfLifeLabel.setText(HALF_LIFE_STRING + SPACE_STRING + getIsotopeProperty() + SPACE_STRING + "is:" + SPACE_STRING + CARBON_HALF_LIFE
                        + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand" + SPACE_STRING + HALF_LIFE_GRAMS + SPACE_STRING + G_UNITS);
                halfLifeYears = CARBON_HALF_LIFE;
                isotope = CARBON_ISOTOPE;
                carbonIsotope.setSelected(true);
                calculations();
                userInputs();
                userDecayLabel.setText(USER_YEARS + SPACE_STRING + getUserInputYears() + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand"
                        + SPACE_STRING + projectedParticlesAmount + SPACE_STRING + G_UNITS);
            }
            if (thoriumIsotope.isSelected()) {
                setIsotopeProperty(THORIUM_ISOTOPE);
                initialDecayLabel.setText(getIsotopeProperty() + SPACE_STRING + "initially at" + SPACE_STRING + INITIAL_SAMPLE + SPACE_STRING + G_UNITS);
                halfLifeLabel.setText(HALF_LIFE_STRING + SPACE_STRING + getIsotopeProperty() + SPACE_STRING + "is:" + SPACE_STRING + THORIUM_HALF_LIFE
                        + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand" + SPACE_STRING + HALF_LIFE_GRAMS + SPACE_STRING + G_UNITS);
                halfLifeYears = THORIUM_HALF_LIFE;
                isotope = THORIUM_ISOTOPE;
                thoriumIsotope.setSelected(true);
                calculations();
                userInputs();
                userDecayLabel.setText(USER_YEARS + SPACE_STRING + getUserInputYears() + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand"
                        + SPACE_STRING + projectedParticlesAmount + SPACE_STRING + G_UNITS);
            }
            if (molybdenumIsotope.isSelected()) {
                setIsotopeProperty(MOLYBDENUM_ISOTOPE);
                initialDecayLabel.setText(getIsotopeProperty() + SPACE_STRING + "initially at" + SPACE_STRING + INITIAL_SAMPLE + SPACE_STRING + G_UNITS);
                halfLifeLabel.setText(HALF_LIFE_STRING + SPACE_STRING + getIsotopeProperty() + SPACE_STRING + "is:" + SPACE_STRING
                        + MOLYBDENUM_HALF_LIFE + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand" + SPACE_STRING + HALF_LIFE_GRAMS + SPACE_STRING + G_UNITS);
                halfLifeYears = MOLYBDENUM_HALF_LIFE;
                isotope = MOLYBDENUM_ISOTOPE;
                molybdenumIsotope.setSelected(true);
                calculations();
                userInputs();
                userDecayLabel.setText(USER_YEARS + SPACE_STRING + getUserInputYears() + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand"
                        + SPACE_STRING + projectedParticlesAmount + SPACE_STRING + G_UNITS);
            }
            if (curiumIsotope.isSelected()) {
                setIsotopeProperty(CURIUM_ISOTOPE);
                initialDecayLabel.setText(getIsotopeProperty() + SPACE_STRING + "initially at" + SPACE_STRING + INITIAL_SAMPLE + SPACE_STRING + G_UNITS);
                halfLifeLabel.setText(HALF_LIFE_STRING + SPACE_STRING + getIsotopeProperty() + SPACE_STRING + "is:" + SPACE_STRING + CURIUM_HALF_LIFE
                        + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand" + SPACE_STRING + HALF_LIFE_GRAMS + SPACE_STRING + G_UNITS);
                halfLifeYears = CURIUM_HALF_LIFE;
                isotope = CURIUM_ISOTOPE;
                curiumIsotope.setSelected(true);
                calculations();
                userInputs();
                userDecayLabel.setText(USER_YEARS + SPACE_STRING + getUserInputYears() + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand"
                        + SPACE_STRING + projectedParticlesAmount + SPACE_STRING + G_UNITS);
            }
            if (niobiumIsotope.isSelected()) {
                setIsotopeProperty(NIOBIUM_ISOTOPE);
                initialDecayLabel.setText(getIsotopeProperty() + SPACE_STRING + "initially at" + SPACE_STRING + INITIAL_SAMPLE + SPACE_STRING + G_UNITS);
                halfLifeLabel.setText(HALF_LIFE_STRING + SPACE_STRING + getIsotopeProperty() + SPACE_STRING + "is:" + SPACE_STRING + NIOBIUM_HALF_LIFE
                        + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand" + SPACE_STRING + HALF_LIFE_GRAMS + SPACE_STRING + G_UNITS);
                halfLifeYears = NIOBIUM_HALF_LIFE;
                isotope = NIOBIUM_ISOTOPE;
                niobiumIsotope.setSelected(true);
                calculations();
                userInputs();
                userDecayLabel.setText(USER_YEARS + SPACE_STRING + getUserInputYears() + SPACE_STRING + YEARS_UNIT + SPACE_STRING + "\nand"
                        + SPACE_STRING + projectedParticlesAmount + SPACE_STRING + SPACE_STRING + G_UNITS);
            }

        } else {
            changeValues.close();
        }

    }

    public void hideInitial() {
        initialDecayRect.setVisible(false);
        for (int i = 0; i < initialParticles.length; i++) {
            initialParticles[i].setVisible(false);
        }
    }

    public void hideHalfLife() {
        halfLifeRect.setVisible(false);
        for (int i = 0; i < halfLifeParticles.length; i++) {
            halfLifeParticles[i].setVisible(false);
        }
    }

    public void hideUserDecay() {
        userDecayRect.setVisible(false);
        for (int i = 0; i < projectedParticlesAmount; i++) {
            userParticles[i].setVisible(false);
        }
    }

    public void removeParticles() {
        for (int i = 0; i < projectedParticlesAmount; i++) {
            animationPane.getChildren().remove(userParticles[i]);
        }
    }

    public void userInputs() {
        userXPoint = 739.75;
        userParticles = new Circle[projectedParticlesAmount];
        int temp3 = 0;
        if (projectedParticlesAmount % 10 != 0) {
            particleRow = (projectedParticlesAmount + 1);
        } else {
            particleRow = projectedParticlesAmount;
        }
        if (projectedParticlesAmount <= 10) {
            for (int i = 0; i < particleRow; i++) {
                if (temp3 == projectedParticlesAmount) {
                    break;
                } else {
                    userParticles[temp3] = new Circle(userXPoint, yPoint, 9.75);
                    userXPoint += 24.5;
                    temp3++;
                }
            }
        } else {
            for (int i = 0; i < particleRow; i++) {
                if (i > 0) {
                    yPoint += 24.5;
                } else {
                    yPoint = 139.75;
                }
                for (int j = 0; j < 10; j++) {
                    if (temp3 == projectedParticlesAmount) {
                        break;
                    } else if (userXPoint > 935.75 + 24.5) {
                        userXPoint = 739.75;
                        userParticles[temp3] = new Circle(userXPoint, yPoint, 9.75);
                        userXPoint += 24.5;
                        temp3++;
                    } else {
                        userParticles[temp3] = new Circle(userXPoint, yPoint, 9.75);
                        userXPoint += 24.5;
                        temp3++;
                    }
                }
            }
        }

        for (int i = 0; i < projectedParticlesAmount; i++) {
            animationPane.getChildren().add(userParticles[i]);
        }
    }

    public void halfLifeParticles() {
        halfLifeXPoint = 389.75;
        halfLifeParticles = new Circle[50];
        int temp2 = 0;
        for (int i = 0; i < 5; i++) {
            if (i > 0) {
                yPoint += 24.5;
            } else {
                yPoint = 139.75;
            }

            for (int j = 0; j < 10; j++) {
                if (halfLifeXPoint > 585.75 + 24.5) {
                    halfLifeXPoint = 389.75;
                    halfLifeParticles[temp2] = new Circle(halfLifeXPoint, yPoint, 9.75);
                    halfLifeXPoint += 24.5;
                    temp2++;
                } else {
                    halfLifeParticles[temp2] = new Circle(halfLifeXPoint, yPoint, 9.75);
                    halfLifeXPoint += 24.5;
                    temp2++;
                }
            }
        }

        for (int i = 0; i < 50; i++) {
            animationPane.getChildren().add(halfLifeParticles[i]);
        }
    }

    public void initialParticles() {
        initialXPoint = 39.75;
        yPoint = 139.75;
        int temp = 0;
        initialParticles = new Circle[100];

        for (int i = 0; i < 10; i++) {
            if (i > 0) {
                yPoint += 24.5;
            } else {
                yPoint = 139.75;
            }

            for (int j = 0; j < 10; j++) {
                if (initialXPoint > 235.75 + 24.5) {
                    initialXPoint = 39.75;
                    initialParticles[temp] = new Circle(initialXPoint, yPoint, 9.75);
                    animationPane.getChildren().add(initialParticles[temp]);
                    initialXPoint += 24.5;
                    temp++;
                } else {
                    initialParticles[temp] = new Circle(initialXPoint, yPoint, 9.75);
                    initialXPoint += 24.5;
                    animationPane.getChildren().add(initialParticles[temp]);
                    temp++;
                }
            }
        }
    }

    public void calculations() {
        lambda = -0.693 / halfLifeYears;
        projectedDecay = (int) (INITIAL_SAMPLE * Math.pow(E_CONSTANT, lambda * userInputYears.getValue()));
        projectedParticlesAmount = (int) projectedDecay;
        particleRow = projectedParticlesAmount / 10;
    }

    public void tableGraphTab() {
        //Table of Values
        int[] xGraphPoints = new int[10];
        int temp = 1000;
        VBox xTabPoints = new VBox();
        xTabPoints.setAlignment(Pos.CENTER);
        Label xTableLabel = new Label(YEARS_UNIT.toUpperCase());
        xTabPoints.getChildren().add(xTableLabel);
        for (int i = 0; i < xGraphPoints.length; i++) {
            xGraphPoints[i] = temp;
            xTabPoints.getChildren().add(new Label(Integer.toString(xGraphPoints[i])));
            temp += 1000;
        }

        int[] yGraphPoints = new int[10];
        VBox yTabPoints = new VBox();
        yTabPoints.setAlignment(Pos.CENTER);
        Label yTableLabel = new Label(G_UNITS.toUpperCase());
        yTabPoints.getChildren().add(yTableLabel);
        for (int i = 0; i < yGraphPoints.length; i++) {
            yGraphPoints[i] = (int) (INITIAL_SAMPLE * Math.pow(E_CONSTANT, lambda * xGraphPoints[i]));
            yTabPoints.getChildren().add(new Label(Integer.toString(yGraphPoints[i])));
        }

        HBox xyTable = new HBox();
        xyTable.setSpacing(5);
        xyTable.setAlignment(Pos.CENTER);
        xyTable.getChildren().addAll(xTabPoints, yTabPoints);
        tableTab.setContent(xyTable);

        //Graphing
        NumberAxis xAxis = new NumberAxis(0, 10000, 1000);
        NumberAxis yAxis = new NumberAxis(0, 100, 20);

        ScatterChart<Number, Number> scatterGraph = new ScatterChart<>(xAxis, yAxis);
        xAxis.setLabel(YEARS_UNIT);
        yAxis.setLabel(G_UNITS);
        scatterGraph.setTitle(NYC_2 + " of " + isotope);
        scatterGraph.setLegendVisible(false);
        XYChart.Series points = new XYChart.Series();
        for (int i = 0; i < xGraphPoints.length; i++) {
            points.getData().add(new XYChart.Data(xGraphPoints[i], yGraphPoints[i]));
        }

        scatterGraph.getData().addAll(points);
        graphTab.setContent(scatterGraph);

    }

    private String getIsotopeProperty() {
        return isotopeProperty.getValue();
    }

    private void setIsotopeProperty(String selection) {
        isotopeProperty.setValue(selection);
    }

    private int getUserInputYears() {
        return userInputYears.getValue();
    }

    private void setUserInputYears(int years) {
        userInputYears.setValue(years);
    }

}
