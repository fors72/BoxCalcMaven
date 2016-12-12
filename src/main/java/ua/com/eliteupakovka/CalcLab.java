package ua.com.eliteupakovka;


import ua.com.eliteupakovka.conctruction.DinamicConstruction;
import ua.com.eliteupakovka.conctruction.Sizes;
import ua.com.eliteupakovka.material.Carton;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.Paper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CalcLab {
    private static CalcLab calcLab;
    private List<Carton> cartonList;
    private List<Paper> paperList;
    private List<Kashirovka> kashirovkaList;
    private List<Sizes> sizesList;

    private Connection connection;
    private Statement statmt;
    private ResultSet resSet;

    public static CalcLab get(){
        if (calcLab == null){
            calcLab = new CalcLab();
        }
        return calcLab;
    }

    private CalcLab() {
        paperList = new ArrayList<>();
        Paper paper = new Paper("меловка", 64 , 90 , 3, false);
        Paper paper1 = new Paper("меловка", 70 , 100 , 3.5, false);
        Paper paper2 = new Paper("печать на меловке", 45 , 64 , 3.5, false);
        Paper paper3 = new Paper("печать на меловке", 50 , 70 , 4, false);
        Paper paper4 = new Paper("печать на меловке", 63 , 90 , 7, false);
        Paper paper5 = new Paper("печать на меловке", 69 , 100 , 7.5, false);
        Paper paper6 = new Paper("imitlin", 72 , 102 , 22, true);
        Paper paper7 = new Paper("stardream", 70 , 100 , 32, true);
        Paper paper8 = new Paper("stardream", 72 , 102 , 32, true);
        Paper paper9 = new Paper("malmero", 70 , 100 , 16, true);
        paperList.add(paper);
        paperList.add(paper1);
        paperList.add(paper2);
        paperList.add(paper3);
        paperList.add(paper4);
        paperList.add(paper5);
        paperList.add(paper6);
        paperList.add(paper7);
        paperList.add(paper8);
        paperList.add(paper9);

        kashirovkaList = new ArrayList<>();
        Kashirovka kashirovkaNon = new Kashirovka("нет",0,0,0);
        Kashirovka kashirovka1 = new Kashirovka("офсет",64,90,2.5);
        Kashirovka kashirovka11 = new Kashirovka("офсет",70,100,3);
        Kashirovka kashirovka2 = new Kashirovka("печать на офсете",64,90,7);
        Kashirovka kashirovka22 = new Kashirovka("печать на офсете",70,100,7.5);
        kashirovkaList.add(kashirovka1);
        kashirovkaList.add(kashirovka11);
        kashirovkaList.add(kashirovka2);
        kashirovkaList.add(kashirovka22);

        kashirovkaList.add(kashirovkaNon);
        for (Paper p:paperList){
            if (p.getKash() != null){
                kashirovkaList.add(p.getKash());
            }
        }

        cartonList = new ArrayList<>();
        Carton carton = new Carton("стандартный", 80, 100, 12, 1.5);
        Carton carton1 = new Carton("стандартный", 90, 100, 13.5, 1.5);
        Carton carton2 = new Carton("стандартный", 70, 100, 20, 2);
        Carton carton3 = new Carton("голандский", 79, 100, 23, 1.5);
        Carton carton4 = new Carton("голандский", 70, 100, 33, 2);
        Carton carton5 = new Carton("голандский", 79 ,100, 35, 2);
        Carton carton6 = new Carton("голандский", 70, 100, 13.5, 1);
        Carton carton7 = new Carton("голандский", 70, 100, 30, 3);
        cartonList.add(carton);
        cartonList.add(carton1);
        cartonList.add(carton2);
        cartonList.add(carton3);
        cartonList.add(carton4);
        cartonList.add(carton5);
        cartonList.add(carton6);
        cartonList.add(carton7);

        sizesList = new ArrayList<>();
        sizesList.add(new Sizes(0,0,0,0));
        sizesList.add(new Sizes(30,20,7,3));
        sizesList.add(new Sizes(25,25,8,3));



    }

    public List<Paper> getPaperList() {
        return paperList;
    }

    public List<Kashirovka> getKashirovkaList() {
        return kashirovkaList;
    }

    public List<Carton> getCartonList() {
        return cartonList;
    }

    public List<Sizes> getSizesList() {
        return sizesList;
    }

    public <T extends Material> List<T> getMaterialListByTypeId(int idType) {
        List<T> materialList = new ArrayList<>();

        try {
            resSet = connect("SELECT * FROM materialSize WHERE id=" + idType);
            while(resSet.next())
            {
                int id = resSet.getInt("id");
                double width = resSet.getDouble("width");
                double length = resSet.getDouble("length");
                double cost = resSet.getDouble("cost");
                materialList.add((T) new Material(String.valueOf(idType),width,length,cost));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materialList;
    }
    public List<DinamicConstruction> getConstructionList() {
        List<DinamicConstruction> constructionList = new ArrayList<>();
        try {
            resSet = connect("SELECT * FROM construction");
            while(resSet.next())
            {
                constructionList.add(new DinamicConstruction(resSet.getString("name"),resSet.getInt("id")));

            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constructionList;
    }

    private ResultSet connect(String sql) throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection("jdbc:sqlite:BoxCalc.sqlite");
        statmt = connection.createStatement();
        return statmt.executeQuery(sql);
    }
    private void close(){
        try {
            resSet.close();
            if (statmt != null) {
                statmt.close();
            }
            connection.close();
        } catch (SQLException e) {

        }
    }
}
