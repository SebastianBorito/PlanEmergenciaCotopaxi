package modelo;

public class Asignacion {

    private Adulto responsable;
    private Integrante dependiente;

    public Asignacion(Adulto responsable, Integrante dependiente) {
        this.responsable = responsable;
        this.dependiente = dependiente;
    }

    public Adulto getResponsable() {
        return responsable;
    }

    public Integrante getDependiente() {
        return dependiente;
    }

    @Override
    public String toString() {
        return "Asignacion{" +
                "responsable=" + responsable.getNombre() +
                ", dependiente=" + dependiente.getNombre() +
                '}';
    }
}
