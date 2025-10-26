package sv.edu.udb.datos;

import sv.edu.udb.Hijasclass.CD;
import sv.edu.udb.Padresclass.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CDDB {

    private final MaterialDB materialDB = new MaterialDB();

    private final String SQL_INSERT = "INSERT INTO cd_audio(id_material, artista, genero, duracion_min, numero_canciones) VALUES (?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE cd_audio SET artista=?, genero=?, duracion_min=?, numero_canciones=? WHERE id_material=?";
    private final String SQL_DELETE = "DELETE FROM cd_audio WHERE id_material=?";
    private final String SQL_SELECT_POR_CODIGO_INTERNO = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, c.artista, c.genero, c.duracion_min, c.numero_canciones FROM cd_audio c INNER JOIN material m ON c.id_material = m.id WHERE m.codigo_interno=?";
    private final String SQL_SELECT_POR_ID = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, c.artista, c.genero, c.duracion_min, c.numero_canciones FROM cd_audio c INNER JOIN material m ON c.id_material = m.id WHERE m.id=?";

    public CD insert(CD nuevoCD) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CD cdInsertado = null;
        try {
            conn = Conexion.getConnection();

            // Primero insertar en la tabla material
            Material materialInsertado = materialDB.insert(
                    Material.TipoMaterial.CD,
                    nuevoCD.getTitulo(),
                    nuevoCD.getUnidadesDisponibles());

            if (materialInsertado != null) {
                // Luego insertar en la tabla cd_audio
                stmt = conn.prepareStatement(SQL_INSERT);
                stmt.setLong(1, materialInsertado.getId());
                stmt.setString(2, nuevoCD.getArtista());
                stmt.setString(3, nuevoCD.getGenero());
                stmt.setInt(4, nuevoCD.getDuracionMin());
                stmt.setInt(5, nuevoCD.getNumeroCanciones());
                stmt.executeUpdate();

                // Obtener el CD completo
                stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
                stmt.setLong(1, materialInsertado.getId());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    cdInsertado = new CD(
                            rs.getLong("id"),
                            rs.getString("codigo_interno"),
                            rs.getString("titulo"),
                            rs.getInt("unidades_disponibles"),
                            rs.getString("artista"),
                            rs.getString("genero"),
                            rs.getInt("duracion_min"),
                            rs.getInt("numero_canciones"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }

        return cdInsertado;
    }

    public List<CD> select(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CD> cdsGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cdsGuardados.add(new CD(
                        rs.getLong("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("artista"),
                        rs.getString("genero"),
                        rs.getInt("duracion_min"),
                        rs.getInt("numero_canciones")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return cdsGuardados;
    }

    public List<CD> select(String codigo_interno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CD> cdsGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_CODIGO_INTERNO);
            stmt.setString(1, codigo_interno);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cdsGuardados.add(new CD(
                        rs.getLong("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("artista"),
                        rs.getString("genero"),
                        rs.getInt("duracion_min"),
                        rs.getInt("numero_canciones")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return cdsGuardados;
    }

    public int update(CD cd) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();

            // Primero actualizar la tabla material
            materialDB.update(
                    cd.getCodigoInterno(),
                    Material.TipoMaterial.CD,
                    cd.getTitulo(),
                    cd.getUnidadesDisponibles());

            // Luego actualizar la tabla cd_audio
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, cd.getArtista());
            stmt.setString(2, cd.getGenero());
            stmt.setInt(3, cd.getDuracionMin());
            stmt.setInt(4, cd.getNumeroCanciones());
            stmt.setLong(5, cd.getId());
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
