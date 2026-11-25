package gui.tablas;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import domain.Vehiculo.Tipo;

public class RendererCombustible extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public RendererCombustible() {
		setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel celda = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// Swing reutiliza el mismo JLabel en todas las celdas: se reinicia
		celda.setText("");
		celda.setIcon(null);
		celda.setToolTipText(null);

		if (value != null) {
			Tipo tipo = (Tipo) value;
			// ELECTRICO -> resources/images/combustible/electrico.png
			celda.setIcon(cargarIcono("resources/images/combustible/" + tipo.toString().toLowerCase() + ".png"));

			switch (tipo) {
				case ELECTRICO:
					celda.setToolTipText("Eléctrico");
					break;
				case HIBRIDO:
					celda.setToolTipText("Híbrido");
					break;
				case GASOLINA:
					celda.setToolTipText("Gasolina");
					break;
			}
		}

		return celda;
	}

	private ImageIcon cargarIcono(String ruta) {
		ImageIcon icono = new ImageIcon(ruta);
		Image imagenEscalada = icono.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		return new ImageIcon(imagenEscalada);
	}

}
