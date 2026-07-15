package Hotel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

public class Contenedor {

    private static Contenedor instancia;

    private Contenedor() {
    }

    public static Contenedor getInstancia() {

        if (instancia == null) {
            instancia = new Contenedor();
        }

        return instancia;
    }

    // ================= HABITACIONES =================

    public ArrayList<Habitacion> getHabitaciones() {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        String sql = "SELECT numero, tipo, disponible FROM Habitacion ORDER BY numero";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                habitaciones.add(new Habitacion(
                    rs.getInt("numero"),
                    rs.getString("tipo"),
                    rs.getBoolean("disponible")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar habitaciones: " + e.getMessage());
        }

        return habitaciones;
    }

    public Habitacion buscarHabitacion(int numero) {
        String sql = "SELECT numero, tipo, disponible FROM Habitacion WHERE numero = ?";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, numero);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Habitacion(
                        rs.getInt("numero"),
                        rs.getString("tipo"),
                        rs.getBoolean("disponible")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar habitación: " + e.getMessage());
        }

        return null;
    }

    private void actualizarDisponibilidad(int numero, boolean disponible) {
        String sql = "UPDATE Habitacion SET disponible = ? WHERE numero = ?";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setBoolean(1, disponible);
            ps.setInt(2, numero);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar habitación: " + e.getMessage());
        }
    }

    // ================= CLIENTES =================

    private void guardarClienteSiNoExiste(Cliente cliente) {
        String buscar = "SELECT dni FROM Cliente WHERE dni = ?";
        String insertar = "INSERT INTO Cliente (dni, nombre, correo) VALUES (?, ?, ?)";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(buscar)) {
            ps.setString(1, cliente.getDni());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
            return;
        }

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(insertar)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getCorreo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    private void guardarEmpleadoSiNoExiste(Empleado empleado) {
        String buscar = "SELECT dni FROM Empleado WHERE dni = ?";
        String insertar = "INSERT INTO Empleado (dni, nombre, cargo) VALUES (?, ?, ?)";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(buscar)) {
            ps.setString(1, empleado.getDni());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
            return;
        }

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(insertar)) {
            ps.setString(1, empleado.getDni());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getCargo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
        }
    }

    // ================= EMPLEADOS =================

    public void insertarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO Empleado (dni, nombre, cargo) VALUES (?, ?, ?)";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, empleado.getDni());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getCargo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
        }
    }

    public Empleado buscarEmpleado(String dni) {
        String sql = "SELECT nombre, dni, cargo FROM Empleado WHERE dni = ?";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("cargo")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }

        return null;
    }

    public boolean eliminarEmpleado(String dni) {
        String sql = "DELETE FROM Empleado WHERE dni = ?";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, dni);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }

    public LinkedList<Empleado> getEmpleados() {
        LinkedList<Empleado> empleados = new LinkedList<>();
        String sql = "SELECT nombre, dni, cargo FROM Empleado";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                empleados.add(new Empleado(
                    rs.getString("nombre"),
                    rs.getString("dni"),
                    rs.getString("cargo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }

        return empleados;
    }

    public String mostrarEmpleados() {
        LinkedList<Empleado> empleados = getEmpleados();

        if (empleados.isEmpty()) {
            return "No hay empleados registrados.";
        }

        StringBuilder texto = new StringBuilder();
        
        empleados.forEach(e -> texto.append(e.mostrarInformacion()).append("\n\n"));
        return texto.toString();
    }

    // ================= RESERVAS =================

    public void insertar(Reserva reserva) {
        guardarClienteSiNoExiste(reserva.getCliente());
        guardarEmpleadoSiNoExiste(reserva.getEmpleado());

        String sql = "INSERT INTO Reserva (clienteDni, empleadoDni, habitacionNumero, monto, metodoPago) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, reserva.getCliente().getDni());
            ps.setString(2, reserva.getEmpleado().getDni());
            ps.setInt(3, reserva.getHabitacion().getNumero());
            ps.setDouble(4, reserva.getPago().getMonto());
            ps.setString(5, reserva.getPago().getMetodoPago());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    reserva.setId(keys.getInt(1));
                }
            }

            actualizarDisponibilidad(reserva.getHabitacion().getNumero(), false);

        } catch (SQLException e) {
            System.err.println("Error al insertar reserva: " + e.getMessage());
        }
    }

    public Reserva buscar(String dni) {
        String sql = consultaReservaBase() + " WHERE c.dni = ?";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearReserva(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar reserva: " + e.getMessage());
        }

        return null;
    }

    public boolean eliminar(String dni) {
        Reserva reserva = buscar(dni);

        if (reserva == null) {
            return false;
        }

        String sql = "DELETE FROM Reserva WHERE id = ?";

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, reserva.getId());
            boolean eliminado = ps.executeUpdate() > 0;

            if (eliminado) {
                actualizarDisponibilidad(reserva.getHabitacion().getNumero(), true);
            }

            return eliminado;
        } catch (SQLException e) {
            System.err.println("Error al eliminar reserva: " + e.getMessage());
            return false;
        }
    }

    public boolean checkout(int numeroHabitacion) {
        String sql = "SELECT c.dni FROM Reserva r JOIN Cliente c ON r.clienteDni = c.dni "
                   + "WHERE r.habitacionNumero = ?";

        String dniCliente = null;

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, numeroHabitacion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dniCliente = rs.getString("dni");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en checkout: " + e.getMessage());
            return false;
        }

        if (dniCliente == null) {
            return false;
        }

        return eliminar(dniCliente);
    }

    public ArrayList<Reserva> getLista() {
        ArrayList<Reserva> lista = new ArrayList<>();

        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(consultaReservaBase());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearReserva(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar reservas: " + e.getMessage());
        }

        return lista;
    }

    public String mostrar() {
        ArrayList<Reserva> lista = getLista();

        if (lista.isEmpty()) {
            return "No hay reservas registradas.";
        }

        StringBuilder texto = new StringBuilder();
        
        lista.forEach(r -> texto.append(r).append("\n\n"));
        return texto.toString();
    }

    private String consultaReservaBase() {
        return "SELECT r.id, r.monto, r.metodoPago, "
             + "c.dni AS clienteDni, c.nombre AS clienteNombre, c.correo, "
             + "e.dni AS empleadoDni, e.nombre AS empleadoNombre, e.cargo, "
             + "h.numero, h.tipo, h.disponible "
             + "FROM Reserva r "
             + "JOIN Cliente c ON r.clienteDni = c.dni "
             + "JOIN Empleado e ON r.empleadoDni = e.dni "
             + "JOIN Habitacion h ON r.habitacionNumero = h.numero";
    }

    private Reserva mapearReserva(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
            rs.getString("clienteNombre"),
            rs.getString("clienteDni"),
            rs.getString("correo")
        );

        Empleado empleado = new Empleado(
            rs.getString("empleadoNombre"),
            rs.getString("empleadoDni"),
            rs.getString("cargo")
        );

        Habitacion habitacion = new Habitacion(
            rs.getInt("numero"),
            rs.getString("tipo"),
            rs.getBoolean("disponible")
        );

        Pago pago = new Pago(
            rs.getDouble("monto"),
            rs.getString("metodoPago")
        );

        Reserva reserva = new Reserva(cliente, empleado, habitacion, pago);
        reserva.setId(rs.getInt("id"));

        return reserva;
    }
}

