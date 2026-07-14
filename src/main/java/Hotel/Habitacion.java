
package Hotel;

public class Habitacion {
    private int numero;
    private String tipo;
    private boolean disponible;

    public Habitacion(int numero,
                      String tipo,
                      boolean disponible) {

        this.numero = numero;
        this.tipo = tipo;
        this.disponible = disponible;
    }

    public int getNumero() {

        return numero;
    }

    public void setNumero(int numero) {

        this.numero = numero;
    }

    public String getTipo() {

        return tipo;
    }

    public void setTipo(String tipo) {

        this.tipo = tipo;
    }

    public boolean isDisponible() {

        return disponible;
    }

    public void setDisponible(boolean disponible) {

        this.disponible = disponible;
    }

    public void mostrarHabitacion() {

        System.out.println(
                "Habitación N°: "
                        + numero);

        System.out.println(
                "Tipo: "
                        + tipo);

        System.out.println(
                "Disponible: "
                        + disponible);
    }
    public double getPrecio() {

        switch (tipo) {
            case "Simple":
                return 40.0;
            case "Doble":
                return 80.0;
            case "Suite":
                return 120.0;
            default:
                return 0.0;
        }
}
}
