package modelo;

public class TareaEvacuacion {

    private int id;
    private String descripcion;
    private int prioridad; // 1 = mas urgente
    private Adulto encargado;

    public TareaEvacuacion(int id, String descripcion, int prioridad, Adulto encargado) {
        this.id = id;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.encargado = encargado;
    }

    //metodos get y set

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Adulto getEncargado() {
        return encargado;
    }

    public void setEncargado(Adulto encargado) {
        this.encargado = encargado;
    }

    @Override
    public String toString() {
        return "TareaEvacuacion{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad=" + prioridad +
                ", encargado=" + encargado.getNombre() +
                '}';
    }
}
