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
import models.Moleculares;
import models.MolecularesDao;
import views.SystemView;


public class MolecularesController implements ActionListener, MouseListener, KeyListener{
    
    private Moleculares molecular;
    private MolecularesDao molecularDao;
    private SystemView views;
    
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public MolecularesController(Moleculares molecular, MolecularesDao molecularDao, SystemView views) {
        this.molecular = molecular;
        this.molecularDao = molecularDao;
        this.views = views;
        //Boton de registrar molecular
        this.views.btn_register_molecular.addActionListener(this);        
        //Boton de modificar molecular
        this.views.btn_update_molecular.addActionListener(this);
        //Boton eliminar cliente
        this.views.btn_delete_molecular.addActionListener(this);
        //Boton cancelar
        this.views.btn_cancel_molecular.addActionListener(this);
        //Buscador
        this.views.jLabelMolecular.addMouseListener(this);
        this.views.moleculares_table.addMouseListener(this);
        this.views.txt_search_moleculares.addKeyListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_molecular) {
            //Verificar si los campos estan vacios
            if (views.txt_molecular_code.getText().equals("")
                    || views.txt_molecular_name.getText().equals("")
                    || views.txt_molecular_price.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                molecular.setId_codigo(Integer.parseInt(views.txt_molecular_code.getText().trim()));
                molecular.setName(views.txt_molecular_name.getText().trim());
                molecular.setPrecio(Double.parseDouble(views.txt_molecular_price.getText()));

                if (molecularDao.registerMolecularQuery(molecular)) {
                    cleanTable();
                    cleanFields();
                    listAllMoleculares();
                    JOptionPane.showMessageDialog(null, "Exámen registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error  al registrar examen");
                }
            }
        } else if (e.getSource() == views.btn_update_molecular) {
            if (views.txt_molecular_code.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_molecular_code.getText().equals("")
                        || views.txt_molecular_name.getText().equals("")
                        || views.txt_molecular_price.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    molecular.setId_codigo(Integer.parseInt(views.txt_molecular_code.getText().trim()));
                    molecular.setName(views.txt_molecular_name.getText().trim());
                    molecular.setPrecio(Double.parseDouble(views.txt_molecular_price.getText()));

                    if (molecularDao.updateMolecularQuery(molecular)) {
                        cleanTable();
                        cleanFields();
                        listAllMoleculares();
                        JOptionPane.showMessageDialog(null, "Examen modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el examen" + e);
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_molecular) {
            int row = views.moleculares_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un examen para eliminar");
            } else {
                int id = Integer.parseInt(views.moleculares_table.getValueAt(row, 1).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar  este examen?");

                if (question == 0 && molecularDao.deleteMolecularQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_molecular.setEnabled(true);
                    listAllMoleculares();
                    JOptionPane.showMessageDialog(null, "Examen eliminado con éxito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_molecular) {
            views.btn_register_molecular.setEnabled(true);
            cleanFields();
        }
    }
    
    //Listar Hematologia
    public void listAllMoleculares() {
        if (rol.equals("Administrador")) {
            List<Moleculares> list = molecularDao.listMolecularQuery(views.txt_search_moleculares.getText());
            model = (DefaultTableModel) views.moleculares_table.getModel();

            Object[] row = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_codigo();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getPrecio();
                model.addRow(row);
            }
            views.moleculares_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.moleculares_table) {
            int row = views.moleculares_table.rowAtPoint(e.getPoint());
            views.txt_molecular_code.setText(views.moleculares_table.getValueAt(row, 0).toString());
            views.txt_molecular_name.setText(views.moleculares_table.getValueAt(row, 1).toString());
            views.txt_molecular_price.setText(views.moleculares_table.getValueAt(row, 2).toString());
            //Deshabilitar  botones
            views.btn_register_molecular.setEnabled(false);
            views.txt_molecular_code.setEditable(false);
            

        } else if (e.getSource() == views.jLabelMolecular) {
            views.jTabbedPane1.setSelectedIndex(3);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar clientes
            listAllMoleculares();
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
       if (e.getSource() == views.txt_search_moleculares) {
            //Limpiar tabla
            cleanTable();
            //Listar cliente
            listAllMoleculares();
        } 
    }
    
    public void cleanFields() {
        views.txt_molecular_code.setText("");
        views.txt_molecular_code.setEditable(true);
        views.txt_molecular_name.setText("");
        views.txt_molecular_price.setText("");
    }
    
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    
}


