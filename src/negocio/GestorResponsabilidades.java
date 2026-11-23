package negocio;

import modelo.*;

import java.util.ArrayList;

// Modulo 3
public class GestorResponsabilidades {

    private Hogar hogar;
    private GestorIntegrantes gestorIntegrantes;
    private int contadorTareas = 1;

    public GestorResponsabilidades(Hogar hogar, GestorIntegrantes gestorIntegrantes) {
        this.hogar = hogar;
        this.gestorIntegrantes = gestorIntegrantes;
    }

    // RF06
    public Asignacion asignarResponsable(int idDependiente, int idResponsable) throws ValidacionException {
        Integrante dependiente = gestorIntegrantes.buscar(idDependiente);
        Integrante responsable = gestorIntegrantes.buscar(idResponsable);

        if (dependiente == responsable) {
            throw new ValidacionException("Una persona no puede ser responsable de si misma");
        }
        if (!dependiente.esVulnerable()) {
            throw new ValidacionException(dependiente.getNombre() + " no es vulnerable, no necesita responsable");
        }
        if (!(responsable instanceof Adulto)) {
            throw new ValidacionException("El responsable tiene que ser un adulto");
        }
        Adulto adulto = (Adulto) responsable;
        if (!adulto.puedeSerResponsable()) {
            throw new ValidacionException(adulto.getNombre() + " tiene movilidad reducida y no puede cuidar a otros");
        }
        if (buscarResponsable(dependiente) != null) {
            throw new ValidacionException(dependiente.getNombre() + " ya tiene un responsable");
        }
        if (contarDependientes(adulto) >= Adulto.MAX_DEPENDIENTES) {
            throw new ValidacionException(adulto.getNombre() + " ya tiene " + Adulto.MAX_DEPENDIENTES + " personas a su cargo");
        }

        Asignacion asignacion = new Asignacion(adulto, dependiente);
        hogar.getAsignaciones().add(asignacion);
        return asignacion;
    }

    public void quitarAsignacion(int idDependiente) throws ValidacionException {
        Integrante dependiente = gestorIntegrantes.buscar(idDependiente);
        for (Asignacion a : hogar.getAsignaciones()) {
            if (a.getDependiente() == dependiente) {
                hogar.getAsignaciones().remove(a);
                return;
            }
        }
        throw new ValidacionException(dependiente.getNombre() + " no tiene responsable asignado");
    }

    public ArrayList<Asignacion> listarAsignaciones() {
        return hogar.getAsignaciones();
    }

    public Adulto buscarResponsable(Integrante dependiente) {
        for (Asignacion a : hogar.getAsignaciones()) {
            if (a.getDependiente() == dependiente) {
                return a.getResponsable();
            }
        }
        return null;
    }

    public int contarDependientes(Adulto adulto) {
        int contador = 0;
        for (Asignacion a : hogar.getAsignaciones()) {
            if (a.getResponsable() == adulto) {
                contador++;
            }
        }
        return contador;
    }

    // RF07
    public ArrayList<Integrante> buscarSinResponsable() {
        ArrayList<Integrante> lista = new ArrayList<>();
        for (Integrante i : hogar.getIntegrantes()) {
            if (i.esVulnerable() && buscarResponsable(i) == null) {
                lista.add(i);
            }
        }
        return lista;
    }

    public ArrayList<Adulto> buscarSobrecargados() {
        ArrayList<Adulto> lista = new ArrayList<>();
        for (Integrante i : hogar.getIntegrantes()) {
            if (i instanceof Adulto) {
                Adulto a = (Adulto) i;
                int cargo = contarDependientes(a);
                if (cargo > Adulto.MAX_DEPENDIENTES || (!a.puedeSerResponsable() && cargo > 0)) {
                    lista.add(a);
                }
            }
        }
        return lista;
    }

    // adulto disponible con menos personas a cargo
    public Adulto sugerirResponsable(Integrante dependiente) {
        Adulto mejor = null;
        for (Integrante i : hogar.getIntegrantes()) {
            if (i instanceof Adulto && i != dependiente) {
                Adulto a = (Adulto) i;
                if (a.puedeSerResponsable() && contarDependientes(a) < Adulto.MAX_DEPENDIENTES) {
                    if (mejor == null || contarDependientes(a) < contarDependientes(mejor)) {
                        mejor = a;
                    }
                }
            }
        }
        return mejor;
    }

    public double porcentajeCobertura() {
        int vulnerables = 0;
        for (Integrante i : hogar.getIntegrantes()) {
            if (i.esVulnerable()) {
                vulnerables++;
            }
        }
        if (vulnerables == 0) {
            return 100;
        }
        int cubiertos = vulnerables - buscarSinResponsable().size();
        return cubiertos * 100.0 / vulnerables;
    }

    // RF08
    public TareaEvacuacion registrarTarea(String descripcion, int prioridad, int idEncargado) throws ValidacionException {
        Validador.validarTexto(descripcion, "descripcion");
        Validador.validarRango(prioridad, 1, 5, "prioridad");
        Adulto encargado = buscarAdulto(idEncargado);
        TareaEvacuacion tarea = new TareaEvacuacion(contadorTareas, descripcion, prioridad, encargado);
        contadorTareas++;
        hogar.getTareas().add(tarea);
        return tarea;
    }

    // ordenadas por prioridad
    public ArrayList<TareaEvacuacion> listarTareas() {
        ArrayList<TareaEvacuacion> ordenadas = new ArrayList<>();
        for (int p = 1; p <= 5; p++) {
            for (TareaEvacuacion t : hogar.getTareas()) {
                if (t.getPrioridad() == p) {
                    ordenadas.add(t);
                }
            }
        }
        return ordenadas;
    }

    public TareaEvacuacion buscarTarea(int id) throws ValidacionException {
        for (TareaEvacuacion t : hogar.getTareas()) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new ValidacionException("No existe una tarea con el codigo " + id);
    }

    public void modificarTarea(int id, String descripcion, int prioridad, int idEncargado) throws ValidacionException {
        TareaEvacuacion tarea = buscarTarea(id);
        Validador.validarTexto(descripcion, "descripcion");
        Validador.validarRango(prioridad, 1, 5, "prioridad");
        tarea.setDescripcion(descripcion);
        tarea.setPrioridad(prioridad);
        tarea.setEncargado(buscarAdulto(idEncargado));
    }

    public void eliminarTarea(int id) throws ValidacionException {
        hogar.getTareas().remove(buscarTarea(id));
    }

    private Adulto buscarAdulto(int id) throws ValidacionException {
        Integrante i = gestorIntegrantes.buscar(id);
        if (!(i instanceof Adulto)) {
            throw new ValidacionException("Las tareas solo se pueden asignar a adultos");
        }
        return (Adulto) i;
    }
}
