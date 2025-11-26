package Vista;

import Controlador.SistemaController;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private SistemaController controlador;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JLabel lblMensaje;

    public LoginFrame() {
        controlador = SistemaController.getInstancia();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Sistema de Gestión Académica - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Título
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN ACADÉMICA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 51, 102));
        lblTitulo.setBounds(60, 30, 350, 30);
        panelPrincipal.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Universidad - Inicio de Sesión");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(100, 100, 100));
        lblSubtitulo.setBounds(120, 60, 250, 25);
        panelPrincipal.add(lblSubtitulo);

        // Correo
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 14));
        lblCorreo.setBounds(70, 120, 100, 25);
        panelPrincipal.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(70, 145, 300, 30);
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 13));
        panelPrincipal.add(txtCorreo);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Arial", Font.BOLD, 14));
        lblContrasena.setBounds(70, 185, 100, 25);
        panelPrincipal.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(70, 210, 300, 30);
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 13));
        panelPrincipal.add(txtContrasena);

        // Botón Ingresar
        btnIngresar = new JButton("INGRESAR");
        btnIngresar.setBounds(145, 260, 150, 35);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(0, 102, 204));
        btnIngresar.setForeground(Color.BLACK);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.addActionListener(e -> iniciarSesion());
        panelPrincipal.add(btnIngresar);

        // Mensaje
        lblMensaje = new JLabel("");
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setBounds(70, 295, 300, 20);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblMensaje);

        add(panelPrincipal);

        // Enter para login
        txtContrasena.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (correo.isEmpty() || contrasena.isEmpty()) {
            lblMensaje.setText("Por favor complete todos los campos");
            return;
        }

        if (controlador.iniciarSesion(correo, contrasena)) {
            String tipoUsuario = controlador.getTipoUsuarioActual();
            
            switch (tipoUsuario) {
                case "ADMINISTRADOR":
                    new AdministradorFrame().setVisible(true);
                    break;
                case "DOCENTE":
                    new DocenteFrame().setVisible(true);
                    break;
                case "ESTUDIANTE":
                    new EstudianteFrame().setVisible(true);
                    break;
            }
            
            dispose();
        } else {
            lblMensaje.setText("Correo o contraseña incorrectos");
            txtContrasena.setText("");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}