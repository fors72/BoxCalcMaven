package ua.com.eliteupakovka;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ua.com.eliteupakovka.conctruction.DynamicConstruction;
import ua.com.eliteupakovka.conctruction.Sizes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ESController implements Initializable {
    CalcLab calcLab = CalcLab.get();

    @FXML
    ListView<Sizes> lvSize;

    @FXML
    ComboBox<DynamicConstruction> ivConstructionWithoutSize;

    @FXML
    ListView<DynamicConstruction> ivConstructionWithSize;

    @FXML
    TextField tfName,tfWidth,tfLength,tfHeightBottom,tfHeightTop;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Sizes> sizes = FXCollections.observableArrayList(calcLab.getSizesList());
        sizes.add(new Sizes(0,0,0,0,0));
        lvSize.setItems(sizes);
        lvSize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bindSizeInfo();

            }
        });
        lvSize.getSelectionModel().select(0);
    }

    private void bindSizeInfo(){
        List<List<DynamicConstruction>> listList = calcLab.getConstructionListWithSizebySizeId(lvSize.getItems().get(lvSize.getSelectionModel().getSelectedIndex()).getId());
        ObservableList<DynamicConstruction> dynamicConstructionsThatHave = FXCollections.observableArrayList(listList.get(0));
        ObservableList<DynamicConstruction> dynamicConstructionsThatNotHave = FXCollections.observableArrayList(listList.get(1));
        ivConstructionWithSize.setItems(dynamicConstructionsThatHave);
        ivConstructionWithoutSize.setItems(dynamicConstructionsThatNotHave);
        if (dynamicConstructionsThatNotHave.size() != 0) {
            ivConstructionWithoutSize.setValue(dynamicConstructionsThatNotHave.get(0));
        }
        Sizes sizes = lvSize.getSelectionModel().getSelectedItem();
        tfName.setText("name");
        tfWidth.setText(String.valueOf(sizes.getWidth()));
        tfLength.setText(String.valueOf(sizes.getLength()));
        tfHeightBottom.setText(String.valueOf(sizes.getHeightBottom()));
        tfHeightTop.setText(String.valueOf(sizes.getHeightTop()));



    }

    public void addSizeForConstruction(){
        calcLab.insertSizeForConstruction(ivConstructionWithoutSize.getValue().getId(),lvSize.getItems().get(lvSize.getSelectionModel().getSelectedIndex()).getId());
        bindSizeInfo();
    }
    public void deleteSizeForConstruction(){
        calcLab.deleteSizeForConstruction(ivConstructionWithSize.getItems().get(ivConstructionWithSize.getSelectionModel().getSelectedIndex()).getId(),lvSize.getItems().get(lvSize.getSelectionModel().getSelectedIndex()).getId());
        bindSizeInfo();
    }

    public void addSize(){
        if (lvSize.getSelectionModel().getSelectedItem().getId() == 0){
            calcLab.insertSize(new Sizes(0,
                    Double.valueOf(tfWidth.getText()),
                    Double.valueOf(tfLength.getText()),
                    Double.valueOf(tfHeightBottom.getText()),
                    Double.valueOf(tfHeightTop.getText()),
                    tfName.getText()
                    ));
            ObservableList<Sizes> sizes = FXCollections.observableArrayList(calcLab.getSizesList());
            sizes.add(new Sizes(0,0,0,0,0));
            lvSize.setItems(sizes);
        }
    }
}
