package modelo;

import java.time.LocalDate;

public class Alimento extends Suministro {

    public Alimento(int id, String nombre, double cantidad, LocalDate fechaCaducidad) {
        super(id, nombre, cantidad, fechaCaducidad);
    }

    @Override
    public String getCategoria() {
        return "Alimento";
    }

    @Override
    public String getUnidad() {
        return "raciones";
    }
}
