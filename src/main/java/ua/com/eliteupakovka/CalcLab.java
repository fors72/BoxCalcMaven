package ua.com.eliteupakovka;


import ua.com.eliteupakovka.conctruction.*;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.MaterialType;
import ua.com.eliteupakovka.material.Paper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CalcLab {
    private static CalcLab calcLab;

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
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:BoxCalc.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<MaterialType> getMaterialTypeList(String type){
        List<MaterialType> materialList = new ArrayList<>();
        if (type.equals("кашировка")){
            type = "бумага";
        }else if (type.equals("другое")){
            type = "пластик' OR type='магнит' OR type='лента' OR type='люверс' OR type='шнур";
        }
        try {
            resSet = connect("SELECT * FROM materialType WHERE type='" + type + "'");
            while(resSet.next())
            {
                materialList.add(new MaterialType(resSet.getInt("id"),resSet.getString("name"),resSet.getDouble("thicness")));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materialList;
    }



    public List<Sizes> getSizesList() {
        List<Sizes> sizesList = new ArrayList<>();
        try {
            resSet = connect("SELECT * FROM sizes");
            sizesList.add(new Sizes(0,0,0,0,0));
            while(resSet.next())
            {
              sizesList.add(new Sizes(resSet.getInt("id"),resSet.getDouble("width"),resSet.getDouble("length"),resSet.getDouble("heightBottom"),resSet.getDouble("heightTop")));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sizesList;
    }

    public <T extends Material> List<T> getMaterialListByTypeId(int idType) {
        List<T> materialList = new ArrayList<>();

        try {
            resSet = connect("SELECT * FROM materialSize WHERE idType=" + idType);
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM materialType WHERE id=" + idType);
            String name = resultSet.getString("name");
            while(resSet.next())
            {
                int id = resSet.getInt("id");
                double width = resSet.getDouble("width");
                double length = resSet.getDouble("length");
                double cost = resSet.getDouble("cost");
                materialList.add((T) new Material(id,name,width,length,cost));
            }
            resultSet.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materialList;
    }
    public List<DynamicConstruction> getConstructionList() {
        List<DynamicConstruction> constructionList = new ArrayList<>();
        try {
            resSet = connect("SELECT * FROM construction");
            while(resSet.next())
            {
                constructionList.add(new DynamicConstruction(resSet.getString("name"),resSet.getInt("id"),new WorkCost(resSet.getDouble("workcosts"),resSet.getDouble("workcostd"))));

            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        constructionList.add(new DynamicConstruction("кришка+дно",1));
//        constructionList.add(new DynamicConstruction("книга на магните",2));
//        constructionList.add(new DynamicConstruction("книга на ленте",3));
        return constructionList;
    }
    public List<ConstructionPart> getConstructionPartListByConstructionId(int id,Sizes sizeBox,Parameters parameters,int quantity){
        List<ConstructionPart> constructionParts = new ArrayList<>();
        try {
            ResultSet  resultSizeFor = connection.createStatement().executeQuery("SELECT * FROM sizeFor WHERE idConstruction=" + id + " AND idSize=" + sizeBox.getId()) ;
            List<ConstructionSize> constructionSizes = new ArrayList<>();
            while (resultSizeFor.next()){
                constructionSizes.add(new ConstructionSize(resultSizeFor.getInt("id"),
                        resultSizeFor.getInt("idSize"),
                        resultSizeFor.getInt("idConstruction"),
                        resultSizeFor.getInt("idConstructionPart"),
                        resultSizeFor.getDouble("ws"),
                        resultSizeFor.getDouble("wd")));
            }
            resSet = connect("SELECT * FROM constructionPart WHERE idConstruction=" + id);

            while(resSet.next())
            {
                WorkCost workCost = new WorkCost(resSet.getDouble("ws"),resSet.getDouble("wd"));
                for (ConstructionSize cs: constructionSizes){
                    if (cs.getIdConstructionPart() == resSet.getInt("id")){
                        workCost.setSimple(cs.getWs());
                        workCost.setDesign(cs.getWd());
                    }
                }
                Parameters param = new Parameters(resSet.getDouble("tolerance"),resSet.getDouble("widthMulti"),resSet.getDouble("widthAdd"),resSet.getDouble("lengthMulti"),resSet.getDouble("lengthAdd"),resSet.getDouble("heightBottomMulti"),resSet.getDouble("heightBottomAdd"),resSet.getDouble("heightTopMulti"),resSet.getDouble("heightTopAdd"),resSet.getDouble("heightBottomMultiByWidth"),resSet.getDouble("heightBottomMultiByLength"),resSet.getDouble("heightTopMultiByWidth"),resSet.getDouble("heightTopMultiByLength"));
                constructionParts.add(new ConstructionPart(resSet.getInt("id"),id,resSet.getInt("material"),resSet.getString("name"),resSet.getString("type"),"delete this field",getSizeForPart(sizeBox,param),quantity,1 == resSet.getInt("pressing"),1 == resSet.getInt("laminable"),workCost,null));

            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constructionParts;
    }
    public List<ConstructionPart> getConstructionPartListByConstructionId(int id){
        List<ConstructionPart> constructionParts = new ArrayList<>();
        try {
            resSet = connect("SELECT * FROM constructionPart WHERE idConstruction=" + id);

            while(resSet.next())
            {

                Parameters param = new Parameters(resSet.getDouble("tolerance"),resSet.getDouble("widthMulti"),resSet.getDouble("widthAdd"),resSet.getDouble("lengthMulti"),resSet.getDouble("lengthAdd"),resSet.getDouble("heightBottomMulti"),resSet.getDouble("heightBottomAdd"),resSet.getDouble("heightTopMulti"),resSet.getDouble("heightTopAdd"),resSet.getDouble("heightBottomMultiByWidth"),resSet.getDouble("heightBottomMultiByLength"),resSet.getDouble("heightTopMultiByWidth"),resSet.getDouble("heightTopMultiByLength"));
                constructionParts.add(new ConstructionPart(resSet.getInt("id"),id,resSet.getInt("material"),resSet.getString("name"),resSet.getString("type"),"dt",null,0,1 == resSet.getInt("pressing"),1 == resSet.getInt("laminable"),new WorkCost(resSet.getDouble("ws"),resSet.getDouble("wd")),param));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constructionParts;
    }
    private Sizes getSizeForPart(Sizes sizeBox,Parameters parameters){
        Sizes sizesPart = new Sizes(sizeBox.getWidth() * parameters.getWidthMulti() +
                        sizeBox.getHeightBottom() * parameters.getHeightBottomMulti() * 2 +
                        sizeBox.getHeightBottom() * parameters.getHeightBottomMultiByWidth() +
                        sizeBox.getHeightTop() * parameters.getHeightTopMulti() * 2 +
                        sizeBox.getHeightTop() * parameters.getHeightTopMultiByWidth() +
                        parameters.getWidthAdd() +
                        parameters.getHeightBottomAdd() +
                        parameters.getHeightTopAdd() +
                        parameters.getTolerance(),
                        sizeBox.getLength() * parameters.getLengthMulti() +
                        sizeBox.getHeightBottom() * parameters.getHeightBottomMulti() * 2 +
                        sizeBox.getHeightBottom() * parameters.getHeightBottomMultiByLength() +
                        sizeBox.getHeightTop() * parameters.getHeightTopMulti() * 2 +
                        sizeBox.getHeightTop() * parameters.getHeightTopMultiByLength() +
                        parameters.getLengthAdd() +
                        parameters.getHeightBottomAdd() +
                        parameters.getHeightTopAdd() +
                        parameters.getTolerance());

        return sizesPart;
    }

    public boolean updateConstructionPart(ConstructionPart part){
        boolean status = false;
        Parameters parameters = part.getParameters();
        int a = part.isPressing() ? 1 : 0;
        int b = part.isLaminable() ? 1 : 0;
        try {
            statmt = connection.createStatement();
            status = statmt.execute("UPDATE constructionPart SET " +
                    "name='" + part.getName() +
                    "', type='"+ part.getType() +
                    "', material="+ part.getMaterialTypeId() +
                    ", widthMulti="+ parameters.getWidthMulti() +
                    ", widthAdd="+ parameters.getWidthAdd() +
                    ", lengthMulti="+ parameters.getLengthMulti() +
                    ", lengthAdd="+ parameters.getLengthAdd() +
                    ", heightBottomMulti="+ parameters.getHeightBottomMulti() +
                    ", heightBottomAdd="+ parameters.getHeightBottomAdd() +
                    ", heightTopMulti="+ parameters.getHeightTopMulti() +
                    ", heightTopAdd="+ parameters.getHeightTopAdd() +
                    ", tolerance="+ parameters.getTolerance() +
                    ", heightBottomMultiByWidth="+ parameters.getHeightBottomMultiByWidth() +
                    ", heightBottomMultiByLength="+ parameters.getHeightBottomMultiByLength() +
                    ", heightTopMultiByWidth="+ parameters.getHeightTopMultiByWidth() +
                    ", heightTopMultiByLength="+ parameters.getHeightTopMultiByLength() +
                    ", ws="+ part.getWorkCost().getWorkCost(false) +
                    ", wd="+ part.getWorkCost().getWorkCost(true) +
                    ", pressing="+ a +
                    ", laminable="+ b +
                    " WHERE id=" + part.getId());
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean insertConstructionPart(ConstructionPart part){
        boolean status = false;
        try {
            statmt = connection.createStatement();
            int a = part.isPressing() ? 1 : 0;
            int b = part.isLaminable() ? 1 : 0;
            Parameters parameters = part.getParameters();
            status = statmt.execute("INSERT INTO constructionPart(idConstruction, name, type, material, widthMulti, widthAdd, lengthMulti, lengthAdd, heightBottomMulti, heightBottomAdd, heightTopMulti, heightTopAdd, tolerance, heightBottomMultiByWidth, heightBottomMultiByLength, heightTopMultiByWidth, heightTopMultiByLength, ws, wd, pressing, laminable) " +
                    "VALUES (" + part.getConstrId() +
                    ",'"+ part.getName() +
                    "','"+ part.getType() +
                    "',"+ part.getMaterialTypeId() +
                    ","+ parameters.getWidthMulti() +
                    ","+ parameters.getWidthAdd() +
                    ","+ parameters.getLengthMulti() +
                    ","+ parameters.getLengthAdd() +
                    ","+ parameters.getHeightBottomMulti() +
                    ","+ parameters.getHeightBottomAdd() +
                    ","+ parameters.getHeightTopMulti() +
                    ","+ parameters.getHeightTopAdd() +
                    ","+ parameters.getTolerance() +
                    ","+ parameters.getHeightBottomMultiByWidth() +
                    ","+ parameters.getHeightBottomMultiByLength() +
                    ","+ parameters.getHeightTopMultiByWidth() +
                    ","+ parameters.getHeightTopMultiByLength() +
                    ","+ part.getWorkCost().getWorkCost(false) +
                    ","+ part.getWorkCost().getWorkCost(true) +
                    ","+ a +
                    ","+ b +
                    ")");
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean deletePartById(int id){
        boolean status = false;
        try {
            statmt = connection.createStatement();
            status = statmt.execute("DELETE FROM constructionPart WHERE id =" + id);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    private ResultSet connect(String sql) throws SQLException{
        statmt = connection.createStatement();
        return statmt.executeQuery(sql);
    }
    private void close(){
        try {
            if (resSet != null) {
                resSet.close();
            }
            if (statmt != null) {
                statmt.close();
            }
        } catch (SQLException e) {

        }
    }

    public boolean ifMaterialIsDesign(int typeId) {
        boolean is = false;
        try {
            resSet = connect("SELECT * FROM materialType WHERE id=" + typeId);
            while(resSet.next())
            {
                is = 1 == resSet.getInt("design");
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return is;
    }
//    private void close(ResultSet resSet,Statement statmt,Connection connection){
//        try {
//            resSet.close();
//            if (statmt != null) {
//                statmt.close();
//            }
//            connection.close();
//        } catch (SQLException e) {
//
//        }
//    }
}
