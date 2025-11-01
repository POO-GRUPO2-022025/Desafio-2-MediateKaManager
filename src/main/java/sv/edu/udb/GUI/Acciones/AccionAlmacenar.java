package sv.edu.udb.GUI.Acciones;
import sv.edu.udb.Hijasclass.CD;
import sv.edu.udb.Hijasclass.DVD;
import sv.edu.udb.Hijasclass.Libro;
import sv.edu.udb.Hijasclass.Revista;
import sv.edu.udb.datos.CDDB;
import sv.edu.udb.datos.DVDDB;
import sv.edu.udb.datos.LibroDB;
import sv.edu.udb.datos.RevistaDB;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccionAlmacenar {

    //Almacena los datos, este metodo requiere un panel, campo omitido que no es incluido siempre es el campoCodigo, y el tipo de material
    public static boolean AlmacenarDatos(JPanel panel, JTextField campoOmitido, String tipo) {
        List<String> datos = new ArrayList<>();
        LibroDB libroDB = new LibroDB();
        RevistaDB revistaDB = new RevistaDB();
        CDDB  cdDB = new CDDB();
        DVDDB dvdDB = new DVDDB();
        boolean Resultado = true;

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField && comp != campoOmitido) {
                JTextField campo = (JTextField) comp;
                datos.add(campo.getText());
            }
        }

        try {
            //Mediante un switch se condiciona en base al tipo de material

        switch (tipo) {

            case "Libro":

                Libro libro = new Libro(
                        0,
                        "",
                        datos.get(0), //Titulo
                        Integer.parseInt(datos.get(6)), //Unidades disponibles
                        datos.get(1), //Autor
                        Integer.parseInt(datos.get(2)),//Numero de paginas
                        datos.get(3), //Editorial
                        datos.get(4), //ISBN
                        Integer.parseInt(datos.get(5))); //Año de publicacion

                libroDB.insert(libro); // Metodo para almacenar
                JOptionPane.showMessageDialog(null, "Se guardaron los datos");

            break;
            case "Revista":
                Revista revista = new Revista(
                        0,
                        "",
                        datos.get(0), //Titulo
                        Integer.parseInt(datos.get(4)), //Unidades disponibles
                        datos.get(1), //Editorial
                        datos.get(2), //Periocidad
                        datos.get(3)); //Fecha de publicacion

                revistaDB.insert(revista);
                JOptionPane.showMessageDialog(null, "Se guardaron los datos");

                break;
            case "CD Audio":
                sv.edu.udb.Hijasclass.CD cd = new CD(
                        0,
                        "",
                        datos.get(0), //Titulo
                        Integer.parseInt(datos.get(5)), //Unidades disponibles
                        datos.get(1), //Artista
                        datos.get(2),//Genero
                        Integer.parseInt(datos.get(3)), //Duracion
                        Integer.parseInt(datos.get(4))); //Numero de canciones

                cdDB.insert(cd);
                JOptionPane.showMessageDialog(null, "Se guardaron los datos");

                break;
            case "DVD":
                DVD dvd = new DVD(
                        0,
                        "",
                        datos.get(0), //Titulo
                        Integer.parseInt(datos.get(4)), //Unidades disponibles
                        datos.get(1), //Director
                        datos.get(3),//Genero
                        Integer.parseInt(datos.get(2))); //Duracion


                dvdDB.insert(dvd);
                JOptionPane.showMessageDialog(null, "Se guardaron los datos");
                break;
        }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Hay problemas para guardar los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            Resultado = false;
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            Resultado = false;
            JOptionPane.showMessageDialog(null, "Validar que el tipo de datos sean los correctos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            System.out.println("Error: el texto no es un número válido.");
        }

        return Resultado;
    }
}
