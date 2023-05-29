package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Empleados;
import models.EmpleadosDao;
import static models.EmpleadosDao.id_user;
import static models.EmpleadosDao.rol_user;
import views.SystemView;

public class EmpleadosController implements ActionListener, MouseListener, KeyListener {

    private Empleados empleado;
    private EmpleadosDao empleadoDao;
    private SystemView views;
    //Rol 
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public EmpleadosController(Empleados empleado, EmpleadosDao empleadoDao, SystemView views) {
        this.empleado = empleado;
        this.empleadoDao = empleadoDao;
        this.views = views;
        //Boton de registrar empleado
        this.views.btn_register_employee.addActionListener(this);
        //Boton de modificar empleados
        this.views.btn_update_employee.addActionListener(this);
        //Boton eliminar empleado
        this.views.btn_delete_employee.addActionListener(this);
        //Boton de cancelar
        this.views.btn_cancel_employee.addActionListener(this);
        this.views.jLabelEmpleados.addMouseListener(this);
        this.views.txt_search_empleado.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_employee) {
            //Verificar si los campos estan vacios
            if (views.txt_empleado_id.getText().equals("")
                    || views.txt_empleado_fullname.getText().equals("")
                    || views.txt_empleado_username.getText().equals("")
                    || views.txt_empleado_address.getText().equals("")
                    || views.txt_empleado_telephone.getText().equals("")
                    || views.txt_empleado_email.getText().equals("")
                    || views.cmb_rol.getSelectedItem().toString().equals("")
                    || String.valueOf(views.txt_empleado_password.getPassword()).equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                //Realizar la inserción
                empleado.setId(Integer.parseInt(views.txt_empleado_id.getText().trim()));
                empleado.setFull_name(views.txt_empleado_fullname.getText().trim());
                empleado.setUsername(views.txt_empleado_username.getText().trim());
                empleado.setAddress(views.txt_empleado_address.getText().trim());
                empleado.setTelephone(views.txt_empleado_telephone.getText().trim());
                empleado.setEmail(views.txt_empleado_email.getText().trim());
                empleado.setPassword(String.valueOf(views.txt_empleado_password.getPassword()));
                empleado.setRol(views.cmb_rol.getSelectedItem().toString());

                if (empleadoDao.registerEmpleadoQuery(empleado)) {
                    cleanTable();
                    cleanFields();
                    listAllEmpleados();
                    JOptionPane.showMessageDialog(null, "Empleado registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al empleado");
                }

            }
        } else if (e.getSource() == views.btn_update_employee) {
            if (views.txt_empleado_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                //Verificar si los campos estan vacios
                if (views.txt_empleado_id.getText().equals("")
                        || views.txt_empleado_fullname.getText().equals("")
                        || views.cmb_rol.getSelectedItem().toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    empleado.setId(Integer.parseInt(views.txt_empleado_id.getText().trim()));
                    empleado.setFull_name(views.txt_empleado_fullname.getText().trim());
                    empleado.setUsername(views.txt_empleado_username.getText().trim());
                    empleado.setAddress(views.txt_empleado_address.getText().trim());
                    empleado.setTelephone(views.txt_empleado_telephone.getText().trim());
                    empleado.setEmail(views.txt_empleado_email.getText().trim());
                    empleado.setPassword(String.valueOf(views.txt_empleado_password.getPassword()));
                    empleado.setRol(views.cmb_rol.getSelectedItem().toString());
                    
                    if(empleadoDao.updateEmpleadoQuery(empleado)){
                        cleanTable();
                        cleanFields();
                        listAllEmpleados();
                        views.btn_register_employee.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos modificados con éxito");
                    }else{
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al empleado");
                    }
                }
            }
        }else if(e.getSource() == views.btn_delete_employee){
            int row = views.empleados_table.getSelectedRow();
            
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Debes seleccionar un empleado para eliminar");
            }else if(views.empleados_table.getValueAt(row, 0).equals(id_user)){
                JOptionPane.showMessageDialog(null, "No puede eliminar al usuario autenticado");
            }else{
                int id = Integer.parseInt(views.empleados_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null,"¿En realidad quieres eliminar a este empleado?");
                
                if(question == 0 && empleadoDao.deleteEmpleadoQuery(id) != false){
                    cleanTable();
                    cleanFields();                    
                    views.btn_register_employee.setEnabled(true);
                    views.txt_empleado_password.setEnabled(true);
                    listAllEmpleados();
                    JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito");
                }
            }
        }else if(e.getSource() == views.btn_cancel_employee){
            cleanFields();
            views.btn_register_employee.setEnabled(true);
            views.txt_empleado_password.setEnabled(true);
            views.txt_empleado_id.setEnabled(true);            
        }
    }

    //Listar todos los empleados 
    public void listAllEmpleados() {
        if (rol.equals("Administrador")) {
            List<Empleados> list = empleadoDao.listEmpleadosQuery(views.txt_search_empleado.getText());
            model = (DefaultTableModel) views.empleados_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFull_name();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();
                model.addRow(row);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.empleados_table) {
            int row = views.empleados_table.rowAtPoint(e.getPoint());

            views.txt_empleado_id.setText(views.empleados_table.getValueAt(row, 0).toString());
            views.txt_empleado_fullname.setText(views.empleados_table.getValueAt(row, 1).toString());
            views.txt_empleado_username.setText(views.empleados_table.getValueAt(row, 2).toString());
            views.txt_empleado_address.setText(views.empleados_table.getValueAt(row, 3).toString());
            views.txt_empleado_telephone.setText(views.empleados_table.getValueAt(row, 4).toString());
            views.txt_empleado_email.setText(views.empleados_table.getValueAt(row, 5).toString());
            views.cmb_rol.setSelectedItem(views.empleados_table.getValueAt(row, 6).toString());

            //Deshabilitar
            views.txt_empleado_id.setEditable(false);
            views.txt_empleado_password.setEnabled(false);
            views.btn_register_employee.setEnabled(false);
            
        }else if(e.getSource() == views.jLabelEmpleados){
            views.jTabbedPane1.setSelectedIndex(6);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar empleados
            listAllEmpleados();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_empleado) {
            cleanTable();
            listAllEmpleados();
        }
    }
    //Limpiar campos
    public void cleanFields(){
        views.txt_empleado_id.setText("");
        views.txt_empleado_id.setEditable(true);
        views.txt_empleado_fullname.setText("");
        views.txt_empleado_username.setText("");
        views.txt_empleado_address.setText("");
        views.txt_empleado_telephone.setText("");
        views.txt_empleado_email.setText("");
        views.txt_empleado_password.setText("");
        views.cmb_rol.setSelectedIndex(0);
        
        
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

}
