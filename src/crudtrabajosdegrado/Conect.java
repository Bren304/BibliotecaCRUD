package crudtrabajosdegrado;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conect {
    
    static String url = "jdbc:mysql://localhost:3306/trabajos_de_grado";
    static String user = "root";
    static String password = "";
    
    public static Connection Connect(){
        Connection conector = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conector = DriverManager.getConnection(url, user, password);
            System.out.println("Se ha conecctado");
        } catch (Exception e) {
            System.err.println(e);
        }
        return conector;
    }
    
}
