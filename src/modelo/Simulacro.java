package modelo;

import java.time.LocalDate;

public class Simulacro {

    private int id;
    private LocalDate fecha;
    private LugarEvacuacion destino;
    private double tiempoReal;     // minutos
    private double tiempoEstimado; // minutos
    private int participantes;
    private String observaciones;

    public Simulacro(int id, LocalDate fecha, LugarEvacuacion destino, double tiempoReal,
                     double tiempoEstimado, int participantes, String observaciones) {
        this.id = id;
        this.fecha = fecha;
        this.destino = destino;
        this.tiempoReal = tiempoReal;
        this.tiempoEstimado = tiempoEstimado;
        this.participantes = participantes;
        this.observaciones = observaciones;
    }

    // aprobado si no pasa del 10% del estimado
    public boolean estaAprobado() {
        return tiempoReal <= tiempoEstimado * 1.10;
    }

    public double calcularDiferencia() {
        return tiempoReal - tiempoEstimado;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LugarEvacuacion getDestino() {
        return destino;
    }

    public double getTiempoReal() {
        return tiempoReal;
    }

    public double getTiempoEstimado() {
        return tiempoEstimado;
    }

    public int getParticipantes() {
        return participantes;
    }

    public String getObservaciones() {
        return observaciones;
    }

    @Override
    public String toString() {
        return "Simulacro{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", destino=" + destino.getNombre() +
                ", tiempoReal=" + tiempoReal +
                ", tiempoEstimado=" + Math.round(tiempoEstimado) +
                ", participantes=" + participantes +
                ", aprobado=" + estaAprobado() +
                '}';
    }
}
