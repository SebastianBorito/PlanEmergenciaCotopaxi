package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class KitEmergencia {

    private ArrayList<Suministro> suministros;
    private LocalDate ultimaRevision;

    public KitEmergencia() {
        suministros = new ArrayList<>();
        ultimaRevision = LocalDate.now();
    }

    public void agregarSuministro(Suministro s) {
        suministros.add(s);
    }

    public void eliminarSuministro(Suministro s) {
        suministros.remove(s);
    }

    // sin contar lo caducado
    public double totalVigente(String categoria) {
        double total = 0;
        for (Suministro s : suministros) {
            if (s.getCategoria().equals(categoria) && !s.estaCaducado()) {
                total = total + s.getCantidad();
            }
        }
        return total;
    }

    public ArrayList<Suministro> getSuministros() {
        return suministros;
    }

    public LocalDate getUltimaRevision() {
        return ultimaRevision;
    }

    public void setUltimaRevision(LocalDate ultimaRevision) {
        this.ultimaRevision = ultimaRevision;
    }
}
