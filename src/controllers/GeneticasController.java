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
import models.Geneticas;
import models.GeneticasDao;
import views.SystemView;

public class GeneticasController implements ActionListener, MouseListener, KeyListener{
    
    private Geneticas genetica;
    private GeneticasDao geneticaDao;
    private SystemView views;
    
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public GeneticasController(Geneticas microbiologia, GeneticasDao geneticaDao, SystemView views) {
        this.genetica = microbiologia;
        this.geneticaDao = geneticaDao;
        this.views = views;
        //Boton de registrar microbiologia
        this.views.btn_register_genetica.addActionListener(this);        
        //Boton de modificar microbiologia
        this.views.btn_update_genetica.addActionListener(this);
        //Boton eliminar cliente
        this.views.btn_delete_genetica.addActionListener(this);
        //Boton cancelar
        this.views.btn_cancel_genetica.addActionListener(this);
        //Buscador
        this.views.jLabelGenetica.addMouseListener(this);
        this.views.geneticas_table.addMouseListener(this);
        this.views.txt_search_geneticas.addKeyListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_genetica) {
            //Verificar si los campos estan vacios
            if (views.txt_genetica_code.getText().equals("")
                    || views.txt_genetica_name.getText().equals("")
                    || views.txt_genetica_price.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                genetica.setId_codigo(Integer.parseInt(views.txt_genetica_code.getText().trim()));
                genetica.setName(views.txt_genetica_name.getText().trim());
                genetica.setPrecio(Double.parseDouble(views.txt_genetica_price.getText()));

                if (geneticaDao.registerGeneticaQuery(genetica)) {
                    cleanTable();
                    cleanFields();
                    listAllGeneticas();
                    JOptionPane.showMessageDialog(null, "Exámen registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error  al registrar examen");
                }
            }
        } else if (e.getSource() == views.btn_update_genetica) {
            if (views.txt_genetica_code.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_genetica_code.getText().equals("")
                        || views.txt_genetica_name.getText().equals("")
                        || views.txt_genetica_price.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    genetica.setId_codigo(Integer.parseInt(views.txt_genetica_code.getText().trim()));
                    genetica.setName(views.txt_genetica_name.getText().trim());
                    genetica.setPrecio(Double.parseDouble(views.txt_genetica_price.getText()));

                    if (geneticaDao.updateGeneticaQuery(genetica)) {
                        cleanTable();
                        cleanFields();
                        listAllGeneticas();
                        JOptionPane.showMessageDialog(null, "Examen modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el examen" + e);
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_genetica) {
            int row = views.geneticas_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un examen para eliminar");
            } else {
                int id = Integer.parseInt(views.geneticas_table.getValueAt(row, 1).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar este examen?");

                if (question == 0 && geneticaDao.deleteGeneticaQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_genetica.setEnabled(true);
                    listAllGeneticas();
                    JOptionPane.showMessageDialog(null, "Examen eliminado con éxito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_genetica) {
            views.btn_register_genetica.setEnabled(true);
            cleanFields();
        }
    }
    
    //Listar Hematologia
    public void listAllGeneticas() {
        if (rol.equals("Administrador")) {
            List<Geneticas> list = geneticaDao.listGeneticaQuery(views.txt_search_geneticas.getText());
            model = (DefaultTableModel) views.geneticas_table.getModel();

            Object[] row = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_codigo();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getPrecio();
                model.addRow(row);
            }
            views.geneticas_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.geneticas_table) {
            int row = views.geneticas_table.rowAtPoint(e.getPoint());
            views.txt_genetica_code.setText(views.geneticas_table.getValueAt(row, 0).toString());
            views.txt_genetica_name.setText(views.geneticas_table.getValueAt(row, 1).toString());
            views.txt_genetica_price.setText(views.geneticas_table.getValueAt(row, 2).toString());
            //Deshabilitar  botones
            views.btn_register_genetica.setEnabled(false);
            views.txt_genetica_code.setEditable(false);
            

        } else if (e.getSource() == views.jLabelGenetica) {
            views.jTabbedPane1.setSelectedIndex(5);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar clientes
            listAllGeneticas();
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
       if (e.getSource() == views.txt_search_geneticas) {
            //Limpiar tabla
            cleanTable();
            //Listar cliente
            listAllGeneticas();
        } 
    }
    
    public void cleanFields() {
        views.txt_genetica_code.setText("");
        views.txt_genetica_code.setEditable(true);
        views.txt_genetica_name.setText("");
        views.txt_genetica_price.setText("");
    }
    
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    
}

