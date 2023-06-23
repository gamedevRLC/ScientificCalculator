import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class NumberOperationPanel extends JPanel{
    
    // The calculator panel needs to be able to listen to the buttons on the number/operation panel
    private CalculatorPanel calculatorPanel;

    // The location of this panel on the window
    private int x;
    private int y;
    private int width;
    private int height;

    // A list for all the buttons on the panel
    List<CalculatorButton> buttons = new ArrayList<CalculatorButton>();
    
    //The border for the panel
    LineBorder lineBorder = new LineBorder(Color.white, 3);

    public NumberOperationPanel(CalculatorPanel _calculatorPanel){
        x = 200;
        y = 250;
        width = 200;
        height = 200;
        calculatorPanel = _calculatorPanel;

        // This for loop ensures that the buttons arrangement similar to real calculators.
        // i.e. The top row is 47, 8, 9, ÷
        for(int i = 0, num = 7; i < 13; i++){
            if(num == 10 && i == 3){
                buttons.add(new CalculatorButton("÷", "operation"));
                num = 4;
            } else if (num == 7 && i == 7) {
                buttons.add(new CalculatorButton("•", "operation"));
                num = 1;
            } else if (num == 4 && i == 11) {
                buttons.add(new CalculatorButton("-", "operation"));
                num = 0;
            } else {
                buttons.add(new CalculatorButton(num + "", "number"));
                num++;
            }
        }

        //Adds the remaining symbols 
        buttons.add(new CalculatorButton(".", "operation"));
        buttons.add(new CalculatorButton("=", "operation"));
        buttons.add(new CalculatorButton("+", "operation"));

        // Add all the buttons to the panel
        buttons.forEach((button) -> {
            this.add(button);
            button.addActionListener(calculatorPanel);
        });

        //Does the work of setting up the panels appearance
        this.setLayout(new GridLayout(4, 4));
        this.setBounds(x, y, width, height);
        this.setBackground(Color.black);
        this.setOpaque(true);
        this.setBorder(lineBorder);
        this.setVisible(true);
    }
}