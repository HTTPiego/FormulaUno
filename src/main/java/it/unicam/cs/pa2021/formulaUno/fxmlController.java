package it.unicam.cs.pa2021.formulaUno;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class fxmlController implements CircuitUpdateListener<Node>{

    private final Controller <GameBuilder<GraphCircuit_DS, Car<Node>>, Node> controller = new DefaultController();

    private boolean newGameFlag = false;

    private GridPane gridPane;

    @FXML
    public TextArea messagges;

    @FXML
    public StackPane circuitPane;

    @FXML
    protected Button newGame;

    @FXML
    protected Button nextTurn;

    @FXML
    protected Button classification;

    @FXML
    protected Button driversStatus;

    @FXML
    public void nextTurnHandle(ActionEvent actionEvent) {
        if (!this.newGameFlag) {
            this.messagges.setText("CLICK 'newGame' BEFORE");
        } else {
            this.messagges.clear();
            if (this.controller.isGameOver())
                this.aCarWon();
            this.controller.nextTurn();
        }
    }

    @FXML
    public void classificationHandle(ActionEvent actionEvent) {
        if (!this.newGameFlag){
            this.messagges.setText("CLICK 'newGame' BEFORE");
        } else {
            this.messagges.clear();
            this.controller.Classfication();
        }
    }


    @FXML
    public void driversStatusHandle(ActionEvent actionEvent) {
        if (!this.newGameFlag) {
            this.messagges.setText("CLICK 'newGame' BEFORE");
        }else {
            this.messagges.clear();
            this.controller.getDriversStatus();
        }
    }

    @FXML
    public void newGameHandle(ActionEvent actionEvent) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        if (!this.newGameFlag) {

            Class<?> gameBuilderClass = fxmlController.class.getClassLoader().loadClass(MainFX.gameBuilderName);

            Constructor<?> gameBuilderConstructor = gameBuilderClass.getConstructor();

            Object gameBuilder = gameBuilderConstructor.newInstance();

            this.messagges.clear();

            this.controller.newGame((GameBuilder<GraphCircuit_DS, Car<Node>>) gameBuilder, Integer.valueOf(MainFX.totNumOfPlayers));

            this.controller.addListener(this);

            this.drawCircuit();

            this.newGameFlag = true;
        }
        else
            this.messagges.setText("GAME IS ALREADY STARTED");
    }

    private void drawCircuit (){
        this.gridPane = new GridPane();
        gridPane.setLayoutX(this.circuitPane.getLayoutY());
        gridPane.setLayoutY(this.circuitPane.getLayoutX());
        gridPane.prefHeight(circuitPane.heightProperty().get());
        gridPane.setMinHeight(circuitPane.heightProperty().get());
        gridPane.setMaxHeight(circuitPane.heightProperty().get());
        gridPane.prefWidth(circuitPane.widthProperty().get());
        gridPane.maxWidth(circuitPane.widthProperty().get());
        gridPane.minWidth(circuitPane.widthProperty().get());
        gridPane.addColumn(this.controller.getBuilder().getMax_X()-this.controller.getBuilder().getMin_X());
        gridPane.addRow(this.controller.getBuilder().getMax_Y()-this.controller.getBuilder().getMin_Y());
        this.circuitPane.getChildren().add(gridPane);
        this.controller.
                getBuilder().
                getGraphCircuit_DS().
                nodeSet().
                stream().
                sequential().
                filter(node -> !node.isOutsideNode()).
                forEach(node -> this.drawNode(node, gridPane));

    }

    private void drawNode(Node node, GridPane pane){

        Circle circle = new Circle();
        circle.setRadius(10);
        if (node.isStartNode()) {
            circle.setStroke(Paint.valueOf("green"));
            if (node.getCar() != null)
                circle.setFill(Paint.valueOf("pink"));
        } else if (node.isEndNode()) {
            circle.setStroke(Paint.valueOf("red"));
        } else {
            circle.setStroke(Paint.valueOf("yellow"));
        }
        pane.add(circle, (int) node.getCardinalPoint().getX(), (int) node.getCardinalPoint().getY());
    }

    @Override
    public void aCarMoved(Car<Node> car) {
        this.controller.
                getBuilder().
                getGraphCircuit_DS().
                getNode(car.getPreviousPosition()).
                ifPresent(this::deleteCarDraw);
        this.controller.
                getBuilder().
                getGraphCircuit_DS().
                getNode(car.getPosition()).
                ifPresent(this::drawNewCarPos);
    }

    private void drawNewCarPos(Node node) {
        int column = (int) node.getCardinalPoint().getX();
        int row = (int) node.getCardinalPoint().getY();
        javafx.scene.Node circle = this.getNodeByRowColumnIndex(column, row);
        this.circuitPane.getChildren().remove(circle);
        Circle newCircle = new Circle();
        newCircle.setRadius(10);
        newCircle.setStroke(Paint.valueOf("yellow"));
        newCircle.setFill(Paint.valueOf("pink"));
        this.gridPane.add(newCircle, column, row);
    }

    @Override
    public void aCarWon() {
        this.messagges.clear();
        this.messagges.setText("Game ended");
        this.newGame.setDisable(true);
        this.classification.setDisable(true);
        this.driversStatus.setDisable(true);
        this.nextTurn.setDisable(true);
    }

    @Override
    public void aCarWentOutOrCrashed(Car<Node> car) {
        this.controller.
                getBuilder().
                getGraphCircuit_DS().
                getNode(car.getPosition()).
                ifPresent(this::deleteCarDraw);
    }

    private void deleteCarDraw(Node node){
        int column = (int) node.getCardinalPoint().getX();
        int row = (int) node.getCardinalPoint().getY();
        javafx.scene.Node circle = this.getNodeByRowColumnIndex(column, row);
        this.circuitPane.getChildren().remove(circle);
        Circle newCircle = new Circle();
        newCircle.setRadius(10);
        if (node.isStartNode()) {
            newCircle.setStroke(Paint.valueOf("green"));
        } else if (node.isEndNode()) {
            newCircle.setStroke(Paint.valueOf("red"));
        } else {
            newCircle.setStroke(Paint.valueOf("yellow"));
        }
        this.gridPane.add(newCircle, column, row);
    }

    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column) {
        javafx.scene.Node returnedCircle = null;
        for (javafx.scene.Node circle : this.gridPane.getChildren()) {
            if(GridPane.getRowIndex(circle) == row && GridPane.getColumnIndex(circle) == column) {
                returnedCircle = circle;
                break;
            }
        }
        return returnedCircle;
    }
}
