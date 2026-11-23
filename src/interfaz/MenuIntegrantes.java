package interfaz;

import modelo.Integrante;
import modelo.Movilidad;
import negocio.GestorIntegrantes;
import negocio.ValidacionException;

import java.time.LocalDate;

public class MenuIntegrantes {

    private GestorIntegrantes gestor;

    public MenuIntegrantes(GestorIntegrantes gestor) {
        this.gestor = gestor;
    }

    public void mostrar() {
        int opc;
        do {
            menu();
            opc = Valida.leerEntero("Ingrese una opcion");

            try {
                switch (opc) {
                    case 1:
                        registrarPersona();
                        break;
                    case 2:
                        registrarMascota();
                        break;
                    case 3:
                        listar();
                        break;
                    case 4:
                        modificar();
                        break;
                    case 5:
                        eliminar();
                        break;
                    case 6:
                        calcularNecesidades();
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
        System.out.println("\nINTEGRANTES DEL HOGAR");
        System.out.println("1. Registrar persona");
        System.out.println("2. Registrar mascota");
        System.out.println("3. Ver integrantes");
        System.out.println("4. Modificar integrante");
        System.out.println("5. Eliminar integrante");
        System.out.println("6. Calcular necesidades del hogar");
        System.out.println("0. Volver");
    }

    private Movilidad leerMovilidad() {
        String[] opciones = {"Normal", "Reducida", "Requiere asistencia"};
        int op = Valida.leerOpcion("Movilidad", opciones);
        return Movilidad.values()[op - 1];
    }

    private void registrarPersona() throws ValidacionException {
        String nombre = Valida.leerString("Nombre");
        LocalDate fecha = Valida.leerFecha("Fecha de nacimiento");
        String parentesco = Valida.leerString("Parentesco");
        String salud = Valida.leerString("Condicion de salud");
        Movilidad movilidad = leerMovilidad();
        int dosis = Valida.leerEntero("Dosis de medicina al dia (0 si no toma)");
        Integrante i = gestor.registrarPersona(nombre, fecha, parentesco, salud, movilidad, dosis);
        System.out.println("Se registro como " + i.getTipo() + " con codigo " + i.getId());
    }

    private void registrarMascota() throws ValidacionException {
        String nombre = Valida.leerString("Nombre de la mascota");
        LocalDate fecha = Valida.leerFecha("Fecha de nacimiento aproximada");
        String especie = Valida.leerString("Especie");
        double peso = Valida.leerDouble("Peso en kg");
        String salud = Valida.leerString("Condicion de salud");
        Movilidad movilidad = leerMovilidad();
        int dosis = Valida.leerEntero("Dosis de medicina al dia (0 si no toma)");
        Integrante m = gestor.registrarMascota(nombre, fecha, especie, peso, salud, movilidad, dosis);
        System.out.println("Mascota registrada con codigo " + m.getId());
    }

    private void listar() {
        if (gestor.listar().isEmpty()) {
            System.out.println("No hay integrantes registrados");
        }
        for (Integrante i : gestor.listar()) {
            System.out.println(i);
        }
    }

    private void modificar() throws ValidacionException {
        Integrante i = gestor.buscar(Valida.leerEntero("Codigo del integrante"));
        System.out.println("Modificando a: " + i);
        String nombre = Valida.leerString("Nuevo nombre");
        String salud = Valida.leerString("Nueva condicion de salud");
        Movilidad movilidad = leerMovilidad();
        int dosis = Valida.leerEntero("Dosis de medicina al dia");
        gestor.modificar(i.getId(), nombre, salud, movilidad, dosis);
        System.out.println("Integrante modificado");
    }

    private void eliminar() throws ValidacionException {
        Integrante i = gestor.buscar(Valida.leerEntero("Codigo del integrante"));
        if (Valida.confirmar("Seguro que quiere eliminar a " + i.getNombre() + "?")) {
            gestor.eliminar(i.getId());
            System.out.println("Integrante eliminado");
        }
    }

    // RF02
    private void calcularNecesidades() throws ValidacionException {
        int dias = Valida.leerEntero("Para cuantos dias (3 = 72 horas)");
        gestor.validarDias(dias);
        System.out.println("\nNecesidades para " + dias + " dias:");
        for (Integrante i : gestor.listar()) {
            System.out.println("- " + i.getNombre() + " (" + i.getTipo() + "): "
                    + String.format("%.1f", i.calcularAguaDiaria() * dias) + " L de agua, "
                    + (i.calcularRacionesDiarias() * dias) + " raciones, "
                    + (i.getDosisDiarias() * dias) + " dosis");
        }
        System.out.println("TOTAL: " + String.format("%.1f", gestor.calcularAguaTotal(dias)) + " L de agua, "
                + gestor.calcularRacionesTotal(dias) + " raciones, "
                + gestor.calcularDosisTotal(dias) + " dosis de medicina");
    }
}
