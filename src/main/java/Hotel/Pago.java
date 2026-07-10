
package Hotel;


    public class Pago {

    private double monto;
    private String metodoPago;

    public Pago(double monto,
                String metodoPago) {

        this.monto = monto;
        this.metodoPago = metodoPago;
    }

    public double getMonto() {

        return monto;
    }

    public void setMonto(double monto) {

        this.monto = monto;
    }

    public String getMetodoPago() {

        return metodoPago;
    }

    public void setMetodoPago(
            String metodoPago) {

        this.metodoPago = metodoPago;
    }

    public void mostrarPago() {

        System.out.println(
                "Monto: " + monto);

        System.out.println(
                "Método de pago: "
                        + metodoPago);
    }
}

