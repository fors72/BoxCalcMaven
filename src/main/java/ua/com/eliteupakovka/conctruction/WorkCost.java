package ua.com.eliteupakovka.conctruction;


public class WorkCost {
    double simple;
    double design;

    public WorkCost(double simple, double design) {
        this.simple = simple;
        this.design = design;
    }

    public void setSimple(double simple) {
        this.simple = simple;
    }

    public void setDesign(double design) {
        this.design = design;
    }

    public double getWorkCost(boolean isDesignPaper){
        if (isDesignPaper){
            return design;
        }else {
            return simple;
        }
    }
}
