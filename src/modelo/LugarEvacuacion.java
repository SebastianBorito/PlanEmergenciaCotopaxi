package modelo;

public class LugarEvacuacion {

    private int id;
    private String nombre;
    private TipoLugar tipo;
    private String referencia;
    private double distancia; // metros
    private int prioridad;

    public LugarEvacuacion(int id, String nombre, TipoLugar tipo, String referencia, double distancia, int prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.referencia = referencia;
        this.distancia = distancia;
        this.prioridad = prioridad;
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

    public TipoLugar getTipo() {
        return tipo;
    }

    public void setTipo(TipoLugar tipo) {
        this.tipo = tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "LugarEvacuacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                ", referencia='" + referencia + '\'' +
                ", distancia=" + distancia +
                ", prioridad=" + prioridad +
                '}';
    }
}
