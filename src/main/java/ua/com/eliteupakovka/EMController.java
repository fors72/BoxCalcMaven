package ua.com.eliteupakovka;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.MaterialType;

import java.net.URL;
import java.util.ResourceBundle;

public class EMController implements Initializable {
    CalcLab calcLab = CalcLab.get();
    Material selectSize;

    ChangeListener<Number> numberChangeListener;

    @FXML
    ComboBox<String> cbMaterialType;

    @FXML
    ComboBox<MaterialType> cbMaterial;

    @FXML
    ListView<Material> lvMaterialSizes;

    @FXML
    CheckBox cgbEnable;

    @FXML
    TextField tfMaterialName,tfThickness,tfWidth,tfLength,tfCost;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] listOfMaterialType = {"картон", "бумага", "другое"};
        TextValidation.validateDouble(tfCost);
        TextValidation.validateDouble(tfWidth);
        TextValidation.validateDouble(tfLength);
        final ObservableList<String> materialType = FXCollections.observableArrayList(listOfMaterialType);
        cbMaterialType.setItems(materialType);
        numberChangeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ObservableList<MaterialType> material = FXCollections.observableArrayList(calcLab.getMaterialTypeList(materialType.get((int)newValue)));
                cbMaterial.setItems(material);
                cbMaterial.getSelectionModel().select(0);
            }
        };
        cbMaterialType.getSelectionModel().selectedIndexProperty().addListener(numberChangeListener);
        cbMaterial.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateUIMaterialSize(cbMaterial.getItems().get((int)newValue).getId());

            }
        });
        cbMaterialType.setValue(materialType.get(0));
        lvMaterialSizes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectSize = lvMaterialSizes.getSelectionModel().getSelectedItem();
                bindView();
            }
        });


    }

    private void updateUIMaterialSize(int id){
        ObservableList<Material> materialSize = FXCollections.observableArrayList(calcLab.getMaterialListByTypeId(id));
        if (!cbMaterial.getValue().getName().equals("пластик") && !cbMaterial.getValue().getName().equals("магнит") && !cbMaterial.getValue().getName().equals("лента") && !cbMaterial.getValue().getName().equals("люверс") && !cbMaterial.getValue().getName().equals("шнур") && !cbMaterial.getValue().getName().equals("тубус") && !cbMaterial.getValue().getName().equals("флок")) {
            materialSize.add(new Material(0,"+ добавить размер",0,0,0));
        }
        lvMaterialSizes.setItems(materialSize);
        lvMaterialSizes.getSelectionModel().select(0);
        selectSize = lvMaterialSizes.getSelectionModel().getSelectedItem();
        bindView();
    }

    private void bindView() {

        tfWidth.setText(String.valueOf(selectSize.getWidth()));
        tfLength.setText(String.valueOf(selectSize.getLength()));
        tfCost.setText(String.valueOf(selectSize.getCost()));
        cgbEnable.setSelected(selectSize.isEnable());


    }

    public void bindSize(){
        selectSize.setIdMaterial(cbMaterial.getValue().getId());
        selectSize.setWidth(Double.valueOf(tfWidth.getText()));
        selectSize.setLength(Double.valueOf(tfLength.getText()));
        selectSize.setCost(Double.valueOf(tfCost.getText()));
        selectSize.setEnable(cgbEnable.isSelected());
        if (selectSize.getId() != 0){
            calcLab.updateMaterialSize(selectSize);
        }else {
            calcLab.insertMaterialSize(selectSize);

        }
        updateUIMaterialSize(cbMaterial.getValue().getId());

    }

    public void deleteMaterialSize(){
        if (!cbMaterial.getValue().getName().equals("пластик") && !cbMaterial.getValue().getName().equals("магнит") && !cbMaterial.getValue().getName().equals("лента") && !cbMaterial.getValue().getName().equals("люверс") && !cbMaterial.getValue().getName().equals("шнур") && !cbMaterial.getValue().getName().equals("тубус") && !cbMaterial.getValue().getName().equals("флок")) {
            calcLab.deleteMaterialSizeById(selectSize.getId());
            updateUIMaterialSize(cbMaterial.getValue().getId());
        }
    }
}
