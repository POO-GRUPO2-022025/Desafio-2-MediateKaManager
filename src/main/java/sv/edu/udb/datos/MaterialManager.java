package sv.edu.udb.datos;

import sv.edu.udb.Hijasclass.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase: MaterialManager
 * Es el "gestor" o intermediario entre la interfaz gráfica (GUI)
 * y las clases que acceden a la base de datos (DAO).
 * Su función principal es:
 * - Guardar temporalmente los materiales en memoria (listas).
 * - Insertar y leer registros usando las clases DAO:
 *   LibroDB, RevistaDB, CDDB y DVDDB.
 * - Enlazar los datos con la interfaz (por ejemplo, JTable).
 */
public class MaterialManager {
    // ===========================================================
// === MÉTODOS DE BÚSQUEDA Y ELIMINACIÓN PARA LIBROS ========
// ===========================================================

    public Libro buscarLibroPorTitulo(String titulo) {
        for (Libro l : libros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                return l;
            }
        }
        return null;
    }

    public boolean eliminarLibroPorTitulo(String titulo) {
        return libros.removeIf(l -> l.getTitulo().equalsIgnoreCase(titulo));
    }


    // =============================
    // 🔹 Listas en memoria (cache)
    // =============================
    private final List<Libro> libros = new ArrayList<>();
    private final List<Revista> revistas = new ArrayList<>();
    private final List<CD> cds = new ArrayList<>();
    private final List<DVD> dvds = new ArrayList<>();

    // =============================
    // 🔹 Objetos DAO (Base de datos)
    // =============================
    private final LibroDB libroDB = new LibroDB();
    private final RevistaDB revistaDB = new RevistaDB();
    private final CDDB cdDB = new CDDB();
    private final DVDDB dvdDB = new DVDDB();
    // ============================================================
    // 📚 MÉTODOS PARA GESTIONAR LIBROS
    // ============================================================
    public void agregarLibro(Libro libro) {
        try {
            // Guarda en la BD
            Libro insertado = libroDB.insert(libro);

            // Si se guardó correctamente, también lo añadimos en memoria
            if (insertado != null) {
                libros.add(insertado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Libro> listarLibros() {
        return libros;
    }

    /**
     * ✅ Método auxiliar: llena un DefaultTableModel con los libros cargados.
     *    (Se puede llamar desde el panel "Listar Material")
     */
    public void cargarTablaLibros(DefaultTableModel modelo) {
        modelo.setRowCount(0); // Limpia la tabla antes de llenarla

        for (Libro lib : libros) {
            modelo.addRow(new Object[]{
                    lib.getTitulo(),
                    lib.getAutor(),
                    lib.getEditorial(),
                    lib.getIsbn(),
                    lib.getAnioPublicacion(),
                    lib.getUnidadesDisponibles()
            });
        }
    }

    // ============================================================
    // 📰 MÉTODOS PARA GESTIONAR REVISTAS
    // ============================================================
    public void agregarRevista(Revista revista) {
        try {
            Revista insertada = revistaDB.insert(revista);
            if (insertada != null) {
                revistas.add(insertada);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Revista> listarRevistas() {
        return revistas;
    }

    // ============================================================
    // 💿 MÉTODOS PARA GESTIONAR CD
    // ============================================================
    public void agregarCD(CD cd) {
        try {
            CD insertado = cdDB.insert(cd);
            if (insertado != null) {
                cds.add(insertado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CD> listarCDs() {
        return cds;
    }

    // ============================================================
    // 📀 MÉTODOS PARA GESTIONAR DVD
    // ============================================================
    public void agregarDVD(DVD dvd) {
        try {
            DVD insertado = dvdDB.insert(dvd);
            if (insertado != null) {
                dvds.add(insertado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DVD> listarDVDs() {
        return dvds;
    }

    // ============================================================
    // 🔄 MÉTODO GENERAL PARA CARGAR DATOS DESDE LA BD
    // ============================================================
    public void cargarDesdeBD() {
        try {
            // Aquí podrías implementar un "select all" en cada DAO.
            // Ejemplo:
            // libros = libroDB.selectTodos();
            // revistas = revistaDB.selectTodos();
            // cds = cdDB.selectTodos();
            // dvds = dvdDB.selectTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
