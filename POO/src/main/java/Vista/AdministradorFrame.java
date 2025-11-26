package Vista;

import Controlador.SistemaController;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdministradorFrame extends JFrame {
    private SistemaController controlador;
    private JTabbedPane tabbedPane;

    public AdministradorFrame() {
        controlador = SistemaController.getInstancia();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Panel de Administrador - " + controlador.getUsuarioActual().getNombre());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 51, 102));
        panelHeader.setPreferredSize(new Dimension(900, 60));
        
        JLabel lblTitulo = new JLabel("PANEL DE ADMINISTRADOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.BLACK);
        panelHeader.add(lblTitulo);
        
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Estudiantes", crearPanelEstudiantes());
        tabbedPane.addTab("Docentes", crearPanelDocentes());
        tabbedPane.addTab("Cursos", crearPanelCursos());
        tabbedPane.addTab("Carreras", crearPanelCarreras());
        tabbedPane.addTab("Aulas", crearPanelAulas());
        tabbedPane.addTab("Materias", crearPanelMaterias());
        
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelFooter.add(btnCerrarSesion);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabla
        String[] columnas = {"ID", "Nombre", "Correo", "Carrera", "Ciclo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnAgregar = new JButton("Agregar Estudiante");
        btnAgregar.addActionListener(e -> agregarEstudiante(modelo));
        
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                if (controlador.eliminarUsuario(id)) {
                    modelo.removeRow(fila);
                    JOptionPane.showMessageDialog(this, "Estudiante eliminado");
                }
            }
        });
        
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarEstudiantes(modelo));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        cargarEstudiantes(modelo);

        return panel;
    }

    private void cargarEstudiantes(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Estudiante> estudiantes = controlador.listarEstudiantes();
        for (Estudiante est : estudiantes) {
            modelo.addRow(new Object[]{
                est.getId(),
                est.getNombre(),
                est.getCorreo(),
                est.getCarrera().getNombre(),
                est.getCiclo()
            });
        }
    }

    private void agregarEstudiante(DefaultTableModel modelo) {
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JPasswordField txtContrasena = new JPasswordField();
        JTextField txtTelefono = new JTextField();
        JComboBox<Carrera> cmbCarrera = new JComboBox<>();
        JTextField txtCiclo = new JTextField();

        for (Carrera c : controlador.listarCarreras()) {
            cmbCarrera.addItem(c);
        }

        Object[] mensaje = {
            "Nombre:", txtNombre,
            "Correo:", txtCorreo,
            "Contraseña:", txtContrasena,
            "Teléfono:", txtTelefono,
            "Carrera:", cmbCarrera,
            "Ciclo:", txtCiclo
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, 
            "Agregar Estudiante", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String correo = txtCorreo.getText().trim();
                String contrasena = new String(txtContrasena.getPassword());
                String telefono = txtTelefono.getText().trim();
                Carrera carrera = (Carrera) cmbCarrera.getSelectedItem();
                int ciclo = Integer.parseInt(txtCiclo.getText().trim());

                if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos");
                    return;
                }

                if (controlador.registrarEstudiante(nombre, correo, contrasena, 
                                                    telefono, carrera, ciclo)) {
                    cargarEstudiantes(modelo);
                    JOptionPane.showMessageDialog(this, "Estudiante registrado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(this, "El correo ya está registrado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ciclo debe ser un número");
            }
        }
    }

    private JPanel crearPanelDocentes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID", "Nombre", "Correo", "Especialidad", "Cursos Asignados"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnAgregar = new JButton("Agregar Docente");
        btnAgregar.addActionListener(e -> agregarDocente(modelo));
        
        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                if (controlador.eliminarUsuario(id)) {
                    modelo.removeRow(fila);
                    JOptionPane.showMessageDialog(this, "Docente eliminado");
                }
            }
        });
        
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarDocentes(modelo));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        cargarDocentes(modelo);

        return panel;
    }

    private void cargarDocentes(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Docente> docentes = controlador.listarDocentes();
        for (Docente doc : docentes) {
            modelo.addRow(new Object[]{
                doc.getId(),
                doc.getNombre(),
                doc.getCorreo(),
                doc.getEspecialidad(),
                doc.getCursosAsignados().size()
            });
        }
    }

    private void agregarDocente(DefaultTableModel modelo) {
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JPasswordField txtContrasena = new JPasswordField();
        JTextField txtTelefono = new JTextField();
        JTextField txtEspecialidad = new JTextField();

        Object[] mensaje = {
            "Nombre:", txtNombre,
            "Correo:", txtCorreo,
            "Contraseña:", txtContrasena,
            "Teléfono:", txtTelefono,
            "Especialidad:", txtEspecialidad
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, 
            "Agregar Docente", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());
            String telefono = txtTelefono.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            if (controlador.registrarDocente(nombre, correo, contrasena, 
                                             telefono, especialidad)) {
                cargarDocentes(modelo);
                JOptionPane.showMessageDialog(this, "Docente registrado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "El correo ya está registrado");
            }
        }
    }

    private JPanel crearPanelCursos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID", "Código", "Materia", "Docente", "Aula", "Horario", "Matriculados"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnAgregar = new JButton("Crear Curso");
        btnAgregar.addActionListener(e -> crearCurso(modelo));
        
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarCursos(modelo));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        cargarCursos(modelo);

        return panel;
    }

    private void cargarCursos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Curso> cursos = controlador.listarCursos();
        for (Curso curso : cursos) {
            modelo.addRow(new Object[]{
                curso.getId(),
                curso.getCodigo(),
                curso.getMateria().getNombre(),
                curso.getDocente().getNombre(),
                curso.getAula().getCodigo(),
                curso.getHorario(),
                curso.getMatriculados() + "/" + curso.getVacantes()
            });
        }
    }

    private void crearCurso(DefaultTableModel modelo) {
        JTextField txtCodigo = new JTextField();
        JComboBox<Materia> cmbMateria = new JComboBox<>();
        JComboBox<Docente> cmbDocente = new JComboBox<>();
        JComboBox<Aula> cmbAula = new JComboBox<>();
        JTextField txtHorario = new JTextField();
        JTextField txtFranja = new JTextField("Mañana");
        JTextField txtVacantes = new JTextField("30");

        for (Materia m : controlador.listarMaterias()) cmbMateria.addItem(m);
        for (Docente d : controlador.listarDocentes()) cmbDocente.addItem(d);
        for (Aula a : controlador.listarAulas()) cmbAula.addItem(a);

        Object[] mensaje = {
            "Código:", txtCodigo,
            "Materia:", cmbMateria,
            "Docente:", cmbDocente,
            "Aula:", cmbAula,
            "Horario:", txtHorario,
            "Franja:", txtFranja,
            "Vacantes:", txtVacantes
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, 
            "Crear Curso", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String codigo = txtCodigo.getText().trim();
                Materia materia = (Materia) cmbMateria.getSelectedItem();
                Docente docente = (Docente) cmbDocente.getSelectedItem();
                Aula aula = (Aula) cmbAula.getSelectedItem();
                String horario = txtHorario.getText().trim();
                String franja = txtFranja.getText().trim();
                int vacantes = Integer.parseInt(txtVacantes.getText().trim());

                if (controlador.crearCurso(codigo, materia, docente, aula, 
                                          horario, franja, vacantes)) {
                    cargarCursos(modelo);
                    JOptionPane.showMessageDialog(this, "Curso creado exitosamente");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vacantes debe ser un número");
            }
        }
    }

    private JPanel crearPanelCarreras() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID", "Nombre", "Vacantes", "Malla Curricular"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar = new JButton("Crear Carrera");
        JButton btnActualizar = new JButton("Actualizar Lista");
        
        btnActualizar.addActionListener(e -> cargarCarreras(modelo));
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        cargarCarreras(modelo);

        return panel;
    }

    private void cargarCarreras(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Carrera c : controlador.listarCarreras()) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getVacantes(),
                c.getMallaCurricular().getNombre()
            });
        }
    }

    private JPanel crearPanelAulas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID", "Código", "Capacidad", "Edificio"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnAgregar = new JButton("Agregar Aula");
        btnAgregar.addActionListener(e -> agregarAula(modelo));
        
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarAulas(modelo));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        cargarAulas(modelo);

        return panel;
    }

    private void cargarAulas(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Aula a : controlador.listarAulas()) {
            modelo.addRow(new Object[]{
                a.getId(),
                a.getCodigo(),
                a.getCapacidad(),
                a.getEdificio()
            });
        }
    }

    private void agregarAula(DefaultTableModel modelo) {
        JTextField txtCodigo = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JTextField txtEdificio = new JTextField();

        Object[] mensaje = {
            "Código:", txtCodigo,
            "Capacidad:", txtCapacidad,
            "Edificio:", txtEdificio
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, 
            "Agregar Aula", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String codigo = txtCodigo.getText().trim();
                int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
                String edificio = txtEdificio.getText().trim();

                if (controlador.crearAula(codigo, capacidad, edificio)) {
                    cargarAulas(modelo);
                    JOptionPane.showMessageDialog(this, "Aula creada exitosamente");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacidad debe ser un número");
            }
        }
    }

    private JPanel crearPanelMaterias() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"ID", "Nombre", "Créditos"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnAgregar = new JButton("Agregar Materia");
        btnAgregar.addActionListener(e -> agregarMateria(modelo));
        
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarMaterias(modelo));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        cargarMaterias(modelo);

        return panel;
    }

    private void cargarMaterias(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Materia m : controlador.listarMaterias()) {
            modelo.addRow(new Object[]{
                m.getId(),
                m.getNombre(),
                m.getCreditos()
            });
        }
    }

    private void agregarMateria(DefaultTableModel modelo) {
        JTextField txtNombre = new JTextField();
        JTextField txtCreditos = new JTextField();

        Object[] mensaje = {
            "Nombre:", txtNombre,
            "Créditos:", txtCreditos
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, 
            "Agregar Materia", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                int creditos = Integer.parseInt(txtCreditos.getText().trim());

                if (controlador.crearMateria(nombre, creditos)) {
                    cargarMaterias(modelo);
                    JOptionPane.showMessageDialog(this, "Materia creada exitosamente");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Créditos debe ser un número");
            }
        }
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de cerrar sesión?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            controlador.cerrarSesion();
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}