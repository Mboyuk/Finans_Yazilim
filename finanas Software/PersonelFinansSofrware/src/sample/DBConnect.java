package sample;

import java.sql.*;

public class DBConnect {
    private String kullanici_adi = "root";
    private String parola = "";

    private String db_ismi = "personelfinanssoftwarework";

    private String host =  "localhost";

    private int port = 3306;

    private Connection con = null;

    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public  Connection connect() throws SQLException {
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
        return con;
    }
    public  Connection getConnection() throws SQLException, ClassNotFoundException{
        if(con !=null && !con.isClosed())
            return con;
        connect();
        return con;

    }

}
