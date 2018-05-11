package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GanttChart {
    private ArrayList<GanttChartCell> cells;

    public GanttChart() {
        this.cells = new ArrayList<>();
    }

    public GanttChart(ArrayList<GanttChartCell> cells) {
        this.cells = cells;
    }

    public ArrayList<GanttChartCell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<GanttChartCell> cells) {
        this.cells = cells;
    }

    public void addCell(GanttChartCell x) {
        cells.add(x);
    }

    private void removeDuplicates() {
        for (int i = 1; i < cells.size()-1; i++) {
            if (cells.get(i).getValue() == cells.get(i - 1).getValue()) {
                cells.remove(i);
            }
        }
    }

    private void fixDoubleProblem(){
        for (int i = 1; i < cells.size(); i++) {
            if (new DecimalFormat("###.###").format(cells.get(i).getBegin()).equals(new DecimalFormat("###.###").format(cells.get(i - 1).getBegin()))) {
                cells.remove(i-1);
            }
        }
    }

    public void draw(HBox node, Scene scene, boolean drawDuplicates) {
        double percentage = 0.9;
        double minWidth = 25;
        double totalTime = cells.get(cells.size() - 1).getBegin();
        //node.setAlignment(Pos.CENTER);
        fixDoubleProblem();
        if (!drawDuplicates)
            removeDuplicates();

        int max = cells.size() - 1;
        for (int i = 0; i < max; i++) {
//            if (!drawDuplicates && cells.get(i).getValue() == cells.get(i + 1).getValue() && i != cells.size() - 2) {
//                continue;
//            }
            VBox v = new VBox();
            Label PID = new Label(cells.get(i).getValue());
            Label time = new Label(new DecimalFormat("###.###").format(cells.get(i).getBegin()));
            PID.setPrefHeight(30);
            PID.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 1))));

            PID.setPrefHeight(30);
            PID.setAlignment(Pos.CENTER);
            v.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);
            PID.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);
            time.setPrefWidth(percentage * scene.getWidth() * (cells.get(i + 1).getBegin() - cells.get(i).getBegin()) / totalTime);

//            PID.prefWidthProperty().bind(v.widthProperty());
//            time.prefWidthProperty().bind(v.widthProperty());
//            v.prefWidthProperty().bind(node.widthProperty());

            v.setMinWidth(minWidth);
            PID.setMinWidth(minWidth);
            time.setMinWidth(minWidth);


            v.getChildren().add(PID);
            v.getChildren().add(time);
            node.getChildren().add(v);
        }
        VBox v = new VBox();

            Label PID = new Label(cells.get(cells.size() - 1).getValue());
            Label time = new Label(new DecimalFormat("###.###").format(cells.get(cells.size() - 2).getBegin()));
            PID.setPrefHeight(30);
            PID.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 1, 1, 1))));

            PID.setPrefHeight(30);
            PID.setAlignment(Pos.CENTER);
            v.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);
            PID.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);
            time.setPrefWidth(percentage * scene.getWidth() * (cells.get(cells.size() - 1).getBegin() - cells.get(cells.size() - 2).getBegin()) / totalTime);

//        PID.prefWidthProperty().bind(v.widthProperty());
//        time.prefWidthProperty().bind(v.widthProperty());
//        v.prefWidthProperty().bind(node.widthProperty());

            v.setMinWidth(minWidth);
            PID.setMinWidth(minWidth);
            time.setMinWidth(minWidth);

            v.getChildren().add(PID);
            v.getChildren().add(time);
            node.getChildren().add(v);

            v.getChildren().clear();

        Label empty = new Label();
        empty.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 1))));
        empty.setPrefHeight(30);
//        Label lastTime = new Label(cells.get(cells.size() - 1).getBegin() + "");
        Label lastTime = new Label(new DecimalFormat("###.###").format(cells.get(cells.size()-1).getBegin()));
//        empty.prefWidthProperty().bind(v.widthProperty());
//        lastTime.prefWidthProperty().bind(v.widthProperty());
//        v.prefWidthProperty().bind(node.widthProperty());

        v.getChildren().addAll(empty, lastTime);
    }


    public void draw(HBox node, Scene scene) {
        draw(node, scene, false);
    }

    public void drawWithDuplicates(HBox node, Scene scene) {
        draw(node, scene, true);
    }
}
