package sample;

import View.RunExample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ProgramState;
import model.adt.*;
import model.statement.IStatement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

public class CentralWindowController implements Initializable{
    private RunExample command;
    private ProgramState crtstate;
    private List<ProgramState> states;

    @FXML
    private Label SetNumber;

    @FXML
    private TableView<HeapTableData> HeapTable;

    @FXML
    private TableColumn<HeapTableData, Integer> AddressColumn;

    @FXML
    private TableColumn<HeapTableData, Value> ValueColumn;

    @FXML
    private TableView<SymbolTableData> SymbolTable;

    @FXML
    private TableColumn<SymbolTableData, String> NameColumn;

    @FXML
    private TableColumn<SymbolTableData, Value> VariableValueColumn;

    @FXML
    private ListView<String> OutList;

    @FXML
    private ListView<String> FileList;

    @FXML
    private ListView<String> ProgramStatesIDsList;

    @FXML
    private ListView<String> ExecutionStackList;

    @FXML
    private Button RunOneStepButton;

    @FXML
    private Button RestartButton;
    private void initializetables(){
        HeapTable.getColumns().clear();
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        ValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        ValueColumn.setMinWidth(100);
        HeapTable.getColumns().addAll(AddressColumn, ValueColumn);

        SymbolTable.getColumns().clear();
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("var_name"));
        VariableValueColumn.setMinWidth(100);

        VariableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        SymbolTable.getColumns().addAll(NameColumn, VariableValueColumn);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        command = (RunExample)SampleController.command;
        this.states = command.getCtrl().getMyRepository().getPrgList();
        crtstate = this.states.get(0);
        this.command.getCtrl().allStepGUI();
        initializetables();
        ProgramStatesIDsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    public void initOutList(ProgramState prg)
    {
        this.OutList.getItems().clear();
        MyIList<Value> out = prg.getOut();
        for(Value output: out.getQueue()) {
            OutList.getItems().add(output.toString());
        }

    }

    public void initFileList(ProgramState prg) {
        this.FileList.getItems().clear();
        MyIDictionary<StringValue, BufferedReader> filetbl = prg.getFileTbl()  ;
        for(Map.Entry file : filetbl.getDictionary().entrySet()) {
            FileList.getItems().add("File Name: " + file.getKey().toString());
        }
    }

    public void initExectionStackList(ProgramState program) {
        this.ExecutionStackList.getItems().clear();
        MyIStack<IStatement> stack = program.getStack();
        Stack<?> newStack = stack.clone();
        while(!newStack.isEmpty()) {
            this.ExecutionStackList.getItems().add(newStack.pop().toString());
        }

    }

    public void initProgramStatesIDsList(ProgramState prg) {
        this.ProgramStatesIDsList.getItems().clear();
        List<ProgramState> prgs = command.getCtrl().getMyRepository().getPrgList();
        for(ProgramState prg1: prgs) {
            this.ProgramStatesIDsList.getItems().add(Integer.toString(prg1.getId()));
        }
        String size = String.valueOf(prgs.size());
        SetNumber.setText(size);
    }

    public void initHeapTable(ProgramState prg) {
        this.HeapTable.getItems().clear();
        ObservableList<HeapTableData> heapdt = FXCollections.observableArrayList();
        MyIHeap<Integer, Value> heap = prg.getHeap();
        for(Map.Entry elem: heap.getDictionary().entrySet()) {
            heapdt.add(new HeapTableData((Integer) elem.getKey(), elem.getValue().toString()));
        }
        HeapTable.setItems(heapdt);
    }
    public void initSymbolTable(ProgramState prg) {
        this.SymbolTable.getItems().clear();
        ObservableList<SymbolTableData> symbldt = FXCollections.observableArrayList();

        MyIDictionary<String, Value> elems = prg.getSymTable();
        for(Map.Entry elem: elems.getDictionary().entrySet()) {
            symbldt.add(new SymbolTableData((String) elem.getKey(), elem.getValue().toString()));
        }
        SymbolTable.setItems(symbldt);
    }

    public void initForkStatement() {
        String stid = ProgramStatesIDsList.getSelectionModel().getSelectedItem();
        ProgramState crt = command.getCtrl().getMyRepository().getByID(Integer.parseInt(stid));
        this.crtstate = crt;
        initSymbolTable(this.crtstate);
        initExectionStackList(this.crtstate);
    }
    public void runOneStep() throws Exception{
        try {
            initProgramStatesIDsList(crtstate);
            initExectionStackList(crtstate);
            initSymbolTable(crtstate);
            if(!states.isEmpty()) {
                initFileList(states.get(0));
                initOutList(crtstate);
                initHeapTable(states.get(0));
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try
        {
            this.states = command.getCtrl().allStepGUI2();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void restart() throws Exception{
        Stage primaryStage = (Stage) RestartButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Toy Language Interpretor");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

}
