package ua.com.eliteupakovka.conctruction;


public class WorkCost {
    double simple;
    double design;

    public WorkCost(double simple, double design) {
        this.simple = simple;
        this.design = design;
    }
    public double getWorkCost(boolean isSimplPaper){
        if (isSimplPaper){
            return simple;
        }else {
            return design;
        }
    }
}
