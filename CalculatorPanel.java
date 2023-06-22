import java.awt.Color;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorPanel extends JPanel implements ActionListener{

    // This panels has access to the width and height of the window
    private int width;
    private int height;

    // The textfield for the output of the calculator when entering calculations and getting an answer
    JTextField outputField;

    // Stacks for the operations in the calculation
    Stack<String> opStack;

    // Stacks for the numbers in the calculation
    Stack<Integer> numStack;

    // The text of the textfield will need to be continuously added to overtime, so this StringBuilder will help with that
    StringBuilder stringBuilder;

    // The text of an operand typed in before the next operation button
    StringBuilder operand;

    // The panel for the numbers and arithmetic operations
    NumberOperationPanel numOpPanel;

    // Use this to drag and drop components on the screen when designing the panel
    // DragListener dragListener = new DragListener();

    public CalculatorPanel(int _width, int _height){
        width = _width;
        height = _height;
        stringBuilder = new StringBuilder();
        operand = new StringBuilder();
        numOpPanel = new NumberOperationPanel(this);
        numStack = new Stack<Integer>();
        opStack = new Stack<String>();

        // Setting up the outputField
        outputField = new JTextField();
        outputField.setBounds(160, 170, 200, 50);
        outputField.setOpaque(true);
        outputField.setBackground(Color.gray);
        outputField.setHorizontalAlignment(JTextField.TRAILING);
        outputField.setVisible(true);

        // Setting up the panel
        this.setBounds(0, 0, width, height);
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setOpaque(true);
        this.add(numOpPanel);
        this.add(outputField);
        this.setVisible(true);

        // this.addMouseMotionListener(dragListener);
    }

    // This inner class will be used to drag components on the screen.
    private class DragListener extends MouseMotionAdapter {

        // When dragging the mouse use the point as the location for whatever component it moves
        public void mouseDragged(MouseEvent e){
            Point currentPoint = e.getPoint();
            outputField.setBounds((int)currentPoint.getX(), (int)currentPoint.getY(), 100, 50);
            System.out.println("Location: " + (int)currentPoint.getX() + ", " + (int)currentPoint.getY());
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the source of the ActionEvent and convert it into a CalculatorButton
        CalculatorButton button = (CalculatorButton) e.getSource();

        //If the "=" button was pressed, clear the string in the outputfield, and push the current operand into the number stack
        if(button.getText().equals("=")){
            numStack.push(Integer.parseInt(operand.toString()));
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder.append(calculate());
        } else { // else add the button's text to the textfield
            stringBuilder.append(button.getText());
        }
        
        // if a number was pressed, add it to the number stack
        if (button.getType().equals(Button.NUMBER)) { 
            operand.append(button.getText());
        } else if (button.getType().equals(Button.OPERATION) && !button.getText().equals("=")) {
            // else if an operation was pressed, add it to the operation stack
            opStack.push(button.getText());

            // push the current operand into the number stack and reset the operand
            numStack.push(Integer.parseInt(operand.toString()));
            operand.delete(0, operand.length());
        }

        // Change the text to the string builder's new text
        outputField.setText(stringBuilder.toString());
    }

    // This method does the work of taking the numbers and operations in the stacks and doing the math
    public String calculate(){
        
        // while the operations stack isn't empty
        while(!opStack.empty()){

            // Pop off the next two numbers and the operation
            int num1 = numStack.pop();
            int num2 = numStack.pop();
            String operation = opStack.pop();

            // Do the operation on the two numbers and push that onto the stack
            switch(operation){
                case "+": numStack.push((num1 + num2));
                break;
                case "-": numStack.push((num2 - num1));
                break;
                case "•": numStack.push((num1 * num2));
                break;
                case "÷": numStack.push((num2 / num1));
                break;
            }
        }

        // the remaining number will be the final result of the calculation
        return numStack.peek() + "";
    }
}