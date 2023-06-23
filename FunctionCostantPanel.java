import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class FunctionCostantPanel extends JPanel{
    
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

    public FunctionCostantPanel(CalculatorPanel _calculatorPanel){
        x = 10;
        y = 250;
        width = 170;
        height = 300;
        calculatorPanel = _calculatorPanel;

        //Adds the symbols for various functions
        buttons.add(new CalculatorButton("a\u00B2", "operation"));
        buttons.add(new CalculatorButton("a\u1D47", "operation"));
        buttons.add(new CalculatorButton("|a|", "operation"));
        buttons.add(new CalculatorButton("\u221A", "operation"));
        buttons.add(new CalculatorButton("\u00B3\u221A", "operation"));
        buttons.add(new CalculatorButton("\u207F\u221A", "operation"));
        buttons.add(new CalculatorButton("sin", "operation"));
        buttons.add(new CalculatorButton("cos", "operation"));
        buttons.add(new CalculatorButton("tan", "operation"));
        buttons.add(new CalculatorButton("sin\u207B\u00B9", "operation"));
        buttons.add(new CalculatorButton("cos\u207B\u00B9", "operation"));
        buttons.add(new CalculatorButton("tan\u207B\u00B9", "operation"));
        buttons.add(new CalculatorButton("\u03C0", "operation"));
        buttons.add(new CalculatorButton("e", "operation"));
        buttons.add(new CalculatorButton("e\u02E3", "operation"));
        buttons.add(new CalculatorButton("log", "operation"));
        buttons.add(new CalculatorButton("ln", "operation"));
        buttons.add(new CalculatorButton("!", "operation"));
        
        // // Add all the buttons to the panel
        buttons.forEach((button) -> {
            this.add(button);
            button.addActionListener(calculatorPanel);
        });

        //Does the work of setting up the panels appearance
        this.setLayout(new GridLayout(6, 3));
        this.setBounds(x, y, width, height);
        this.setBackground(Color.black);
        this.setOpaque(true);
        this.setBorder(lineBorder);
        this.setVisible(true);
    }

}
