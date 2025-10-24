package sv.edu.udb.Hijasclass;

import sv.edu.udb.Padresclass.Material;

public class Libro extends Material {
    private String autor;
    private int numeroPaginas;
    private String editorial;
    private String isbn;
    private int anioPublicacion;

    // Constructor actualizado
    public Libro(long id, String codigoInterno, String titulo, int unidadesDisponibles,
                 String autor, int numeroPaginas, String editorial, String isbn, int anioPublicacion) {
        super(id, codigoInterno, titulo, unidadesDisponibles, TipoMaterial.LIBRO); // 👈 se agrega el tipo
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
        this.editorial = editorial;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
    }

    //  Getters y Setters
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    //  Sobrescribimos toString para incluir más datos
    @Override
    public String toString() {
        return super.toString() +
               ", Autor: " + autor +
               ", Páginas: " + numeroPaginas +
               ", Editorial: " + editorial +
               ", ISBN: " + isbn +
               ", Año: " + anioPublicacion;
    }
}
