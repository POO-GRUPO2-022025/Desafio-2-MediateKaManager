package sv.edu.udb.Hijasclass;

import sv.edu.udb.Padresclass.Material;

public class CD extends Material {

    private String artista;
    private String genero;
    private int duracionMin;
    private int numeroCanciones;

    public CD(long id, String codigoInterno, String titulo, int unidadesDisponibles,
              String artista, String genero, int duracionMin, int numeroCanciones) {

        super(id, codigoInterno, titulo, unidadesDisponibles);
        this.artista = artista;
        this.genero = genero;
        this.duracionMin = duracionMin;
        this.numeroCanciones = numeroCanciones;
    }

    // Implementación de métodos abstractos
    @Override
    public TipoMaterial getTipoMaterial() {
        return TipoMaterial.CD;
    }

    @Override
    public String mostrarInformacion() {
        return toString() +
               ", Artista: " + artista +
               ", Género: " + genero +
               ", Duración: " + duracionMin + " min" +
               ", Canciones: " + numeroCanciones;
    }

    // Getters y Setters
    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getDuracionMin() { return duracionMin; }
    public void setDuracionMin(int duracionMin) { this.duracionMin = duracionMin; }

    public int getNumeroCanciones() { return numeroCanciones; }
    public void setNumeroCanciones(int numeroCanciones) { this.numeroCanciones = numeroCanciones; }
}
