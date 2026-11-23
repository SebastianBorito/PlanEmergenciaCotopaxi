package modelo;

// no caduca
public class Herramienta extends Suministro {

    public Herramienta(int id, String nombre, double cantidad) {
        super(id, nombre, cantidad, null);
    }

    @Override
    public String getCategoria() {
        return "Herramienta";
    }

    @Override
    public String getUnidad() {
        return "unidades";
    }
}
