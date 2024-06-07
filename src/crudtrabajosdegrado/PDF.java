package crudtrabajosdegrado;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PDF {

    Connection con = Conect.Connect();
    PreparedStatement ps;
    ResultSet rs;
    int id;

    public void SubirArchivo(File archivoPDF) {
        try {
            FileInputStream subArc = new FileInputStream(archivoPDF);

            ps = con.prepareStatement("INSERT INTO pdf (nombre, archivo, estado) VALUES (?,?,1)", PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, archivoPDF.getName());
            ps.setBinaryStream(2, subArc, archivoPDF.length());

            int val = ps.executeUpdate();
            System.out.println(val);

            if (val > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, "Archivo subido exitosamente. ID: " + id);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha subido el archivo");
            }
        } catch (HeadlessException | FileNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ModificarArchivo(File archivoPDF, JTextField id) {
        try {
            FileInputStream subArc = new FileInputStream(archivoPDF);

            ps = con.prepareStatement("UPDATE pdf SET nombre = ?, archivo = ? WHERE id = ?");

            ps.setString(1, archivoPDF.getName());
            ps.setBlob(2, subArc);
            ps.setInt(3, Integer.parseInt(id.getText()));

            int val = ps.executeUpdate();

            if (val > 0) {
                JOptionPane.showMessageDialog(null, "Archivo modificado");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar el archivo");
            }
        } catch (HeadlessException | FileNotFoundException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void EliminarArchivo(JTextField id) {
        try {
            ps = con.prepareStatement("UPDATE pdf SET estado = 0 WHERE id = ?");

            ps.setInt(1, Integer.parseInt(id.getText()));

            int val = ps.executeUpdate();

            if (val > 0) {
                JOptionPane.showMessageDialog(null, "Se ha eliminado el archivo exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido eliminar el archivo");
            }
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            System.err.println(e);
        }
    }
}
