package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MicrobiologiasDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar microbiologia
    public boolean registerMicrobiologiaQuery(Microbiologias microbiologia){
        String query = "INSERT INTO microbiologias (id_codigo, name, price) "
                + "VALUES (?,?,?)";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, microbiologia.getId_codigo());
            pst.setString(2, microbiologia.getName());
            pst.setDouble(3, microbiologia.getPrecio());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el examen de microbiologia" + e);
            return false;
        }
    }
    
    //Listar examen
    public List listMicrobiologiaQuery(String value){
        List<Microbiologias> list_microbiologias = new ArrayList();
        String query = "SELECT * FROM microbiologias";
        String query_search_microbiologia = "SELECT * FROM microbiologias WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_microbiologia);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Microbiologias microbiologia = new Microbiologias();
                microbiologia.setId_codigo(rs.getInt("id_codigo"));
                microbiologia.setName(rs.getString("name"));
                microbiologia.setPrecio(rs.getDouble("price"));
                list_microbiologias.add(microbiologia);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_microbiologias;
    }
    
    //Modificar microbiologia
    public boolean updateMicrobiologiaQuery(Microbiologias microbiologia) {
        String query = "UPDATE microbiologias SET name = ?, price = ? "
                + "WHERE id_codigo = ?";
        
       
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, microbiologia.getName());
            pst.setDouble(2, microbiologia.getPrecio());
            pst.setInt(3, microbiologia.getId_codigo());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el examen de microbiologia" + e);
            return false;
        }
    }
    
    //Eliminar microbiologia
    public boolean deleteMicrobiologiaQuery(int id){
        String query = "DELETE FROM microbiologias WHERE id_codigo = " + id;
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No puede eliminar un examen que tenga relacion con otra tabla" + e);
            return false;
        }
    }
}


