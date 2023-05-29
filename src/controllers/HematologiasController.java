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
import static models.EmpleadosDao.rol_user;
import models.Hematologias;
import models.HematologiasDao;
import views.SystemView;


public class HematologiasController implements ActionListener, MouseListener, KeyListener {
    
    private Hematologias hematologia;
    private HematologiasDao hematologiaDao;
    private SystemView views;
    
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public HematologiasController(Hematologias hematologia, HematologiasDao hematologiaDao, SystemView views) {
        this.hematologia = hematologia;
        this.hematologiaDao = hematologiaDao;
        this.views = views;
        //Boton de registrar hematologia
        this.views.btn_register_hematologia.addActionListener(this);        
        //Boton de modificar hematologia
        this.views.btn_update_hematologia.addActionListener(this);
        //Boton eliminar cliente
        this.views.btn_delete_hematologia.addActionListener(this);
        //Boton cancelar
        this.views.btn_cancel_hematologia.addActionListener(this);
        //Buscador
        this.views.jLabelHematologia.addMouseListener(this);
        this.views.hematologias_table.addMouseListener(this);
        this.views.txt_search_hematologia.addKeyListener(this);        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_hematologia) {
            //Verificar si los campos estan vacios
            if (views.txt_hematologia_code.getText().equals("")
                    || views.txt_hematologia_name.getText().equals("")
                    || views.txt_hematologia_price.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                hematologia.setId_codigo(Integer.parseInt(views.txt_hematologia_code.getText().trim()));
                hematologia.setName(views.txt_hematologia_name.getText().trim());
                hematologia.setPrecio(Double.parseDouble(views.txt_hematologia_price.getText()));

                if (hematologiaDao.registerHematologiaQuery(hematologia)) {
                    cleanTable();
                    cleanFields();
                    listAllHematologias();
                    JOptionPane.showMessageDialog(null, "Exámen registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error  al registrar examen");
                }
            }
        } else if (e.getSource() == views.btn_update_hematologia) {
            if (views.txt_hematologia_code.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_hematologia_code.getText().equals("")
                        || views.txt_hematologia_name.getText().equals("")
                        || views.txt_hematologia_price.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    hematologia.setId_codigo(Integer.parseInt(views.txt_hematologia_code.getText().trim()));
                    hematologia.setName(views.txt_hematologia_name.getText().trim());
                    hematologia.setPrecio(Double.parseDouble(views.txt_hematologia_price.getText()));

                    if (hematologiaDao.updateHematologiaQuery(hematologia)) {
                        cleanTable();
                        cleanFields();
                        listAllHematologias();
                        JOptionPane.showMessageDialog(null, "Examen modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el examen" + e);
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_hematologia) {
            int row = views.hematologias_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un examen para eliminar");
            } else {
                int id = Integer.parseInt(views.hematologias_table.getValueAt(row, 1).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar  este examen?");

                if (question == 0 && hematologiaDao.deleteHematologiaQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_hematologia.setEnabled(true);
                    listAllHematologias();
                    JOptionPane.showMessageDialog(null, "Examen eliminado con éxito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_hematologia) {
            views.btn_register_hematologia.setEnabled(true);
            cleanFields();
        }
    }
    
    //Listar Hematologia
    public void listAllHematologias() {
        if (rol.equals("Administrador")) {
            List<Hematologias> list = hematologiaDao.listHematologiaQuery(views.txt_search_hematologia.getText());
            model = (DefaultTableModel) views.hematologias_table.getModel();

            Object[] row = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_codigo();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getPrecio();
                model.addRow(row);
            }
            views.hematologias_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.hematologias_table) {
            int row = views.hematologias_table.rowAtPoint(e.getPoint());
            views.txt_hematologia_code.setText(views.hematologias_table.getValueAt(row, 0).toString());
            views.txt_hematologia_name.setText(views.hematologias_table.getValueAt(row, 1).toString());
            views.txt_hematologia_price.setText(views.hematologias_table.getValueAt(row, 2).toString());
            //Deshabilitar  botones
            views.btn_register_hematologia.setEnabled(false);
            views.txt_hematologia_code.setEditable(false);
            

        } else if (e.getSource() == views.jLabelHematologia) {
            views.jTabbedPane1.setSelectedIndex(1);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar clientes
            listAllHematologias();
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
       if (e.getSource() == views.txt_search_hematologia) {
            //Limpiar tabla
            cleanTable();
            //Listar cliente
            listAllHematologias();
        } 
    }
    
    public void cleanFields() {
        views.txt_hematologia_code.setText("");
        views.txt_hematologia_code.setEditable(true);
        views.txt_hematologia_name.setText("");
        views.txt_hematologia_price.setText("");
    }
    
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    
}
