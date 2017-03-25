package BeanPackage;

import InterfacePackage.ComLayout;
import MainPackage.MainClass;
import java.util.Optional;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Bean_NYB2 extends ComLayout {

    Color partOneColor = POS_CHARGE_COLOR; //change color depending on charge sign
    Color partTwoColor = NEG_CHARGE_COLOR; //change color depending on charge sign
    Label particleOne = new Label(PARTICLE_ONE + SPACE_STRING + DEFAULT_CHARGE_SIGN_ONE + DEFAULT_CHARGE_ONE + SPACE_STRING + UNITS_CHARGE);
    Label particleTwo = new Label(PARTICLE_TWO + SPACE_STRING + DEFAULT_CHARGE_SIGN_TWO + DEFAULT_CHARGE_TWO + SPACE_STRING + UNITS_CHARGE);
    Label mainTitle = new Label(NYB_TITLE + LINE_BREAK + NYB_2);
    Label formulaLabel = new Label(NYB_2_FORMULA);
    Pane animationPane = new Pane();
    Node secondArrow;
    Node firstArrow;
    Polygon animationArrowOne;
    Polygon animationArrowTwo;
    Circle Particle_1;
    Circle Particle_2;
    ScaleTransition firstArrowScaleAnimation;
    ScaleTransition secondArrowScaleAnimation;
    TranslateTransition transFirstArrow;
    TranslateTransition transSecondArrow;
    ComboBox chargeSignsOne;
    ComboBox chargeSignsTwo;
    int circleRadius;
    int finalDest;
    IntegerProperty userInputVarOne = new SimpleIntegerProperty();
    IntegerProperty userInputVarTwo = new SimpleIntegerProperty();
    StringProperty signPropertyParticleTwo = new SimpleStringProperty();
    StringProperty signPropertyParticleOne = new SimpleStringProperty();
    TextField variableTwoInput;
    Spinner varOneInput;
    Spinner varTwoInput;
    TextField variableOneInput;
    Tab tableTab;
    Tab graphTab;


    @Override
    public BorderPane getPane() {

        setUserInputVarOne(DEFAULT_CHARGE_ONE);
        setUserInputVarTwo(DEFAULT_CHARGE_TWO);
        circleRadius = 50;
        finalDest = 350;

        Particle_1 = new Circle(PARTICLE_ONE_X, PARTICLE_Y, circleRadius, partOneColor); 
        Particle_2 = new Circle(PARTICLE_TWO_X, PARTICLE_Y, circleRadius, partTwoColor); 
        animationArrowOne = getAttractionRightArrow();
        animationArrowTwo = getAttractionLeftArrow();
        
        //Adding to animation pane
        animationPane.getChildren().addAll(Particle_1, Particle_2, animationArrowOne, animationArrowTwo);
        firstArrow = animationPane.getChildren().get(2);
        secondArrow = animationPane.getChildren().get(3);
        
        //Title pane
        VBox VBoxTopPane = new VBox();
        VBoxTopPane.getChildren().addAll(mainTitle, formulaLabel, particleOne, particleTwo);
        
        //Translate anim for particles
        TranslateTransition transFirstParticle = new TranslateTransition(TIME, Particle_1);
        transFirstParticle.setByX(finalDest);
        transFirstParticle.setAutoReverse(true);
        transFirstParticle.setCycleCount(Timeline.INDEFINITE);
        TranslateTransition transSecondParticle = new TranslateTransition(TIME, Particle_2);
        transSecondParticle.setByX(-finalDest);
        transSecondParticle.setAutoReverse(true);
        transSecondParticle.setCycleCount(Timeline.INDEFINITE);
        
        //Scale anim for arrows
        firstArrowScaleAnimation = new ScaleTransition(TIME, firstArrow);
        firstArrowScaleAnimation.setByX(1.5f);
        firstArrowScaleAnimation.setAutoReverse(true);
        firstArrowScaleAnimation.setCycleCount(Timeline.INDEFINITE);
        secondArrowScaleAnimation = new ScaleTransition(TIME, secondArrow);
        secondArrowScaleAnimation.setByX(1.5f);
        secondArrowScaleAnimation.setAutoReverse(true);
        secondArrowScaleAnimation.setCycleCount(Timeline.INDEFINITE);
        
        //Tranlate anim for arrows
        transFirstArrow = new TranslateTransition(TIME, firstArrow);
        transFirstArrow.setByX(-finalDest);
        transFirstArrow.setAutoReverse(true);
        transFirstArrow.setCycleCount(Timeline.INDEFINITE);
        transSecondArrow = new TranslateTransition(TIME, secondArrow);
        transSecondArrow.setByX(finalDest);
        transSecondArrow.setAutoReverse(true);
        transSecondArrow.setCycleCount(Timeline.INDEFINITE);
        
        //Adding all tranisitions
        ParallelTransition arrowFirstAnim = new ParallelTransition(firstArrowScaleAnimation, transFirstArrow);
        ParallelTransition arrowSecondAnim = new ParallelTransition(secondArrowScaleAnimation, transSecondArrow);
        ParallelTransition NYB_animation = new ParallelTransition(transFirstParticle, transSecondParticle, arrowFirstAnim, arrowSecondAnim);

        Button start = new Button(START_STRING);
        Button done = new Button(DONE_STRING);
        Button pause = new Button(PAUSE_STRING);
        pause.setDisable(true);
        Button reset = new Button(RESET_STRING);

        Alert resetPopUp = new Alert(AlertType.CONFIRMATION, "Choose your option: ", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        resetPopUp.setTitle("Change Values?");
        resetPopUp.setHeaderText("Would you like to change the charges of the two particle?");
        
        //Defining buttona actiong
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NYB_animation.stop();
                MainClass.returnToMain();
                start.setDisable(false);
            }
        });
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NYB_animation.play();
                start.setDisable(true);
                pause.setDisable(false);
                tableGraphTab();
            }
        });
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start.setDisable(false);
                pause.setDisable(true);
                NYB_animation.pause();
            }
        });
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NYB_animation.jumpTo(Duration.ZERO);
                NYB_animation.stop();
                tableTab.setContent(null);
                graphTab.setContent(null);
                Optional<ButtonType> result = resetPopUp.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    pause.fire();
                    resetPopUp.close();
                    showResetPopup();
                } else if (result.isPresent() && result.get() == ButtonType.NO) {
                    NYB_animation.jumpTo(Duration.ZERO);
                } else {
                }
            }
        });
        Button help = new Button(HELP_STRING);
        Alert helpMsg = new Alert(Alert.AlertType.INFORMATION, null, ButtonType.OK);
        helpMsg.setTitle(HELP_STRING);
        helpMsg.setContentText(HELP_MESSAGE_NYB_2); //add message in InterfConst
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                helpMsg.show();
            }
        });
        
        //Button pane
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(start, done, pause, reset, help);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(10);
        //Graph Pane
        TabPane graphPane = new TabPane();
        graphTab = new Tab(GRAPH_TAB_STRING);
        tableTab = new Tab(TABLE_TAB_STRING);
        graphTab.setClosable(false);
        tableTab.setClosable(false);
        graphPane.getTabs().addAll(graphTab, tableTab);
        //Bottom Pane
        SplitPane bottomPane = new SplitPane();
        bottomPane.getItems().addAll(buttonPane, graphPane);
        bottomPane.setDividerPositions(0.5f);
        bottomPane.setMinHeight(250);
        bottomPane.setPrefHeight(160);
        bottomPane.setPrefWidth(200);
        
        //Set pane positions 
        BorderPane root = new BorderPane();
        root.setCenter(animationPane);
        root.setBottom(bottomPane);
        root.setTop(VBoxTopPane);
        return root;
    }
    public Polygon getAttractionRightArrow() {
        Double[] arrowPoints = {
            700.0, 300.0,
            740.0, 335.0,
            740.0, 310.0,
            850.0, 310.0,
            850.0, 290.0,
            740.0, 290.0,
            740.0, 265.0,
            700.0, 300.0};
        Polygon arrow = new Polygon();
        arrow.setFill(Color.BLACK);
        arrow.getPoints().addAll(arrowPoints);
        return arrow;
    }
    public Polygon getAttractionLeftArrow() {
        Double[] arrowPoints = {
            300.0, 300.0,
            260.0, 335.0,
            260.0, 310.0,
            150.0, 310.0,
            150.0, 290.0,
            260.0, 290.0,
            260.0, 265.0,
            300.0, 300.0};
        Polygon arrow = new Polygon();
        arrow.setFill(Color.BLACK);
        arrow.getPoints().addAll(arrowPoints);
        return arrow;
    }
    public Polygon getRepelLeftArrow() {
        Double[] arrowPoints = {
            -100.0, 300.0,
            -60.0, 335.0,
            -60.0, 310.0,
            50.0, 310.0,
            50.0, 290.0,
            -60.0, 290.0,
            -60.0, 265.0,
            -100.0, 300.0};
        Polygon arrow = new Polygon();
        arrow.setFill(Color.BLACK);
        arrow.getPoints().addAll(arrowPoints);
        return arrow;
    }
    public Polygon getRepelRightArrow() {
        Double[] arrowPoints = {
            1100.0, 300.0,
            1060.0, 335.0,
            1060.0, 310.0,
            950.0, 310.0,
            950.0, 290.0,
            1060.0, 290.0,
            1060.0, 265.0,
            1100.0, 300.0};
        Polygon arrow = new Polygon();
        arrow.setFill(Color.BLACK);
        arrow.getPoints().addAll(arrowPoints);
        return arrow;
    }
    public void showResetPopup() {
        Dialog changeValues = new Dialog();
        changeValues.setContentText("Reset Values");
        changeValues.setHeaderText("Reset charge and sign for Particle One an Particle Two");
        changeValues.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        HBox FirstVariableInputValues = new HBox();
        HBox SecondVariableInputValues = new HBox();
        VBox alignmentPane = new VBox();

        Label variableOne = new Label("Charge of Particle One: ");
        Label unitsOne = new Label(SPACE_STRING + UNITS_CHARGE);
        varOneInput = new Spinner(1,100, getUserInputVarOne(), 5);

        Label variableTwo = new Label("Change of Particle Two: ");
        Label unitsTwo = new Label(SPACE_STRING + UNITS_CHARGE);
        varTwoInput = new Spinner(1,100, getUserInputVarTwo(), 5);
        
        chargeSignsOne = new ComboBox();
        chargeSignsOne.getItems().addAll(POS_CHARGE, NEG_CHARGE);
        chargeSignsOne.setValue(POS_CHARGE);
        chargeSignsTwo = new ComboBox();
        chargeSignsTwo.getItems().addAll(POS_CHARGE, NEG_CHARGE);
        chargeSignsTwo.setValue(NEG_CHARGE);
        //Add all
        FirstVariableInputValues.getChildren().addAll(variableOne, varOneInput, unitsOne, chargeSignsOne);
        SecondVariableInputValues.getChildren().addAll(variableTwo, varTwoInput, unitsTwo, chargeSignsTwo);
        alignmentPane.getChildren().addAll(FirstVariableInputValues, SecondVariableInputValues);
        
        changeValues.getDialogPane().setContent(alignmentPane);
        Optional<ButtonType> result = changeValues.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            changeValues.close();
            makeAnimation();
            setUserInputVarOne(Integer.parseInt(varOneInput.getValue().toString()));
            setUserInputVarTwo(Integer.parseInt(varTwoInput.getValue().toString()));
            particleOne.setText(PARTICLE_ONE + SPACE_STRING + getSignPropertyParticleOne() + getUserInputVarOne() + SPACE_STRING + UNITS_CHARGE);
            particleTwo.setText(PARTICLE_TWO + SPACE_STRING + getSignPropertyParticleTwo() + getUserInputVarTwo() + SPACE_STRING + UNITS_CHARGE);     
        } else {
            changeValues.close();
        }
    }
    public void makeAnimation() {
        if (chargeSignsOne.getValue() == "+") {
            setSignPropertyParticleOne(POS_CHARGE);
            Particle_1.setFill(POS_CHARGE_COLOR);
            if (chargeSignsTwo.getValue() == "-") {
                Particle_2.setFill(NEG_CHARGE_COLOR);
                setSignPropertyParticleTwo(NEG_CHARGE);
                animationPane.getChildren().set(2, getAttractionRightArrow());
                animationPane.getChildren().set(3, getAttractionLeftArrow());
                transFirstArrow.setNode(animationPane.getChildren().get(2));
                firstArrowScaleAnimation.setNode(animationPane.getChildren().get(2));
                transSecondArrow.setNode(animationPane.getChildren().get(3));
                secondArrowScaleAnimation.setNode(animationPane.getChildren().get(3));
            } else {
                Particle_2.setFill(POS_CHARGE_COLOR);
                setSignPropertyParticleTwo(POS_CHARGE);
                animationPane.getChildren().set(2, getRepelRightArrow());
                animationPane.getChildren().set(3, getRepelLeftArrow());
                transFirstArrow.setNode(animationPane.getChildren().get(2));
                firstArrowScaleAnimation.setNode(animationPane.getChildren().get(2));
                transSecondArrow.setNode(animationPane.getChildren().get(3));
                secondArrowScaleAnimation.setNode(animationPane.getChildren().get(3));
            }
        } else {
            Particle_1.setFill(NEG_CHARGE_COLOR);
            setSignPropertyParticleOne(NEG_CHARGE);
            if (chargeSignsTwo.getValue() == "+") {
                Particle_2.setFill(POS_CHARGE_COLOR);
                setSignPropertyParticleTwo(POS_CHARGE);
                animationPane.getChildren().set(2, getAttractionRightArrow());
                animationPane.getChildren().set(3, getAttractionLeftArrow());
                transFirstArrow.setNode(animationPane.getChildren().get(2));
                firstArrowScaleAnimation.setNode(animationPane.getChildren().get(2));
                transSecondArrow.setNode(animationPane.getChildren().get(3));
                secondArrowScaleAnimation.setNode(animationPane.getChildren().get(3));
            } else {
                Particle_2.setFill(NEG_CHARGE_COLOR);
                setSignPropertyParticleTwo(NEG_CHARGE);
                animationPane.getChildren().set(2, getRepelRightArrow());
                animationPane.getChildren().set(3, getRepelLeftArrow());
                transFirstArrow.setNode(animationPane.getChildren().get(2));
                transSecondArrow.setNode(animationPane.getChildren().get(3));
                firstArrowScaleAnimation.setNode(animationPane.getChildren().get(2));
                secondArrowScaleAnimation.setNode(animationPane.getChildren().get(3));
            }
        }
    }
    public void tableGraphTab() {
        //Table of value
        int[] xGraphPoints = new int[10];
        int temp = 1;
        VBox xTabPoints = new VBox();
        xTabPoints.setAlignment(Pos.CENTER);
        Label xTablePoints = new Label(DISTANCE_LABEL.toUpperCase());
        xTabPoints.getChildren().add(xTablePoints);
        for (int i = 0; i < xGraphPoints.length; i++) {
            xGraphPoints[i] = temp;
            xTabPoints.getChildren().add(new Label(Integer.toString(xGraphPoints[i])));
            temp ++;
        }
        double[] yGraphPoints = new double[10];
        VBox yTabPoints = new VBox();
        yTabPoints.setAlignment(Pos.CENTER);
        Label yTableLabel = new Label(FORCE_LABEL.toUpperCase());
        yTabPoints.getChildren().add(yTableLabel);
        for (int i = 0; i < yGraphPoints.length; i++) {
            yGraphPoints[i] = ((K_CONSTANT*Math.pow(10,9)) * (getUserInputVarOne() * Math.pow(10, -6))
                    * (getUserInputVarTwo() * Math.pow(10, -6))) / Math.pow(xGraphPoints[i], 2);
            yTabPoints.getChildren().add(new Label(String.format("%.4f",yGraphPoints[i])));
        }
        HBox xyTable = new HBox();
        xyTable.setSpacing(5);
        xyTable.setAlignment(Pos.CENTER);
        xyTable.getChildren().addAll(xTabPoints, yTabPoints);
        tableTab.setContent(xyTable);

        //Graphing
        NumberAxis xAxis = new NumberAxis(0, 10, 1);
        NumberAxis yAxis = new NumberAxis(0, 25, 0.5);
        ScatterChart<Number, Number> scatterGraph = new ScatterChart<>(xAxis, yAxis);
        xAxis.setLabel(DISTANCE_LABEL);
        yAxis.setLabel(FORCE_LABEL);
        scatterGraph.setTitle(NYB_2);
        scatterGraph.setLegendVisible(false);
        XYChart.Series points = new XYChart.Series();
        for (int i = 0; i < xGraphPoints.length; i++) {
            points.getData().add(new XYChart.Data(xGraphPoints[i], yGraphPoints[i]));
        }
        scatterGraph.getData().addAll(points);
        graphTab.setContent(scatterGraph);

    }    
        private String getSignPropertyParticleOne(){
            return signPropertyParticleOne.getValue();
        }
        private void setSignPropertyParticleOne(String selection){
            signPropertyParticleOne.setValue(selection);
        }
        private String getSignPropertyParticleTwo(){
            return signPropertyParticleTwo.getValue();
        }
        private void setSignPropertyParticleTwo(String selection){
            signPropertyParticleTwo.setValue(selection);
        }
        private int getUserInputVarOne() {
            return userInputVarOne.getValue();
        }
        private void setUserInputVarOne(int varOne) {
            userInputVarOne.setValue(varOne);
        }
        private int getUserInputVarTwo() {
            return userInputVarTwo.getValue();
        }
        private void setUserInputVarTwo(int varTwo) {
            userInputVarTwo.setValue(varTwo);
        }  
}
