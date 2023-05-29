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

public class LiquidosDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar Liquidos
    public boolean registerLiquidosQuery(Liquidos liquido){
        String query = "INSERT INTO liquidos (id_codigo, name, price) "
                + "VALUES (?,?,?)";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, liquido.getId_codigo());
            pst.setString(2, liquido.getName());
            pst.setDouble(3, liquido.getPrecio());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el examen de liquidos");
            return false;
        }
    }
    
    //Listar examen
    public List listLiquidoQuery(String value){
        List<Liquidos> list_liquidos = new ArrayList();
        String query = "SELECT * FROM liquidos";
        String query_search_liquido = "SELECT * FROM liquidos WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_liquido);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Liquidos liquido = new Liquidos();
                liquido.setId_codigo(rs.getInt("id_codigo"));
                liquido.setName(rs.getString("name"));
                liquido.setPrecio(rs.getDouble("price"));
                list_liquidos.add(liquido);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_liquidos;
    }
    
    //Modificar quimica
    public boolean updateLiquidoQuery(Liquidos liquido) {
        String query = "UPDATE liquidos SET name = ?, price = ? "
                + "WHERE id_codigo = ?";
        
       
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, liquido.getName());
            pst.setDouble(2, liquido.getPrecio());
            pst.setInt(3, liquido.getId_codigo());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el examen de liquidos" + e);
            return false;
        }
    }
    
    //Eliminar liquidos
    public boolean deleteLiquidoQuery(int id){
        String query = "DELETE FROM liquidos WHERE id_codigo = " + id;
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
