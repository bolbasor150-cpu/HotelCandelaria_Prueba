
package Hotel;


public class ReservaFactory {
     public static Reserva crearReserva(
            Cliente cliente,
            Empleado empleado,
            Habitacion habitacion,
            Pago pago) {

        return new Reserva(
                cliente,
                empleado,
                habitacion,
                pago);
    }
}
