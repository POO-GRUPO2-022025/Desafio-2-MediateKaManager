package sv.edu.udb.Padresclass;

public class Material {

    // Enum para los tipos de material
    public enum TipoMaterial {
        LIBRO,
        REVISTA,
        CD,
        DVD
    }

    //  Atributos comunes
    private long id;
    private String codigoInterno;
    private String titulo;
    private int unidadesDisponibles;
    private TipoMaterial tipoMaterial; 

    //  Constructor
    public Material(long id, String codigoInterno, String titulo, int unidadesDisponibles, TipoMaterial tipoMaterial) {
        this.id = id;
        this.codigoInterno = codigoInterno;
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
        this.tipoMaterial = tipoMaterial;
    }

    //  Getters y Setters
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

    public TipoMaterial getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(TipoMaterial tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    //  Método para mostrar información
    public String mostrarInformacion() {
        return "ID: " + id +
               ", Código: " + codigoInterno +
               ", Título: " + titulo +
               ", Unidades disponibles: " + unidadesDisponibles +
               ", Tipo: " + tipoMaterial;
    }

    //  Sobrescritura del método toString()
    @Override
    public String toString() {
        return mostrarInformacion();
    }
}

