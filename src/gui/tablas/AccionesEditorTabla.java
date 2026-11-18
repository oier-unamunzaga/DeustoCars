package gui.tablas;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import domain.Vehiculo;
import gui.VentanaPublicacionYGestionAnuncios;

//Clase para hacer el TableCellEditor
public class AccionesEditorTabla extends AbstractCellEditor implements TableCellEditor {

    private static final long serialVersionUID = 1L;

    private final JPanel pPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 2));
    private final VentanaPublicacionYGestionAnuncios ventana;

    public AccionesEditorTabla(VentanaPublicacionYGestionAnuncios ventana) {
        this.ventana = ventana;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelectes, int row, int column) {
    	pPrincipal.removeAll();
        Vehiculo coche = (Vehiculo) value;

        JButton bEliminar = new JButton("Eliminar vehículo");
        bEliminar.addActionListener(e -> {
        	//Notifica al JTable que la celda se ha editado
            fireEditingStopped();
            ventana.eliminarVehiculo(coche);
        });
        pPrincipal.add(bEliminar);

        if (coche.getEstado().equals("En venta")) {
            JButton bEditar = new JButton("Editar anuncio");
            bEditar.addActionListener(e -> {
            	//Notifica al JTable que la celda se ha editado
                fireEditingStopped();
                ventana.mostrarDetalles(coche, true);
            });
            pPrincipal.add(bEditar);
        }

        pPrincipal.repaint();
        return pPrincipal;
    }
    
    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
