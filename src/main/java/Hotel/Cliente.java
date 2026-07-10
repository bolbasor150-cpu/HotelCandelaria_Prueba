
package Hotel;

    public class Cliente extends Persona {

    private String correo;

    public Cliente(String nombre,
                   String dni,
                   String correo) {

        super(nombre, dni);

        this.correo = correo;
    }

    public String getCorreo() {

        return correo;
    }

    public void setCorreo(String correo) {

        this.correo = correo;
    }

    @Override
    public String mostrarInformacion() {

        return "CLIENTE"
                + "\nNombre: " + nombre
                + "\nDNI: " + dni
                + "\nCorreo: " + correo;
    }
}

