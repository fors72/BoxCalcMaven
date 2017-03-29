package ua.com.eliteupakovka;


import ua.com.eliteupakovka.conctruction.DynamicConstruction;
import ua.com.eliteupakovka.conctruction.Sizes;
import ua.com.eliteupakovka.conctruction.WorkCost;
import ua.com.eliteupakovka.material.Carton;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.Paper;

import java.util.ArrayList;
import java.util.List;


public class Calc {
    int quantity;
    String paper, tistenie, lamination;
    DynamicConstruction construction;


//    Costs costs;


    public Calc(int quantity, DynamicConstruction construction, String lamination, Sizes sizeBox,int cartonId,int paperId,int kashirovkaId, Parameters parameters) {
        this.quantity = quantity;
        this.construction = construction;
        this.lamination = lamination;

//        costs = new Costs(1, 1.8, 0.16, 1, 50, 0.25, 0.1, 50, 0.06);
        CalcLab calcLab = CalcLab.get();
        this.construction = new DynamicConstruction(calcLab.getConstructionPartListByConstructionId(construction.getId(),sizeBox,parameters,quantity),construction.getWorkCost(),construction.getName(),construction.getId(),sizeBox,cartonId,paperId,kashirovkaId,quantity,construction.costs);

    }

}


