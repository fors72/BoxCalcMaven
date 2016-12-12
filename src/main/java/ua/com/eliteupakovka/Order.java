package ua.com.eliteupakovka;

import ua.com.eliteupakovka.material.Carton;
import ua.com.eliteupakovka.material.Paper;

import java.util.ArrayList;
import java.util.List;

class Order {
    String name;
    int quantity;
    double widthBox;
    double lengthBox;
    double heightB;
    double heightT;
    double cost,cartonCost,paperCost,tapeCost,glueCost,workCost,cuttingCost,pressinCost,rentCost,stretchCost,extraCost,laminationCost;
    double prise;

    Construction construction;
    List<PossibleResults<Carton>> listTopResultCarton = new ArrayList<>();
    List<PossibleResults<Paper>> listTopResultPaper = new ArrayList<>();



    Order(Calc calc) {
        this.construction = calc.constructionNew;
        name = calc.name;
        quantity = calc.quantity;
        widthBox = calc.width;
        lengthBox = calc.length;
        heightB = calc.heightBottom;
        heightT = calc.heightTop;
        listTopResultCarton= calc.constructionNew.listTopResultCarton;
        listTopResultPaper = calc.constructionNew.listTopResultPaper;
        extraCost = calc.constructionNew.addCosts * quantity;
        int quantCarton = 0,quantPaper= 0;
        for (PossibleResults pr:listTopResultCarton){
            cartonCost = cartonCost + (pr.quantityMaterial*pr.material.getCost());
            quantCarton = quantCarton + pr.quantityMaterial;
        }
        double lamin = 0;
        for (PossibleResults pr:listTopResultPaper){
            paperCost = paperCost +(pr.quantityMaterial * pr.material.getCost());
            quantPaper = quantPaper + pr.quantityMaterial;
            if (pr.lamin > 0){
                lamin = lamin + pr.lamin;
            }
        }
        tapeCost = calc.costs.getTape() * quantity;
        glueCost = calc.costs.getGlue() * quantity;
        rentCost = calc.costs.getRent() * quantity;
        stretchCost = calc.costs.getStretch() * quantity;
        if ((quantCarton* calc.costs.getCutCarton() + quantPaper * calc.costs.getCutPaper()) <= calc.costs.getMinCutting()) cuttingCost = calc.costs.getMinCutting();
        if ((quantCarton* calc.costs.getCutCarton() + quantPaper * calc.costs.getCutPaper()) > calc.costs.getMinCutting()) cuttingCost = (quantCarton * calc.costs.getCutCarton() + quantPaper * calc.costs.getCutPaper());
        pressinCost = calc.costs.getFitting() + calc.costs.getCutting() * quantity;

        if (calc.paper.equals("меловка")|| calc.paper.equals("печать на меловке")){
            workCost = 3 * quantity;
        }else if (calc.paper.equals("imitlin")|| calc.paper.equals("stardream")|| calc.paper.equals("malmero")){
            workCost = 7 * quantity;
        }

        if (calc.lamination.equals("матовая")){
            laminationCost = (int)((lamin /7000) * quantity * 3.5);
        } else if (calc.lamination.equals("глянцевая")){
            laminationCost = (int)((lamin /7000) * quantity * 3.5);
        }


        cost = cartonCost + paperCost + tapeCost + glueCost + workCost + cuttingCost + pressinCost + rentCost + stretchCost + extraCost + laminationCost;
        prise = cost + getProfit(quantity) * quantity;




    }
    private double getProfit(int quantity){
        double profit = 0;
        if (quantity >= 1000){
            profit = 20;
        }else if (quantity >= 500 && quantity < 1000){
            profit = 25 - (0.01 *(quantity - 500));
        }else if (quantity >= 300 && quantity < 500){
            profit = 28 - (0.015 *(quantity - 300));
        }else if (quantity >= 100 && quantity < 300){
            profit = 30 - (0.01 *(quantity - 100));
        }else if (quantity >= 50 && quantity < 100){
            profit = 40 - (0.2 *(quantity - 50));
        }else if (quantity >= 25 && quantity < 50){
            profit = 50 - (0.4 *(quantity - 25));
        }
        return profit;
    }
}
