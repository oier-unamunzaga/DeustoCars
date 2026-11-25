import domain.Usuario;
import domain.Vehiculo;
import domain.Vehiculo.Estado;
import domain.Vehiculo.Marca;
import gui.VentanaCaracteristicas;
import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.Map;


public class Prueba {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario u = new Usuario(1, "Adrián Cámara", "adrian", "adrian@deusto.es");
            Vehiculo v = new Vehiculo(Marca.TOYOTA, "Yaris", 2002, 2000, Estado.EN_VENTA);

            Map<String, Object> extras = new HashMap<>();
            extras.put("kilometraje", 42000);
            extras.put("combustible", "Híbrido");
            extras.put("ubicacion", "Bilbao, Bizkaia");
            extras.put("descripcion", "Un solo propietario, mantenimiento en servicio oficial Toyota, cámara trasera y control de crucero adaptativo.");

            new VentanaCaracteristicas(v, extras, u, null).setVisible(true);
        });
    }
}