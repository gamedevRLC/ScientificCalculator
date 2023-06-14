import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class NumberOperationPanel extends JPanel{
    
    // The location of this panel on the window
    private int x;
    private int y;

    // A list for all the buttons on the panel
    List<JButton> buttons = new ArrayList<JButton>();
    
    //The border for the panel
    LineBorder lineBorder = new LineBorder(Color.white, 3);

    public NumberOperationPanel(int _width, int _height){
        x = 160;
        y = 250;
        for(int i = 0, num = 7; i < 13; i++){
            if(num == 10 && i == 3){
                buttons.add(new JButton("÷"));
                num = 4;
            } else if (num == 7 && i == 7) {
                buttons.add(new JButton("•"));
                num = 1;
            } else if (num == 4 && i == 11) {
                buttons.add(new JButton("-"));
                num = 0;
            } else {
                buttons.add(new JButton(num + ""));
                num++;
            }
        }

        buttons.add(new JButton("."));
        buttons.add(new JButton("="));
        buttons.add(new JButton("+"));

        buttons.forEach((button) -> this.add(button));
        this.setLayout(new GridLayout(4, 4));
        this.setBounds(x, y, 200, 200);
        this.setBackground(Color.black);
        this.setOpaque(true);
        this.setBorder(lineBorder);
        this.setVisible(true);
    }
}