import java.awt.Color;
// import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.LinkedList;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseMotionAdapter;

public class CalculatorPanel extends JPanel implements ActionListener{

    // This panels has access to the width and height of the window
    private int width;
    private int height;

    // int for the position of the caret in the textfield
    private int caretPosition;

    // The textfield for the output of the calculator when entering calculations and getting an answer
    JTextField outputField;

    // Stacks for the operations in the calculation
    Deque<String> opStack;

    // Stack for the numbers in the calculation
    Deque<Integer> numStack;

    // The stack that keeps a record of the full expression as list of strings
    Deque<String> mainStack;

    // The text of the textfield will need to be continuously added to overtime, so this StringBuilder will help with that
    StringBuilder stringBuilder;

    // The text of an operand typed in before the next operation button
    StringBuilder operand;

    // The panel for the numbers and arithmetic operations
    NumberOperationPanel numOpPanel;

    // The panel for math functions and constants
    FunctionCostantPanel funcCostPanel;

    // The panel for misc. buttons
    MiscPanel miscPanel;

    // Use this to drag and drop components on the screen when designing the panel
    // DragListener dragListener = new DragListener();

    public CalculatorPanel(int _width, int _height){
        width = _width;
        height = _height;
        stringBuilder = new StringBuilder();
        operand = new StringBuilder();
        numOpPanel = new NumberOperationPanel(this);
        funcCostPanel = new FunctionCostantPanel(this);
        miscPanel = new MiscPanel(this);
        numStack = new LinkedList<Integer>();
        opStack = new LinkedList<String>();
        mainStack = new LinkedList<String>();

        // Setting up the outputField
        outputField = new JTextField();
        outputField.setBounds(50, 170, 500, 50);
        outputField.setOpaque(true);
        outputField.setBackground(Color.gray);
        outputField.setHorizontalAlignment(JTextField.TRAILING);
        outputField.setVisible(true);
        outputField.setHighlighter(null);
        caretPosition = outputField.getCaretPosition();

        // Setting up the panel
        this.setBounds(0, 0, width, height);
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setOpaque(true);
        this.add(numOpPanel);
        this.add(funcCostPanel);
        this.add(outputField);
        this.add(miscPanel);
        this.setVisible(true);

        // this.addMouseMotionListener(dragListener);
    }

    // This inner class will be used to drag components on the screen.
    // private class DragListener extends MouseMotionAdapter {

    //     // When dragging the mouse use the point as the location for whatever component it moves
    //     public void mouseDragged(MouseEvent e){
    //         Point currentPoint = e.getPoint();
    //         miscPanel.setBounds((int)currentPoint.getX(), (int)currentPoint.getY(), miscPanel.getWidth(), miscPanel.getHeight());
    //         System.out.println("Location: " + (int)currentPoint.getX() + ", " + (int)currentPoint.getY());
    //         repaint();
    //     }
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the source of the ActionEvent and convert it into a CalculatorButton
        CalculatorButton button = (CalculatorButton) e.getSource();

        // If there's nothing in the operand string and the operation stack is empty
        if(operand.isEmpty() && opStack.isEmpty() && numStack.isEmpty()){
            // Clear the string displayed in the text field
            stringBuilder.delete(0, stringBuilder.length());
        }

        //If the "=" button was pressed
        if(button.getType().equals(Button.EQUALS)){
            // push the current operand into the number stack
            if(!operand.toString().equals("")){
                numStack.push(Integer.parseInt(operand.toString()));
                operand.delete(0, operand.length());
            }
            
            // clear the textfield and display the calculation
            mainStack.clear();
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder.append(calculate(opStack, numStack));
        } 
        
        handleText(button);
        handleButtonTypes(button);
    
        // Change the text to the string builder's new text and set the position of the caret
        outputField.setText(stringBuilder.toString());
        outputField.setCaretPosition(caretPosition);
        outputField.requestFocus();
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
            case "(":
            case ")": precedence = 3;
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

    private void handleText(CalculatorButton button){

        // if the button is a number
        if (button.getType().equals(Button.NUMBER)) { 
            // append it to the string
            stringBuilder.append(button.getText());
        } else if (button.getType().equals(Button.CLEAR)) { // if the clear button was pressed
            //clear the string displayed
            stringBuilder.delete(0, stringBuilder.length());
        } else if (button.getType().equals(Button.DELETE)) {
            stringBuilder.deleteCharAt(stringBuilder.length());
        } else { // else add the button's text to the textfield
        
            switch(button.getText()){
                case "+":
                case "-":
                case "•":
                case "÷": 
                    stringBuilder.append(button.getText());
                    mainStack.add(button.getText());
                break;
                case "(": 
                case ")": 
                    stringBuilder.append(button.getText());
                    mainStack.add(button.getText());
                break;
                case "a\u00B2": 
                    stringBuilder.append("\u00B2");
                    mainStack.add(button.getText());
                break;
            }
        }

        // Reset the position of the caret to the end of the string if the button wasn't an arrow
        if(!button.getType().equals(Button.ARROW)){
            caretPosition = stringBuilder.toString().length();
        }
    }

    private void handleButtonTypes(CalculatorButton button){
        // if a number was pressed
        if (button.getType().equals(Button.NUMBER)) { 
            // add it to the number stack
            operand.append(button.getText());
            try {
                int num = Integer.parseInt(mainStack.getLast());
                mainStack.removeLast();
                mainStack.addLast(operand.toString()+"");
            } catch (Exception e) {
                // TODO: handle exception
                mainStack.add(operand.toString());
            }
        } else if (button.getType().equals(Button.OPERATION)) { // else if a symbol button besides = was pressed
            operationButtons(button);
        } else if (button.getType().equals(Button.PARENTHESIS)) { // else if a parenthesis is pressed
            // Push it to the opStack
            opStack.push(button.getText());

            // If ) was pressed
            if(button.getText().equals(")")){
                // Add the current operand if it exists and do the entire expression
                if(!operand.toString().equals("")){
                    numStack.push(Integer.parseInt(operand.toString()));
                }
                doParenthesis();
            }
        } else if (button.getType().equals(Button.CLEAR)) { // else if the clear button was pressed

            // clear all the data structures
            opStack.clear();
            numStack.clear();
            operand.delete(0, operand.length());
            mainStack.clear();
        } else if (button.getType().equals(Button.ARROW)) { // else if an arrow button is pressed
            
            // move the position of the caret accordingly
            if(caretPosition >= 0 && button.getText().equals("\u2190")){
                caretPosition -= 1;
            } else if (caretPosition < stringBuilder.toString().length() && button.getText().equals("\u2192")) {
                caretPosition += 1;
            }
        } else if (button.getType().equals(Button.DELETE)) {
            
        }
    }

    private void operationButtons(CalculatorButton button){
        // push the operand onto the stack if it exists
        if(!operand.toString().equals("")){
            numStack.push(Integer.parseInt(operand.toString()));
        }
        
        // Take care of the order of operations
        PEMDASHandler(button.getText(), opStack, numStack);

        // add the operation stack to the operation stack
        opStack.push(button.getText());
        // reset the operand
        operand.delete(0, operand.length());
    }

    // This class takes care of calculating expressions within parenthesis
    private void doParenthesis(){
        // These deques are for the operations and operands respectively
        Deque<String> operations = new LinkedList<String>();
        Deque<Integer> operands = new LinkedList<Integer>();

        // Get all the operands and operations just after the corresponding (
        while(!opStack.peek().equals("(")){
            operations.add(opStack.pop());
        }

        for (int i = 0; i < operations.size() && !numStack.isEmpty(); i++) {
            operands.add(numStack.pop());
        }

        // clear the operand string
        operand.delete(0, operand.length());

        // calculate the full expression from the parenthesis group
        String result = calculate(operations, operands);

        // Push the result to the number stack and pop the ( from the opStack
        numStack.push(Integer.parseInt(result));
        opStack.pop();
    }

    private void PEMDASHandler(String op, Deque<String> ops, Deque<Integer> nums){
        // while there are operation in the stack and the current operation doesn't have the most precedence
        while(!ops.isEmpty() && getPrecedence(op) < getPrecedence(ops.peek())){
            // Pop off the numbers and operation from their respective stacks
            String operation = ops.pop();
            
            // Do the operation and push the result onto the number stack
            int result = 0;
            switch(operation){
                case "+":
                case "-":
                case "•":
                case "÷":
                    int num1 = nums.pop();
                    int num2 = nums.pop();
                    result = doOperation(num1, num2, operation);
                    nums.push(result);
                break;
                case "a\u00B2":
                    int num = nums.pop();
                    result = num * num;
                    nums.push(result);
                break;
                case "(": 
                    ops.push(operation);
            }
            break;
        }
    }

    // This method does the work of taking the numbers and operations in the stacks and doing the math
    // Takes in Deques for parameters
    public String calculate(Deque<String> ops, Deque<Integer> operands){
        
        // while the operations stack isn't empty
        while(!ops.isEmpty()){

            // Pop off the numbers and operation from their respective stacks
            String operation = ops.pop();
            
            // Do the operation and push the result onto the number stack
            int result = 0;
            switch(operation){
                case "+":
                case "-":
                case "•":
                case "÷":
                    int num1 = operands.pop();
                    int num2 = operands.pop();
                    result = doOperation(num1, num2, operation);
                    operands.push(result);
                break;
                case "a\u00B2":
                    int num = operands.pop();
                    result = doOperation(num, num, operation);
                    operands.push(result);
                break;
                case ")":
                break;
            }
        }

        // the remaining number will be the final result of the calculation
        return operands.pop() + "";
    }
}