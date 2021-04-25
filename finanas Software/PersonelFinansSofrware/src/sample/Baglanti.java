package sample;


import sun.util.resources.LocaleData;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Baglanti {

    private String kullanici_adi = "root";
    private String parola = "";

    private String db_ismi = "personelfinanssoftwarework";

    private String host =  "localhost";

    private int port = 3306;

    private Connection con = null;

    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public void preparedCalisanlariGetir(int id) {

        String sorgu = "Select * From calisanlar where id > ? and ad like ? ";


        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,"M%");


            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String ad  = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String email =  rs.getString("email");

                System.out.println("Ad : " + ad + " Soyad : " + soyad + " Email : " + email);



            }


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<Double> altUstLimitBul(String kullaniciAdi,String dovizCinsi){
        //  ArrayList<java.util.Date> date=new ArrayList<>();

        String sorgu4 = "SELECT  AltLimit,UstLimit,MevcutBakiye FROM deposit WHERE KullaniciAdi='"+kullaniciAdi+"' and DovizCinsi='"+dovizCinsi+"'";
        ArrayList<Double> arrayList=new ArrayList<>();

        try {
            statement = con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu4);
            while (rs.next()){

                arrayList.add(rs.getDouble("AltLimit"));
                arrayList.add(rs.getDouble("UstLimit"));
                arrayList.add(rs.getDouble("MevcutBakiye"));


            }
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrayList;
    }
    public void yapilanIslemler(String kullaniciAdi,String dovizCinsi,double e_c_tutar,double altLimit,double ustLimit,String donusturulenDovizCinsi,String yapilanIslem){
        LocalDate date=LocalDate.now();
        try {
            statement = con.createStatement();

            String sorgu2= "Insert Into yapilanislemler2 (KullaniciAdi,DovizCinsi,ECTutar,AltLimit,UstLimit,DDovizCinsi,YapilanIslem,Tarih) VALUES(" + "'" +kullaniciAdi + "',"+"'"+dovizCinsi + "'," + "'" + e_c_tutar + "'," + "'" + altLimit +  "'," + "'" + ustLimit +  "'," + "'" + donusturulenDovizCinsi + "'," + "'" + yapilanIslem + "'," + "'" + date + "')";
            statement.executeUpdate(sorgu2);



        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void kurCeviriciYukle(ArrayList dovizCinsi,ArrayList dovizAlis,double cevirilecekTutar){

        try {
            statement = con.createStatement();
            for(int i=0;i<dovizCinsi.size();i++){
                String sorgu= "Insert Into transactions (DovizCinsi,DovizAlis,CevirilecekTutar) VALUES(" + "'" + dovizCinsi.get(i) + "'," + "'" + dovizAlis.get(i) + "'," + "'" + cevirilecekTutar +   "')";
                statement.executeUpdate(sorgu);
            }
            //System.out.println(dovizAlis);
            // System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList lineChartDoldur3(String dovizCinsi,LocalDate value1,LocalDate value2){

        ArrayList<java.util.Date> date=new ArrayList<>();

        String sorgu4 = "SELECT  Tarih FROM currencyrates where DovizCinsi='"+dovizCinsi+"' and Tarih between '"+value1+" 'and '"+value2+"' ";


        try {
            statement = con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu4);
            while (rs.next()){

                date.add(rs.getDate("Tarih"));


            }
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }
    public void lineChartdoldur2(String dovizCinsi,double dovizAlis, java.util.Date date){
        try {
            statement = con.createStatement();
            String sorgu= "Insert Into deneme3 (DovizCinsi,DovizAlis,Tarih) VALUES(" + "'" + dovizCinsi + "'," + "'" + dovizAlis +"'," +"'" + date +  "')";
            statement.executeUpdate(sorgu);// String sorgu= "Insert Into deneme (KullaniciAdi,DovizCinsi,TLkarsiligi) VALUES(" + "'" + kullaniciAdi + "'," + "'" + dovizCinsi + "'," + "'" + dovizAlis2 +   "')";



            //System.out.println(dovizAlis);
            // System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList lineChartDoldur(String dovizCinsi,LocalDate value1,LocalDate value2){
        ArrayList<Double> dovizAlis2=new ArrayList<>();
        // ArrayList<java.util.Date> date=new ArrayList<>();
        // double dovizAlis=0.0;
        //java.util.Date date;
        String sorgu4 = "SELECT DovizAlis,Tarih FROM currencyrates where DovizCinsi='"+dovizCinsi+"' and Tarih between '"+value1+"' and '"+value2+"'";


        try {
            statement = con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu4);
            while (rs.next()){
                double dovizAlis=rs.getDouble("DovizAlis");
                java.util.Date date=rs.getDate("Tarih");
                dovizAlis2.add(rs.getDouble("DovizAlis"));
                lineChartdoldur2(dovizCinsi,dovizAlis,date);
                System.out.println("seccccxx"+date);

            }
            //[5.5709, 5.5227, 5.5815, 5.5809, 5.5809, 5.5809]
            //[2019-07-31, 2019-07-31, 2019-08-01, 2019-08-03, 2019-08-04, 2019-08-05]


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(dovizAlis);
        // System.out.println(date);
        return dovizAlis2;
    }
    public ArrayList pieChartDoldur(String kullaniciAdi){

        String sorgu4 = "Select DISTINCT TLkarsiligi From deneme WHERE KullaniciAdi='"+kullaniciAdi+"'";

        ArrayList<Double> tlKarsiligi=new ArrayList<>();


        try {
            statement = con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu4);
            while (rs.next()){
                tlKarsiligi.add(rs.getDouble("TLkarsiligi"));
            }


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tlKarsiligi;


    }

    public  double pieChartveriler2(String kullaniciAdi,String dovizCinsi, double paraMiktari){

        //Kullanici kullanici=new Kullanici();
        double dovizAlis2=0.0;
        LocalDate date=LocalDate.now();
        Date datee=Date.valueOf("2019-08-09");
        String sorgu4 = "Select DovizAlis From currencyrates WHERE DovizCinsi='"+dovizCinsi+"' and Tarih='"+datee+"'";

        ArrayList<Double> dovizAlis=new ArrayList<>();


        try {
            statement = con.createStatement();
            ResultSet rs2=statement.executeQuery(sorgu4);
            while (rs2.next()){
                dovizAlis2=(rs2.getDouble("DovizAlis"))*paraMiktari;
            }
            String sorgu= "Insert Into deneme (KullaniciAdi,DovizCinsi,TLkarsiligi) VALUES(" + "'" + kullaniciAdi + "'," + "'" + dovizCinsi + "'," + "'" + dovizAlis2 +   "')";
            statement.executeUpdate(sorgu);


            //System.out.println(dovizAlis);
            // System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dovizAlis2;

    }
    public ArrayList pieChartVeriler(String kullaniciAdi){
        //  kullaniciAdi="muro";
        LocalDate date=LocalDate.now();
        String sorgu3 = "Select MevcutBakiye,DovizCinsi From deposit WHERE KullaniciAdi='"+kullaniciAdi+"'";
        ArrayList<String> dovizCinsi=new ArrayList<>();
        ArrayList<Double> arrayList2=new ArrayList<>();
        double aa=0.0;
        try {
            int i=0;
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu3);
            while (rs.next()) {
                dovizCinsi.add(rs.getString("DovizCinsi"));
                arrayList2.add(rs.getDouble("MevcutBakiye"));
                aa=  pieChartveriler2(kullaniciAdi,dovizCinsi.get(i),arrayList2.get(i));

                i++;
            }
            // String sorgu4 = "Select DovizAlis From dovizkurlari WHERE DovizCinsi='"+donusenDovizCinsi+"' and Tarih='"+date+"'";


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dovizCinsi ;
    }
    public ArrayList bilgileriGetir(String kullaniciAdi){
        String sorgu2 = "Select DovizCinsi,MevcutBakiye,AltLimit,UstLimit From deposit WHERE KullaniciAdi='"+kullaniciAdi+"'";

        ArrayList<Double> arrayList=new ArrayList<>();
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu2);
            while (rs.next()) {
                String dovizCinsi=rs.getString("DovizCinsi");
                arrayList.add(rs.getDouble("MevcutBakiye"));
                arrayList.add(rs.getDouble("AltLimit"));
                arrayList.add(rs.getDouble("UstLimit"));


            }


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrayList;
    }
    public double kurCevirici(String dovizCinsi){
        LocalDate date=LocalDate.now();

        String sorgu2 = "Select DovizAlis From currencyrates WHERE Tarih='"+date+"' and DovizCinsi='"+dovizCinsi+"'";

        double dovizAlis=0.0;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu2);
            while (rs.next()) {
                dovizAlis=rs.getDouble("DovizAlis");
            }


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dovizAlis;
    }


    public double paraDonustur(String kullaniciAdi,String donusturulendovizCinsi,String donusenDovizCinsi,double donusturulecekMiktar,double altLimit,double ustLimit){
        LocalDate date=LocalDate.now();
        String sorgu2 = "Select DovizAlis From currencyrates WHERE DovizCinsi='"+donusturulendovizCinsi+"' and Tarih='"+date+"'";
        String sorgu3 = "Select DovizAlis From currencyrates WHERE DovizCinsi='"+donusenDovizCinsi+"' and Tarih='"+date+"'";
        double donusenSonuc=0.0;
        double dovizAlisMiktar=0.0;
        double dovizAlisMiktardonusturulen=0.0;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu2);

            while (rs.next()) {
                dovizAlisMiktar=rs.getDouble("DovizAlis");
            }
            ResultSet rs2=statement.executeQuery(sorgu3);

            while (rs2.next()){
                dovizAlisMiktardonusturulen=rs2.getDouble("DovizAlis");
            }
            System.out.println(dovizAlisMiktar+" "+dovizAlisMiktardonusturulen);
            donusenSonuc=dovizAlisMiktar/dovizAlisMiktardonusturulen*donusturulecekMiktar;
            paraCikar(kullaniciAdi,donusturulendovizCinsi,donusturulecekMiktar);
            depositEkle(kullaniciAdi,donusenDovizCinsi,donusenSonuc,altLimit,ustLimit);


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return donusenSonuc;

    }
    public void paraCikar(String kullaniciAdi,String dovizCinsi,double cikarilacakTutar){
        String sorgu2 = "Select MevcutBakiye From deposit WHERE KullaniciAdi='"+kullaniciAdi+"' and DovizCinsi='"+dovizCinsi+"'";

        double mbakiye=0.0;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu2);
            while (rs.next()) {
                mbakiye=rs.getDouble("MevcutBakiye");
            }


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            mbakiye-=cikarilacakTutar;

            statement = con.createStatement();

            String sorgu =  "Update deposit Set MevcutBakiye = '"+mbakiye+"' WHERE KullaniciAdi='"+kullaniciAdi+"' and DovizCinsi='"+dovizCinsi+"'";

            statement.executeUpdate(sorgu);

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void depositEkle(String kullaniciAdi ,String dovizCinsi,double eklenecekTutar,double altLimit,double ustLimit){

        String sorgu2 = "Select KullaniciAdi , DovizCinsi,MevcutBakiye From deposit WHERE KullaniciAdi='"+kullaniciAdi+"' and DovizCinsi='"+dovizCinsi+"'";
        String kadi="";
        String dcinsi="";
        double mbakiye=0.0;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu2);
            while (rs.next()) {
                kadi = rs.getString("KullaniciAdi");
                dcinsi = rs.getString("DovizCinsi");
                mbakiye=rs.getDouble("MevcutBakiye");
            }


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if(kullaniciAdi.equals(kadi)&&dovizCinsi.equals(dcinsi)){
                eklenecekTutar+=mbakiye;
                statement = con.createStatement();

                String sorgu4 = "Update deposit Set MevcutBakiye = '"+eklenecekTutar+"' WHERE KullaniciAdi='"+kullaniciAdi+"' and DovizCinsi='"+dovizCinsi+"'";

                statement.executeUpdate(sorgu4);
            }
            else
            {
                statement = con.createStatement();
                String sorgu = "Insert Into deposit (KullaniciAdi,DovizCinsi,MevcutBakiye,AltLimit,UstLimit) VALUES(" + "'" + kullaniciAdi + "'," + "'" + dovizCinsi + "'," + "'" + eklenecekTutar + "'," + "'" + altLimit + "'," + "'" + ustLimit +   "')";
                statement.executeUpdate(sorgu);
                System.out.println("Paranız Eklendi...");

            }
            //System.out.println("ssssssss");


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<String> dovizCinsiCek(){
        String sorgu = "SELECT DISTINCT DovizCinsi From currencyrates ";
        ArrayList<String> dovizcinsi=new ArrayList<>();
        String kullaniciAdi="";
        String sifre="";
        int i=0;
        try {
            statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                dovizcinsi.add(rs.getString("DovizCinsi"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dovizcinsi;

    }
    public void calisanEkle() {



        try {
            statement = con.createStatement();
            String ad = "Semih";
            String soyad = "Aktaş";
            String email =  "semihaktas@gmail.com";
            // Insert Into calisanlar (ad,soyad,email) VALUES('Yusuf','Çetinkaya','mucahit@gmail.com')
            String sorgu = "Insert Into calisanlar (ad,soyad,email) VALUES(" + "'" + ad + "'," + "'" + soyad + "'," + "'" + email + "')";

            statement.executeUpdate(sorgu);



        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public void dovizEkle(ArrayList<String> dovizCinsi,ArrayList<Double> dovizAlis) {
        Date date2=null;
        String sorgu2 = "Select * FROM currencyrates ";

        try {
            statement=con.createStatement();
            ResultSet rs = statement.executeQuery(sorgu2);
            while(rs.next()){
                date2= rs.getDate("Tarih");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);

        }
        LocalDate dateTime = LocalDate.now();
        Date datee=Date.valueOf("2019-08-09");

        if(date2.toString().equals(dateTime.toString())){//AYNI GÜN İÇERİSİNDE Kİ KAYITI ÖNLEMEK İÇİN  date2.toString().equals(dateTime.toString())
            System.out.println(dateTime+" TARİHİNDE KAYIT YAPILDI. TEKRAR KAYIT YAPAMASSINIZ!!!!!");
        }
        else
        {
            try {
                for (int i = 0; i < dovizCinsi.size(); i++) {
                    statement = con.createStatement();
                    String sorgu = "Insert Into currencyrates (DovizCinsi,DovizAlis,Tarih) VALUES(" + "'" + dovizCinsi.get(i) + "'," + "'" + dovizAlis.get(i) + "'," + "'" + dateTime + "')";
                    statement.executeUpdate(sorgu);
                }
                //System.out.println("ssssssss");
                System.out.println(dateTime+" tarihinde veriler cekildi");

            } catch (SQLException ex) {
                Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

            /*
            LocalDate dateTime = LocalDate.now();
            try {
                for (int i = 0; i < dovizCinsi.size(); i++) {
                    statement = con.createStatement();
                    String sorgu = "Insert Into dovizkurlari (DovizCinsi,DovizAlis,Tarih) VALUES(" + "'" + dovizCinsi + "'," + "'" + dovizAlis + "'," + "'" + dateTime + "')";
                    statement.executeUpdate(sorgu);
                }
                //System.out.println("ssssssss");

            } catch (SQLException ex) {
                Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
    public String kullaniciGiris(String kAdi) {

        String sorgu = "Select KullaniciAdi,Sifre From user WHERE KullaniciAdi='"+kAdi+"'";
        String kullaniciAdi="";
        String sifre="";
        try {
            statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sorgu);

            while (rs.next()) {
                kullaniciAdi = rs.getString("KullaniciAdi");
                sifre = rs.getString("Sifre");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sifre;

    }
    public void kullaniciEkle(String ad,String soyad,String kullaniciAdi,String sifre){
        try {
            statement = con.createStatement();
            String sorgu = "Insert Into user (Ad,Soyad,KullaniciAdi,Sifre) VALUES(" + "'" + ad + "'," + "'" + soyad + "'," + "'" + kullaniciAdi +"'," + "'" + sifre + "')";

            statement.executeUpdate(sorgu);

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }

    }







    public void sil() {

        try {
            statement = con.createStatement();

            String sorgu = "Delete from transactions";

            int deger = statement.executeUpdate(sorgu);
            System.out.println(deger + " kadar veri etkilendi...");


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public void sil2() {

        try {
            statement = con.createStatement();

            String sorgu = "Delete from deneme";

            int deger = statement.executeUpdate(sorgu);
            System.out.println(deger + " kadar veri etkilendi...");


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public void sil3() {

        try {
            statement = con.createStatement();

            String sorgu = "Delete from deneme3";

            int deger = statement.executeUpdate(sorgu);
            System.out.println(deger + " kadar veri etkilendi...");


        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public void calisanGuncelle() {


        try {
            statement = con.createStatement();

            String sorgu = "Update calisanlar Set email = 'example@gmail.com' where id > 3";

            statement.executeUpdate(sorgu);

        } catch (SQLException ex) {
            Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    /*
     public void calisanlariGetir() {

         String sorgu = "Select * From calisanlar";

         try {
             statement = con.createStatement();

             ResultSet rs = statement.executeQuery(sorgu);

             while (rs.next()) {

                 int id = rs.getInt("id");
                 String ad = rs.getString("ad");
                 String soyad = rs.getString("soyad");
                 String email = rs.getString("email");

                 System.out.println("Id : " + id + "Ad: " + ad + "Soyad : " + soyad + " Email : " + email);


             }


         } catch (SQLException ex) {
             Logger.getLogger(Baglanti.class.getName()).log(Level.SEVERE, null, ex);
         }



     }*/
    public Baglanti() {

        // "jbdc:mysql://localhost:3306/demo"
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db_ismi+ "?useUnicode=true&characterEncoding=utf8";


        try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı....");
        }


        try {
            con = DriverManager.getConnection(url, kullanici_adi, parola);
            System.out.println("Bağlantı Başarılı...");


        } catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız...");
            //ex.printStackTrace();
        }

    }


}

