package sv.edu.udb.Hijasclass;

import sv.edu.udb.Padresclass.Material;

public class CD extends Material {

    private String artista;
    private String genero;
    private int duracionMin;
    private int numeroCanciones;

    // Constructor
    public CD(long id, String codigoInterno, String titulo, int unidadesDisponibles,
              String artista, String genero, int duracionMin, int numeroCanciones) {

        super(id, codigoInterno, titulo, unidadesDisponibles, TipoMaterial.CD);
        this.artista = artista;
        this.genero = genero;
        this.duracionMin = duracionMin;
        this.numeroCanciones = numeroCanciones;
    }

    //  Getters y Setters
    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getDuracionMin() { return duracionMin; }
    public void setDuracionMin(int duracionMin) { this.duracionMin = duracionMin; }

    public int getNumeroCanciones() { return numeroCanciones; }
    public void setNumeroCanciones(int numeroCanciones) { this.numeroCanciones = numeroCanciones; }

    //  toString sobrescrito
    @Override
    public String toString() {
        return super.toString() +
               ", Artista: " + artista +
               ", Género: " + genero +
               ", Duración: " + duracionMin + " min" +
               ", Canciones: " + numeroCanciones;
    }
}
