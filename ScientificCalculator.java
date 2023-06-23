import javax.swing.JFrame;

public class ScientificCalculator{

    // Defining the dimensions of the window
    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    // The window for the program
    JFrame frame = new JFrame("Scientific Calculator");

    public ScientificCalculator(){

        // Setting up the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.add(new CalculatorPanel(WIDTH, HEIGHT));
        frame.setVisible(true);
    }
}