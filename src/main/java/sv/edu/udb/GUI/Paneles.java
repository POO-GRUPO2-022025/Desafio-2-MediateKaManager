package sv.edu.udb.GUI;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;



public class Paneles extends JPanel {
    private PantallaInicial pantalla;

    // Constructor que llama guarda el valor de opcineMenu desde Pantalla inicial
    public Paneles(PantallaInicial pantalla) {
        this.pantalla = pantalla;
    }

    private final JPanel panelInferior = new JPanel(new BorderLayout());
    public String controlOpciones; //Se declara la varibale que permite controlar que panel mostrara

    private JPanel panelSuperior;

    // El siguiente metodo prepara los dos paneles superior e inferior
    public void construirPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        // Panel superior, se mostrara cuando se elija alguna opcion del menu que lo requiera
        if (panelSuperior == null) { //Con la condicion si el panel ya esta creado se evita crearlo nuevamente
            panelSuperior = crearPanelSuperior();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            gbc.fill = GridBagConstraints.BOTH;
            add(panelSuperior, gbc);
        }

        // Panel inferior, que mostrara el formulario a llenar dependiendo de la opcion del combobox
        gbc.gridy = 1;
        gbc.weighty = 0.9;
        add(panelInferior, gbc); //Se agrega el panel inferior que mostrara el formulario
        panelInferior.setVisible(false); //Se deja oculto el panel inferior hasta elegir una opcion y dar a GO

    }

    // Metodo para crear el panel superior que contiene las opciones del combo
    public JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        String[] opciones = {"Libro", "Revista", "CD Audio", "DVD"}; //Se agregan las opciones que contiene el combo

        gbc.gridx = 0; //Columna 0
        gbc.gridy = 0; //Fila 0
        panel.add(new JLabel("Elegir el tipo de material:  "), gbc);
        gbc.gridx = 1; //Columna 1
        gbc.gridy = 0; //Fila 0
        JComboBox comboTipoMaterial = new JComboBox(opciones);//Combobox que nos mostrara las diferentes opciones de materiales
        panel.add(comboTipoMaterial, gbc);
        gbc.gridx = 0; //Columna 0
        gbc.gridy = 1; //Fila 2
        gbc.gridwidth = 2; //Abarcara la posicion de las dos columnas en ld grid
        JButton btnGo = new JButton("GO"); //Boton que al dar clic mostrara el panel inferior
        btnGo.addActionListener(e -> {
            controlOpciones = (String) comboTipoMaterial.getSelectedItem(); //Se agrega la opcion elegida en el listado a la variable de control
            String menu = pantalla.getOpcioneMenu();
            if (menu.equals("listar")){ //Si la opcion del menu elegida es listar listara los datos
                //Se han agregado datos para prueba de la tabla
                String[] columnas = {"Código", "Título", "Autor"};
                List<Object[]> datos = new ArrayList<>();
                datos.add(new Object[]{"001", "El Quijote", "Cervantes"});
                datos.add(new Object[]{"002", "Cien años de soledad", "García Márquez"});
                mostrarTabla(columnas,datos);//Llama al metodo para crear la table

            }else { //De lo contrario mostrara los paneles para ver y modificar datos
                panelInferior.removeAll(); // Limpia el contenido anterior
                panelInferior.add(crearPanelInferior(), BorderLayout.CENTER); // Agrega el panel inferior
                panelInferior.revalidate();
                panelInferior.repaint();
                panelInferior.setVisible(true); //Deja visible el panel
            }

            System.out.println(controlOpciones);// Solo es para control BORRAR
        });
        panel.add(btnGo, gbc);//Se agrega el boton para actualizar el panel de acuerdo al elegido en el combo



        return panel;
    }

    // Metodo para el panel inferior que contiene los diferentes formularios dependiendo el tipo de material
    public JPanel crearPanelInferior() {
        String menu = pantalla.getOpcioneMenu(); //Se obtiene la opcion que se trabaja si es para agregar, modificar, etc.
        System.out.println("Opción desde PantallaInicial: " + menu);//Solo es para control BORRAR

        JPanel panel = new JPanel(); //Se declara el nuevo panel que se trabajara con las opciones para ingresar datos
        panel.setLayout(new GridBagLayout()); //Setea el panel con GridBagLayout para controlar cada uno de los elementos
        GridBagConstraints gbc = new GridBagConstraints();//gbc nos permitira ubicar cada elemento ordenado en un grid
        switch (controlOpciones) {
            case "Libro":
                // panel.setBackground(Color.BLACK);

                //Elementos que se utilizan
                JLabel codigo = new JLabel("Codigo: ");
                JTextField campoCodigo = new JTextField(6);
                JLabel titulo = new JLabel("Titulo: ");
                JTextField campoTitulo = new JTextField(20);
                if (menu.equals("buscar")) campoTitulo.setEnabled(false);
                JLabel autor = new JLabel("Autor: ");
                JTextField campoAutor = new JTextField(20);
                if (menu.equals("buscar")) campoAutor.setEnabled(false);
                JLabel numPaginas = new JLabel("N° de paginas: ");
                JTextField campoNumPaginas = new JTextField(4);
                if (menu.equals("buscar")) campoNumPaginas.setEnabled(false);
                JLabel editorial = new JLabel("Editorial: ");
                JTextField campoEditorial = new JTextField(10);
                if (menu.equals("buscar")) campoEditorial.setEnabled(false);
                JLabel isbn = new JLabel("ISBN: ");
                JTextField campoISBN = new JTextField(4);
                if (menu.equals("buscar")) campoISBN.setEnabled(false);
                JLabel anioPub = new JLabel("Año de publicacion: ");
                JTextField campoAnioPub = new JTextField(4);
                if (menu.equals("buscar")) campoAnioPub.setEnabled(false);
                JLabel undDisp = new JLabel("Unidades disponibles: ");
                JTextField campoUnidadesDisponibles = new JTextField(4);
                if (menu.equals("buscar")) campoUnidadesDisponibles.setEnabled(false);

                //Botones
                JButton btnBuscar = new JButton("Buscar");
                if (menu.equals("Agregar")) btnBuscar.setEnabled(false);
                JButton guardar = new JButton("Guardar");
                if (menu.equals("buscar")) guardar.setEnabled(false);
                if (menu.equals("borrar")) guardar.setText("Borrar");
                JButton cancelar = new JButton("Cancelar");

                //Se agregan los elementos ordenados
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(codigo);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoCodigo, gbc);

                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(btnBuscar, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(titulo, gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoTitulo, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(autor, gbc);

                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoAutor, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(numPaginas, gbc);

                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoNumPaginas, gbc);

                gbc.gridx = 2;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(editorial, gbc);

                gbc.gridx = 3;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoEditorial, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(isbn, gbc);

                gbc.gridx = 1;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoISBN, gbc);

                gbc.gridx = 2;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(anioPub, gbc);

                gbc.gridx = 3;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoAnioPub, gbc);

                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(undDisp, gbc);

                gbc.gridx = 1;
                gbc.gridy = 5;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoUnidadesDisponibles, gbc);

                gbc.gridx = 1;
                gbc.gridy = 6;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(guardar, gbc);

                gbc.gridx = 2;
                gbc.gridy = 6;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(cancelar, gbc);

                break;

            case "Revista":
                // panel.setBackground(Color.BLUE);

                //Elementos que se utilizan
                JLabel codigoRev = new JLabel("Codigo: ");
                JTextField campoCodigoRev = new JTextField(6);
                JLabel tituloRev = new JLabel("Titulo: ");
                JTextField campoTituloRev = new JTextField(20);
                if (menu.equals("buscar")) campoTituloRev.setEnabled(false);
                JLabel editorialRev = new JLabel("Editorial: ");
                JTextField campoEditorialRev = new JTextField(10);
                if (menu.equals("buscar")) campoEditorialRev.setEnabled(false);
                JLabel periocidad = new JLabel("Periocidad: ");
                JTextField campoPeriocidad = new JTextField(4);
                if (menu.equals("buscar")) campoPeriocidad.setEnabled(false);
                JLabel fechaPub = new JLabel("Fecha de publicacion: ");
                JTextField campoFechaPub = new JTextField(4);
                if (menu.equals("buscar")) campoFechaPub.setEnabled(false);
                JLabel undDispRev = new JLabel("Unidades disponibles: ");
                JTextField campoUnidadesDisponiblesRev = new JTextField(4);
                if (menu.equals("buscar")) campoUnidadesDisponiblesRev.setEnabled(false);

                //Botones
                JButton btnBuscarRev = new JButton("Buscar");
                if (menu.equals("Agregar")) btnBuscarRev.setEnabled(false);
                JButton guardarRev = new JButton("Guardar");
                if (menu.equals("buscar")) guardarRev.setEnabled(false);
                if (menu.equals("borrar")) guardarRev.setText("Borrar");
                JButton cancelarRev = new JButton("Cancelar");

                //Se agregan los elementos ordenados
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(codigoRev);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoCodigoRev, gbc);

                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(btnBuscarRev, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(tituloRev, gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoTituloRev, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(editorialRev, gbc);

                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoEditorialRev, gbc);

                gbc.gridx = 2;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(periocidad, gbc);

                gbc.gridx = 3;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoPeriocidad, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(fechaPub, gbc);

                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoFechaPub, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(undDispRev, gbc);

                gbc.gridx = 1;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoUnidadesDisponiblesRev, gbc);

                gbc.gridx = 1;
                gbc.gridy = 6;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(guardarRev, gbc);

                gbc.gridx = 2;
                gbc.gridy = 6;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(cancelarRev, gbc);
                break;
            case "CD Audio":
                //panel.setBackground(Color.RED);

                JLabel codigoAud = new JLabel("Codigo: ");
                JTextField campoCodigoAud = new JTextField(6);
                JLabel tituloAud = new JLabel("Titulo: ");
                JTextField campoTituloAud = new JTextField(20);
                if (menu.equals("buscar")) campoTituloAud.setEnabled(false);
                JLabel artista = new JLabel("Artista: ");
                JTextField campoArtista = new JTextField(20);
                if (menu.equals("buscar")) campoArtista.setEnabled(false);
                JLabel genero = new JLabel("Genero: ");
                JTextField campoGenero = new JTextField(4);
                if (menu.equals("buscar")) campoGenero.setEnabled(false);
                JLabel duracion = new JLabel("Duracion: ");
                JTextField campoDuaracion = new JTextField(10);
                if (menu.equals("buscar")) campoDuaracion.setEnabled(false);
                JLabel nCanciones = new JLabel("Numero de canciones: ");
                JTextField campoNcanciones = new JTextField(4);
                if (menu.equals("buscar")) campoNcanciones.setEnabled(false);
                JLabel undDispAud = new JLabel("Unidades disponibles: ");
                JTextField campoUnidadesDisponiblesAud = new JTextField(4);
                if (menu.equals("buscar")) campoUnidadesDisponiblesAud.setEnabled(false);

                //Botones
                JButton btnBuscarAud = new JButton("Buscar");
                if (menu.equals("Agregar")) btnBuscarAud.setEnabled(false);
                JButton guardarAud = new JButton("Guardar");
                if (menu.equals("buscar")) guardarAud.setEnabled(false);
                if (menu.equals("borrar")) guardarAud.setText("Borrar");
                JButton cancelarAud = new JButton("Cancelar");

                //Se agregan los elementos ordenados
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(codigoAud);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoCodigoAud, gbc);

                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(btnBuscarAud, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(tituloAud, gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoTituloAud, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(artista, gbc);

                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoArtista, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(genero, gbc);

                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoGenero, gbc);

                gbc.gridx = 2;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(duracion, gbc);

                gbc.gridx = 3;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoDuaracion, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(nCanciones, gbc);

                gbc.gridx = 1;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoNcanciones, gbc);

                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(undDispAud, gbc);

                gbc.gridx = 1;
                gbc.gridy = 5;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoUnidadesDisponiblesAud, gbc);

                gbc.gridx = 1;
                gbc.gridy = 6;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(guardarAud, gbc);

                gbc.gridx = 2;
                gbc.gridy = 6;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(cancelarAud, gbc);

                break;
            case "DVD":
                JLabel codigoDVD = new JLabel("Codigo: ");
                JTextField campoCodigoDVD = new JTextField(6);
                JLabel tituloDVD = new JLabel("Titulo: ");
                JTextField campoTituloDVD = new JTextField(20);
                if (menu.equals("buscar")) campoTituloDVD.setEnabled(false);
                JLabel director = new JLabel("Director: ");
                JTextField campoDirector = new JTextField(15);
                if (menu.equals("buscar")) campoDirector.setEnabled(false);
                JLabel duracionDVD = new JLabel("Duracion: ");
                JTextField campoDuracionDVD = new JTextField(4);
                if (menu.equals("buscar")) campoDuracionDVD.setEnabled(false);
                JLabel generoDVD = new JLabel("Genero: ");
                JTextField campoGeneroDVD = new JTextField(4);
                if (menu.equals("buscar")) campoGeneroDVD.setEnabled(false);
                JLabel undDispDVD = new JLabel("Unidades disponibles: ");
                JTextField campoUnidadesDisponiblesDVD = new JTextField(4);
                if (menu.equals("buscar")) campoUnidadesDisponiblesDVD.setEnabled(false);

                //Botones
                JButton btnBuscarDVD = new JButton("Buscar");
                if (menu.equals("Agregar")) btnBuscarDVD.setEnabled(false);
                JButton guardarDVD = new JButton("Guardar");
                if (menu.equals("buscar")) guardarDVD.setEnabled(false);
                if (menu.equals("borrar")) guardarDVD.setText("Borrar");
                JButton cancelarDVD = new JButton("Cancelar");

                //Se agregan los elementos ordenados
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.WEST;
                panel.add(codigoDVD);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoCodigoDVD, gbc);

                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(btnBuscarDVD, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(tituloDVD, gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoTituloDVD, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(director, gbc);

                gbc.gridx = 1;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoDirector, gbc);

                gbc.gridx = 2;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(duracionDVD, gbc);

                gbc.gridx = 3;
                gbc.gridy = 2;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoDuracionDVD, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(generoDVD, gbc);

                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoGeneroDVD, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(undDispDVD, gbc);

                gbc.gridx = 1;
                gbc.gridy = 4;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(campoUnidadesDisponiblesDVD, gbc);

                gbc.gridx = 1;
                gbc.gridy = 6;
                gbc.anchor = GridBagConstraints.LINE_END;
                panel.add(guardarDVD, gbc);

                gbc.gridx = 2;
                gbc.gridy = 6;
                gbc.anchor = GridBagConstraints.LINE_START;
                panel.add(cancelarDVD, gbc);
                break;
        }

        return panel;

    }

    public void mostrarTabla(String[] columnas, List<Object[]> datos) {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        panelInferior.removeAll();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.add(scroll, BorderLayout.CENTER);
        panelInferior.revalidate();
        panelInferior.repaint();
        panelInferior.setVisible(true);
    }

}
