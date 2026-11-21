import domain.Usuario;
import domain.Vehiculo;
import gui.VentanaCaracteristicas;
import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.Map;


public class Prueba {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario u = new Usuario(1, "Adrián Cámara", "adrian", "adrian@deusto.es");
            Vehiculo v = new Vehiculo("Toyota", "Corolla Hybrid", 2022, 24500.0, "En venta");

            Map<String, Object> extras = new HashMap<>();
            extras.put("kilometraje", 42000);
            extras.put("combustible", "Híbrido");
            extras.put("ubicacion", "Bilbao, Bizkaia");
            extras.put("descripcion", "Un solo propietario, mantenimiento en servicio oficial Toyota, cámara trasera y control de crucero adaptativo.");

            new VentanaCaracteristicas(v, extras, u, null).setVisible(true);
        });
    }
}