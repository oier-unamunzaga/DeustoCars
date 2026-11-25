package main;

import domain.Usuario;
import domain.Vehiculo;
import gui.VentanaCaracteristicas;

import javax.swing.SwingUtilities;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
            extras.put("imagenes", Arrays.asList(
                    "resources/corolla_1.jpg",
                    "resources/corolla_2.jpg",
                    "resources/corolla_3.jpg",
                    "resources/corolla_4.jpg",
                    "resources/corolla_5.jpg"
            ));

            VentanaCaracteristicas ventana = new VentanaCaracteristicas(v, extras, u, null);

            List<Vehiculo> similaresPrueba = Arrays.asList(
                    new Vehiculo("Hyundai", "Kona EV", 2023, 29950.0, "En venta"),
                    new Vehiculo("Volkswagen", "Golf", 2021, 19900.0, "En venta")
            );
            ventana.pintarTarjetasSimilares(similaresPrueba);

            ventana.setVisible(true);
        });
    }
}