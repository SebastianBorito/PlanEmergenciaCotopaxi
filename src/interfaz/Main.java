package interfaz;

import modelo.Hogar;
import negocio.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("PLAN FAMILIAR DE EMERGENCIA VOLCANICA");
        String apellido = Valida.leerString("Apellido de la familia");
        String direccion = Valida.leerString("Direccion de la casa");

        Hogar hogar = new Hogar(apellido, direccion);
        GestorIntegrantes gestorIntegrantes = new GestorIntegrantes(hogar);
        GestorKit gestorKit = new GestorKit(hogar, gestorIntegrantes);
        GestorResponsabilidades gestorResponsabilidades = new GestorResponsabilidades(hogar, gestorIntegrantes);
        GestorEvacuacion gestorEvacuacion = new GestorEvacuacion(hogar);
        GestorSimulacros gestorSimulacros = new GestorSimulacros(hogar, gestorIntegrantes, gestorKit,
                gestorResponsabilidades, gestorEvacuacion);

        MenuIntegrantes menuIntegrantes = new MenuIntegrantes(gestorIntegrantes);
        MenuKit menuKit = new MenuKit(gestorKit, gestorIntegrantes);
        MenuResponsabilidades menuResponsabilidades = new MenuResponsabilidades(gestorResponsabilidades, gestorIntegrantes);
        MenuEvacuacion menuEvacuacion = new MenuEvacuacion(gestorEvacuacion);
        MenuSimulacros menuSimulacros = new MenuSimulacros(gestorSimulacros, gestorEvacuacion);

        int opc;
        do {
            menu();
            opc = Valida.leerEntero("Ingrese una opcion");

            switch (opc) {
                case 1:
                    menuIntegrantes.mostrar();
                    break;
                case 2:
                    menuKit.mostrar();
                    break;
                case 3:
                    menuResponsabilidades.mostrar();
                    break;
                case 4:
                    menuEvacuacion.mostrar();
                    break;
                case 5:
                    menuSimulacros.mostrar();
                    break;
                case 0:
                    System.out.println("Fin del programa");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        } while (opc != 0);
    }

    public static void menu() {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("1. Integrantes del hogar");
        System.out.println("2. Mochila de emergencia");
        System.out.println("3. Responsabilidades");
        System.out.println("4. Puntos de encuentro y evacuacion");
        System.out.println("5. Simulacros y evaluacion");
        System.out.println("0. Salir");
    }
}
