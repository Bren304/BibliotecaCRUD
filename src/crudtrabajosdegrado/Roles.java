package crudtrabajosdegrado;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Roles {
    
    Connection con = Conect.Connect();
    PreparedStatement ps;
    ResultSet rs;
    
    public void CrearRol(JTextField nombre, JTextField descripcion){
        int yes = JOptionPane.showConfirmDialog(null, "¿Esta seguro de crear este usuario?");
        
        if(yes == JOptionPane.YES_OPTION){
            try {
                ps = con.prepareStatement("INSERT INTO roles (nombre_rol, descripcion_rol, estado) VALUES (?,?,1)");
                
                ps.setString(1, nombre.getText());
                ps.setString(2, descripcion.getText());
                
                int val = ps.executeUpdate();
                
                if (val > 0) {
                    JOptionPane.showMessageDialog(null, "Registrado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido registar, revise los campos");
                }
            } catch (HeadlessException | SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public void Editar(JTextField nombre, JTextField descripcion, JTextField id){
        int yes = JOptionPane.showConfirmDialog(null, "¿Esta seguro de crear este usuario?");
        
        if(yes == JOptionPane.YES_OPTION){
            try {
                ps = con.prepareStatement("UPDATE roles SET nombre_rol = ?, descripcion_rol = ? WHERE id_rol = ?");
                
                ps.setString(1, nombre.getText());
                ps.setString(2, descripcion.getText());
                ps.setString(3, id.getText());
                
                int val = ps.executeUpdate();
                
                if (val > 0) {
                    JOptionPane.showMessageDialog(null, "Registrado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido registar, revise los campos");
                }
            } catch(HeadlessException | SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public void Eliminar(JTextField id){
        int yes = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este usuario?");
        
        if (yes == JOptionPane.YES_OPTION) {
            try {
                ps = con.prepareStatement("UPDATE roles SET estado = 0 WHERE id_rol = ?");
                
                ps.setString(1, id.getText());
                
                int val = ps.executeUpdate();
                
                if (val > 0) {
                    JOptionPane.showMessageDialog(null, "El rol se ha eliminado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido eliminar, por favor revise los campos");
                }
            } catch (HeadlessException | SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public void Limpiar(JTextField nombre, JTextField descripcion, JTextField id){
        nombre.setText(null);
        descripcion.setText(null);
        id.setText(null);
    }
    
    public void LlenarTabla(JTable tabla){
        try {
            ps = con.prepareStatement("SELECT * FROM roles WHERE estado = 1");
            
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);
            
            rs = ps.executeQuery();
            
            String[] myTabla;
            myTabla = new String[4];
            
            while(rs.next()) {
                
                myTabla[0] = rs.getString("id_rol");
                myTabla[1] = rs.getString("nombre_rol");
                myTabla[2] = rs.getString("descripcion_rol");
                myTabla[3] = rs.getString("estado");
                modelo.addRow(myTabla);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void SeleccionarTabla(JTextField nombre, JTextField descripcion, JTextField id, JTable tabla){
        int filaSeleccionada = tabla.getSelectedRow();
        
        id.setText(tabla.getValueAt(filaSeleccionada, 0).toString());
        nombre.setText(tabla.getValueAt(filaSeleccionada, 1).toString());
        descripcion.setText(tabla.getValueAt(filaSeleccionada, 2).toString());   
    }
}
