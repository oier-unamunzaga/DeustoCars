package gui.tablas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Vehiculo;

public class ModeloTablaAnuncios extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private static final String[] columnas = {"Marca", "Modelo", "Precio (€)", "Estado", "Acciones"};
    private static final List<Vehiculo> filas = new ArrayList<>();

    @Override
    public int getRowCount() {
        return filas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columna) {
        return columnas[columna];
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Vehiculo vehiculo = filas.get(fila);
        return switch (columna) {
            case 0 -> vehiculo.getMarca();
            case 1 -> vehiculo.getModelo();
            case 2 -> vehiculo.getPrecio();
            case 3 -> vehiculo.getEstado();
            case 4 -> vehiculo;
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int fila, int columna) {
	    //La columna de acciones no se edita porque los botones se gestionan directamente desde la tabla.
	    return false;
    }

    public void limpiar() {
        filas.clear();
        //Notifica al JTable de los cambios realizados
        fireTableDataChanged();
    }

    public void anadirFila(Vehiculo vehiculo) {
        filas.add(vehiculo);
        //Notifica al JTable de los cambios realizados
        fireTableDataChanged();
    }
}
