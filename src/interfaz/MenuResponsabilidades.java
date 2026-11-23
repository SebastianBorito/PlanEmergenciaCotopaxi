package interfaz;

import modelo.Adulto;
import modelo.Asignacion;
import modelo.Integrante;
import modelo.TareaEvacuacion;
import negocio.GestorIntegrantes;
import negocio.GestorResponsabilidades;
import negocio.ValidacionException;

public class MenuResponsabilidades {

    private GestorResponsabilidades gestor;
    private GestorIntegrantes gestorIntegrantes;

    public MenuResponsabilidades(GestorResponsabilidades gestor, GestorIntegrantes gestorIntegrantes) {
        this.gestor = gestor;
        this.gestorIntegrantes = gestorIntegrantes;
    }

    public void mostrar() {
        int opc;
        do {
            menu();
            opc = Valida.leerEntero("Ingrese una opcion");

            try {
                switch (opc) {
                    case 1:
                        asignar();
                        break;
                    case 2:
                        gestor.quitarAsignacion(Valida.leerEntero("Codigo del integrante"));
                        System.out.println("Asignacion eliminada");
                        break;
                    case 3:
                        for (Asignacion a : gestor.listarAsignaciones()) {
                            System.out.println(a);
                        }
                        break;
                    case 4:
                        validarCobertura();
                        break;
                    case 5:
                        registrarTarea();
                        break;
                    case 6:
                        for (TareaEvacuacion t : gestor.listarTareas()) {
                            System.out.println(t);
                        }
                        break;
                    case 7:
                        modificarTarea();
                        break;
                    case 8:
                        gestor.eliminarTarea(Valida.leerEntero("Codigo de la tarea"));
                        System.out.println("Tarea eliminada");
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
        System.out.println("\nRESPONSABILIDADES");
        System.out.println("1. Asignar responsable a un integrante vulnerable");
        System.out.println("2. Quitar responsable");
        System.out.println("3. Ver asignaciones");
        System.out.println("4. Revisar si todos tienen responsable");
        System.out.println("5. Registrar tarea de evacuacion");
        System.out.println("6. Ver tareas");
        System.out.println("7. Modificar tarea");
        System.out.println("8. Eliminar tarea");
        System.out.println("0. Volver");
    }

    private void mostrarIntegrantes() {
        for (Integrante i : gestorIntegrantes.listar()) {
            String texto = i.getId() + ". " + i.getNombre() + " (" + i.getTipo() + ")";
            if (i.esVulnerable()) {
                texto += " - vulnerable";
            }
            System.out.println(texto);
        }
    }

    // RF06
    private void asignar() throws ValidacionException {
        mostrarIntegrantes();
        int dependiente = Valida.leerEntero("Codigo del integrante vulnerable");
        int responsable = Valida.leerEntero("Codigo del adulto responsable");
        Asignacion a = gestor.asignarResponsable(dependiente, responsable);
        System.out.println("Listo: " + a);
    }

    // RF07
    private void validarCobertura() {
        System.out.println("Cobertura: " + Math.round(gestor.porcentajeCobertura()) + "% de los vulnerables tienen responsable");
        if (gestor.buscarSinResponsable().isEmpty() && gestor.buscarSobrecargados().isEmpty()) {
            System.out.println("El plan de responsabilidades esta COMPLETO");
            return;
        }
        System.out.println("El plan de responsabilidades esta INCOMPLETO");
        for (Integrante i : gestor.buscarSinResponsable()) {
            Adulto sugerido = gestor.sugerirResponsable(i);
            if (sugerido == null) {
                System.out.println("- " + i.getNombre() + " no tiene responsable y no hay adultos disponibles");
            } else {
                System.out.println("- " + i.getNombre() + " no tiene responsable. Sugerencia: " + sugerido.getNombre());
            }
        }
        for (Adulto a : gestor.buscarSobrecargados()) {
            System.out.println("- " + a.getNombre() + " no puede cuidar a las personas que tiene asignadas");
        }
    }

    // RF08
    private void registrarTarea() throws ValidacionException {
        String descripcion = Valida.leerString("Descripcion de la tarea");
        int prioridad = Valida.leerEntero("Prioridad (1 = mas urgente, 5 = menos urgente)");
        mostrarIntegrantes();
        int encargado = Valida.leerEntero("Codigo del adulto encargado");
        TareaEvacuacion t = gestor.registrarTarea(descripcion, prioridad, encargado);
        System.out.println("Tarea registrada con codigo " + t.getId());
    }

    private void modificarTarea() throws ValidacionException {
        TareaEvacuacion t = gestor.buscarTarea(Valida.leerEntero("Codigo de la tarea"));
        System.out.println("Modificando: " + t);
        String descripcion = Valida.leerString("Nueva descripcion");
        int prioridad = Valida.leerEntero("Nueva prioridad (1-5)");
        int encargado = Valida.leerEntero("Codigo del nuevo encargado");
        gestor.modificarTarea(t.getId(), descripcion, prioridad, encargado);
        System.out.println("Tarea modificada");
    }
}
