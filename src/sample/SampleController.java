package sample;

import View.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SampleController implements Initializable{
    public static Command command;

    @FXML
    private ListView<String> Listview;

    @FXML
    private  Button runButton;

    private Interpreter Interpreter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Interpreter = new Interpreter();
        try{
            Interpreter.runMenu();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        Map<String, Command> Mapcommands = Interpreter.getCommands();
        ObservableList<String> Obscommands = this.Listview.getItems();

        for(Map.Entry c: Mapcommands.entrySet())
        {

            if(!(c.getKey().equals("0"))){

                if(c.getValue() instanceof RunExample){

                    Command command = (RunExample) c.getValue();
                    Obscommands.add("Key: " + command.getKey() + " Description: " + command.getDescription());

                }
            }
        }

        Listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    public void startProgram() throws IOException {
        String program = Listview.getSelectionModel().getSelectedItem();
        Integer id;
        try{
            id = Integer.parseInt(program.substring(5,7));
        }catch (Exception e)
        {
            id = Integer.parseInt(program.substring(5,6));
        }
        String cmdID = id.toString();
        Interpreter = new Interpreter();
        try{
            Interpreter.runMenu();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        this.command = Interpreter.getCommands().get(cmdID);
        Parent loader = FXMLLoader.load(getClass().getResource("centralWindow.fxml"));
        runButton.getScene().setRoot(loader);
    }
}
