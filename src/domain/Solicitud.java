package domain;

public class Solicitud {
	
	private int id;
	private Vehiculo vehiculo;
	private Usuario comprador; //Usuario que quiere comprar el vehículo
	private String fecha;     //Ejemplo: "18/11/2026"
	private EstadoSolicitud estado;    //Pendiente, Aceptada o Rechazada
	
	public enum EstadoSolicitud {
		PENDIENTE, ACEPTADA, RECHADADA
	}

	public Solicitud() {
		super();
	}

	public Solicitud(Vehiculo vehiculo, Usuario comprador, String fecha, EstadoSolicitud estado) {
		super();
		this.vehiculo = vehiculo;
		this.comprador = comprador;
		this.fecha = fecha;
		this.estado = estado;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public void setComprador(Usuario comprador) {
		this.comprador = comprador;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public EstadoSolicitud getEstado() {
		return estado;
	}

	public void setEstado(EstadoSolicitud estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Solicitud [vehiculo=" + vehiculo + ", comprador=" + comprador + ", fecha=" + fecha + ", estado="
				+ estado + "]";
	}

}
