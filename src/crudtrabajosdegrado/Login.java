package crudtrabajosdegrado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Login {
    
    Connection con = Conect.Connect();
    PreparedStatement ps;
    ResultSet rs;
    byte rol;
    
    public int InicioSesion(JTextField txtCorreo, JTextField txtContrasena){
        int res = 0;
        try {            
            ps = con.prepareStatement("SELECT * FROM usuarios WHERE correo_institucional = ? AND contrasena = ?");
            
            ps.setString(1, txtCorreo.getText());
            ps.setString(2, txtContrasena.getText());
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                ViewMenu myMenu = new ViewMenu();                
                myMenu.setVisible(true);
                res = 1;
                
            } else {
                JOptionPane.showMessageDialog(null, "No ha podido ingresar, \npor favor intételo de nuevo");
            }
            
        } catch (Exception e) {
            System.err.println(e);
        }
        return res;
    }
}
