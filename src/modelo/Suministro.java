package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Suministro {

    private int id;
    private String nombre;
    private double cantidad;
    private LocalDate fechaCaducidad;

    public Suministro(int id, String nombre, double cantidad, LocalDate fechaCaducidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaCaducidad = fechaCaducidad;
    }

    public abstract String getCategoria();

    public abstract String getUnidad();

    public EstadoSuministro calcularEstado(int diasAviso) {
        if (fechaCaducidad == null) {
            return EstadoSuministro.VIGENTE; // las herramientas no caducan
        }
        LocalDate hoy = LocalDate.now();
        if (fechaCaducidad.isBefore(hoy)) {
            return EstadoSuministro.CADUCADO;
        }
        long diasQueFaltan = ChronoUnit.DAYS.between(hoy, fechaCaducidad);
        if (diasQueFaltan <= diasAviso) {
            return EstadoSuministro.POR_CADUCAR;
        }
        return EstadoSuministro.VIGENTE;
    }

    public boolean estaCaducado() {
        return calcularEstado(0) == EstadoSuministro.CADUCADO;
    }

    //metodos get y set

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String toString() {
        return getCategoria() + "{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad + " " + getUnidad() +
                ", fechaCaducidad=" + fechaCaducidad +
                '}';
    }
}
