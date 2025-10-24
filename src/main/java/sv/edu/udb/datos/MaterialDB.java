package sv.edu.udb.datos;

import sv.edu.udb.Padresclass.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDB {

    private final String SQL_INSERT = "INSERT INTO material(tipo, titulo, unidades_disponibles) VALUES (?,?,?)";
    private final String SQL_UPDATE = "UPDATE material SET tipo=?, titulo=?, unidades_disponibles=? WHERE codigo_interno=?";
    private final String SQL_DELETE_POR_CODIGO_INTERNO = "DELETE FROM material WHERE codigo_interno=?";
    private final String SQL_DELETE_POR_ID = "DELETE FROM material WHERE id=?";
    private final String SQL_SELECT_POR_CODIGO_INTERNO = "SELECT id, codigo_interno, titulo, unidades_disponibles,tipo FROM material WHERE codigo_interno=?";
    private final String SQL_SELECT_POR_ID = "SELECT id, codigo_interno, titulo, unidades_disponibles,tipo FROM material WHERE id=?";

    public Material insert(Material.TipoMaterial tipo, String titulo, int unidades_disponibles) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet idsGenerados = null;
        Material nuevoMaterial = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, tipo.toString());
            stmt.setString(2, titulo);
            stmt.setString(3, String.valueOf(unidades_disponibles));
            stmt.executeUpdate();
            idsGenerados = stmt.getGeneratedKeys();

            if (idsGenerados.next()) {
                int idGenerado = idsGenerados.getInt(1);
                stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
                stmt.setInt(1, idGenerado);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    nuevoMaterial = new Material(
                            rs.getInt("id"),
                            rs.getString("codigo_interno"),
                            rs.getString("titulo"),
                            rs.getInt("unidades_disponibles"),
                            Material.TipoMaterial.valueOf(rs.getString("tipo")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }

        return nuevoMaterial;
    }

    public List<Material> select(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Material> materialesGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                materialesGuardados.add(new Material(
                        rs.getInt("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        Material.TipoMaterial.valueOf(rs.getString("tipo"))));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return materialesGuardados;
    }

    public List<Material> select(String codigo_interno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Material> materialesGuardados = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_POR_CODIGO_INTERNO);
            stmt.setString(1, codigo_interno);
            rs = stmt.executeQuery();
            while (rs.next()) {
                materialesGuardados.add(new Material(
                        rs.getInt("id"),
                        rs.getString("codigo_interno"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        Material.TipoMaterial.valueOf(rs.getString("tipo"))));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return materialesGuardados;
    }

    public int update(String codigoInterno, Material.TipoMaterial nuevoTipo, String nuevoTitulo, int nuevasUnidades)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, nuevoTipo.toString());
            stmt.setString(2, nuevoTitulo);
            stmt.setInt(3, nuevasUnidades);
            stmt.setString(4, codigoInterno);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Conexion.close(stmt);
        }
    }

    public int delete(String codigoInterno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_POR_CODIGO_INTERNO);
            stmt.setString(1, codigoInterno);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Conexion.close(stmt);
        }
    }

    public int delete(long id_material) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE_POR_ID);
            stmt.setLong(1, id_material);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Conexion.close(stmt);
        }
    }

}
