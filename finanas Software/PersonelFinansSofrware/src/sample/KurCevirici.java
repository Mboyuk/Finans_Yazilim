package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;


public class KurCevirici extends Application {
    private TableView tableview;
    private ObservableList<ObservableList> data;
    private  ObservableList<PieChart.Data> details=FXCollections.observableArrayList();
    public void buildData() {
        Connection c;
        data = FXCollections.observableArrayList();
        DBConnect dbConnect=new DBConnect();
        try {
            c = dbConnect.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT DISTINCT DovizCinsi,DovizAlis from transactions ";
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
    public void start(Stage primaryStage) {
        GridPane gridPane=new GridPane();
        ArrayList<String> dovizCinsiArray=new ArrayList<>();

        Baglanti baglanti=new Baglanti();
        dovizCinsiArray=baglanti.dovizCinsiCek();
        VBox vBox=new VBox();
        HBox hBox=new HBox();
        ;
        Button button=new Button(  "Çevir");
        Button button1=new Button("Temizle");
        Button button2=new Button("<-- Geri");
        // ChoiceBox cb=new ChoiceBox();
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(dovizCinsiArray);


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
        TextField textField=new TextField();
        textField.setPromptText("Çevirilecek Miktar");
        Label label=new Label("Çevirilecek Tutar");

        tableview = new TableView();


        ArrayList<Double> dovizAlis=new ArrayList<>();
        ArrayList<String> dovizCinsleri=new ArrayList<>();


        button.setOnAction(e->{
            String dovizCinsi=comboBox.getValue().toString();
            double mevcutPara=Double.parseDouble(textField.getText().trim());
            double dovizAlisMiktarı=baglanti.kurCevirici(dovizCinsi);
            if(checkbox1.isSelected()){
                // baglanti.kurCevirici(comboboxSecilenStr);
                double dovizMiktarı2=baglanti.kurCevirici("ABD DOLARI");

                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                dovizCinsleri.add("ABD DOLARI");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);

            }
            if(checkbox2.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("AVUSTRALYA DOLARI");
                dovizCinsleri.add("AVUSTRALYA DOLARI");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox3.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("DANİMARKA KRONU");
                dovizCinsleri.add("DANİMARKA KRONU");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox4.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("EURO");
                dovizCinsleri.add("EURO");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox5.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("İNGİLİZ STERLİNİ");
                dovizCinsleri.add("İNGİLİZ STERLİNİ");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox6.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("İSVİÇRE FRANGI");
                dovizCinsleri.add("İSVİÇRE FRANGI");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox7.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("İSVEÇ KRONU");
                dovizCinsleri.add("İSVEÇ KRONU");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox8.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("KANADA DOLARI");
                dovizCinsleri.add("KANADA DOLARI");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox9.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("KUVEYT DİNARI");
                dovizCinsleri.add("KUVEYT DİNARI");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox10.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("NORVEÇ KRONU");
                dovizCinsleri.add("NORVEÇ KRONU");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox11.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("SUUDİ ARABİSTAN RİYALİ");
                dovizCinsleri.add("SUUDİ ARABİSTAN RİYALİ");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox12.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("JAPON YENİ");
                dovizCinsleri.add("JAPON YENİ");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox13.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("BULGAR LEVASI");
                dovizCinsleri.add("BULGAR LEVASI");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox14.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("RUMEN LEYİ");
                dovizCinsleri.add("RUMEN LEYİ");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox15.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("RUS RUBLESİ");
                dovizCinsleri.add("RUS RUBLESİ");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox16.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("İRAN RİYALİ");
                dovizCinsleri.add("İRAN RİYALİ");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox17.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("ÇİN YUANI");
                dovizCinsleri.add("ÇİN YUANI");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox18.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("PAKİSTAN RUPİSİ");
                dovizCinsleri.add("PAKİSTAN RUPİSİ");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox19.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("KATAR RİYALİ");
                dovizCinsleri.add("KATAR RİYALİ");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            if(checkbox20.isSelected()){
                double dovizMiktarı2=baglanti.kurCevirici("ÖZEL ÇEKME HAKKI (SDR)");
                dovizCinsleri.add("ÖZEL ÇEKME HAKKI (SDR)");
                dovizAlis.add(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
                System.out.println(dovizAlisMiktarı/dovizMiktarı2*mevcutPara);
            }
            System.out.println(dovizCinsleri);
            System.out.println(dovizAlis);
            baglanti.kurCeviriciYukle(dovizCinsleri,dovizAlis,mevcutPara);
            buildData();
        });
        button1.setOnAction(e->{
            baglanti.sil();
        });
        button2.setOnAction(e->{
            baglanti.sil();
            Kullanici kullanici=new Kullanici();
            kullanici.isimal(isim);
            kullanici.start(primaryStage);
        });

        gridPane.getChildren().addAll(checkbox1,checkbox2,checkbox3,checkbox4,checkbox5,checkbox6,
                checkbox7,checkbox8,checkbox9,checkbox10,checkbox11,checkbox12,checkbox13,
                checkbox14,checkbox15,checkbox16,checkbox17,checkbox18,checkbox19,checkbox20,comboBox,textField,button,tableview,button1,button2);


        GridPane.setConstraints(comboBox,0,0);
        //GridPane.setConstraints(label,0,1);
        GridPane.setConstraints(textField,0,1);
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
        GridPane.setConstraints(button,0,4);
        GridPane.setConstraints(tableview,0,6);
        GridPane.setConstraints(button1,1,6);
        GridPane.setConstraints(button2,0,7);
        //GridPane.setConstraints(c,2,11);

        gridPane.setHgap(4); //Sütunlar arasındaki yatay boşlukları ayar
        gridPane.setVgap(5); //Sütunar arasındaki dikey  boşlukları ayarlama
        gridPane.setPadding(new Insets(20)); //GridPane'içevresi ile arasındaki boşluğun belirlenmesi
        gridPane.setAlignment(Pos.TOP_LEFT);

        primaryStage.setTitle("Kur Çevirici");
        Scene scene = new Scene(gridPane, 1680, 900);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
