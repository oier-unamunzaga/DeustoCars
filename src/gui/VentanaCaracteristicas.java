package gui;

import domain.Usuario;
import domain.Vehiculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class VentanaCaracteristicas extends JFrame {

	private static final long serialVersionUID = 1L;
	protected final Vehiculo vehiculo;
    protected final Map<String, Object> detallesExtra;
    protected final Usuario usuarioSesion;
    protected final JFrame ventanaCatalogo;
    protected JPanel panelCentral;

    public VentanaCaracteristicas(Vehiculo vehiculo, Map<String, Object> detallesExtra,
                                  Usuario usuarioSesion, JFrame ventanaCatalogo) {
        this.vehiculo = vehiculo;
        this.detallesExtra = (detallesExtra != null) ? detallesExtra : new HashMap<>();
        this.usuarioSesion = usuarioSesion;
        this.ventanaCatalogo = ventanaCatalogo;

        setTitle("DeustoCars - " + vehiculo.getMarca() + " " + vehiculo.getModelo());
        setSize(1060, 730);
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

        panelCentral.add(new JPanel());

        panelCentral.add(crearPanelInformacion());
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

    private JPanel crearCampoSoloLectura(String titulo, String valor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        JTextField txt = new JTextField(valor);
        txt.setEditable(false);
        txt.setBackground(Color.WHITE);
        panel.add(txt, BorderLayout.CENTER);
        return panel;
    }
}
