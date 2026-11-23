package modelo;

import java.time.LocalDate;
import java.time.Period;

public abstract class Integrante {

    private int id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String parentesco;
    private String condicionSalud;
    private Movilidad movilidad;
    private int dosisDiarias; // 0 si no toma

    public Integrante(int id, String nombre, LocalDate fechaNacimiento, String parentesco,
                      String condicionSalud, Movilidad movilidad, int dosisDiarias) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.parentesco = parentesco;
        this.condicionSalud = condicionSalud;
        this.movilidad = movilidad;
        this.dosisDiarias = dosisDiarias;
    }

    public abstract double calcularAguaDiaria();

    public abstract int calcularRacionesDiarias();

    public abstract String getTipo();

    // vulnerable si no tiene movilidad normal
    public boolean esVulnerable() {
        return movilidad != Movilidad.NORMAL;
    }

    public int calcularEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getParentesco() {
        return parentesco;
    }

    public String getCondicionSalud() {
        return condicionSalud;
    }

    public void setCondicionSalud(String condicionSalud) {
        this.condicionSalud = condicionSalud;
    }

    public Movilidad getMovilidad() {
        return movilidad;
    }

    public void setMovilidad(Movilidad movilidad) {
        this.movilidad = movilidad;
    }

    public int getDosisDiarias() {
        return dosisDiarias;
    }

    public void setDosisDiarias(int dosisDiarias) {
        this.dosisDiarias = dosisDiarias;
    }

    @Override
    public String toString() {
        return getTipo() + "{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + calcularEdad() +
                ", parentesco='" + parentesco + '\'' +
                ", condicionSalud='" + condicionSalud + '\'' +
                ", movilidad=" + movilidad +
                ", dosisDiarias=" + dosisDiarias +
                '}';
    }
}
