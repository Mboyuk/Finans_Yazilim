package sample;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

public class Main extends Application {
    final String secretKey = "SectumSempra";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Veriler veriler=new Veriler();
        Baglanti baglanti=new Baglanti();
        Button veriCekmebutonu=new Button("Kur Bilgileri Çek");
        veriCekmebutonu.setFont(Font.font("Calibri", FontWeight.BOLD,15));
        veriCekmebutonu.setMinWidth(200);
        //Kullanıcı kayıt kodları
        Button kkButton=new Button("KAYIT");
        Label kkAdLabel,kkSoyadLabel,kkKAdiLabel,kkSifreLAbel;
        PasswordField passwordField=new PasswordField();
        passwordField.setPromptText("Şifre");
        TextField kkAdtext,kkSoyadText,kkKAdiText,kkSifreText;
        kkAdLabel=new Label("Ad:");
        kkSoyadLabel=new Label("Soyad:");
        kkKAdiLabel=new Label("Kullanici Adi:");
        kkSifreLAbel=new Label("Sifre");
        kkAdLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        kkSoyadLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        kkKAdiLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        kkSifreLAbel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        kkButton.setFont(Font.font("Calibri", FontWeight.BOLD,15));
        kkButton.setMinWidth(100);
        kkAdtext=new TextField();
        kkSoyadText=new TextField();
        kkKAdiText=new TextField();
        kkSifreText=new TextField();
        kkSoyadText.setPromptText("Soyad giriniz");
        kkAdtext.setPromptText("Adı giriniz");
        kkSifreText.setPromptText("Sifre giriniz");
        kkKAdiText.setPromptText("Kullanici Adı giriniz");
        //Kullanici giriş kodları
        Button kullaniciGirisButton=new Button("GİRİŞ");
        Label kadiLabel,sifreLabel;
        kadiLabel=new Label("Kullanici Adi:"); //Label  tanımlanması
        kadiLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15)); //Button'ın yazı şekli,kalınlığı ve büyüklüğünün ayarlanması
        sifreLabel=new Label("Sifre");
        sifreLabel.setFont(Font.font("Calibri", FontWeight.NORMAL,15));
        kullaniciGirisButton.setFont(Font.font("Calibri", FontWeight.BOLD,15));
        kullaniciGirisButton.setMinWidth(100);
        TextField kAdiText=new TextField();
        kAdiText.setPromptText("Adı giriniz");
        TextField sifreText=new TextField();
        sifreText.setPromptText("Sifre giriniz");
        ///
        Button dovizDoldur=new Button( "dobiz doldur");
        GridPane gridPane=new GridPane();
        gridPane.setHgap(5); //Sütunlar arasındaki yatay boşlukları ayarlama
        gridPane.setVgap(5); //Sütunlar arasındaki dikey  boşlukları ayarlama
        gridPane.setPadding(new Insets(20)); //GridPane'in çevresi ile arasındaki boşluğun belirlenmesi
        gridPane.setAlignment(Pos.TOP_LEFT); //GridPane'in hizalanması
        //gridPane.setGridLinesVisible(true); //Bölmelerin çizgisinin görünür yapılması
        gridPane.getChildren().addAll(kAdiText,passwordField,kullaniciGirisButton,sifreLabel,
                kadiLabel,kkAdLabel,kkAdtext,kkSoyadLabel,kkSoyadText,kkKAdiLabel,kkKAdiText,
                kkSifreLAbel,kkSifreText,kkButton,veriCekmebutonu,dovizDoldur);
        GridPane.setConstraints(kadiLabel,0,0);
        GridPane.setConstraints(sifreLabel,0,1);
        GridPane.setConstraints(kAdiText,1,0); //0. kolon 0. satır
        GridPane.setConstraints(passwordField,1,1); //0. kolon 1. satır
        GridPane.setConstraints(kullaniciGirisButton,1,2);
        //
        GridPane.setConstraints(kkAdLabel,4,0);
        GridPane.setConstraints(kkSoyadLabel,4,1);
        GridPane.setConstraints(kkKAdiLabel,4,2);
        GridPane.setConstraints(kkSifreLAbel,4,3);
        GridPane.setConstraints(kkAdtext,5,0);
        GridPane.setConstraints(kkSoyadText,5,1);
        GridPane.setConstraints(kkKAdiText,5,2);
        GridPane.setConstraints(kkSifreText,5,3);
        GridPane.setConstraints(kkButton,5,4);
        GridPane.setConstraints(veriCekmebutonu,3,9);
        GridPane.setConstraints(dovizDoldur,3,10);


        Timer timer=new Timer();
        TimerTask gorev=new TimerTask() {
            @Override
            public void run() {
                veriler.verileriCek(false);
            }
        };
        long period = 1000L * 60L * 60L * 24L;
        timer.schedule(gorev,0,period);
       // DesSifreleme desSifreleme = new DesSifreleme();

        dovizDoldur.setOnAction(e->{
            veriler.verileriCek(false);
        });


        veriCekmebutonu.setOnAction(e->{
            veriler.verileriCek(true);
        });

        kkButton.setOnAction(e->{
            String ad=kkAdtext.getText();
            String soyad=kkSoyadText.getText();
            String kullaniciAdi=kkKAdiText.getText();
            String sifre=kkSifreText.getText();

            String encryptedSifre = AesSifrelemeMethod.encrypt(sifre, secretKey) ;
            baglanti.kullaniciEkle(ad,soyad,kullaniciAdi,encryptedSifre);
            //şifreleme Algoritması ekle
            kkAdtext.clear();
            kkSoyadText.clear();
            kkKAdiText.clear();
            kkSifreText.clear();

        });




        kullaniciGirisButton.setOnAction(e->{
            String  kullaniciAdi=kAdiText.getText().trim();
            String sifre=passwordField.getText();
            String gelenSifre=baglanti.kullaniciGiris(kullaniciAdi);
            String decryptedSifre= AesSifrelemeMethod.decrypt(gelenSifre, secretKey) ;
            System.out.println(kullaniciAdi+" "+sifre);
            System.out.println(gelenSifre);
            if(sifre.equals(decryptedSifre))
            {
                //KULALNICI SAYFASI AÇILIRRR

                Kullanici kullanici=new Kullanici();
                kullanici.isimal(kullaniciAdi);
                kullanici.start(primaryStage);
                //Kullanici kullanici=new Kullanici();
                // kullanici.start(primaryStage);
                // Preferences userPreferences = Preferences.userRoot();
                //userPreferences.put(kullaniciAdi,sifre);
            }
            else{
                System.out.println( "yanlış sifre");
            }
        });


        Scene scene=new Scene(gridPane,1680,900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
