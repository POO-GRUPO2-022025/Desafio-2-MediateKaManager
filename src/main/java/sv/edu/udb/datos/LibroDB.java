package sv.edu.udb.datos;

import sv.edu.udb.Hijasclass.Libro;
import sv.edu.udb.Padresclass.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDB {

    private final MaterialDB materialDB = new MaterialDB();

    private final String SQL_INSERT = "INSERT INTO libro(id_material, autor, numero_paginas, editorial, isbn, anio_publicacion) VALUES (?,?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE libro SET autor=?, numero_paginas=?, editorial=?, isbn=?, anio_publicacion=? WHERE id_material=?";
    private final String SQL_DELETE = "DELETE FROM libro WHERE id_material=?";
    private final String SQL_SELECT_POR_CODIGO_INTERNO = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, l.autor, l.numero_paginas, l.editorial, l.isbn, l.anio_publicacion FROM libro l INNER JOIN material m ON l.id_material = m.id WHERE m.codigo_interno=?";
    private final String SQL_SELECT_POR_ID = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, l.autor, l.numero_paginas, l.editorial, l.isbn, l.anio_publicacion FROM libro l INNER JOIN material m ON l.id_material = m.id WHERE m.id=?";

    public Libro insert(Libro nuevoLibro) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Libro libroInsertado = null;
        try {
            conn = Conexion.getConnection();

            // Primero insertar en la tabla material
            Material materialInsertado = materialDB.insert(
                    Material.TipoMaterial.LIBRO,
                    nuevoLibro.getTitulo(),
                    nuevoLibro.getUnidadesDisponibles());

            if (materialInsertado != null) {
                // Luego insertar en la tabla libro
                stmt = conn.prepareStatement(SQL_INSERT);
                stmt.setLong(1, materialInsertado.getId());
                stmt.setString(2, nuevoLibro.getAutor());
                stmt.setInt(3, nuevoLibro.getNumeroPaginas());
                stmt.setString(4, nuevoLibro.getEditorial());
                stmt.setString(5, nuevoLibro.getIsbn());
                stmt.setInt(6, nuevoLibro.getAnioPublicacion());
                stmt.executeUpdate();

                // Obtener el libro completo
                stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
                stmt.setLong(1, materialInsertado.getId());
                rs = stmt.executeQuery();
                if (rs.next()) {
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
            Conexion.close(rs);
            Conexion.close(stmt);
        }

        return libroInsertado;
    }

    public List<Libro> select(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Libro> librosGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
            stmt.setInt(1, id);
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
                        rs.getInt("anio_publicacion")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return librosGuardados;
    }

    public List<Libro> select(String codigo_interno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Libro> librosGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_CODIGO_INTERNO);
            stmt.setString(1, codigo_interno);
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
                        rs.getInt("anio_publicacion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return librosGuardados;
    }

    public int update(Libro libro)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();

            // Primero actualizar la tabla material
            materialDB.update(
                    libro.getCodigoInterno(),
                    Material.TipoMaterial.LIBRO,
                    libro.getTitulo(),
                    libro.getUnidadesDisponibles());

            // Luego actualizar la tabla libro
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, libro.getAutor());
            stmt.setInt(2, libro.getNumeroPaginas());
            stmt.setString(3, libro.getEditorial());
            stmt.setString(4, libro.getIsbn());
            stmt.setInt(5, libro.getAnioPublicacion());
            stmt.setLong(6, libro.getId());
            return stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Conexion.close(stmt);
        }
    }

    public int delete(long id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setLong(1, id);
            int result = stmt.executeUpdate();

            // También eliminar de la tabla material
            materialDB.delete(id);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Conexion.close(stmt);
        }
    }

}
