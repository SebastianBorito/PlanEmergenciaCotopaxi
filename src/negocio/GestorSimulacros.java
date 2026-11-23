package negocio;

import modelo.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

// Modulo 5
public class GestorSimulacros {

    private Hogar hogar;
    private GestorIntegrantes gestorIntegrantes;
    private GestorKit gestorKit;
    private GestorResponsabilidades gestorResponsabilidades;
    private GestorEvacuacion gestorEvacuacion;
    private int contadorId = 1;

    public GestorSimulacros(Hogar hogar, GestorIntegrantes gestorIntegrantes, GestorKit gestorKit,
                            GestorResponsabilidades gestorResponsabilidades, GestorEvacuacion gestorEvacuacion) {
        this.hogar = hogar;
        this.gestorIntegrantes = gestorIntegrantes;
        this.gestorKit = gestorKit;
        this.gestorResponsabilidades = gestorResponsabilidades;
        this.gestorEvacuacion = gestorEvacuacion;
    }

    // RF11
    public Simulacro registrar(LocalDate fecha, int idLugar, double tiempoReal, int participantes,
                               String observaciones) throws ValidacionException {
        Validador.validarFechaPasada(fecha, "fecha");
        LugarEvacuacion lugar = gestorEvacuacion.buscar(idLugar);
        Validador.validarPositivo(tiempoReal, "tiempo real");
        Validador.validarRango(participantes, 1, hogar.getIntegrantes().size(), "participantes");

        double estimado = gestorEvacuacion.estimarTiempo(lugar);
        Simulacro s = new Simulacro(contadorId, fecha, lugar, tiempoReal, estimado, participantes, observaciones);
        contadorId++;
        hogar.getSimulacros().add(s);
        return s;
    }

    public ArrayList<Simulacro> listar() {
        return hogar.getSimulacros();
    }

    public Simulacro ultimoSimulacro() {
        Simulacro ultimo = null;
        for (Simulacro s : hogar.getSimulacros()) {
            if (ultimo == null || s.getFecha().isAfter(ultimo.getFecha())) {
                ultimo = s;
            }
        }
        return ultimo;
    }

    // RF12: kit 30%, vigencia 15%, responsables 25%, lugares 15%, simulacros 15%
    public double calcularIndice() {
        if (hogar.getIntegrantes().isEmpty()) {
            return 0;
        }
        double kit = (gestorKit.calcularCobertura("Agua", gestorIntegrantes.calcularAguaTotal(3))
                + gestorKit.calcularCobertura("Alimento", gestorIntegrantes.calcularRacionesTotal(3))
                + gestorKit.calcularCobertura("Medicamento", gestorIntegrantes.calcularDosisTotal(3))) / 3;

        return kit * 0.30
                + gestorKit.porcentajeVigentes() * 0.15
                + gestorResponsabilidades.porcentajeCobertura() * 0.25
                + gestorEvacuacion.puntajeLugares() * 0.15
                + puntajeSimulacros() * 0.15;
    }

    private double puntajeSimulacros() {
        Simulacro ultimo = ultimoSimulacro();
        if (ultimo == null) {
            return 0;
        }
        long dias = ChronoUnit.DAYS.between(ultimo.getFecha(), LocalDate.now());
        if (dias > 90) {
            return 0;
        }
        if (ultimo.estaAprobado()) {
            return 100;
        }
        return 50;
    }

    public String obtenerNivel(double indice) {
        if (indice < 50) {
            return "BAJO";
        } else if (indice < 80) {
            return "MEDIO";
        }
        return "ALTO";
    }

    public ArrayList<String> obtenerRecomendaciones() {
        ArrayList<String> lista = new ArrayList<>();
        if (hogar.getIntegrantes().isEmpty()) {
            lista.add("Registre a los integrantes del hogar");
            return lista;
        }
        try {
            if (!gestorKit.kitCompleto(3)) {
                lista.add("Complete la mochila: faltan suministros para 72 horas");
            }
        } catch (ValidacionException e) {
            lista.add(e.getMessage());
        }
        if (gestorKit.porcentajeVigentes() < 100) {
            lista.add("Cambie los suministros caducados o registre suministros");
        }
        if (gestorKit.necesitaRevision()) {
            lista.add("Revise la mochila, ya pasaron mas de 3 meses");
        }
        if (!gestorResponsabilidades.buscarSinResponsable().isEmpty()) {
            lista.add("Asigne un responsable a cada integrante vulnerable");
        }
        if (gestorEvacuacion.puntajeLugares() < 100) {
            lista.add("Registre un punto de encuentro y una zona segura o albergue");
        }
        if (puntajeSimulacros() < 100) {
            lista.add("Haga un simulacro y practique la ruta de evacuacion");
        }
        return lista;
    }

    // RF13
    public String generarReporte() {
        String r = "PLAN FAMILIAR DE EMERGENCIA\n";
        r += "Familia: " + hogar.getApellido() + "\n";
        r += "Direccion: " + hogar.getDireccion() + "\n";
        r += "Fecha: " + LocalDate.now() + "\n\n";

        r += "INTEGRANTES\n";
        for (Integrante i : hogar.getIntegrantes()) {
            r += "  " + i + "\n";
        }

        r += "\nMOCHILA DE EMERGENCIA\n";
        for (Suministro s : hogar.getKit().getSuministros()) {
            r += "  " + s + " [" + s.calcularEstado(30) + "]\n";
        }

        r += "\nRESPONSABLES Y TAREAS\n";
        for (Asignacion a : hogar.getAsignaciones()) {
            r += "  " + a + "\n";
        }
        for (TareaEvacuacion t : gestorResponsabilidades.listarTareas()) {
            r += "  " + t + "\n";
        }

        r += "\nLUGARES DE EVACUACION\n";
        for (LugarEvacuacion l : hogar.getLugares()) {
            r += "  " + l + "\n";
        }

        r += "\nULTIMO SIMULACRO\n";
        Simulacro ultimo = ultimoSimulacro();
        if (ultimo == null) {
            r += "  No hay simulacros\n";
        } else {
            r += "  " + ultimo + "\n";
        }

        double indice = calcularIndice();
        r += "\nINDICE DE PREPARACION: " + Math.round(indice) + "% (" + obtenerNivel(indice) + ")\n";
        for (String rec : obtenerRecomendaciones()) {
            r += "  - " + rec + "\n";
        }
        return r;
    }
}
