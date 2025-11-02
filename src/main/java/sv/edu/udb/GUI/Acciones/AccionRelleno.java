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

public class AccionRelleno {
    LibroDB libroDB = new LibroDB(); //Se crea el objeto libroDB a partir de la clase LIbroDB
    RevistaDB revistaDB = new RevistaDB();
    CDDB cdDB = new CDDB();
    DVDDB dvdDB = new DVDDB();
    List<Libro> libro= new ArrayList();
    List<Revista> revista = new ArrayList();
    List<CD> cd= new ArrayList();
    List<DVD> dvd= new ArrayList();


    public List <Libro> buscarLibro( String codigoBuscar) throws SQLException {
        try {
                    libro = libroDB.select(codigoBuscar);

            return libro;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se encontro el material", "Advertencia", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    public List <Revista> buscarRevista( String codigoBuscar) throws SQLException {
        try {
            revista = revistaDB.select(codigoBuscar);

            return revista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List <CD> buscarCD( String codigoBuscar) throws SQLException {
        try {
            cd = cdDB.select(codigoBuscar);

            return cd;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List <DVD> buscarDVD( String codigoBuscar) throws SQLException {
        try {
            dvd = dvdDB.select(codigoBuscar);

            return dvd;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
