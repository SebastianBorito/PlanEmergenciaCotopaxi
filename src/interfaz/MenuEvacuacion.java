package interfaz;

import modelo.LugarEvacuacion;
import modelo.TipoLugar;
import negocio.GestorEvacuacion;
import negocio.ValidacionException;

public class MenuEvacuacion {

    private GestorEvacuacion gestor;

    public MenuEvacuacion(GestorEvacuacion gestor) {
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
                        registrar();
                        break;
                    case 2:
                        for (LugarEvacuacion l : gestor.listar()) {
                            System.out.println(l);
                        }
                        break;
                    case 3:
                        modificar();
                        break;
                    case 4:
                        LugarEvacuacion l = gestor.buscar(Valida.leerEntero("Codigo del lugar"));
                        if (Valida.confirmar("Seguro que quiere eliminar " + l.getNombre() + "?")) {
                            gestor.eliminar(l.getId());
                            System.out.println("Lugar eliminado");
                        }
                        break;
                    case 5:
                        calcularTiempos();
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
        System.out.println("\nPUNTOS DE ENCUENTRO Y EVACUACION");
        System.out.println("1. Registrar lugar");
        System.out.println("2. Ver lugares");
        System.out.println("3. Modificar lugar");
        System.out.println("4. Eliminar lugar");
        System.out.println("5. Calcular tiempos de evacuacion");
        System.out.println("0. Volver");
    }

    private TipoLugar leerTipo() {
        String[] opciones = {"Punto de encuentro", "Zona segura", "Albergue"};
        int op = Valida.leerOpcion("Tipo de lugar", opciones);
        return TipoLugar.values()[op - 1];
    }

    private void registrar() throws ValidacionException {
        String nombre = Valida.leerString("Nombre del lugar");
        TipoLugar tipo = leerTipo();
        String referencia = Valida.leerString("Direccion o referencia");
        double distancia = Valida.leerDouble("Distancia desde la casa (metros)");
        int prioridad = Valida.leerEntero("Prioridad (1 = primera opcion)");
        LugarEvacuacion l = gestor.registrar(nombre, tipo, referencia, distancia, prioridad);
        System.out.println("Lugar registrado con codigo " + l.getId());
    }

    private void modificar() throws ValidacionException {
        LugarEvacuacion l = gestor.buscar(Valida.leerEntero("Codigo del lugar"));
        System.out.println("Modificando: " + l);
        String nombre = Valida.leerString("Nuevo nombre");
        TipoLugar tipo = leerTipo();
        String referencia = Valida.leerString("Nueva referencia");
        double distancia = Valida.leerDouble("Nueva distancia (metros)");
        int prioridad = Valida.leerEntero("Nueva prioridad");
        gestor.modificar(l.getId(), nombre, tipo, referencia, distancia, prioridad);
        System.out.println("Lugar modificado");
    }

    // RF10
    private void calcularTiempos() throws ValidacionException {
        if (gestor.listar().isEmpty()) {
            System.out.println("Primero registre algun lugar");
            return;
        }
        double maximo = Valida.leerDouble("Tiempo maximo para evacuar en minutos (ej. 30)");
        System.out.println("La familia camina a " + gestor.movilidadMasLenta().getVelocidad()
                + " m/min (velocidad del integrante mas lento)");
        for (LugarEvacuacion l : gestor.listar()) {
            double minutos = gestor.estimarTiempo(l);
            String estado = "llegan a tiempo";
            if (minutos > maximo) {
                estado = "NO llegan a tiempo";
            }
            System.out.println("- " + l.getNombre() + ": " + Math.round(minutos) + " minutos, " + estado);
        }
        LugarEvacuacion recomendado = gestor.recomendarLugar(maximo);
        if (recomendado == null) {
            System.out.println("Ningun lugar se alcanza en el tiempo maximo");
        } else {
            System.out.println("Lugar recomendado: " + recomendado.getNombre());
        }
    }
}
