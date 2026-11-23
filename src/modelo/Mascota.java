package modelo;

import java.time.LocalDate;

public class Mascota extends Integrante {

    private String especie;
    private double peso; // en kg

    public Mascota(int id, String nombre, LocalDate fechaNacimiento, String condicionSalud,
                   Movilidad movilidad, int dosisDiarias, String especie, double peso) {
        super(id, nombre, fechaNacimiento, "Mascota", condicionSalud, movilidad, dosisDiarias);
        this.especie = especie;
        this.peso = peso;
    }

    // 60 ml por kg
    @Override
    public double calcularAguaDiaria() {
        return peso * 0.06;
    }

    // 1 racion por cada 10 kg
    @Override
    public int calcularRacionesDiarias() {
        int raciones = (int) Math.ceil(peso / 10);
        if (raciones < 1) {
            raciones = 1;
        }
        return raciones;
    }

    @Override
    public String getTipo() {
        return "Mascota";
    }

    @Override
    public boolean esVulnerable() {
        return true;
    }

    public String getEspecie() {
        return especie;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "especie='" + especie + '\'' +
                ", peso=" + peso +
                '}';
    }
}
