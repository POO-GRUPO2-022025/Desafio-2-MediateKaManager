package sv.edu.udb.Padresclass;

public abstract class Material {

    // Enum para los tipos de material
    public enum TipoMaterial {
        LIBRO,
        REVISTA,
        CD,
        DVD
    }

    // Atributos
    private long id;
    private String codigoInterno;
    private String titulo;
    private int unidadesDisponibles;

    // Constructor
    public Material(long id, String codigoInterno, String titulo, int unidadesDisponibles) {
        this.id = id;
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public abstract TipoMaterial getTipoMaterial();
    public abstract String mostrarInformacion();
    
    @Override
    public String toString() {
        return "ID: " + id +
               ", Código: " + codigoInterno +
               ", Título: " + titulo +
               ", Unidades: " + unidadesDisponibles +
               ", Tipo: " + getTipoMaterial();
    }
}
