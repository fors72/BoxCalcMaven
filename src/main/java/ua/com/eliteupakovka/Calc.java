package ua.com.eliteupakovka;


import ua.com.eliteupakovka.material.Carton;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.Paper;

import java.util.ArrayList;
import java.util.List;


public class Calc {
    double width, length, heightTop, heightBottom;
    int quantity;
    String name, carton, paper, construction, kashirovka, tistenie, lamination;
    List<Carton> cartonList;
    List<Paper> paperList;
    List<Kashirovka> kashirovkaList;
    List<Carton> cartonSelectList;
    List<Paper> paperSelectList;
    List<Kashirovka> kashirovkaSelectList;

    Construction constructionNew;
    Costs costs;


    public Calc(List<Kashirovka> kashirovkaList, String name, int quantity, String carton, String paper, String construction, String kashirovka, String tistenie, String lamination, double width, double length, double heightTop, double heightBottom, List<Carton> cartonList, List<Paper> paperList,Parameters parameters) {
        this.kashirovkaList = kashirovkaList;
        this.name = name;
        this.quantity = quantity;
        this.carton = carton;
        this.paper = paper;
        this.construction = construction;
        this.kashirovka = kashirovka;
        this.tistenie = tistenie;
        this.lamination = lamination;
        this.width = width;
        this.length = length;
        this.heightTop = heightTop;
        this.heightBottom = heightBottom;
        this.cartonList = cartonList;
        this.paperList = paperList;

        cartonSelectList = getListOfSelectItem(carton,cartonList);
        paperSelectList = getListOfSelectItem(paper, paperList);
        kashirovkaSelectList = getListOfSelectItem(kashirovka,kashirovkaList);
        costs = new Costs(1, 1.8, 0.16, 1, 50, 0.25, 0.1, 200, 0.24);
        constructionNew = new Construction(construction,width, length, heightBottom, heightTop,cartonSelectList,paperSelectList,kashirovkaSelectList,quantity,parameters);
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


