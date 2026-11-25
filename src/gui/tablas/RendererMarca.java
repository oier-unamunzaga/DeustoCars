package gui.tablas;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import domain.Vehiculo.Marca;

public class RendererMarca extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public RendererMarca() {
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
			Marca marca = (Marca) value;
			celda.setIcon(cargarIcono(marca.getRutaImagen()));
			celda.setToolTipText(marca.getNombre());
		}

		return celda;
	}

	private ImageIcon cargarIcono(String ruta) {
		ImageIcon icono = new ImageIcon(ruta);
		// ayudado y comprobado con la IA
		// -1: el ancho se calcula solo para que el logo no se deforme
		Image imagenEscalada = icono.getImage().getScaledInstance(-1, 36, Image.SCALE_SMOOTH);
		return new ImageIcon(imagenEscalada);
	}

}
