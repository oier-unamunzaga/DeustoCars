package gui.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import domain.Vehiculo.Marca;

public class CarruselMarcasVehiculosDisponibles extends JDialog {

	private static final long serialVersionUID = 1L;

	private int indiceMarcaActual;
	private volatile boolean parado = true;
	private Thread hiloCarrusel;

	private final JLabel lbMarca;
	private final JButton bIniciar;
	private final JButton bParar;

	public CarruselMarcasVehiculosDisponibles() {
		setTitle("Marcas disponibles");
		setSize(500, 650);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		lbMarca = new JLabel();
		lbMarca.setHorizontalAlignment(SwingConstants.CENTER);
		lbMarca.setFont(new Font("Arial", Font.BOLD, 28));
		add(lbMarca, BorderLayout.CENTER);

		JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		bIniciar = new JButton("Iniciar");
		bParar = new JButton("Parar");
		bParar.setEnabled(false);
		pBotones.add(bIniciar);
		pBotones.add(bParar);
		add(pBotones, BorderLayout.SOUTH);

		bIniciar.addActionListener(e -> {
			parado = false;
			if (hiloCarrusel == null || !hiloCarrusel.isAlive()) {
				iniciarCarrusel();
			}
			bIniciar.setEnabled(false);
			bParar.setEnabled(true);
		});

		bParar.addActionListener(e -> {
			paraCarrusel();
		});

		setVisible(true);
	}

	// Hilo que recorre todas las marcas definidas en Vehiculo.Marca.
	private void iniciarCarrusel() {
		hiloCarrusel = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				synchronized (this) {
					while (parado) {
						try {
							wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return;
						}
					}
				}

				Marca marca = Marca.values()[indiceMarcaActual];
				ImageIcon imagenOriginal = new ImageIcon(marca.getRutaImagen());
				Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(
						300, 300, Image.SCALE_SMOOTH);

				SwingUtilities.invokeLater(() -> {
				    lbMarca.setText(marca.getNombre());
				    lbMarca.setIcon(new ImageIcon(imagenEscalada));
				    lbMarca.setHorizontalTextPosition(SwingConstants.CENTER);
				    lbMarca.setVerticalTextPosition(SwingConstants.BOTTOM);
				    lbMarca.setFont(new Font("Arial", Font.BOLD, 28));
				});

				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}

				indiceMarcaActual++;
				if (indiceMarcaActual >= Marca.values().length) {
					indiceMarcaActual = 0;
				}
			}
		});
		hiloCarrusel.start();
	}

	private void paraCarrusel() {
		synchronized (this) {
			if (bParar.getText().equals("Parar")) {
				bParar.setText("Reanudar");
				parado = true;
			} else {
				bParar.setText("Parar");
				parado = false;
				notifyAll();
			}
		}
	}
}
