package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ComboBox<String> scheduleMethod;
    @FXML
    CheckBox preemptive;
    @FXML
    TextField pid;
    @FXML
    TextField arrivalTime;
    @FXML
    TextField burstTime;
    @FXML
    TextField priority;
    @FXML
    TextField timeQuantum;

    @FXML
    TableView<Process> table;
    @FXML
    TableColumn<Process, String> pidCol;
    @FXML
    TableColumn<Process, Double> arrivalTimeCol;
    @FXML
    TableColumn<Process, Double> burstTimeCol;
    @FXML
    TableColumn<Process, Double> waitingTimeCol;
    @FXML
    TableColumn<Process, Double> departureTimeCol;
    @FXML
    TableColumn<Process, Integer> priorityCol;
    @FXML
    TableColumn<Process, Double> startingTimeCol;

    @FXML
    HBox ganttChart;

    ObservableList<Process> processes = FXCollections.observableArrayList();

    private static boolean first;
    private static double maxWidth;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        first = true;

        scheduleMethod.getItems().addAll("FCFS", "SJF", "Priority", "Round Robin");
        scheduleMethod.getSelectionModel().selectFirst();

        preemptive.setVisible(false);
        priority.setVisible(false);
        timeQuantum.setVisible(false);

        // Visible or Hidden Based On Method
        scheduleMethod.getSelectionModel().selectedItemProperty().addListener((v, o, n) -> {
            if (n == "FCFS") {
                preemptive.setVisible(false);
                priority.setVisible(false);
                timeQuantum.setVisible(false);
            } else if (n == "SJF") {
                preemptive.setVisible(true);
                priority.setVisible(false);
                timeQuantum.setVisible(false);
            } else if (n == "Priority") {
                preemptive.setVisible(true);
                priority.setVisible(true);
                priority.setMaxWidth(priority.getMinWidth());
                timeQuantum.setVisible(false);
            } else if (n == "Round Robin") {
                preemptive.setVisible(false);
                priority.setVisible(false);
                priority.setMaxWidth(0);
                timeQuantum.setVisible(true);
            }
        });

        pidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burstTimeCol.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        departureTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        startingTimeCol.setCellValueFactory(new PropertyValueFactory<>("startingTime"));
        table.setEditable(true);
        table.setItems(processes);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        pidCol.setCellFactory(TextFieldTableCell.forTableColumn());
        arrivalTimeCol.setCellFactory(TextFieldTableCell.<Process, Double>forTableColumn(new DoubleStringConverter()));
        burstTimeCol.setCellFactory(TextFieldTableCell.<Process, Double>forTableColumn(new DoubleStringConverter()));
        priorityCol.setCellFactory(TextFieldTableCell.<Process, Integer>forTableColumn(new IntegerStringConverter()));

        pid.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) this.onAddProcess();
        });
        arrivalTime.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) this.onAddProcess();
        });
        burstTime.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) this.onAddProcess();
        });
        priority.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) this.onAddProcess();
        });

        timeQuantum.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) this.onCalculateAndDraw();
        });

        table.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.DELETE)) this.onDeleteProcesses();
        });
    }

    public void onCalculateAndDraw() {
//            if(!first){
//                ganttChart.setMaxWidth(maxWidth);
//                ganttChart.setMinWidth(maxWidth);
//                ganttChart.setPrefWidth(maxWidth);
//            }else{
//                maxWidth = ganttChart.getWidth();
//                first=false;
//            }

//            ganttChart.getChildren().clear();
//            ganttChart.setAlignment(Pos.CENTER);
        double timeQuantumInput = Validator.validate_double(timeQuantum);
        if (timeQuantumInput <= 0 && scheduleMethod.getSelectionModel().getSelectedItem() == "Round Robin") {
            timeQuantum.getStyleClass().add("input-wrong");
            return;
        }
        timeQuantum.getStyleClass().removeAll("input-wrong");

        Stage stage = new Stage();
        stage.setTitle("Gantt Chart");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        //StackPane spane = new StackPane();
        StackPane layout = new StackPane();
        HBox ganttChart = new HBox();
        layout.setAlignment(Pos.CENTER);
        VBox ganttChartContainer = new VBox();
        ganttChartContainer.getChildren().add(ganttChart);
        ganttChartContainer.setAlignment(Pos.CENTER);
//        ganttChart.setPadding(new Insets(0,5,0,5));
        //spane.getChildren().add(ganttChart);
        //ganttChart.prefWidthProperty().bind(spane.widthProperty());

        //spane.setAlignment(Pos.CENTER);
        //ganttChart.setAlignment(Pos.BOTTOM_CENTER);
        layout.getChildren().add(ganttChartContainer);

        Scene scene = new Scene(layout, 900, 400);
        stage.setScene(scene);
        stage.show();

        DoubleProperty leftPadding = new SimpleDoubleProperty(0.1 * scene.getWidth());
        ganttChartContainer.paddingProperty().bind(Bindings.createObjectBinding(() -> new Insets(0, 0, 0, leftPadding.doubleValue()), leftPadding));

        //System.out.println("ganttChart.getMaxWidth() : "+ganttChart.getMaxWidth()+" ganttChart.getMinWidth() : "+ganttChart.getMinWidth()+" ganttChart.getPrefWidth() : "+ganttChart.getPrefWidth()+" ganttChart.getWidth() : "+ganttChart.getWidth());

        if (scheduleMethod.getSelectionModel().getSelectedItem() == "FCFS") {
            Scheduler.sortFCFS(ganttChart, processes, scene);
        } else if (scheduleMethod.getSelectionModel().getSelectedItem() == "Priority") {
            Scheduler.sortPriority(ganttChart, processes, preemptive.isSelected(), scene);
        } else if (scheduleMethod.getSelectionModel().getSelectedItem() == "SJF") {
            Scheduler.sortSJF(ganttChart, processes, preemptive.isSelected(), scene);
        } else if (scheduleMethod.getSelectionModel().getSelectedItem() == "Round Robin") {
            Scheduler.sortRoundRobin(ganttChart, processes, Double.parseDouble(timeQuantum.getText()), scene);
        }
        VBox INFO = new VBox();
        double waitingTime = 0;
        for (Process p : processes)
            waitingTime += p.getWaitingTime();
        Label awt = new Label("Average Waiting Time = " + new DecimalFormat("###.###").format(waitingTime / processes.size()));
        awt.setAlignment(Pos.CENTER);
        INFO.getChildren().add(awt);

        double turnAroundTime = 0;
        for (Process p : processes)
            turnAroundTime += (p.getDepartureTime() - p.getArrivalTime());
        Label atta = new Label("Average Turnaround Time = " + new DecimalFormat("###.###").format(turnAroundTime / processes.size()));

        ganttChart.setAlignment(Pos.BOTTOM_RIGHT);
        INFO.setAlignment(Pos.BOTTOM_CENTER);
//        INFO.setPrefWidth(scene.getWidth());
        awt.setMinWidth(scene.getWidth());
        atta.setMinWidth(scene.getWidth());
        awt.setAlignment(Pos.CENTER);
        atta.setAlignment(Pos.CENTER);

        INFO.getChildren().add(atta);
        layout.getChildren().add(INFO);
        layout.setAlignment(Pos.CENTER);
        ganttChart.setAlignment(Pos.CENTER);
        ganttChartContainer.setAlignment(Pos.CENTER);

    }

    public void onAddProcess() {
        String pidInput = Validator.validate_string(pid);
        double arrivalTimeInput = Validator.validate_double(arrivalTime);
        double burstTimeInput = Validator.validate_double(burstTime);
        int priorityInput = Validator.validate_int(priority);
        if (scheduleMethod.getSelectionModel().getSelectedItem() != "Priority") priorityInput = 1;
        if (pidInput != "-1" && arrivalTimeInput != -1 && burstTimeInput != -1 && priorityInput != -1) {
            processes.add(new Process(pidInput, arrivalTimeInput, burstTimeInput, priorityInput));
            pid.clear();
            arrivalTime.clear();
            burstTime.clear();
            priority.clear();
        }
        if (pidInput == "-1") pid.getStyleClass().add("input-wrong");
        else pid.getStyleClass().removeAll("input-wrong");
        if (arrivalTimeInput == -1) arrivalTime.getStyleClass().add("input-wrong");
        else arrivalTime.getStyleClass().removeAll("input-wrong");
        if (burstTimeInput == -1) burstTime.getStyleClass().add("input-wrong");
        else burstTime.getStyleClass().removeAll("input-wrong");
        if (priorityInput == -1) priority.getStyleClass().add("input-wrong");
        else priority.getStyleClass().removeAll("input-wrong");
    }

    public void onDeleteProcesses() {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
    }

    public void resetColumn(TableColumn.CellEditEvent newCell) {

    }

    public void editPidCellEvent(TableColumn.CellEditEvent newCell) {
        Process selectedProcess = table.getSelectionModel().getSelectedItem();
        Process to = (Process) newCell.getTableView().getItems().get(newCell.getTablePosition().getRow());
        String pidInput = Validator.validate_string(newCell.getNewValue().toString());
        if (pidInput != "-1") {
            selectedProcess.setPid(newCell.getNewValue().toString());
        } else {
            newCell.getTableView().getItems().set(newCell.getTablePosition().getRow(), to);
        }
    }

    public void editPriorityCellEvent(TableColumn.CellEditEvent newCell) {
        Process selectedProcess = table.getSelectionModel().getSelectedItem();
        Process to = (Process) newCell.getTableView().getItems().get(newCell.getTablePosition().getRow());
        int priority = Validator.validate_int((int) newCell.getNewValue());
        if (priority >= 0) {
            selectedProcess.setPriority((int) newCell.getNewValue());
        } else {
            throw new NumberFormatException();
//            newCell.getTableView().getItems().set(newCell.getTablePosition().getRow(),to);
        }
    }

    public void editArrivalTimeCellEvent(TableColumn.CellEditEvent newCell) {
        Process selectedProcess = table.getSelectionModel().getSelectedItem();
        Process to = (Process) newCell.getTableView().getItems().get(newCell.getTablePosition().getRow());
        double arrivalTime = Validator.validate_double((double) newCell.getNewValue());
        if (arrivalTime >= 0) {
            selectedProcess.setArrivalTime((double) newCell.getNewValue());
        } else {
            throw new NumberFormatException();
//            newCell.getTableView().getItems().set(newCell.getTablePosition().getRow(),to);
        }
    }

    public void editBurstTimeCellEvent(TableColumn.CellEditEvent newCell) {
        Process selectedProcess = table.getSelectionModel().getSelectedItem();
        Process to = (Process) newCell.getTableView().getItems().get(newCell.getTablePosition().getRow());
        double burstTime = Validator.validate_double((double) newCell.getNewValue());
        if (burstTime >= 0) {
            selectedProcess.setBurstTime((double) newCell.getNewValue());
        } else {
            throw new NumberFormatException();
//            newCell.getTableView().getItems().set(newCell.getTablePosition().getRow(),to);
        }
    }

    public void generateData() {
        processes.clear();
        Random rand = new Random();
        int n = rand.nextInt(15);
        switch (n) {
            case 0:
//      // Dummy Data - Set 1
                processes.add(new Process("P1", 0, 5, 4));
                processes.add(new Process("P2", 3, 5, 3));
                processes.add(new Process("P3", 2, 5, 2));
                processes.add(new Process("P4", 15, 5, 1));
                processes.add(new Process("P5", 25, 5, 1));
                break;
            case 1:
//        // Dummy Data - Set 2
                processes.add(new Process("P1", 0, 2, 4));
                processes.add(new Process("P2", 1, 2, 1));
                processes.add(new Process("P3", 0, 2, 3));
                processes.add(new Process("P4", 0, 2, 2));
                break;
            case 2:
//        // Dummy Data - Set 3
                processes.add(new Process("P1", 0, 6, 4));
                processes.add(new Process("P2", 0, 8, 1));
                processes.add(new Process("P3", 0, 7, 3));
                processes.add(new Process("P4", 0, 3, 2));
                processes.add(new Process("P5", 1, 20, 1));
                processes.add(new Process("P6", 45, 1, 1));
                break;
            case 3:
//        // Dummy Data - Set 4
                processes.add(new Process("P1", 0, 6, 4));
                processes.add(new Process("P2", 5, 6, 1));
                break;
            case 4:
//        // Dummy Data - Set 5
                processes.add(new Process("P1", 0, 6, 1));
                processes.add(new Process("P2", 2, 6, 2));
                break;
//        // Dummy Data - Set 6
            case 5:
                processes.add(new Process("P1", 0, 3, 1));
                processes.add(new Process("P2", 2, 2, 2));
                break;
            case 6:
//        // Dummy Data - Set 7
                processes.add(new Process("P1", 0, 8, 1));
                processes.add(new Process("P2", 1, 4, 2));
                processes.add(new Process("P3", 2, 9, 2));
                processes.add(new Process("P4", 3, 5, 2));
                break;
            case 7:
                //        // Dummy Data - Set 8 - Priority With gaps
                processes.add(new Process("P1", 0, 8, 2));
                processes.add(new Process("P2", 10, 4, 1));
                break;
//        // Dummy Data - Set 9
            case 8:
                processes.add(new Process("P1", 0, 6, 3));
                processes.add(new Process("P2", 1, 2, 2));
                processes.add(new Process("P3", 2, 3, 1));
                processes.add(new Process("P4", 4, 1, 1));
                // GAP
                processes.add(new Process("P5", 20, 1, 1));
                break;
            case 9:
//        // Dummy Data - Set 10 - Page 15
                processes.add(new Process("P1", 0, 8, 3));
                processes.add(new Process("P2", 1, 4, 2));
                processes.add(new Process("P3", 2, 9, 1));
                processes.add(new Process("P4", 3, 5, 1));
                break;
            case 10:
//        // Dummy Data - Set 11 - Page 12
                processes.add(new Process("P1", 0, 6, 3));
                processes.add(new Process("P2", 0, 8, 2));
                processes.add(new Process("P3", 0, 7, 1));
                processes.add(new Process("P4", 0, 3, 1));
                break;
            case 11:
//        // Dummy Data - Set 12 - Page 17
                processes.add(new Process("P1", 0, 10, 3));
                processes.add(new Process("P2", 0, 1, 1));
                processes.add(new Process("P3", 0, 2, 4));
                processes.add(new Process("P4", 0, 1, 5));
                processes.add(new Process("P5", 0, 5, 2));
                break;
            case 12:
//        // Dummy Data - Set 13 - Page 19
                processes.add(new Process("P1", 0, 24, 1));
                processes.add(new Process("P2", 0, 3, 1));
                processes.add(new Process("P3", 0, 3, 1));
                break;
            case 13:
//        // Dummy Data - Set 14 - Exam
                processes.add(new Process("P1", 0, 7, 1));
                processes.add(new Process("P2", 2, 4, 1));
                processes.add(new Process("P3", 4, 1, 1));
                processes.add(new Process("P4", 5, 4, 1));
                break;
            case 14:
//        // Dummy Data - Set 15 - Exam SJF NP & FCFS
                processes.add(new Process("P1", 0, 2, 1));
                processes.add(new Process("P2", 0, 4, 1));
                processes.add(new Process("P3", 2, 1, 1));
                processes.add(new Process("P4", 2, 3, 1));
                processes.add(new Process("P5", 3, 2, 1));
                break;
            case 15:
//        // Dummy Data - Set 16 - Priority P
                processes.add(new Process("P1", 0, 2, 5));
                processes.add(new Process("P2", 1, 4, 6));
                processes.add(new Process("P3", 2, 1, 3));
                processes.add(new Process("P4", 3, 3, 2));
                processes.add(new Process("P5", 4, 2, 1));
                break;
            case 16:
//        // Dummy Data - Set 17 - Priority P
                processes.add(new Process("P1", 0, 2, 5));
                processes.add(new Process("P2", 1, 2, 4));
                processes.add(new Process("P3", 2, 2, 3));
                processes.add(new Process("P4", 3, 2, 2));
                processes.add(new Process("P5", 4, 2, 1));
                processes.add(new Process("P6", 15, 2, 1));
                break;
        }


    }
}
