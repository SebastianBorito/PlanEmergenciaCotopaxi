package negocio;

import modelo.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

// Modulo 2
public class GestorKit {

    private Hogar hogar;
    private GestorIntegrantes gestorIntegrantes;
    private int contadorId = 1;

    public GestorKit(Hogar hogar, GestorIntegrantes gestorIntegrantes) {
        this.hogar = hogar;
        this.gestorIntegrantes = gestorIntegrantes;
    }

    // RF03
    public Suministro registrar(int categoria, String nombre, double cantidad, LocalDate caducidad) throws ValidacionException {
        Validador.validarTexto(nombre, "nombre");
        Validador.validarPositivo(cantidad, "cantidad");
        if (categoria != 4 && caducidad == null) {
            throw new ValidacionException("La fecha de caducidad es obligatoria");
        }

        Suministro s;
        switch (categoria) {
            case 1:
                s = new Agua(contadorId, nombre, cantidad, caducidad);
                break;
            case 2:
                s = new Alimento(contadorId, nombre, cantidad, caducidad);
                break;
            case 3:
                s = new Medicamento(contadorId, nombre, cantidad, caducidad);
                break;
            case 4:
                s = new Herramienta(contadorId, nombre, cantidad);
                break;
            default:
                throw new ValidacionException("Categoria no valida");
        }
        contadorId++;
        hogar.getKit().agregarSuministro(s);
        return s;
    }

    public ArrayList<Suministro> listar() {
        return hogar.getKit().getSuministros();
    }

    public Suministro buscar(int id) throws ValidacionException {
        for (Suministro s : hogar.getKit().getSuministros()) {
            if (s.getId() == id) {
                return s;
            }
        }
        throw new ValidacionException("No existe un suministro con el codigo " + id);
    }

    public void modificar(int id, String nombre, double cantidad, LocalDate caducidad) throws ValidacionException {
        Suministro s = buscar(id);
        Validador.validarTexto(nombre, "nombre");
        Validador.validarPositivo(cantidad, "cantidad");
        s.setNombre(nombre);
        s.setCantidad(cantidad);
        if (!(s instanceof Herramienta)) {
            if (caducidad == null) {
                throw new ValidacionException("La fecha de caducidad es obligatoria");
            }
            s.setFechaCaducidad(caducidad);
        }
    }

    public void eliminar(int id) throws ValidacionException {
        hogar.getKit().eliminarSuministro(buscar(id));
    }

    // RF04
    public double calcularCobertura(String categoria, double necesario) {
        if (necesario == 0) {
            return 100;
        }
        double porcentaje = hogar.getKit().totalVigente(categoria) * 100 / necesario;
        if (porcentaje > 100) {
            porcentaje = 100;
        }
        return porcentaje;
    }

    public double calcularFaltante(String categoria, double necesario) {
        double falta = necesario - hogar.getKit().totalVigente(categoria);
        if (falta < 0) {
            falta = 0;
        }
        return falta;
    }

    public boolean kitCompleto(int dias) throws ValidacionException {
        gestorIntegrantes.validarDias(dias);
        return calcularFaltante("Agua", gestorIntegrantes.calcularAguaTotal(dias)) == 0
                && calcularFaltante("Alimento", gestorIntegrantes.calcularRacionesTotal(dias)) == 0
                && calcularFaltante("Medicamento", gestorIntegrantes.calcularDosisTotal(dias)) == 0;
    }

    // RF05
    public ArrayList<Suministro> buscarPorCaducar(int diasAviso) throws ValidacionException {
        Validador.validarRango(diasAviso, 1, 365, "dias de aviso");
        ArrayList<Suministro> lista = new ArrayList<>();
        for (Suministro s : hogar.getKit().getSuministros()) {
            if (s.calcularEstado(diasAviso) != EstadoSuministro.VIGENTE) {
                lista.add(s);
            }
        }
        return lista;
    }

    // revision cada 3 meses
    public boolean necesitaRevision() {
        long dias = ChronoUnit.DAYS.between(hogar.getKit().getUltimaRevision(), LocalDate.now());
        return dias >= 90;
    }

    public void marcarRevisado() {
        hogar.getKit().setUltimaRevision(LocalDate.now());
    }

    public double porcentajeVigentes() {
        int total = hogar.getKit().getSuministros().size();
        if (total == 0) {
            return 0;
        }
        int vigentes = 0;
        for (Suministro s : hogar.getKit().getSuministros()) {
            if (!s.estaCaducado()) {
                vigentes++;
            }
        }
        return vigentes * 100.0 / total;
    }
}
