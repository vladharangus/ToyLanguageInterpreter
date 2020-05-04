package View;

import controller.Controller;
import exeptions.MyExeption;
import model.ProgramState;
import model.adt.*;
import model.exp.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.Map;

public class Interpreter {
    private TextMenu menu;
    public void runMenu() throws MyExeption {

        menu = new TextMenu();

        MyIStack<IStatement> exeStack = new MyStack<IStatement>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
        MyIList<Value> out = new MyList<Value>();
        MyIDictionary<String, Type> typeenv1 = new MyDictionary<>();
        MyIDictionary<StringValue, BufferedReader> FileTable = new MyDictionary<StringValue, BufferedReader>() {
        };
        MyIHeap<Integer, Value> heap = new MyHeap<>();
        IStatement ex1 = new CompStatement(new VarDeclStatement("varf", new StringType()),
                new CompStatement(new AssignStatement("varf", new ValueExp(new StringValue("src/test.txt"))),
                        new CompStatement(new OpenRFile(new VarExp("varf")),
                                new CompStatement(new VarDeclStatement("varc", new IntType()),
                                        new CompStatement(new ReadFile(new VarExp("varf"),"varc"),
                                                new CompStatement(new PrintStatement(new VarExp("varc")),
                                                        new CompStatement(new ReadFile(new VarExp("varf"),"varc"),
                                                                new CompStatement(new PrintStatement(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
        ;
        try {
            ex1.typecheck(typeenv1);
            ProgramState prg1 = new ProgramState(exeStack, symtbl, out, FileTable, ex1, heap);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        //ctr1.addProgram(prg1);

        MyIStack<IStatement> exeStack2 = new MyStack<IStatement>();
        MyIDictionary<String, Value> symtbl2 = new MyDictionary<String, Value>();
        MyIDictionary<String, Type> typeenv2 = new MyDictionary<>();
        MyIList<Value> out2 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable2 = new MyDictionary<StringValue, BufferedReader>() {
        };
        MyIHeap<Integer, Value> heap2 = new MyHeap<>();
        IStatement ex2 = new CompStatement(new VarDeclStatement("v",new IntType()), new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                            new CompStatement(new AssignStatement("v", new ValueExp(new IntValue(10))), new CompStatement(new newStatement("a", new ValueExp(new IntValue(20))),
                                    new CompStatement(new forkStatement(new CompStatement(new wH("a", new ValueExp(new IntValue(30))),new CompStatement(new AssignStatement("v",
                                            new ValueExp(new IntValue(32))),new CompStatement(new PrintStatement(new VarExp("v")), new PrintStatement(new rH(new VarExp("a"))))))),
                                                new CompStatement(new PrintStatement(new VarExp("v")), new PrintStatement(new rH(new VarExp("a")))))))));

        try {
            ex2.typecheck(typeenv2);
            ProgramState prg2 = new ProgramState(exeStack2, symtbl2, out2, FileTable2, ex2, heap2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        //ctr2.addProgram(prg2);

        MyIStack<IStatement> exeStack3 = new MyStack<IStatement>();
        MyIDictionary<String, Value> symtbl3 = new MyDictionary<String, Value>();
        MyIList<Value> out3 = new MyList<Value>();
        MyIDictionary<String, Type> typeenv3 = new MyDictionary<>();
        MyIDictionary<StringValue, BufferedReader> FileTable3 = new MyDictionary<StringValue, BufferedReader>() {
        };
        MyIHeap<Integer, Value> heap3 = new MyHeap<>();
        IStatement ex3 = new CompStatement(new VarDeclStatement("a",new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExp(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VarExp("a"),new AssignStatement("v",new ValueExp(new
                                        IntValue(2))), new AssignStatement("v", new ValueExp(new IntValue(3)))), new PrintStatement(new
                                        VarExp("v"))))));
        try {
            ex3.typecheck(typeenv3);
            ProgramState prg3 = new ProgramState(exeStack3, symtbl3, out3, FileTable3, ex3, heap3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        MyIStack<IStatement> exeStack4 = new MyStack<IStatement>();
        MyIDictionary<String, Type> typeenv4 = new MyDictionary<>();
        MyIDictionary<String, Value> symtbl4 = new MyDictionary<String, Value>();
        MyIList<Value> out4 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable4 = new MyDictionary<StringValue, BufferedReader>() {
        };
        MyIHeap<Integer, Value> heap4 = new MyHeap<>();
        IStatement ex4 = new CompStatement(new VarDeclStatement("v", (new RefType(new IntType()))),
                new CompStatement(new newStatement("v", new ValueExp(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new newStatement("a", new VarExp("v")),
                                        new CompStatement(new PrintStatement(new VarExp("v")), new PrintStatement(new VarExp("a")))))));

        try
        {
            ex4.typecheck(typeenv4);
            ProgramState prg4 = new ProgramState(exeStack4, symtbl4, out4, FileTable4, ex4, heap4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        MyIStack<IStatement> exeStack5 = new MyStack<IStatement>();
        MyIDictionary<String, Type> typeenv5 = new MyDictionary<>();
        MyIDictionary<String, Value> symtbl5 = new MyDictionary<String, Value>();
        MyIList<Value> out5 = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> FileTable5 = new MyDictionary<StringValue, BufferedReader>() {
        };
        MyIHeap<Integer, Value> heap5 = new MyHeap<>();
        IStatement ex5 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExp(new IntValue(4))),
                        new CompStatement(new whileStatement(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">")
                                , new CompStatement(new PrintStatement(new VarExp("v")), new AssignStatement("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStatement(new VarExp("v")))));
 
        try
        {
            ex5.typecheck(typeenv5);
            ProgramState prg5 = new ProgramState(exeStack5, symtbl5, out5, FileTable5, ex5, heap5);
            IRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public Map<String, Command> getCommands()
    {
        return menu.getCommands();
    }
}