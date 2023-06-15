import java.awt.Color;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class CalculatorPanel extends JPanel{

    // This panels has access to the width and height of the window
    private int width;
    private int height;

    // The textfield for the output of the calculator when entering calculations and getting an answer
    JTextField outputField;

    // The panel for the numbers and arithmetic operations
    NumberOperationPanel numOpPanel;

    // Use this to drag and drop components on the screen when designing the panel
    // DragListener dragListener = new DragListener();

    public CalculatorPanel(int _width, int _height){
        width = _width;
        height = _height;
        numOpPanel = new NumberOperationPanel();

        // Setting up the outputField
        outputField = new JTextField();
        outputField.setBounds(160, 170, 200, 50);
        outputField.setOpaque(true);
        outputField.setBackground(Color.gray);
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
}