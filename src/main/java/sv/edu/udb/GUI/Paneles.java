package sv.edu.udb.GUI;
import sv.edu.udb.datos.MaterialManager;
import sv.edu.udb.Hijasclass.Libro;
import sv.edu.udb.Hijasclass.Revista;
import sv.edu.udb.Hijasclass.CD;
import sv.edu.udb.Hijasclass.DVD;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;


public class Paneles extends JPanel {

    // --- Campos globales (Libro) para que el listener los vea sin NPE ---
    private JTextField campoCodigo;
    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoNumPaginas;
    private JTextField campoEditorial;
    private JTextField campoISBN;
    private JTextField campoAnioPub;
    private JTextField campoUnidadesDisponibles;


    private final JPanel panelInferior = new JPanel(new BorderLayout());
    private JPanel panelSuperior;
    public  String controlOpciones;


    private final PantallaInicial pantalla;
    private final MaterialManager manager = new MaterialManager();

    public Paneles(PantallaInicial pantalla) { this.pantalla = pantalla; }

    // ========== Layout raíz ==========
    public void construirPanel() {
        GridBagConstraints gbc = new GridBagConstraints();

        if (panelSuperior == null) {
            panelSuperior = crearPanelSuperior();
            gbc.gridx = 0; gbc.gridy = 0;
            gbc.weightx = 1.0; gbc.weighty = 0.1;
            gbc.fill = GridBagConstraints.BOTH;
            add(panelSuperior, gbc);
        }

        gbc.gridy = 1; gbc.weighty = 0.9;
        add(panelInferior, gbc);
        panelInferior.setVisible(false);
    }

    // ========== Panel superior (combo + GO) ==========
    public JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        String[] opciones = {"Libro", "Revista", "CD Audio", "DVD"};

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Elegir el tipo de material:  "), gbc);

        gbc.gridx = 1;
        JComboBox<String> combo = new JComboBox<>(opciones);
        panel.add(combo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton btnGo = new JButton("GO");
        btnGo.addActionListener(e -> {
            controlOpciones = (String) combo.getSelectedItem();
            String menu = pantalla.getOpcioneMenu();
            if ("listar".equalsIgnoreCase(menu)) {
                listarSegunTipo(); // ← listar según lo elegido en el combo
            } else {
                panelInferior.removeAll();
                panelInferior.add(crearPanelInferior(), BorderLayout.CENTER);
                panelInferior.revalidate();
                panelInferior.repaint();
                panelInferior.setVisible(true);
            }
        });
        panel.add(btnGo, gbc);

        return panel;
    }

    // ========== Panel inferior (formularios) ==========
    public JPanel crearPanelInferior() {
        String menu = pantalla.getOpcioneMenu();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;
        campoCodigo = new JTextField(6);


        switch (controlOpciones) {

            // ---------------- LIBRO ----------------
            case "Libro": {
                // --- Campos ---
                JLabel lblcodigo = new JLabel("Codigo: ");
                campoCodigo = new JTextField(6);
                JLabel titulo = new JLabel("Titulo: ");
                campoTitulo = new JTextField(20);
                if (menu.equals("buscar")) campoTitulo.setEnabled(false);

                JLabel autor = new JLabel("Autor: ");
                campoAutor = new JTextField(20);
                if (menu.equals("buscar")) campoAutor.setEnabled(false);

                JLabel numPaginas = new JLabel("N° de paginas: ");
                campoNumPaginas = new JTextField(4);
                if (menu.equals("buscar")) campoNumPaginas.setEnabled(false);

                JLabel editorial = new JLabel("Editorial: ");
                campoEditorial = new JTextField(10);
                if (menu.equals("buscar")) campoEditorial.setEnabled(false);

                JLabel isbn = new JLabel("ISBN: ");
                campoISBN = new JTextField(10);
                if (menu.equals("buscar")) campoISBN.setEnabled(false);

                JLabel anioPub = new JLabel("Año de publicacion: ");
                campoAnioPub = new JTextField(4);
                if (menu.equals("buscar")) campoAnioPub.setEnabled(false);

                JLabel undDisp = new JLabel("Unidades disponibles: ");
                campoUnidadesDisponibles = new JTextField(4);
                if (menu.equals("buscar")) campoUnidadesDisponibles.setEnabled(false);

                // --- Botones ---
                JButton btnBuscar = new JButton("Buscar");
                // === ACCIÓN: BUSCAR POR CÓDIGO ===
                btnBuscar.addActionListener(e -> {
                    try {
                        String codigo = campoCodigo.getText().trim();

                        if (codigo.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Por favor ingresa un código para buscar.");
                            return;
                        }

                        // Conectar a la base de datos
                        sv.edu.udb.datos.LibroDB libroDB = new sv.edu.udb.datos.LibroDB();
                        java.util.List<sv.edu.udb.Hijasclass.Libro> resultados = libroDB.select(codigo);

                        if (!resultados.isEmpty()) {
                            sv.edu.udb.Hijasclass.Libro libro = resultados.get(0);
                            campoTitulo.setText(libro.getTitulo());
                            campoAutor.setText(libro.getAutor());
                            campoNumPaginas.setText(String.valueOf(libro.getNumeroPaginas()));
                            campoEditorial.setText(libro.getEditorial());
                            campoISBN.setText(libro.getIsbn());
                            campoAnioPub.setText(String.valueOf(libro.getAnioPublicacion()));
                            campoUnidadesDisponibles.setText(String.valueOf(libro.getUnidadesDisponibles()));

                            JOptionPane.showMessageDialog(this, "📗 Libro encontrado correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(this, "No se encontró ningún libro con ese código.");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al buscar libro: " + ex.getMessage());
                    }
                });

                if (menu.equals("Agregar")) btnBuscar.setEnabled(false);

                JButton guardar = new JButton(menu.equals("borrar") ? "Borrar" : "Guardar");
                // Botón Guardar
                JButton Guardar = new JButton("Guardar");
                if (menu.equals("buscar")) guardar.setEnabled(false);
                if (menu.equals("borrar")) guardar.setText("Borrar");

// 🚀 Acción del botón Guardar
                guardar.addActionListener(e -> {
                    try {
                        // Validación de campos vacíos
                        if (campoTitulo.getText().trim().isEmpty() ||
                                campoAutor.getText().trim().isEmpty() ||
                                campoNumPaginas.getText().trim().isEmpty() ||
                                campoEditorial.getText().trim().isEmpty() ||
                                campoISBN.getText().trim().isEmpty() ||
                                campoAnioPub.getText().trim().isEmpty() ||
                                campoUnidadesDisponibles.getText().trim().isEmpty()) {

                            JOptionPane.showMessageDialog(this, "Por favor completa todos los campos antes de guardar.");
                            return;
                        }

                        // Crear objeto Libro
                        Libro nuevoLibro = new Libro(
                                0, "",
                                campoTitulo.getText().trim(),
                                Integer.parseInt(campoUnidadesDisponibles.getText().trim()),
                                campoAutor.getText().trim(),
                                Integer.parseInt(campoNumPaginas.getText().trim()),
                                campoEditorial.getText().trim(),
                                campoISBN.getText().trim(),
                                Integer.parseInt(campoAnioPub.getText().trim())
                        );

                        // Guardar en el manager
                        manager.agregarLibro(nuevoLibro);
                        JOptionPane.showMessageDialog(this, "Libro guardado correctamente.");

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Verifica los campos numéricos. Deben contener solo números.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
                    }
                });

                if (menu.equals("buscar")) guardar.setEnabled(false);

                JButton cancelar = new JButton("Cancelar");

                //Borrar con el boton cancelar
                cancelar.addActionListener(e -> {
                    java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(Paneles.this);
                    if (win != null) {
                        win.dispose();               // cierra la ventana (JFrame/JDialog) que contiene a Paneles
                    } else {
                        limpiarCamposLibro();        // fallback: limpia los campos
                        panelInferior.setVisible(false);
                    }
                });


                // --- Listener de Guardar/Borrar ---
                guardar.addActionListener(e -> {
                    try {
                        // Construir objeto desde los campos
                        Libro nuevoLibro = new Libro(
                                0, "",
                                campoTitulo.getText().trim(),
                                Integer.parseInt(campoUnidadesDisponibles.getText().trim()),
                                campoAutor.getText().trim(),
                                Integer.parseInt(campoNumPaginas.getText().trim()),
                                campoEditorial.getText().trim(),
                                campoISBN.getText().trim(),
                                Integer.parseInt(campoAnioPub.getText().trim())
                        );

                        if (menu.equals("borrar")) {
                            // aquí podrías llamar manager.eliminarLibro(...) si lo implementas
                            JOptionPane.showMessageDialog(this, "Función borrar pendiente de implementar.");
                        } else {
                            manager.agregarLibro(nuevoLibro);
                            JOptionPane.showMessageDialog(this, "Libro guardado correctamente.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
                    }
                });

                // --- Layout ---
                gbc.insets = new Insets(10, 10, 10, 10);

                gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
                panel.add(lblcodigo, gbc);
                gbc.gridx = 1; panel.add(campoCodigo, gbc);
                gbc.gridx = 2; panel.add(btnBuscar, gbc);


                gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
                panel.add(titulo, gbc);
                gbc.gridx = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
                panel.add(campoTitulo, gbc);

                gbc.gridwidth = 1;
                gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
                panel.add(autor, gbc);
                gbc.gridx = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
                panel.add(campoAutor, gbc);

                gbc.gridwidth = 1;
                gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
                panel.add(numPaginas, gbc);
                gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
                panel.add(campoNumPaginas, gbc);

                gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST;
                panel.add(editorial, gbc);
                gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST;
                panel.add(campoEditorial, gbc);

                gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
                panel.add(isbn, gbc);
                gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
                panel.add(campoISBN, gbc);

                gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST;
                panel.add(anioPub, gbc);
                gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST;
                panel.add(campoAnioPub, gbc);

                gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
                panel.add(undDisp, gbc);
                gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
                panel.add(campoUnidadesDisponibles, gbc);

                gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.EAST;
                panel.add(guardar, gbc);
                gbc.gridx = 2; gbc.anchor = GridBagConstraints.WEST;
                panel.add(cancelar, gbc);

                break;
            }


            // ---------------- REVISTA ----------------
            case "Revista": {
                JLabel lCodigo = new JLabel("Código:");     JTextField tCodigo = new JTextField(6);
                JLabel lTitulo = new JLabel("Título:");     JTextField tTitulo = new JTextField(20);
                JLabel lEdit   = new JLabel("Editorial:");  JTextField tEdit   = new JTextField(15);
                JLabel lPer    = new JLabel("Periodicidad:"); JTextField tPer = new JTextField(10);
                JLabel lFecha  = new JLabel("Fecha pub. (YYYY-MM-DD):"); JTextField tFecha = new JTextField(12);
                JLabel lUnd    = new JLabel("Unidades:");   JTextField tUnd = new JTextField(4);

                JButton guardar = new JButton("Guardar");
                JButton cancelar = new JButton("Cancelar");

                guardar.addActionListener(ev -> {
                    try {
                        Revista r = new Revista(
                                0,"",
                                tTitulo.getText().trim(),
                                Integer.parseInt(tUnd.getText().trim()),
                                tEdit.getText().trim(),
                                tPer.getText().trim(),
                                tFecha.getText().trim()
                        );
                        manager.agregarRevista(r);
                        JOptionPane.showMessageDialog(this,"Revista guardada.");
                    } catch (Exception ex) { JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage()); }
                });

                int r=0;
                gbc.gridx=0; gbc.gridy=r; panel.add(lCodigo,gbc);  gbc.gridx=1; panel.add(tCodigo,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lTitulo,gbc); gbc.gridx=1; panel.add(tTitulo,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lEdit,gbc);   gbc.gridx=1; panel.add(tEdit,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lPer,gbc);    gbc.gridx=1; panel.add(tPer,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lFecha,gbc);  gbc.gridx=1; panel.add(tFecha,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lUnd,gbc);    gbc.gridx=1; panel.add(tUnd,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(guardar,gbc); gbc.gridx=1; panel.add(cancelar,gbc);
                break;
            }

            // ---------------- CD AUDIO ----------------
            case "CD Audio": {
                JLabel lCodigo = new JLabel("Código:");     JTextField tCodigo = new JTextField(6);
                JLabel lTitulo = new JLabel("Título:");     JTextField tTitulo = new JTextField(20);
                JLabel lArt    = new JLabel("Artista:");    JTextField tArt    = new JTextField(20);
                JLabel lGen    = new JLabel("Género:");     JTextField tGen    = new JTextField(12);
                JLabel lDur    = new JLabel("Duración (min):"); JTextField tDur = new JTextField(4);
                JLabel lNum    = new JLabel("N° canciones:");   JTextField tNum = new JTextField(4);
                JLabel lUnd    = new JLabel("Unidades:");   JTextField tUnd = new JTextField(4);

                JButton guardar = new JButton("Guardar");
                JButton cancelar = new JButton("Cancelar");

                guardar.addActionListener(ev -> {
                    try {
                        CD c = new CD(
                                0,"",
                                tTitulo.getText().trim(),
                                Integer.parseInt(tUnd.getText().trim()),
                                tArt.getText().trim(),
                                tGen.getText().trim(),
                                Integer.parseInt(tDur.getText().trim()),
                                Integer.parseInt(tNum.getText().trim())
                        );
                        manager.agregarCD(c);
                        JOptionPane.showMessageDialog(this,"CD guardado.");
                    } catch (Exception ex) { JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage()); }
                });

                int r=0;
                gbc.gridx=0; gbc.gridy=r; panel.add(lCodigo,gbc);  gbc.gridx=1; panel.add(tCodigo,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lTitulo,gbc); gbc.gridx=1; panel.add(tTitulo,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lArt,gbc);    gbc.gridx=1; panel.add(tArt,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lGen,gbc);    gbc.gridx=1; panel.add(tGen,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lDur,gbc);    gbc.gridx=1; panel.add(tDur,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lNum,gbc);    gbc.gridx=1; panel.add(tNum,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lUnd,gbc);    gbc.gridx=1; panel.add(tUnd,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(guardar,gbc); gbc.gridx=1; panel.add(cancelar,gbc);
                break;
            }

            // ---------------- DVD ----------------
            case "DVD": {
                JLabel lCodigo = new JLabel("Código:");     JTextField tCodigo = new JTextField(6);
                JLabel lTitulo = new JLabel("Título:");     JTextField tTitulo = new JTextField(20);
                JLabel lDir    = new JLabel("Director:");   JTextField tDir    = new JTextField(20);
                JLabel lGen    = new JLabel("Género:");     JTextField tGen    = new JTextField(12);
                JLabel lDur    = new JLabel("Duración (min):"); JTextField tDur = new JTextField(4);
                JLabel lUnd    = new JLabel("Unidades:");   JTextField tUnd = new JTextField(4);

                JButton guardar = new JButton("Guardar");
                JButton cancelar = new JButton("Cancelar");

                guardar.addActionListener(ev -> {
                    try {
                        DVD d = new DVD(
                                0,"",
                                tTitulo.getText().trim(),
                                Integer.parseInt(tUnd.getText().trim()),
                                tDir.getText().trim(),
                                tGen.getText().trim(),
                                Integer.parseInt(tDur.getText().trim())
                        );
                        manager.agregarDVD(d);
                        JOptionPane.showMessageDialog(this,"DVD guardado.");
                    } catch (Exception ex) { JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage()); }
                });

                int r=0;
                gbc.gridx=0; gbc.gridy=r; panel.add(lCodigo,gbc);  gbc.gridx=1; panel.add(tCodigo,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lTitulo,gbc); gbc.gridx=1; panel.add(tTitulo,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lDir,gbc);    gbc.gridx=1; panel.add(tDir,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lGen,gbc);    gbc.gridx=1; panel.add(tGen,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lDur,gbc);    gbc.gridx=1; panel.add(tDur,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(lUnd,gbc);    gbc.gridx=1; panel.add(tUnd,gbc);
                r++; gbc.gridx=0; gbc.gridy=r; panel.add(guardar,gbc); gbc.gridx=1; panel.add(cancelar,gbc);
                break;
            }
        }
        return panel;
    }

    // ========== Listar según el tipo elegido en el combo ==========
    private void listarSegunTipo() {
        switch (controlOpciones) {
            case "Libro":   listarLibros();   break;
            case "Revista": listarRevistas(); break;
            case "CD Audio":listarCDs();      break;
            case "DVD":     listarDVDs();     break;
        }
    }

    private void listarLibros() {
        String[] cols = {"Título", "Autor", "Editorial", "ISBN", "Año", "Unid."};
        DefaultTableModel m = new DefaultTableModel(cols, 0);

        for (Libro l : manager.listarLibros()) {
            m.addRow(new Object[]{
                    l.getTitulo(),
                    l.getAutor(),
                    l.getEditorial(),
                    l.getIsbn(),
                    l.getAnioPublicacion(),
                    l.getUnidadesDisponibles()
            });
        }

        ponerTabla(m);
    }


    private void listarRevistas() {
        String[] cols = {"Título", "Editorial", "Periodicidad", "Fecha", "Unid."};
        DefaultTableModel m = new DefaultTableModel(cols, 0);

        for (Revista r : manager.listarRevistas()) {
            m.addRow(new Object[]{
                    r.getTitulo(),
                    r.getEditorial(),
                    r.getPeriodicidad(),
                    r.getFechaPublicacion(),
                    r.getUnidadesDisponibles()
            });
        }

        ponerTabla(m);
    }


    private void listarCDs() {
        String[] cols = {"Título","Artista","Género","Dur(min)","#Canc.","Unid."};
        DefaultTableModel m = new DefaultTableModel(cols,0);
        for (CD c : manager.listarCDs()) {
            m.addRow(new Object[]{ c.getTitulo(), c.getArtista(), c.getGenero(),
                    c.getDuracionMin(), c.getNumeroCanciones(), c.getUnidadesDisponibles() });
        }
        ponerTabla(m);
    }

    private void listarDVDs() {
        String[] cols = {"Título","Director","Género","Dur(min)","Unid."};
        DefaultTableModel m = new DefaultTableModel(cols,0);
        for (DVD d : manager.listarDVDs()) {
            m.addRow(new Object[]{ d.getTitulo(), d.getDirector(), d.getGenero(),
                    d.getDuracionMin(), d.getUnidadesDisponibles() });
        }
        ponerTabla(m);
    }

    private void ponerTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        panelInferior.removeAll();
        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.revalidate();
        panelInferior.repaint();
        panelInferior.setVisible(true);
    }

    // =======================================================
// MÉTODOS DE UTILIDAD PARA VALIDAR Y LIMPIAR CAMPOS (LIBROS)
// =======================================================

    private boolean validarLibro() {
        if (campoTitulo.getText().trim().isEmpty() ||
                campoAutor.getText().trim().isEmpty() ||
                campoEditorial.getText().trim().isEmpty() ||
                campoISBN.getText().trim().isEmpty() ||
                campoAnioPub.getText().trim().isEmpty() ||
                campoNumPaginas.getText().trim().isEmpty() ||
                campoUnidadesDisponibles.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "⚠️ Por favor completa todos los campos antes de guardar.");
            return true; // hay error
        }
        return false; // todo correcto
    }

    private void limpiarCamposLibro() {
        campoTitulo.setText("");
        campoAutor.setText("");
        campoEditorial.setText("");
        campoISBN.setText("");
        campoAnioPub.setText("");
        campoNumPaginas.setText("");
        campoUnidadesDisponibles.setText("");
    }

}
