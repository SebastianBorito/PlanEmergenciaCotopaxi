package modelo;

import java.time.LocalDate;

public class Agua extends Suministro {

    public Agua(int id, String nombre, double cantidad, LocalDate fechaCaducidad) {
        super(id, nombre, cantidad, fechaCaducidad);
    }

    @Override
    public String getCategoria() {
        return "Agua";
    }

    @Override
    public String getUnidad() {
        return "litros";
    }
}
