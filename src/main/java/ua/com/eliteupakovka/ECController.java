package ua.com.eliteupakovka;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import ua.com.eliteupakovka.conctruction.*;
import ua.com.eliteupakovka.material.MaterialType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class ECController implements Initializable {
    CalcLab calcLab = CalcLab.get();

    ConstructionPart selectPart;
    ConstructionPartSize constructionPartSize;

    @FXML
    ComboBox<DynamicConstruction> cbConstruction;

    @FXML
    ComboBox<String> cbMaterialType;
    @FXML
    ComboBox<MaterialType> cbMaterial;
    @FXML
    ComboBox<Sizes> cbEnableSize;
    @FXML
    CheckBox chbPressing, chbLaminable;
    @FXML
    TextField tfPartName,tfConstruction,tfWM,tfLM,tfHBM,tfHTM,tfWHBM,tfHLBM,tfHWTM,tfHLTM,tfWA,tfLA,tfHBA,tfHTA,tfTolerance,tfWS,tfWD,tfEWS,tfEWD;

    @FXML
    ListView<ConstructionPart> lvConstructionPart;

    ChangeListener<Number> numberChangeListener;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] listOfMaterialType = {"картон", "бумага", "кашировка", "другое"};


        ObservableList<DynamicConstruction> constructionOS = FXCollections.observableArrayList(calcLab.getConstructionList());
        ObservableList<String> materialType = FXCollections.observableArrayList(listOfMaterialType);
//        constructionOS.add(new DynamicConstruction("Создать конструкцию",0,null));
        cbMaterialType.setItems(materialType);
        cbMaterialType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ObservableList<MaterialType> materialType = FXCollections.observableArrayList();
                if (cbMaterialType.getItems().get((int)newValue).equals("картон")) {
                    materialType.add(new MaterialType(0,"задается при просчете",0));
                } else if (cbMaterialType.getItems().get((int)newValue).equals("бумага")){
                    materialType.add(new MaterialType(-1,"задается при просчете",0));
                } else if (cbMaterialType.getItems().get((int)newValue).equals("кашировка")){
                    materialType.add(new MaterialType(-2,"задается при просчете",0));
                }
                materialType.addAll(calcLab.getMaterialTypeList(cbMaterialType.getItems().get((int)newValue)));
                cbMaterial.setItems(materialType);
                cbMaterial.setValue(cbMaterial.getItems().get(0));

            }
        });
        cbConstruction.setItems(constructionOS);
        numberChangeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateUIConstructionPart(cbConstruction.getItems().get((int)newValue).getId());
            }
        };
        cbConstruction.getSelectionModel().selectedIndexProperty().addListener(numberChangeListener);

        cbConstruction.setValue(constructionOS.get(0));
        lvConstructionPart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectPart = lvConstructionPart.getSelectionModel().getSelectedItem();
                bindView();
            }
        });

        cbEnableSize.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateUIWorccostDependSize();
            }
        });

    }

    private void updateUIWorccostDependSize() {
        if (cbEnableSize.getValue() != null) {
            constructionPartSize = calcLab.getConstructionPartSizeByThisId(lvConstructionPart.getSelectionModel().getSelectedItem().getId(),cbEnableSize.getValue().getId());
            boolean ifIs = constructionPartSize.getId() != 0;
            tfEWS.setText(String.valueOf(ifIs ? constructionPartSize.getWs() : "-"));
            tfEWD.setText(String.valueOf(ifIs ? constructionPartSize.getWd() : "-"));
        }

    }
    public void addWorccostDependSize(){
        if (selectPart.getId() != 0 && cbEnableSize.getValue() != null) {
            calcLab.insertConstructionPartSize(new ConstructionPartSize(0,cbEnableSize.getValue().getId(),selectPart.getConstrId(),selectPart.getId(),Double.valueOf(tfEWS.getText()),Double.valueOf(tfEWD.getText())));
            updateUIWorccostDependSize();
        }
    }
    public void deleteWorccostDependSize(){
        calcLab.deleteConstructionPartSize(constructionPartSize.getId());
        updateUIWorccostDependSize();
    }

    public void updateUIConstructionPart(int id){
        ObservableList<ConstructionPart> constructionParts = FXCollections.observableArrayList(calcLab.getConstructionPartListByConstructionId(id));
        constructionParts.add(new ConstructionPart(0,id,0,"+ добавить деталь","картон","",null,0,false,false,new WorkCost(0,0),new Parameters(0,0,0,0,0,0,0,0,0,0,0,0,0),0));
        lvConstructionPart.setItems(constructionParts);
        lvConstructionPart.getSelectionModel().select(0);
        selectPart = lvConstructionPart.getSelectionModel().getSelectedItem();
        bindView();
        ObservableList<Sizes> sizes = FXCollections.observableArrayList(calcLab.getSizeListByConstructionId(cbConstruction.getValue().getId()));
        cbEnableSize.setItems(sizes);
    }
    private void updateUIConstructionList(){
        cbConstruction.getSelectionModel().selectedIndexProperty().removeListener(numberChangeListener);
        ObservableList<DynamicConstruction> constructionOS = FXCollections.observableArrayList(calcLab.getConstructionList());
        cbConstruction.setItems(constructionOS);
        cbConstruction.getSelectionModel().selectedIndexProperty().addListener(numberChangeListener);
        cbConstruction.setValue(constructionOS.get(constructionOS.size()-1));
    }
    private void bindView(){
        if (selectPart.getId() != 0) {
            tfPartName.setText(selectPart.getName());
        } else {
            tfPartName.setText("");
        }
        chbPressing.setSelected(selectPart.isPressing());
        chbLaminable.setSelected(selectPart.isLaminable());
        tfWS.setText(String.valueOf(selectPart.getWorkCost().getWorkCost(false)));
        tfWD.setText(String.valueOf(selectPart.getWorkCost().getWorkCost(true)));
        tfWM.setText(String.valueOf(selectPart.getParameters().getWidthMulti()));
        tfLM.setText(String.valueOf(selectPart.getParameters().getLengthMulti()));
        tfHBM.setText(String.valueOf(selectPart.getParameters().getHeightBottomMulti()));
        tfHTM.setText(String.valueOf(selectPart.getParameters().getHeightTopMulti()));
        tfWHBM.setText(String.valueOf(selectPart.getParameters().getHeightBottomMultiByWidth()));
        tfHLBM.setText(String.valueOf(selectPart.getParameters().getHeightBottomMultiByLength()));
        tfHWTM.setText(String.valueOf(selectPart.getParameters().getHeightTopMultiByWidth()));
        tfHLTM.setText(String.valueOf(selectPart.getParameters().getHeightTopMultiByLength()));
        tfWA.setText(String.valueOf(selectPart.getParameters().getWidthAdd()));
        tfLA.setText(String.valueOf(selectPart.getParameters().getLengthAdd()));
        tfHBA.setText(String.valueOf(selectPart.getParameters().getHeightBottomAdd()));
        tfHTA.setText(String.valueOf(selectPart.getParameters().getHeightTopAdd()));
        tfTolerance.setText(String.valueOf(selectPart.getParameters().getTolerance()));
        if (selectPart.getType().equals("картон") || selectPart.getType().equals("бумага") || selectPart.getType().equals("кашировка")) {
            cbMaterialType.setValue(selectPart.getType());
        } else {
            cbMaterialType.setValue("другое");
        }
        ObservableList<MaterialType> materialType = cbMaterial.getItems();
        for (MaterialType mt:materialType){
            if (mt.getId() == selectPart.getMaterialTypeId()){
                cbMaterial.setValue(mt);
            }
        }
        updateUIWorccostDependSize();

    }
    public void bindPart(){
        Parameters parameters = new Parameters(Double.valueOf(tfTolerance.getText()),Double.valueOf(tfWM.getText()),
                Double.valueOf(tfWA.getText()),Double.valueOf(tfLM.getText()),Double.valueOf(tfLA.getText()),
                Double.valueOf(tfHBM.getText()), Double.valueOf(tfHBA.getText()),Double.valueOf(tfHTM.getText()),
                Double.valueOf(tfHTA.getText()),Double.valueOf(tfWHBM.getText()), Double.valueOf(tfHLBM.getText()),
                Double.valueOf(tfHWTM.getText()),Double.valueOf(tfHLTM.getText()));
        WorkCost workCost = new WorkCost(Double.valueOf(tfWS.getText()),Double.valueOf(tfWD.getText()));
        selectPart.setName(tfPartName.getText());
        selectPart.setType(cbMaterialType.getValue().equals("другое") ? cbMaterial.getValue().getName() : cbMaterialType.getValue());
        selectPart.setMaterialTypeId(cbMaterial.getValue().getId());
        selectPart.setLaminable(chbLaminable.isSelected());
        selectPart.setPressing(chbPressing.isSelected());
        selectPart.setParameters(parameters);
        selectPart.setWorkCost(workCost);

        if (selectPart.getId() != 0) {
            calcLab.updateConstructionPart(selectPart);
        } else {
            calcLab.insertConstructionPart(selectPart);
        }
        updateUIConstructionPart(selectPart.getConstrId());
    }
    public void deletePart(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!!!");
        alert.setContentText("Вы действительно хотите удалить детать " + selectPart.getName());
        alert.setHeaderText("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            calcLab.deletePartById(selectPart.getId());
            updateUIConstructionPart(selectPart.getConstrId());
        }
    }
    public void createConstruction(){
        calcLab.insertConstruction(tfConstruction.getText());
        updateUIConstructionList();
    }
    public void deleteConstruction(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!!!");
        alert.setContentText("Вы действительно хотите удалить конструкцию " + cbConstruction.getValue().getName());
        alert.setHeaderText("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("!!!");
            alert2.setContentText("Данная конструкция и все детали к ней будут удалены,удалить?");
            alert2.setHeaderText("");
            Optional<ButtonType> result2 = alert.showAndWait();
            if (result2.get() == ButtonType.OK){
                calcLab.deleteConstructionById(cbConstruction.getValue().getId());
                updateUIConstructionList();
            }
        }
    }
}
