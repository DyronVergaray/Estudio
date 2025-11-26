package Vista;

import Controlador.SistemaController;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DocenteFrame extends JFrame {
    private SistemaController controlador;
    private Docente docente;
    private JTabbedPane tabbedPane;

    public DocenteFrame() {
        controlador = SistemaController.getInstancia();
        docente = (Docente) controlador.getUsuarioActual();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Panel de Docente - " + docente.getNombre());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(102, 51, 0));
        panelHeader.setPreferredSize(new Dimension(900, 80));
        
        JLabel lblTitulo = new JLabel("PANEL DE DOCENTE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblInfo = new JLabel("<html><center>Docente: " + docente.getNombre() + 
                                    "<br>Especialidad: " + docente.getEspecialidad() + 
                                    " | Cursos asignados: " + docente.getCursosAsignados().size() + 
                                    "</center></html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelHeader.add(lblTitulo, BorderLayout.NORTH);
        panelHeader.add(lblInfo, BorderLayout.CENTER);
        
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Mis Cursos", crearPanelMisCursos());
        tabbedPane.addTab("Estudiantes", crearPanelEstudiantes());
        tabbedPane.addTab("Registrar Notas", crearPanelNotas());
        tabbedPane.addTab("Mi Horario", crearPanelHorario());
        tabbedPane.addTab("Reportes", crearPanelReportes());
        
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelFooter.add(btnCerrarSesion);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelMisCursos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("MIS CURSOS ASIGNADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Código", "Materia", "Aula", "Horario", "Estudiantes", "Vacantes"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabla);

        cargarMisCursos(modelo);

        // Panel de resumen
        JPanel panelResumen = new JPanel();
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen"));
        panelResumen.setLayout(new GridLayout(2, 1));
        
        int totalEstudiantes = 0;
        for (Curso c : docente.getCursosAsignados()) {
            totalEstudiantes += c.getMatriculados();
        }
        
        JLabel lblTotalCursos = new JLabel("Total de cursos: " + docente.getCursosAsignados().size());
        JLabel lblTotalEst = new JLabel("Total de estudiantes: " + totalEstudiantes);
        
        panelResumen.add(lblTotalCursos);
        panelResumen.add(lblTotalEst);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelResumen, BorderLayout.SOUTH);

        return panel;
    }

    private void cargarMisCursos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Curso curso : docente.getCursosAsignados()) {
            modelo.addRow(new Object[]{
                curso.getCodigo(),
                curso.getMateria().getNombre(),
                curso.getAula().getCodigo(),
                curso.getHorario(),
                curso.getMatriculados(),
                curso.getVacantes()
            });
        }
    }

    private JPanel crearPanelEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("ESTUDIANTES POR CURSO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(lblTitulo, BorderLayout.NORTH);

        // Selector de curso
        JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblCurso = new JLabel("Seleccionar curso:");
        JComboBox<Curso> cmbCursos = new JComboBox<>();
        for (Curso c : docente.getCursosAsignados()) {
            cmbCursos.addItem(c);
        }
        panelSelector.add(lblCurso);
        panelSelector.add(cmbCursos);
        panelTop.add(panelSelector, BorderLayout.CENTER);

        panel.add(panelTop, BorderLayout.NORTH);

        // Tabla de estudiantes
        String[] columnas = {"ID", "Nombre", "Correo", "Carrera", "Ciclo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Listener del combo
        cmbCursos.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) cmbCursos.getSelectedItem();
            if (cursoSeleccionado != null) {
                cargarEstudiantesCurso(modelo, cursoSeleccionado);
            }
        });

        // Cargar primer curso por defecto
        if (cmbCursos.getItemCount() > 0) {
            cargarEstudiantesCurso(modelo, (Curso) cmbCursos.getItemAt(0));
        }

        return panel;
    }

    private void cargarEstudiantesCurso(DefaultTableModel modelo, Curso curso) {
        modelo.setRowCount(0);
        List<Matricula> matriculas = controlador.listarMatriculasPorCurso(curso);
        
        for (Matricula m : matriculas) {
            Estudiante est = m.getEstudiante();
            modelo.addRow(new Object[]{
                est.getId(),
                est.getNombre(),
                est.getCorreo(),
                est.getCarrera().getNombre(),
                est.getCiclo()
            });
        }
    }

    private JPanel crearPanelNotas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("REGISTRAR Y MODIFICAR CALIFICACIONES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(lblTitulo, BorderLayout.NORTH);

        // Selector de curso
        JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblCurso = new JLabel("Seleccionar curso:");
        JComboBox<Curso> cmbCursos = new JComboBox<>();
        for (Curso c : docente.getCursosAsignados()) {
            cmbCursos.addItem(c);
        }
        panelSelector.add(lblCurso);
        panelSelector.add(cmbCursos);
        panelTop.add(panelSelector, BorderLayout.CENTER);

        panel.add(panelTop, BorderLayout.NORTH);

        // Tabla de notas
        String[] columnas = {"Estudiante", "PC1", "PC2", "PC3", "PCF", "Promedio", "Asistencia %"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(0, 153, 76));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) cmbCursos.getSelectedItem();
            if (cursoSeleccionado != null) {
                guardarNotas(modelo, cursoSeleccionado);
            }
        });
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) cmbCursos.getSelectedItem();
            if (cursoSeleccionado != null) {
                cargarNotasCurso(modelo, cursoSeleccionado);
            }
        });

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        // Listener del combo
        cmbCursos.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) cmbCursos.getSelectedItem();
            if (cursoSeleccionado != null) {
                cargarNotasCurso(modelo, cursoSeleccionado);
            }
        });

        // Cargar primer curso por defecto
        if (cmbCursos.getItemCount() > 0) {
            cargarNotasCurso(modelo, (Curso) cmbCursos.getItemAt(0));
        }

        return panel;
    }

    private void cargarNotasCurso(DefaultTableModel modelo, Curso curso) {
        modelo.setRowCount(0);
        List<Matricula> matriculas = controlador.listarMatriculasPorCurso(curso);
        
        for (Matricula m : matriculas) {
            modelo.addRow(new Object[]{
                m.getEstudiante().getNombre(),
                m.getPc1(),
                m.getPc2(),
                m.getPc3(),
                m.getPcf(),
                String.format("%.2f", m.calcularPromedio()),
                m.getAsistencia()
            });
        }
    }

    private void guardarNotas(DefaultTableModel modelo, Curso curso) {
        List<Matricula> matriculas = controlador.listarMatriculasPorCurso(curso);
        
        try {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (i < matriculas.size()) {
                    Matricula m = matriculas.get(i);
                    
                    double pc1 = Double.parseDouble(modelo.getValueAt(i, 1).toString());
                    double pc2 = Double.parseDouble(modelo.getValueAt(i, 2).toString());
                    double pc3 = Double.parseDouble(modelo.getValueAt(i, 3).toString());
                    double pcf = Double.parseDouble(modelo.getValueAt(i, 4).toString());
                    int asistencia = Integer.parseInt(modelo.getValueAt(i, 6).toString());
                    
                    m.setPc1(pc1);
                    m.setPc2(pc2);
                    m.setPc3(pc3);
                    m.setPcf(pcf);
                    m.setAsistencia(asistencia);
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Notas guardadas exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Actualizar la tabla
            cargarNotasCurso(modelo, curso);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: Verifique que todas las notas sean números válidos",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel crearPanelHorario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("MI HORARIO DE CLASES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);

        StringBuilder horario = new StringBuilder();
        horario.append("========================================\n");
        horario.append("       HORARIO DE DOCENTE\n");
        horario.append("       ").append(docente.getNombre()).append("\n");
        horario.append("       Especialidad: ").append(docente.getEspecialidad()).append("\n");
        horario.append("========================================\n\n");

        if (docente.getCursosAsignados().isEmpty()) {
            horario.append("No tiene cursos asignados.\n");
        } else {
            for (Curso curso : docente.getCursosAsignados()) {
                horario.append("CURSO: ").append(curso.getMateria().getNombre()).append("\n");
                horario.append("  Código: ").append(curso.getCodigo()).append("\n");
                horario.append("  Aula: ").append(curso.getAula().getCodigo()).append(" - ")
                       .append(curso.getAula().getEdificio()).append("\n");
                horario.append("  Horario: ").append(curso.getHorario()).append("\n");
                horario.append("  Franja: ").append(curso.getFranjaHoraria()).append("\n");
                horario.append("  Estudiantes: ").append(curso.getMatriculados())
                       .append("/").append(curso.getVacantes()).append("\n");
                horario.append("----------------------------------------\n");
            }
        }

        textArea.setText(horario.toString());

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("GENERAR REPORTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel central con opciones
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblInstrucciones = new JLabel("Seleccione el curso para generar el reporte:");
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 14));
        panelCentro.add(lblInstrucciones);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 15)));

        JComboBox<Curso> cmbCursos = new JComboBox<>();
        for (Curso c : docente.getCursosAsignados()) {
            cmbCursos.addItem(c);
        }
        cmbCursos.setMaximumSize(new Dimension(400, 30));
        panelCentro.add(cmbCursos);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnGenerar = new JButton("Generar Reporte del Curso");
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerar.setBackground(new Color(51, 102, 153));
        btnGenerar.setForeground(Color.BLACK);
        btnGenerar.addActionListener(e -> {
            Curso cursoSeleccionado = (Curso) cmbCursos.getSelectedItem();
            if (cursoSeleccionado != null) {
                mostrarReporteCurso(cursoSeleccionado);
            }
        });
        panelCentro.add(btnGenerar);

        panel.add(panelCentro, BorderLayout.CENTER);

        // Área de texto para mostrar reporte
        JTextArea textAreaReporte = new JTextArea();
        textAreaReporte.setEditable(false);
        textAreaReporte.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(textAreaReporte);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private void mostrarReporteCurso(Curso curso) {
        String reporte = controlador.generarReporteCurso(curso);
        
        JTextArea textArea = new JTextArea(reporte);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Reporte del Curso", JOptionPane.INFORMATION_MESSAGE);
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