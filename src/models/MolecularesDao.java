package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MolecularesDao {
    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    //Registrar Molecular
    public boolean registerMolecularQuery(Moleculares molecular){
        String query = "INSERT INTO moleculares (id_codigo, name, price) "
                + "VALUES (?,?,?)";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, molecular.getId_codigo());
            pst.setString(2, molecular.getName());
            pst.setDouble(3, molecular.getPrecio());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el examen de molecular" + e);
            return false;
        }
    }
    
    //Listar examen
    public List listMolecularQuery(String value){
        List<Moleculares> list_moleculares = new ArrayList();
        String query = "SELECT * FROM moleculares";
        String query_search_molecular = "SELECT * FROM moleculares WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_molecular);
                rs = pst.executeQuery();
            }
            
            while(rs.next()){
                Moleculares molecular = new Moleculares();
                molecular.setId_codigo(rs.getInt("id_codigo"));
                molecular.setName(rs.getString("name"));
                molecular.setPrecio(rs.getDouble("price"));
                list_moleculares.add(molecular);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_moleculares;
    }
    
    //Modificar molecular
    public boolean updateMolecularQuery(Moleculares molecular) {
        String query = "UPDATE moleculares SET name = ?, price = ? "
                + "WHERE id_codigo = ?";
        
       
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, molecular.getName());
            pst.setDouble(2, molecular.getPrecio());
            pst.setInt(3, molecular.getId_codigo());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el examen de molecular" + e);
            return false;
        }
    }
    
    //Eliminar molecular
    public boolean deleteMolecularQuery(int id){
        String query = "DELETE FROM moleculares WHERE id_codigo = " + id;
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


