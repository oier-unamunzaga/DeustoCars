package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Luego JDialog
public class VentanaCrearCuenta extends JFrame {

    private static final long serialVersionUID = 1L;

    // MODIFICACIÓN: se reduce la separación entre el logo y el contenido.
    private static JPanel contentPane = new JPanel(new BorderLayout(0, 3));

    public VentanaCrearCuenta() {

        //Configuración de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // MODIFICACIÓN: se adapta la altura de la ventana al contenido real.
        setSize(540, 380);
        // setLocationRelativeTo(La ventana principal);
        setResizable(false);
        setTitle("Registrarse");
        setContentPane(contentPane);

        try {
            setIconImage(ImageIO.read(new File("resources/images/iconoApp.jpg")));
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("Error al cargar el logo.");
            e.printStackTrace();
        }

        //Configuración del panel del logo
        JPanel pLogo = new JPanel();
        pLogo.setBackground(Color.WHITE);
        ImageIcon iconoO = new ImageIcon("resources/images/logo.png");
        Image imagenEscalada = iconoO.getImage().getScaledInstance(180, 90, Image.SCALE_SMOOTH);
        JLabel lbLogo = new JLabel(new ImageIcon(imagenEscalada));
        pLogo.add(lbLogo);
        contentPane.add(pLogo, BorderLayout.NORTH);

        //IA utilizada para que me explique como funciona el GridBadLayout
        JPanel pCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 5, 0);

        //Panel Nombre y Apellido
        JPanel nYa = new JPanel(new GridLayout(1, 2, 20, 0));

        //Panel nombre
        JPanel pNombre = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel lbNombre = new JLabel("Nombre:", JLabel.LEFT);
        pNombre.add(lbNombre);
        JTextField tfNombre = new JTextField();
        pNombre.add(tfNombre);

        //Panel apellido
        JPanel pApellido = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel lbApellido = new JLabel("Apellido:", JLabel.LEFT);
        pApellido.add(lbApellido);
        JTextField tfApellido = new JTextField();
        pApellido.add(tfApellido);

        nYa.add(pNombre);
        nYa.add(pApellido);

        //Panel correo
        JPanel pCorreo = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel lbCorreo = new JLabel("Correo electrónico:", JLabel.LEFT);
        pCorreo.add(lbCorreo);
        JTextField tfCorreo = new JTextField();
        pCorreo.add(tfCorreo);

        //Panel contraseña
        JPanel pContraseña = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel lbContraseña = new JLabel("Contraseña (mínimo 8 dígitos):", JLabel.LEFT);
        pContraseña.add(lbContraseña);
        JPanel pCampoContraseña = new JPanel(new BorderLayout(5, 0));
        JPasswordField tfContraseña = new JPasswordField();
        //Le asigno ese simbolo al PasswordField para que la contraseña no sea visible
        tfContraseña.setEchoChar('•');
        JButton bMostrarContraseña = new JButton("Mostrar");
        pCampoContraseña.add(tfContraseña, BorderLayout.CENTER);
        pCampoContraseña.add(bMostrarContraseña, BorderLayout.EAST);
        pContraseña.add(pCampoContraseña);

        //Botón para mostrar u ocultar la contraseña
        bMostrarContraseña.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tfContraseña.getEchoChar() == 0) {
					tfContraseña.setEchoChar('•');
					bMostrarContraseña.setText("Mostrar");
				} else {
					//Esto sirve para mostrar el caracter que hay tal cual
					tfContraseña.setEchoChar((char) 0);
					bMostrarContraseña.setText("Ocultar");
				}
			}
		});

        //Panel botones
        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton bRegistro = new JButton("Confirmar registro");
        pBotones.add(bRegistro);
        JButton bCancelar = new JButton("Cancelar");
        pBotones.add(bCancelar);

        //Panel iniciar sesión
        JPanel pIniciarSesion = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel lbIniciarSesion = new JLabel("¿Ya tienes una cuenta?", JLabel.LEFT);
        pIniciarSesion.add(lbIniciarSesion);
        JButton bIniciarSesion = new JButton("Iniciar sesión");
        pIniciarSesion.add(bIniciarSesion);

        //IA utilizada para que me explique como funciona el GridBadLayout
        gbc.gridy = 0;
        pCentral.add(nYa, gbc);
        gbc.gridy = 1;
        pCentral.add(pCorreo, gbc);
        gbc.gridy = 2;
        pCentral.add(pContraseña, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 2, 0);
        pCentral.add(pBotones, gbc);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        pCentral.add(pIniciarSesion, gbc); 

        contentPane.add(pCentral, BorderLayout.CENTER);
        
        //ActionListener Iniciar sesion
        bIniciarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaIniciarSesion ventana = new VentanaIniciarSesion();
				ventana.setVisible(true);
				//Cerramos esta ventana
				dispose();
			}
		});
        
        //ActionListener Cancelar
        bCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
        
        
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        VentanaCrearCuenta ventana = new VentanaCrearCuenta();
        ventana.setVisible(true);
    }
}
