package sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Veriler {
    StringBuilder htmlText=new StringBuilder();

    public void verileriCek(boolean nereyeGidecek){
        ArrayList<String> arrayDovizCinsi=new ArrayList<>();
        ArrayList<Double> arrayDovizAlis=new ArrayList<>();

        try{
            URL openUrl=new URL("http://www.tcmb.gov.tr/kurlar/today.xml");
            URLConnection connection=openUrl.openConnection();
            InputStream is=connection.getInputStream();
            InputStreamReader isReader=new InputStreamReader(is,"UTF-8");
            int gelenData=0;
            do{
                gelenData=isReader.read();
                if(gelenData!=-1){
                    htmlText.append((char)gelenData);
                }
            }while (gelenData!=-1);

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        String regexDovizCinsi="(<Isim>)(.*?)(</Isim>)";

        Pattern pattern=Pattern.compile(regexDovizCinsi);
        Matcher matcher=pattern.matcher(htmlText);
        while (matcher.find()){
            arrayDovizCinsi.add(matcher.group(2));
        }
        // System.out.println(arrayDovizCinsi);
        String regexDovizAlis="(<ForexBuying>)(.*?)(</ForexBuying>)";
        Pattern pattern2=Pattern.compile(regexDovizAlis);
        Matcher matcher2=pattern2.matcher(htmlText);
        while (matcher2.find()){
            arrayDovizAlis.add( Double.parseDouble(matcher2.group(2)));
        }

        // System.out.println(arrayDovizAlis);
        // System.out.println(arrayDovizAlis.size()+"   "+arrayDovizCinsi.size());


        if(nereyeGidecek==true)
        {
            System.out.println(arrayDovizCinsi);
            System.out.println(arrayDovizAlis);

        }
        else{
            Baglanti baglanti=new Baglanti();
            baglanti.dovizEkle(arrayDovizCinsi,arrayDovizAlis);
        }
        //baglanti.dovizEkle("mehmet",25.4);

    }


}
