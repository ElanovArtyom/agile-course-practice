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
    private JTextField аTextField;
    private JTextField вTextField;
    private JTextField сTextField;
    private JButton проверитьНаПересечениеНАЖМИButton;
    private JTextField textField4;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
