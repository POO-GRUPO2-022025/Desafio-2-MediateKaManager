package sv.edu.udb.Hijasclass;

import sv.edu.udb.Padresclass.Material;

public class DVD extends Material {

    private String director;
    private String genero;
    private int duracionMin;

    public DVD(long id, String codigoInterno, String titulo, int unidadesDisponibles,
               String director, String genero, int duracionMin) {

        super(id, codigoInterno, titulo, unidadesDisponibles, TipoMaterial.DVD);
        this.director = director;
        this.genero = genero;
        this.duracionMin = duracionMin;
    }

    // Getters y Setters
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getDuracionMin() { return duracionMin; }
    public void setDuracionMin(int duracionMin) { this.duracionMin = duracionMin; }

    // toString sobrescrito
    @Override
    public String toString() {
        return super.toString() +
               ", Director: " + director +
               ", Género: " + genero +
               ", Duración: " + duracionMin + " min";
    }
}


