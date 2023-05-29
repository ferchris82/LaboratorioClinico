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
import models.Liquidos;
import models.LiquidosDao;
import views.SystemView;

public class LiquidosController implements ActionListener, MouseListener, KeyListener{
    
    private Liquidos liquido;
    private LiquidosDao liquidoDao;
    private SystemView views;
    
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();
    
    public LiquidosController(Liquidos liquido, LiquidosDao liquidoDao, SystemView views) {
        this.liquido = liquido;
        this.liquidoDao = liquidoDao;
        this.views = views;
        //Boton de registrar liquido
        this.views.btn_register_liquido.addActionListener(this);        
        //Boton de modificar liquido
        this.views.btn_update_liquido.addActionListener(this);
        //Boton eliminar cliente
        this.views.btn_delete_liquido.addActionListener(this);
        //Boton cancelar
        this.views.btn_cancel_liquido.addActionListener(this);
        //Buscador
        this.views.jLabelLiquidosCorporales.addMouseListener(this);
        this.views.liquidos_table.addMouseListener(this);
        this.views.txt_search_liquidos.addKeyListener(this);        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_liquido) {
            //Verificar si los campos estan vacios
            if (views.txt_liquido_code.getText().equals("")
                    || views.txt_liquido_name.getText().equals("")
                    || views.txt_liquido_price.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");

            } else {
                liquido.setId_codigo(Integer.parseInt(views.txt_liquido_code.getText().trim()));
                liquido.setName(views.txt_liquido_name.getText().trim());
                liquido.setPrecio(Double.parseDouble(views.txt_liquido_price.getText()));

                if (liquidoDao.registerLiquidosQuery(liquido)) {
                    cleanTable();
                    cleanFields();
                    listAllLiquidos();
                    JOptionPane.showMessageDialog(null, "Exámen registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error  al registrar examen");
                }
            }
        } else if (e.getSource() == views.btn_update_liquido) {
            if (views.txt_liquido_code.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                if (views.txt_liquido_code.getText().equals("")
                        || views.txt_liquido_name.getText().equals("")
                        || views.txt_liquido_price.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    liquido.setId_codigo(Integer.parseInt(views.txt_liquido_code.getText().trim()));
                    liquido.setName(views.txt_liquido_name.getText().trim());
                    liquido.setPrecio(Double.parseDouble(views.txt_liquido_price.getText()));

                    if (liquidoDao.updateLiquidoQuery(liquido)) {
                        cleanTable();
                        cleanFields();
                        listAllLiquidos();
                        JOptionPane.showMessageDialog(null, "Examen modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el examen" + e);
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_liquido) {
            int row = views.liquidos_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un examen para eliminar");
            } else {
                int id = Integer.parseInt(views.liquidos_table.getValueAt(row, 1).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar  este examen?");

                if (question == 0 && liquidoDao.deleteLiquidoQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_liquido.setEnabled(true);
                    listAllLiquidos();
                    JOptionPane.showMessageDialog(null, "Examen eliminado con éxito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_liquido) {
            views.btn_register_liquido.setEnabled(true);
            cleanFields();
        }
    }
    
    //Listar Liquidos
    public void listAllLiquidos() {
        if (rol.equals("Administrador")) {
            List<Liquidos> list = liquidoDao.listLiquidoQuery(views.txt_search_liquidos.getText());
            model = (DefaultTableModel) views.liquidos_table.getModel();

            Object[] row = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_codigo();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getPrecio();
                model.addRow(row);
            }
            views.liquidos_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.liquidos_table) {
            int row = views.liquidos_table.rowAtPoint(e.getPoint());
            views.txt_liquido_code.setText(views.liquidos_table.getValueAt(row, 0).toString());
            views.txt_liquido_name.setText(views.liquidos_table.getValueAt(row, 1).toString());
            views.txt_liquido_price.setText(views.liquidos_table.getValueAt(row, 2).toString());
            //Deshabilitar  botones
            views.btn_register_liquido.setEnabled(false);
            views.txt_liquido_code.setEditable(false);
            

        } else if (e.getSource() == views.jLabelLiquidosCorporales) {
            views.jTabbedPane1.setSelectedIndex(2);
            //Limpiar tabla
            cleanTable();
            //Limpiar campos
            cleanFields();
            //Listar clientes
            listAllLiquidos();
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
       if (e.getSource() == views.txt_search_liquidos) {
            //Limpiar tabla
            cleanTable();
            //Listar cliente
            listAllLiquidos();
        } 
    }
    
    public void cleanFields() {
        views.txt_liquido_code.setText("");
        views.txt_liquido_code.setEditable(true);
        views.txt_liquido_name.setText("");
        views.txt_liquido_price.setText("");
    }
    
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    
}

