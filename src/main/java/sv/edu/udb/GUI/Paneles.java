package sv.edu.udb.GUI;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

public class Paneles extends JPanel {
    private PantallaInicial pantalla;
    // Constructor que llama guarda el valor de opcionMenu desde Pantalla inicial
    public Paneles(PantallaInicial pantalla) {
        this.pantalla = pantalla;
    }

    private final JPanel panelInferior = new JPanel(new BorderLayout());
    public String controlOpciones; //Se declara la varibale que permite controlar que panel mostrara
    private JPanel panelSuperior;

    // El siguiente metodo prepara los dos paneles superior e inferior
    public void construirPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        // Panel superior, se mostrará cuando se elija alguna opcion del menu que lo requiera
        if (panelSuperior == null) { //Con la condicion si el panel ya está creado se evita crearlo nuevamente
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
        controlarPanel(panelSuperior,true);
    }

    // Metodo para crear el panel superior que contiene las opciones del combo
    public JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new GridBagLayout()); //Define el uso de GridBagLayout para ordenar los componentes
        GridBagConstraints gbc = new GridBagConstraints(); //Establece el gbc que nos ayudara a ubicar los componentes
        String[] opciones = {"Libro", "Revista", "CD Audio", "DVD"}; //Se agregan las opciones que contiene el combolist

        //Agregamos la label y el combo que nos permite seleccionar el tipo de material que se trabajara
        gbc.gridx = 0; //Columna 0
        gbc.gridy = 0; //Fila 0
        panel.add(new JLabel("Elegir el tipo de material:  "), gbc); //Label
        gbc.gridx = 1; //Columna 1
        gbc.gridy = 0; //Fila 0
        JComboBox comboTipoMaterial = new JComboBox(opciones);//Combobox que nos mostrara las diferentes opciones de materiales
        panel.add(comboTipoMaterial, gbc);
        gbc.gridx = 0; //Columna 0
        gbc.gridy = 1; //Fila 2
        gbc.gridwidth = 2; //Abarcará la posicion de las dos columnas en ld grid

        //Se agrega un boton que nos permite ir al panel que dependera se la opcion elegida en el combo
        JButton btnGo = new JButton("GO"); //Boton que al dar clic mostrara el panel inferior
        btnGo.addActionListener(e -> { //Listener para el boton

            controlOpciones = (String) comboTipoMaterial.getSelectedItem(); //Se agrega la opcion elegida en el listado a la variable de control
            String menu = pantalla.getOpcioneMenu(); //Se agrega la opcion que previamente se elegio desde el menu, de esto depende si se trabajara listar materiales
            if (menu.equals("listar")){ //Si la opcion del menu elegida es listar, listará los datos

                //El siguiente switch case, mostrará los datos de la tabla dependiendo la seleccion que se haga en el listado
                switch (controlOpciones) {
                    case "Libro":
                        //Se han agregado datos para prueba de la tabla
                        String[] columnas = {"Libro", "Libro", "Libro"}; //Se definen las columnas y los encabezados
                        List<Object[]> datos = new ArrayList<>(); //Se agrega instancia de un ArrayLista que se utilizara para agregar las datos de las fials
                        //Se agregan datos para prueba al ArrayLista
                        datos.add(new Object[]{"001", "El Quijote", "Cervantes"});
                        datos.add(new Object[]{"002", "Cien años de soledad", "García Márquez"});
                        //Llamamos al metodo para crear la tabla con las propiedas columnas y el arraylista datos
                        mostrarTabla(columnas,datos);

                        break;

                    case "Revista":
                        //Se han agregado datos para prueba de la tabla
                        String[] columnasRev = {"Revista", "Revista", "Revista"}; //Se definen las columnas y los encabezados
                        List<Object[]> datosRev = new ArrayList<>(); //Se agrega instancia de un ArrayLista que se utilizara para agregar las datos de las fials
                        //Se agregan datos para prueba al ArrayLista
                        datosRev.add(new Object[]{"001", "El Quijote", "Cervantes"});
                        datosRev.add(new Object[]{"002", "Cien años de soledad", "García Márquez"});
                        //Llamamos al metodo para crear la tabla con las propiedas columnas y el arraylista datos
                        mostrarTabla(columnasRev,datosRev);

                        break;

                    case "CD Audio":
                        //Se han agregado datos para prueba de la tabla
                        String[] columnasCd = {"CD Audio", "CD Audio", "CD Audio"}; //Se definen las columnas y los encabezados
                        List<Object[]> datosCd = new ArrayList<>(); //Se agrega instancia de un ArrayLista que se utilizara para agregar las datos de las fials
                        //Se agregan datos para prueba al ArrayLista
                        datosCd.add(new Object[]{"001", "El Quijote", "Cervantes"});
                        datosCd.add(new Object[]{"002", "Cien años de soledad", "García Márquez"});
                        //Llamamos al metodo para crear la tabla con las propiedas columnas y el arraylista datos
                        mostrarTabla(columnasCd,datosCd);

                        break;

                    case "DVD":
                        //Se han agregado datos para prueba de la tabla
                        String[] columnasDvd = {"DVD", "DVD", "DVD"}; //Se definen las columnas y los encabezados
                        List<Object[]> datosDvd = new ArrayList<>(); //Se agrega instancia de un ArrayLista que se utilizara para agregar las datos de las fials
                        //Se agregan datos para prueba al ArrayLista
                        datosDvd.add(new Object[]{"001", "El Quijote", "Cervantes"});
                        datosDvd.add(new Object[]{"002", "Cien años de soledad", "García Márquez"});
                        //Llamamos al metodo para crear la tabla con las propiedas columnas y el arraylista datos
                        mostrarTabla(columnasDvd,datosDvd);

                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + controlOpciones);
                }

            }else { //De lo contrario mostrará los paneles para ver y modificar datos
                panelInferior.removeAll(); // Limpia el contenido anterior
                panelInferior.add(crearPanelInferior(), BorderLayout.CENTER); // Agrega el panel inferior
                panelInferior.revalidate();
                panelInferior.repaint();
                panelInferior.setVisible(true); //Deja visible el panel
                controlarPanel(panelSuperior,false);
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

                //Elementos que se utilizan para Libro
                JLabel codigo = new JLabel("Codigo: ");
                JTextField campoCodigo = new JTextField(6);
                if (menu.equals("Agregar")) {campoCodigo.setEnabled(false);campoCodigo.setText("codigo");} // Cuando se esta agregando un material, el codigo se genera solo
                JLabel titulo = new JLabel("Titulo: ");
                JTextField campoTitulo = new JTextField(20);
                if (menu.equals("buscar")||menu.equals("borrar")) campoTitulo.setEnabled(false);
                JLabel autor = new JLabel("Autor: ");
                JTextField campoAutor = new JTextField(20);
                if (menu.equals("buscar")||menu.equals("borrar")) campoAutor.setEnabled(false);
                JLabel numPaginas = new JLabel("N° de paginas: ");
                JTextField campoNumPaginas = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoNumPaginas.setEnabled(false);
                JLabel editorial = new JLabel("Editorial: ");
                JTextField campoEditorial = new JTextField(10);
                if (menu.equals("buscar")||menu.equals("borrar")) campoEditorial.setEnabled(false);
                JLabel isbn = new JLabel("ISBN: ");
                JTextField campoISBN = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoISBN.setEnabled(false);
                JLabel anioPub = new JLabel("Año de publicacion: ");
                JTextField campoAnioPub = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoAnioPub.setEnabled(false);
                JLabel undDisp = new JLabel("Unidades disponibles: ");
                JTextField campoUnidadesDisponibles = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoUnidadesDisponibles.setEnabled(false);

                //Botones
                JButton btnBuscar = new JButton("Buscar");
                if (menu.equals("Agregar")) btnBuscar.setEnabled(false);
                JButton guardar = new JButton("Guardar");
                if (menu.equals("buscar")) guardar.setEnabled(false);
                if (menu.equals("borrar")) guardar.setText("Borrar");
                JButton cancelar = new JButton("Cancelar");

                //Listener de los botones
                guardar.addActionListener(e->{
                    Boolean camposCompletos = validarCampos(panel);//Llama al metodo para validar que todos los campos esten llenos
                    if(!menu.equals("borrar")) {//Valida si no esta dentro de la opcion borrar
                        if (camposCompletos.equals(true)) {

                            //INGRESAR EL PROCESO PARA GUARDAR LOS DATOS

                            //Cuando se ingresan los datos muestra un mensaje confirmando y limpia la pantalla
                            JOptionPane.showMessageDialog(null, "Se guardaron los datos");
                            controlarPanel(panelSuperior, true);
                            panel.removeAll();
                            panel.revalidate();
                            panel.repaint();

                        } else { //Si hay un campo vacio muestra la advertencia
                            JOptionPane.showMessageDialog(null, "Validar que todos los campos esten completo", "Advertencia", JOptionPane.WARNING_MESSAGE);

                        }
                    }else{ //Si está en la opcion borrar se ejecutará el proceso para borrar los datos
                        //AGREGAR EL PROCESO PARA BORRAR LOS DATOS
                    }
                });

                cancelar.addActionListener( e ->{
                    controlarPanel(panelSuperior,true);
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                        });
                btnBuscar.addActionListener(e ->{
                    if (campoCodigo.getText().isEmpty()) { //Valida si el codigo esta vacio
                        JOptionPane.showMessageDialog(null, "Debe ingresar un codigo para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }else {//Si no esta vacio, validar si el esta modificando o borrando
                        if (menu.equals("modificar") || menu.equals("borrar")) {
                            //Si está en alguna de estas opciones, el campo se deshabilita para evitar posibles modificaciones
                            campoCodigo.setEnabled(false);
                        }
                        //INGRESAR EL PROCESO DE BUSQUEDA DE DATOS <---------------------
                    }

                });

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

                //Elementos que se utilizan para Revista
                JLabel codigoRev = new JLabel("Codigo: ");
                JTextField campoCodigoRev = new JTextField(6);
                if (menu.equals("Agregar")) {campoCodigoRev.setEnabled(false);campoCodigoRev.setText("codigo");}
                JLabel tituloRev = new JLabel("Titulo: ");
                JTextField campoTituloRev = new JTextField(20);
                if (menu.equals("buscar")||menu.equals("borrar")) campoTituloRev.setEnabled(false);
                JLabel editorialRev = new JLabel("Editorial: ");
                JTextField campoEditorialRev = new JTextField(10);
                if (menu.equals("buscar")||menu.equals("borrar")) campoEditorialRev.setEnabled(false);
                JLabel periocidad = new JLabel("Periocidad: ");
                JTextField campoPeriocidad = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoPeriocidad.setEnabled(false);
                JLabel fechaPub = new JLabel("Fecha de publicacion: ");
                JTextField campoFechaPub = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoFechaPub.setEnabled(false);
                JLabel undDispRev = new JLabel("Unidades disponibles: ");
                JTextField campoUnidadesDisponiblesRev = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoUnidadesDisponiblesRev.setEnabled(false);

                //Botones
                JButton btnBuscarRev = new JButton("Buscar");
                if (menu.equals("Agregar")) btnBuscarRev.setEnabled(false);
                JButton guardarRev = new JButton("Guardar");
                if (menu.equals("buscar")) guardarRev.setEnabled(false);
                if (menu.equals("borrar")) guardarRev.setText("Borrar");
                JButton cancelarRev = new JButton("Cancelar");

                //Listener de los botones
                guardarRev.addActionListener(e->{
                    Boolean camposCompletos = validarCampos(panel);//Llama al metodo para validar que todos los campos esten llenos
                    if(!menu.equals("borrar")) {//Valida si no esta dentro de la opcion borrar
                        if (camposCompletos.equals(true)) {

                            //INGRESAR EL PROCESO PARA GUARDAR LOS DATOS

                            //Cuando se ingresan los datos muestra un mensaje confirmando y limpia la pantalla
                            JOptionPane.showMessageDialog(null, "Se guardaron los datos");
                            controlarPanel(panelSuperior, true);
                            panel.removeAll();
                            panel.revalidate();
                            panel.repaint();

                        } else { //Si hay un campo vacio muestra la advertencia
                            JOptionPane.showMessageDialog(null, "Validar que todos los campos esten completo", "Advertencia", JOptionPane.WARNING_MESSAGE);

                        }
                    }else{ //Si está en la opcion borrar se ejecutará el proceso para borrar los datos
                        //AGREGAR EL PROCESO PARA BORRAR LOS DATOS
                    }
                });

                cancelarRev.addActionListener( e ->{
                    controlarPanel(panelSuperior,true);
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                });
                btnBuscarRev.addActionListener(e ->{
                    if (campoCodigoRev.getText().isEmpty()) { //Valida si el codigo esta vacio
                        JOptionPane.showMessageDialog(null, "Debe ingresar un codigo para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }else {//Si no esta vacio, validar si el esta modificando o borrando
                        if (menu.equals("modificar") || menu.equals("borrar")) {
                            //Si está en alguna de estas opciones, el campo se deshabilita para evitar posibles modificaciones
                            campoCodigoRev.setEnabled(false);
                        }
                        //INGRESAR EL PROCESO DE BUSQUEDA DE DATOS <---------------------
                    }

                });

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

                // Elemento utilizados para CDAudio
                JLabel codigoAud = new JLabel("Codigo: ");
                JTextField campoCodigoAud = new JTextField(6);
                if (menu.equals("Agregar")) {campoCodigoAud.setEnabled(false);campoCodigoAud.setText("codigo");}
                JLabel tituloAud = new JLabel("Titulo: ");
                JTextField campoTituloAud = new JTextField(20);
                if (menu.equals("buscar")||menu.equals("borrar")) campoTituloAud.setEnabled(false);
                JLabel artista = new JLabel("Artista: ");
                JTextField campoArtista = new JTextField(20);
                if (menu.equals("buscar")||menu.equals("borrar")) campoArtista.setEnabled(false);
                JLabel genero = new JLabel("Genero: ");
                JTextField campoGenero = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoGenero.setEnabled(false);
                JLabel duracion = new JLabel("Duracion: ");
                JTextField campoDuaracion = new JTextField(10);
                if (menu.equals("buscar")||menu.equals("borrar")) campoDuaracion.setEnabled(false);
                JLabel nCanciones = new JLabel("Numero de canciones: ");
                JTextField campoNcanciones = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoNcanciones.setEnabled(false);
                JLabel undDispAud = new JLabel("Unidades disponibles: ");
                JTextField campoUnidadesDisponiblesAud = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoUnidadesDisponiblesAud.setEnabled(false);

                //Botones
                JButton btnBuscarAud = new JButton("Buscar");
                if (menu.equals("Agregar")) btnBuscarAud.setEnabled(false);
                JButton guardarAud = new JButton("Guardar");
                if (menu.equals("buscar")) guardarAud.setEnabled(false);
                if (menu.equals("borrar")) guardarAud.setText("Borrar");
                JButton cancelarAud = new JButton("Cancelar");

                //Listener de los botones
                guardarAud.addActionListener(e->{
                    Boolean camposCompletos = validarCampos(panel);//Llama al metodo para validar que todos los campos esten llenos
                    if(!menu.equals("borrar")) {//Valida si no esta dentro de la opcion borrar
                        if (camposCompletos.equals(true)) {

                            //INGRESAR EL PROCESO PARA GUARDAR LOS DATOS

                            //Cuando se ingresan los datos muestra un mensaje confirmando y limpia la pantalla
                            JOptionPane.showMessageDialog(null, "Se guardaron los datos");
                            controlarPanel(panelSuperior, true);
                            panel.removeAll();
                            panel.revalidate();
                            panel.repaint();

                        } else { //Si hay un campo vacio muestra la advertencia
                            JOptionPane.showMessageDialog(null, "Validar que todos los campos esten completo", "Advertencia", JOptionPane.WARNING_MESSAGE);

                        }
                    }else{ //Si está en la opcion borrar se ejecutará el proceso para borrar los datos
                        //AGREGAR EL PROCESO PARA BORRAR LOS DATOS
                    }
                });

                cancelarAud.addActionListener( e ->{
                    controlarPanel(panelSuperior,true);
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                });
                btnBuscarAud.addActionListener(e ->{
                    if (campoCodigoAud.getText().isEmpty()) { //Valida si el codigo esta vacio
                        JOptionPane.showMessageDialog(null, "Debe ingresar un codigo para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }else {//Si no esta vacio, validar si el esta modificando o borrando
                        if (menu.equals("modificar") || menu.equals("borrar")) {
                            //Si está en alguna de estas opciones, el campo se deshabilita para evitar posibles modificaciones
                            campoCodigoAud.setEnabled(false);
                        }
                        //INGRESAR EL PROCESO DE BUSQUEDA DE DATOS <---------------------
                    }

                });

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

                // Elementos utilizados para DVD
                JLabel codigoDVD = new JLabel("Codigo: ");
                JTextField campoCodigoDVD = new JTextField(6);
                if (menu.equals("Agregar")) {campoCodigoDVD.setEnabled(false);campoCodigoDVD.setText("codigo");}
                JLabel tituloDVD = new JLabel("Titulo: ");
                JTextField campoTituloDVD = new JTextField(20);
                if (menu.equals("buscar")||menu.equals("borrar")) campoTituloDVD.setEnabled(false);
                JLabel director = new JLabel("Director: ");
                JTextField campoDirector = new JTextField(15);
                if (menu.equals("buscar")||menu.equals("borrar")) campoDirector.setEnabled(false);
                JLabel duracionDVD = new JLabel("Duracion: ");
                JTextField campoDuracionDVD = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoDuracionDVD.setEnabled(false);
                JLabel generoDVD = new JLabel("Genero: ");
                JTextField campoGeneroDVD = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoGeneroDVD.setEnabled(false);
                JLabel undDispDVD = new JLabel("Unidades disponibles: ");
                JTextField campoUnidadesDisponiblesDVD = new JTextField(4);
                if (menu.equals("buscar")||menu.equals("borrar")) campoUnidadesDisponiblesDVD.setEnabled(false);

                //Botones
                JButton btnBuscarDVD = new JButton("Buscar");
                if (menu.equals("Agregar")) btnBuscarDVD.setEnabled(false);
                JButton guardarDVD = new JButton("Guardar");
                if (menu.equals("buscar")) guardarDVD.setEnabled(false);
                if (menu.equals("borrar")) guardarDVD.setText("Borrar");
                JButton cancelarDVD = new JButton("Cancelar");

                //Listener de los botones
                guardarDVD.addActionListener(e->{
                    Boolean camposCompletos = validarCampos(panel);//Llama al metodo para validar que todos los campos esten llenos
                    if(!menu.equals("borrar")) {//Valida si no esta dentro de la opcion borrar
                        if (camposCompletos.equals(true)) {

                            //INGRESAR EL PROCESO PARA GUARDAR LOS DATOS

                            //Cuando se ingresan los datos muestra un mensaje confirmando y limpia la pantalla
                            JOptionPane.showMessageDialog(null, "Se guardaron los datos");
                            controlarPanel(panelSuperior, true);
                            panel.removeAll();
                            panel.revalidate();
                            panel.repaint();

                        } else { //Si hay un campo vacio muestra la advertencia
                            JOptionPane.showMessageDialog(null, "Validar que todos los campos esten completo", "Advertencia", JOptionPane.WARNING_MESSAGE);

                        }
                    }else{ //Si está en la opcion borrar se ejecutará el proceso para borrar los datos
                        //AGREGAR EL PROCESO PARA BORRAR LOS DATOS
                    }
                });

                cancelarDVD.addActionListener( e ->{
                    controlarPanel(panelSuperior,true);
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                });
                btnBuscarDVD.addActionListener(e ->{
                    if (campoCodigoDVD.getText().isEmpty()) { //Valida si el codigo esta vacio
                        JOptionPane.showMessageDialog(null, "Debe ingresar un codigo para buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }else {//Si no esta vacio, validar si el esta modificando o borrando
                        if (menu.equals("modificar") || menu.equals("borrar")) {
                            //Si está en alguna de estas opciones, el campo se deshabilita para evitar posibles modificaciones
                            campoCodigoDVD.setEnabled(false);
                        }
                        //INGRESAR EL PROCESO DE BUSQUEDA DE DATOS <---------------------
                    }

                });

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

    // Metodo para mostrar la tabla en la opcion de listar materiales
    public void mostrarTabla(String[] columnas, List<Object[]> datos) {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0); // Se crea una tabla vacia con 0 filas
        for (Object[] fila : datos) { //Por cada arreglo en datos, se le agrega una fila a la tabla
            modelo.addRow(fila); // Agrega la fila a la tabla
        }
        //Crea la tabla
        JTable tabla = new JTable(modelo); //Se crea una instancia con base en el modelo
        JScrollPane scroll = new JScrollPane(tabla); //Agrega un scroll para desplazarse por la tabla

        //Agregando la tabla al panel inferior
        panelInferior.removeAll(); //Elimina todos los componentes que puedan existir en el panel inferior
        panelInferior.setLayout(new BorderLayout()); //Define el BorderLayout para ubicar la tabla
        panelInferior.add(scroll, BorderLayout.CENTER); //Se agrega el Scroll
        panelInferior.revalidate();
        panelInferior.repaint();
        panelInferior.setVisible(true); // Deja el pantel inferior como visible
    }

    //Utilidades varias en metodos

    //Metodo que nos permite limpiar los JTextField de un panel
    public void limpiarCampos(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
        }
    }
    //Desactivar los elementos de un panel
    public void controlarPanel(JPanel panel,Boolean estado) {
        for (Component comp : panel.getComponents()) {
            comp.setEnabled(estado);
        }
    }

    //Metodo para validar que todos los campos esten llenos
    public boolean validarCampos(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField campo = (JTextField) comp;
                if (campo.getText().trim().isEmpty()) {
                    return false; // Campo vacío encontrado
                }
            }
        }
        return true; // Todos los campos tienen contenido
    }
}
