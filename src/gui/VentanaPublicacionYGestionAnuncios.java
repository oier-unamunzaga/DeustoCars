package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import domain.Vehiculo;
import domain.Vehiculo.Estado;
import domain.Vehiculo.Marca;
import domain.Vehiculo.Tipo;
import gui.tablas.ModeloTablaAnuncios;
import gui.util.BotonVerMarcasDisponibles;
import gui.util.ComboBoxMarcas;
import gui.util.ComboBoxEstadoVehiculo;
import gui.util.ComboBoxTipoCombustible;
//Las dimensiones para que quede todo mas o menos homogeneo me las ha dado la IA
public class VentanaPublicacionYGestionAnuncios extends JFrame {

    private static final long serialVersionUID = 1L;

    private final List<Vehiculo> coches = new ArrayList<>();
    private final ModeloTablaAnuncios modeloTabla;
    private final JTable tablaAnuncios;
    private final JPanel pDetalles = new JPanel(new BorderLayout(8, 8));
    private List<Marca> marcasCoches; //Guarda las marcas que hay en la tabla

    private static final JPanel contentPane = new JPanel(new BorderLayout(0, 3));

    private Vehiculo cocheSeleccionado;
    private JTextField tfMarca;
    private ComboBoxMarcas cbMarca;
    private ComboBoxEstadoVehiculo cbEstado;
    private ComboBoxTipoCombustible cbTipo;
    private JTextField tfModelo;
    private JTextField tfAnio;
    private JTextField tfPrecio;
    private JTextField tfEstado;
    private JTextField tfTipo;
    private JTextField tfMatricula;
    private JTextField tfKm;

    public VentanaPublicacionYGestionAnuncios() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setResizable(false);
        setTitle("Mis anuncios");
        setContentPane(contentPane);
        
        try {
            setIconImage(ImageIO.read(new File("resources/images/iconoApp.jpg")));
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("Error al cargar el logo.");
            e.printStackTrace();
        }

        JLabel lbTitulo = new JLabel("Mis anuncios", JLabel.LEFT);
        contentPane.add(lbTitulo, BorderLayout.NORTH);

        //Creo el modelo de la tabla
        modeloTabla = new ModeloTablaAnuncios();
        //Creo la tabla y le asigno el modelo
        tablaAnuncios = new JTable(modeloTabla);
        tablaAnuncios.setRowHeight(42);
        tablaAnuncios.getColumnModel().getColumn(4).setPreferredWidth(300);
        tablaAnuncios.getColumnModel().getColumn(4).setMinWidth(280);
        tablaAnuncios.getColumnModel().getColumn(4).setMaxWidth(320);
        //El modo de selección me lo ha dicho la IA
        tablaAnuncios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaAnuncios.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Vehiculo coche && column == 4) {
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 2));
                    panel.setOpaque(true);
                    panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                    panel.add(new JButton("Eliminar vehículo"));
                    if (Estado.EN_VENTA.equals(coche.getEstado())) {
                        panel.add(new JButton("Editar anuncio"));
                    }
                    return panel;
                } else if (value instanceof Marca m && column == 0) {
                	JLabel lbMarca = new JLabel();
                	lbMarca.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                	if (m.getRutaImagen() == null) {
                		lbMarca.setText(m.getNombre());
                	} else {
                		ImageIcon icono = new ImageIcon(m.getRutaImagen());
                		Image iconoEscalado = icono.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
                		lbMarca.setIcon(new ImageIcon(iconoEscalado));
                		lbMarca.setHorizontalAlignment(JLabel.CENTER);
                	}
                	return lbMarca;
                }

                JLabel etiqueta = new JLabel(String.valueOf(value));
                etiqueta.setOpaque(true);
                etiqueta.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                etiqueta.setHorizontalAlignment(JLabel.CENTER);
                return etiqueta;
            }
        });

        //Gestiona directamente los botones de la columna de acciones.
        //Hecho con IA para corregir errores que me estaba dando la columna de acciones
        tablaAnuncios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tablaAnuncios.rowAtPoint(e.getPoint());
				int columna = tablaAnuncios.columnAtPoint(e.getPoint());

				if (fila >= 0 && columna == 4) {
					int filaModelo = tablaAnuncios.convertRowIndexToModel(fila);
					Vehiculo coche = coches.get(filaModelo);
					int posicionEnCelda = e.getX() - tablaAnuncios.getCellRect(fila, columna, false).x;

					tablaAnuncios.removeEditor();

					int anchoCelda = tablaAnuncios.getColumnModel().getColumn(4).getWidth();
					int anchoEliminar = new JButton("Eliminar vehículo").getPreferredSize().width;
					int anchoEditar = new JButton("Editar anuncio").getPreferredSize().width;
					int separacion = 6;

					if (Estado.EN_VENTA.equals(coche.getEstado())) {
						int anchoTotal = anchoEliminar + separacion + anchoEditar;
						int inicioBotones = (anchoCelda - anchoTotal) / 2;
						int inicioEditar = inicioBotones + anchoEliminar + separacion;

						if (posicionEnCelda >= inicioBotones
								&& posicionEnCelda < inicioBotones + anchoEliminar) {
							eliminarVehiculo(coche);
						} else if (posicionEnCelda >= inicioEditar
								&& posicionEnCelda < inicioEditar + anchoEditar) {
							mostrarDetalles(coche, true);
						}
					} else {
						int inicioEliminar = (anchoCelda - anchoEliminar) / 2;
						if (posicionEnCelda >= inicioEliminar
								&& posicionEnCelda < inicioEliminar + anchoEliminar) {
							eliminarVehiculo(coche);
						}
					}
				}
			}
		});

        JScrollPane scrollTabla = new JScrollPane(tablaAnuncios);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Publicaciones"));
        pDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del vehículo"));

        //Creo un separador por si quiero que la tabla de vehiculos o el formulario sea mas grande
        //Y lo pongo mas o menos a la mitad de la pantalla
        JSplitPane separador = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, pDetalles);
        separador.setResizeWeight(1);
        separador.setDividerLocation(300);
        contentPane.add(separador, BorderLayout.CENTER);

        JPanel pBotonesParteAbajo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
        JButton bAnadirVehiculo = new JButton("Añadir nuevo vehículo");
        bAnadirVehiculo.addActionListener(e -> {
        	//Desmarco por si habia seleccionado algun vehiculo
        	tablaAnuncios.clearSelection();
        	mostrarFormularioNuevoVehiculo();
        });
        pBotonesParteAbajo.add(bAnadirVehiculo);
        pBotonesParteAbajo.add(new BotonVerMarcasDisponibles());
        contentPane.add(pBotonesParteAbajo, BorderLayout.SOUTH);

        tablaAnuncios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaVista = tablaAnuncios.getSelectedRow();
                if (filaVista >= 0) {
                    int filaModelo = tablaAnuncios.convertRowIndexToModel(filaVista);
                    mostrarDetalles(coches.get(filaModelo), false);
                }
            }
        });

        setLocationRelativeTo(null);
        cargarDatosDePrueba();
    }

    //Datos de prueba
    private void cargarDatosDePrueba() {
        coches.add(new Vehiculo(Marca.TOYOTA, "Corolla", 2021, 18500, Estado.EN_VENTA, Tipo.HIBRIDO, "1234ABC", 45000));
        coches.add(new Vehiculo(Marca.SEAT, "Ibiza", 2019, 12900, Estado.VENDIDO, Tipo.GASOLINA, "5678DEF", 80000));
        actualizarTabla();
    }

    private void actualizarTabla() {
        modeloTabla.limpiar();
        for (Vehiculo coche : coches) {
            modeloTabla.anadirFila(coche);
        }
    }

    //Metodo para rellenar el panel de detalles
    public void mostrarDetalles(Vehiculo coche, boolean editable) {
        cocheSeleccionado = coche;
        pDetalles.removeAll();

        JPanel pCampos = new JPanel(new GridLayout(8, 2, 8, 8));
        //Solo son editables el Precio y el Estado
        tfMarca = crearCampo(pCampos, "Marca:", coche.getMarca().getNombre(), false);
        tfModelo = crearCampo(pCampos, "Modelo:", coche.getModelo(), false);
        tfAnio = crearCampo(pCampos, "Año:", String.valueOf(coche.getAnio()), false);
        tfPrecio = crearCampo(pCampos, "Precio (€):", String.valueOf(coche.getPrecio()), editable);
        if (editable) {
			pCampos.add(new JLabel("Estado:"));
			cbEstado = new ComboBoxEstadoVehiculo();
			cbEstado.setSelectedItem(coche.getEstado());
			pCampos.add(cbEstado);
		} else {
			tfEstado = crearCampo(pCampos, "Estado:", coche.getEstado().toString(), false);
		}
        tfTipo = crearCampo(pCampos, "Tipo:", coche.getTipo().toString(), false);
        tfMatricula = crearCampo(pCampos, "Matrícula:", coche.getMatricula(), false);
        tfKm = crearCampo(pCampos, "Kilómetros (KM):", String.valueOf(coche.getKm()), false);
        pDetalles.add(pCampos, BorderLayout.CENTER);

        JPanel pAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        if (editable) {
            JButton bGuardar = new JButton("Guardar cambios");
            JButton bCancelar = new JButton("Cancelar edición");
            bGuardar.addActionListener(e -> guardarCambios());
            bCancelar.addActionListener(e -> mostrarDetalles(cocheSeleccionado, false));
            pAcciones.add(bGuardar);
            pAcciones.add(bCancelar);
        } else {
            pAcciones.add(new JLabel("Pulsa ‘Editar anuncio’ para modificar los datos."));
        }

        pDetalles.add(pAcciones, BorderLayout.SOUTH);
        pDetalles.revalidate();
        pDetalles.repaint();
    }

    //Metodo para añadir un nuevo vehiculo
    private void mostrarFormularioNuevoVehiculo() {
        cocheSeleccionado = null;
        pDetalles.removeAll();

        JPanel pCampos = new JPanel(new GridLayout(8, 2, 8, 8));
        pCampos.add(new JLabel("Marca:"));
        cbMarca = new ComboBoxMarcas();
        pCampos.add(cbMarca);
        tfModelo = crearCampo(pCampos, "Modelo:", "", true);
        tfAnio = crearCampo(pCampos, "Año:", "", true);
        tfPrecio = crearCampo(pCampos, "Precio (€):", "", true);
		pCampos.add(new JLabel("Estado:"));
		cbEstado = new ComboBoxEstadoVehiculo();
		pCampos.add(cbEstado);
        pCampos.add(new JLabel("Tipo:"));
        cbTipo = new ComboBoxTipoCombustible();
        pCampos.add(cbTipo);
        tfMatricula = crearCampo(pCampos, "Matrícula:", "", true);
        tfKm = crearCampo(pCampos, "Kilómetros (KM):", "", true);
        pDetalles.add(pCampos, BorderLayout.CENTER);

        JPanel pAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        JButton bGuardar = new JButton("Guardar vehículo");
        JButton bCancelar = new JButton("Cancelar");
        bGuardar.addActionListener(e -> guardarNuevoVehiculo());
        bCancelar.addActionListener(e -> {
            pDetalles.removeAll();
            pDetalles.revalidate();
            pDetalles.repaint();
        });
        pAcciones.add(bGuardar);
        pAcciones.add(bCancelar);
        pDetalles.add(pAcciones, BorderLayout.SOUTH);
        pDetalles.revalidate();
        pDetalles.repaint();
    }

    //Metodo auxiliar para los detalles
    private JTextField crearCampo(JPanel panel, String texto, String valor, boolean editable) {
        panel.add(new JLabel(texto));
        JTextField campo = new JTextField(valor);
        if (editable) {
        	campo.setBackground(Color.WHITE);
        } else {
        	campo.setBackground(Color.LIGHT_GRAY);
        }
        campo.setEditable(editable);
        panel.add(campo);
        return campo;
    }

    //Solo se pueden modificar el Precio y el Estado
    private void guardarCambios() {
        try {
        	if (Double.parseDouble(tfPrecio.getText()) <= 0) {
        		JOptionPane.showMessageDialog(this, "El Precio debe ser mayor de 0.",
                        "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
        	}
            cocheSeleccionado.setPrecio(Double.parseDouble(tfPrecio.getText()));
            cocheSeleccionado.setEstado((Estado) cbEstado.getSelectedItem());
            actualizarTabla();
            mostrarDetalles(cocheSeleccionado, false);
        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(this, "El Precio debe ser numérico.",
                    "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    //Metodo para guardar los datos del nuevo vehiculo
    private void guardarNuevoVehiculo() {
        try {
        	if (Double.parseDouble(tfPrecio.getText()) <= 0) {
        		JOptionPane.showMessageDialog(this, "El Precio debe ser mayor de 0.",
                        "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
        	}
        	if (Integer.parseInt(tfAnio.getText()) < 1800) {
        		JOptionPane.showMessageDialog(this, "El año tiene que ser mayor a 1800.", 
        				"Datos incorrectos", JOptionPane.WARNING_MESSAGE);
        	}
        	if (Integer.parseInt(tfKm.getText()) < 0) {
        		JOptionPane.showMessageDialog(this, "Los kilometros deben ser al meneos 0.",
                        "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
        	}
			Marca marcaSeleccionada = (Marca) cbMarca.getSelectedItem();
			Estado estadoSeleccionado = (Estado) cbEstado.getSelectedItem();
			Tipo tipoSeleccionado = (Tipo) cbTipo.getSelectedItem();
			Vehiculo nuevo = new Vehiculo(marcaSeleccionada, 
            		tfModelo.getText(),
                    Integer.parseInt(tfAnio.getText()), 
                    Double.parseDouble(tfPrecio.getText()),
					estadoSeleccionado, 
                    tipoSeleccionado, 
                    tfMatricula.getText(),
                    Integer.parseInt(tfKm.getText()));
            coches.add(nuevo);
            actualizarTabla();
            mostrarDetalles(nuevo, false);
        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(this, "El año y el precio deben ser numéricos.",
                    "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Metodo que se usa en el TableCellEditor
    public void eliminarVehiculo(Vehiculo coche) {
        coches.remove(coche);
        actualizarTabla();
        pDetalles.removeAll();
        pDetalles.revalidate(); //Recalcula el layout del render la tabla
        pDetalles.repaint(); //Vuelve a pintar la tabla
    }
    
    
    public static void main(String[] args) {
        VentanaPublicacionYGestionAnuncios ventana = new VentanaPublicacionYGestionAnuncios();
        ventana.setVisible(true);
    }
}
