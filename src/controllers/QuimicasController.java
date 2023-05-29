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
import models.EmpleadosDao;
import static models.EmpleadosDao.rol_user;
import models.Quimicas;
import models.QuimicasDao;
import views.SystemView;

public class QuimicasController implements ActionListener, MouseListener, KeyListener {

    private Quimicas quimica;
    private QuimicasDao quimicaDao;
    private SystemView views;
    //Rol
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public QuimicasController(Quimicas quimica, QuimicasDao quimicaDao, SystemView views) {
        this.quimica = quimica;
        this.quimicaDao = quimicaDao;
        this.views = views;
        //Boton de registrar quimica
        this.views.btn_register_quimica.addActionListener(this);        
        //Boton de modificar quimica
        this.views.btn_update_quimica.addActionListener(this);
        //Boton eliminar cliente
        this.views.btn_delete_quimica.addActionListener(this);
        //Boton cancelar
        this.views.btn_cancel_quimica.addActionListener(this);
        //Buscador
        this.views.jLabelQuimica.addMouseListener(this);
        this.views.quimicas_table.addMouseListener(this);
        this.views.txt_search_quimica.addKeyListener(this);
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_quimica) {
            //Verificar si los campos estan vacios
            if (views.txt_quimica_code.getText().equals("")
                    || views.txt_quimica_name.getText().equals("")
                    || views.txt_quimica_price.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                quimica.setId_codigo(Integer.parseInt(views.txt_quimica_code.getText().trim()));
                quimica.setName(views.txt_quimica_name.getText().trim());
                quimica.setPrecio(Double.parseDouble(views.txt_quimica_price.getText()));

                if (quimicaDao.registerQuimicaQuery(quimica)) {
                    cleanTable();
                    cleanFields();
                    listAllQuimicas();
                    JOptionPane.showMessageDialog(null, "Exámen registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error  al registrar examen");
                }
            }
        } else if (e.getSource() == views.btn_update_quimica) {
            if (views.txt_quimica_code.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_quimica_code.getText().equals("")
                        || views.txt_quimica_name.getText().equals("")
                        || views.txt_quimica_price.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    quimica.setId_codigo(Integer.parseInt(views.txt_quimica_code.getText().trim()));
                    quimica.setName(views.txt_quimica_name.getText().trim());
                    quimica.setPrecio(Double.parseDouble(views.txt_quimica_price.getText()));

                    if (quimicaDao.updateQuimicaQuery(quimica)) {
                        cleanTable();
                        cleanFields();
                        listAllQuimicas();
                        JOptionPane.showMessageDialog(null, "Examen modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el examen" + e);
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_quimica) {
            int row = views.quimicas_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un examen para eliminar");
            } else {
                int id = Integer.parseInt(views.quimicas_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar  este examen?");

                if (question == 0 && quimicaDao.deleteQuimicaQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_quimica.setEnabled(true);
                    listAllQuimicas();
                    JOptionPane.showMessageDialog(null, "Examen eliminado con éxito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_quimica) {
            views.btn_register_quimica.setEnabled(true);
            cleanFields();
        }
    }

    //Listar Quimicas
    public void listAllQuimicas() {
        if (rol.equals("Administrador")) {
            List<Quimicas> list = quimicaDao.listQuimicaQuery(views.txt_search_quimica.getText());
            model = (DefaultTableModel) views.quimicas_table.getModel();

            Object[] row = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_codigo();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getPrecio();
                model.addRow(row);
            }
            views.quimicas_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.quimicas_table) {
            int row = views.quimicas_table.rowAtPoint(e.getPoint());
            views.txt_quimica_code.setText(views.quimicas_table.getValueAt(row, 0).toString());
            views.txt_quimica_name.setText(views.quimicas_table.getValueAt(row, 1).toString());
            views.txt_quimica_price.setText(views.quimicas_table.getValueAt(row, 2).toString());
            //Deshabilitar  botones
            views.btn_register_quimica.setEnabled(false);
            views.txt_quimica_code.setEditable(false);
            

        } else if (e.getSource() == views.jLabelQuimica) {
            views.jTabbedPane1.setSelectedIndex(0);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar clientes
            listAllQuimicas();
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
        if (e.getSource() == views.txt_search_quimica) {
            //Limpiar tabla
            cleanTable();
            //Listar cliente
            listAllQuimicas();
        }
    }

    public void cleanFields() {
        views.txt_quimica_code.setText("");
        views.txt_quimica_code.setEditable(true);
        views.txt_quimica_name.setText("");
        views.txt_quimica_price.setText("");
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

}
