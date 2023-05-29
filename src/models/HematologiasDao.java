package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class HematologiasDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar Hematologia
    public boolean registerHematologiaQuery(Hematologias hematologia){
        String query = "INSERT INTO hematologias (id_codigo, name, price) "
                + "VALUES (?,?,?)";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, hematologia.getId_codigo());
            pst.setString(2, hematologia.getName());
            pst.setDouble(3, hematologia.getPrecio());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el examen de hematologia");
            return false;
        }
    }
    
    //Listar examen
    public List listHematologiaQuery(String value){
        List<Hematologias> list_hematologias = new ArrayList();
        String query = "SELECT * FROM hematologias";
        String query_search_hematologia = "SELECT * FROM hematologias WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_hematologia);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Hematologias hematologia = new Hematologias();
                hematologia.setId_codigo(rs.getInt("id_codigo"));
                hematologia.setName(rs.getString("name"));
                hematologia.setPrecio(rs.getDouble("price"));
                list_hematologias.add(hematologia);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_hematologias;
    }
    
    //Modificar quimica
    public boolean updateHematologiaQuery(Hematologias hematologia) {
        String query = "UPDATE hematologias SET name = ?, price = ? "
                + "WHERE id_codigo = ?";
        
       
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, hematologia.getName());
            pst.setDouble(2, hematologia.getPrecio());
            pst.setInt(3, hematologia.getId_codigo());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el examen de hematologia" + e);
            return false;
        }
    }
    
    //Eliminar quimica
    public boolean deleteHematologiaQuery(int id){
        String query = "DELETE FROM hematologias WHERE id_codigo = " + id;
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
