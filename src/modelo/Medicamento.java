package modelo;

import java.time.LocalDate;

public class Medicamento extends Suministro {

    public Medicamento(int id, String nombre, double cantidad, LocalDate fechaCaducidad) {
        super(id, nombre, cantidad, fechaCaducidad);
    }

    @Override
    public String getCategoria() {
        return "Medicamento";
    }

    @Override
    public String getUnidad() {
        return "dosis";
    }
}
