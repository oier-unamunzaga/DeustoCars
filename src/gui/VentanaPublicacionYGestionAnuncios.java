package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import domain.Vehiculo;
import domain.Vehiculo.Estado;
import domain.Vehiculo.Marca;
import domain.Vehiculo.Tipo;
import gui.tablas.AccionesEditorTabla;
import gui.tablas.ModeloTablaAnuncios;

//Las dimensiones para que quede todo mas o menos homogeneo me las ha dado la IA
public class VentanaPublicacionYGestionAnuncios extends JFrame {

    private static final long serialVersionUID = 1L;

    private final List<Vehiculo> coches = new ArrayList<>();
    private final ModeloTablaAnuncios modeloTabla;
    private final JTable tablaAnuncios;
    private final JPanel pDetalles = new JPanel(new BorderLayout(8, 8));

    private static final JPanel contentPane = new JPanel(new BorderLayout(0, 3));

    private Vehiculo cocheSeleccionado;
    private JTextField tfMarca;
    private JTextField tfModelo;
    private JTextField tfAnio;
    private JTextField tfPrecio;
    private JTextField tfEstado;
    private JTextField tfTipo;
    private JTextField tfMatricula;
    private JTextField tfKm;

    public VentanaPublicacionYGestionAnuncios() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setResizable(false);
        setTitle("Mis anuncios");
        setContentPane(contentPane);

        try {
            setIconImage(ImageIO.read(new File("resources/images/iconoApp.jpg")));
        } catch (IOException e) {
            System.out.println("Error al cargar el logo.");
            e.printStackTrace();
        }

        JLabel lbTitulo = new JLabel("Mis anuncios", JLabel.LEFT);
        contentPane.add(lbTitulo, BorderLayout.NORTH);

        //Creo el modelo de la tabla
        modeloTabla = new ModeloTablaAnuncios();
        //Creo la tabla y le asigno el modelo
        tablaAnuncios = new JTable(modeloTabla);
        tablaAnuncios.setRowHeight(42);
        tablaAnuncios.getColumnModel().getColumn(4).setPreferredWidth(300);
        tablaAnuncios.getColumnModel().getColumn(4).setMinWidth(280);
        tablaAnuncios.getColumnModel().getColumn(4).setMaxWidth(320);
        //El modo de selección me lo ha dicho la IA
        tablaAnuncios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaAnuncios.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Vehiculo coche && column == 4) {
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 2));
                    panel.setOpaque(true);
                    panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                    panel.add(new JButton("Eliminar vehículo"));
                    if (Estado.EN_VENTA.equals(coche.getEstado())) {
                        panel.add(new JButton("Editar anuncio"));
                    }
                    return panel;
                }

                JLabel etiqueta = new JLabel(String.valueOf(value));
                etiqueta.setOpaque(true);
                etiqueta.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                return etiqueta;
            }
        });

        //Tengo que crar una clase aparte porque si no tengo que sobreescribir muchos metodos para el TableCellEditor
        tablaAnuncios.getColumnModel().getColumn(4).setCellEditor(new AccionesEditorTabla(this));

        JScrollPane scrollTabla = new JScrollPane(tablaAnuncios);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Publicaciones"));
        pDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del vehículo"));

        //Creo un separador por si quiero que la tabla de vehiculos o el formulario sea mas grande
        //Y lo pongo mas o menos a la mitad de la pantalla
        JSplitPane separador = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, pDetalles);
        separador.setResizeWeight(1);
        separador.setDividerLocation(300);
        contentPane.add(separador, BorderLayout.CENTER);

        JPanel pNuevoVehiculo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
        JButton bAnadirVehiculo = new JButton("Añadir nuevo vehículo");
        bAnadirVehiculo.addActionListener(e -> {
            //Desmarco por si habia seleccionado algun vehiculo
            tablaAnuncios.clearSelection();
            mostrarFormularioNuevoVehiculo();
        });
        pNuevoVehiculo.add(bAnadirVehiculo);
        contentPane.add(pNuevoVehiculo, BorderLayout.SOUTH);

        tablaAnuncios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaVista = tablaAnuncios.getSelectedRow();
                if (filaVista >= 0) {
                    int filaModelo = tablaAnuncios.convertRowIndexToModel(filaVista);
                    mostrarDetalles(coches.get(filaModelo), false);
                }
            }
        });

        setLocationRelativeTo(null);
        cargarDatosDePrueba();
    }

    //Datos de prueba
    private void cargarDatosDePrueba() {
        coches.add(new Vehiculo(Marca.TOYOTA, "Corolla", 2021, 18500, Estado.EN_VENTA, Tipo.HIBRIDO, "1234ABC", 45000));
        coches.add(new Vehiculo(Marca.SEAT, "Ibiza", 2019, 12900, Estado.VENDIDO, Tipo.GASOLINA, "5678DEF", 80000));
        actualizarTabla();
    }

    private void actualizarTabla() {
        modeloTabla.limpiar();
        for (Vehiculo coche : coches) {
            modeloTabla.anadirFila(coche);
        }
    }

    //Metodo para rellenar el panel de detalles
    public void mostrarDetalles(Vehiculo coche, boolean editable) {
        cocheSeleccionado = coche;
        pDetalles.removeAll();

        JPanel pCampos = new JPanel(new GridLayout(8, 2, 8, 8));
        //Solo son editables el Precio y el Estado
        tfMarca = crearCampo(pCampos, "Marca:", String.valueOf(coche.getMarca()), false);
        tfModelo = crearCampo(pCampos, "Modelo:", coche.getModelo(), false);
        tfAnio = crearCampo(pCampos, "Año:", String.valueOf(coche.getAnio()), false);
        tfPrecio = crearCampo(pCampos, "Precio (€):", String.valueOf(coche.getPrecio()), editable);
        tfEstado = crearCampo(pCampos, "Estado:", String.valueOf(coche.getEstado()), editable);
        tfTipo = crearCampo(pCampos, "Tipo:", String.valueOf(coche.getTipo()), false);
        tfMatricula = crearCampo(pCampos, "Matrícula:", coche.getMatricula(), false);
        tfKm = crearCampo(pCampos, "Kilómetros:", String.valueOf(coche.getKm()), false);
        pDetalles.add(pCampos, BorderLayout.CENTER);

        JPanel pAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        if (editable) {
            JButton bGuardar = new JButton("Guardar cambios");
            JButton bCancelar = new JButton("Cancelar edición");
            bGuardar.addActionListener(e -> guardarCambios());
            bCancelar.addActionListener(e -> mostrarDetalles(cocheSeleccionado, false));
            pAcciones.add(bGuardar);
            pAcciones.add(bCancelar);
        } else {
            pAcciones.add(new JLabel("Pulsa ‘Editar anuncio’ para modificar los datos."));
        }

        pDetalles.add(pAcciones, BorderLayout.SOUTH);
        pDetalles.revalidate();
        pDetalles.repaint();
    }

    //Metodo para añadir un nuevo vehiculo
    private void mostrarFormularioNuevoVehiculo() {
        cocheSeleccionado = null;
        pDetalles.removeAll();

        JPanel pCampos = new JPanel(new GridLayout(8, 2, 8, 8));
        tfMarca = crearCampo(pCampos, "Marca:", "", true);
        tfModelo = crearCampo(pCampos, "Modelo:", "", true);
        tfAnio = crearCampo(pCampos, "Año:", "", true);
        tfPrecio = crearCampo(pCampos, "Precio (€):", "", true);
        tfEstado = crearCampo(pCampos, "Estado:", Estado.EN_VENTA.name(), true);
        tfTipo = crearCampo(pCampos, "Tipo:", "", true);
        tfMatricula = crearCampo(pCampos, "Matrícula:", "", true);
        tfKm = crearCampo(pCampos, "Kilómetros:", "", true);
        pDetalles.add(pCampos, BorderLayout.CENTER);

        JPanel pAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        JButton bGuardar = new JButton("Guardar vehículo");
        JButton bCancelar = new JButton("Cancelar");
        bGuardar.addActionListener(e -> guardarNuevoVehiculo());
        bCancelar.addActionListener(e -> {
            pDetalles.removeAll();
            pDetalles.revalidate();
            pDetalles.repaint();
        });
        pAcciones.add(bGuardar);
        pAcciones.add(bCancelar);
        pDetalles.add(pAcciones, BorderLayout.SOUTH);
        pDetalles.revalidate();
        pDetalles.repaint();
    }

    //Metodo auxiliar para los detalles
    private JTextField crearCampo(JPanel panel, String texto, String valor, boolean editable) {
        panel.add(new JLabel(texto));
        JTextField campo = new JTextField(valor);
        if (editable) {
            campo.setBackground(Color.WHITE);
        } else {
            campo.setBackground(Color.LIGHT_GRAY);
        }
        campo.setEditable(editable);
        panel.add(campo);
        return campo;
    }

    //Solo se pueden modificar el Precio y el Estado
    private void guardarCambios() {
        try {
            double precio = Double.parseDouble(tfPrecio.getText().trim());
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El Precio debe ser mayor de 0.",
                        "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            cocheSeleccionado.setPrecio(precio);
            cocheSeleccionado.setEstado(Estado.valueOf(tfEstado.getText().trim().toUpperCase().replace(" ", "_")));
            actualizarTabla();
            mostrarDetalles(cocheSeleccionado, false);
        } catch (IllegalArgumentException excepcion) {
            JOptionPane.showMessageDialog(this,
                    "El Precio debe ser numérico y el Estado debe ser válido (ej. EN_VENTA, VENDIDO).",
                    "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Metodo para guardar los datos del nuevo vehiculo
    private void guardarNuevoVehiculo() {
        try {
            double precio = Double.parseDouble(tfPrecio.getText().trim());
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El Precio debe ser mayor de 0.",
                        "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Vehiculo nuevo = new Vehiculo(
                    Marca.valueOf(tfMarca.getText().trim().toUpperCase()),
                    tfModelo.getText().trim(),
                    Integer.parseInt(tfAnio.getText().trim()),
                    precio,
                    Estado.valueOf(tfEstado.getText().trim().toUpperCase().replace(" ", "_")),
                    Tipo.valueOf(tfTipo.getText().trim().toUpperCase()),
                    tfMatricula.getText().trim(),
                    Integer.parseInt(tfKm.getText().trim()));
            coches.add(nuevo);
            actualizarTabla();
            mostrarDetalles(nuevo, false);
        } catch (IllegalArgumentException excepcion) {
            JOptionPane.showMessageDialog(this,
                    "Revisa los datos: los campos numéricos y los valores de Marca, Estado o Tipo deben ser válidos.",
                    "Datos incorrectos", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Metodo que se usa en el TableCellEditor
    public void eliminarVehiculo(Vehiculo coche) {
        coches.remove(coche);
        actualizarTabla();
        pDetalles.removeAll();
        pDetalles.revalidate();
        pDetalles.repaint();
    }

    public static void main(String[] args) {
        VentanaPublicacionYGestionAnuncios ventana = new VentanaPublicacionYGestionAnuncios();
        ventana.setVisible(true);
    }
}