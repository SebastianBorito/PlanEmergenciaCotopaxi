package modelo;

import java.time.LocalDate;

public class Nino extends Integrante {

    public Nino(int id, String nombre, LocalDate fechaNacimiento, String parentesco,
                String condicionSalud, Movilidad movilidad, int dosisDiarias) {
        super(id, nombre, fechaNacimiento, parentesco, condicionSalud, movilidad, dosisDiarias);
    }

    @Override
    public double calcularAguaDiaria() {
        if (calcularEdad() < 5) {
            return 2;
        }
        return 3;
    }

    @Override
    public int calcularRacionesDiarias() {
        return 3;
    }

    @Override
    public String getTipo() {
        return "Niño";
    }

    @Override
    public boolean esVulnerable() {
        return true;
    }
}
