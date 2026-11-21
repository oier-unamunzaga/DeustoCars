package domain;

public class Vehiculo {

    private String marca;
    private String modelo;
    private int anio;
    private double precio;
    private String estado; //En venta o Vendido
    private String tipo; //Electrico, Hibrido o Gasolina
    private String matricula;
    private int km;
    

    public Vehiculo(String marca, String modelo, int anio, double precio, String estado, String tipo, String matricula, int km) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.estado = estado;
        this.tipo = tipo;
        this.matricula = matricula;
        this.km = km;
    }

    // Constructor compatible con el código anterior de Prueba.java.
    public Vehiculo(String marca, String modelo, int anio, double precio, String estado) {
        this(marca, modelo, anio, precio, estado, null, null, 0);
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getKm() {
		return km;
	}

	public void setKm(int km) {
		this.km = km;
	}
    
    
}
