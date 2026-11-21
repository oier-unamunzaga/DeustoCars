package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaIniciarSesion extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static final JPanel contentPane = new JPanel(new BorderLayout(0, 4));
	
	public VentanaIniciarSesion() {
		//Configuración de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(540, 330);
        setResizable(false);
        setTitle("Iniciar Sesion");
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
        
        //Panel correo
        JPanel pCorreo = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel lbCorreo = new JLabel("Correo: ", JLabel.LEFT);
        pCorreo.add(lbCorreo);
        JTextField tfCorreo = new JTextField();
        pCorreo.add(tfCorreo);
        
        //Panel contraseña
        JPanel pContraseña = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel lbContraseña = new JLabel("Contraseña: ", JLabel.LEFT);
        pContraseña.add(lbContraseña);
        JTextField tfContraseña = new JTextField();
        pContraseña.add(tfContraseña);
        
        //Panel botones
        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton bRegistro = new JButton("Iniciar sesión");
        pBotones.add(bRegistro);
        JButton bIniciarSesion = new JButton("Cancelar");
        pBotones.add(bIniciarSesion);
        
        //Panel crear cuenta
        JPanel pCrearCuenta = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel lbCrearCuenta = new JLabel("Si no tienes cuenta créala: ", JLabel.LEFT);
        pCrearCuenta.add(lbCrearCuenta);
        JButton bCrearCuenta = new JButton("Crear cuenta");
        pCrearCuenta.add(bCrearCuenta);
        
        //IA utilizada para que me explique como funciona el GridBadLayout
        gbc.gridy = 0;
        pCentral.add(pCorreo, gbc);
        gbc.gridy = 1;
        pCentral.add(pContraseña, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 2, 0);
        pCentral.add(pBotones, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        pCentral.add(pCrearCuenta, gbc);
        contentPane.add(pCentral, BorderLayout.CENTER);
        
      //ActionListener Iniciar sesion
        bCrearCuenta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaCrearCuenta ventana = new VentanaCrearCuenta();
				ventana.setVisible(true);
				//Cerramos esta ventana
				dispose();
			}
		});
        
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
        VentanaIniciarSesion ventana = new VentanaIniciarSesion();
        ventana.setVisible(true);
	}
}