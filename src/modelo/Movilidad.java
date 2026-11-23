package modelo;

// velocidad en metros por minuto
public enum Movilidad {
    NORMAL(80),
    REDUCIDA(50),
    REQUIERE_ASISTENCIA(30);

    private int velocidad;

    Movilidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getVelocidad() {
        return velocidad;
    }
}
