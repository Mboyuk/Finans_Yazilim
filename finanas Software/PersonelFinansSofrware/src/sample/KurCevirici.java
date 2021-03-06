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
        Button button=new Button(  "??evir");
        Button button1=new Button("Temizle");
        Button button2=new Button("<-- Geri");
        // ChoiceBox cb=new ChoiceBox();
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(dovizCinsiArray);


        CheckBox checkbox1=new CheckBox("ABD Dolar??");
        CheckBox checkbox2=new CheckBox("Avustalya Dolar??");
        CheckBox checkbox3=new CheckBox("Danimarka Kronu");
        CheckBox checkbox4=new CheckBox("EURO");
        CheckBox checkbox5=new CheckBox("??ngiliz Sterlini");
        CheckBox checkbox6=new CheckBox("??svi??re Frang??");
        CheckBox checkbox7=new CheckBox("??sve?? Kronu");
        CheckBox checkbox8=new CheckBox("Kanada Dolar??");
        CheckBox checkbox9=new CheckBox("Kuveyt Dinar??");
        CheckBox checkbox10=new CheckBox("Norve?? Kronu");
        CheckBox checkbox11=new CheckBox("SA Riyali");
        CheckBox checkbox12=new CheckBox("Japon Yeni");
        CheckBox checkbox13=new CheckBox("Bulgar Levas??");
        CheckBox checkbox14=new CheckBox("Rumen Leyi");
        CheckBox checkbox15=new CheckBox("Rus Lublesi");
        CheckBox checkbox16=new CheckBox("??ran Riyali");
        CheckBox checkbox17=new CheckBox("??in Yuan??");
        CheckBox checkbox18=new CheckBox("Pakistan Rupisi");
        CheckBox checkbox19=new CheckBox("Katar Riyali");
        CheckBox checkbox20=new CheckBox("????H (SDR)");
        TextField textField=new TextField();
        textField.setPromptText("??evirilecek Miktar");
        Label label=new Label("??evirilecek Tutar");

        tableview = new TableView();


        ArrayList<Double> dovizAlis=new ArrayList<>();
        ArrayList<String> dovizCinsleri=new ArrayList<>();


        button.setOnAction(e->{
            String dovizCinsi=comboBox.getValue().toString();
            double mevcutPara=Double.parseDouble(textField.getText().trim());
            double dovizAlisMiktar??=baglanti.kurCevirici(dovizCinsi);
            if(checkbox1.isSelected()){
                // baglanti.kurCevirici(comboboxSecilenStr);
                double dovizMiktar??2=baglanti.kurCevirici("ABD DOLARI");

                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                dovizCinsleri.add("ABD DOLARI");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);

            }
            if(checkbox2.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("AVUSTRALYA DOLARI");
                dovizCinsleri.add("AVUSTRALYA DOLARI");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox3.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("DAN??MARKA KRONU");
                dovizCinsleri.add("DAN??MARKA KRONU");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox4.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("EURO");
                dovizCinsleri.add("EURO");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox5.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("??NG??L??Z STERL??N??");
                dovizCinsleri.add("??NG??L??Z STERL??N??");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox6.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("??SV????RE FRANGI");
                dovizCinsleri.add("??SV????RE FRANGI");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox7.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("??SVE?? KRONU");
                dovizCinsleri.add("??SVE?? KRONU");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox8.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("KANADA DOLARI");
                dovizCinsleri.add("KANADA DOLARI");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox9.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("KUVEYT D??NARI");
                dovizCinsleri.add("KUVEYT D??NARI");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox10.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("NORVE?? KRONU");
                dovizCinsleri.add("NORVE?? KRONU");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox11.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("SUUD?? ARAB??STAN R??YAL??");
                dovizCinsleri.add("SUUD?? ARAB??STAN R??YAL??");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox12.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("JAPON YEN??");
                dovizCinsleri.add("JAPON YEN??");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox13.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("BULGAR LEVASI");
                dovizCinsleri.add("BULGAR LEVASI");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox14.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("RUMEN LEY??");
                dovizCinsleri.add("RUMEN LEY??");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox15.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("RUS RUBLES??");
                dovizCinsleri.add("RUS RUBLES??");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox16.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("??RAN R??YAL??");
                dovizCinsleri.add("??RAN R??YAL??");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox17.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("????N YUANI");
                dovizCinsleri.add("????N YUANI");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox18.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("PAK??STAN RUP??S??");
                dovizCinsleri.add("PAK??STAN RUP??S??");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox19.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("KATAR R??YAL??");
                dovizCinsleri.add("KATAR R??YAL??");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
            }
            if(checkbox20.isSelected()){
                double dovizMiktar??2=baglanti.kurCevirici("??ZEL ??EKME HAKKI (SDR)");
                dovizCinsleri.add("??ZEL ??EKME HAKKI (SDR)");
                dovizAlis.add(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
                System.out.println(dovizAlisMiktar??/dovizMiktar??2*mevcutPara);
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

        gridPane.setHgap(4); //S??tunlar aras??ndaki yatay bo??luklar?? ayar
        gridPane.setVgap(5); //S??tunar aras??ndaki dikey  bo??luklar?? ayarlama
        gridPane.setPadding(new Insets(20)); //GridPane'i??evresi ile aras??ndaki bo??lu??un belirlenmesi
        gridPane.setAlignment(Pos.TOP_LEFT);

        primaryStage.setTitle("Kur ??evirici");
        Scene scene = new Scene(gridPane, 1680, 900);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
