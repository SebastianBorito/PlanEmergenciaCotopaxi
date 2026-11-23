package interfaz;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Valida {
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String leerString(String mensaje) {
        String valor;
        do {
            System.out.print(mensaje + ": ");
            valor = sc.nextLine();
            if (valor == null || valor.trim().isEmpty()) {
                System.out.println("El dato no puede estar nulo ni vacio");
            }
        } while (valor == null || valor.trim().isEmpty());
        return valor.trim();
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + ": ");
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero");
            }
        }
    }

    public static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + ": ");
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido");
            }
        }
    }

    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " (dd/mm/aaaa): ");
                return LocalDate.parse(sc.nextLine().trim(), formato);
            } catch (DateTimeParseException e) {
                System.out.println("La fecha debe tener el formato dd/mm/aaaa");
            }
        }
    }

    public static int leerOpcion(String mensaje, String[] opciones) {
        System.out.println(mensaje);
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        int opc;
        do {
            opc = leerEntero("Ingrese una opcion");
            if (opc < 1 || opc > opciones.length) {
                System.out.println("Opcion no valida");
            }
        } while (opc < 1 || opc > opciones.length);
        return opc;
    }

    public static boolean confirmar(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        return sc.nextLine().trim().equalsIgnoreCase("s");
    }
}
