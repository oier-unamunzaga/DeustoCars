package domain;

public class Usuario {
	    private int id;
	    private String nombreCompleto;
	    private String username;
	    private String email;

	    public Usuario(int id, String nombreCompleto, String username, String email) {
	        this.id = id;
	        this.nombreCompleto = nombreCompleto;
	        this.username = username;
	        this.email = email;
	    }

	    public int getId() { 
	    	return id; 
	    }
	    public String getNombreCompleto() { 
	    	return nombreCompleto; 
	    }
	    public String getUsername() { 
	    	return username; 
	    }
	    public String getEmail() { 
	    	return email; 
	    }
	}
