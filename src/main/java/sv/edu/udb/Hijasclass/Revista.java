package sv.edu.udb.Hijasclass;

import sv.edu.udb.Padresclass.Material;
import sv.edu.udb.Padresclass.Material.TipoMaterial;

public class Revista extends Material {
    private String editorial;
    private String periodicidad;
    private String fechaPublicacion;

    public Revista(long id, String codigoInterno, String titulo, int unidadesDisponibles,
                   String editorial, String periodicidad, String fechaPublicacion) {
        super(id, codigoInterno, titulo, unidadesDisponibles, TipoMaterial.REVISTA);
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    // Getters y Setters
    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public TipoMaterial getTipoMaterial() {
        return TipoMaterial.REVISTA;
    }

    @Override
    public String toString() {
        return "📰 REVISTA\n" +
               "Título: " + getTitulo() + "\n" +
               "Editorial: " + editorial + "\n" +
               "Periodicidad: " + periodicidad + "\n" +
               "Fecha de Publicación: " + fechaPublicacion + "\n" +
               "Código Interno: " + getCodigoInterno() + "\n" +
               "Unidades Disponibles: " + getUnidadesDisponibles() + "\n" +
               "Tipo: " + getTipoMaterial();
    }
}
