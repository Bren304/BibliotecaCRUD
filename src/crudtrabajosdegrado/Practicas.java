package crudtrabajosdegrado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Practicas extends TrabajoDeGrado {

    Connection con = Conect.Connect();
    PreparedStatement ps;
    ResultSet rs;

    JComboBox estudiante;
    JComboBox docente;
    JComboBox codirector;
    JTextField txtNomEmpresa;
    JTextField txtDirEmpresa;
    JTextField txtTelEmpresa;
    JTextField txtNomDelegado;
    JTextField txtIDArc;
    JTextField txtIDPrac;

    public void setInfo(JComboBox estudiante, JComboBox docente, JComboBox codirector, JTextField txtNomEmpresa, JTextField txtDirEmpresa, JTextField txtTelEmpresa, JTextField txtNomDelegado) {
        this.estudiante = estudiante;
        this.docente = docente;
        this.codirector = codirector;
        this.txtNomEmpresa = txtNomEmpresa;
        this.txtDirEmpresa = txtDirEmpresa;
        this.txtTelEmpresa = txtTelEmpresa;
        this.txtNomDelegado = txtNomDelegado;
    }

    public void setTxtIDPract(JTextField txtIDPrac) {
        this.txtIDPrac = txtIDPrac;
    }

    public void setTxtIDArc(JTextField txtIDArc) {
        this.txtIDArc = txtIDArc;
    }

    @Override
    public void Registrar() {
        try {
            ps = con.prepareStatement("INSERT INTO practica (nombre_empresa, direccion_empresa, numero_empresa, nombre_delegado, id_pdf) VALUES (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, txtNomEmpresa.getText());
            ps.setString(2, txtDirEmpresa.getText());
            ps.setString(3, txtTelEmpresa.getText());
            ps.setString(4, txtNomDelegado.getText());
            ps.setInt(5, Integer.parseInt(txtIDArc.getText()));

            int val = ps.executeUpdate();

            if (val > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    txtIDPrac.setText(Integer.toString(rs.getInt(1)));

                    String idEstudiante = "", idDocente = "", idCodirector = "";

                    ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                    ps.setString(1, this.estudiante.getSelectedItem().toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        idEstudiante = rs.getString("id_usuario");
                    }
                    
                    ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                    ps.setString(1, this.docente.getSelectedItem().toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        idDocente = rs.getString("id_usuario");
                    }
                    
                    ps = con.prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                    ps.setString(1, this.codirector.getSelectedItem().toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        idCodirector = rs.getString("id_usuario");
                    }

                    ps = con.prepareStatement("INSERT INTO trabajo_de_grado (id_estudiante1, id_docente, id_codirector, fecha_propuest, tipo_trabajo, estado_trabajo, id_practica, estado) VALUES (?,?,?,CURRENT_DATE,3,1,?,1)");
                    
                    ps.setInt(1, Integer.parseInt(idEstudiante));
                    ps.setInt(2, Integer.parseInt(idDocente));
                    ps.setInt(3, Integer.parseInt(idCodirector));
                    ps.setInt(4, Integer.parseInt(txtIDPrac.getText()));
                }
            }
        } catch (NumberFormatException | SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void Modificar() {

    }

    @Override
    public void Eliminar(){
        
    }

    public void Limpiar() {
        estudiante.setSelectedIndex(0);
        docente.setSelectedIndex(0);
        codirector.setSelectedIndex(0);
        txtNomEmpresa.setText(null);
        txtDirEmpresa.setText(null);
        txtTelEmpresa.setText(null);
        txtNomDelegado.setText(null);
        txtIDArc.setText(null);
    }
}
