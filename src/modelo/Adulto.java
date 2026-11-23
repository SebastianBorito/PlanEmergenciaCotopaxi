package modelo;

import java.time.LocalDate;

public class Adulto extends Integrante {

    // maximo de personas a su cargo
    public static final int MAX_DEPENDIENTES = 2;

    public Adulto(int id, String nombre, LocalDate fechaNacimiento, String parentesco,
                  String condicionSalud, Movilidad movilidad, int dosisDiarias) {
        super(id, nombre, fechaNacimiento, parentesco, condicionSalud, movilidad, dosisDiarias);
    }

    @Override
    public double calcularAguaDiaria() {
        return 4;
    }

    @Override
    public int calcularRacionesDiarias() {
        return 3;
    }

    @Override
    public String getTipo() {
        return "Adulto";
    }

    // solo si tiene movilidad normal
    public boolean puedeSerResponsable() {
        return getMovilidad() == Movilidad.NORMAL;
    }
}
