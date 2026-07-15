
package Hotel;


public class Reserva {
    private int id;

    private static int contador = 1;

    private Cliente cliente;
    private Empleado empleado;
    private Habitacion habitacion;
    private Pago pago;

    public Reserva(Cliente cliente,
                   Empleado empleado,
                   Habitacion habitacion,
                   Pago pago) {

        this.id = contador++;

        this.cliente = cliente;
        this.empleado = empleado;
        this.habitacion = habitacion;
        this.pago = pago;
    }
    public Empleado getEmpleado() {

        return empleado;
    }

    public Pago getPago() {

        return pago;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    public static int getSiguienteId() {

        return contador;
    }

    public Cliente getCliente() {

        return cliente;
    }

    public Habitacion getHabitacion() {

        return habitacion;
    }

    public void mostrarReserva() {

        System.out.println(
                "===== RESERVA =====");

        System.out.println(
                "ID: "
                        + String.format("%04d", id));

        System.out.println();

        System.out.println(
                cliente.mostrarInformacion());

        System.out.println();

        System.out.println(
                empleado.mostrarInformacion());

        System.out.println();

        habitacion.mostrarHabitacion();

        System.out.println();

        pago.mostrarPago();
    }

    @Override
    public String toString() {

        return "ID: "
                + String.format("%04d", id)
                + "\n\nCLIENTE\n"
                + "Nombre: "
                + cliente.getNombre()
                + "\nDNI: "
                + cliente.getDni()
                + "\nCorreo: "
                + cliente.getCorreo()
                + "\n\nHABITACION\n"
                + "Número: "
                + habitacion.getNumero()
                + "\nTipo: "
                + habitacion.getTipo()
                + "\n\nPAGO\n"
                + "Monto: "
                + pago.getMonto()
                + "\nMétodo: "
                + pago.getMetodoPago();
    }
}
