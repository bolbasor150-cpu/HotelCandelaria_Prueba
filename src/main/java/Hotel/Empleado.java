
package Hotel;

public class Empleado extends Persona {

    private String cargo;

    public Empleado(String nombre,
                    String dni,
                    String cargo) {

        super(nombre, dni);

        this.cargo = cargo;
    }

    public String getCargo() {

        return cargo;
    }

    public void setCargo(String cargo) {

        this.cargo = cargo;
    }

    @Override
    public String mostrarInformacion() {

        return "EMPLEADO"
                + "\nNombre: " + nombre
                + "\nDNI: " + dni
                + "\nCargo: " + cargo;
    }
}
