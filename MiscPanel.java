import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.LineBorder;

public class MiscPanel extends JPanel{
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

    public MiscPanel(CalculatorPanel _calculatorPanel){
        x = 450;
        y = 250;
        width = 100;
        height = 200;
        calculatorPanel = _calculatorPanel;

        //Adds the remaining symbols 
        buttons.add(new CalculatorButton("(", "operation"));
        buttons.add(new CalculatorButton(")", "operation"));
        buttons.add(new CalculatorButton("\u2190", "operation"));
        buttons.add(new CalculatorButton("\u2192", "operation"));
        buttons.add(new CalculatorButton("\u2421", "operation"));
        buttons.add(new CalculatorButton("clear", "operation"));
        
        // Add all the buttons to the panel
        buttons.forEach((button) -> {
            this.add(button);
            button.addActionListener(calculatorPanel);
        });

        //Does the work of setting up the panels appearance
        this.setLayout(new GridLayout(3, 2));
        this.setBounds(x, y, width, height);
        this.setBackground(Color.black);
        this.setOpaque(true);
        this.setBorder(lineBorder);
        this.setVisible(true);
    }

}