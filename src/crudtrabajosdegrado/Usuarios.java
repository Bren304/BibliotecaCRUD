package crudtrabajosdegrado;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Usuarios {

    Connection con = Conect.Connect();

    PreparedStatement ps;
    ResultSet rs;

    public void Crear(JTextField nombre, JTextField dni, JTextField correo, JTextField pass, JComboBox rol) {
        int yes = JOptionPane.showConfirmDialog(null, "¿Esta seguro de crear este usuario?");

        if (yes == JOptionPane.YES_OPTION) {
            try {
                ps = con.prepareStatement("INSERT INTO usuarios (nombre_usuario, DNI_usuario, correo_institucional, contrasena, fk_rol, estado) VALUES (?,?,?,?,?,1)");

                ps.setString(1, nombre.getText());
                ps.setString(2, dni.getText());
                ps.setString(3, correo.getText());
                ps.setString(4, pass.getText());
                ps.setInt(5, (rol.getSelectedIndex() + 1));

                int val = ps.executeUpdate();

                if (val > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Ah ocurrido un error");
                }
            } catch (HeadlessException | SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public void Modificiar(JTextField nombre, JTextField dni, JTextField correo, JTextField contrasena, JComboBox rol, JTextField id){
        int yes = JOptionPane.showConfirmDialog(null, "¿Esta seguro de modificar este usuario?");
        
        if(yes == JOptionPane.YES_OPTION){
            try {
                ps = con.prepareStatement("UPDATE usuarios SET nombre_usuario = ?, DNI_usuario = ?, correo_institucional = ?, contrasena = ?, fk_rol = ? WHERE id_usuario = ?");
                
                ps.setString(1, nombre.getText());
                ps.setString(2, dni.getText());
                ps.setString(3, correo.getText());
                ps.setString(4, contrasena.getText());
                ps.setInt(5, (rol.getSelectedIndex()+1));
                ps.setString(6, id.getText());
                
                int val = ps.executeUpdate();
                
                if (val > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha modificado la informacion");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un erros, revise los campos");
                }
            } catch (HeadlessException | SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public void Eliminar(JTextField id){
        int yes = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este usuario?");
        
        if (yes == JOptionPane.YES_OPTION) {
            try {
                ps = con.prepareStatement("UPDATE usuarios SET estado = 0 WHERE id_usuario = ?");
                
                ps.setInt(1, Integer.parseInt(id.getText()));
                
                int val = ps.executeUpdate();
                
                if (val > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha eliminado al usuario");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido eliminar al usuario, revise los campos");
                }
            } catch (HeadlessException | NumberFormatException | SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public void Limpiar(JTextField nombre, JTextField dni, JTextField correo, JTextField pass, JComboBox rol, JTextField id){
        nombre.setText(null);
        dni.setText(null);
        correo.setText(null);
        pass.setText(null);
        rol.setSelectedIndex(0);
        id.setText(null);
    }

    //Tablas y Comboboxes ------------------------------------------------------
    
    public void LlenarCombo(JComboBox comboRoles) {
        try {
            ps = con.prepareStatement("SELECT nombre_rol FROM roles");

            rs = ps.executeQuery();

            while (rs.next()) {
                String rol = rs.getString("nombre_rol");
                comboRoles.addItem(rol);
            }

        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    public void LlenarComboEstudiantes(JComboBox comboEstudiante1, JComboBox comboEstudiante2, JComboBox comboEstudiante3){
        try {
            ps = con.prepareStatement("SELECT * FROM usuarios WHERE fk_rol = 3");
            
            rs = ps.executeQuery();
            
            comboEstudiante1.removeAllItems();
            comboEstudiante2.removeAllItems();
            comboEstudiante3.removeAllItems();
            
            while(rs.next()){
                String nomEstudiante = rs.getString("nombre_usuario");
                comboEstudiante1.addItem(nomEstudiante);
                comboEstudiante2.addItem(nomEstudiante);
                comboEstudiante3.addItem(nomEstudiante);                
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void LlenarComboDoc(JComboBox comboDocente, JComboBox comboCodirector){
        try {
            ps = con.prepareStatement("SELECT * FROM usuarios WHERE fk_rol = 2");
            
            rs = ps.executeQuery();
            
            comboCodirector.removeAllItems();
            comboDocente.removeAllItems();
            
            while(rs.next()){
                String docente = rs.getString("nombre_usuario");
                
                comboDocente.addItem(docente);
                comboCodirector.addItem(docente);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void LlenarTabla(JTable tabla) {
        try {
            ps = con.prepareStatement("SELECT * FROM usuarios INNER JOIN roles ON roles.id_rol = usuarios.fk_rol WHERE usuarios.estado = 1 ORDER BY usuarios.id_usuario");

            rs = ps.executeQuery();

            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0); // Limpia la tabla

            String[] myTabla;
            myTabla = new String[7];
            while (rs.next()) {
                myTabla[0] = Integer.toString(rs.getInt("id_usuario"));
                myTabla[1] = rs.getString("nombre_usuario");
                myTabla[2] = rs.getString("DNI_usuario");
                myTabla[3] = rs.getString("correo_institucional");
                myTabla[4] = rs.getString("contrasena");
                myTabla[5] = rs.getString("roles.nombre_rol");
                myTabla[6] = rs.getString("usuarios.estado");
                modelo.addRow(myTabla);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    public void SeleccionarTable(JTable tabla, JTextField nombre, JTextField dni, JTextField correo, JTextField pass, JComboBox rol, JTextField id, JTextField estado){
        int tableSeleccionada = tabla.getSelectedRow();
        
        id.setText((tabla.getValueAt(tableSeleccionada, 0).toString()));
        nombre.setText((tabla.getValueAt(tableSeleccionada, 1).toString()));
        dni.setText((tabla.getValueAt(tableSeleccionada, 2).toString()));
        correo.setText((tabla.getValueAt(tableSeleccionada, 3).toString()));
        pass.setText((tabla.getValueAt(tableSeleccionada, 4).toString()));
        rol.setSelectedItem(tabla.getValueAt(tableSeleccionada, 5));
        estado.setText((tabla.getValueAt(tableSeleccionada, 6).toString()));
    }
}
