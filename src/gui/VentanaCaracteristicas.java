package gui;

import domain.Usuario;
import domain.Vehiculo;
import domain.Vehiculo.Marca;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentanaCaracteristicas extends JFrame {

	private static final long serialVersionUID = 1L;
	protected final Vehiculo vehiculo;
    protected final Map<String, Object> detallesExtra;
    protected final Usuario usuarioSesion;
    protected final JFrame ventanaCatalogo;

    protected JPanel panelCentral;
    private JLabel lblImagenPrincipal;
    private JLabel[] lblMiniaturas;
    private JButton btnFavoritos;
    private JButton btnSolicitarCompra;
    private JPanel panelListaSimilares;

    public VentanaCaracteristicas(Vehiculo vehiculo, Map<String, Object> detallesExtra,
                                  Usuario usuarioSesion, JFrame ventanaCatalogo) {
        this.vehiculo = vehiculo;
        this.detallesExtra = (detallesExtra != null) ? detallesExtra : new HashMap<>();
        this.usuarioSesion = usuarioSesion;
        this.ventanaCatalogo = ventanaCatalogo;

        setTitle("DeustoCars - " + vehiculo.getMarca() + " " + vehiculo.getModelo());
        setSize(1060, 730);
        setMinimumSize(new Dimension(920, 640));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 15, 15, 15));

        JPanel panelNavegacion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("← Volver al catálogo");
        btnVolver.addActionListener(e -> {
            if (ventanaCatalogo != null) {
                ventanaCatalogo.setVisible(true);
            }
            dispose();
        });
        panelNavegacion.add(btnVolver);
        add(panelNavegacion, BorderLayout.NORTH);

        panelCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        add(panelCentral, BorderLayout.CENTER);

        panelCentral.add(crearPanelIzquierdo());

        panelCentral.add(crearPanelInformacion());

        add(crearPanelSimilares(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));

        JPanel panelGaleria = new JPanel(new BorderLayout(5, 5));
        panelGaleria.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagenPrincipal = new JLabel("Cargando imagen...", SwingConstants.CENTER);
        lblImagenPrincipal.setPreferredSize(new Dimension(450, 260));
        panelGaleria.add(lblImagenPrincipal, BorderLayout.CENTER);

        JPanel panelMiniaturas = new JPanel(new GridLayout(1, 5, 5, 5));
        lblMiniaturas = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            final int idx = i;
            lblMiniaturas[i] = new JLabel("Foto " + (i + 1), SwingConstants.CENTER);
            lblMiniaturas[i].setPreferredSize(new Dimension(80, 55));
            lblMiniaturas[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            lblMiniaturas[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lblMiniaturas[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mostrarFotoGrande(idx);
                }
            });
            panelMiniaturas.add(lblMiniaturas[i]);
        }
        panelGaleria.add(panelMiniaturas, BorderLayout.SOUTH);
        panelIzquierdo.add(panelGaleria);
        panelIzquierdo.add(Box.createVerticalStrut(10));

        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String estadoActual = (vehiculo.getEstado() != null) ? vehiculo.getEstado() : "En venta";
        JLabel lblEstado = new JLabel("Estado del vehículo: " + estadoActual);
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 14));
        boolean enVenta = "En venta".equalsIgnoreCase(estadoActual);
        lblEstado.setForeground(enVenta ? new Color(0, 128, 0) : Color.RED);
        panelEstado.add(lblEstado);
        panelIzquierdo.add(panelEstado);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnFavoritos = new JButton("Añadir a favoritos");
        btnSolicitarCompra = new JButton("Solicitud de compra");
        btnSolicitarCompra.setEnabled(enVenta); 

        if (!enVenta) {
            btnSolicitarCompra.setToolTipText("Este vehículo ya figura como Vendido.");
        }

        btnFavoritos.addActionListener(e -> {
            if (usuarioSesion == null) {
                JOptionPane.showMessageDialog(this, "Debes iniciar sesión para añadir favoritos.", "Sesión requerida", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Vehículo marcado para favoritos (se guardará en SQLite en el Día 6).");
            }
        });

        btnSolicitarCompra.addActionListener(e -> {
            if (usuarioSesion == null) {
                JOptionPane.showMessageDialog(this, "Debes iniciar sesión para solicitar la compra.", "Sesión requerida", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Aquí se abrirá DialogoSolicitudCompra (Día 5).");
            }
        });

        panelAcciones.add(btnFavoritos);
        panelAcciones.add(btnSolicitarCompra);
        panelIzquierdo.add(panelAcciones);

        cargarMiniaturasAsync();
        return panelIzquierdo;
    }

    @SuppressWarnings("unchecked")
    private void cargarMiniaturasAsync() {
        List<String> rutas = (List<String>) detallesExtra.getOrDefault("imagenes", Collections.emptyList());
        new SwingWorker<ImageIcon[], Void>() {
            @Override
            protected ImageIcon[] doInBackground() {
                ImageIcon[] iconos = new ImageIcon[5];
                for (int i = 0; i < 5 && i < rutas.size(); i++) {
                    if (new File(rutas.get(i)).exists()) {
                        Image img = new ImageIcon(rutas.get(i)).getImage().getScaledInstance(80, 55, Image.SCALE_SMOOTH);
                        iconos[i] = new ImageIcon(img);
                    }
                }
                return iconos;
            }

            @Override
            protected void done() {
                try {
                    ImageIcon[] iconos = get();
                    for (int i = 0; i < iconos.length; i++) {
                        if (iconos[i] != null) {
                            lblMiniaturas[i].setText("");
                            lblMiniaturas[i].setIcon(iconos[i]);
                        }
                    }
                    mostrarFotoGrande(0);
                } catch (Exception e) {
                    lblImagenPrincipal.setText(vehiculo.getMarca() + " " + vehiculo.getModelo());
                }
            }
        }.execute();
    }

    @SuppressWarnings("unchecked")
    private void mostrarFotoGrande(int indice) {
        List<String> rutas = (List<String>) detallesExtra.getOrDefault("imagenes", Collections.emptyList());
        if (indice < rutas.size() && new File(rutas.get(indice)).exists()) {
            Image img = new ImageIcon(rutas.get(indice)).getImage().getScaledInstance(440, 250, Image.SCALE_SMOOTH);
            lblImagenPrincipal.setText("");
            lblImagenPrincipal.setIcon(new ImageIcon(img));
        } else {
            lblImagenPrincipal.setIcon(null);
            lblImagenPrincipal.setText(vehiculo.getMarca() + " " + vehiculo.getModelo() + " · Vista " + (indice + 1));
        }
    }

    private JPanel crearPanelInformacion() {
        JPanel panelInformacion = new JPanel(new GridLayout(5, 1, 5, 8));

        JPanel panelMarcaModelo = new JPanel(new GridLayout(1, 2, 10, 0));
        panelMarcaModelo.add(crearCampoSoloLectura("Marca", vehiculo.getMarca()));
        panelMarcaModelo.add(crearCampoSoloLectura("Modelo", vehiculo.getModelo()));
        panelInformacion.add(panelMarcaModelo);

        JPanel panelPrecio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPrecio.setBorder(BorderFactory.createTitledBorder("Precio al contado / financiado"));
        JLabel lblPrecio = new JLabel(String.format("%,.2f €", vehiculo.getPrecio()));
        lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPrecio.setForeground(new Color(0, 102, 51));
        panelPrecio.add(lblPrecio);
        panelInformacion.add(panelPrecio);

        JPanel panelDatosTecnicos = new JPanel(new GridLayout(2, 2, 8, 5));
        int km = (int) detallesExtra.getOrDefault("kilometraje", 0);
        String combustible = (String) detallesExtra.getOrDefault("combustible", "No especificado");
        String ubicacion = (String) detallesExtra.getOrDefault("ubicacion", "Bilbao, Bizkaia");

        panelDatosTecnicos.add(new JLabel(String.format("Kilómetros: %,d km", km)));
        panelDatosTecnicos.add(new JLabel("Combustible: " + combustible));
        panelDatosTecnicos.add(new JLabel("Ubicación: " + ubicacion));
        panelDatosTecnicos.add(new JLabel("Año: " + vehiculo.getAnio()));
        panelInformacion.add(panelDatosTecnicos);

        JPanel panelDescripcion = new JPanel(new BorderLayout(5, 5));
        panelDescripcion.add(new JLabel("Descripción y equipamiento adicional:"), BorderLayout.NORTH);
        JTextArea txtDescripcion = new JTextArea(
                (String) detallesExtra.getOrDefault("descripcion", "Sin descripción adicional.")
        );
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        panelDescripcion.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);
        panelInformacion.add(panelDescripcion);

        JPanel panelGarantia = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelGarantia.add(new JLabel("Garantía DeustoCars incluida · Revisión certificada"));
        panelInformacion.add(panelGarantia);

        return panelInformacion;
    }

    private Component crearCampoSoloLectura(String titulo, Marca marca) {
		// TODO Auto-generated method stub
		return null;
	}

	private JPanel crearCampoSoloLectura(String titulo, String valor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        JTextField txt = new JTextField(valor);
        txt.setEditable(false);
        txt.setBackground(Color.WHITE);
        panel.add(txt, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelSimilares() {
        JPanel panelSeccionSimilares = new JPanel(new BorderLayout(5, 5));
        panelSeccionSimilares.setBorder(new EmptyBorder(10, 0, 0, 0));
        JLabel lblTitulo = new JLabel("Vehículos Similares:");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelSeccionSimilares.add(lblTitulo, BorderLayout.NORTH);

        panelListaSimilares = new JPanel(new GridLayout(1, 4, 10, 0));
        panelListaSimilares.setPreferredSize(new Dimension(0, 115));
        panelSeccionSimilares.add(panelListaSimilares, BorderLayout.CENTER);

        pintarTarjetasSimilares(Collections.emptyList());
        return panelSeccionSimilares;
    }

    public void pintarTarjetasSimilares(List<Vehiculo> similares) {
        panelListaSimilares.removeAll();
        for (int i = 0; i < 4; i++) {
            JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
            tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            if (similares != null && i < similares.size()) {
                Vehiculo sim = similares.get(i);
                JLabel lblNombre = new JLabel(sim.getMarca() + " " + sim.getModelo(), SwingConstants.CENTER);
                lblNombre.setFont(new Font("SansSerif", Font.BOLD, 12));

                tarjeta.add(lblNombre, BorderLayout.NORTH);
                tarjeta.add(new JLabel("[Foto vehículo]", SwingConstants.CENTER), BorderLayout.CENTER);
                tarjeta.add(new JLabel(sim.getAnio() + " · " + String.format("%,.0f €", sim.getPrecio()), SwingConstants.CENTER), BorderLayout.SOUTH);
                tarjeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                tarjeta.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        new VentanaCaracteristicas(sim, null, usuarioSesion, ventanaCatalogo).setVisible(true);
                        dispose();
                    }
                });
            } else {
                tarjeta.add(new JLabel("Sin sugerencia", SwingConstants.CENTER), BorderLayout.CENTER);
            }
            panelListaSimilares.add(tarjeta);
        }
        panelListaSimilares.revalidate();
        panelListaSimilares.repaint();
    }
}