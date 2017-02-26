package ru.unn.agile.IntersectTwoLine.viewmodel;

import ru.unn.agile.IntersectTwoLine.model.LineSegment2D;

/**
 * Created by Artyom on 026 26.02.17.
 */
public class ViewModel {

    private String a1;
    private String b1;
    private String c1;
    private String a2;
    private String b2;
    private String c2;
    private String result;
    private String status;
    private boolean isCalculateButtonEnabled;

    public ViewModel() {
        a1 = "";
        b1 = "";
        c1 = "";
        a2 = "";
        b2 = "";
        c2 = "";
        result = "";
        status = Status.WAITING;
        isCalculateButtonEnabled = false;
    }

    public final class Status {
        public static final String WAITING = "Please provide input data";
        public static final String READY = "Press button or Enter";
        public static final String BAD_FORMAT = "Bad format";
        public static final String SUCCESS = "Success";

        private Status() { }
    }

    public String getA1() {
        return a1;
    }

    public String getB1() {
        return b1;
    }

    public String getC1() {
        return c1;
    }

    public String getA2() {
        return a2;
    }

    public String getB2() {
        return b2;
    }

    public String getC2() {
        return c2;
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setA1(final String a1) {
        if (a1.equals(this.a1)) {
            return;
        }

        this.a1 = a1;
    }

    public void setB1(final String b1) {
        if (b1.equals(this.b1)) {
            return;
        }

        this.b1 = b1;
    }

    public void setC1(final String c1) {
        if (c1.equals(this.c1)) {
            return;
        }

        this.c1 = c1;
    }

    public void setA2(final String a2) {
        if (a2.equals(this.a2)) {
            return;
        }

        this.a2 = a2;
    }

    public void setB2(final String b2) {
        if (b2.equals(this.b2)) {
            return;
        }

        this.b2 = b2;
    }

    public void setC2(final String c2) {
        if (c2.equals(this.c2)) {
            return;
        }

        this.c2 = c2;
    }

    public boolean isCalculateButtonEnabled() {
        return isCalculateButtonEnabled;
    }

    private boolean isInputAvailable() {
        return !a1.isEmpty() && !b1.isEmpty() && !c1.isEmpty()
                && !a2.isEmpty() && !b2.isEmpty() && !c2.isEmpty();
    }
    public void processKeyInTextField(final int keyCode) {
        parseInput();

        if (keyCode == KeyboardKeys.ENTER) {
            enterPressed();
        }
    }

    private void enterPressed() {

        if (isCalculateButtonEnabled()) {
            checkIntersect();
        }
    }

    private boolean parseInput() {
        try {
            if (!a1.isEmpty()) {
                Double.parseDouble(a1);
            }
            if (!b1.isEmpty()) {
                Double.parseDouble(b1);
            }
            if (!c1.isEmpty()) {
                Double.parseDouble(c1);
            }
            if (!a2.isEmpty()) {
                Double.parseDouble(a2);
            }
            if (!b2.isEmpty()) {
                Double.parseDouble(b2);
            }
            if (!c2.isEmpty()) {
                Double.parseDouble(c2);
            }
        } catch (Exception e) {
            status = Status.BAD_FORMAT;
            isCalculateButtonEnabled = false;
            return false;
        }

        isCalculateButtonEnabled = isInputAvailable();
        if (isCalculateButtonEnabled) {
            status = Status.READY;
        } else {
            status = Status.WAITING;
        }

        return isCalculateButtonEnabled;
    }

    public void checkIntersect() {
        if (!parseInput()) {
            return;
        }

        LineSegment2D z1 = new LineSegment2D(a1, b1, c1);
        LineSegment2D z2 = new LineSegment2D(a2, b2, c2);
        String z3;

        z3 = z1.checkIntersection(z2);


        result = z3;
        status = Status.SUCCESS;
    }
}

