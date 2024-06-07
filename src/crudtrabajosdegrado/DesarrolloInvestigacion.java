package crudtrabajosdegrado;

import java.awt.HeadlessException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DesarrolloInvestigacion extends TrabajoDeGrado {

    Connection con = Conect.Connect();
    PreparedStatement ps;
    ResultSet rs;
    JTextField nomProy;
    JTextField idPDF;
    JTextField idTrabajo;
    JTextField txtidProy;
    JComboBox estudiante1;
    JComboBox estudiante2;
    JComboBox estudiante3;
    JComboBox docente;
    JComboBox codirector;
    JComboBox tipoProy;

    int idProy;

    public void setNom(JTextField nomProy) {
        this.nomProy = nomProy;
    }

    public void setIdPDF(JTextField idPDF) {
        this.idPDF = idPDF;
    }

    public void setIdProy(JTextField txtidProy) {
        this.txtidProy = txtidProy;
    }

    public void setIdTrabajo(JTextField idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public void setCombos(JComboBox estudiante1, JComboBox estudiante2, JComboBox estudiante3, JComboBox docente, JComboBox codirector, JComboBox tipoProy) {
        this.estudiante1 = estudiante1;
        this.estudiante2 = estudiante2;
        this.estudiante3 = estudiante3;
        this.docente = docente;
        this.codirector = codirector;
        this.tipoProy = tipoProy;
    }

    @Override
    public void Registrar() {
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

                //Obtención de ID de los diferentes usuarios seleccionados
                String IDest1 = "", IDest2 = "", IDest3 = "", IDdoc = "", IDcodir = "";

                ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                ps.setString(1, estudiante1.getSelectedItem().toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    IDest1 = rs.getString("id_usuario");
                }

                ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                ps.setString(1, estudiante2.getSelectedItem().toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    IDest2 = rs.getString("id_usuario");
                }

                ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                ps.setString(1, estudiante3.getSelectedItem().toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    IDest3 = rs.getString("id_usuario");
                }

                ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                ps.setString(1, docente.getSelectedItem().toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    IDdoc = rs.getString("id_usuario");
                }

                ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                ps.setString(1, codirector.getSelectedItem().toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    IDcodir = rs.getString("id_usuario");
                }

                try {
                    ps = con.prepareStatement("INSERT INTO trabajo_de_grado(id_estudiante1, id_estudiante2, id_estudiante3, id_docente, id_codirector, fecha_propuest, tipo_trabajo, estado_trabajo, id_proyecto_investigacion, estado) VALUES (?,?,?,?,?,CURRENT_DATE,?,1,?,1)");

                    ps.setInt(1, Integer.parseInt(IDest1));
                    ps.setInt(2, Integer.parseInt(IDest2));
                    ps.setInt(3, Integer.parseInt(IDest3));
                    ps.setInt(4, Integer.parseInt(IDdoc));
                    ps.setInt(5, Integer.parseInt(IDcodir));
                    ps.setInt(6, tipoProy.getSelectedIndex() + 1);
                    ps.setInt(7, idProy);

                    val = ps.executeUpdate();

                    if (val > 0) {
                        JOptionPane.showMessageDialog(null, "Proyecto subido exitosamente \nDebe Proyecto en revisión...");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido subir el proyecto, revise las entradas");
                    }
                } catch (HeadlessException | NumberFormatException | SQLException e) {
                    System.err.println(e);
                }

            } catch (NumberFormatException | SQLException e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public void Modificar() {
        int yes = JOptionPane.showConfirmDialog(null, "¿Está seguro de modificar este proyecto? \nLa unico que se puede modificar es el archivo PDF y el nombre del proyecto");
        if (yes == JOptionPane.YES_OPTION) {
            try {
                ps.setString(1, nomProy.getText());
                ps.setInt(2, Integer.parseInt(idPDF.getText()));
                ps.setInt(3, idProy);
                ps.setInt(4, Integer.parseInt(idTrabajo.getText()));

                int val = ps.executeUpdate();

                if (val > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha actualizado la informacion");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido actualizar");
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public void Eliminar() {
        int yes = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta trabajo de grado?");
        if (yes == JOptionPane.YES_OPTION) {
            try {
                ps = con.prepareStatement("UPDATE trabajo_de_grado SET estado = 0 WHERE id = ?");

                ps.setInt(1, Integer.parseInt(idTrabajo.getText()));

                int val = ps.executeUpdate();
                if (val > 0) {
                    JOptionPane.showMessageDialog(null, "Eliminado Exitosamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar");
                }
            } catch (HeadlessException | NumberFormatException | SQLException e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public void Devolver() {

    }

    public void Limpiar() {
        nomProy.setText(null);
        estudiante1.setSelectedIndex(0);
        estudiante2.setSelectedIndex(0);
        estudiante3.setSelectedIndex(0);
        docente.setSelectedIndex(0);
        codirector.setSelectedIndex(0);
    }

    public void TipoProyecto(JComboBox tipoProyecto) {
        try {
            ps = con.prepareStatement("SELECT * FROM tipo_trabajo_de_grado WHERE id < 3");

            rs = ps.executeQuery();

            tipoProyecto.removeAllItems();

            while (rs.next()) {
                String tipoProyec = rs.getString("nombre_tipoTrab");
                tipoProyecto.addItem(tipoProyec);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void LlenarTabla(JTable tabla) {

        String sql = """
                     SELECT tg.id, u1.nombre_usuario, u2.nombre_usuario, u3.nombre_usuario, u4.nombre_usuario, u5.nombre_usuario, fecha_propuest, fecha_modificacion, fecha_aceptacion, tt.nombre_tipoTrab, ep.nom_estado, pi.nombre_proyecto, tg.estado
                     FROM trabajo_de_grado tg LEFT JOIN usuarios u1 ON tg.id_estudiante1 = u1.id_usuario
                      LEFT JOIN usuarios u2 ON tg.id_estudiante2 = u2.id_usuario
                      LEFT JOIN usuarios u3 ON tg.id_estudiante3 = u3.id_usuario
                      LEFT JOIN usuarios u4 ON tg.id_docente = u4.id_usuario
                      LEFT JOIN usuarios u5 ON tg.id_codirector = u5.id_usuario
                      LEFT JOIN tipo_trabajo_de_grado tt ON tg.tipo_trabajo = tt.id
                      LEFT JOIN estado_proyecto ep ON tg.estado_trabajo = ep.id
                      LEFT JOIN proy_investigacion pi ON tg.id_proyecto_investigacion = pi.id
                      WHERE tg.estado > 0""";
        try {
            ps = con.prepareStatement(sql);
            
            rs = ps.executeQuery();
            
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0); // Limpia la tabla

            String[] myTabla;
            myTabla = new String[13];
            
            while (rs.next()) {
                myTabla[0] = Integer.toString(rs.getInt("tg.id"));
                myTabla[1] = rs.getString("u1.nombre_usuario");
                myTabla[2] = rs.getString("u2.nombre_usuario");
                myTabla[3] = rs.getString("u3.nombre_usuario");
                myTabla[4] = rs.getString("u4.nombre_usuario");
                myTabla[5] = rs.getString("u5.nombre_usuario");
                myTabla[6] = rs.getString("tg.fecha_propuest");
                myTabla[7] = rs.getString("tg.fecha_modificacion");
                myTabla[8] = rs.getString("tg.fecha_aceptacion");
                myTabla[9] = rs.getString("tt.nombre_tipoTrab");
                myTabla[10] = rs.getString("ep.nom_estado");
                myTabla[11] = rs.getString("pi.nombre_proyecto");
                myTabla[12] = rs.getString("tg.estado");
                modelo.addRow(myTabla);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void SeleccionarFilas(JTable tabla){
        int tableSeleccionada = tabla.getSelectedRow();
        
        txtidProy.setText(tabla.getValueAt(tableSeleccionada, 0).toString());
        estudiante1.setSelectedItem(tabla.getValueAt(tableSeleccionada, 1));
        estudiante2.setSelectedItem(tabla.getValueAt(tableSeleccionada, 2));
        estudiante2.setSelectedItem(tabla.getValueAt(tableSeleccionada, 3));
        docente.setSelectedItem(tabla.getValueAt(tableSeleccionada, 4));
        codirector.setSelectedItem(tabla.getValueAt(tableSeleccionada, 5));
        tipoProy.setSelectedItem(tabla.getValueAt(tableSeleccionada, 9));
        nomProy.setText(tabla.getValueAt(tableSeleccionada, 11).toString());
        
    }
}
