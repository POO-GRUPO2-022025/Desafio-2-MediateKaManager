package sv.edu.udb.datos;

import sv.edu.udb.Hijasclass.Revista;
import sv.edu.udb.Padresclass.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevistaDB {

    private final MaterialDB materialDB = new MaterialDB();

    private final String SQL_INSERT = "INSERT INTO revista(id_material, editorial, periodicidad, fecha_publicacion) VALUES (?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE revista SET editorial=?, periodicidad=?, fecha_publicacion=? WHERE id_material=?";
    private final String SQL_DELETE = "DELETE FROM revista WHERE id_material=?";
    private final String SQL_SELECT_POR_CODIGO_INTERNO = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, r.editorial, r.periodicidad, r.fecha_publicacion FROM revista r INNER JOIN material m ON r.id_material = m.id WHERE m.codigo_interno=?";
    private final String SQL_SELECT_POR_ID = "SELECT m.id, m.codigo_interno, m.titulo, m.unidades_disponibles, r.editorial, r.periodicidad, r.fecha_publicacion FROM revista r INNER JOIN material m ON r.id_material = m.id WHERE m.id=?";

    public Revista insert(Revista nuevaRevista) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Revista revistaInsertada = null;
        try {
            conn = Conexion.getConnection();

            // Primero insertar en la tabla material
            Material materialInsertado = materialDB.insert(
                    Material.TipoMaterial.REVISTA,
                    nuevaRevista.getTitulo(),
                    nuevaRevista.getUnidadesDisponibles());

            if (materialInsertado != null) {
                // Luego insertar en la tabla revista
                stmt = conn.prepareStatement(SQL_INSERT);
                stmt.setLong(1, materialInsertado.getId());
                stmt.setString(2, nuevaRevista.getEditorial());
                stmt.setString(3, nuevaRevista.getPeriodicidad());
                stmt.setString(4, nuevaRevista.getFechaPublicacion());
                stmt.executeUpdate();

                // Obtener la revista completa
                stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
                stmt.setLong(1, materialInsertado.getId());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    revistaInsertada = new Revista(
                            rs.getLong("id"),
                            rs.getString("codigo_interno"),
                            rs.getString("titulo"),
                            rs.getInt("unidades_disponibles"),
                            rs.getString("editorial"),
                            rs.getString("periodicidad"),
                            rs.getString("fecha_publicacion"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }

        return revistaInsertada;
    }

    public List<Revista> select(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Revista> revistasGuardadas = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                revistasGuardadas.add(new Revista(
                        rs.getLong("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("editorial"),
                        rs.getString("periodicidad"),
                        rs.getString("fecha_publicacion")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return revistasGuardadas;
    }

    public List<Revista> select(String codigo_interno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Revista> revistasGuardadas = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_CODIGO_INTERNO);
            stmt.setString(1, codigo_interno);
            rs = stmt.executeQuery();
            while (rs.next()) {
                revistasGuardadas.add(new Revista(
                        rs.getLong("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("editorial"),
                        rs.getString("periodicidad"),
                        rs.getString("fecha_publicacion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return revistasGuardadas;
    }

    public int update(Revista revista) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();

            // Primero actualizar la tabla material
            materialDB.update(
                    revista.getCodigoInterno(),
                    Material.TipoMaterial.REVISTA,
                    revista.getTitulo(),
                    revista.getUnidadesDisponibles());

            // Luego actualizar la tabla revista
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, revista.getEditorial());
            stmt.setString(2, revista.getPeriodicidad());
            stmt.setString(3, revista.getFechaPublicacion());
            stmt.setLong(4, revista.getId());
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
