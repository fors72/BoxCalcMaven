package ua.com.eliteupakovka.conctruction;


import ua.com.eliteupakovka.*;
import ua.com.eliteupakovka.material.Carton;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.Paper;

import java.util.*;

public class DynamicConstruction {
    public List<ConstructionPart> parts;
    WorkCost workCost;
    Sizes sizes;
    String name;
    int id;
    private int cartonId;
    private int paperId;
    private int kashirovkaId;
    public List<PossibleResults> listTopResult = new ArrayList<>();
    private Comparator<ConstructionPart> partComparator = (o1, o2) -> {
        int o2int = (int) o2.getSizes().getLength()*100 * (int) o2.getSizes().getWidth()*100;
        int o1int = (int) o1.getSizes().getLength()*100 * (int) o1.getSizes().getWidth()*100;
        return o2int - o1int;
    };
    private Comparator<ConstructionPart> partComparatorByTypeId = (o1, o2) -> o2.getMaterialTypeId() - o1.getMaterialTypeId();


    public DynamicConstruction(List<ConstructionPart> parts, WorkCost workCost, String name, int id, Sizes sizes, int cartonId, int paperId, int kashirovkaId, int quantity) {
        this.parts = parts;
        this.workCost = workCost;
        this.sizes = sizes;
        this.name = name;
        this.id = id;
        this.cartonId = cartonId;
        this.paperId = paperId;
        this.kashirovkaId = kashirovkaId;
        initListTopResult(parts);

    }

    public DynamicConstruction(String name, int id,WorkCost workCost) {
        this.name = name;
        this.id = id;
        this.workCost = workCost;
    }

    private <T extends Material> void initListTopResult(List<ConstructionPart> constructionParts){
        constructionParts.sort(partComparatorByTypeId);
        List<List<ConstructionPart>> listList = new ArrayList<>();
        int matTypeId = constructionParts.get(0).getMaterialTypeId();
        List<ConstructionPart> partList = new ArrayList<>();
        for (int i = 0;i < constructionParts.size();i++){
            if (matTypeId != constructionParts.get(i).getMaterialTypeId() ){
                matTypeId = constructionParts.get(i).getMaterialTypeId();
                partList.sort(partComparator);
                listList.add(partList);
                partList = new ArrayList<>();
            }
            partList.add(constructionParts.get(i));
        }
        listList.add(partList);

        CalcLab calcLab = CalcLab.get();
        List<PossibleResults> resultsList = new ArrayList<>();


        for (int i = 0;i < listList.size();i++){
            List<T> materialSelectList = getSelectMaterialList(listList.get(i),calcLab);
            for (int j = 0;j < listList.get(i).size();j++){
                if (listList.get(i).get(j).getEvenNeed()> 0){
                for (int k = 0;k < listList.get(i).size();k++){
                    if (listList.get(i).get(k).getEvenNeed()> 0){
                         resultsList.addAll(getListPossibleREsualt(listList.get(i).get(j),listList.get(i).get(k),materialSelectList));
                    }
                }
                PossibleResults possibleResults = getMinOst(resultsList);
                listTopResult.add(possibleResults);
                for (int k = 0;k < listList.get(i).size();k++){
                    if (possibleResults.part1.getId() == listList.get(i).get(k).getId()){
                        listList.get(i).get(k).setEvenNeed(listList.get(i).get(k).getEvenNeed() - possibleResults.quantBottom * possibleResults.quantityMaterial);
                    }else if (possibleResults.part2.getId() == listList.get(i).get(k).getId()){
                        listList.get(i).get(k).setEvenNeed(listList.get(i).get(k).getEvenNeed() - possibleResults.quantTop * possibleResults.quantityMaterial);
                    }
                }
                resultsList = new ArrayList<>();
                }
            }
        }
    }

    private static<T extends Material> PossibleResults getMinOst(List<PossibleResults> list){
        double min = Double.MAX_VALUE;
        PossibleResults topResult = null;
        for(int i=0; i<list.size(); i++){
            if(list.get(i).ost < min){
                min = list.get(i).ost;
                topResult = list.get(i);
            }
        }
        return topResult;
    }

    private<T extends Material> List<PossibleResults> getListPossibleREsualt(ConstructionPart part1,ConstructionPart part2,List<T> materialSelectList){
        String name;
        if (part1.getName().equals(part2.getName())){
            name = part1.getName();
        }else {
            name = part1.getName() + " " + "+" + " " + part2.getName();
        }
        //TODO:connect to db and get list available materials
        List<PossibleResults>resultsList = new ArrayList<>();
        for (T mt:materialSelectList){
            try {

                if (materialSelectList.get(0).getName().equals("магнит") || materialSelectList.get(0).getName().equals("лента") || materialSelectList.get(0).getName().equals("шнур") || materialSelectList.get(0).getName().equals("люверс")) {
                    resultsList.add(new PossibleResults(part1,mt));

                } else {
                    resultsList.add(new PossibleResults<>(mt,part1,part2, name));
                }
            } catch (AnotherTypeExeption anotherTypeExeption) {
                anotherTypeExeption.printStackTrace();
            }
        }
        return resultsList;
    }

    private <T extends Material> List<T>  getSelectMaterialList(List<ConstructionPart> constructionParts,CalcLab calcLab){
        if (constructionParts.get(0).getMaterialTypeId() <= 0){
            if (constructionParts.get(0).getType().equals("картон")){
                setIdForMaterialList(constructionParts,cartonId);
                return calcLab.getMaterialListByTypeId(cartonId);
            }else if (constructionParts.get(0).getType().equals("бумага")) {
                setIdForMaterialList(constructionParts,paperId);
                return calcLab.getMaterialListByTypeId(paperId);
            } else if (constructionParts.get(0).getType().equals("кашировка")){
                setIdForMaterialList(constructionParts,kashirovkaId);
                return calcLab.getMaterialListByTypeId(kashirovkaId);
            }else {
                return null;
            }
        }else {
            return calcLab.getMaterialListByTypeId(constructionParts.get(0).getMaterialTypeId());
        }

    }
    private void setIdForMaterialList(List<ConstructionPart> constructionParts,int id){
        for (ConstructionPart cp: constructionParts){
            cp.setMaterialTypeId(id);
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public WorkCost getWorkCost() {
        return workCost;
    }

    public Sizes getSizes() {
        return sizes;
    }

    @Override
    public String toString() {
        return name;
    }

}
