package crudtrabajosdegrado;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DesarrolloInvestigacion extends TrabajoDeGrado {

    Connection con = Conect.Connect();
    PreparedStatement ps;
    ResultSet rs;
    JTextField nomProy;
    JTextField idPDF;
    JTextField idTrabajo;
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
    
    public void setIdProy(JTextField txtidProy){
        this.idProy = Integer.parseInt(txtidProy.getText());
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
                ps = con.prepareStatement("UPDATE proy_investigacion SET nombre_proyecto = ?, id_pdf = ? WHERE id = ?");
                
                ps.setString(1, nomProy.getText());
                ps.setInt(2, Integer.parseInt(idPDF.getText()));
                ps.setInt(3, idProy);
                
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
    
    public void Limpiar(){
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
}
