
public class Avion {

    private String nombre;
    private int velocidad;
    private int eficiencia;
    private int fortaleza;
    private Lugar ubicacionActual;
    public EstadoAvion estado;
    private int combustible;

    public Avion(String nombre, int velocidad, int eficiencia, int fortaleza) {
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.eficiencia = eficiencia;
        this.fortaleza = fortaleza;
        this.estado = EstadoAvion.EN_ESPERA;
        this.combustible = 0;
    }

    public void despegar(Lugar destino) {
        ubicacionActual = destino;
        estado = EstadoAvion.EN_VUELO;
        System.out.println("Avión " + nombre + " ha despegado hacia " + destino);
    }

    public void aterrizar() {
        estado = EstadoAvion.EN_ESPERA;
        System.out.println("Avión " + nombre + " ha aterrizado en " + ubicacionActual);
    }

    public String getNombre() {
        return nombre;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getEficiencia() {
        return eficiencia;
    }
    public EstadoAvion getEstado() {
        return estado;
    }

    public int getCombustible() {
        return combustible;
    }

    public void actualizarUbicacion(Lugar nuevaUbicacion) {
        ubicacionActual = nuevaUbicacion;
    }

    public void gestionarCombustible(int cantidad) {
        combustible += cantidad;
        System.out.println("Avión " + nombre + " ha recargado " + cantidad + " unidades de combustible");
        if (combustible<=0){
            estado = EstadoAvion.DESTRUIDO;
            System.out.println("Avión " + nombre + " ha sido destruido" );
            
        }
    }

    public int consumirCombustible(int cantidad) {
        int combustibleTotal;
        combustibleTotal=combustible;
        int consumido = combustibleTotal-cantidad;
        System.out.println("Avión " + nombre + " ha gastado " + cantidad + " unidades de combustible, combustible total: " + consumido);
        
        return consumido;
        
    }

    public void destruir(Avion avion) {
        estado = EstadoAvion.DESTRUIDO;
    }

    // Otros métodos y getters/setters según sea necesario
    public enum EstadoAvion {
        EN_ESPERA,
        EN_VUELO,
        DESTRUIDO
    }
}
