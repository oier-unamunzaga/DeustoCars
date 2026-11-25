package gui.util;

import javax.swing.JButton;

public class BotonVerMarcasDisponibles extends JButton {

	private static final long serialVersionUID = 1L;
	//Le ponemos nombre al boton con el super y hacemos que cuando lo pulses se cree la ventana del carrusel
	public BotonVerMarcasDisponibles() {
		super("Ver marcas disponibles");
		addActionListener(e -> new CarruselMarcasVehiculosDisponibles());
	}
}
