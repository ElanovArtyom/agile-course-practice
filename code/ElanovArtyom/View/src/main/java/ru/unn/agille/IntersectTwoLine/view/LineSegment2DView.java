package ru.unn.agille.IntersectTwoLine.view;

import ru.unn.agile.IntersectTwoLine.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Artyom on 021 21.02.17.
 */
public final class LineSegment2DView {

    private ViewModel viewModel;
    private JPanel mainPanel;
    private JTextField a1TextField;
    private JTextField b1TextField;
    private JTextField c1TextField;
    private JButton buttonCheck;
    private JTextField textSolve;
    private JLabel lbSolve;
    private JTextField b2TextField;
    private JTextField a2TextField;
    private JTextField c2TextField;
    private JLabel lbStatus;

    private LineSegment2DView() {

    }

    private LineSegment2DView(final ViewModel viewModel) {
        this.viewModel = viewModel;
        backBind();


        buttonCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                LineSegment2DView.this.viewModel.checkIntersect();
                backBind();
            }
        });


        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                LineSegment2DView.this.viewModel.processKeyInTextField(e.getKeyCode());
                backBind();
            }
        };
        a1TextField.addKeyListener(keyListener);
        b1TextField.addKeyListener(keyListener);
        c1TextField.addKeyListener(keyListener);
        a2TextField.addKeyListener(keyListener);
        b2TextField.addKeyListener(keyListener);
        c2TextField.addKeyListener(keyListener);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("LineSegment2DView");
        frame.setContentPane(new LineSegment2DView(new ViewModel()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bind() {
        viewModel.setA1(a1TextField.getText());
        viewModel.setB1(b1TextField.getText());
        viewModel.setC1(c1TextField.getText());
        viewModel.setA2(a2TextField.getText());
        viewModel.setB2(b2TextField.getText());
        viewModel.setC2(c2TextField.getText());

    }

    private void backBind() {
        buttonCheck.setEnabled(viewModel.isCalculateButtonEnabled());

        textSolve.setText(viewModel.getResult());
        lbStatus.setText(viewModel.getStatus());
    }
}
