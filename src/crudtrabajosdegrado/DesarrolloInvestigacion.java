package crudtrabajosdegrado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


public class DesarrolloInvestigacion extends TrabajoDeGrado{
    
    Connection con = Conect.Connect();
    PreparedStatement ps;
    ResultSet rs;

    String nombreEstudiante2, nombreEstudiante3, nombreProyecto, descripcion;
    int valorConsignacion;

    @Override
    public void Registrar(){
        int yes = JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar este proyecto?");
        
        if (yes == JOptionPane.YES_OPTION) {
            try {
                ps = con.prepareStatement("INSERT INTO ");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    @Override
    public void Modificar(){
        
    }
    
    @Override
    public void Eliminar(){
        
    }
    
    @Override
    public void Devolver(){
        
    }
    
    @Override
    public void SubirArchivo(){
        
    }
    
    public void TipoProyecto(JComboBox tipoProyecto){
        try {
            ps = con.prepareStatement("SELECT * FROM tipo_trabajo_de_grado WHERE id < 3");
            
            rs = ps.executeQuery();
            
            tipoProyecto.removeAllItems();
            
            while(rs.next()){
                String tipoProyec = rs.getString("nombre_tipoTrab");
                tipoProyecto.addItem(tipoProyec);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}