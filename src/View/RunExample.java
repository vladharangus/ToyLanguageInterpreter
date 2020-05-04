package View;

import controller.Controller;

public class RunExample extends Command {
    private Controller ctr;
    public RunExample(String key, String desc, Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    public Controller getCtrl(){
        return ctr;
    }
    @Override
    public void execute() {
        try{
            ctr.allStep(); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}

