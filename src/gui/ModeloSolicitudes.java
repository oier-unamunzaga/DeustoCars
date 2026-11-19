package gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import domain.Solicitud;

public class ModeloSolicitudes extends DefaultTableModel {
	private List<String> lTitulos = Arrays.asList("Vehículo", "Comprador", "Fecha", "Estado");
	private List<Solicitud> lSolicitudes;

	public ModeloSolicitudes(List<Solicitud> ls) {
		this.lSolicitudes = ls;
	}

	@Override
	public int getRowCount() {
		if (this.lSolicitudes == null)
			return 0;
		return this.lSolicitudes.size();
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
		Solicitud s = this.lSolicitudes.get(row);
		switch (column) {
		case 0: return s.getVehiculo().getMarca() + " " + s.getVehiculo().getModelo() + " · " + s.getVehiculo().getPrecio() + " €";
		case 1: return s.getComprador();
		case 2: return s.getFecha();
		case 3: return s.getEstado();
		default: return null;
		}
	}

}
