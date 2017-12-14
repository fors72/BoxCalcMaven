package ua.com.eliteupakovka;

import ua.com.eliteupakovka.conctruction.ConstructionPart;
import ua.com.eliteupakovka.conctruction.DynamicConstruction;
import ua.com.eliteupakovka.conctruction.Sizes;
import ua.com.eliteupakovka.material.Paper;

import java.util.ArrayList;
import java.util.List;

class Order {
    int quantity;
    Sizes sizes;
    double cost,cartonCost,paperCost,tapeCost,glueCost,workCost,cuttingCost,pressinCost,rentCost,stretchCost,extraCost,laminationCost;
    double prise;

    DynamicConstruction construction;
    List<PossibleResults> listTopResult = new ArrayList<>();




    Order(Calc calc) {
        this.construction = calc.construction;
        quantity = calc.quantity;
        this.sizes = calc.construction.getSizes();
        listTopResult = calc.construction.listTopResult;

        int quantCarton = 0,quantPaper= 0;
        int quantPrintO = 0;
        int quantPrintM = 0;
        for (PossibleResults pr: listTopResult){
            if (pr.part1.getType().equals("картон")){
                cartonCost += pr.quantityMaterial*pr.material.getCost();
                quantCarton += pr.quantityMaterial;
            }else if (pr.part1.getType().equals("бумага") || pr.part1.getType().equals("кашировка")){
                if (!pr.material.getName().equals("печать на меловке") || !pr.material.getName().equals("печать на офсете")) {
                    paperCost += pr.quantityMaterial*pr.material.getCost();
                }else {
                    if (pr.material.getName().equals("печать на меловке")){
                        quantPrintM += pr.quantityMaterial;
                    }else if (pr.material.getName().equals("печать на офсете")){
                        quantPrintO += pr.quantityMaterial;
                    }
                }
                quantPaper += pr.quantityMaterial;
            }else {
                extraCost += pr.quantityMaterial*pr.material.getCost();
            }
        }
        paperCost += getPrintPaperCost(quantPrintM) * quantPrintM;
        paperCost += getPrintPaperCost(quantPrintO) * quantPrintO;


        tapeCost = construction.costs.getTape() * quantity;
        rentCost = construction.costs.getRent() * quantity;
        stretchCost = construction.costs.getStretch() * quantity;
        if ((quantCarton* construction.costs.getCutCarton() + quantPaper * construction.costs.getCutPaper()) <= construction.costs.getMinCutting()) cuttingCost = construction.costs.getMinCutting();
        if ((quantCarton* construction.costs.getCutCarton() + quantPaper * construction.costs.getCutPaper()) > construction.costs.getMinCutting()) cuttingCost = (quantCarton * construction.costs.getCutCarton() + quantPaper * construction.costs.getCutPaper());

        workCost = 0;

        CalcLab calcLab = CalcLab.get();
        double lamin = 0;
        for (ConstructionPart cp: calc.construction.parts){
            if (cp.getType().equals("бумага")){
                if (cp.isLaminable()){
                    lamin += cp.getSizes().getWidth() * cp.getSizes().getLength();
                }
            }
            if (cp.getType().equals("бумага") || cp.getType().equals("кашировка")) {
                glueCost += cp.getSizes().getWidth() * cp.getSizes().getLength() * 0.0004;
            }

            workCost += (cp.getWorkCost().getWorkCost(calcLab.ifMaterialIsDesign(cp.getMaterialTypeId())));

            pressinCost += cp.isPressing() ? construction.costs.getFitting() + cp.getQuantity()* construction.costs.getCutting() : 0;
        }
        if (construction.getName().equals("Книга на магните") || construction.getName().equals("Книга на ленте")){
            double vol = construction.getSizes().getWidth() * construction.getSizes().getLength() * construction.getSizes().getHeightBottom();
            if (vol < 1500){
                workCost = 7 * quantity;
            }else if (vol >= 1500 && vol < 6000){
                workCost = 10 * quantity;
            }else {
                workCost = 13 * quantity;
            }
        }

        if (glueCost < 1.8)glueCost = 1.8;
        glueCost *= quantity;

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
        }else {
            profit = quantity * 100;
        }
        //todo:работа книга не меньшн 7 20х15х5 > 10 20x30x10 > 13
        return profit;
    }
    private double getPrintPaperCost(int quantity){
        double cost;
        if (quantity >= 750){
            cost = 7.5;
        }else if (quantity >= 600 && quantity < 750){
            cost = 8 - (0.003333 *(quantity - 600));
        }else if (quantity >= 500 && quantity < 600){
            cost = 9 - (0.01 *(quantity - 500));
        }else if (quantity >= 400 && quantity < 500){
            cost = 10 - (0.01 *(quantity - 400));
        }else if (quantity >= 300 && quantity < 400){
            cost = 11 - (0.01 *(quantity - 300));
        }else if (quantity >= 200 && quantity < 300){
            cost = 12 - (0.01 *(quantity - 200));
        }else if (quantity >= 100 && quantity < 200){
            cost = 15 - (0.03 *(quantity - 100));
        }else if (quantity >= 1 && quantity < 100){
            cost = 20 - (0.05 *(quantity - 1));
        }else {
            cost = 0;
        }
        return cost;
    }
}
