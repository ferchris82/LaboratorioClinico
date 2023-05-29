package models;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class QuimicasDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar quimica
    public boolean registerQuimicaQuery(Quimicas quimica){
        String query = "INSERT INTO quimicas (id_codigo, name, price) "
                + "VALUES (?,?,?)";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, quimica.getId_codigo());
            pst.setString(2, quimica.getName());
            pst.setDouble(3, quimica.getPrecio());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el examen de quimica");
            return false;
        }
    }
    
    //Listar examen
    public List listQuimicaQuery(String value){
        List<Quimicas> list_quimicas = new ArrayList();
        String query = "SELECT * FROM quimicas";
        String query_search_quimica = "SELECT * FROM quimicas WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_quimica);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Quimicas quimica = new Quimicas();
                quimica.setId_codigo(rs.getInt("id_codigo"));
                quimica.setName(rs.getString("name"));
                quimica.setPrecio(rs.getDouble("price"));
                list_quimicas.add(quimica);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_quimicas;
    }
    
    //Modificar quimica
    public boolean updateQuimicaQuery(Quimicas quimica) {
        String query = "UPDATE quimicas SET name = ?, price = ? "
                + "WHERE id_codigo = ?";
        
       
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, quimica.getName());
            pst.setDouble(2, quimica.getPrecio());
            pst.setInt(3, quimica.getId_codigo());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el examen de quimica" + e);
            return false;
        }
    }
    
    //Eliminar quimica
    public boolean deleteQuimicaQuery(int id){
        String query = "DELETE FROM quimicas WHERE id_codigo = " + id;
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
