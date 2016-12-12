package ua.com.eliteupakovka.conctruction;


import ua.com.eliteupakovka.*;
import ua.com.eliteupakovka.material.Carton;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.Paper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DinamicConstruction  {
    List<ConstructionPart> parts;
    WorkCost workCost;
    String name;
    int id;
    List<PossibleResults> listTopResult = new ArrayList<>();
    Comparator<ConstructionPart> partComparator = new Comparator<ConstructionPart>() {
        @Override
        public int compare(ConstructionPart o1, ConstructionPart o2) {
            int o2int = (int) o2.getLength()*100 * (int) o2.getWidth()*100;
            int o1int = (int) o1.getLength()*100 * (int) o1.getWidth()*100;
            return o2int - o1int;
        }
    };
    Comparator<ConstructionPart> partComparatorByTypeId = new Comparator<ConstructionPart>() {
        @Override
        public int compare(ConstructionPart o1, ConstructionPart o2) {
            return o1.getMaterialTypeId()-o2.getMaterialTypeId();
        }
    };


    public DinamicConstruction(List<ConstructionPart> parts, WorkCost workCost, String name, int id, Sizes sizes, List<Carton> cartonSelectList, List<Paper> paperSelectList, List<Kashirovka> kashirovkaSelectList, int quantity, Parameters parameters) {
        this.parts = parts;
        this.workCost = workCost;
        this.name = name;
        this.id = id;
        initListTopResult(parts);

    }

    public DinamicConstruction(String name, int id) {
        this.name = name;
        this.id = id;
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
            List<T> materialSelectList = calcLab.getMaterialListByTypeId(listList.get(i).get(0).getMaterialTypeId());
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
                        listList.get(i).get(k).setEvenNeed(listList.get(i).get(k).getEvenNeed() - possibleResults.quantBottom);
                    }else if (possibleResults.part2.getId() == listList.get(i).get(k).getId()){
                        listList.get(i).get(k).setEvenNeed(listList.get(i).get(k).getEvenNeed() - possibleResults.quantTop);
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
            resultsList.add(new PossibleResults<>(mt,part1.getWidth(), part1.getLength(),part2.getWidth(),part2.getLength(), name,part1.getQuantity()));
        }
        return resultsList;
    }

    @Override
    public String toString() {
        return name;
    }
}
