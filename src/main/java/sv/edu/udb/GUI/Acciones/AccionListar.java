package sv.edu.udb.GUI.Acciones;

import sv.edu.udb.Hijasclass.Libro;
import sv.edu.udb.datos.LibroDB;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccionListar {

    public List<Object[]> AccionListar(List<Libro> libros) {
        List<Object[]> datosRev = new ArrayList<>();
        for (Libro libro : libros) {
            datosRev.add(new Object[]{
                    libro.getId(),
                    libro.getCodigoInterno(),
                    libro.getTitulo(),
                    libro.getUnidadesDisponibles(),
                    libro.getAutor(),
                    libro.getNumeroPaginas(),
                    libro.getEditorial(),
                    libro.getIsbn(),
                    libro.getAnioPublicacion()
            });
        }
        return datosRev;
    }
}

    

