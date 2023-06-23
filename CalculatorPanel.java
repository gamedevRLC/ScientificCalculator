import java.awt.Color;
// import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseMotionAdapter;
import java.util.Stack;

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

    FunctionCostantPanel funcCostPanel;

    // Use this to drag and drop components on the screen when designing the panel
    // DragListener dragListener = new DragListener();

    public CalculatorPanel(int _width, int _height){
        width = _width;
        height = _height;
        stringBuilder = new StringBuilder();
        operand = new StringBuilder();
        numOpPanel = new NumberOperationPanel(this);
        funcCostPanel = new FunctionCostantPanel(this);
        numStack = new Stack<Integer>();
        opStack = new Stack<String>();

        // Setting up the outputField
        outputField = new JTextField();
        outputField.setBounds(50, 170, 500, 50);
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
        this.add(funcCostPanel);
        this.add(outputField);
        this.setVisible(true);

        // this.addMouseMotionListener(dragListener);
    }

    // This inner class will be used to drag components on the screen.
    // private class DragListener extends MouseMotionAdapter {

    //     // When dragging the mouse use the point as the location for whatever component it moves
    //     public void mouseDragged(MouseEvent e){
    //         Point currentPoint = e.getPoint();
    //         outputField.setBounds((int)currentPoint.getX(), (int)currentPoint.getY(), 100, 50);
    //         System.out.println("Location: " + (int)currentPoint.getX() + ", " + (int)currentPoint.getY());
    //         repaint();
    //     }
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the source of the ActionEvent and convert it into a CalculatorButton
        CalculatorButton button = (CalculatorButton) e.getSource();

        // If there's nothing in the operand string and the operation stack is empty
        if(operand.isEmpty() && opStack.isEmpty()){
            // Clear the string displayed in the text field
            stringBuilder.delete(0, stringBuilder.length());
        }

        
        //If the "=" button was pressed
        if(button.getText().equals("=")){
            // push the current operand into the number stack
            if(!operand.toString().equals("")){
                numStack.push(Integer.parseInt(operand.toString()));
            }
            operand.delete(0, operand.length());
            
            // clear the textfield and display the calculation
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder.append(calculate());
        } else if (button.getType().equals(Button.NUMBER)) { // else if the button is a number
            // append it to the string
            stringBuilder.append(button.getText());
        } else { // else add the button's text to the textfield
        
            switch(button.getText()){
                case "+":
                case "-":
                case "•":
                case "÷": stringBuilder.append(button.getText());
                break;
                case "a\u00B2": stringBuilder.append("\u00B2");
                break;
            }
        }
    
        // if a number was pressed
        if (button.getType().equals(Button.NUMBER)) { 
            // add it to the number stack
            operand.append(button.getText());
        } else if (button.getType().equals(Button.OPERATION) && 
        !button.getText().equals("=")) { // else if a symbol button besides = was pressed
            
            // push the operand onto the stack if it exists
            if(!operand.toString().equals("")){
                numStack.push(Integer.parseInt(operand.toString()));
            }
            
            // while there are operation in the stack and the current operation doesn't have the most precedence
            while(!opStack.empty() && getPrecedence(button.getText()) <= getPrecedence(opStack.peek())){
                
                // Pop off the numbers and operation from their respective stacks
                int num1 = numStack.pop();
                String operation = opStack.pop();
                
                //Do the operation and push the result onto the number stack
                int result = 0;
                switch(operation){
                    case "+":
                    case "-":
                    case "•":
                    case "÷":
                        int num2 = numStack.pop();
                        result = doOperation(num1, num2, operation);
                    break;
                    case "a\u00B2":
                        result = doOperation(num1, num1, operation);
                    break;
                }
                numStack.push(result);
            }
            
            // add the operation stack to the operation stack
            opStack.push(button.getText());
            // reset the operand
            operand.delete(0, operand.length());
        }
    
        // Change the text to the string builder's new text
        outputField.setText(stringBuilder.toString());
    }

    // Return the precedence of an operation
    private int getPrecedence(String operation){
        int precedence = 0;
        switch(operation){
            case "+":
            case "-": precedence = 0;
            break;
            case "•":
            case "÷": precedence = 1;
            break;
            case "a\u00B2": precedence = 2;
            break;
        }
        return precedence;
    }

    // Do an operation with the given numbers and return the result
    public int doOperation(int num1, int num2, String operation) {
        int result = 0;

        switch(operation){
            case "+": result = num1 + num2;
            break;
            case "-": result = num2 - num1;
            break;
            case "a\u00B2":
            case "•": result = num1 * num2;
            break;
            case "÷": result = num2 / num1;
            break;
        }

        return result;
    }

    // This method does the work of taking the numbers and operations in the stacks and doing the math
    public String calculate(){
        
        // while the operations stack isn't empty
        while(!opStack.empty()){

            // Pop off the numbers and operation from their respective stacks
            int num1 = numStack.pop();
            String operation = opStack.pop();
                
            // Do the operation and push the result onto the number stack
            int result = 0;
            switch(operation){
                case "+":
                case "-":
                case "•":
                case "÷":
                    int num2 = numStack.pop();
                    result = doOperation(num1, num2, operation);
                break;
                case "a\u00B2":
                    result = doOperation(num1, num1, operation);
                break;
            }
            numStack.push(result);
        }

        // the remaining number will be the final result of the calculation
        return numStack.pop() + "";
    }
}