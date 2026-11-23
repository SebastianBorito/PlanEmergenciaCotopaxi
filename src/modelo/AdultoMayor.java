package modelo;

import java.time.LocalDate;

public class AdultoMayor extends Integrante {

    public AdultoMayor(int id, String nombre, LocalDate fechaNacimiento, String parentesco,
                       String condicionSalud, Movilidad movilidad, int dosisDiarias) {
        super(id, nombre, fechaNacimiento, parentesco, condicionSalud, movilidad, dosisDiarias);
    }

    @Override
    public double calcularAguaDiaria() {
        return 3.5;
    }

    @Override
    public int calcularRacionesDiarias() {
        return 3;
    }

    @Override
    public String getTipo() {
        return "Adulto mayor";
    }

    @Override
    public boolean esVulnerable() {
        return true;
    }
}
