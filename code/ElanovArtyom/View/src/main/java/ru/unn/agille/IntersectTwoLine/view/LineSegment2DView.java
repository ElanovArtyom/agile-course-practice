package ru.unn.agille.IntersectTwoLine.view;

import javax.swing.*;

/**
 * Created by Artyom on 021 21.02.17.
 */
public class LineSegment2DView {
    public static void main(String[] args) {
        JFrame frame = new JFrame("LineSegment2DView");
        frame.setContentPane(new LineSegment2DView().main_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel main_panel;
    private JTextField a1TextField;
    private JTextField b1TextField;
    private JTextField c1TextField;
    private JButton Button;
    private JTextField textSolve;
    private JLabel lbSolve;
    private JTextField b2TextField;
    private JTextField a2TextField;
    private JTextField c2TextField;
    private JLabel lbStatus;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
