package sv.edu.udb.Hijasclass;

import sv.edu.udb.Padresclass.Material;

public class Revista extends Material {
    private String editorial;
    private String periodicidad;
    private String fechaPublicacion; // Puedes usar LocalDate si prefieres

    // Constructor
    public Revista(long id, String codigoInterno, String titulo, int unidadesDisponibles,
                   String editorial, String periodicidad, String fechaPublicacion) {
        super(id, codigoInterno, titulo, unidadesDisponibles);
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    // Implementación del método abstracto getTipoMaterial()
    @Override
    public TipoMaterial getTipoMaterial() {
        return TipoMaterial.REVISTA;
    }

    // Implementación del método abstracto mostrarInformacion()
    @Override
    public String mostrarInformacion() {
        return toString() +
               ", Editorial: " + editorial +
               ", Periodicidad: " + periodicidad +
               ", Fecha de publicación: " + fechaPublicacion;
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
}
