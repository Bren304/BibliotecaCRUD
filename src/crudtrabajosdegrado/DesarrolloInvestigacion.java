package crudtrabajosdegrado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class DesarrolloInvestigacion extends TrabajoDeGrado{
    
    Connection con = Conect.Connect();
    PreparedStatement ps;
    ResultSet rs;
    JTextField nomProy;
    JTextField idPDF;
    
    int idProy;
    
    public void setNom(JTextField nomProy) {
        this.nomProy = nomProy;
    }

    public ResultSet getRs() {
        return rs;
    }

    public JTextField getIdPDF() {
        return idPDF;
    }
    
    @Override
    public void Registrar(){        
        int yes = JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar este proyecto?");
        
        if (yes == JOptionPane.YES_OPTION) {
            try {
                ps = con.prepareStatement("INSERT INTO proy_investigacion (nombre_proyecto, id_pdf) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, nomProy.getText());
                ps.setInt(2, Integer.parseInt(idPDF.getText()));
                
                int val = ps.executeUpdate();
                
                if (val > 0) {
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        idProy = rs.getInt(1);
                    }
                }
                
                ps = con.prepareStatement("INSERT INTO trabajo_de_grado(id_estudiante1, id_estudiante2, id_estudiante3, id_docente, id_codirector, tipo_trabajo, estado_trabajo, id_proyecto_investigacion, id_practica, estado) VALUES ()");
                
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