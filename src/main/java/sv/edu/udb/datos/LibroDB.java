package sv.edu.udb.datos;

import sv.edu.udb.Hijasclass.Libro;
import sv.edu.udb.Padresclass.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar operaciones CRUD de libros.
 * Maneja las tablas 'material' y 'libro'.
 */
public class LibroDB {

    // Instancia de MaterialDB para manejar la tabla padre
    private final MaterialDB materialDB = new MaterialDB();

    // Sentencias SQL preparadas (los ? son parámetros que se asignan después)
    private final String SQL_INSERT = "INSERT INTO libro(id_material, autor, numero_paginas, editorial, isbn, anio_publicacion) VALUES (?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE libro SET autor=?, numero_paginas=?, editorial=?, isbn=?, anio_publicacion=? WHERE id_material=?";
    private final String SQL_DELETE = "DELETE FROM libro WHERE id_material=?";
    // INNER JOIN combina datos de las tablas material y libro
    private final String SQL_SELECT_POR_CODIGO_INTERNO = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, l.autor, l.numero_paginas, l.editorial, l.isbn, l.anio_publicacion FROM libro l INNER JOIN material m ON l.id_material = m.id WHERE m.codigo_interno=?";
    private final String SQL_SELECT_POR_ID = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, l.autor, l.numero_paginas, l.editorial, l.isbn, l.anio_publicacion FROM libro l INNER JOIN material m ON l.id_material = m.id WHERE m.id=?";
    private static final String SQL_SELECT_ALL = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, " +
            "l.autor, l.numero_paginas, l.editorial, l.isbn, l.anio_publicacion " +
            "FROM libro l INNER JOIN material m ON l.id_material = m.id";




    /**
     * Inserta un nuevo libro en la base de datos.
     * Se insertan datos en dos tablas: material (tabla padre) y libro (tabla hija).
     * 
     * @param nuevoLibro objeto Libro con los datos a insertar
     * @return Libro insertado con su ID generado, o null si falla
     * @throws SQLException si hay error en la BD
     */
    public Libro insert(Libro nuevoLibro) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Libro libroInsertado = null;
        try {
            conn = Conexion.getConnection();

            // Paso 1: Insertar en la tabla material (datos generales)
            Material materialInsertado = materialDB.insert(
                    Material.TipoMaterial.LIBRO,
                    nuevoLibro.getTitulo(),
                    nuevoLibro.getUnidadesDisponibles());

            if (materialInsertado != null) {
                // Paso 2: Insertar en la tabla libro (datos específicos)
                stmt = conn.prepareStatement(SQL_INSERT);
                // Los números corresponden a los ? en la consulta SQL
                stmt.setLong(1, materialInsertado.getId());      // id_material
                stmt.setString(2, nuevoLibro.getAutor());        // autor
                stmt.setInt(3, nuevoLibro.getNumeroPaginas());   // numero_paginas
                stmt.setString(4, nuevoLibro.getEditorial());    // editorial
                stmt.setString(5, nuevoLibro.getIsbn());         // isbn
                stmt.setInt(6, nuevoLibro.getAnioPublicacion()); // anio_publicacion
                stmt.executeUpdate();

                // Paso 3: Recuperar el libro completo con todos sus datos
                stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
                stmt.setLong(1, materialInsertado.getId());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    // Crear objeto Libro con los datos de la BD
                    libroInsertado = new Libro(
                            rs.getLong("id"),
                            rs.getString("codigo_interno"),
                            rs.getString("titulo"),
                            rs.getInt("unidades_disponibles"),
                            rs.getString("autor"),
                            rs.getInt("numero_paginas"),
                            rs.getString("editorial"),
                            rs.getString("isbn"),
                            rs.getInt("anio_publicacion"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Importante: cerrar recursos para evitar fugas de memoria
            Conexion.close(rs);
            Conexion.close(stmt);
        }

        return libroInsertado;
    }

    /**
     * Busca libros por ID.
     * Este método realiza una consulta SELECT con INNER JOIN entre las tablas
     * material y libro para obtener información completa del libro.
     * 
     * @param id identificador único del libro en la base de datos
     * @return Lista de libros encontrados (normalmente 0 o 1 elemento)
     * @throws SQLException si hay error en la BD
     */
    public List<Libro> select(int id) throws SQLException {
        // Declarar objetos de conexión fuera del try para poder cerrarlos en finally
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Libro> librosGuardados = new ArrayList<>();
        
        try {
            // Paso 1: Obtener conexión a la base de datos
            conn = Conexion.getConnection();
            
            // Paso 2: Preparar la consulta SQL con el parámetro ID
            stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
            stmt.setInt(1, id); // Reemplazar el primer ? con el valor del ID
            
            // Paso 3: Ejecutar la consulta y obtener resultados
            rs = stmt.executeQuery();
            
            // Paso 4: Recorrer todos los resultados
            while (rs.next()) {
                // Extraer datos de cada columna del resultado
                // y crear un objeto Libro con esos datos
                librosGuardados.add(new Libro(
                        rs.getLong("id"),                        // ID del material
                        rs.getString("codigo_interno"),          // Código único (ej: "LIB00001")
                        rs.getString("titulo"),                  // Título del libro
                        rs.getInt("unidades_disponibles"),       // Cantidad disponible
                        rs.getString("autor"),                   // Nombre del autor
                        rs.getInt("numero_paginas"),             // Total de páginas
                        rs.getString("editorial"),               // Casa editorial
                        rs.getString("isbn"),                    // Código ISBN
                        rs.getInt("anio_publicacion")));        // Año de publicación
            }
        } catch (Exception e) {
            // Capturar y mostrar cualquier error durante la consulta
            e.printStackTrace();
        } finally {
            // SIEMPRE cerrar recursos para liberar memoria y conexiones
            // Se ejecuta incluso si hay errores
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        
        // Retornar la lista (puede estar vacía si no se encontró nada)
        return librosGuardados;
    }

    /**
     * Busca libros por código interno (sobrecarga de método select).
     * Java diferencia cuál método usar según el tipo de dato que recibe.
     * 
     * @param codigo_interno código alfanumérico del libro (ej: "LIB00001")
     * @return Lista de libros encontrados
     * @throws SQLException si hay error en la BD
     */
    public List<Libro> select(String codigo_interno) throws SQLException {
        // Inicializar objetos de BD en null
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Libro> librosGuardados = new ArrayList<>();
        
        try {
            // Paso 1: Establecer conexión con la base de datos
            conn = Conexion.getConnection();
            
            // Paso 2: Preparar consulta SQL para buscar por código interno
            // Usa SQL_SELECT_POR_CODIGO_INTERNO en lugar de SQL_SELECT_POR_ID
            stmt = conn.prepareStatement(SQL_SELECT_POR_CODIGO_INTERNO);
            stmt.setString(1, codigo_interno); // Parámetro String (no int como el otro select)
            
            // Paso 3: Ejecutar consulta
            rs = stmt.executeQuery();
            
            // Paso 4: Procesar resultados
            // Mientras haya filas en el ResultSet, crear objetos Libro
            while (rs.next()) {
                // Mapear columnas de la BD a atributos del objeto Libro
                librosGuardados.add(new Libro(
                        rs.getLong("id"),                        // ID numérico (PK)
                        rs.getString("codigo_interno"),          // Código alfanumérico (UK)
                        rs.getString("titulo"),                  // Título
                        rs.getInt("unidades_disponibles"),       // Stock
                        rs.getString("autor"),                   // Autor
                        rs.getInt("numero_paginas"),             // Páginas
                        rs.getString("editorial"),               // Editorial
                        rs.getString("isbn"),                    // ISBN
                        rs.getInt("anio_publicacion")));        // Año
            }
        } catch (Exception e) {
            // Manejo de excepciones: mostrar error en consola
            e.printStackTrace();
        } finally {
            // Liberar recursos de forma segura
            // El bloque finally SIEMPRE se ejecuta
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        
        return librosGuardados;
    }

    /**
     * Actualiza los datos de un libro existente.
     * Actualiza tanto la tabla material como la tabla libro.
     * 
     * @param libro objeto con los nuevos datos
     * @return número de filas afectadas (1 si se actualizó, 0 si no)
     * @throws SQLException si hay error en la BD
     */
    public int update(Libro libro)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();

            // Paso 1: Actualizar la tabla material (datos generales)
            materialDB.update(
                    libro.getCodigoInterno(),
                    Material.TipoMaterial.LIBRO,
                    libro.getTitulo(),
                    libro.getUnidadesDisponibles());

            // Paso 2: Actualizar la tabla libro (datos específicos)
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, libro.getAutor());
            stmt.setInt(2, libro.getNumeroPaginas());
            stmt.setString(3, libro.getEditorial());
            stmt.setString(4, libro.getIsbn());
            stmt.setInt(5, libro.getAnioPublicacion());
            stmt.setLong(6, libro.getId()); // WHERE id_material=?
            return stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Conexion.close(stmt);
        }
    }

    /**
     * Elimina un libro de la base de datos.
     * Elimina el registro de la tabla libro y luego de la tabla material.
     * 
     * @param id identificador del libro a eliminar
     * @return número de filas afectadas
     * @throws SQLException si hay error en la BD
     */
    public int delete(long id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            // Paso 1: Eliminar de la tabla libro
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setLong(1, id);
            int result = stmt.executeUpdate();

            // Paso 2: Eliminar de la tabla material
            materialDB.delete(id);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Conexion.close(stmt);
        }
    }
    public List<Libro> selectALL() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Libro> librosGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                librosGuardados.add(new Libro(
                        rs.getLong("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("autor"),
                        rs.getInt("numero_paginas"),
                        rs.getString("editorial"),
                        rs.getString("isbn"),
                        rs.getInt("anio_publicacion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return librosGuardados;
    }


}
