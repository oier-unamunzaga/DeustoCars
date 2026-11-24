package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import domain.Vehiculo;
import domain.Vehiculo.Estado;
import domain.Vehiculo.Marca;
import domain.Vehiculo.Tipo;
import gui.tablas.RendererCombustible;
import gui.tablas.RendererMarca;


public class VentanaCatalogo extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JTextField txtBuscar;
	
	private JComboBox<String> cmbMarca;
	private JComboBox<String> cmbCombustible;
	private JComboBox<String> cmbAnyo;
	private JComboBox<String> cmbPrecio;
	private JComboBox<String> cmbKm;
	
	private JTable tablaVehiculos;
	private DefaultTableModel modeloTabla;
	
	private JScrollPane scrollTabla;
	
	private JComboBox<String> cmbOrdenar;
	
	private JLabel lblResultados;

	private ArrayList<Vehiculo> vehiculos;

	public VentanaCatalogo() {
		setTitle("DeustoCars");
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel(new BorderLayout(0, 10));
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		contentPane.add(crearPanelSuperior(), BorderLayout.NORTH);
		contentPane.add(crearPanelTabla(), BorderLayout.CENTER);
		contentPane.add(crearPanelInferior(), BorderLayout.SOUTH);
	}

	private JPanel crearPanelSuperior() {
		JPanel panelSuperior = new JPanel(new BorderLayout(0, 10));

		panelSuperior.add(crearPanelBusqueda(), BorderLayout.NORTH);
		panelSuperior.add(crearPanelFiltros(), BorderLayout.CENTER);

		return crearRecuadro(panelSuperior);
	}

	private JPanel crearPanelBusqueda() {
		JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));

		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setFont(lblBuscar.getFont().deriveFont(Font.BOLD));
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		txtBuscar.setPreferredSize(new Dimension(1030, 40));
		// filtra cada vez que se suelta una tecla
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar();
			}
		});
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

		return panelBusqueda;
	}

	private JPanel crearPanelFiltros() {
		// fila 1: títulos, fila 2: combos
		JPanel panelFiltros = new JPanel(new GridLayout(2, 5, 30, 5));

		String[] titulos = {"Marca", "Combustible", "Año desde", "Precio hasta", "Km hasta"};
		for (String titulo : titulos) {
			JLabel lblTitulo = new JLabel(titulo);
			lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD));
			panelFiltros.add(lblTitulo);
		}

		cmbMarca = new JComboBox<String>();
		cmbMarca.addItem("Todas");
		cmbMarca.addItem("BMW");
		cmbMarca.addItem("Hyundai");
		cmbMarca.addItem("Seat");
		cmbMarca.addItem("Tesla");
		cmbMarca.addItem("Toyota");
		cmbMarca.addItem("Volkswagen");
		cmbMarca.setPreferredSize(new Dimension(220, 40));
		panelFiltros.add(cmbMarca);

		cmbCombustible = new JComboBox<String>();
		cmbCombustible.addItem("Todos");
		cmbCombustible.addItem("Gasolina");
		cmbCombustible.addItem("Hibrido");
		cmbCombustible.addItem("Electrico");
		cmbCombustible.setPreferredSize(new Dimension(220, 40));
		panelFiltros.add(cmbCombustible);

		cmbAnyo = new JComboBox<String>();
		cmbAnyo.addItem("Todos");
		cmbAnyo.addItem("2015");
		cmbAnyo.addItem("2018");
		cmbAnyo.addItem("2020");
		cmbAnyo.addItem("2022");
		cmbAnyo.addItem("2024");
		cmbAnyo.setPreferredSize(new Dimension(220, 40));
		panelFiltros.add(cmbAnyo);

		cmbPrecio = new JComboBox<String>();
		cmbPrecio.addItem("Todos");
		cmbPrecio.addItem("10000");
		cmbPrecio.addItem("20000");
		cmbPrecio.addItem("30000");
		cmbPrecio.addItem("50000");
		cmbPrecio.setPreferredSize(new Dimension(220, 40));
		panelFiltros.add(cmbPrecio);

		cmbKm = new JComboBox<String>();
		cmbKm.addItem("Todos");
		cmbKm.addItem("25000");
		cmbKm.addItem("50000");
		cmbKm.addItem("100000");
		cmbKm.addItem("150000");
		cmbKm.setPreferredSize(new Dimension(220, 40));
		panelFiltros.add(cmbKm);

		return panelFiltros;
	}

	private JPanel crearPanelTabla() {
		JPanel panelTabla = new JPanel(new BorderLayout());

		String[] columnas = {"Marca", "Modelo", "Año", "Precio", "Km", "Combustible"};
		modeloTabla = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			// ninguna celda editable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		vehiculos = cargarVehiculosPrueba();
		for (Vehiculo vehiculo : vehiculos) {
			Object[] fila = {vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getAnio(), vehiculo.getPrecio(), vehiculo.getKm(), vehiculo.getTipo()};
			modeloTabla.addRow(fila);
		}

		tablaVehiculos = new JTable(modeloTabla);
		tablaVehiculos.setRowHeight(55);
		tablaVehiculos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

		tablaVehiculos.getColumnModel().getColumn(0).setPreferredWidth(180);
		tablaVehiculos.getColumnModel().getColumn(1).setPreferredWidth(260);
		tablaVehiculos.getColumnModel().getColumn(2).setPreferredWidth(140);
		tablaVehiculos.getColumnModel().getColumn(3).setPreferredWidth(180);
		tablaVehiculos.getColumnModel().getColumn(4).setPreferredWidth(160);
		tablaVehiculos.getColumnModel().getColumn(5).setPreferredWidth(250);

		// año, precio y km centrados
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(JLabel.CENTER);
		tablaVehiculos.getColumnModel().getColumn(2).setCellRenderer(centrado);
		tablaVehiculos.getColumnModel().getColumn(3).setCellRenderer(centrado);
		tablaVehiculos.getColumnModel().getColumn(4).setCellRenderer(centrado);

		// ayudado y comprobado con la IA
		// deja 20 px a la izquierda dentro de la celda
		DefaultTableCellRenderer conMargen = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel celda = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				celda.setBorder(new EmptyBorder(0, 20, 0, 0));
				return celda;
			}
		};
		tablaVehiculos.getColumnModel().getColumn(0).setCellRenderer(new RendererMarca());
		tablaVehiculos.getColumnModel().getColumn(1).setCellRenderer(conMargen);
		tablaVehiculos.getColumnModel().getColumn(5).setCellRenderer(new RendererCombustible());

		scrollTabla = new JScrollPane(tablaVehiculos);
		panelTabla.add(scrollTabla, BorderLayout.CENTER);

		return crearRecuadro(panelTabla);
	}

	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel(new BorderLayout());

		JPanel panelOrdenar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

		JLabel lblOrdenar = new JLabel("Ordenar por:");
		lblOrdenar.setFont(lblOrdenar.getFont().deriveFont(Font.BOLD));
		panelOrdenar.add(lblOrdenar);

		cmbOrdenar = new JComboBox<String>();
		cmbOrdenar.addItem("Precio");
		cmbOrdenar.addItem("Año");
		cmbOrdenar.addItem("Km");
		cmbOrdenar.setPreferredSize(new Dimension(250, 40));
		panelOrdenar.add(cmbOrdenar);

		panelInferior.add(panelOrdenar, BorderLayout.WEST);

		lblResultados = new JLabel(modeloTabla.getRowCount() + " resultados");
		panelInferior.add(lblResultados, BorderLayout.EAST);

		return crearRecuadro(panelInferior);
	}

	// vacía la tabla y añade solo los coches cuya marca o modelo contienen el texto
	private void filtrar() {
		String texto = txtBuscar.getText().toLowerCase();

		modeloTabla.setRowCount(0);
		for (Vehiculo vehiculo : vehiculos) {
			String marca = vehiculo.getMarca().toString().toLowerCase();
			String modelo = vehiculo.getModelo().toLowerCase();

			if (marca.contains(texto) || modelo.contains(texto)) {
				Object[] fila = {vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getAnio(), vehiculo.getPrecio(), vehiculo.getKm(), vehiculo.getTipo()};
				modeloTabla.addRow(fila);
			}
		}

		lblResultados.setText(modeloTabla.getRowCount() + " resultados");
	}

	// recuadro verde con el contenido separado 10 px del borde
	private JPanel crearRecuadro(JPanel contenido) {
		contenido.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel recuadro = new JPanel(new BorderLayout());
		recuadro.setBorder(BorderFactory.createLineBorder(new Color(46, 139, 87), 2));
		recuadro.add(contenido, BorderLayout.CENTER);

		return recuadro;
	}

	private ArrayList<Vehiculo> cargarVehiculosPrueba() {
		ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		vehiculos.add(new Vehiculo(Marca.TESLA, "Model 3", 2024, 41000, Estado.EN_VENTA, Tipo.ELECTRICO, "1234ABC", 30000));
		vehiculos.add(new Vehiculo(Marca.TOYOTA, "Corolla Hybrid", 2022, 24500, Estado.EN_VENTA, Tipo.HIBRIDO, "1254ABC", 3000));
		vehiculos.add(new Vehiculo(Marca.VOLKSWAGEN, "Golf", 2021, 19900, Estado.EN_VENTA, Tipo.GASOLINA, "9012GHI", 20000));
		vehiculos.add(new Vehiculo(Marca.BMW, "320i", 2020, 27800, Estado.EN_VENTA, Tipo.GASOLINA, "3456JKL", 12000));
		vehiculos.add(new Vehiculo(Marca.HYUNDAI, "Kona EV", 2023, 29950, Estado.EN_VENTA, Tipo.HIBRIDO, "7890MNO", 21000));
		return vehiculos;
	}

}
