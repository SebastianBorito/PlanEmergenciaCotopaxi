package negocio;

import java.time.LocalDate;

public class Validador {

    public static void validarTexto(String texto, String campo) throws ValidacionException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio");
        }
    }

    public static void validarNombre(String nombre, String campo) throws ValidacionException {
        validarTexto(nombre, campo);
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new ValidacionException("El campo " + campo + " solo puede tener letras");
        }
    }

    public static void validarPositivo(double numero, String campo) throws ValidacionException {
        if (numero <= 0) {
            throw new ValidacionException("El campo " + campo + " debe ser mayor a 0");
        }
    }

    public static void validarRango(int numero, int minimo, int maximo, String campo) throws ValidacionException {
        if (numero < minimo || numero > maximo) {
            throw new ValidacionException("El campo " + campo + " debe estar entre " + minimo + " y " + maximo);
        }
    }

    public static void validarFechaPasada(LocalDate fecha, String campo) throws ValidacionException {
        if (fecha.isAfter(LocalDate.now())) {
            throw new ValidacionException("El campo " + campo + " no puede ser una fecha futura");
        }
    }
}
