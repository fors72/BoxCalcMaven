package ua.com.eliteupakovka;

public class Costs {
    private double rent;
    private double glue;
    private double tape;
    private double stretch;
    private double minCutting;
    private double cutCarton;
    private double cutPaper;
    private double fitting;
    private double cutting;

    public Costs(double rent, double glue, double tape, double stretch, double minCutting, double cutCarton, double cutPaper, double fitting, double cutting) {
        this.rent = rent;
        this.glue = glue;
        this.tape = tape;
        this.stretch = stretch;
        this.minCutting = minCutting;
        this.cutCarton = cutCarton;
        this.cutPaper = cutPaper;
        this.fitting = fitting;
        this.cutting = cutting;
    }

    public double getFitting() {
        return fitting;
    }

    public double getCutting() {
        return cutting;
    }

    public double getCutCarton() {
        return cutCarton;
    }

    public double getCutPaper() {
        return cutPaper;
    }

    public double getMinCutting() {
        return minCutting;
    }

    public double getRent() {
        return rent;
    }

    public double getGlue() {
        return glue;
    }

    public double getTape() {
        return tape;
    }

    public double getStretch() {
        return stretch;
    }
}
