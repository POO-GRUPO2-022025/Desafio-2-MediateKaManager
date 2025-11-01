package sv.edu.udb.datos;

import sv.edu.udb.Hijasclass.DVD;
import sv.edu.udb.Hijasclass.Libro;
import sv.edu.udb.Padresclass.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DVDDB {

    private final MaterialDB materialDB = new MaterialDB();

    private final String SQL_INSERT = "INSERT INTO dvd(id_material, director, genero, duracion_min) VALUES (?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE dvd SET director=?, genero=?, duracion_min=? WHERE id_material=?";
    private final String SQL_DELETE = "DELETE FROM dvd WHERE id_material=?";
    private final String SQL_SELECT_POR_CODIGO_INTERNO = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, d.director, d.genero, d.duracion_min FROM dvd d INNER JOIN material m ON d.id_material = m.id WHERE m.codigo_interno=?";
    private final String SQL_SELECT_POR_ID = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, d.director, d.genero, d.duracion_min FROM dvd d INNER JOIN material m ON d.id_material = m.id WHERE m.id=?";
    private final String SQL_SELECT_ALL = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, d.director, d.genero, d.duracion_min FROM dvd d INNER JOIN material m ON d.id_material = m.id";

    public DVD insert(DVD nuevoDVD) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DVD dvdInsertado = null;
        try {
            conn = Conexion.getConnection();

            // Primero insertar en la tabla material
            Material materialInsertado = materialDB.insert(
                    Material.TipoMaterial.DVD,
                    nuevoDVD.getTitulo(),
                    nuevoDVD.getUnidadesDisponibles());

            if (materialInsertado != null) {
                // Luego insertar en la tabla dvd
                stmt = conn.prepareStatement(SQL_INSERT);
                stmt.setLong(1, materialInsertado.getId());
                stmt.setString(2, nuevoDVD.getDirector());
                stmt.setString(3, nuevoDVD.getGenero());
                stmt.setInt(4, nuevoDVD.getDuracionMin());
                stmt.executeUpdate();

                // Obtener el DVD completo
                stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
                stmt.setLong(1, materialInsertado.getId());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    dvdInsertado = new DVD(
                            rs.getLong("id"),
                            rs.getString("codigo_interno"),
                            rs.getString("titulo"),
                            rs.getInt("unidades_disponibles"),
                            rs.getString("director"),
                            rs.getString("genero"),
                            rs.getInt("duracion_min"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }

        return dvdInsertado;
    }

    public List<DVD> select(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DVD> dvdsGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dvdsGuardados.add(new DVD(
                        rs.getLong("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("director"),
                        rs.getString("genero"),
                        rs.getInt("duracion_min")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return dvdsGuardados;
    }

    public List<DVD> select(String codigo_interno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DVD> dvdsGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_CODIGO_INTERNO);
            stmt.setString(1, codigo_interno);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dvdsGuardados.add(new DVD(
                        rs.getLong("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("director"),
                        rs.getString("genero"),
                        rs.getInt("duracion_min")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return dvdsGuardados;
    }

    public int update(DVD dvd) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();

            // Primero actualizar la tabla material
            materialDB.update(
                    dvd.getCodigoInterno(),
                    Material.TipoMaterial.DVD,
                    dvd.getTitulo(),
                    dvd.getUnidadesDisponibles());

            // Luego actualizar la tabla dvd
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, dvd.getDirector());
            stmt.setString(2, dvd.getGenero());
            stmt.setInt(3, dvd.getDuracionMin());
            stmt.setLong(4, dvd.getId());
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

    public List<DVD> selectALL() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<DVD> dvdsGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dvdsGuardados.add(new DVD(
                        rs.getLong("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("director"),
                        rs.getString("genero"),
                        rs.getInt("duracion_min")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return dvdsGuardados;
    }

}
