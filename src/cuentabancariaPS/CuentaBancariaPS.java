package cuentabancariaPS;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class CuentaBancariaPS {

    static final int ANIO_ACTUAL = 2025;
    static final String PAIS = "Colombia";
    static final String USUARIO = "admin";
    static final String CONTRASENA = "1234";

    int anioCreacion;
    int numeroCuenta;
    String titular;
    double saldo;
    String estado;

    // Constructor
    CuentaBancariaPS(int numeroCuenta, String titular, int anioCreacion, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.anioCreacion = anioCreacion;
        this.saldo = saldo;
        verificarSaldo();
    }

    // Mostrar datos de la cuenta
    void mostrarDatos() {
        String datos = """
                   ----- INFORMACIÓN DE LA CUENTA BANCARIA -----
                   Número de Cuenta: """ + numeroCuenta
                + "\nTitular: " + titular
                + "\nAño de Creación: " + anioCreacion
                + "\nSaldo: $" + saldo
                + "\nPaís: " + PAIS
                + "\nEstado de la cuenta: " + estado;
        JOptionPane.showMessageDialog(null, datos);
    }

    // Verificar y asignar el estado
    public void verificarSaldo() {
        estado = (saldo <= 0) ? "Inactiva (sin fondos)" : "Activa (con fondos)";
    }

    // Incrementar saldo si la cuenta es creada este año
    public void incrementarSaldo() {
        if (ANIO_ACTUAL == anioCreacion) {
            saldo += saldo * 0.05;
            JOptionPane.showMessageDialog(null, "✅ Beneficio promocional aplicado. Nuevo saldo: $" + saldo);
        } else {
            JOptionPane.showMessageDialog(null, "ℹ️ Esta cuenta no cumple con los requisitos para la promoción.");
        }
    }

    // Método principal
    public static void main(String[] args) {

        String contraUsuari;
        String contraUsuario;
        int intentosUsuario = 0;
        boolean usuarioCorrecto = false;

        // Validar usuario
        while (intentosUsuario < 3) {
            contraUsuari = JOptionPane.showInputDialog("Ingrese el nombre de usuario:");
            if (contraUsuari != null && contraUsuari.equals(USUARIO)) {
                usuarioCorrecto = true;
                break;
            } else {
                intentosUsuario++;
                JOptionPane.showMessageDialog(null, "❌ Usuario incorrecto. Intento " + intentosUsuario + " de 3.");
            }
        }

        if (!usuarioCorrecto) {
            JOptionPane.showMessageDialog(null, "🚫 Demasiados intentos fallidos. El programa se cerrará por seguridad.");
            System.exit(0);
        }

        int intentosContrasena = 0;
        boolean contrasenaCorrecta = false;

        // Validar contraseña
        while (intentosContrasena < 3) {
            contraUsuario = JOptionPane.showInputDialog("Ingrese la contraseña del usuario " + USUARIO + ":");
            if (contraUsuario != null && contraUsuario.equals(CONTRASENA)) {
                contrasenaCorrecta = true;
                JOptionPane.showMessageDialog(null, "✅ ¡Acceso concedido!");
                break;
            } else {
                intentosContrasena++;
                JOptionPane.showMessageDialog(null, "❌ Contraseña incorrecta. Intento " + intentosContrasena + " de 3.");
            }
        }

        if (!contrasenaCorrecta) {
            JOptionPane.showMessageDialog(null, "🚫 Demasiados intentos fallidos. El programa se cerrará por seguridad.");
            System.exit(0);
        }

        JOptionPane.showMessageDialog(null, "Bienvenido al sistema de BancoMpensar, donde tu seguridad es nuestra prioridad ;)");

        ArrayList<CuentaBancariaPS> cuentas = new ArrayList<>();
        int opcion;

        do {
            String[] opciones = {"Registrar cuenta", "Buscar cuenta por ID", "Eliminar cuenta", "Mostrar todas las cuentas", "Transacciones", "Salir"};
            opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Menú principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (opcion) {
                case 0 -> { // Registrar cuenta
                    int id;
                    while (true) {
                        try {
                            id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Número de Cuenta (ID):"));
                            break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "❌ Error: Ingrese un número válido para el ID.");
                        }
                    }

                    String nombre = JOptionPane.showInputDialog("Ingrese el nombre completo del titular:");

                    int anio;
                    while (true) {
                        try {
                            anio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el año en que se abrió la cuenta (AAAA):"));
                            break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "❌ Error: Ingrese un año válido.");
                        }
                    }

                    double saldo;
                    while (true) {
                        try {
                            saldo = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el saldo inicial de la cuenta:"));
                            break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "❌ Error: Ingrese un valor numérico para el saldo.");
                        }
                    }

                    CuentaBancariaPS nuevaCuenta = new CuentaBancariaPS(id, nombre, anio, saldo);
                    nuevaCuenta.incrementarSaldo();
                    cuentas.add(nuevaCuenta);
                    JOptionPane.showMessageDialog(null, "✅ Registro exitoso. La cuenta ha sido creada.");
                }

                case 1 -> { // Buscar cuenta
                    try {
                        int buscarId = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Número de Cuenta (ID) a buscar:"));
                        boolean encontrada = false;
                        for (CuentaBancariaPS cuenta : cuentas) {
                            if (cuenta.numeroCuenta == buscarId) {
                                cuenta.mostrarDatos();
                                encontrada = true;
                                break;
                            }
                        }
                        if (!encontrada) {
                            JOptionPane.showMessageDialog(null, "❌ Cuenta no encontrada.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "❌ Error: Ingrese un ID válido.");
                    }
                }

                case 2 -> { // Eliminar cuenta
                    try {
                        int eliminarId = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Número de Cuenta (ID) a eliminar:"));
                        boolean eliminada = false;
                        for (int i = 0; i < cuentas.size(); i++) {
                            if (cuentas.get(i).numeroCuenta == eliminarId) {
                                cuentas.remove(i);
                                eliminada = true;
                                JOptionPane.showMessageDialog(null, "✅ Cuenta eliminada correctamente.");
                                break;
                            }
                        }
                        if (!eliminada) {
                            JOptionPane.showMessageDialog(null, "❌ No se encontró una cuenta con ese ID.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "❌ Error: Ingrese un ID válido.");
                    }
                }

                case 3 -> { // Mostrar todas las cuentas
                    if (cuentas.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay cuentas registradas actualmente.");
                    } else {
                        for (CuentaBancariaPS cuenta : cuentas) {
                            cuenta.mostrarDatos();
                        }
                    }
                }

                case 4 -> { // Transacciones
                    try {
                        int transaccionId = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Número de Cuenta (ID):"));
                        CuentaBancariaPS cuentaTransaccion = null;
                        for (CuentaBancariaPS c : cuentas) {
                            if (c.numeroCuenta == transaccionId) {
                                cuentaTransaccion = c;
                                break;
                            }
                        }

                        if (cuentaTransaccion == null) {
                            JOptionPane.showMessageDialog(null, "❌ Cuenta no encontrada.");
                            break;
                        }

                        String[] opcionesTrans = {"Depositar", "Retirar"};
                        int opcionTrans = JOptionPane.showOptionDialog(null, "Seleccione una operación:",
                                "Transacciones", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                                null, opcionesTrans, opcionesTrans[0]);

                        double monto;
                        try {
                            monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto:"));
                            if (monto <= 0) {
                                JOptionPane.showMessageDialog(null, "❌ El monto debe ser mayor a cero.");
                                break;
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "❌ Monto inválido.");
                            break;
                        }

                        if (opcionTrans == 0) { // Depositar
                            cuentaTransaccion.saldo += monto;
                            cuentaTransaccion.verificarSaldo();
                            JOptionPane.showMessageDialog(null, "✅ Depósito exitoso. Nuevo saldo: $" + cuentaTransaccion.saldo);
                        } else if (opcionTrans == 1) { // Retirar
                            if (cuentaTransaccion.saldo >= monto) {
                                cuentaTransaccion.saldo -= monto;
                                cuentaTransaccion.verificarSaldo();
                                JOptionPane.showMessageDialog(null, "✅ Retiro exitoso. Nuevo saldo: $" + cuentaTransaccion.saldo);
                            } else {
                                JOptionPane.showMessageDialog(null, "❌ Fondos insuficientes para realizar el retiro.");
                            }
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "❌ Error: ID no válido.");
                    }
                }

                case 5 ->
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema de cuentas bancarias.");
            }

        } while (opcion != 5); // Salir
    }
}
