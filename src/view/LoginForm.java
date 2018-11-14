package view;

import controller.AdminController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginForm extends JFrame{
    private JPanel loginPanel;
    private JLabel formLogin;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private AdminController adminController;

    public LoginForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Login Form");
        setContentPane(this.loginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 500);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    login();
                }
            }
        });
    }
    private void login(){
        String username = usernameTextField.getText();
        String password = new String(passwordField.getPassword());

        adminController = new AdminController(username, password);
        if(adminController.login()){
            JOptionPane.showMessageDialog(loginPanel, "Welcome " + adminController.admin.getName());
            new StaffManagementForm(this.adminController);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(loginPanel, "Login failed");
        }

    }


    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
    }


}
