package ua.com.eliteupakovka;


import ua.com.eliteupakovka.conctruction.*;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.MaterialType;

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
            type = "пластик' OR type='магнит' OR type='лента' OR type='люверс' OR type='шнур' OR type='тубус' OR type='флок";
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
//            sizesList.add(new Sizes(0,0,0,0,0));
            while(resSet.next())
            {
              sizesList.add(new Sizes(resSet.getInt("id"),resSet.getDouble("width"),resSet.getDouble("length"),resSet.getDouble("heightBottom"),resSet.getDouble("heightTop"),resSet.getString("name")));
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
            double thickness = resultSet.getDouble("thicness");
            while(resSet.next())
            {
                int id = resSet.getInt("id");
                double width = resSet.getDouble("width");
                double length = resSet.getDouble("length");
                double cost = resSet.getDouble("cost");
                int enable = resSet.getInt("enable");
                materialList.add((T) new Material(id,name,width,length,cost,enable,thickness));
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
                constructionList.add(new DynamicConstruction(resSet.getString("name"),resSet.getInt("id"),null,new Costs(resSet.getDouble("rent"),resSet.getDouble("glue"),resSet.getDouble("tape"),resSet.getDouble("stretch"),resSet.getDouble("minCutting"),resSet.getDouble("cutCarton"),resSet.getDouble("cutPaper"),resSet.getDouble("fitting"),resSet.getDouble("cutting"))));

            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constructionList;
    }
    public List<ConstructionPart> getConstructionPartListByConstructionId(int id,Sizes sizeBox,Parameters parameters,int quantity){
        List<ConstructionPart> constructionParts = new ArrayList<>();
        try {
            ResultSet  resultSizeFor = connection.createStatement().executeQuery("SELECT * FROM sizeFor WHERE idConstruction=" + id + " AND idSize=" + sizeBox.getId()) ;
            List<ConstructionPartSize> constructionPartSizes = new ArrayList<>();
            while (resultSizeFor.next()){
                constructionPartSizes.add(new ConstructionPartSize(resultSizeFor.getInt("id"),
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
                for (ConstructionPartSize cs: constructionPartSizes){
                    if (cs.getIdConstructionPart() == resSet.getInt("id")){
                        workCost.setSimple(cs.getWs());
                        workCost.setDesign(cs.getWd());
                    }
                }
                Parameters param = new Parameters(resSet.getDouble("tolerance"),resSet.getDouble("widthMulti"),resSet.getDouble("widthAdd"),resSet.getDouble("lengthMulti"),resSet.getDouble("lengthAdd"),resSet.getDouble("heightBottomMulti"),resSet.getDouble("heightBottomAdd"),resSet.getDouble("heightTopMulti"),resSet.getDouble("heightTopAdd"),resSet.getDouble("heightBottomMultiByWidth"),resSet.getDouble("heightBottomMultiByLength"),resSet.getDouble("heightTopMultiByWidth"),resSet.getDouble("heightTopMultiByLength"));
                constructionParts.add(new ConstructionPart(resSet.getInt("id"),id,resSet.getInt("material"),resSet.getString("name"),resSet.getString("type"),"delete this field",getSizeForPart(sizeBox,param),quantity,1 == resSet.getInt("pressing"),1 == resSet.getInt("laminable"),workCost,null,resSet.getInt("groupPart")));

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
                constructionParts.add(new ConstructionPart(resSet.getInt("id"),id,resSet.getInt("material"),resSet.getString("name"),resSet.getString("type"),"dt",null,0,1 == resSet.getInt("pressing"),1 == resSet.getInt("laminable"),new WorkCost(resSet.getDouble("ws"),resSet.getDouble("wd")),param,resSet.getInt("groupPart")));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constructionParts;
    }
    private Sizes getSizeForPart(Sizes sizeBox,Parameters parameters){
        return new Sizes(sizeBox.getWidth() * parameters.getWidthMulti() +
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
            status = statmt.execute("INSERT INTO constructionPart(idConstruction, name, type, material, widthMulti, widthAdd, lengthMulti, lengthAdd, heightBottomMulti, heightBottomAdd, heightTopMulti, heightTopAdd, tolerance, heightBottomMultiByWidth, heightBottomMultiByLength, heightTopMultiByWidth, heightTopMultiByLength, ws, wd, pressing, laminable,groupPart) " +
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
                    ","+ 0 +
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
            statmt = connection.createStatement();
            status = statmt.execute("DELETE FROM sizeFor WHERE idConstructionPart =" + id);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean insertConstruction(String name){
        boolean status = false;
        try {
            statmt = connection.createStatement();
            status = statmt.execute("INSERT INTO construction(name) VALUES ('" + name + "')");
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    public boolean deleteConstructionById(int id){
        boolean statusP = false;
        boolean statusC = false;
        Statement deletePartStatment = null;
        Statement deleteConstructionStatment = null;
        Statement delete = null;
        try {
            deletePartStatment = connection.createStatement();
            deleteConstructionStatment = connection.createStatement();
            delete = connection.createStatement();
            connection.setAutoCommit(false);
            statusP = deletePartStatment.execute("DELETE FROM constructionPart WHERE idConstruction =" + id);
            statusC = deleteConstructionStatment.execute("DELETE FROM construction WHERE id =" + id);
            delete.execute("DELETE FROM constructionSize WHERE idConstruction =" + id);
            delete = connection.createStatement();
            delete.execute("DELETE FROM sizeFor WHERE idConstruction =" + id);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
            if (deleteConstructionStatment != null){
                deleteConstructionStatment.close();
            }
            if (deletePartStatment != null){
                deletePartStatment.close();
            }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statusP && statusC;
    }

    public void insertMaterialSize(Material material){
        int enable = material.isEnable() ? 1 : 0;
        try {
            statmt = connection.createStatement();
            statmt.execute("INSERT INTO materialSize(idType,width,length,cost,enable) VALUES (" + material.getIdMaterial() +
                    ", " + material.getWidth() +
                    ", " + material.getLength() +
                    ", " + material.getCost() +
                    ", " + enable +
                    ")");
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMaterialSize(Material material){
        int enable = material.isEnable() ? 1 : 0;
        try {
            statmt = connection.createStatement();
            statmt.execute("UPDATE materialSize SET idType=" + material.getIdMaterial() +
                    ", width= " + material.getWidth() +
                    ", length=" + material.getLength() +
                    ", cost=" + material.getCost() +
                    ", enable=" + enable +
                    " WHERE id = " + material.getId());
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteMaterialSizeById(int id){
        boolean status = false;
        try {
            statmt = connection.createStatement();
            status = statmt.execute("DELETE FROM materialSize WHERE id =" + id);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    public List<List<DynamicConstruction>> getConstructionListWithSizebySizeId(int id){
        List<DynamicConstruction> dynamicConstructions = this.getConstructionList();
        List<List<DynamicConstruction>> listList = new ArrayList<>();
        try {
            ResultSet  resultSizeFor = connection.createStatement().executeQuery("SELECT * FROM constructionSize WHERE idSize=" + id) ;
            List<DynamicConstruction> constructionThatHaveSize = new ArrayList<>();
            List<DynamicConstruction> constructionThatNotHaveSize = new ArrayList<>();
            while (resultSizeFor.next()){
                int cId = resultSizeFor.getInt("idConstruction");
                for (int i = 0;i<dynamicConstructions.size();i++){
                    if (dynamicConstructions.get(i).getId() == cId){
                        constructionThatHaveSize.add(dynamicConstructions.remove(i));
                        break;
                    }
                }

            }
            constructionThatNotHaveSize.addAll(dynamicConstructions);
            listList.add(constructionThatHaveSize);
            listList.add(constructionThatNotHaveSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listList;

    }

    public void insertSizeForConstruction(int idConstruction,int idSize){

        try {
            statmt = connection.createStatement();
            statmt.execute("INSERT INTO constructionSize(idConstruction,idSize) VALUES (" + idConstruction +
                    ", " + idSize +
                    ")");
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSizeForConstruction(int idConstruction,int idSize){
        try {
            statmt = connection.createStatement();
            statmt.execute("DELETE FROM constructionSize WHERE idConstruction =" + idConstruction + " AND idSize =" +idSize);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void insertSize(Sizes sizes) {
        try {
            statmt = connection.createStatement();
            statmt.execute("INSERT INTO sizes(width,length,heightBottom,heightTop,name) VALUES (" + sizes.getWidth() +
                    ", " + sizes.getLength() +
                    ", " + sizes.getHeightBottom() +
                    ", " + sizes.getHeightTop() +
                    ", '" + sizes.getName() +
                    "')");
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Sizes> getSizeListByConstructionId(int id) {
        List<Sizes> sizes = new ArrayList<>();
        try {
            statmt = connection.createStatement();
            resSet = statmt.executeQuery("SELECT * FROM constructionSize WHERE idConstruction =" + id);
            while (resSet.next()){
                ResultSet res = connection.createStatement().executeQuery("SELECT * FROM sizes WHERE id=" + resSet.getInt("idSize"));
                while (res.next()){
                    sizes.add(new Sizes(res.getInt("id"),
                            res.getDouble("width"),
                            res.getDouble("length"),
                            res.getDouble("heightBottom"),
                            res.getDouble("heightTop"),
                            res.getString("name")));
                }
                res.close();
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sizes;
    }

    public ConstructionPartSize getConstructionPartSizeByThisId(int idPrt,int idSize) {
        ConstructionPartSize constructionPartSize = new ConstructionPartSize(0,0,0,0,0,0);
        try {
            statmt = connection.createStatement();
            resSet = statmt.executeQuery("SELECT * FROM sizeFor WHERE idConstructionPart =" + idPrt + " AND idSize = " + idSize);
            while (resSet.next()){
                constructionPartSize = new ConstructionPartSize(resSet.getInt("id"),idSize,resSet.getInt("idConstruction"),idPrt,
                        resSet.getDouble("ws"),resSet.getDouble("wd"));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return constructionPartSize;

    }

    public void insertConstructionPartSize(ConstructionPartSize constructionPartSize){
        try {
            statmt = connection.createStatement();
            statmt.execute("INSERT INTO sizeFor(idSize,idConstructionPart,ws,wd,idConstruction) VALUES (" + constructionPartSize.getIdSize() +
                    ", " + constructionPartSize.getIdConstructionPart() +
                    ", " + constructionPartSize.getWs() +
                    ", " + constructionPartSize.getWd() +
                    ", " + constructionPartSize.getIdConstruction() +
                    ")");
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteConstructionPartSize(int id){
        try {
            statmt = connection.createStatement();
            statmt.execute("DELETE FROM sizeFor WHERE id =" + id);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSize(Sizes sizes) {

        try {
            statmt = connection.createStatement();
            statmt.execute("UPDATE sizes SET heightBottom=" + sizes.getHeightBottom() +
                    ", width= " + sizes.getWidth() +
                    ", length=" + sizes.getLength() +
                    ", heightTop=" + sizes.getHeightTop() +
                    ", name='" + sizes.getName() +
                    "' WHERE id = " + sizes.getId());
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSize(int id) {
        try {
            statmt = connection.createStatement();
            statmt.execute("DELETE FROM sizeFor WHERE idSize =" + id);
            statmt = connection.createStatement();
            statmt.execute("DELETE FROM sizes WHERE id =" + id);
            statmt = connection.createStatement();
            statmt.execute("DELETE FROM constructionSize WHERE idSize =" + id);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateConstructionCosts(Costs costs,int id) {
        try {
            statmt = connection.createStatement();
            statmt.execute("UPDATE construction SET rent=" + costs.getRent() +
                    ", glue= " + costs.getGlue() +
                    ", tape=" + costs.getTape() +
                    ", stretch=" + costs.getStretch() +
                    ", minCutting=" + costs.getMinCutting() +
                    ", cutCarton=" + costs.getCutCarton() +
                    ", cutPaper=" + costs.getCutPaper() +
                    ", fitting=" + costs.getFitting() +
                    ", cutting=" + costs.getCutting() +
                    " WHERE id = " + id);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
