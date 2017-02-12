package ua.com.eliteupakovka;


import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ua.com.eliteupakovka.conctruction.DynamicConstruction;
import ua.com.eliteupakovka.conctruction.Sizes;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.MaterialType;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    private String name;
    private CalcLab calcLab = CalcLab.get();
    @FXML
    ComboBox<String> cbLamination;
    @FXML
    ComboBox<MaterialType> cbCarton,cbPaper,cbKashirovka;
    @FXML
    ComboBox<DynamicConstruction> cbConstruction;
    @FXML
    ComboBox<Sizes> cbBox;
    @FXML
    TextField fQuantity,fName,fW,fL,fH,fHc,sC1,sC2,toleranceOflengthPaperTop,toleranceOfwidthPaperTop,toleranceOflengthPapeBottomr,toleranceOfwidthPaperBottom,toleranceOflengthCartonTop,toleranceOfwidthCartonTop,toleranceOflengthCartonBottom,toleranceOfwidthCartonBottom,toleranceAll;
    @FXML
    ListView<PossibleResults> cartonListView;
    @FXML
    ListView<String> testDb;
    @FXML
    Label cost,prise,cartonCost,paperCost,tapeCost,glueCost,workCost,cuttingCost,pressinCost,rentCost,stretchCost,extraCost,laminationCost,prisePer1,costPer1,k1,k2,k3,k4,k5;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] listOfLamination = {"нет", "глянцевая", "матовая"};



        ObservableList<DynamicConstruction> constructionOS = FXCollections.observableArrayList(calcLab.getConstructionList());

        ObservableList<MaterialType> cartonOS = FXCollections.observableArrayList(calcLab.getMaterialTypeList("картон"));
        ObservableList<MaterialType> paperOS = FXCollections.observableArrayList(calcLab.getMaterialTypeList("бумага"));
        ObservableList<MaterialType> kashirovkaOS = FXCollections.observableArrayList(calcLab.getMaterialTypeList("бумага"));
        kashirovkaOS.add(new MaterialType(-10,"нет",0));
        ObservableList<String> laminationOS = FXCollections.observableArrayList(listOfLamination);

        cbConstruction.setItems(constructionOS);
        cbConstruction.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ObservableList<Sizes> boxOS = FXCollections.observableArrayList();
                boxOS.add(new Sizes(0,0,0,0,0));
                boxOS.addAll(calcLab.getSizeListByConstructionId(cbConstruction.getItems().get((int)newValue).getId()));
                cbBox.setItems(boxOS);
                if (boxOS.get(0) != null) {
                    cbBox.setValue(boxOS.get(0));
                }
            }
        });
        cbConstruction.setValue(constructionOS.get(0));

        cbBox.valueProperty().addListener(new ChangeListener<Sizes>() {
            @Override
            public void changed(ObservableValue<? extends Sizes> observable, Sizes oldValue, Sizes newValue) {
                if (newValue != null) {
                    if (newValue.toString().equals("Другой")){
                        fW.setText("");
                        fL.setText("");
                        fH.setText("");
                        fHc.setText("");
                        fW.setDisable(false);
                        fL.setDisable(false);
                        fH.setDisable(false);
                        fHc.setDisable(false);
                    }else {
                        fW.setText(newValue.getWidth() + "");
                        fL.setText(newValue.getLength() + "");
                        fH.setText(newValue.getHeightBottom() + "");
                        fHc.setText(newValue.getHeightTop() + "");
                        fW.setDisable(true);
                        fL.setDisable(true);
                        fH.setDisable(true);
                        fHc.setDisable(true);
                    }
                }
            }
        });
        cbCarton.setItems(cartonOS);
        cbCarton.setValue(cartonOS.get(0));
        cbPaper.setItems(paperOS);
        cbPaper.setValue(paperOS.get(0));
        cbKashirovka.setItems(kashirovkaOS);
        cbKashirovka.setValue(kashirovkaOS.get(0));
        cbLamination.setItems(laminationOS);
        cbLamination.setValue(laminationOS.get(0));

        ChangeListener<String> calcKnivesListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                CalcPressKnife();
            }
        };
        fW.textProperty().addListener(calcKnivesListener);
        fL.textProperty().addListener(calcKnivesListener);
        fH.textProperty().addListener(calcKnivesListener);
        fHc.textProperty().addListener(calcKnivesListener);
    }

    public static void write(String fileName, String text) {
        //Определяем файл
        File file = new File(fileName);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void CalcPressKnife(){
        double dobCB;
        double dobCC;
        double dobPB;
        double dobPC;
        try {
            dobCB = Double.valueOf(fW.getText());
            dobCC = Double.valueOf(fL.getText());
            dobPB = Double.valueOf(fH.getText());
            dobPC = Double.valueOf(fHc.getText());
        } catch (Exception e) {
            k1.setText("");
            k2.setText("");
            k3.setText("");
            k4.setText("");
            k5.setText("");
            return;
        }

        int mknivesCB = (int)(((4 * dobCB + 4 * dobCC + 8 * dobPB) / 100) * 15 * 27);
        int mknivesCC = (int)(((4 * (dobCB + 0.5) + 4 * (dobCC + 0.5) + 8 * dobPC) / 100) * 15 * 27);
        int mknivesPB = (int)(((2 * dobCB + 2 * dobCC + 8 * (dobPB + 1.5)) / 100) * 15 * 27);
        int mknivesPC = (int)(((2 * (dobCB + 0.5) + 2 * (dobCC + 0.5) + 8 * (dobPC + 1.5)) / 100) * 15 * 27);

        k1.setText("Картон дно - " + mknivesCB + " грн");
        k2.setText("Картон крышка - " + mknivesCC + " грн");
        k3.setText("Бумага дно - " + mknivesPB + " грн");
        k4.setText("Бумага Крышка - " + mknivesPC + " грн");
        k5.setText("Сумма - " + (mknivesCB + mknivesCC + mknivesPB + mknivesPC) + " грн");

    }
    public void CalcCliche(){
        double dobSC1;
        double dobSC2;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            dobSC1 = Double.valueOf(sC1.getText());
            dobSC2 = Double.valueOf(sC2.getText());
        } catch (Exception e) {
            alert.setTitle("!!!!");
            alert.setContentText("неправельный формат числа");
            alert.setHeaderText("");
            alert.showAndWait();
            return;
        }
        double klisheCost = (int)(dobSC1 + 2) * (dobSC2 + 2) * 5.76;
        alert.setTitle("Клише");
        alert.setContentText(klisheCost + " грн");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public void OnCalc() {
        Order order;
        try {
            //TODO: check w l hb ht
            Sizes sizes;
            if (cbBox.getValue().getId() == 0) {
                sizes = new Sizes(0,Double.valueOf(fW.getText()),Double.valueOf(fL.getText()), Double.valueOf(fH.getText()),Double.valueOf(fHc.getText()));
            } else {
                sizes = cbBox.getValue();
            }
            Calc calc = new Calc(Integer.valueOf(fQuantity.getText()),cbConstruction.getValue(),
                    cbLamination.getValue(),sizes, cbCarton.getValue().getId(),cbPaper.getValue().getId(),cbKashirovka.getValue().getId(),null);
            order = new Order(calc);
            name = cbConstruction.getValue().getName() + " " + sizes.getWidth() + "x" + sizes.getLength() + "x" + sizes.getHeightBottom() + "x" + sizes.getHeightTop();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("!!!");
            alert.setContentText("Выбраны не все параметы или формат параметра не правильный");
            alert.setHeaderText("");
            alert.showAndWait();
            e.printStackTrace();
            return;
        }



        prise.setText("Цена - " + String.valueOf((int) order.prise));
        cost.setText("Стоимость - " + String.valueOf((int) order.cost));
        prisePer1.setText("Цена за шт. " + String.valueOf((int)order.prise/order.quantity));
        costPer1.setText("Стоимость за шт. " + String.valueOf((int)order.cost/order.quantity));
        cartonCost.setText("Картон " + order.cartonCost);
        paperCost.setText("Бумага " + order.paperCost);
        tapeCost.setText("Скотч " + order.tapeCost);
        glueCost.setText("Клей " + order.glueCost);
        workCost.setText("Работа " + order.workCost);
        cuttingCost.setText("Порезка " + order.cuttingCost);
        pressinCost.setText("Высечка " + order.pressinCost);
        rentCost.setText("Оренда " + order.rentCost);
        stretchCost.setText("Стрейч"  + order.stretchCost);
        extraCost.setText("Доп. " + order.extraCost);
        laminationCost.setText("Ламинация" + order.laminationCost);
        ObservableList<PossibleResults> observableListCarton = FXCollections.observableArrayList(order.listTopResult);
        cartonListView.setItems(observableListCarton);
        cartonListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    cutResultStage(cartonListView.getSelectionModel().getSelectedItem());
                }
            }
        });
    }
    private void cutResultStage(PossibleResults<? extends Material> possibleResults) {
        GraphicsContext gc;
        Stage primaryStage = new Stage();
        FlowPane root = null;
        try {
            root = (FlowPane)new FXMLLoader().load(getClass().getResourceAsStream("/fxml/cut_result.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle(possibleResults.name + " " + possibleResults.material.toString() + " " + possibleResults.quantityMaterial + " шт. ");
        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(450);
        primaryStage.setScene(new Scene(root, 1000, 600));
        Canvas myCanvas = new Canvas(1000, 600);
        gc = myCanvas.getGraphicsContext2D();
        drawCut(gc,possibleResults,4);
        root.getChildren().add(myCanvas);
        primaryStage.show();
    }
    @FXML
    public void buttonPressed(KeyEvent e)
    {
        if(e.getCode() == KeyCode.ENTER)
        {
            OnCalc();
        }
    }

    public void OnAdd(){
        Stage primaryStage = new Stage();
        AnchorPane root = null;
        try {
            root = (AnchorPane) new FXMLLoader().load(getClass().getResourceAsStream("/fxml/open_editor.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Добавить");
        primaryStage.setMinWidth(290);
        primaryStage.setMinHeight(300);
        primaryStage.setScene(new Scene(root, 290, 270));
        primaryStage.show();
    }


    private void drawCut(GraphicsContext gc,PossibleResults<? extends Material> possibleResults,int zoom){
        javafx.scene.text.Font font = new javafx.scene.text.Font(20);
        gc.setFont(font);
        double startLength = 40;
        double startWidth = 40;
        gc.strokeRect(startLength, startWidth,possibleResults.material.getLength()*zoom,possibleResults.material.getWidth()*zoom);
        if (possibleResults.needTurnBottom) {
            gc.strokeText(String.valueOf(possibleResults.part1.getSizes().getLength()),startLength + possibleResults.material.getLength()*zoom + 20 + (possibleResults.part1.getSizes().getLength()*zoom)/2 - 10 ,startWidth - 5 );
            gc.strokeText(String.valueOf(possibleResults.part1.getSizes().getWidth()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part1.getSizes().getLength()*zoom + 5 ,startWidth + (possibleResults.part1.getSizes().getWidth() * zoom / 2) );
            gc.strokeRect(startLength + possibleResults.material.getLength()*zoom + 20,startWidth, possibleResults.part1.getSizes().getLength()*zoom, possibleResults.part1.getSizes().getWidth()*zoom );
        }else {
            gc.strokeText(String.valueOf(possibleResults.part1.getSizes().getWidth()),startLength + possibleResults.material.getLength()*zoom + 20 + (possibleResults.part1.getSizes().getWidth()*zoom)/2 - 10 ,startWidth - 5 );
            gc.strokeText(String.valueOf(possibleResults.part1.getSizes().getLength()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part1.getSizes().getWidth()*zoom + 5 ,startWidth + (possibleResults.part1.getSizes().getLength() * zoom / 2) );
            gc.strokeRect(startLength + possibleResults.material.getLength()*zoom + 20,startWidth, possibleResults.part1.getSizes().getWidth()*zoom, possibleResults.part1.getSizes().getLength()*zoom );

        }
        if (!possibleResults.part1.equals(possibleResults.part2)) {
            if (possibleResults.belowBottom) {
                if (!possibleResults.needTurnTop) {
                    gc.strokeText(String.valueOf(possibleResults.part2.getSizes().getLength()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part2.getSizes().getLength()*zoom /2 - 10,startWidth + possibleResults.part1.getSizes().getWidth()*zoom +20 );
                    gc.strokeText(String.valueOf(possibleResults.part2.getSizes().getWidth()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part2.getSizes().getLength()*zoom,startWidth + possibleResults.part1.getSizes().getWidth()*zoom +20 + possibleResults.part2.getSizes().getWidth()*zoom /2);
                    gc.strokeRect(startLength + possibleResults.material.getLength()*zoom + 20, startWidth + possibleResults.part1.getSizes().getWidth()*zoom +20, possibleResults.part2.getSizes().getLength()*zoom, possibleResults.part2.getSizes().getWidth()*zoom );
                } else {
                    gc.strokeText(String.valueOf(possibleResults.part2.getSizes().getWidth()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part2.getSizes().getWidth()*zoom /2 - 10,startWidth + possibleResults.part1.getSizes().getLength()*zoom +25 );
                    gc.strokeText(String.valueOf(possibleResults.part2.getSizes().getLength()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part2.getSizes().getWidth()*zoom,startWidth + possibleResults.part1.getSizes().getLength()*zoom +20 + possibleResults.part2.getSizes().getLength()*zoom /2);
                    gc.strokeRect(startLength + possibleResults.material.getLength()*zoom + 20, startWidth + possibleResults.part1.getSizes().getLength()*zoom +30, possibleResults.part2.getSizes().getWidth()*zoom, possibleResults.part2.getSizes().getLength()*zoom );

                }
            }else {
                if (possibleResults.needTurnTop) {
                    gc.strokeText(String.valueOf(possibleResults.part2.getSizes().getLength()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part2.getSizes().getLength()*zoom /2 - 10,startWidth + possibleResults.part1.getSizes().getWidth()*zoom +20 );
                    gc.strokeText(String.valueOf(possibleResults.part2.getSizes().getWidth()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part2.getSizes().getLength()*zoom,startWidth + possibleResults.part1.getSizes().getWidth()*zoom +20 + possibleResults.part2.getSizes().getWidth()*zoom /2);
                    gc.strokeRect(startLength + possibleResults.material.getLength()*zoom + 20, startWidth + possibleResults.part1.getSizes().getWidth()*zoom +20, possibleResults.part2.getSizes().getLength()*zoom, possibleResults.part2.getSizes().getWidth()*zoom );
                } else {
                    gc.strokeText(String.valueOf(possibleResults.part2.getSizes().getWidth()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part2.getSizes().getWidth()*zoom /2 - 10,startWidth + possibleResults.part1.getSizes().getLength()*zoom +25 );
                    gc.strokeText(String.valueOf(possibleResults.part2.getSizes().getLength()),startLength + possibleResults.material.getLength()*zoom + 20 + possibleResults.part2.getSizes().getWidth()*zoom,startWidth + possibleResults.part1.getSizes().getLength()*zoom +20 + possibleResults.part2.getSizes().getLength()*zoom /2);
                    gc.strokeRect(startLength + possibleResults.material.getLength()*zoom + 20, startWidth + possibleResults.part1.getSizes().getLength()*zoom +30, possibleResults.part2.getSizes().getWidth()*zoom, possibleResults.part2.getSizes().getLength()*zoom );

                }
            }
        }
        if (possibleResults.part1.isParentPart()){
            if (possibleResults.part1.getIdGroup() < 0){
                String text = possibleResults.part1.getSizes().getLength() + " (" + possibleResults.part1.getOwnSize().getLength();
                for (int i = 0; i < possibleResults.part1.getChildConstructionParts().size();i++){
                    text +=", " + possibleResults.part1.getChildConstructionParts().get(i).getSizes().getLength();
                }
                text += ")";
                gc.strokeText(text,startWidth,startWidth + possibleResults.material.getWidth()*zoom + 30 );
            }else if (possibleResults.part1.getIdGroup() > 0){
                String text = possibleResults.part1.getSizes().getWidth() + " (" + possibleResults.part1.getOwnSize().getWidth();
                for (int i = 0; i < possibleResults.part1.getChildConstructionParts().size();i++){
                    text +=", " + possibleResults.part1.getChildConstructionParts().get(i).getSizes().getWidth();
                }
                text += ")";
                gc.strokeText(text,startWidth,startWidth + possibleResults.material.getWidth()*zoom + 30 );
            }
        }
        if (!possibleResults.part1.equals(possibleResults.part2)) {
            if (possibleResults.part2.isParentPart()){
                if (possibleResults.part2.getIdGroup() < 0){
                    String text = possibleResults.part2.getSizes().getLength() + " (" + possibleResults.part2.getOwnSize().getLength();
                    for (int i = 0; i < possibleResults.part2.getChildConstructionParts().size();i++){
                        text +=", " + possibleResults.part2.getChildConstructionParts().get(i).getSizes().getLength();
                    }
                    text += ")";
                    gc.strokeText(text,startWidth,startWidth + possibleResults.material.getWidth()*zoom + 70 );
                }else if (possibleResults.part2.getIdGroup() > 0){
                    String text = possibleResults.part2.getSizes().getWidth() + " (" + possibleResults.part2.getOwnSize().getWidth();
                    for (int i = 0; i < possibleResults.part2.getChildConstructionParts().size();i++){
                        text +=", " + possibleResults.part2.getChildConstructionParts().get(i).getSizes().getWidth();
                    }
                    text += ")";
                    gc.strokeText(text,startWidth,startWidth + possibleResults.material.getWidth()*zoom + 70 );
                }
            }
        }
        if (possibleResults.needTurnBottom){
            for (int i = 0;i <possibleResults.qbw;i++){
                for (int j = 0;j < possibleResults.qbl;j++){
                    gc.strokeRect(startLength + j * possibleResults.lengthBottom * zoom, startWidth + i * possibleResults.widthBottom * zoom,possibleResults.lengthBottom*zoom,possibleResults.widthBottom*zoom);

                }
            }
            if (!possibleResults.belowBottom) {
                for (int i = 0;i<possibleResults.k2;i++) {
                    for (int j = 0; j < possibleResults.k1; j++) {
                        if (possibleResults.needTurnTop) {
                            gc.strokeRect(startLength + j * possibleResults.lengthTop * zoom + possibleResults.lengthBottom * possibleResults.qbl * zoom, startWidth + i * possibleResults.widthTop * zoom, possibleResults.lengthTop * zoom, possibleResults.widthTop * zoom);
                        } else {
                            gc.strokeRect(startLength + j * possibleResults.widthTop * zoom + possibleResults.lengthBottom * possibleResults.qbl * zoom, startWidth + i * possibleResults.lengthTop * zoom, possibleResults.widthTop * zoom, possibleResults.lengthTop * zoom);
                        }
                    }
                }
            }else {
                for (int i = 0;i<possibleResults.k1;i++){
                    for (int j = 0;j<possibleResults.k2;j++){
                        if (!possibleResults.needTurnTop){
                            gc.strokeRect(startLength + j * possibleResults.lengthTop * zoom, startWidth + i * possibleResults.widthTop * zoom + possibleResults.widthBottom * possibleResults.qbw * zoom, possibleResults.lengthTop * zoom, possibleResults.widthTop * zoom);
                        }else {
                            gc.strokeRect(startLength + j * possibleResults.widthTop * zoom, startWidth + i * possibleResults.lengthTop * zoom + possibleResults.widthBottom * possibleResults.qbw * zoom, possibleResults.widthTop * zoom, possibleResults.lengthTop * zoom);
                        }
                    }
                }
            }
        }else {
            for (int i = 0;i <possibleResults.qbl;i++){
                for (int j = 0;j < possibleResults.qbw;j++){
                    gc.strokeRect(startLength + j * possibleResults.widthBottom* zoom, startWidth + i * possibleResults.lengthBottom * zoom,possibleResults.widthBottom*zoom,possibleResults.lengthBottom*zoom);

                }
            }
            if (!possibleResults.belowBottom) {
                for (int i = 0;i<possibleResults.k2;i++){
                    for (int j = 0;j<possibleResults.k1;j++) {
                        if (!possibleResults.needTurnTop) {
                            gc.strokeRect(startLength + j * possibleResults.widthTop * zoom + possibleResults.widthBottom * possibleResults.qbw * zoom, startWidth + i * possibleResults.lengthTop * zoom, possibleResults.widthTop* zoom, possibleResults.lengthTop * zoom);
                        } else {
                            gc.strokeRect(startLength + j * possibleResults.lengthTop * zoom + possibleResults.widthBottom * possibleResults.qbw * zoom, startWidth + i * possibleResults.widthTop * zoom, possibleResults.lengthTop * zoom, possibleResults.widthTop * zoom);
                        }
                    }
                }

            }else {
                for (int i = 0;i<possibleResults.k1;i++){
                    for (int j = 0;j<possibleResults.k2;j++) {
                        if (possibleResults.needTurnTop){
                            gc.strokeRect(startLength + j * possibleResults.widthTop * zoom, startWidth + i * possibleResults.lengthTop * zoom + possibleResults.lengthBottom * possibleResults.qbl * zoom, possibleResults.widthTop * zoom, possibleResults.lengthTop * zoom);
                        }else {
                            gc.strokeRect(startLength + j * possibleResults.lengthTop * zoom, startWidth + i * possibleResults.widthTop * zoom + possibleResults.lengthBottom * possibleResults.qbl * zoom, possibleResults.lengthTop * zoom, possibleResults.widthTop * zoom);
                        }
                    }
                }
            }
        }
    }




    public void createPDF(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить");
        fileChooser.setInitialFileName(name);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF file(*.pdf)","*.pdf"));
        try {
            Document document = new Document();
            document.setMargins(0,0,0,0);
            PdfWriter.getInstance(document, new FileOutputStream(fileChooser.showSaveDialog(null)));
            BaseFont baseFont = BaseFont.createFont("c:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 20, Font.NORMAL);
            document.open();
            Paragraph title = new Paragraph(name,font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            fillPDF(document,baseFont);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void fillPDF(Document document,BaseFont baseFont) throws DocumentException,IOException{
        ObservableList<PossibleResults> resultsList = cartonListView.getItems();
        GraphicsContext gc;
        Canvas myCanvas;
        WritableImage writableImage;
        RenderedImage renderedImage;
        ByteArrayOutputStream byteOutput;
        Font font = new Font(baseFont, 7, Font.NORMAL);
        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.setTotalWidth(PageSize.A4.getWidth());
        pdfPTable.setLockedWidth(true);
        for (int i = 0;i < resultsList.size();i++){
            if (resultsList.get(i).part2 == null){
                continue;
            }
            myCanvas = new Canvas(1100, 600);
            gc = myCanvas.getGraphicsContext2D();
            drawCut(gc,resultsList.get(i),4);
            writableImage = new WritableImage(900, 600);
            myCanvas.snapshot(null,writableImage);
            renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            byteOutput = new ByteArrayOutputStream();

            try {
                ImageIO.write( renderedImage, "png", byteOutput );
                Image image = Image.getInstance(byteOutput.toByteArray());
                image.setAlignment(Element.ALIGN_LEFT);
                image.scalePercent(30);
                PdfPCell  pdfPCell = new PdfPCell();
                pdfPCell.setBorder(Rectangle.NO_BORDER);
                pdfPCell.addElement(new Paragraph(resultsList.get(i).toString(),font));
                pdfPCell.addElement(image);
                pdfPTable.addCell(pdfPCell);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PdfPCell  pdfPCell = new PdfPCell();
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);
        document.add(pdfPTable);
    }
}
