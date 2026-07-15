
package Hotel;

import java.util.stream.*;
import java.util.LinkedList;

import java.util.ArrayList;

public class Contenedor {
    // instancia de Contenedor
    private static Contenedor instancia;

    // Lista de reservas
    private ArrayList<Reserva> lista;
    private LinkedList<Empleado> empleados;
    
    // Constructores 
    private Contenedor() {
         empleados = new LinkedList<>();
         lista = new ArrayList<>();
         habitaciones = new ArrayList<>();
         for (int i = 1; i <= 120; i++) {

        String tipo;

        if (i <= 60) {
            tipo = "Simple";
        } else if (i <= 100) {
            tipo = "Doble";
        } else {
            tipo = "Suite";
        }

        habitaciones.add(new Habitacion(i, tipo, true));
    }
         
    }
public void insertarEmpleado(Empleado empleado) {
    empleados.add(empleado);
}

public Empleado buscarEmpleado(String dni) {

    return empleados.stream()
        .filter(e -> e.getDni().equals(dni))
        .findFirst()
        .orElse(null);
}

public boolean eliminarEmpleado(String dni) {

    return empleados.removeIf(
        e -> e.getDni().equals(dni)
    );
}

public String mostrarEmpleados() {
    if (empleados.isEmpty()) {
        return "No hay empleados registrados.";
    }
    StringBuilder texto = new StringBuilder();

    empleados.forEach(e ->
        texto.append(e.mostrarInformacion()).append("\n\n")
    );

    return texto.toString();
    }

    public LinkedList<Empleado> getEmpleados() {
    return empleados;
    }
    
    
    public static Contenedor getInstancia() {

        if (instancia == null) {
            instancia = new Contenedor();
        }

        return instancia;
    }

    public void insertar(Reserva reserva) {
        lista.add(reserva);
    }

    public Reserva buscar(String dni) {

        return lista.stream()
            .filter(r -> r.getCliente().getDni().equals(dni))
            .findFirst()
            .orElse(null);
    }
    public Habitacion buscarHabitacion(int numero) {

    return habitaciones.stream()
        .filter(h -> h.getNumero() == numero)
        .findFirst()
        .orElse(null);
}

    public boolean eliminar(String dni) {

       return lista.removeIf(
            r -> r.getCliente().getDni().equals(dni)
    );
    }
    public boolean checkout(int numeroHabitacion) {

     Reserva reservaEncontrada = lista.stream()
    .filter(r -> r.getHabitacion().getNumero() == numeroHabitacion)
    .findFirst()
    .orElse(null);

     if (reservaEncontrada == null) {
    return false;
}

     Habitacion habitacion = buscarHabitacion(numeroHabitacion);

     if (habitacion != null) {
      habitacion.setDisponible(true);
}

      lista.remove(reservaEncontrada);

     return true;

}

    public String mostrar() {
     if (lista.isEmpty()) {
            return "No hay reservas registradas.";
            
        }
        StringBuilder texto = new StringBuilder();

     lista.forEach(r ->
    texto.append(r).append("\n\n")
);

    return texto.toString();
    }

    public ArrayList<Reserva> getLista() {
    return lista;
    }
    public ArrayList<Habitacion> getHabitaciones() {
    return habitaciones;
    }
    private ArrayList<Habitacion> habitaciones;
}

