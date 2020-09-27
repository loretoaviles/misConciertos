package cl.inacap.misConciertos.dto;

public class Evento {
    private String nombre;
    private String fecha;
    private String genero;
    private String valor;
    private int calificacion;

    public Evento() {
        this.nombre = nombre;
        this.fecha = fecha;
        this.genero = genero;
        this.valor = valor;
        this.calificacion = calificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}