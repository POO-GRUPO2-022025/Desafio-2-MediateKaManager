package sv.edu.udb.GUI.Acciones;

import sv.edu.udb.Hijasclass.CD;
import sv.edu.udb.datos.CDDB;
import sv.edu.udb.datos.DVDDB;
import sv.edu.udb.datos.LibroDB;
import sv.edu.udb.datos.RevistaDB;

public class AccionBorrar {

    public static Boolean borrar(String tipo, Long ID) {

        LibroDB libroDB = new LibroDB();
        RevistaDB revistaDB = new RevistaDB();
        CDDB cdDB = new CDDB();
        DVDDB dvdDB = new DVDDB();
        Boolean result = false;
        try {
            switch (tipo) {
                case "Libro":
                    libroDB.delete(ID);
                    break;
                case "Revista":
                    revistaDB.delete(ID);
                    break;
                case "CD Audio":
                    cdDB.delete(ID);
                    break;
                case "DVD":
                    dvdDB.delete(ID);
                    break;
            }
            result = true;
        } catch (Exception e) {
            throw new RuntimeException("Error al borrar el " + tipo);
        }
        return result;
    }

}
