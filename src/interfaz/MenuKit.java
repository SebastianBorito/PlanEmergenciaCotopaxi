package interfaz;

import modelo.Herramienta;
import modelo.Suministro;
import negocio.GestorIntegrantes;
import negocio.GestorKit;
import negocio.ValidacionException;

import java.time.LocalDate;
import java.util.ArrayList;

public class MenuKit {

    private GestorKit gestor;
    private GestorIntegrantes gestorIntegrantes;

    public MenuKit(GestorKit gestor, GestorIntegrantes gestorIntegrantes) {
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
                        registrar();
                        break;
                    case 2:
                        listar();
                        break;
                    case 3:
                        modificar();
                        break;
                    case 4:
                        eliminar();
                        break;
                    case 5:
                        verificarSuficiencia();
                        break;
                    case 6:
                        revisarCaducidad();
                        break;
                    case 7:
                        gestor.marcarRevisado();
                        System.out.println("Revision guardada con fecha de hoy");
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
        System.out.println("\nMOCHILA DE EMERGENCIA");
        System.out.println("1. Registrar suministro");
        System.out.println("2. Ver suministros");
        System.out.println("3. Modificar suministro");
        System.out.println("4. Eliminar suministro");
        System.out.println("5. Verificar si la mochila alcanza");
        System.out.println("6. Revisar caducidad");
        System.out.println("7. Marcar mochila como revisada hoy");
        System.out.println("0. Volver");
    }

    private void registrar() throws ValidacionException {
        String[] categorias = {"Agua (litros)", "Alimento (raciones)", "Medicamento (dosis)", "Herramienta (unidades)"};
        int categoria = Valida.leerOpcion("Categoria", categorias);
        String nombre = Valida.leerString("Nombre");
        double cantidad = Valida.leerDouble("Cantidad");
        LocalDate caducidad = null;
        if (categoria != 4) {
            caducidad = Valida.leerFecha("Fecha de caducidad");
        }
        Suministro s = gestor.registrar(categoria, nombre, cantidad, caducidad);
        System.out.println("Suministro registrado con codigo " + s.getId());
    }

    private void listar() {
        if (gestor.listar().isEmpty()) {
            System.out.println("La mochila esta vacia");
        }
        for (Suministro s : gestor.listar()) {
            System.out.println(s + " [" + s.calcularEstado(30) + "]");
        }
    }

    private void modificar() throws ValidacionException {
        Suministro s = gestor.buscar(Valida.leerEntero("Codigo del suministro"));
        System.out.println("Modificando: " + s);
        String nombre = Valida.leerString("Nuevo nombre");
        double cantidad = Valida.leerDouble("Nueva cantidad");
        LocalDate caducidad = null;
        if (!(s instanceof Herramienta)) {
            caducidad = Valida.leerFecha("Nueva fecha de caducidad");
        }
        gestor.modificar(s.getId(), nombre, cantidad, caducidad);
        System.out.println("Suministro modificado");
    }

    private void eliminar() throws ValidacionException {
        Suministro s = gestor.buscar(Valida.leerEntero("Codigo del suministro"));
        if (Valida.confirmar("Seguro que quiere eliminar " + s.getNombre() + "?")) {
            gestor.eliminar(s.getId());
            System.out.println("Suministro eliminado");
        }
    }

    // RF04
    private void verificarSuficiencia() throws ValidacionException {
        int dias = Valida.leerEntero("Para cuantos dias (3 = 72 horas)");
        gestorIntegrantes.validarDias(dias);
        mostrarCategoria("Agua", gestorIntegrantes.calcularAguaTotal(dias), "litros");
        mostrarCategoria("Alimento", gestorIntegrantes.calcularRacionesTotal(dias), "raciones");
        mostrarCategoria("Medicamento", gestorIntegrantes.calcularDosisTotal(dias), "dosis");
        if (gestor.kitCompleto(dias)) {
            System.out.println("La mochila esta COMPLETA");
        } else {
            System.out.println("La mochila esta INCOMPLETA");
        }
    }

    private void mostrarCategoria(String categoria, double necesario, String unidad) {
        System.out.println(categoria + ": necesita " + String.format("%.1f", necesario) + " " + unidad
                + " - cubierto " + Math.round(gestor.calcularCobertura(categoria, necesario)) + "%"
                + " - falta " + String.format("%.1f", gestor.calcularFaltante(categoria, necesario)) + " " + unidad);
    }

    // RF05
    private void revisarCaducidad() throws ValidacionException {
        int diasAviso = Valida.leerEntero("Avisar con cuantos dias de anticipacion (ej. 30)");
        ArrayList<Suministro> lista = gestor.buscarPorCaducar(diasAviso);
        if (lista.isEmpty()) {
            System.out.println("Todos los suministros estan vigentes");
        }
        for (Suministro s : lista) {
            System.out.println("ALERTA " + s.calcularEstado(diasAviso) + ": " + s);
        }
        if (gestor.necesitaRevision()) {
            System.out.println("ALERTA: ya pasaron mas de 3 meses desde la ultima revision de la mochila");
        }
    }
}
