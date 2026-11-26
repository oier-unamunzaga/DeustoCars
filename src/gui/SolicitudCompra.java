package gui;

import domain.Usuario;
import domain.Vehiculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SolicitudCompra extends JDialog {

	private static final long serialVersionUID = 1L;
	private final Vehiculo vehiculo;
    private final Usuario usuarioSesion;

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JComboBox<String> cmbFormaPago;
    private JTextArea txtObservaciones;

    public SolicitudCompra(JFrame parent, Vehiculo vehiculo, Usuario usuarioSesion) {
        super(parent, "Tramitar Solicitud de Compra", true);
        this.vehiculo = vehiculo;
        this.usuarioSesion = usuarioSesion;

        setSize(470, 440);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        // Resumen superior del vehículo
        JPanel panelVehiculo = new JPanel(new GridLayout(2, 1, 0, 4));
        panelVehiculo.setBorder(BorderFactory.createTitledBorder("INFORMACIÓN DEL VEHÍCULO"));
        panelVehiculo.add(new JLabel("Vehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo() + " (" + vehiculo.getAnio() + ")"));
        panelVehiculo.add(new JLabel(String.format("Precio: %,.2f € · Tipo: %s", vehiculo.getPrecio(), vehiculo.getTipo())));
        add(panelVehiculo, BorderLayout.NORTH);

        // Campos del formulario
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));

        txtNombre = new JTextField(usuarioSesion.getNombreCompleto());
        txtEmail = new JTextField(usuarioSesion.getEmail());
        cmbFormaPago = new JComboBox<>(new String[]{"Financiación", "Transferencia"});
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        panelForm.add(crearBloque("Nombre Completo:", txtNombre));
        panelForm.add(Box.createVerticalStrut(8));
        panelForm.add(crearBloque("Correo Electrónico:", txtEmail));
        panelForm.add(Box.createVerticalStrut(8));
        panelForm.add(crearBloque("Forma de Pago:", cmbFormaPago));
        panelForm.add(Box.createVerticalStrut(8));
        panelForm.add(crearBloque("Observaciones para el Vendedor:", new JScrollPane(txtObservaciones)));
        add(panelForm, BorderLayout.CENTER);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnEnviar = new JButton("Enviar Solicitud");

        btnCancelar.addActionListener(e -> dispose());
        btnEnviar.addActionListener(e -> validarYEnviar());

        panelBotones.add(btnCancelar);
        panelBotones.add(btnEnviar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearBloque(String etiqueta, JComponent campo) {
        JPanel p = new JPanel(new BorderLayout(4, 3));
        p.add(new JLabel(etiqueta), BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }

    private void validarYEnviar() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String formaPago = (String) cmbFormaPago.getSelectedItem();
        String observaciones = txtObservaciones.getText().trim();

        // RF-20: Validaciones de datos introducidos
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre completo es obligatorio.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Introduce un correo electrónico válido.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mañana sustituirás el interior de este método por la llamada a GestorBD
        guardarSolicitudTemporal(nombre, email, formaPago, observaciones);
    }

    private void guardarSolicitudTemporal(String nombre, String email, String formaPago, String observaciones) {
        JOptionPane.showMessageDialog(this,
                "Solicitud validada correctamente para " + nombre + " (" + formaPago + ").\n",
                "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}