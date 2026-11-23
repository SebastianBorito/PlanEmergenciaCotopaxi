package interfaz;

import modelo.LugarEvacuacion;
import modelo.Simulacro;
import negocio.GestorEvacuacion;
import negocio.GestorSimulacros;
import negocio.ValidacionException;

import java.time.LocalDate;

public class MenuSimulacros {

    private GestorSimulacros gestor;
    private GestorEvacuacion gestorEvacuacion;

    public MenuSimulacros(GestorSimulacros gestor, GestorEvacuacion gestorEvacuacion) {
        this.gestor = gestor;
        this.gestorEvacuacion = gestorEvacuacion;
    }

    public void mostrar() {
        int opc;
        do {
            menu();
            opc = Valida.leerEntero("Ingrese una opcion");

            try {
                switch (opc) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        for (Simulacro s : gestor.listar()) {
                            System.out.println(s);
                        }
                        break;
                    case 3:
                        mostrarIndice();
                        break;
                    case 4:
                        System.out.println(gestor.generarReporte());
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (ValidacionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opc != 0);
    }

    public void menu() {
        System.out.println("\nSIMULACROS Y EVALUACION");
        System.out.println("1. Registrar simulacro");
        System.out.println("2. Ver simulacros");
        System.out.println("3. Calcular indice de preparacion");
        System.out.println("4. Ver reporte del plan familiar");
        System.out.println("0. Volver");
    }

    // RF11
    private void registrar() throws ValidacionException {
        LocalDate fecha = Valida.leerFecha("Fecha del simulacro");
        for (LugarEvacuacion l : gestorEvacuacion.listar()) {
            System.out.println(l);
        }
        int lugar = Valida.leerEntero("Codigo del lugar al que fueron");
        double tiempo = Valida.leerDouble("Minutos que se demoraron");
        int participantes = Valida.leerEntero("Cuantos integrantes participaron");
        String observaciones = Valida.leerString("Observaciones");
        Simulacro s = gestor.registrar(fecha, lugar, tiempo, participantes, observaciones);
        System.out.println("Tiempo estimado: " + Math.round(s.getTiempoEstimado()) + " min, tiempo real: "
                + s.getTiempoReal() + " min");
        if (s.estaAprobado()) {
            System.out.println("Simulacro APROBADO");
        } else {
            System.out.println("Simulacro NO APROBADO, se demoraron mas de lo esperado");
        }
    }

    // RF12
    private void mostrarIndice() {
        double indice = gestor.calcularIndice();
        System.out.println("Indice de preparacion: " + Math.round(indice) + "% - nivel " + gestor.obtenerNivel(indice));
        for (String r : gestor.obtenerRecomendaciones()) {
            System.out.println("- " + r);
        }
    }
}
