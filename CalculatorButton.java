import javax.swing.JButton;

public class CalculatorButton extends JButton {
    
    Button type;

    public CalculatorButton(String text, String button){
        super(text);
        switch(button){
            case "number": type = Button.NUMBER;
            break;
            case "operation" : type = Button.OPERATION;
            break;
        }
    }

    public Button getType(){
        return type;
    }

}
