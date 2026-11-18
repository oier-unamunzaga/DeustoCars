


import domain.Usuario;
import domain.Vehiculo;
import gui.VentanaCaracteristicas;
import javax.swing.SwingUtilities;
import java.util.HashMap;

public class Prueba {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario u = new Usuario(1, "Adrián Cámara", "adrian", "adrian@deusto.es");
            Vehiculo v = new Vehiculo("Toyota", "Corolla Hybrid", 2022, 24500.0, "En venta");

            new VentanaCaracteristicas(v, new HashMap<>(), u, null).setVisible(true);
        });
    }
}