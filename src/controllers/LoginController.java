package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Empleados;
import models.EmpleadosDao;
import views.LoginView;
import views.SystemView;

public class LoginController implements ActionListener {

    private Empleados empleado;
    private EmpleadosDao empleados_dao;
    private LoginView login_view;
    
    public LoginController(Empleados empleado, EmpleadosDao empleados_dao, LoginView login_view) {
        this.empleado = empleado;
        this.empleados_dao = empleados_dao;
        this.login_view = login_view;
        this.login_view.btn_enter.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Obtener los datos de la vista
        String user = login_view.txt_username.getText().trim();
        String pass = String.valueOf(login_view.txt_password.getPassword());
        
        if (e.getSource() == login_view.btn_enter) {
            //Validar que los campos no esten vacios
            if (!user.equals("") || !pass.equals("")) {
                //Pasar los parametros al método login
                empleado = empleados_dao.loginQuery(user, pass);
                //Verificar la existencia del usuario
                if (empleado.getUsername() != null) {
                    if (empleado.getRol().equals("Administrador")) {
                        SystemView admin = new SystemView();
                        admin.setVisible(true);
                    } else {
                        SystemView aux = new SystemView();
                        aux.setVisible(true);
                    }
                    this.login_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }
    
}
