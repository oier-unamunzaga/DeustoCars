package gui.util;

import javax.swing.JComboBox;

import domain.Vehiculo.Estado;

public class ComboBoxEstadoVehiculo extends JComboBox<Estado> {

	private static final long serialVersionUID = 1L;
	
	public ComboBoxEstadoVehiculo() {
		super(Estado.values());
	}
	
}
