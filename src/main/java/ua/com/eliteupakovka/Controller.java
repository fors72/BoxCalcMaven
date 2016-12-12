package ua.com.eliteupakovka;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ua.com.eliteupakovka.conctruction.DinamicConstruction;
import ua.com.eliteupakovka.conctruction.Sizes;
import ua.com.eliteupakovka.material.Carton;
import ua.com.eliteupakovka.material.Material;
import ua.com.eliteupakovka.material.Paper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    ComboBox<String> cbCarton,cbPaper,cbKashirovka,cbTisnenie,cbLamination;
    @FXML
    ComboBox<DinamicConstruction> cbConstruction;
    @FXML
    ComboBox<Sizes> cbBox;
    @FXML
    TextField fQuantity,fName,fW,fL,fH,fHc,sC1,sC2,toleranceOflengthPaperTop,toleranceOfwidthPaperTop,toleranceOflengthPapeBottomr,toleranceOfwidthPaperBottom,toleranceOflengthCartonTop,toleranceOfwidthCartonTop,toleranceOflengthCartonBottom,toleranceOfwidthCartonBottom,toleranceAll;
    @FXML
    ListView<PossibleResults<? extends Material>> resultsListView,cartonListView,paperListView;
    @FXML
    ListView<String> testDb;
    @FXML
    Label cost,prise,cartonCost,paperCost,tapeCost,glueCost,workCost,cuttingCost,pressinCost,rentCost,stretchCost,extraCost,laminationCost;

    @FXML
    CheckBox isToleranceAll;


    private List<Kashirovka> kashirovkaList;
    private List<Carton> cartonList;
    private List<Paper> paperList;
    private List<Sizes> sizesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] listOfSizeBox = {"30x20","32x28","43x30","30x20","32x28","43x30","30x20","32x28","43x30","30x20","32x28","43x30","30x20","32x28","43x30","30x20","32x28","43x30","30x20","32x28","43x30","30x20","32x28","43x30","30x20","32x28","43x30","30x20","32x28","43x30"};
//        String[] listOfConstruction = {"кришка+дно","книга на магните","книга на ленте","шкатулка","гардеробная","круглая","с ложементом","с окошком из пласт.","с ложементом из флок."};
        String[] listOfTisnenie = {"нет","золото", "серебро", "черное", "красное", "другое"};
        String[] listOfLamination = {"нет", "глянцевая", "матовая"};
        CalcLab calcLab = CalcLab.get();
        paperList = calcLab.getPaperList();
        List<String> paper = Calc.getSimplListForView(paperList);

        kashirovkaList = calcLab.getKashirovkaList();
        List<String> kashirovka = Calc.getSimplListForView(kashirovkaList);

        cartonList = calcLab.getCartonList();
        List<String> carton = Calc.getSimplListForView(cartonList);

        sizesList = calcLab.getSizesList();
        ObservableList<DinamicConstruction> constructionOS = FXCollections.observableArrayList(calcLab.getConstructionList());
        ObservableList<Sizes> boxOS = FXCollections.observableArrayList(sizesList);
        ObservableList<String> cartonOS = FXCollections.observableArrayList(carton);
        ObservableList<String> paperOS = FXCollections.observableArrayList(paper);
        ObservableList<String> kashirovkaOS = FXCollections.observableArrayList(kashirovka);
        ObservableList<String> tisnenieOS = FXCollections.observableArrayList(listOfTisnenie);
        ObservableList<String> laminationOS = FXCollections.observableArrayList(listOfLamination);
        cbConstruction.setItems(constructionOS);
        cbConstruction.setValue(constructionOS.get(0));
        cbBox.setItems(boxOS);
        cbBox.setValue(boxOS.get(0));
        cbBox.valueProperty().addListener(new ChangeListener<Sizes>() {
            @Override
            public void changed(ObservableValue<? extends Sizes> observable, Sizes oldValue, Sizes newValue) {
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
        });
        cbCarton.setItems(cartonOS);
        cbCarton.setValue(cartonOS.get(0));
        cbPaper.setItems(paperOS);
        cbPaper.setValue(paperOS.get(0));
        cbKashirovka.setItems(kashirovkaOS);
        cbKashirovka.setValue(kashirovkaOS.get(0));
        cbTisnenie.setItems(tisnenieOS);
        cbTisnenie.setValue(tisnenieOS.get(0));
        cbLamination.setItems(laminationOS);
        cbLamination.setValue(laminationOS.get(0));

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            dobCB = Double.valueOf(fW.getText());
            dobCC = Double.valueOf(fL.getText());
            dobPB = Double.valueOf(fH.getText());
            dobPC = Double.valueOf(fHc.getText());
        } catch (Exception e) {
            alert.setTitle("!!!");
            alert.setContentText("неправельный формат числа");
            alert.setHeaderText("");
            alert.showAndWait();
            return;
        }

        int mknivesCB = (int)(((4 * dobCB + 4 * dobCC + 8 * dobPB) / 100) * 15 * 27);
        int mknivesCC = (int)(((4 * (dobCB + 0.5) + 4 * (dobCC + 0.5) + 8 * dobPC) / 100) * 15 * 27);
        int mknivesPB = (int)(((2 * dobCB + 2 * dobCC + 8 * (dobPB + 1.5)) / 100) * 15 * 27);
        int mknivesPC = (int)(((2 * (dobCB + 0.5) + 2 * (dobCC + 0.5) + 8 * (dobPC + 1.5)) / 100) * 15 * 27);

        alert.setTitle("Штампы");
        alert.setContentText("Кртон дно - " + mknivesCB + " грн" + "\n" +
                "Кртон крышка - " + mknivesCC + " грн" + "\n" +
                "Бумага дно - " + mknivesPB + " грн" + "\n" +
                "Бумага Крышка - " + mknivesPC + " грн" + "\n" +
                "Сумма - " + (mknivesCB + mknivesCC + mknivesPB + mknivesPC) + " грн"  );
        alert.setHeaderText("");
        alert.showAndWait();
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

    public void OnLoadDb(){
//        Connection conn = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection("jdbc:sqlite:construction.sqlite");
//            conn.setAutoCommit(false);
//            System.out.println(conn.isClosed());
//
//            stmt = conn.createStatement();
//            stmt.execute("create table if not exists 'constr' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text);");
//            stmt.execute("INSERT INTO 'constr' ('name') VALUES ('test54'); ");
//            ResultSet rs = stmt.executeQuery("SELECT * FROM constr;");
//            ObservableList<String> listDb = FXCollections.observableArrayList();
//            while ( rs.next() ) {
//                int id = rs.getInt("id");
//                String  name = rs.getString("name");
//                listDb.add(name);
//                System.out.println( "ID = " + id );
//                System.out.println( "NAME = " + name );
//                System.out.println();
//            }
//            testDb.setItems(listDb);
//            conn.close();
//            rs.close();
//        } catch (Exception e) {
//            write("D://exception.txt",System.getProperty("java.class.path"));
//            e.printStackTrace();
//        }

    }
    public void OnDownloadDb(){
//        File file = new File("construction.sqlite");
//        System.out.println(file.getAbsolutePath());
//        Connection connection;
//        Statement statmt;
//        ResultSet resSet;
//        try {
//            Class.forName("org.sqlite.JDBC");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:construction.sqlite");
//            statmt = connection.createStatement();
////            statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");
//            statmt.execute("INSERT INTO 'constr' ('name') VALUES ('test'); ");
//            resSet = statmt.executeQuery("SELECT * FROM constr");
//            while(resSet.next())
//            {
//                int id = resSet.getInt("id");
//                String  name = resSet.getString("name");
//                System.out.println( "ID = " + id );
//                System.out.println( "name = " + name );
//                System.out.println();
//            }
//
//            System.out.println("Таблица создана или уже существует.");
//            if (!connection.isClosed()){
//                System.out.println("connected");
//            }
//            connection.close();
//            if (statmt != null) {
////                statmt.close();
//            }
//            resSet.close();
//            if (connection.isClosed()){
//                System.out.println("disconnected");
//            }
//        } catch (SQLException e) {
//            write("D://exception.txt",System.getProperty("java.class.path"));
//            e.printStackTrace();
//        }
    }


    public void OnCalc() {
        Order order;
        try {
            Parameters parameters;
            if (isToleranceAll.isSelected()) {
                parameters = new Parameters(Double.valueOf(toleranceAll.getText()));
            }else {
                parameters = new Parameters(Double.valueOf(toleranceOfwidthCartonBottom.getText()),Double.valueOf(toleranceOflengthCartonBottom.getText()),Double.valueOf(toleranceOfwidthCartonTop.getText()),Double.valueOf(toleranceOflengthCartonTop.getText()),Double.valueOf(toleranceOfwidthPaperBottom.getText()),Double.valueOf(toleranceOflengthPapeBottomr.getText()),Double.valueOf(toleranceOfwidthPaperTop.getText()),Double.valueOf(toleranceOflengthPaperTop.getText()));
            }
            Calc calc = new Calc(kashirovkaList,fName.getText(),Integer.valueOf(fQuantity.getText()),cbCarton.getValue(),
                    cbPaper.getValue(),cbConstruction.getValue().toString(),cbKashirovka.getValue(),cbTisnenie.getValue(),
                    cbLamination.getValue(),Double.valueOf(fW.getText()),Double.valueOf(fL.getText()),
                    Double.valueOf(fHc.getText()),Double.valueOf(fH.getText()),cartonList,paperList,parameters);
            order = new Order(calc);
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
        ObservableList<PossibleResults<? extends Material>> observableListCarton = FXCollections.observableArrayList(order.listTopResultCarton);
        ObservableList<PossibleResults<? extends Material>> observableListPaper = FXCollections.observableArrayList(order.listTopResultPaper);
        cartonListView.setItems(observableListCarton);
        paperListView.setItems(observableListPaper);
        resultsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    PossibleResults<? extends Material> possibleResults = resultsListView.getSelectionModel().getSelectedItem();
                    cutResultStage(possibleResults);
                }
            }
        });
        cartonListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    cutResultStage(cartonListView.getSelectionModel().getSelectedItem());
                }
            }
        });
        paperListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    cutResultStage(paperListView.getSelectionModel().getSelectedItem());
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
        primaryStage.setTitle(possibleResults.name);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(500);
        primaryStage.setScene(new Scene(root, 600, 500));
        Canvas myCanvas = new Canvas(1200, 1000);
        gc = myCanvas.getGraphicsContext2D();
        drawCut(gc,possibleResults);
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

    private void drawCut(GraphicsContext gc,PossibleResults<? extends Material> possibleResults){
        double startLength = 20;
        double startWidth = 20;
        int zoom = 5;
        gc.strokeRect(startLength, startWidth,possibleResults.material.getLength()*zoom,possibleResults.material.getWidth()*zoom);
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
}
