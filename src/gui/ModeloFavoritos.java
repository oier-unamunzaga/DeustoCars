package gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import domain.Vehiculo;

public class ModeloFavoritos extends DefaultTableModel {
	private List<String> lTitulos = Arrays.asList("Vehículo", "Estado");
	private List<Vehiculo> lVehiculos;

	public ModeloFavoritos(List<Vehiculo> lv) {
		this.lVehiculos = lv;
	}

	@Override
	public int getRowCount() {
		if (this.lVehiculos == null)
			return 0;
		return this.lVehiculos.size();
	}

	@Override
	public int getColumnCount() {
		return this.lTitulos.size();
	}

	@Override
	public String getColumnName(int column) {
		return this.lTitulos.get(column);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Vehiculo v = this.lVehiculos.get(row);
		switch (column) {
		case 0: return v.getMarca() + " " + v.getModelo() + " · " + v.getPrecio() + " €";
		case 1: return v.getEstado();
		default: return null;
		}
	}

}
