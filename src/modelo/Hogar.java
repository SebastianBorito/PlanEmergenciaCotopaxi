package modelo;

import java.util.ArrayList;

public class Hogar {

    private String apellido;
    private String direccion;
    private ArrayList<Integrante> integrantes;
    private KitEmergencia kit;
    private ArrayList<Asignacion> asignaciones;
    private ArrayList<TareaEvacuacion> tareas;
    private ArrayList<LugarEvacuacion> lugares;
    private ArrayList<Simulacro> simulacros;

    public Hogar(String apellido, String direccion) {
        this.apellido = apellido;
        this.direccion = direccion;
        integrantes = new ArrayList<>();
        kit = new KitEmergencia();
        asignaciones = new ArrayList<>();
        tareas = new ArrayList<>();
        lugares = new ArrayList<>();
        simulacros = new ArrayList<>();
    }

    public String getApellido() {
        return apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public ArrayList<Integrante> getIntegrantes() {
        return integrantes;
    }

    public KitEmergencia getKit() {
        return kit;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public ArrayList<TareaEvacuacion> getTareas() {
        return tareas;
    }

    public ArrayList<LugarEvacuacion> getLugares() {
        return lugares;
    }

    public ArrayList<Simulacro> getSimulacros() {
        return simulacros;
    }
}
