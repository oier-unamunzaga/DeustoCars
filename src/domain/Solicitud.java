package domain;

public class Solicitud {

	private Vehiculo vehiculo;
	private String comprador; //Usuario que quiere comprar el vehículo
	private String fecha;     //Ejemplo: "18/11/2026"
	private String estado;    //Pendiente, Aceptada o Rechazada

	public Solicitud() {
		super();
	}

	public Solicitud(Vehiculo vehiculo, String comprador, String fecha, String estado) {
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

	public String getComprador() {
		return comprador;
	}

	public void setComprador(String comprador) {
		this.comprador = comprador;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Solicitud [vehiculo=" + vehiculo + ", comprador=" + comprador + ", fecha=" + fecha + ", estado="
				+ estado + "]";
	}

}
