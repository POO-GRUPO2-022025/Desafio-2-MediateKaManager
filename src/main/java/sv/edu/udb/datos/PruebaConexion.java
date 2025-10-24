package sv.edu.udb.datos;

import sv.edu.udb.Hijasclass.CD;
import sv.edu.udb.Hijasclass.DVD;
import sv.edu.udb.Hijasclass.Libro;
import sv.edu.udb.Hijasclass.Revista;
import sv.edu.udb.Padresclass.Material;

import java.sql.SQLException;
import java.util.List;

public class PruebaConexion {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBAS DE CONEXIÓN A LA BASE DE DATOS ===\n");

        try {
            // Probar conexión básica
            // probarConexion();

            // // Probar operaciones con Material
            // probarMaterialDB();

            // // Probar operaciones con Libro
            // probarLibroDB();

            // // Probar operaciones con Revista
            // probarRevistaDB();

            // // Probar operaciones con CD
            // probarCDDB();

            // // Probar operaciones con DVD
            probarDVDDB();

            System.out.println("\n=== TODAS LAS PRUEBAS COMPLETADAS EXITOSAMENTE ===");

        } catch (Exception e) {
            System.err.println("Error durante las pruebas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void probarConexion() {
        System.out.println("--- PRUEBA 1: Conexión Básica ---");
        try {
            if (Conexion.getConnection() != null) {
                System.out.println("[OK] Conexión a la base de datos exitosa");
            } else {
                System.out.println("[ERROR] No se pudo establecer conexión");
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void probarMaterialDB() throws SQLException {
        System.out.println("--- PRUEBA 2: MaterialDB (CRUD) ---");
        MaterialDB materialDB = new MaterialDB();

        // INSERT
        System.out.println("Insertando material...");
        Material nuevoMaterial = materialDB.insert(
                Material.TipoMaterial.LIBRO,
                "Material de Prueba",
                5);
        if (nuevoMaterial != null) {
            System.out.println("[OK] Material insertado: " + nuevoMaterial.getCodigoInterno());
            System.out.println("  ID: " + nuevoMaterial.getId());
            System.out.println("  Título: " + nuevoMaterial.getTitulo());

            // SELECT por ID
            System.out.println("\nConsultando material por ID...");
            List<Material> materialesPorId = materialDB.select((int) nuevoMaterial.getId());
            System.out.println("[OK] Materiales encontrados por ID: " + materialesPorId.size());

            // SELECT por código interno
            System.out.println("\nConsultando material por código interno...");
            List<Material> materialesPorCodigo = materialDB.select(nuevoMaterial.getCodigoInterno());
            System.out.println("[OK] Materiales encontrados por código: " + materialesPorCodigo.size());

            // UPDATE
            System.out.println("\nActualizando material...");
            int actualizados = materialDB.update(
                    nuevoMaterial.getCodigoInterno(),
                    Material.TipoMaterial.LIBRO,
                    "Material de Prueba Actualizado",
                    10);
            System.out.println("[OK] Materiales actualizados: " + actualizados);

            // DELETE
            System.out.println("\nEliminando material...");
            int eliminados = materialDB.delete(nuevoMaterial.getCodigoInterno());
            System.out.println("[OK] Materiales eliminados: " + eliminados);
        } else {
            System.out.println("[ERROR] No se pudo insertar el material");
        }
        System.out.println();
    }

    private static void probarLibroDB() throws SQLException {
        System.out.println("--- PRUEBA 3: LibroDB (CRUD) ---");
        LibroDB libroDB = new LibroDB();

        // INSERT
        System.out.println("Insertando libro...");
        Libro nuevoLibro = new Libro(
                0, "", "Java Programming", 10,
                "Herbert Schildt", 1200, "McGraw-Hill",
                "978-0071809252", 2014);

        Libro libroInsertado = libroDB.insert(nuevoLibro);
        if (libroInsertado != null) {
            System.out.println("[OK] Libro insertado: " + libroInsertado.getCodigoInterno());
            System.out.println("  ID: " + libroInsertado.getId());
            System.out.println("  Título: " + libroInsertado.getTitulo());
            System.out.println("  Autor: " + libroInsertado.getAutor());
            System.out.println("  ISBN: " + libroInsertado.getIsbn());

            // SELECT por ID
            System.out.println("\nConsultando libro por ID...");
            List<Libro> librosPorId = libroDB.select((int) libroInsertado.getId());
            System.out.println("[OK] Libros encontrados por ID: " + librosPorId.size());
            if (!librosPorId.isEmpty()) {
                System.out.println("  Título: " + librosPorId.get(0).getTitulo());
            }

            // SELECT por código interno
            System.out.println("\nConsultando libro por código interno...");
            List<Libro> librosPorCodigo = libroDB.select(libroInsertado.getCodigoInterno());
            System.out.println("[OK] Libros encontrados por código: " + librosPorCodigo.size());

            // UPDATE
            System.out.println("\nActualizando libro...");
            libroInsertado.setTitulo("Java: The Complete Reference");
            libroInsertado.setNumeroPaginas(1500);
            libroInsertado.setUnidadesDisponibles(15);
            int actualizados = libroDB.update(libroInsertado);
            System.out.println("[OK] Libros actualizados: " + actualizados);

            // Verificar actualización
            List<Libro> librosActualizados = libroDB.select(libroInsertado.getCodigoInterno());
            if (!librosActualizados.isEmpty()) {
                System.out.println("  Nuevo título: " + librosActualizados.get(0).getTitulo());
                System.out.println("  Nuevas páginas: " + librosActualizados.get(0).getNumeroPaginas());
            }

            // DELETE
            System.out.println("\nEliminando libro...");
            int eliminados = libroDB.delete(libroInsertado.getId());
            System.out.println("[OK] Libros eliminados: " + eliminados);
        } else {
            System.out.println("[ERROR] No se pudo insertar el libro");
        }
        System.out.println();
    }

    private static void probarRevistaDB() throws SQLException {
        System.out.println("--- PRUEBA 4: RevistaDB (CRUD) ---");
        RevistaDB revistaDB = new RevistaDB();

        // INSERT
        System.out.println("Insertando revista...");
        Revista nuevaRevista = new Revista(
                0, "", "National Geographic", 8,
                "National Geographic Society", "Mensual", "2024-10-01");

        Revista revistaInsertada = revistaDB.insert(nuevaRevista);
        if (revistaInsertada != null) {
            System.out.println("[OK] Revista insertada: " + revistaInsertada.getCodigoInterno());
            System.out.println("  ID: " + revistaInsertada.getId());
            System.out.println("  Título: " + revistaInsertada.getTitulo());
            System.out.println("  Editorial: " + revistaInsertada.getEditorial());
            System.out.println("  Periodicidad: " + revistaInsertada.getPeriodicidad());

            // SELECT por ID
            System.out.println("\nConsultando revista por ID...");
            List<Revista> revistasPorId = revistaDB.select((int) revistaInsertada.getId());
            System.out.println("[OK] Revistas encontradas por ID: " + revistasPorId.size());

            // SELECT por código interno
            System.out.println("\nConsultando revista por código interno...");
            List<Revista> revistasPorCodigo = revistaDB.select(revistaInsertada.getCodigoInterno());
            System.out.println("[OK] Revistas encontradas por código: " + revistasPorCodigo.size());

            // UPDATE
            System.out.println("\nActualizando revista...");
            revistaInsertada.setEditorial("Nat Geo Partners");
            revistaInsertada.setPeriodicidad("Quincenal");
            revistaInsertada.setUnidadesDisponibles(12);
            int actualizados = revistaDB.update(revistaInsertada);
            System.out.println("[OK] Revistas actualizadas: " + actualizados);

            // DELETE
            System.out.println("\nEliminando revista...");
            int eliminados = revistaDB.delete(revistaInsertada.getId());
            System.out.println("[OK] Revistas eliminadas: " + eliminados);
        } else {
            System.out.println("[ERROR] No se pudo insertar la revista");
        }
        System.out.println();
    }

    private static void probarCDDB() throws SQLException {
        System.out.println("--- PRUEBA 5: CDDB (CRUD) ---");
        CDDB cdDB = new CDDB();

        // INSERT
        System.out.println("Insertando CD...");
        CD nuevoCD = new CD(
                0, "", "Greatest Hits", 6,
                "Queen", "Rock", 75, 17);

        CD cdInsertado = cdDB.insert(nuevoCD);
        if (cdInsertado != null) {
            System.out.println("[OK] CD insertado: " + cdInsertado.getCodigoInterno());
            System.out.println("  ID: " + cdInsertado.getId());
            System.out.println("  Título: " + cdInsertado.getTitulo());
            System.out.println("  Artista: " + cdInsertado.getArtista());
            System.out.println("  Género: " + cdInsertado.getGenero());
            System.out.println("  Canciones: " + cdInsertado.getNumeroCanciones());

            // SELECT por ID
            System.out.println("\nConsultando CD por ID...");
            List<CD> cdsPorId = cdDB.select((int) cdInsertado.getId());
            System.out.println("[OK] CDs encontrados por ID: " + cdsPorId.size());

            // SELECT por código interno
            System.out.println("\nConsultando CD por código interno...");
            List<CD> cdsPorCodigo = cdDB.select(cdInsertado.getCodigoInterno());
            System.out.println("[OK] CDs encontrados por código: " + cdsPorCodigo.size());

            // UPDATE
            System.out.println("\nActualizando CD...");
            cdInsertado.setGenero("Classic Rock");
            cdInsertado.setDuracionMin(80);
            cdInsertado.setUnidadesDisponibles(10);
            int actualizados = cdDB.update(cdInsertado);
            System.out.println("[OK] CDs actualizados: " + actualizados);

            // DELETE
            System.out.println("\nEliminando CD...");
            int eliminados = cdDB.delete(cdInsertado.getId());
            System.out.println("[OK] CDs eliminados: " + eliminados);
        } else {
            System.out.println("[ERROR] No se pudo insertar el CD");
        }
        System.out.println();
    }

    private static void probarDVDDB() throws SQLException {
        System.out.println("--- PRUEBA 6: DVDDB (CRUD) ---");
        DVDDB dvdDB = new DVDDB();

        // INSERT
        System.out.println("Insertando DVD...");
        DVD nuevoDVD = new DVD(
                0, "", "The Matrix", 7,
                "The Wachowskis", "Ciencia Ficción", 136);

        DVD dvdInsertado = dvdDB.insert(nuevoDVD);
        if (dvdInsertado != null) {
            System.out.println("[OK] DVD insertado: " + dvdInsertado.getCodigoInterno());
            System.out.println("  ID: " + dvdInsertado.getId());
            System.out.println("  Título: " + dvdInsertado.getTitulo());
            System.out.println("  Director: " + dvdInsertado.getDirector());
            System.out.println("  Género: " + dvdInsertado.getGenero());
            System.out.println("  Duración: " + dvdInsertado.getDuracionMin() + " min");

            // SELECT por ID
            System.out.println("\nConsultando DVD por ID...");
            List<DVD> dvdsPorId = dvdDB.select((int) dvdInsertado.getId());
            System.out.println("[OK] DVDs encontrados por ID: " + dvdsPorId.size());

            // SELECT por código interno
            System.out.println("\nConsultando DVD por código interno...");
            List<DVD> dvdsPorCodigo = dvdDB.select(dvdInsertado.getCodigoInterno());
            System.out.println("[OK] DVDs encontrados por código: " + dvdsPorCodigo.size());

            // UPDATE
            System.out.println("\nActualizando DVD...");
            dvdInsertado.setGenero("Sci-Fi/Action");
            dvdInsertado.setDuracionMin(138);
            dvdInsertado.setUnidadesDisponibles(12);
            int actualizados = dvdDB.update(dvdInsertado);
            System.out.println("[OK] DVDs actualizados: " + actualizados);

            // DELETE
            System.out.println("\nEliminando DVD...");
            int eliminados = dvdDB.delete(dvdInsertado.getId());
            System.out.println("[OK] DVDs eliminados: " + eliminados);
        } else {
            System.out.println("[ERROR] No se pudo insertar el DVD");
        }
        System.out.println();
    }
}
