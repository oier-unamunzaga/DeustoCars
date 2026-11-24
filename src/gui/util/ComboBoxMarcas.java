package gui.util;

import javax.swing.JComboBox;

import domain.Vehiculo.Marca;

public class ComboBoxMarcas extends JComboBox<Marca> {

	private static final long serialVersionUID = 1L;

	public ComboBoxMarcas() {
		super(Marca.values());
	}

}
