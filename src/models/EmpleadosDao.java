package models;

import java.util.List;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class EmpleadosDao {

    //Instanciar la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Variables para enviar datos entre interfaces
    public static int id_user = 0;
    public static String full_name_user = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String rol_user = "";
    public static String email_user = "";
    public static String telephone_user = "";

    //Método de Login
    public Empleados loginQuery(String user, String password) {
        String query = "SELECT * FROM empleados WHERE username = ? AND password = ?";
        Empleados empleado = new Empleados();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            //Enviar parametros
            pst.setString(1, user);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()) {
                empleado.setId(rs.getInt("id"));
                id_user = empleado.getId();
                empleado.setFull_name(rs.getString("full_name"));
                full_name_user = empleado.getFull_name();
                empleado.setUsername(rs.getString("username"));
                username_user = empleado.getUsername();
                empleado.setUsername(rs.getString("address"));
                address_user = empleado.getAddress();
                empleado.setTelephone(rs.getString("telephone"));
                telephone_user = empleado.getTelephone();
                empleado.setUsername(rs.getString("email"));
                email_user = empleado.getEmail();
                empleado.setRol(rs.getString("rol"));
                rol_user = empleado.getRol();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener al empleado " + e);
        }
        return empleado;
    }

    //Registrar empleado
    public boolean registerEmpleadoQuery(Empleados empleado) {
        String query = "INSERT INTO empleados(id, full_name, username, address, telephone, email, password, rol, created,"
                + "updated) VALUES(?,?,?,?,?,?,?,?,?,?)";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, empleado.getId());
            pst.setString(2, empleado.getFull_name());
            pst.setString(3, empleado.getUsername());
            pst.setString(4, empleado.getAddress());
            pst.setString(5, empleado.getTelephone());
            pst.setString(6, empleado.getEmail());
            pst.setString(7, empleado.getPassword());
            pst.setString(8, empleado.getRol());
            pst.setTimestamp(9, datetime);
            pst.setTimestamp(10, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado" + e);
            return false;
        }
    }

    //Listar empleado
    public List listEmpleadosQuery(String value) {
        List<Empleados> list_empleados = new ArrayList();
        String query = "SELECT * FROM empleados ORDER BY rol ASC";
        String query_search_empleado = "SELECT * FROM empleados WHERE id LIKE '%" + value + "%'";
        
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_empleado);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Empleados empleado = new Empleados();
                empleado.setId(rs.getInt("id"));
                empleado.setFull_name(rs.getString("full_name"));
                empleado.setUsername(rs.getString("username"));
                empleado.setAddress(rs.getString("address"));
                empleado.setTelephone(rs.getString("telephone"));
                empleado.setEmail(rs.getString("email"));
                empleado.setRol(rs.getString("rol"));
                list_empleados.add(empleado);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_empleados;
    }

    //Modificar empleado
    public boolean updateEmpleadoQuery(Empleados empleado) {
        String query = "UPDATE empleados SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?, rol = ?, updated = ?"
                + "WHERE id = ?";

        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, empleado.getFull_name());
            pst.setString(2, empleado.getUsername());
            pst.setString(3, empleado.getAddress());
            pst.setString(4, empleado.getTelephone());
            pst.setString(5, empleado.getEmail());
            pst.setString(6, empleado.getRol());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, empleado.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del empleado" + e);
            return false;
        }
    }
    
    //Eliminar empleado
    public boolean deleteEmpleadoQuery(int id){
        String query = "DELETE FROM empleados WHERE id = " + id;
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No puede eliminar un empleado que tenga relacion con otra tabla");
            return false;
        }
    }
    
    //Cambiar la contraseña
    public boolean updateEmpleadoPassword(Empleados empleado){
        String query = "UPDATE empleados SET password = ? WHERE username = '" + username_user + "'";
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, empleado.getPassword());
            pst.executeUpdate();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la contraseña " + e);
            return false;
        }
    }
}
