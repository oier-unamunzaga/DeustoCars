package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private int filaSeleccionada; //Fila de la tabla en la que ha hecho click el usuario (-1 si no hay ninguna)
	
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
		filaSeleccionada = -1; //Al principio no hay ninguna fila seleccionada

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
				//El estado del vehículo es un enum (Vehiculo.Estado) y el de la solicitud un String
				if (Vehiculo.Estado.EN_VENTA.equals(value) || "Aceptada".equals(value)) {
					l.setForeground(new Color(0, 130, 0));
				} else if (Vehiculo.Estado.VENDIDO.equals(value) || "Rechazada".equals(value)) {
					l.setForeground(Color.RED);
				} else if ("Pendiente".equals(value)) {
					l.setForeground(Color.ORANGE);
				}
				return l;
			}
		});
		
		//Listeners

		//Al hacer click en la tabla guardamos la fila pulsada
		tabla.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint(); //Coordenadas (x, y) en las que se ha hecho click
				filaSeleccionada = tabla.rowAtPoint(p); //Fila que hay en ese punto (-1 si no hay ninguna)
			}
		});

		btnMisAnuncios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Abrimos la Ventana 3 de Oier (Publicar y gestionar anuncios)
				VentanaPublicacionYGestionAnuncios ventana = new VentanaPublicacionYGestionAnuncios();
				ventana.setVisible(true);
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
				filaSeleccionada = -1;
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
				filaSeleccionada = -1;
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
				filaSeleccionada = -1;
			}
		});

		//Perfil: vuelve a la sección inicial (Mis favoritos)
		btnPerfil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnFavoritos.doClick(); //Hace lo mismo que si se pulsara el botón Mis favoritos
			}
		});

		//Botón de acción 1: Ver detalle (favoritos y mis solicitudes) o Aceptar (recibidas)
		btnAccion1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona primero una fila de la tabla");
				} else if (seccion.equals("FAVORITOS")) {
					Vehiculo v = lFavoritos.get(filaSeleccionada);
					JOptionPane.showMessageDialog(null, "Marca: " + v.getMarca() + "\nModelo: " + v.getModelo()
							+ "\nAño: " + v.getAnio() + "\nPrecio: " + v.getPrecio() + " €" + "\nKm: " + v.getKm()
							+ "\nTipo: " + v.getTipo() + "\nMatrícula: " + v.getMatricula() + "\nEstado: " + v.getEstado());
				} else if (seccion.equals("MIS_SOLICITUDES")) {
					Solicitud s = lMisSolicitudes.get(filaSeleccionada);
					Vehiculo v = s.getVehiculo();
					JOptionPane.showMessageDialog(null, "Marca: " + v.getMarca() + "\nModelo: " + v.getModelo()
							+ "\nAño: " + v.getAnio() + "\nPrecio: " + v.getPrecio() + " €" + "\nKm: " + v.getKm()
							+ "\nTipo: " + v.getTipo() + "\nMatrícula: " + v.getMatricula()
							+ "\n\nFecha de la solicitud: " + s.getFecha() + "\nEstado de la solicitud: " + s.getEstado());
				} else if (seccion.equals("RECIBIDAS")) {
					//Aceptar la solicitud
					Solicitud s = lSolicitudesRecibidas.get(filaSeleccionada);
					if (!s.getEstado().equals("Pendiente")) {
						JOptionPane.showMessageDialog(null, "Esta solicitud ya está " + s.getEstado());
					} else {
						s.setEstado("Aceptada");
						s.getVehiculo().setEstado(Vehiculo.Estado.VENDIDO); //RF-19: el anuncio pasa a vendido
						//El resto de solicitudes pendientes de ese mismo coche se rechazan
						for (Solicitud otra : lSolicitudesRecibidas) {
							if (otra != s && otra.getEstado().equals("Pendiente")
									&& otra.getVehiculo().getMatricula().equals(s.getVehiculo().getMatricula())) {
								otra.setEstado("Rechazada");
							}
						}
						modeloTabla = new ModeloSolicitudes(lSolicitudesRecibidas);
						tabla.setModel(modeloTabla);
						filaSeleccionada = -1;
						JOptionPane.showMessageDialog(null, "Solicitud aceptada. El vehículo ha pasado a Vendido");
					}
				}
			}
		});

		//Botón de acción 2: Quitar de favoritos (favoritos) o Rechazar (recibidas)
		btnAccion2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (filaSeleccionada == -1) {
					JOptionPane.showMessageDialog(null, "Selecciona primero una fila de la tabla");
				} else if (seccion.equals("FAVORITOS")) {
					Vehiculo v = lFavoritos.get(filaSeleccionada);
					lFavoritos.remove(filaSeleccionada); //RF-16: quitar de favoritos
					modeloTabla = new ModeloFavoritos(lFavoritos);
					tabla.setModel(modeloTabla);
					filaSeleccionada = -1;
					JOptionPane.showMessageDialog(null, v.getMarca() + " " + v.getModelo() + " quitado de favoritos");
				} else if (seccion.equals("RECIBIDAS")) {
					//Rechazar la solicitud
					Solicitud s = lSolicitudesRecibidas.get(filaSeleccionada);
					if (!s.getEstado().equals("Pendiente")) {
						JOptionPane.showMessageDialog(null, "Esta solicitud ya está " + s.getEstado());
					} else {
						s.setEstado("Rechazada");
						modeloTabla = new ModeloSolicitudes(lSolicitudesRecibidas);
						tabla.setModel(modeloTabla);
						filaSeleccionada = -1;
						JOptionPane.showMessageDialog(null, "Solicitud rechazada");
					}
				}
			}
		});

		btnEditarDatos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Abre la ventana para editar los datos; le pasamos la etiqueta del usuario para que la actualice
				new VentanaEditarDatos(lblUsuario);
			}
		});

		btnCerrarSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Volvemos a la Ventana 5 (Iniciar sesión) y cerramos esta, igual que hace Oier en sus ventanas
				JOptionPane.showMessageDialog(null, "Has cerrado sesión");
				VentanaIniciarSesion ventana = new VentanaIniciarSesion();
				ventana.setVisible(true);
				dispose(); //Cerramos esta ventana
			}
		});
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		//Datos de prueba
		Vehiculo v1 = new Vehiculo(Vehiculo.Marca.TOYOTA, "Corolla", 2019, 18500, Vehiculo.Estado.EN_VENTA, Vehiculo.Tipo.HIBRIDO, "1234ABC", 60000);
		Vehiculo v2 = new Vehiculo(Vehiculo.Marca.SEAT, "Ibiza", 2015, 9900, Vehiculo.Estado.VENDIDO, Vehiculo.Tipo.GASOLINA, "5678DEF", 120000);
		Vehiculo v3 = new Vehiculo(Vehiculo.Marca.TESLA, "Model 3", 2021, 32000, Vehiculo.Estado.EN_VENTA, Vehiculo.Tipo.ELECTRICO, "9012GHI", 40000);

		List<Vehiculo> lf = new ArrayList<>();
		lf.add(v1);
		lf.add(v2);

		List<Solicitud> lms = new ArrayList<>();
		lms.add(new Solicitud(v3, "pablo", "18/11/2026", "Pendiente"));

		List<Solicitud> lsr = new ArrayList<>();
		lsr.add(new Solicitud(v1, "ana_g", "17/11/2026", "Pendiente"));
		lsr.add(new Solicitud(v1, "mikel", "15/11/2026", "Pendiente"));

		new VentanaPerfil("pablo", lf, lms, lsr);
	}
}