package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GeneticasDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar genetica
    public boolean registerGeneticaQuery(Geneticas genetica){
        String query = "INSERT INTO geneticas (id_codigo, name, price) "
                + "VALUES (?,?,?)";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, genetica.getId_codigo());
            pst.setString(2, genetica.getName());
            pst.setDouble(3, genetica.getPrecio());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el examen de genetica" + e);
            return false;
        }
    }
    
    //Listar examen
    public List listGeneticaQuery(String value){
        List<Geneticas> list_geneticas = new ArrayList();
        String query = "SELECT * FROM geneticas";
        String query_search_genetica = "SELECT * FROM geneticas WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_genetica);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Geneticas genetica = new Geneticas();
                genetica.setId_codigo(rs.getInt("id_codigo"));
                genetica.setName(rs.getString("name"));
                genetica.setPrecio(rs.getDouble("price"));
                list_geneticas.add(genetica);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_geneticas;
    }
    
    //Modificar genetica
    public boolean updateGeneticaQuery(Geneticas genetica) {
        String query = "UPDATE geneticas SET name = ?, price = ? "
                + "WHERE id_codigo = ?";
        
       
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, genetica.getName());
            pst.setDouble(2, genetica.getPrecio());
            pst.setInt(3, genetica.getId_codigo());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el examen de geneticas" + e);
            return false;
        }
    }
    
    //Eliminar genetica
    public boolean deleteGeneticaQuery(int id){
        String query = "DELETE FROM geneticas WHERE id_codigo = " + id;
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


