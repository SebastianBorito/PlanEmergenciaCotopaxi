package negocio;

import modelo.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

// Modulo 1
public class GestorIntegrantes {

    private Hogar hogar;
    private int contadorId = 1;

    public GestorIntegrantes(Hogar hogar) {
        this.hogar = hogar;
    }

    // RF01: el tipo depende de la edad
    public Integrante registrarPersona(String nombre, LocalDate fechaNacimiento, String parentesco,
                                       String salud, Movilidad movilidad, int dosis) throws ValidacionException {
        Validador.validarNombre(nombre, "nombre");
        Validador.validarFechaPasada(fechaNacimiento, "fecha de nacimiento");
        Validador.validarTexto(parentesco, "parentesco");
        Validador.validarTexto(salud, "condicion de salud");
        Validador.validarRango(dosis, 0, 20, "dosis diarias");

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad > 120) {
            throw new ValidacionException("La fecha de nacimiento no es valida");
        }

        Integrante nuevo;
        if (edad < 18) {
            nuevo = new Nino(contadorId, nombre, fechaNacimiento, parentesco, salud, movilidad, dosis);
        } else if (edad >= 65) {
            nuevo = new AdultoMayor(contadorId, nombre, fechaNacimiento, parentesco, salud, movilidad, dosis);
        } else {
            nuevo = new Adulto(contadorId, nombre, fechaNacimiento, parentesco, salud, movilidad, dosis);
        }
        contadorId++;
        hogar.getIntegrantes().add(nuevo);
        return nuevo;
    }

    public Mascota registrarMascota(String nombre, LocalDate fechaNacimiento, String especie, double peso,
                                    String salud, Movilidad movilidad, int dosis) throws ValidacionException {
        Validador.validarNombre(nombre, "nombre");
        Validador.validarFechaPasada(fechaNacimiento, "fecha de nacimiento");
        Validador.validarNombre(especie, "especie");
        Validador.validarTexto(salud, "condicion de salud");
        Validador.validarPositivo(peso, "peso");
        Validador.validarRango(dosis, 0, 20, "dosis diarias");
        if (peso > 150) {
            throw new ValidacionException("El peso de la mascota no puede pasar de 150 kg");
        }

        Mascota mascota = new Mascota(contadorId, nombre, fechaNacimiento, salud, movilidad, dosis, especie, peso);
        contadorId++;
        hogar.getIntegrantes().add(mascota);
        return mascota;
    }

    public ArrayList<Integrante> listar() {
        return hogar.getIntegrantes();
    }

    public Integrante buscar(int id) throws ValidacionException {
        for (Integrante i : hogar.getIntegrantes()) {
            if (i.getId() == id) {
                return i;
            }
        }
        throw new ValidacionException("No existe un integrante con el codigo " + id);
    }

    public void modificar(int id, String nombre, String salud, Movilidad movilidad, int dosis) throws ValidacionException {
        Integrante integrante = buscar(id);
        Validador.validarNombre(nombre, "nombre");
        Validador.validarTexto(salud, "condicion de salud");
        Validador.validarRango(dosis, 0, 20, "dosis diarias");
        integrante.setNombre(nombre);
        integrante.setCondicionSalud(salud);
        integrante.setMovilidad(movilidad);
        integrante.setDosisDiarias(dosis);
    }

    // tambien borra sus asignaciones y tareas
    public void eliminar(int id) throws ValidacionException {
        Integrante integrante = buscar(id);

        for (int i = hogar.getAsignaciones().size() - 1; i >= 0; i--) {
            Asignacion a = hogar.getAsignaciones().get(i);
            if (a.getDependiente() == integrante || a.getResponsable() == integrante) {
                hogar.getAsignaciones().remove(i);
            }
        }
        for (int i = hogar.getTareas().size() - 1; i >= 0; i--) {
            if (hogar.getTareas().get(i).getEncargado() == integrante) {
                hogar.getTareas().remove(i);
            }
        }
        hogar.getIntegrantes().remove(integrante);
    }

    // RF02
    public void validarDias(int dias) throws ValidacionException {
        Validador.validarRango(dias, 1, 30, "dias");
        if (hogar.getIntegrantes().isEmpty()) {
            throw new ValidacionException("Primero registre a los integrantes del hogar");
        }
    }

    public double calcularAguaTotal(int dias) {
        double total = 0;
        for (Integrante i : hogar.getIntegrantes()) {
            total = total + i.calcularAguaDiaria() * dias;
        }
        return total;
    }

    public int calcularRacionesTotal(int dias) {
        int total = 0;
        for (Integrante i : hogar.getIntegrantes()) {
            total = total + i.calcularRacionesDiarias() * dias;
        }
        return total;
    }

    public int calcularDosisTotal(int dias) {
        int total = 0;
        for (Integrante i : hogar.getIntegrantes()) {
            total = total + i.getDosisDiarias() * dias;
        }
        return total;
    }
}
