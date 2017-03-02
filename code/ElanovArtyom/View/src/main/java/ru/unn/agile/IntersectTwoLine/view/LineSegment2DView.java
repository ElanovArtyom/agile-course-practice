package ru.unn.agile.IntersectTwoLine.view;


import ru.unn.agile.IntersectTwoLine.viewmodel.ViewModel;
import ru.unn.agile.IntersectTwoLine.infrastructure.Logger;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;


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
    private JList<String> lstLog;

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
                LineSegment2DView.this.viewModel.processKeyInTF(e.getKeyCode());
                backBind();
            }
        };
        a1TextField.addKeyListener(keyListener);
        b1TextField.addKeyListener(keyListener);
        c1TextField.addKeyListener(keyListener);
        a2TextField.addKeyListener(keyListener);
        b2TextField.addKeyListener(keyListener);
        c2TextField.addKeyListener(keyListener);

        FocusAdapter focusLostListener = new FocusAdapter() {
            public void focusLost(final FocusEvent e) {
                bind();
                LineSegment2DView.this.viewModel.focusLost();
                backBind();
            }
        };
        a1TextField.addFocusListener(focusLostListener);
        b1TextField.addFocusListener(focusLostListener);
        c1TextField.addFocusListener(focusLostListener);
        a2TextField.addFocusListener(focusLostListener);
        b2TextField.addFocusListener(focusLostListener);
        c2TextField.addFocusListener(focusLostListener);
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("LineSegment2DView");

        Logger logger = new Logger("./Calculator.logging");
        frame.setContentPane(new LineSegment2DView(new ViewModel(logger)).mainPanel);
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

    private void getLog() {
        List<String> log = viewModel.getLogs();
        String[] tmp = log.toArray(new String[log.size()]);
        lstLog.setListData(tmp);
    }
    private void backBind() {
        buttonCheck.setEnabled(viewModel.isCalculateButtonEnabled());

        textSolve.setText(viewModel.getResult());
        lbStatus.setText(viewModel.getStatus());

       getLog();
    }
}
