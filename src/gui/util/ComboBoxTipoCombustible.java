package gui.util;

import javax.swing.JComboBox;

import domain.Vehiculo.Tipo;

public class ComboBoxTipoCombustible extends JComboBox<Tipo> {
	
	private static final long serialVersionUID = 1L;

	public ComboBoxTipoCombustible() {
		super(Tipo.values());
	}
	
}
