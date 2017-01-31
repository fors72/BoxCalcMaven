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
    String name, paper, tistenie, lamination;
    DynamicConstruction construction;


    Costs costs;


    public Calc(String name, int quantity, DynamicConstruction construction, String tistenie, String lamination, Sizes sizeBox,int cartonId,int paperId,int kashirovkaId, Parameters parameters) {
        this.name = name;
        this.quantity = quantity;
        this.construction = construction;
        this.tistenie = tistenie;
        this.lamination = lamination;

        costs = new Costs(1, 1.8, 0.16, 1, 50, 0.25, 0.1, 200, 0.24);
        CalcLab calcLab = CalcLab.get();
        this.construction = new DynamicConstruction(calcLab.getConstructionPartListByConstructionId(construction.getId(),sizeBox,parameters,quantity),construction.getWorkCost(),construction.getName(),construction.getId(),sizeBox,cartonId,paperId,kashirovkaId,quantity);

    }


    public static <T extends Material> List<String> getSimplListForView(List<T> list) {
        List<String> arr = new ArrayList<>();
        boolean bl = true;
        for (T ob : list) {
            for (String str : arr) {
                if (str.equals(ob.toString())) {
                    bl = false;
                }
            }
            if (bl) {
                arr.add(ob.toString());
            }
            bl = true;
        }
        return arr;
    }

    public static <T extends Material> List<T> getListOfSelectItem(String str,List<T> list) {
        List<T> newList = new ArrayList<>();
        for (T ob:list){
            if (str.equals(ob.toString())){
                newList.add(ob);
            }
        }
    return newList;
    }
}


