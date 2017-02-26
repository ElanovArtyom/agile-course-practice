package ru.unn.agile.IntersectTwoLine.model;


public class LineSegment2D {

    private final double coefficientA;
    private final double coefficientB;
    private final double coefficientC;

    public LineSegment2D(final double factorA, final double factorB, final double factorC) {
        this.coefficientA = factorA;
        this.coefficientB = factorB;
        this.coefficientC = factorC;
    }

    public LineSegment2D(final String factorA, final String factorB, final String factorC) {
        this.coefficientA = Double.parseDouble(factorA);
        this.coefficientB = Double.parseDouble(factorB);
        this.coefficientC = Double.parseDouble(factorC);
    }

    public double getA() {
        return coefficientA;
    }

    public double getB() {
        return coefficientB;
    }

    public double getC() {
        return coefficientC;
    }

    public String toFormula() {

        return Formatter.getFormatted(this);
    }

    public String checkIntersection(final LineSegment2D lineSegment2) {
        double coordinatesX, coordinatesY;
        if (lineSegment2.getA() / getA() == lineSegment2.getB() / getB()
                & lineSegment2.getA() / getA() == lineSegment2.getC() / getC()) {
            return "Lines match";
        } else if (lineSegment2.getA() / getA() == lineSegment2.getB() / getB()) {
            return "Lines are parallel";
        } else {
            coordinatesX = (getC() * lineSegment2.getB() - lineSegment2.getC() * getB())
                    / (getA() * lineSegment2.getB() - getB() * lineSegment2.getA());
            coordinatesY = (getA() * lineSegment2.getC() - lineSegment2.getA() * getC())
                    / (getA() * lineSegment2.getB() - lineSegment2.getA() * getB());
            return "Intersection point ( " + coordinatesX + " ; " + coordinatesY + " )";

        }
    }
}
