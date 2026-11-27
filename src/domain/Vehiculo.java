package domain;

public class Vehiculo {
	
	private int id;
    private Marca marca;
    private String modelo;
    private int anio;
    private double precio;
    private Estado estado; //En venta o Vendido
    private Tipo tipo; //Electrico, Hibrido o Gasolina
    private String matricula;
    private int km;
    private Usuario usuario; //Dueño del coche
    
    //Marcas de coche que se pueden vender
    public enum Marca {
    	TOYOTA("Toyota", "resources/images/marcas/toyota.png"),
    	BMW("BMW", "resources/images/marcas/bmw.png"),
    	AUDI("Audi", "resources/images/marcas/audi.png"),
    	RENAULT("Renault", "resources/images/marcas/renault.png"),
    	VOLKSWAGEN("Volkswagen", "resources/images/marcas/volkswagen.png"),
    	HYUNDAI("Hyundai", "resources/images/marcas/hyundai.png"),
    	SEAT("SEAT", "resources/images/marcas/seat.png"),
    	DACIA("Dacia", "resources/images/marcas/dacia.png"),
    	KIA("Kia", "resources/images/marcas/kia.png"),
    	PEUGEOT("Peugeot", "resources/images/marcas/peugeot.png"),
    	MERCEDES_BENZ("Mercedes-Benz", "resources/images/marcas/mercedes.png"),
    	SKODA("Škoda", "resources/images/marcas/skoda.png"),
    	NISSAN("Nissan", "resources/images/marcas/nissan.png"),
    	CITROEN("Citroën", "resources/images/marcas/citroen.png"),
    	FORD("Ford", "resources/images/marcas/ford.png"),
    	CUPRA("CUPRA", "resources/images/marcas/cupra.png"),
    	OPEL("Opel", "resources/images/marcas/opel.png"),
    	BYD("BYD", "resources/images/marcas/byd.png"),
    	MAZDA("Mazda", "resources/images/marcas/mazda.png"),
    	MASERATI("Maserati", "resources/images/marcas/maserati.png"),
    	PORSCHE("Porsche", "resources/images/marcas/porsche.png"),
    	TESLA("Tesla", "resources/images/marcas/tesla.png"),
    	FERRARI("Ferrari", "resources/images/marcas/ferrari.png");

        private final String nombre;
        private final String rutaImagen;

        Marca(String nombre, String rutaImagen) {
            this.nombre = nombre;
            this.rutaImagen = rutaImagen;
        }

        public String getNombre() {
            return nombre;
        }

        public String getRutaImagen() {
            return rutaImagen;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }
    
    //Estado del vehiculo
    public enum Estado {
    	EN_VENTA, VENDIDO
    }
    
    //Tipo de combustible
    public enum Tipo {
    	ELECTRICO, GASOLINA, HIBRIDO, DIESEL
    }
    
    public Vehiculo(Marca marca, String modelo, int anio, double precio, Estado estado, Tipo tipo, String matricula, int km, Usuario usuario) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.estado = estado;
        this.tipo = tipo;
        this.matricula = matricula;
        this.km = km;
        this.usuario = usuario;
    }

    // Constructor compatible con el código anterior de Prueba.java.
    public Vehiculo(Marca marca, String modelo, int anio, double precio, Estado estado, Usuario usuario) {
        this(marca, modelo, anio, precio, estado, null, null, 0, usuario);
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
    
}
