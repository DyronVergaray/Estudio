package Vista;

import Controlador.SistemaController;
import Modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstudianteFrame extends JFrame {
    private SistemaController controlador;
    private Estudiante estudiante;
    private JTabbedPane tabbedPane;

    public EstudianteFrame() {
        controlador = SistemaController.getInstancia();
        estudiante = (Estudiante) controlador.getUsuarioActual();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Panel de Estudiante - " + estudiante.getNombre());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(0, 102, 51));
        panelHeader.setPreferredSize(new Dimension(900, 80));
        
        JLabel lblTitulo = new JLabel("PANEL DE ESTUDIANTE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblInfo = new JLabel("<html><center>Estudiante: " + estudiante.getNombre() + 
                                    "<br>Carrera: " + estudiante.getCarrera().getNombre() + 
                                    " - Ciclo: " + estudiante.getCiclo() + "</center></html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblInfo.setForeground(Color.BLACK);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelHeader.add(lblTitulo, BorderLayout.NORTH);
        panelHeader.add(lblInfo, BorderLayout.CENTER);
        
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Mis Cursos", crearPanelMisCursos());
        tabbedPane.addTab("Matrícular Curso", crearPanelMatricular());
        tabbedPane.addTab("Mi Horario", crearPanelHorario());
        tabbedPane.addTab("Mis Notas", crearPanelNotas());
        
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

        JLabel lblTitulo = new JLabel("MIS CURSOS MATRICULADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Código", "Materia", "Docente", "Aula", "Horario", "Créditos"};
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

        // Panel de información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Resumen"));
        
        int totalCursos = estudiante.getMatriculas().size();
        int totalCreditos = 0;
        for (Matricula m : estudiante.getMatriculas()) {
            totalCreditos += m.getCurso().getMateria().getCreditos();
        }
        
        JLabel lblResumen = new JLabel("<html>Total de cursos: " + totalCursos + 
                                       "<br>Total de créditos: " + totalCreditos + "</html>");
        lblResumen.setFont(new Font("Arial", Font.PLAIN, 13));
        panelInfo.add(lblResumen);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelInfo, BorderLayout.SOUTH);

        return panel;
    }

    private void cargarMisCursos(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Matricula m : estudiante.getMatriculas()) {
            Curso curso = m.getCurso();
            modelo.addRow(new Object[]{
                curso.getCodigo(),
                curso.getMateria().getNombre(),
                curso.getDocente().getNombre(),
                curso.getAula().getCodigo(),
                curso.getHorario(),
                curso.getMateria().getCreditos()
            });
        }
    }

    private JPanel crearPanelMatricular() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("CURSOS DISPONIBLES PARA MATRÍCULA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Código", "Materia", "Docente", "Horario", "Vacantes", "Disponibles"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton btnMatricular = new JButton("Matricular Curso Seleccionado");
        btnMatricular.setBackground(new Color(0, 153, 76));
        btnMatricular.setForeground(Color.BLACK);
        btnMatricular.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String codigo = (String) modelo.getValueAt(fila, 0);
                matricularCurso(codigo, modelo);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un curso");
            }
        });
        
        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarCursosDisponibles(modelo));

        panelBotones.add(btnMatricular);
        panelBotones.add(btnActualizar);

        panel.add(panelBotones, BorderLayout.SOUTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        cargarCursosDisponibles(modelo);

        return panel;
    }

    private void cargarCursosDisponibles(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Curso> cursosDisponibles = controlador.listarCursosDisponibles(estudiante);
        
        for (Curso curso : cursosDisponibles) {
            int disponibles = curso.getVacantes() - curso.getMatriculados();
            modelo.addRow(new Object[]{
                curso.getCodigo(),
                curso.getMateria().getNombre(),
                curso.getDocente().getNombre(),
                curso.getHorario(),
                curso.getVacantes(),
                disponibles
            });
        }
    }

    private void matricularCurso(String codigo, DefaultTableModel modelo) {
        List<Curso> cursos = controlador.listarCursos();
        Curso cursoSeleccionado = null;
        
        for (Curso c : cursos) {
            if (c.getCodigo().equals(codigo)) {
                cursoSeleccionado = c;
                break;
            }
        }
        
        if (cursoSeleccionado != null) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Desea matricularse en el curso:\n" + 
                cursoSeleccionado.getMateria().getNombre() + 
                "\nDocente: " + cursoSeleccionado.getDocente().getNombre() +
                "\nHorario: " + cursoSeleccionado.getHorario() + "?",
                "Confirmar Matrícula",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (controlador.matricularEstudiante(estudiante, cursoSeleccionado)) {
                    JOptionPane.showMessageDialog(this, 
                        "¡Matrícula exitosa!\nYa está inscrito en el curso.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarCursosDisponibles(modelo);
                    
                    // Actualizar también la pestaña de "Mis Cursos"
                    Component[] components = tabbedPane.getComponents();
                    if (components.length > 0 && components[0] instanceof JPanel) {
                        // Refrescar la primera pestaña
                        tabbedPane.setSelectedIndex(0);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se pudo completar la matrícula.\nPosibles razones:\n" +
                        "- No hay vacantes disponibles\n" +
                        "- Ya está matriculado en este curso",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
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
        horario.append("       HORARIO DE CLASES - ").append(estudiante.getCarrera().getNombre()).append("\n");
        horario.append("       Estudiante: ").append(estudiante.getNombre()).append("\n");
        horario.append("========================================\n\n");

        if (estudiante.getMatriculas().isEmpty()) {
            horario.append("No tiene cursos matriculados aún.\n");
        } else {
            for (Matricula m : estudiante.getMatriculas()) {
                Curso curso = m.getCurso();
                horario.append("CURSO: ").append(curso.getMateria().getNombre()).append("\n");
                horario.append("  Código: ").append(curso.getCodigo()).append("\n");
                horario.append("  Docente: ").append(curso.getDocente().getNombre()).append("\n");
                horario.append("  Aula: ").append(curso.getAula().getCodigo()).append(" - ")
                       .append(curso.getAula().getEdificio()).append("\n");
                horario.append("  Horario: ").append(curso.getHorario()).append("\n");
                horario.append("  Franja: ").append(curso.getFranjaHoraria()).append("\n");
                horario.append("----------------------------------------\n");
            }
        }

        textArea.setText(horario.toString());

        JButton btnImprimir = new JButton("Imprimir Horario");
        btnImprimir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Función de impresión disponible en versión completa",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnImprimir);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelNotas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("MIS CALIFICACIONES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Materia", "PC1", "PC2", "PC3", "PCF", "Promedio", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabla);

        cargarNotas(modelo);

        // Panel de resumen
        JPanel panelResumen = new JPanel();
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen Académico"));
        panelResumen.setLayout(new GridLayout(3, 1, 5, 5));
        
        double sumaPromedios = 0.0;
        int cursosAprobados = 0;
        int cursosDesaprobados = 0;
        
        for (Matricula m : estudiante.getMatriculas()) {
            double promedio = m.calcularPromedio();
            sumaPromedios += promedio;
            if (promedio >= 10.5) {
                cursosAprobados++;
            } else if (promedio > 0) {
                cursosDesaprobados++;
            }
        }
        
        double promedioGeneral = estudiante.getMatriculas().isEmpty() ? 0.0 : 
                                 sumaPromedios / estudiante.getMatriculas().size();
        
        JLabel lblPromedioGeneral = new JLabel("Promedio General: " + 
            String.format("%.2f", promedioGeneral));
        lblPromedioGeneral.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblAprobados = new JLabel("Cursos Aprobados: " + cursosAprobados);
        JLabel lblDesaprobados = new JLabel("Cursos Desaprobados: " + cursosDesaprobados);
        
        panelResumen.add(lblPromedioGeneral);
        panelResumen.add(lblAprobados);
        panelResumen.add(lblDesaprobados);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelResumen, BorderLayout.SOUTH);

        return panel;
    }

    private void cargarNotas(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Matricula m : estudiante.getMatriculas()) {
            double promedio = m.calcularPromedio();
            String estado = promedio == 0.0 ? "Sin notas" : 
                          (promedio >= 10.5 ? "Aprobado" : "Desaprobado");
            
            modelo.addRow(new Object[]{
                m.getCurso().getMateria().getNombre(),
                m.getPc1() == 0.0 ? "-" : m.getPc1(),
                m.getPc2() == 0.0 ? "-" : m.getPc2(),
                m.getPc3() == 0.0 ? "-" : m.getPc3(),
                m.getPcf() == 0.0 ? "-" : m.getPcf(),
                promedio == 0.0 ? "-" : String.format("%.2f", promedio),
                estado
            });
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