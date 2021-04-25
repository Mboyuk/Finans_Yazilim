package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.prefs.Preferences;

public class Kullanici extends Application {
    private ObservableList<ObservableList> data;
    private  ObservableList<PieChart.Data> details=FXCollections.observableArrayList();

    private TableView tableview;
    private final ObservableList<PieChart.Data> a= FXCollections.observableArrayList();
    private BorderPane root;
    private PieChart pieChart;
    private  String isim2;

    public void isimal(String isim){
        isim2=isim;
    }
    public void buildData() {
        Connection c;
        data = FXCollections.observableArrayList();
        DBConnect dbConnect=new DBConnect();
        try {
            c = dbConnect.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from deposit WHERE KullaniciAdi='"+isim2+"'";
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
                //System.out.println();
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        // System.out.print();
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                // System.out.println();

                tableview.getColumns().addAll(col);
                // System.out.println("Column [" + i + "] ");
            }

            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
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
    public void veriAl(double dovizAlis,String dovizCinsi){
        ArrayList<Double> arrayList=new ArrayList<>();
        ArrayList<String> arrayList1=new ArrayList<>();
        arrayList1.add(dovizCinsi);
        arrayList.add(dovizAlis);

    }
    @Override
    public void start(Stage primaryStage) {


        BorderPane borderPane=new BorderPane();
        GridPane gridPane=new GridPane();
        GridPane gridPane1=new GridPane();
        GridPane gridPane2=new GridPane();
        Baglanti baglanti=new Baglanti();
        Button button5=new Button("getir");
        Button button6=new Button("Line Chart");
        Button kurceviriciButton=new Button("Kur Çevirici Sayfası -->");
        Button button7=new Button("Yapilan islemler -->");
        Button button8=new Button("Pie charts getir");

        ArrayList<Double> aaa=new ArrayList<>();
        ArrayList<String> bbb=new ArrayList<>();
        bbb=baglanti.pieChartVeriler(isim2);
        aaa=baglanti.pieChartDoldur(isim2);
        // aaa=baglanti.pieChartveriler2(bbb.get(1,))

        System.out.println(bbb);
        System.out.println(aaa);
        for(int i=0;i<bbb.size();i++){
            //double k=aaa.get(i);
            details.addAll(new PieChart.Data(bbb.get(i),aaa.get(i))

            );
        }

        pieChart=new PieChart();
        pieChart.setData(details);
        pieChart.setTitle("Pie Charts");
        //pieChart.minHeight(300.0);
        //pieChart.minWidth(300.0);
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);
        //gridPane.getChildren().add(pieChart);
        //pieChart.setMaxSize(800,800);

        bbb.clear();
        aaa.clear();
        pieChart.setStyle("-fx-min-width: 800px;");
        tableview = new TableView();



        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });




        ArrayList<String> dovizCinsiArray=new ArrayList<>();
        dovizCinsiArray=baglanti.dovizCinsiCek();
        ArrayList<Double> arrayList=new ArrayList<>();
        arrayList=baglanti.bilgileriGetir(isim2);
        // table.setItems(arrayList);



        //  System.out.println(arrayList);



        Button button=new Button("Ekle");
        Button butoon2=new Button("Çıkar");
        Button button3=new Button("Dönüştür");
        // Button button4=new Button("getir");
        Label baslikLabel,paraEkLabel,eklenecekTutarLabel,altLimitLabel,ustLimitLabel,paraCikarmaLabel,dovizCinsiLAbel,cikarilacakTutarLabel,
                donusturBaslikLabel,donusturulecekLabel,donusenLabel,donusecekTutarLabel,donusturulenSonuc,altLimitLabel2,ustLimitLabel2;
        TextField eklenecekTutarText,altLimitText,ustLimitText,cikarilacakTutarText,donusturulenMiktarText,altLimitText2,ustLimitText2;
        eklenecekTutarText=new TextField();
        altLimitText=new TextField();
        ustLimitText=new TextField();
        cikarilacakTutarText=new TextField();
        donusturulenMiktarText=new TextField();
        altLimitText2=new TextField();
        ustLimitText2=new TextField();

        paraEkLabel=new Label("Döviz Cinsi");
        baslikLabel=new Label("PARA EKLEME");
        eklenecekTutarLabel=new Label("Eklenecek Tutar");
        altLimitLabel=new Label("Alt Limit");
        ustLimitLabel=new Label("Ust Limit");
        paraCikarmaLabel=new Label("PARA ÇIKARMA");
        dovizCinsiLAbel=new Label("Doviz Cinsi");
        cikarilacakTutarLabel=new Label("Çıkarılacak tutar");
        donusturBaslikLabel=new Label("PARA DÖNÜŞTÜRME");
        donusturulecekLabel=new Label("'nı");
        donusenLabel=new Label("'a dönüştür");
        donusecekTutarLabel=new Label("                    Donüştürülecek Miktar");
        donusturulenSonuc=new Label();
        altLimitLabel2=new Label("                                     Alt Limit");
        ustLimitLabel2=new Label("                                     Ust Limit");

        paraCikarmaLabel.setFont(Font.font("Calibri", FontWeight.BLACK,20));
        paraEkLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        cikarilacakTutarLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        dovizCinsiLAbel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        baslikLabel.setFont(Font.font("Calibri", FontWeight.BLACK,20));
        eklenecekTutarLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        paraEkLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,14));
        altLimitLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,14));
        ustLimitLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        altLimitText2.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        ustLimitText2.setFont(Font.font("Calibri", FontWeight.NORMAL,14));

        donusturBaslikLabel.setFont(Font.font("Calibri", FontWeight.BLACK,20));
        donusturulecekLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        donusenLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        donusecekTutarLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        donusturulenSonuc.setFont(Font.font("Calibri", FontWeight.LIGHT,14));
        altLimitLabel2.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        ustLimitLabel2.setFont(Font.font("Calibri", FontWeight.NORMAL,15));

        eklenecekTutarText.setPromptText("Eklenecek Tutar Giriniz");
        altLimitText.setPromptText("Alt limit giriniz");
        ustLimitText.setPromptText("Üst Limit giriniz");
        cikarilacakTutarText.setPromptText("Çıkarılacak Tutar Giriniz");
        donusturulenMiktarText.setPromptText("Donüstürülecek Tutar");
        altLimitText2.setPromptText("Alt Limit");
        ustLimitText2.setPromptText("Ust Limit");


        button.setFont(Font.font("Calibri", FontWeight.BOLD,15));
        button.setMinWidth(100);
        butoon2.setFont(Font.font("Calibri", FontWeight.BOLD,15));
        butoon2.setMinWidth(100);
        button3.setFont(Font.font("Calibri", FontWeight.BOLD,15));
        button3.setMinWidth(100);
        //System.out.println(dovizCinsiArray+"ssex");
        ComboBox comboBox = new ComboBox();
        ComboBox comboBox2 = new ComboBox();
        ComboBox comboBox3 = new ComboBox();
        ComboBox comboBox4 = new ComboBox();
        comboBox.getItems().addAll(dovizCinsiArray);
        //comboBox.setEditable(true);
        comboBox2.getItems().addAll(dovizCinsiArray);
        comboBox3.getItems().addAll(dovizCinsiArray);
        comboBox4.getItems().addAll(dovizCinsiArray);

        //Button cikis=new Button("ÇIKIŞ");
        Label bilgi=new Label();



        gridPane.setHgap(4); //Sütunlar arasındaki yatay boşlukları ayar
        gridPane.setVgap(5); //Sütunar arasındaki dikey  boşlukları ayarlama
        gridPane.setPadding(new Insets(20)); //GridPane'içevresi ile arasındaki boşluğun belirlenmesi
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.getChildren().addAll(comboBox,button,baslikLabel,paraEkLabel,eklenecekTutarLabel,altLimitLabel,ustLimitLabel,eklenecekTutarText,
                altLimitText,ustLimitText,paraCikarmaLabel,dovizCinsiLAbel,cikarilacakTutarLabel,butoon2,comboBox2,cikarilacakTutarText,
                donusturBaslikLabel,donusturulecekLabel,donusenLabel,donusecekTutarLabel,donusturulenMiktarText,button3,comboBox3,
                comboBox4,donusturulenSonuc,altLimitLabel2,ustLimitLabel2,altLimitText2,ustLimitText2,bilgi);
        GridPane.setConstraints(baslikLabel,2,0);
        GridPane.setConstraints(paraEkLabel,1,1);
        GridPane.setConstraints(comboBox,2,1);
        GridPane.setConstraints(eklenecekTutarLabel,1,2);
        GridPane.setConstraints(eklenecekTutarText,2,2);
        GridPane.setConstraints(altLimitLabel,1,3);
        GridPane.setConstraints(altLimitText,2,3);
        GridPane.setConstraints(ustLimitLabel,1,4);
        GridPane.setConstraints(ustLimitText,2,4);
        GridPane.setConstraints(button,2,5);

        GridPane.setConstraints(paraCikarmaLabel,4,0);
        GridPane.setConstraints(dovizCinsiLAbel,3,1);
        GridPane.setConstraints(cikarilacakTutarLabel,3,2);
        GridPane.setConstraints(cikarilacakTutarText,4,2);
        GridPane.setConstraints(butoon2,4,3);
        GridPane.setConstraints(comboBox2,4,1);

        GridPane.setConstraints(donusturBaslikLabel,6,0);
        GridPane.setConstraints(donusturulecekLabel,6,1);
        GridPane.setConstraints(donusenLabel,8,1);
        GridPane.setConstraints(donusecekTutarLabel,5,2);
        GridPane.setConstraints(donusturulenMiktarText,6,2);
        GridPane.setConstraints(comboBox3,5,1);
        GridPane.setConstraints(comboBox4,7,1);
        GridPane.setConstraints(button3,7,5);
        GridPane.setConstraints(donusturulenSonuc,8,3);
        GridPane.setConstraints(altLimitLabel2,5,3);
        GridPane.setConstraints(ustLimitLabel2,5,4);
        GridPane.setConstraints(altLimitText2,6,3);
        GridPane.setConstraints(ustLimitText2,6,4);
        //GridPane.setConstraints(tableview,2,10);
        // GridPane.setConstraints(pieChart,8,10);
        //GridPane.setConstraints(button5,2,11);
        //GridPane.setConstraints(kurceviriciButton,9,15);
        //GridPane.setConstraints(button6,9,16);
        //GridPane.setConstraints(button7,8,15);
        //GridPane.setConstraints(cikis,8,16);
        GridPane.setConstraints(bilgi,8,17);
        //GridPane.setConstraints(button8,8,18);
        //  GridPane.setConstraints(table,5,8);
        gridPane1.getChildren().addAll(tableview,pieChart,button5);
        GridPane.setConstraints(tableview,2,0);
        GridPane.setConstraints(pieChart,0,0);
        GridPane.setConstraints(button5,3,0);

        gridPane2.getChildren().addAll(kurceviriciButton,button6,button7);
        GridPane.setConstraints(kurceviriciButton,0,0);
        GridPane.setConstraints(button6,1,0);
        GridPane.setConstraints(button7,2,0);
        borderPane.setCenter(gridPane1);
        borderPane.setTop(gridPane);
        borderPane.setBottom(gridPane2);
/*
         //Button tanımlanması
        cikis.setMinHeight(50); //Button un min yükseklik-genişlik ayarının yapılması
        cikis.setMinWidth(70);

         //Label tanımlanması

        cikis.setOnAction(e->{  //Ekle buttonuna tıklandığında çalışacak event

            Alert alert=new Alert(Alert.AlertType.CONFIRMATION); //Alert tanımlanması ve alert türünün seçilmesi

            alert.setTitle("Confirmation Diologs"); //Alert e başlık verilmesi
            alert.setHeight(100); //Alert in genişlik ve yüksekliğinin belirlenmesi
            alert.setWidth(100);

            alert.setHeaderText("Bu bir onaylama diyaloğudur."); //Başlık yazısının ayaralanması
            alert.setContentText(cikis.getText() + " butonuna tıkladınız.Çıkmak istediğinden emin misin?"); //İçerik yazısına button ismi alınması ve yazının ayarlanması

            Optional<ButtonType> result = alert.showAndWait(); //Alert diyaloğunun gösterrilmesi

            if (result.get() == ButtonType.OK){
                Platform.exit();
                System.exit(0);
            } else {
                bilgi.setText("Cansel butonuna tıklandı.");
            }
        });*/


        button.setOnAction(e->{
            ArrayList<Double> arrayList1=new ArrayList<>();
            String comboboxSecilenStr=comboBox.getValue().toString();
            double eklenecekTutar=Double.parseDouble(eklenecekTutarText.getText().trim());
            double altLimit=Double.parseDouble(altLimitText.getText().trim());
            double ustLimit=Double.parseDouble(ustLimitText.getText().trim());
            arrayList1= baglanti.altUstLimitBul(isim2,comboboxSecilenStr);
            //comboBox.setValue();
            //System.out.println(comboBox.getS);
            System.out.println(arrayList1);
            if(arrayList1.isEmpty())
            {
                baglanti.depositEkle(isim2.trim(),comboboxSecilenStr,eklenecekTutar,altLimit,ustLimit);
                baglanti.yapilanIslemler(isim2,comboboxSecilenStr,eklenecekTutar,altLimit,ustLimit,null,"Para Ekleme");
            }
            else if(eklenecekTutar+arrayList1.get(2)>arrayList1.get(1)){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION); //Alert tanımlanması ve alert türünün seçilmesi

                alert.setTitle("Confirmation Diologs"); //Alert e başlık verilmesi
                alert.setHeight(100); //Alert in genişlik ve yüksekliğinin belirlenmesi
                alert.setWidth(100);

                alert.setHeaderText("Belirlenen Üst Limiti Aşıldı. ONAYLIYOR MUSUNUZ?"); //Başlık yazısının ayaralanması
                alert.setContentText(button.getText() + " butonuna tıkladınız.Eklemek istediğinizden emin misin?"); //İçerik yazısına button ismi alınması ve yazının ayarlanması

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){

                    //System.out.println(isim2);
                    //System.out.println(comboboxSecilenStr+" "+eklenecekTutar);
                    baglanti.depositEkle(isim2.trim(),comboboxSecilenStr,eklenecekTutar,altLimit,ustLimit);
                    baglanti.yapilanIslemler(isim2,comboboxSecilenStr,eklenecekTutar,altLimit,ustLimit,null,"Para Ekleme");


                } else {
                    bilgi.setText("Cansel butonuna tıklandı.");
                }

            }
            else
            {
                /*
                String comboboxSecilenStr=comboBox.getValue().toString();
                double eklenecekTutar=Double.parseDouble(eklenecekTutarText.getText().trim());
                double altLimit=Double.parseDouble(altLimitText.getText().trim());
                double ustLimit=Double.parseDouble(ustLimitText.getText().trim());*/
                //System.out.println(isim2);
                //System.out.println(comboboxSecilenStr+" "+eklenecekTutar);
                baglanti.depositEkle(isim2.trim(),comboboxSecilenStr,eklenecekTutar,altLimit,ustLimit);
                baglanti.yapilanIslemler(isim2,comboboxSecilenStr,eklenecekTutar,altLimit,ustLimit,null,"Para Ekleme");
            }



        });

        butoon2.setOnAction(e->{
            ArrayList<Double> arrayList1=new ArrayList<>();
            //String comboboxSecilenStr2=comboBox.getValue().toString();

            String dovizCinsicb2=comboBox2.getValue().toString();
            double cikarilacakTutar=Double.parseDouble(cikarilacakTutarText.getText().trim());
            arrayList1= baglanti.altUstLimitBul(isim2,dovizCinsicb2);
            if(arrayList1.isEmpty())
            {

                baglanti.paraCikar(isim2,dovizCinsicb2,cikarilacakTutar);
                baglanti.yapilanIslemler(isim2,dovizCinsicb2,cikarilacakTutar,0,0,null,"Para Çikarma");
            }
            else if(arrayList1.get(2)-cikarilacakTutar<arrayList1.get(0))
            {
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION); //Alert tanımlanması ve alert türünün seçilmesi

                alert.setTitle("Confirmation Diologs"); //Alert e başlık verilmesi
                alert.setHeight(100); //Alert in genişlik ve yüksekliğinin belirlenmesi
                alert.setWidth(100);

                alert.setHeaderText("Belirlenen Alt Limitin Altına Düşüldü. ONAYLIYOR MUSUNUZ?"); //Başlık yazısının ayaralanması
                alert.setContentText(button.getText() + " butonuna tıkladınız.Çıkarmak istediğinizden emin misin?"); //İçerik yazısına button ismi alınması ve yazının ayarlanması

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){

                    baglanti.paraCikar(isim2,dovizCinsicb2,cikarilacakTutar);
                    baglanti.yapilanIslemler(isim2,dovizCinsicb2,cikarilacakTutar,0,0,null,"Para Çikarma");



                } else {
                    bilgi.setText("Cansel butonuna tıklandı.");
                }
            }
            else{
                baglanti.paraCikar(isim2,dovizCinsicb2,cikarilacakTutar);
                baglanti.yapilanIslemler(isim2,dovizCinsicb2,cikarilacakTutar,0,0,null,"Para Çikarma");
            }


        });

        button3.setOnAction(e->{
            // baglanti.sil2();

            String donusturulenDovizCinsi=comboBox3.getValue().toString();
            String donusenDovizCinsi=comboBox4.getValue().toString();
            double donusturulenMiktar=Double.parseDouble(donusturulenMiktarText.getText().trim());
            double altLimit2=Double.parseDouble(altLimitText2.getText().trim());
            double ustLimit2=Double.parseDouble(ustLimitText2.getText().trim());
            double donusenSonuc= baglanti.paraDonustur(isim2,donusturulenDovizCinsi,donusenDovizCinsi,donusturulenMiktar,altLimit2,ustLimit2);
            ArrayList<Double> arrayList1=new ArrayList<>();
            ArrayList<Double> arrayList2=new ArrayList<>();
            arrayList1= baglanti.altUstLimitBul(isim2,donusturulenDovizCinsi);
            arrayList2=baglanti.altUstLimitBul(isim2,donusenDovizCinsi);
            if(arrayList1.isEmpty()&&arrayList2.isEmpty()){
                String g=String.valueOf(donusenSonuc);
                donusturulenSonuc.setText("Sonuc: "+g);
                buildData();
                baglanti.sil2();
                baglanti.yapilanIslemler(isim2,donusenDovizCinsi,donusturulenMiktar,altLimit2,ustLimit2,donusturulenDovizCinsi,"Para Dönüştürme");


            }
            if(arrayList1.get(2)-donusturulenMiktar<arrayList1.get(0)||arrayList2.get(2)+donusenSonuc>arrayList2.get(1)){

                Alert alert=new Alert(Alert.AlertType.CONFIRMATION); //Alert tanımlanması ve alert türünün seçilmesi

                alert.setTitle("Confirmation Diologs"); //Alert e başlık verilmesi
                alert.setHeight(100); //Alert in genişlik ve yüksekliğinin belirlenmesi
                alert.setWidth(100);

                alert.setHeaderText("Belirlenen Limit Değerleri Aşıldı/Altına Düşüldü. ONAYLIYOR MUSUNUZ?"); //Başlık yazısının ayaralanması
                alert.setContentText(button.getText() + " butonuna tıkladınız.Onaylamak istediğinizden emin misin?"); //İçerik yazısına button ismi alınması ve yazının ayarlanması

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){



                    String g=String.valueOf(donusenSonuc);
                    donusturulenSonuc.setText("Sonuc: "+g);
                    buildData();
                    baglanti.sil2();
                    baglanti.yapilanIslemler(isim2,donusenDovizCinsi,donusturulenMiktar,altLimit2,ustLimit2,donusturulenDovizCinsi,"Para Dönüştürme");


                } else {
                    bilgi.setText("Cansel butonuna tıklandı.");
                }

            }
            else{

                String g=String.valueOf(donusenSonuc);
                donusturulenSonuc.setText("Sonuc: "+g);
                buildData();
                baglanti.sil2();
                baglanti.yapilanIslemler(isim2,donusenDovizCinsi,donusturulenMiktar,altLimit2,ustLimit2,donusturulenDovizCinsi,"Para Dönüştürme");
            }


        });
        kurceviriciButton.setOnAction(e->{
            KurCevirici kurCevirici=new KurCevirici();
            kurCevirici.isimVer(isim2);
            kurCevirici.start(primaryStage);
        });
        button5.setOnAction(e->{
            buildData();
            baglanti.sil2();
        });
        button6.setOnAction(e->{
            LineChartDoviz lineChartDoviz=new LineChartDoviz();
            lineChartDoviz.isimVer(isim2);
            lineChartDoviz.start(primaryStage);
        });
        button7.setOnAction(e->{
            YapilanIslemler yapilanIslemler=new YapilanIslemler();
            yapilanIslemler.isimal(isim2);
            yapilanIslemler.start(primaryStage);
        });






        Scene scene = new Scene(borderPane, 1680, 900);
        primaryStage.setScene(scene);
        primaryStage.show();




        /*
        a.addAll(new PieChart.Data("aa",25),
                 new PieChart.Data("bb",25),
                new PieChart.Data("c",25),
                new PieChart.Data("d",25)


        );
        root =new BorderPane();

        pieChart=new PieChart();
        pieChart.setData(a);
        pieChart.setTitle("asdasasd");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);
        root.setCenter(pieChart);

        primaryStage.setTitle("Kullanici Sayfasi");

        Scene scene=new Scene(root,500,500);

        primaryStage.setScene(scene);
        primaryStage.show();*/

    }
    public static void main(String[] args) {
        launch(args);
    }
}
