package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LineChartDoviz extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private TableView tableview;
    private ObservableList<ObservableList> data;
    private  ObservableList<PieChart.Data> details= FXCollections.observableArrayList();
    public void buildData() {
        Connection c;
        data = FXCollections.observableArrayList();
        DBConnect dbConnect=new DBConnect();
        try {
            c = dbConnect.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT DISTINCT DovizCinsi,DovizAlis,Tarih from deneme3 ";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);
                // System.out.println("Column [" + i + "] ");
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    private String isim;
    public void isimVer(String isim){
        this.isim=isim;
    }
    @Override
    public void start(Stage stage) {
        BorderPane borderPane=new BorderPane();
        GridPane gridPane=new GridPane();
        GridPane gridPane1=new GridPane();
        GridPane gridPane2=new GridPane();
        Baglanti baglanti=new Baglanti();
        Button button=new Button("getir");
        Button button2=new Button("<-- Kullanıcı Sayfası");
        DatePicker datePicker=new DatePicker();
        DatePicker datePicker1=new DatePicker();





        CheckBox checkbox1=new CheckBox("ABD Doları");
        CheckBox checkbox2=new CheckBox("Avustalya Doları");
        CheckBox checkbox3=new CheckBox("Danimarka Kronu");
        CheckBox checkbox4=new CheckBox("EURO");
        CheckBox checkbox5=new CheckBox("İngiliz Sterlini");
        CheckBox checkbox6=new CheckBox("İsviçre Frangı");
        CheckBox checkbox7=new CheckBox("İsveç Kronu");
        CheckBox checkbox8=new CheckBox("Kanada Doları");
        CheckBox checkbox9=new CheckBox("Kuveyt Dinarı");
        CheckBox checkbox10=new CheckBox("Norveç Kronu");
        CheckBox checkbox11=new CheckBox("SA Riyali");
        CheckBox checkbox12=new CheckBox("Japon Yeni");
        CheckBox checkbox13=new CheckBox("Bulgar Levası");
        CheckBox checkbox14=new CheckBox("Rumen Leyi");
        CheckBox checkbox15=new CheckBox("Rus Lublesi");
        CheckBox checkbox16=new CheckBox("İran Riyali");
        CheckBox checkbox17=new CheckBox("Çin Yuanı");
        CheckBox checkbox18=new CheckBox("Pakistan Rupisi");
        CheckBox checkbox19=new CheckBox("Katar Riyali");
        CheckBox checkbox20=new CheckBox("ÖÇH (SDR)");
        //baglanti.lineChartDoldur("ABD DOLARI");
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis =new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Days");
        final LineChart<String,Number> lineChart =
                new LineChart<String, Number>(xAxis,yAxis);
        lineChart.setTitle("Line Chart 2019");

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series6 = new XYChart.Series();
        XYChart.Series series7 = new XYChart.Series();
        XYChart.Series series8 = new XYChart.Series();
        XYChart.Series series9 = new XYChart.Series();
        XYChart.Series series10 = new XYChart.Series();
        XYChart.Series series11= new XYChart.Series();
        XYChart.Series series12= new XYChart.Series();
        XYChart.Series series13= new XYChart.Series();
        XYChart.Series series14= new XYChart.Series();
        XYChart.Series series15= new XYChart.Series();
        XYChart.Series series16= new XYChart.Series();
        XYChart.Series series17= new XYChart.Series();
        XYChart.Series series18= new XYChart.Series();
        XYChart.Series series19= new XYChart.Series();
        XYChart.Series series20 = new XYChart.Series();


        //lineChart.setStyle("-fx-stroke-width: 85px;");
        //lineChart.setStyle("-fx-arc-width: 15px;");
        //lineChart.setStyle("-fx-background-size: 885px;");
        //lineChart.setStyle("-fx-background-color: red;");
        //lineChart.setStyle("-fx-cell-size: 115px;");
        // lineChart.setStyle("-fx-legend-side: 500px;");

        //lineChart.setStyle("-fx-min-height: 1900px;");


        lineChart.setStyle("-fx-min-width: 1300px;");
        //lineChart.setStyle("-fx-padding: 90px");
        //  lineChart.setStyle("-fx-start-margin: 200px");
        // lineChart.setStyle("-fx-tick-length: 70;\n" +
        //          "    -fx-minor-tick-length: 60;");
        xAxis.setTickLabelFill(Color.CHOCOLATE);

        //  yAxis.setTickUnit(200.0);
        //yAxis.setTickLength(0.1);
        // yAxis.setMinorTickLength(100);
        //yAxis.setMinorTickCount(100);
        // yAxis.setTickLabelGap(6);
        yAxis.setTickLength(30.0);




        series1.setName("ABD Doları");
        series2.setName("Avustalya Doları");
        series3.setName("Danimarka Kronu");
        series4.setName("EURO");
        series5.setName("İngiliz Sterlini");
        series6.setName("İsviçre Frangı");
        series7.setName("İsveç Kronu");
        series8.setName("Kanada Doları");
        series9.setName("Kuveyt Dinarı");
        series10.setName("Norveç Kronu");
        series11.setName("SA Riyali");
        series12.setName("Japon Yeni");
        series13.setName("Bulgar Levası");
        series14.setName("Rumen Leyi");
        series15.setName("Rus Lublesi");
        series16.setName("İran Riyali");
        series17.setName("Çin Yuanı");
        series18.setName("Pakistan Rupisi");
        series19.setName("Katar Riyali");
        series20.setName("ÖÇH (SDR)");

        tableview = new TableView();
        /*lineChart.setMinHeight(450.0);
        lineChart.setMinWidth(500.0);
        lineChart.resize(500,500);
        lineChart.setMaxSize(500,300);*/




        button.setOnAction(e->{
            LocalDate value1=datePicker.getValue();
            LocalDate value2=datePicker1.getValue();
            System.out.println(value1);
            System.out.println(value2);
            ArrayList<Double> dovizAlis=new ArrayList<>();

            ArrayList<Date> date=new ArrayList<>();
            // date=baglanti.lineChartDoldur3("ABD DOLARI");
            if(checkbox1.isSelected()){
                date=baglanti.lineChartDoldur3("ABD DOLARI",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("ABD DOLARI",value1,value2);
                System.out.println("asda"+dovizAlis);
                System.out.println("kk"+date);

                System.out.println("sss"+date.get(0).toString());
                for(int i=0;i<dovizAlis.size();i++){

                    series1.getData().add(new XYChart.Data((date.get(i).toString()), dovizAlis.get(i)));

                }
            }
            if(checkbox2.isSelected()){
                date=baglanti.lineChartDoldur3("AVUSTRALYA DOLARI",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("AVUSTRALYA DOLARI",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series2.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));
                }
            }
            if(checkbox3.isSelected()){
                date=baglanti.lineChartDoldur3("DANİMARKA KRONU",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("DANİMARKA KRONU",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series3.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox4.isSelected()){
                date=baglanti.lineChartDoldur3("EURO",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("EURO",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series4.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox5.isSelected()){
                date=baglanti.lineChartDoldur3("İNGİLİZ STERLİNİ",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("İNGİLİZ STERLİNİ",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series5.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox6.isSelected()){
                date=baglanti.lineChartDoldur3("İSVİÇRE FRANGI",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("İSVİÇRE FRANGI",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series6.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox7.isSelected()){
                date=baglanti.lineChartDoldur3("İSVEÇ KRONU",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("İSVEÇ KRONU",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series7.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox8.isSelected()){
                date=baglanti.lineChartDoldur3("KANADA DOLARI",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("KANADA DOLARI",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series8.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox9.isSelected()){
                date=baglanti.lineChartDoldur3("KUVEYT DİNARI",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("KUVEYT DİNARI",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series9.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox10.isSelected()){
                date=baglanti.lineChartDoldur3("NORVEÇ KRONU",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("NORVEÇ KRONU",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series10.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox11.isSelected()){
                date=baglanti.lineChartDoldur3("SUUDİ ARABİSTAN RİYALİ",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("SUUDİ ARABİSTAN RİYALİ",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series11.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox12.isSelected()){
                date=baglanti.lineChartDoldur3("JAPON YENİ",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("JAPON YENİ",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series12.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox13.isSelected()){
                date=baglanti.lineChartDoldur3("BULGAR LEVASI",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("BULGAR LEVASI",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series13.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox14.isSelected()){
                date=baglanti.lineChartDoldur3("RUMEN LEYİ",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("RUMEN LEYİ",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series14.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox15.isSelected()){
                date=baglanti.lineChartDoldur3("RUS RUBLESİ",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("RUS RUBLESİ",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series15.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox16.isSelected()){
                date=baglanti.lineChartDoldur3("İRAN RİYALİ",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("İRAN RİYALİ",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series16.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox17.isSelected()){
                date=baglanti.lineChartDoldur3("ÇİN YUANI",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("ÇİN YUANI",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series17.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox18.isSelected()){
                date=baglanti.lineChartDoldur3("PAKİSTAN RUPİSİ",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("PAKİSTAN RUPİSİ",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series18.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox19.isSelected()){
                date=baglanti.lineChartDoldur3("KATAR RİYALİ",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("KATAR RİYALİ",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series19.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }
            if(checkbox20.isSelected()){
                date=baglanti.lineChartDoldur3("ÖZEL ÇEKME HAKKI (SDR)",value1,value2);
                dovizAlis=baglanti.lineChartDoldur("ÖZEL ÇEKME HAKKI (SDR)",value1,value2);

                for(int i=0;i<dovizAlis.size();i++){
                    series20.getData().add(new XYChart.Data(date.get(i).toString(), dovizAlis.get(i)));

                }
            }


            buildData();
            baglanti.sil3();

        });
        button2.setOnAction(e->{
            Kullanici kullanici=new Kullanici();

            kullanici.isimal(isim);
            kullanici.start(stage);
        });
        gridPane.getChildren().addAll(checkbox1,checkbox2,checkbox3,checkbox4,checkbox5,checkbox6,
                checkbox7,checkbox8,checkbox9,checkbox10,checkbox11,checkbox12,checkbox13,
                checkbox14,checkbox15,checkbox16,checkbox17,checkbox18,checkbox19,checkbox20
                ,button);

        GridPane.setConstraints(checkbox1,1,0);
        GridPane.setConstraints(checkbox2,2,0);
        GridPane.setConstraints(checkbox3,3,0);
        GridPane.setConstraints(checkbox4,4,0);
        GridPane.setConstraints(checkbox5,5,0);
        GridPane.setConstraints(checkbox6,1,1);
        GridPane.setConstraints(checkbox7,2,1);
        GridPane.setConstraints(checkbox8,3,1);
        GridPane.setConstraints(checkbox9,4,1);
        GridPane.setConstraints(checkbox10,5,1);
        GridPane.setConstraints(checkbox11,1,2);
        GridPane.setConstraints(checkbox12,2,2);
        GridPane.setConstraints(checkbox13,3,2);
        GridPane.setConstraints(checkbox14,4,2);
        GridPane.setConstraints(checkbox15,5,2);
        GridPane.setConstraints(checkbox16,1,3);
        GridPane.setConstraints(checkbox17,2,3);
        GridPane.setConstraints(checkbox18,3,3);
        GridPane.setConstraints(checkbox19,4,3);
        GridPane.setConstraints(checkbox20,5,3);
        GridPane.setConstraints(button,3,4);
        // GridPane.setConstraints(lineChart,9,5);
        //  GridPane.setConstraints(button2,1,6);
        // GridPane.setConstraints(tableview,9,6);


        gridPane1.getChildren().addAll(lineChart,datePicker,datePicker1);
        GridPane.setConstraints(lineChart,0,0);
        GridPane.setConstraints(datePicker,1,0);
        GridPane.setConstraints(datePicker1,2,0);
        //GridPane.setConstraints(tableview,1,0);

        gridPane2.getChildren().addAll(button2,tableview);
        //GridPane.setConstraints(tableview,0,0);
        GridPane.setConstraints(button2,0,2);
        GridPane.setConstraints(tableview,0,1);
        borderPane.setTop(gridPane);
        borderPane.setCenter(gridPane1);
        borderPane.setBottom(gridPane2);

        // series1.getData().add(new XYChart.Data("Jan", 23));
        // series1.getData().add(new XYChart.Data("Feb", 14));
        // series1.getData().add(new XYChart.Data("Mar", 15));
        lineChart.getData().addAll(series1,series2,series3,series4,series5,series6,series7,series8,series9,series10,series11,series12,series13,series14,series15,series16,series17,series18,series19,series20);

        Scene scene  = new Scene(borderPane,1680,900);

        stage.setScene(scene);
        stage.show();

    }


}
