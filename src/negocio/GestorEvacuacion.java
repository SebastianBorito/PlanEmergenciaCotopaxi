package negocio;

import modelo.*;

import java.util.ArrayList;

// Modulo 4
public class GestorEvacuacion {

    private Hogar hogar;
    private int contadorId = 1;

    public GestorEvacuacion(Hogar hogar) {
        this.hogar = hogar;
    }

    // RF09
    public LugarEvacuacion registrar(String nombre, TipoLugar tipo, String referencia, double distancia, int prioridad) throws ValidacionException {
        validarDatos(nombre, referencia, distancia, prioridad);
        LugarEvacuacion lugar = new LugarEvacuacion(contadorId, nombre, tipo, referencia, distancia, prioridad);
        contadorId++;
        hogar.getLugares().add(lugar);
        return lugar;
    }

    public ArrayList<LugarEvacuacion> listar() {
        return hogar.getLugares();
    }

    public LugarEvacuacion buscar(int id) throws ValidacionException {
        for (LugarEvacuacion l : hogar.getLugares()) {
            if (l.getId() == id) {
                return l;
            }
        }
        throw new ValidacionException("No existe un lugar con el codigo " + id);
    }

    public void modificar(int id, String nombre, TipoLugar tipo, String referencia, double distancia, int prioridad) throws ValidacionException {
        LugarEvacuacion lugar = buscar(id);
        validarDatos(nombre, referencia, distancia, prioridad);
        lugar.setNombre(nombre);
        lugar.setTipo(tipo);
        lugar.setReferencia(referencia);
        lugar.setDistancia(distancia);
        lugar.setPrioridad(prioridad);
    }

    public void eliminar(int id) throws ValidacionException {
        LugarEvacuacion lugar = buscar(id);
        for (Simulacro s : hogar.getSimulacros()) {
            if (s.getDestino() == lugar) {
                throw new ValidacionException("No se puede eliminar porque tiene simulacros registrados");
            }
        }
        hogar.getLugares().remove(lugar);
    }

    private void validarDatos(String nombre, String referencia, double distancia, int prioridad) throws ValidacionException {
        Validador.validarTexto(nombre, "nombre");
        Validador.validarTexto(referencia, "referencia");
        Validador.validarPositivo(distancia, "distancia");
        Validador.validarRango(prioridad, 1, 10, "prioridad");
        if (distancia > 50000) {
            throw new ValidacionException("La distancia no puede pasar de 50 km");
        }
    }

    // RF10: se toma la velocidad del mas lento
    public Movilidad movilidadMasLenta() throws ValidacionException {
        if (hogar.getIntegrantes().isEmpty()) {
            throw new ValidacionException("Primero registre a los integrantes del hogar");
        }
        Movilidad lenta = Movilidad.NORMAL;
        for (Integrante i : hogar.getIntegrantes()) {
            if (i.getMovilidad().getVelocidad() < lenta.getVelocidad()) {
                lenta = i.getMovilidad();
            }
        }
        return lenta;
    }

    public double estimarTiempo(LugarEvacuacion lugar) throws ValidacionException {
        return lugar.getDistancia() / movilidadMasLenta().getVelocidad();
    }

    public LugarEvacuacion recomendarLugar(double tiempoMaximo) throws ValidacionException {
        Validador.validarPositivo(tiempoMaximo, "tiempo maximo");
        LugarEvacuacion mejor = null;
        for (LugarEvacuacion l : hogar.getLugares()) {
            if (estimarTiempo(l) <= tiempoMaximo) {
                if (mejor == null || l.getPrioridad() < mejor.getPrioridad()) {
                    mejor = l;
                }
            }
        }
        return mejor;
    }

    // 50 por punto de encuentro y 50 por refugio
    public double puntajeLugares() {
        boolean hayPunto = false;
        boolean hayRefugio = false;
        for (LugarEvacuacion l : hogar.getLugares()) {
            if (l.getTipo() == TipoLugar.PUNTO_ENCUENTRO) {
                hayPunto = true;
            } else {
                hayRefugio = true;
            }
        }
        double puntaje = 0;
        if (hayPunto) {
            puntaje = puntaje + 50;
        }
        if (hayRefugio) {
            puntaje = puntaje + 50;
        }
        return puntaje;
    }
}
