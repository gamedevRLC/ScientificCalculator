import java.awt.Color;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class CalculatorPanel extends JPanel implements ActionListener{

    // This panels has access to the width and height of the window
    private int width;
    private int height;

    // The textfield for the output of the calculator when entering calculations and getting an answer
    JTextField outputField;

    // The text of the textfield will need to be continuously added to overtime, so this StringBuilder will help with that
    StringBuilder stringBuilder;

    // The panel for the numbers and arithmetic operations
    NumberOperationPanel numOpPanel;

    // Use this to drag and drop components on the screen when designing the panel
    // DragListener dragListener = new DragListener();

    public CalculatorPanel(int _width, int _height){
        width = _width;
        height = _height;
        stringBuilder = new StringBuilder();
        numOpPanel = new NumberOperationPanel(this);

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
        // Get the source of the ActionEvent and convert it into a JButton
        JButton button = (JButton) e.getSource();

        //If the "=" button was pressed, clear the string in the outputfield
        if(button.getText().equals("=")){
            stringBuilder.delete(0, stringBuilder.length());
        } else { // else, append the character of the button that was pressed
            stringBuilder.append(button.getText());
        }

        // Change the text to the string builder's new text
        outputField.setText(stringBuilder.toString());
    }
}