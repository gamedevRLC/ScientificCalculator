import java.awt.Color;
import java.awt.Point;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class CalculatorPanel extends JPanel{

    // This panels has access to the width and height of the window
    private int width;
    private int height;


    NumberOperationPanel numOpPanel;
    Point panelLocation, prevPanelLocation;
    DragListener dragListener = new DragListener();

    public CalculatorPanel(int _width, int _height){
        width = _width;
        height = _height;
        numOpPanel = new NumberOperationPanel(width, height);

        this.setBounds(0, 0, width, height);
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setOpaque(true);
        this.add(numOpPanel);
        this.setVisible(true);
        // this.addMouseMotionListener(dragListener);
    }

    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e){
            Point currentPoint = e.getPoint();
            numOpPanel.setBounds((int)currentPoint.getX(), (int)currentPoint.getY(), 200, 200);
            System.out.println("Location: " + (int)currentPoint.getX() + ", " + (int)currentPoint.getY());
            repaint();
        }
    }
}