package sv.edu.udb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sv.edu.udb.GUI.PantallaInicial;


import javax.swing.*;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
       logger.error("Hola UDB!");
        SwingUtilities.invokeLater(() -> new PantallaInicial());


        SwingUtilities.invokeLater(() -> new PantallaInicial());




    }
}