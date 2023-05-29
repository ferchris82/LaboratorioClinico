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
import models.Microbiologias;
import models.MicrobiologiasDao;
import views.SystemView;

public class MicrobiologiasController implements ActionListener, MouseListener, KeyListener{
    
    private Microbiologias microbiologia;
    private MicrobiologiasDao microbiologiaDao;
    private SystemView views;
    
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public MicrobiologiasController(Microbiologias microbiologia, MicrobiologiasDao microbiologiaDao, SystemView views) {
        this.microbiologia = microbiologia;
        this.microbiologiaDao = microbiologiaDao;
        this.views = views;
        //Boton de registrar microbiologia
        this.views.btn_register_microbiologia.addActionListener(this);        
        //Boton de modificar microbiologia
        this.views.btn_update_microbiologia.addActionListener(this);
        //Boton eliminar cliente
        this.views.btn_delete_microbiologia.addActionListener(this);
        //Boton cancelar
        this.views.btn_cancel_microbiologia.addActionListener(this);
        //Buscador
        this.views.jLabelMicrobiologia.addMouseListener(this);
        this.views.microbiologias_table.addMouseListener(this);
        this.views.txt_search_microbiologias.addKeyListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_microbiologia) {
            //Verificar si los campos estan vacios
            if (views.txt_microbiologia_code.getText().equals("")
                    || views.txt_microbiologia_name.getText().equals("")
                    || views.txt_microbiologia_price.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                microbiologia.setId_codigo(Integer.parseInt(views.txt_microbiologia_code.getText().trim()));
                microbiologia.setName(views.txt_microbiologia_name.getText().trim());
                microbiologia.setPrecio(Double.parseDouble(views.txt_microbiologia_price.getText()));

                if (microbiologiaDao.registerMicrobiologiaQuery(microbiologia)) {
                    cleanTable();
                    cleanFields();
                    listAllMicrobiologias();
                    JOptionPane.showMessageDialog(null, "Exámen registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error  al registrar examen");
                }
            }
        } else if (e.getSource() == views.btn_update_microbiologia) {
            if (views.txt_microbiologia_code.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_microbiologia_code.getText().equals("")
                        || views.txt_microbiologia_name.getText().equals("")
                        || views.txt_microbiologia_price.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    microbiologia.setId_codigo(Integer.parseInt(views.txt_microbiologia_code.getText().trim()));
                    microbiologia.setName(views.txt_microbiologia_name.getText().trim());
                    microbiologia.setPrecio(Double.parseDouble(views.txt_microbiologia_price.getText()));

                    if (microbiologiaDao.updateMicrobiologiaQuery(microbiologia)) {
                        cleanTable();
                        cleanFields();
                        listAllMicrobiologias();
                        JOptionPane.showMessageDialog(null, "Examen modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el examen" + e);
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_microbiologia) {
            int row = views.microbiologias_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un examen para eliminar");
            } else {
                int id = Integer.parseInt(views.microbiologias_table.getValueAt(row, 1).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar este examen?");

                if (question == 0 && microbiologiaDao.deleteMicrobiologiaQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_microbiologia.setEnabled(true);
                    listAllMicrobiologias();
                    JOptionPane.showMessageDialog(null, "Examen eliminado con éxito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_microbiologia) {
            views.btn_register_microbiologia.setEnabled(true);
            cleanFields();
        }
    }
    
    //Listar Hematologia
    public void listAllMicrobiologias() {
        if (rol.equals("Administrador")) {
            List<Microbiologias> list = microbiologiaDao.listMicrobiologiaQuery(views.txt_search_microbiologias.getText());
            model = (DefaultTableModel) views.microbiologias_table.getModel();

            Object[] row = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_codigo();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getPrecio();
                model.addRow(row);
            }
            views.microbiologias_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.microbiologias_table) {
            int row = views.microbiologias_table.rowAtPoint(e.getPoint());
            views.txt_microbiologia_code.setText(views.microbiologias_table.getValueAt(row, 0).toString());
            views.txt_microbiologia_name.setText(views.microbiologias_table.getValueAt(row, 1).toString());
            views.txt_microbiologia_price.setText(views.microbiologias_table.getValueAt(row, 2).toString());
            //Deshabilitar  botones
            views.btn_register_microbiologia.setEnabled(false);
            views.txt_microbiologia_code.setEditable(false);
            

        } else if (e.getSource() == views.jLabelMicrobiologia) {
            views.jTabbedPane1.setSelectedIndex(4);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar clientes
            listAllMicrobiologias();
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
       if (e.getSource() == views.txt_search_microbiologias) {
            //Limpiar tabla
            cleanTable();
            //Listar cliente
            listAllMicrobiologias();
        } 
    }
    
    public void cleanFields() {
        views.txt_microbiologia_code.setText("");
        views.txt_microbiologia_code.setEditable(true);
        views.txt_microbiologia_name.setText("");
        views.txt_microbiologia_price.setText("");
    }
    
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    
}

