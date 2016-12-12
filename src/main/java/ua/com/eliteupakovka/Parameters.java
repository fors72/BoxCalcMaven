package ua.com.eliteupakovka;


public class Parameters {
    double toleranceOfwidthCartonBottom;
    double toleranceOflengthCartonBottom;
    double toleranceOfwidthCartonTop;
    double toleranceOflengthCartonTop;
    double toleranceOfwidthPaperBottom;
    double toleranceOflengthPapeBottomr;
    double toleranceOfwidthPaperTop;
    double toleranceOflengthPaperTop;


    public Parameters(double toleranceOfwidthCartonBottom, double toleranceOflengthCartonBottom, double toleranceOfwidthCartonTop, double toleranceOflengthCartonTop, double toleranceOfwidthPaperBottom, double toleranceOflengthPapeBottomr, double toleranceOfwidthPaperTop, double toleranceOflengthPaperTop) {
        this.toleranceOfwidthCartonBottom = toleranceOfwidthCartonBottom;
        this.toleranceOflengthCartonBottom = toleranceOflengthCartonBottom;
        this.toleranceOfwidthCartonTop = toleranceOfwidthCartonTop;
        this.toleranceOflengthCartonTop = toleranceOflengthCartonTop;
        this.toleranceOfwidthPaperBottom = toleranceOfwidthPaperBottom;
        this.toleranceOflengthPapeBottomr = toleranceOflengthPapeBottomr;
        this.toleranceOfwidthPaperTop = toleranceOfwidthPaperTop;
        this.toleranceOflengthPaperTop = toleranceOflengthPaperTop;
    }
    public Parameters(double toleranceAll) {
        this.toleranceOfwidthCartonBottom = toleranceAll;
        this.toleranceOflengthCartonBottom = toleranceAll;
        this.toleranceOfwidthCartonTop = toleranceAll;
        this.toleranceOflengthCartonTop = toleranceAll;
        this.toleranceOfwidthPaperBottom = toleranceAll;
        this.toleranceOflengthPapeBottomr = toleranceAll;
        this.toleranceOfwidthPaperTop = toleranceAll;
        this.toleranceOflengthPaperTop = toleranceAll;
    }


    public double getToleranceOfwidthCartonBottom() {
        return toleranceOfwidthCartonBottom;
    }

    public double getToleranceOflengthCartonBottom() {
        return toleranceOflengthCartonBottom;
    }

    public double getToleranceOfwidthCartonTop() {
        return toleranceOfwidthCartonTop;
    }

    public double getToleranceOflengthCartonTop() {
        return toleranceOflengthCartonTop;
    }

    public double getToleranceOfwidthPaperBottom() {
        return toleranceOfwidthPaperBottom;
    }

    public double getToleranceOflengthPapeBottomr() {
        return toleranceOflengthPapeBottomr;
    }

    public double getToleranceOfwidthPaperTop() {
        return toleranceOfwidthPaperTop;
    }

    public double getToleranceOflengthPaperTop() {
        return toleranceOflengthPaperTop;
    }
}

