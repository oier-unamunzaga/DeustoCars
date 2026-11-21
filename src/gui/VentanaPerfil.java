package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import domain.Solicitud;
import domain.Vehiculo;

public class VentanaPerfil extends JFrame{
	//Paneles
	private JPanel panelNorte, panelUsuario, panelCentro, panelCabecera, panelTitulo, panelEditar, panelSecciones, panelTabla, panelAcciones;
	
	// Componentes
	private JLabel lblLogo, lblUsuario, lblMiPerfil, lblSubtitulo, lblSeccion, lblNota;
	private JButton btnPerfil, btnCerrarSesion, btnEditarDatos;
	private JButton btnMisAnuncios, btnFavoritos, btnMisSolicitudes, btnSolicitudesRecibidas;
	private JButton btnAccion1, btnAccion2;
	
	//Tabla
	private DefaultTableModel modeloTabla;
	private JTable tabla;
	private JScrollPane scrollTabla;
	
	//Datos
	private List<Vehiculo> lFavoritos;
	private List<Solicitud> lMisSolicitudes, lSolicitudesRecibidas;
	private String seccion;
	
	public VentanaPerfil(String usuario, List<Vehiculo> lf, List<Solicitud> lms, List<Solicitud> lsr) {
		super();
		lFavoritos = lf;
		lMisSolicitudes = lms;
		lSolicitudesRecibidas = lsr;
		
		//Propiedades de la ventana
		setBounds(200, 100, 900, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("DeustoCars - Mi Perfil");
		
		//Creacion de paneles
		panelNorte = new JPanel(new BorderLayout());
		panelUsuario = new JPanel(); 
		panelCentro = new JPanel(new BorderLayout());
		panelCabecera = new JPanel(new GridLayout(3, 1));
		panelTitulo = new JPanel(new BorderLayout());
		panelEditar = new JPanel(); 
		panelSecciones = new JPanel(); 
		panelTabla = new JPanel(new BorderLayout());
		panelTabla.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		panelAcciones = new JPanel(); 
		
		//Colocar los paneles
		add(panelNorte, BorderLayout.NORTH);
		add(panelCentro, BorderLayout.CENTER);
		panelCentro.add(panelCabecera, BorderLayout.NORTH);
		panelCentro.add(panelTabla, BorderLayout.CENTER);
		panelCabecera.add(panelTitulo);
		
		
		//Creacion de componentes
		
		//panelNorte: logo a la izquierda, usuario y botones a la derecha
		lblLogo = new JLabel("DeustoCars");
		lblLogo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		lblUsuario = new JLabel("Usuario: " + usuario);
		btnPerfil = new JButton("Perfil");
		btnCerrarSesion = new JButton("Cerrar sesión");
		panelUsuario.add(lblUsuario);
		panelUsuario.add(btnPerfil);
		panelUsuario.add(btnCerrarSesion);
		panelNorte.add(lblLogo, BorderLayout.WEST);
		panelNorte.add(panelUsuario, BorderLayout.EAST);

		//panelCabecera fila 1: "Mi perfil" y botón Editar datos
		lblMiPerfil = new JLabel("Mi perfil");
		lblMiPerfil.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		btnEditarDatos = new JButton("Editar datos");
		panelEditar.add(btnEditarDatos);
		panelTitulo.add(lblMiPerfil, BorderLayout.WEST);
		panelTitulo.add(panelEditar, BorderLayout.EAST);

		//panelCabecera fila 2: subtítulo
		lblSubtitulo = new JLabel("Gestiona tus anuncios, favoritos y solicitudes");
		panelCabecera.add(lblSubtitulo);

		//panelCabecera fila 3: botones de sección
		btnMisAnuncios = new JButton("Mis anuncios");
		btnFavoritos = new JButton("Mis favoritos");
		btnMisSolicitudes = new JButton("Mis solicitudes");
		btnSolicitudesRecibidas = new JButton("Solicitudes recibidas");
		panelSecciones.add(btnMisAnuncios);
		panelSecciones.add(btnFavoritos);
		panelSecciones.add(btnMisSolicitudes);
		panelSecciones.add(btnSolicitudesRecibidas);
		panelCabecera.add(panelSecciones);

		//panelTabla: título de la sección, tabla y botones de acción
		lblSeccion = new JLabel("Mis favoritos");
		lblSeccion.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		lblSeccion.setOpaque(true);
		lblSeccion.setBackground(new Color(200, 220, 255));

		modeloTabla = new ModeloFavoritos(lFavoritos);
		tabla = new JTable(modeloTabla);
		scrollTabla = new JScrollPane(tabla);
		tabla.setRowHeight(30);
		tabla.getTableHeader().setReorderingAllowed(false);
		seccion = "FAVORITOS";

		btnAccion1 = new JButton("Ver detalle");
		btnAccion2 = new JButton("Quitar de favoritos");
		panelAcciones.add(btnAccion1);
		panelAcciones.add(btnAccion2);

		panelTabla.add(lblSeccion, BorderLayout.NORTH);
		panelTabla.add(scrollTabla, BorderLayout.CENTER);
		panelTabla.add(panelAcciones, BorderLayout.SOUTH);

		//Nota al pie de la ventana
		lblNota = new JLabel("Desde \"Mis anuncios\" se accede a la Ventana 3 para crear, editar o eliminar vehículos.");
		add(lblNota, BorderLayout.SOUTH);
		
		//Renderer
		
		//Pinta la columna del estado según su valor
		tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel l = new JLabel();
				if (value != null) { //Evita el NullPointerException si la celda está vacía
					l.setText(value.toString());
				}
				l.setOpaque(true);
				if (isSelected) { //Para que se vea la fila seleccionada
					l.setBackground(Color.LIGHT_GRAY);
				} else {
					l.setBackground(Color.WHITE);
				}
				if ("En venta".equals(value) || "Aceptada".equals(value)) {
					l.setForeground(new Color(0, 130, 0));
				} else if ("Vendido".equals(value) || "Rechazada".equals(value)) {
					l.setForeground(Color.RED);
				} else if ("Pendiente".equals(value)) {
					l.setForeground(Color.ORANGE);
				}
				return l;
			}
		});
		
		//Listeners
		btnMisAnuncios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Aquí se abrirá la Ventana 3 de Oier
				JOptionPane.showMessageDialog(null, "Aquí se abrirá la Ventana 3 (Publicar y gestionar anuncios)");
			}
		});

		btnFavoritos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modeloTabla = new ModeloFavoritos(lFavoritos);
				tabla.setModel(modeloTabla);
				lblSeccion.setText("Mis favoritos");
				btnAccion1.setText("Ver detalle");
				btnAccion2.setText("Quitar de favoritos");
				btnAccion2.setVisible(true);
				seccion = "FAVORITOS";
			}
		});

		btnMisSolicitudes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modeloTabla = new ModeloSolicitudes(lMisSolicitudes);
				tabla.setModel(modeloTabla);
				lblSeccion.setText("Mis solicitudes");
				btnAccion1.setText("Ver detalle");
				btnAccion2.setVisible(false); //En esta sección solo hay un botón
				seccion = "MIS_SOLICITUDES";
			}
		});

		btnSolicitudesRecibidas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modeloTabla = new ModeloSolicitudes(lSolicitudesRecibidas);
				tabla.setModel(modeloTabla);
				lblSeccion.setText("Solicitudes recibidas");
				btnAccion1.setText("Aceptar");
				btnAccion2.setText("Rechazar");
				btnAccion2.setVisible(true);
				seccion = "RECIBIDAS";
			}
		});

		btnAccion1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//De momento solo avisa; falta saber qué fila está seleccionada
				JOptionPane.showMessageDialog(null, btnAccion1.getText() + " (sección " + seccion + ") - pendiente");
			}
		});

		btnAccion2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, btnAccion2.getText() + " (sección " + seccion + ") - pendiente");
			}
		});

		btnEditarDatos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Editar datos - pendiente");
			}
		});

		btnCerrarSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Aquí se volverá a la Ventana 5 (Iniciar sesión)
				JOptionPane.showMessageDialog(null, "Cerrar sesión - pendiente");
			}
		});
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		//Datos de prueba
		Vehiculo v1 = new Vehiculo("Toyota", "Corolla", 2019, 18500, "En venta", "Hibrido", "1234ABC", 60000);
		Vehiculo v2 = new Vehiculo("Seat", "Ibiza", 2015, 9900, "Vendido", "Gasolina", "5678DEF", 120000);
		Vehiculo v3 = new Vehiculo("Tesla", "Model 3", 2021, 32000, "En venta", "Electrico", "9012GHI", 40000);

		List<Vehiculo> lf = new ArrayList<>();
		lf.add(v1);
		lf.add(v2);

		List<Solicitud> lms = new ArrayList<>();
		lms.add(new Solicitud(v3, "pablo", "18/11/2026", "Pendiente"));

		List<Solicitud> lsr = new ArrayList<>();
		lsr.add(new Solicitud(v1, "ana_g", "17/11/2026", "Pendiente"));
		lsr.add(new Solicitud(v1, "mikel", "15/11/2026", "Rechazada"));

		new VentanaPerfil("pablo", lf, lms, lsr);
	}
}
