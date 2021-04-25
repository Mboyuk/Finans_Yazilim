package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;

public class YapilanIslemler extends Application {
    private ObservableList<ObservableList> data;
    private  ObservableList<PieChart.Data> details=FXCollections.observableArrayList();
    private String isim2;
    private TableView tableview;
    private final ObservableList<PieChart.Data> a= FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }
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
            String SQL = "SELECT * from yapilanislemler2 WHERE KullaniciAdi='"+isim2+"'";
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

    @Override
    public void start(Stage primaryStage) {
        Button button=new Button("Kullanici Sayfasi");
        tableview=new TableView();
        buildData();
        GridPane gridPane=new GridPane();
        gridPane.getChildren().addAll(tableview,button);
        GridPane.setConstraints(tableview,0,0);
        GridPane.setConstraints(button,0,1);
        button.setOnAction(e->{
            Kullanici kullanici=new Kullanici();
            kullanici.isimal(isim2);
            kullanici.start(primaryStage);
        });
        Scene scene = new Scene(gridPane, 1680, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
