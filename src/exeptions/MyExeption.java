package exeptions;

public class MyExeption extends Exception{
    private String message;
    public MyExeption(String message){
        super(message);
    }
}
