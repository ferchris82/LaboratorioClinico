package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private String database_name = "laboratory_clinic";
    private String user = "root";
    private String password = "root";
    private String url = "jdbc:mysql://localhost:3306/" + database_name;
    Connection conn = null;
    
    public Connection getConnection(){
        try{
            //Obtener valor del driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Obtener la conexión
            conn = DriverManager.getConnection(url, user, password);
            
        }catch(ClassNotFoundException e){
            System.err.println("Ha ocurrido un ClassFoundException " + e.getMessage());
        }catch(SQLException e){
            System.err.println("Ha ocurrido un SQLException " + e.getMessage());
        }
        return conn;
    }
}
