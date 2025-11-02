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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccionListar {
    LibroDB libroDB = new LibroDB(); //Se crea el objeto libroDB a partir de la clase LIbroDB
    RevistaDB revistaDB = new RevistaDB();
    CDDB cdDB = new CDDB();
    DVDDB dvdDB = new DVDDB();
    List<Libro> libro= new ArrayList<>();
    List<Revista> revista = new ArrayList<>();
    List<CD> cd= new ArrayList<>();
    List<DVD> dvd= new ArrayList<>();
    List<Object[]> datos = new ArrayList<>();


    public List <Object[]> AccionListar(String tipo) throws SQLException {
        try {

            switch (tipo) {
                case "Libro":
                libro = libroDB.selectALL();
                for (Libro l : libro) {
                    Object[] fila = new Object[]{
                            l.getCodigoInterno(),
                            l.getTitulo(),
                            l.getAutor(),
                            l.getEditorial(),
                            l.getAnioPublicacion(),
                            l.getIsbn(),
                            l.getNumeroPaginas(),
                            l.getUnidadesDisponibles()
                    };
                    datos.add(fila);
                }
                break;
                case "Revista":
                    revista = revistaDB.selectALL();
                    for (Revista l : revista) {
                        Object[] fila = new Object[]{
                                l.getCodigoInterno(),
                                l.getTitulo(),
                                l.getEditorial(),
                                l.getFechaPublicacion(),
                                l.getPeriodicidad(),
                                l.getUnidadesDisponibles()
                        };
                        datos.add(fila);
                    }
                    break;
                case "CD Audio":
                    cd = cdDB.selectALL();
                    for (CD l : cd) {
                        Object[] fila = new Object[]{
                                l.getCodigoInterno(),
                                l.getTitulo(),
                                l.getArtista(),
                                l.getGenero(),
                                l.getNumeroCanciones(),
                                l.getDuracionMin(),
                                l.getUnidadesDisponibles()// y otros campos que necesites
                        };
                        datos.add(fila);
                    }
                    break;
                    case "DVD":
                        dvd = dvdDB.selectALL();
                        for (DVD l : dvd) {
                            Object[] fila = new Object[]{
                                    l.getCodigoInterno(),
                                    l.getTitulo(),
                                    l.getGenero(),
                                    l.getDirector(),
                                    l.getDuracionMin(),
                                    l.getUnidadesDisponibles()// y otros campos que necesites
                            };
                            datos.add(fila);
                        }
                        break;
                default:
                    throw new IllegalStateException("Unexpected value: " + tipo);
            }
            return datos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

    

