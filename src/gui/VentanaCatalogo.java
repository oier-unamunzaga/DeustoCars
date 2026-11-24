package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.Vehiculo;
import domain.Vehiculo.Estado;
import domain.Vehiculo.Marca;
import domain.Vehiculo.Tipo;


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
		panelSuperior.setBorder(crearBorde());

		panelSuperior.add(crearPanelBusqueda(), BorderLayout.NORTH);
		panelSuperior.add(crearPanelFiltros(), BorderLayout.CENTER);

		return panelSuperior;
	}

	private JPanel crearPanelBusqueda() {
		JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));

		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setFont(lblBuscar.getFont().deriveFont(Font.BOLD));
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		txtBuscar.setPreferredSize(new Dimension(1030, 40));
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

		return panelBusqueda;
	}

	private JPanel crearPanelFiltros() {
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
		panelTabla.setBorder(crearBorde());

		String[] columnas = {"Foto", "Marca", "Modelo", "Año", "Precio", "Km", "Combustible"};
		modeloTabla = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (Vehiculo vehiculo : cargarVehiculosPrueba()) {
			Object[] fila = {"", vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getAnio(), vehiculo.getPrecio(), vehiculo.getKm(), vehiculo.getTipo()};
			modeloTabla.addRow(fila);
		}

		tablaVehiculos = new JTable(modeloTabla);
		tablaVehiculos.setRowHeight(55);

		Dimension tamanyoCabecera = tablaVehiculos.getTableHeader().getPreferredSize();
		tablaVehiculos.getTableHeader().setPreferredSize(new Dimension(tamanyoCabecera.width, 40));

		int[] anchos = {180, 135, 210, 125, 160, 145, 215};
		for (int i = 0; i < anchos.length; i++) {
			tablaVehiculos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}

		scrollTabla = new JScrollPane(tablaVehiculos);
		panelTabla.add(scrollTabla, BorderLayout.CENTER);

		return panelTabla;
	}

	private JPanel crearPanelInferior() {
		JPanel panelInferior = new JPanel(new BorderLayout());
		panelInferior.setBorder(crearBorde());

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

		return panelInferior;
	}

	private Border crearBorde() {
		Border linea = BorderFactory.createLineBorder(new Color(46, 139, 87), 2);
		Border margen = new EmptyBorder(10, 10, 10, 10);
		return BorderFactory.createCompoundBorder(linea, margen);
	}

	private ArrayList<Vehiculo> cargarVehiculosPrueba() {
		ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
		vehiculos.add(new Vehiculo(Marca.TESLA, "Model 3", 2024, 41000, Estado.EN_VENTA, Tipo.ELECTRICO, "1234ABC", 30000));
		vehiculos.add(new Vehiculo(Marca.TOYOTA, "Corolla Hybrid", 2022, 24500, Estado.EN_VENTA, Tipo.HIBRIDO, "1254ABC", 3000));
		vehiculos.add(new Vehiculo(Marca.VOLKSWAGEN,"Golf", 2021, 19900, Estado.EN_VENTA, Tipo.GASOLINA, "9012GHI", 20000));
		vehiculos.add(new Vehiculo(Marca.BMW, "320i", 2020, 27800, Estado.EN_VENTA, Tipo.GASOLINA, "3456JKL", 12000));
		vehiculos.add(new Vehiculo(Marca.HYUNDAI, "Kona EV", 2023, 29950, Estado.EN_VENTA, Tipo.HIBRIDO, "7890MNO", 21000));
		return vehiculos;
	}

}
