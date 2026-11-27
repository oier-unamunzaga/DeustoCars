package domain;

public class Usuario {
	
	    private int id;
	    private String nombre;
	    private String apellido;
	    private int edad;
	    private String username;
	    private String email;
	    private String contraseña;

	    public Usuario(String nombre, String apellido, int edad, String username, String email,
				String contraseña) {
			this.nombre = nombre;
			this.apellido = apellido;
			this.edad = edad;
			this.username = username;
			this.email = email;
			this.contraseña = contraseña;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellido() {
			return apellido;
		}

		public void setApellido(String apellido) {
			this.apellido = apellido;
		}

		public int getEdad() {
			return edad;
		}

		public void setEdad(int edad) {
			this.edad = edad;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getContraseña() {
			return contraseña;
		}

		public void setContraseña(String contraseña) {
			this.contraseña = contraseña;
		}
		
		public boolean esContrasenaValida(String contraseña) {
			return obtenerErroresContrasena(contraseña).isEmpty(); //Si la funcion no devuelve String es porque no hay errores.
		}

		public static String obtenerErroresContrasena(String contraseña) {
			String errores = "";
			String letrasMayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			String letrasMinusculas = "abcdefghijklmnopqrstuvwxyz";
			String caracteresEspeciales = "@!¡?¿-_&$/()=";
			String numeros = "1234567890";

			if (contraseña == null || contraseña.length() < 8) {
				errores += "- Tener al menos 8 caracteres\n";
			}

			if (contraseña == null || !contieneCaracter(contraseña, letrasMayusculas)) {
				errores += "- Tener una letra mayúscula\n";
			}

			if (contraseña == null || !contieneCaracter(contraseña, letrasMinusculas)) {
				errores += "- Tener una letra minúscula\n";
			}

			if (contraseña == null || !contieneCaracter(contraseña, caracteresEspeciales)) {
				errores += "- Tener un carácter especial\n";
			}

			if (contraseña == null || !contieneCaracter(contraseña, numeros)) {
				errores += "- Tener un número\n";
			}

			return errores;
		}
		
		public static boolean contieneCaracter(String contraseña, String abecedario) {
			for (int i = 0; i < abecedario.length(); i++) {
				char letra = abecedario.charAt(i);
				
				if (contraseña.indexOf(letra) >= 0) { //Si el index es mayor o igual a 0 es porque esta
					return true;
				}
			}
			return false;
		}			

	}
