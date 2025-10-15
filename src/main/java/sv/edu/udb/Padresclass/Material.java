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
    private TipoMaterial tipo;

    // Constructor
    public Material(long id, String codigoInterno, String titulo, int unidadesDisponibles, TipoMaterial tipo) {
        this.id = id;
        this.codigoInterno = codigoInterno;
         this.tipo = tipo;
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
       
    }

    // Getters y Setters públicos
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

    public TipoMaterial getTipo() {
        return tipo;
    }

    public void setTipo(TipoMaterial tipo) {
        this.tipo = tipo;
    }

    public abstract String mostrarInformacion();
}
