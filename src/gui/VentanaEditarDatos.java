package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaEditarDatos extends JFrame {
	//Paneles
	private JPanel panelDatos, panelBotones;

	//Componentes
	private JLabel lblNuevoUsuario, lblCorreo;
	private JTextField txtNuevoUsuario, txtCorreo;
	private JButton btnGuardar, btnCancelar;

	public VentanaEditarDatos(JLabel lblUsuario) {
		super();

		//Propiedades de la ventana
		setBounds(350, 250, 400, 180);
		setResizable(false);
		setTitle("Editar datos");
		//No ponemos EXIT_ON_CLOSE: si no, al cerrar esta ventana se cerraría toda la aplicación.
		//Por defecto, al cerrar un JFrame solo se oculta.

		//Creación de paneles
		panelDatos = new JPanel(new GridLayout(2, 2, 5, 5));
		panelBotones = new JPanel(); //FlowLayout por defecto
		add(panelDatos, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);

		//Creación de componentes
		lblNuevoUsuario = new JLabel("Nombre de usuario:");
		txtNuevoUsuario = new JTextField(20);
		lblCorreo = new JLabel("Correo electrónico:");
		txtCorreo = new JTextField(20);
		btnGuardar = new JButton("Guardar");
		btnCancelar = new JButton("Cancelar");

		panelDatos.add(lblNuevoUsuario);
		panelDatos.add(txtNuevoUsuario);
		panelDatos.add(lblCorreo);
		panelDatos.add(txtCorreo);
		panelBotones.add(btnGuardar);
		panelBotones.add(btnCancelar);

		//Listeners
		btnGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNuevoUsuario.getText();
				String correo = txtCorreo.getText();
				if (nombre.equals("") || correo.equals("")) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos");
				} else if (nombre.contains(" ")) {
					JOptionPane.showMessageDialog(null, "El nombre de usuario no puede tener espacios");
				} else if (!correo.contains("@") || !correo.contains(".")) {
					JOptionPane.showMessageDialog(null, "El correo no es válido (debe tener @ y un punto)");
				} else {
					lblUsuario.setText("Usuario: " + nombre); //Actualizamos la etiqueta de la VentanaPerfil
					JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
					setVisible(false); //Ocultamos esta ventana
				}
			}
		});

		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false); //Ocultamos esta ventana sin guardar nada
			}
		});

		setVisible(true);
	}

}