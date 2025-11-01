package sv.edu.udb.GUI;


import javax.swing.*;
import java.awt.*;

public class PantallaInicial extends JFrame{
    private final Paneles panel = new Paneles(this);
    private final JFrame pantalla = new JFrame("MediateKaManager");
    private String opcionMenu;

    public PantallaInicial() {

        pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantalla.setSize(900, 500);
        pantalla.setLocationRelativeTo(null);
        pantalla.setResizable(true);
        pantalla.setVisible(true);
        PanelInicial();
    }

    public JPanel PanelInicial() {

        JPanel panelInicial = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton btnOpe = new JButton("Entrar como Operador");
        btnOpe.addActionListener(e -> {
                    JMenuBar menuBarra = createMenuBarOpe();
                    pantalla.setTitle("MediatekaManager - OPE");
                    pantalla.setJMenuBar(menuBarra);
                    pantalla.revalidate();
                    panelInicial.setVisible(false);
                });
        panelInicial.add(btnOpe,gbc);
        gbc.gridx = 1;
        JButton btnUser = new JButton("Entrar como Usuario");
        btnUser.addActionListener(e -> {
                    JMenuBar menuBarraUser = createMenuBarUser();
                    pantalla.setTitle("MediatekaManager - USER");
                    pantalla.setJMenuBar(menuBarraUser);
                    pantalla.revalidate();
                    panelInicial.setVisible(false);

                });
        panelInicial.add(btnUser,gbc);
        pantalla.add(panelInicial, BorderLayout.CENTER);
        return panelInicial;
    }


    private JMenuBar createMenuBarOpe(){

        JMenuBar menuBarOpe = new JMenuBar();
        JMenu menuOpe = new JMenu("Operador");
        JMenuItem itemAgregar = new JMenuItem("Agregar material");
        JMenuItem itemModificar = new JMenuItem("Modificar material");
        JMenuItem itemListar = new JMenuItem("Listar material");
        JMenuItem itemBorrar = new JMenuItem("Borrar material");
        JMenuItem itemBuscar = new JMenuItem("Buscar material");
        JMenuItem itemSalir = new JMenuItem("Salir");
        menuOpe.add(itemAgregar);
        menuOpe.add(itemModificar);
        menuOpe.add(itemListar);
        menuOpe.add(itemBorrar);
        menuOpe.add(itemBuscar);
        menuOpe.add(itemSalir);

        menuBarOpe.add(menuOpe);

        itemAgregar.addActionListener(e -> {
           panel.construirPanel();
            pantalla.getContentPane().removeAll();
            pantalla.getContentPane().add(panel);
            pantalla.setTitle("MediatekaManager - OPE - AGREGAR");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuOpe, true);


           itemAgregar.setEnabled(false);
           opcionMenu="Agregar";


        });

        itemBuscar.addActionListener(e -> {
            panel.construirPanel();
            pantalla.getContentPane().removeAll();
            pantalla.getContentPane().add(panel);
            pantalla.setTitle("MediatekaManager - OPE - BUSCAR");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuOpe, true);

            itemBuscar.setEnabled(false);
            opcionMenu="buscar";
        });
        itemBorrar.addActionListener(e -> {
            panel.construirPanel();
            pantalla.getContentPane().removeAll();
            pantalla.getContentPane().add(panel);
            pantalla.setTitle("MediatekaManager - OPE - BORRAR");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuOpe, true);

            itemBorrar.setEnabled(false);
            opcionMenu="borrar";
        });
        itemModificar.addActionListener(e -> {
            panel.construirPanel();
            pantalla.getContentPane().removeAll();
            pantalla.getContentPane().add(panel);
            pantalla.setTitle("MediatekaManager - OPE - MODIFICAR");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuOpe, true);

            itemModificar.setEnabled(false);
            opcionMenu="modificar";
        });
        itemListar.addActionListener(e -> {
            panel.construirPanel();
            pantalla.getContentPane().removeAll();
            pantalla.getContentPane().add(panel);
            pantalla.setTitle("MediatekaManager - OPE - LISTAR");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuOpe, true);

            itemListar.setEnabled(false);
            opcionMenu="listar";
        });
        itemSalir.addActionListener(e -> {
            pantalla.getContentPane().removeAll();
            PanelInicial();
            pantalla.setTitle("MediatekaManager");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuOpe, true);
            menuBarOpe.setVisible(false);

        });
        return menuBarOpe;
    }
    private JMenuBar createMenuBarUser(){

        JMenuBar menuBarUser = new JMenuBar();
        JMenu menuUser = new JMenu("Usuario");
        JMenuItem itemListar = new JMenuItem("Listar material");
        JMenuItem itemBuscar = new JMenuItem("Buscar material");
        JMenuItem itemSalir = new JMenuItem("Salir");
        menuUser.add(itemListar);
        menuUser.add(itemBuscar);
        menuUser.add(itemSalir);

        menuBarUser.add(menuUser);

        itemBuscar.addActionListener(e -> {
            panel.construirPanel();
            pantalla.getContentPane().removeAll();
            pantalla.getContentPane().add(panel);
            pantalla.setTitle("MediatekaManager - USER - BUSCAR");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuUser, true);

            itemBuscar.setEnabled(false);
            opcionMenu="buscar";
        });
        itemListar.addActionListener(e -> {
            panel.construirPanel();
            pantalla.getContentPane().removeAll();
            pantalla.getContentPane().add(panel);
            pantalla.setTitle("MediatekaManager - USER - LISTAR");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuUser, true);

            itemListar.setEnabled(false);
            opcionMenu="listar";
        });
        itemSalir.addActionListener(e -> {
            pantalla.getContentPane().removeAll();
            PanelInicial();
            pantalla.setTitle("MediatekaManager");
            pantalla.revalidate();
            pantalla.repaint();
            activarItemsMenu(menuUser, true);
            menuBarUser.setVisible(false);


        });
        return menuBarUser;
    }
    public void activarItemsMenu(JMenu menu, boolean activar) {
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem item = menu.getItem(i);
            if (item != null) {
                item.setEnabled(activar);
            }
        }
    }

    public String getOpcioneMenu(){
        return opcionMenu;
    }

}
