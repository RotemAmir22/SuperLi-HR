package Presentation.GUI_HR;

import javax.swing.*;
import java.awt.*;

public class CellShift {
    private JPanel panel;
    private JTextField M;
    private JTextField E;
    public CellShift(){
        M = new JTextField();
        M.setText("0");
        E = new JTextField();
        E.setText("0");
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.add(new JLabel("M:"));
        panel.add(M);
        panel.add(new JLabel("E:"));
        panel.add(E);
    }

    public JPanel showPanel(){
        return panel;
    }
}
