package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Usuario;
import domain.Vehiculo;
import domain.Vehiculo.Estado;
import domain.Vehiculo.Marca;
import domain.Vehiculo.Tipo;

public class GestorDB {

	//Base de datos SQLite que se guardará dentro de resources/db.
	private static final String BBDD = "resources/db/deustocars.db";
	protected static final String CONNECTION_STRING = "jdbc:sqlite:" + BBDD;

	//METODOS PRINCIPALES DE LA  BBDD
	public void crearBBDD() {
		String sqlUsuario = "CREATE TABLE IF NOT EXISTS USUARIO ("
				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "NOMBRE TEXT NOT NULL, "
				+ "APELLIDO TEXT NOT NULL, "
				+ "EDAD INTEGER NOT NULL, "
				+ "USERNAME TEXT NOT NULL UNIQUE, "
				+ "EMAIL TEXT NOT NULL, "
				+ "CONTRASEÑA TEXT NOT NULL)";
		
		String sqlVehiculo = "CREATE TABLE IF NOT EXISTS VEHICULO ("
				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "MARCA TEXT NOT NULL, "
				+ "MODELO TEXT NOT NULL, "
				+ "ANIO INTEGER NOT NULL, "
				+ "PRECIO DOUBLE NOT NULL, "
				+ "ESTADO TEXT NOT NULL, "
				+ "TIPO TEXT NOT NULL, "
				+ "MATRICULA TEXT UNIQUE, "
				+ "KM INTEGER, "
				+ "ID_USUARIO INTEGER NOT NULL, "
				+ "FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID))";
		
		String sqlSolicitud = "CREATE TABLE IF NOT EXISTS SOLICITUD ("
				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "ID_VEHICULO INTEGER NOT NULL, "
				+ "ID_USUARIO INTEGER NOT NULL, "
				+ "FECHA TEXT NOT NULL, "
				+ "ESTADO TEXT NOT NULL, "
				+ "FOREIGN KEY (ID_VEHICULO) REFERENCES VEHICULO(ID), "
				+ "FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID))";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmtUsuario = con.prepareStatement(sqlUsuario);
				PreparedStatement pstmtVehiculo = con.prepareStatement(sqlVehiculo);
				PreparedStatement pstmtSolicitud = con.prepareStatement(sqlSolicitud)) {
			pstmtUsuario.executeUpdate();
			pstmtVehiculo.executeUpdate();
			pstmtSolicitud.executeUpdate();
			System.out.println("[DB] Se ha creado correctamente la tabla USUARIO");
			System.out.println("[DB] Se ha creado correctamente la tabla VEHICULO");
			System.out.println("[DB] Se ha creado correctamente la tabla SOLICITUD");
			System.out.println("[DB] Se ha creado correctamente la BBDD");
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.format("[DB] Error al crear las tablas: %s\n", e.getMessage());
		}
		
	}
	
	public void borrarBBDD() {
		String sqlUsuario = "DROP TABLE IF EXISTS USUARIO";
		String sqlVehiculo = "DROP TABLE IF EXISTS VEHICULO";
		String sqlSolicitud = "DROP TABLE IF EXISTS SOLICITUD";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmtUsuario = con.prepareStatement(sqlUsuario);
				PreparedStatement pstmtVehiculo = con.prepareStatement(sqlVehiculo);
				PreparedStatement pstmtSolicitud = con.prepareStatement(sqlSolicitud)) {
			
			pstmtUsuario.executeUpdate();
			System.out.println("[DB] Se ha borrado correctamente la tabla USUARIO");
			pstmtVehiculo.executeUpdate();
			System.out.println("[DB] Se ha borrado correctamente la tabla VEHICULO");
			pstmtSolicitud.executeUpdate();
			System.out.println("[DB] Se ha borrado correctamente la tabla SOLICITUD");
			
			System.out.println("[DB] Se ha borrado correctamente la BBDD");
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.format("[DB] Error al borrar las tablas: %s\n", e.getMessage());
		}
		
	}
	
	//METODOS DE LOS VEHICULOS
		//Al crear un anuncio
	public void guardarVehiculo(Vehiculo v) {
		String sql = "INSERT INTO VEHICULO (MARCA, MODELO, ANIO, PRECIO, ESTADO, TIPO, MATRICULA, KM, ID_USUARIO) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, v.getMarca().name());
			pstmt.setString(2, v.getModelo());
			pstmt.setInt(3, v.getAnio());
			pstmt.setDouble(4, v.getPrecio());
			pstmt.setString(5, v.getEstado().name());
			pstmt.setString(6, v.getTipo().name());
			pstmt.setString(7, v.getMatricula());
			pstmt.setInt(8, v.getKm());
			pstmt.setInt(9, v.getUsuario().getId());
			
			if (pstmt.executeUpdate() != 1) {
				System.err.println("[VEHICULO] No se ha insertado correctamente el vehiculo");
			} else {
				//Le añadimos el valor del ID al vehiculo una vez añadido a la BBDD
				//ya que al ser autoincremental se tiene que crear desde la BBDD
				//para eso tenemos que hacer un SELECT del ultimo ID generado por la BBDD
				String sqlID = "SELECT last_insert_rowid()";
				try (PreparedStatement pstmtID = con.prepareStatement(sqlID)) {
					ResultSet resultado = pstmtID.executeQuery();
					if (resultado.next()) {
						int idGenerado = resultado.getInt(1);
						v.setId(idGenerado);
						System.out.println("[VEHICULO] ID añadido correctamente al objeto vehiculo");
						System.out.format("[VEHICULO] Se ha insertado correctamente el vehiculo con ID: %d\n", v.getId());
					}
				} catch (SQLException e) {
					// TODO: handle exception
					System.err.format("[VEHICULO] Error al insertar el ID: %s\n", e.getMessage());
				}
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.format("[VEHICULO] Error al insertar el vehiculo: %s\n", e.getMessage());
		}
	}
		//Al crear un anuncio
	public boolean existeMatricula(String matricula) {
		String sql = "SELECT COUNT(*) FROM VEHICULO WHERE MATRICULA = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, matricula);
			
			ResultSet rs = pstmt.executeQuery();
			
			return rs.next() && rs.getInt(1) > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
		//Al vender un vehiculo o al editar algun dato del anuncio
	public void actualizarVehiculo(Vehiculo v) {
		String sql = "UPDATE VEHICULO SET PRECIO = ?, ESTADO = ?, ID_USUARIO = ? WHERE ID = ?";

	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	    	//Actualizamos los valores que pueden cambiar
	        pstmt.setDouble(1, v.getPrecio());
	        pstmt.setString(2, v.getEstado().name());
	        pstmt.setInt(3, v.getUsuario().getId());
	        //Pasandole el ID correspondiente
	        pstmt.setInt(4, v.getId());

	        pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	//METODOS DE LOS USUARIOS
		//Al crear cuenta
	public void guardarUsuario(Usuario u) {
		String sql = "INSERT INTO USUARIO (NOMBRE, APELLIDO, EDAD, USERNAME, EMAIL, CONTRASEÑA) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, u.getNombre());
			pstmt.setString(2, u.getApellido());
			pstmt.setInt(3, u.getEdad());
			pstmt.setString(4, u.getUsername());
			pstmt.setString(5, u.getEmail());
			pstmt.setString(6, u.getContraseña());
			
			if (pstmt.executeUpdate() != 1) {
				System.err.println("[USUARIO] No se ha insertado correctamente el usuario");
			} else {
				String sqlID = "SELECT last_insert_rowid()";
				try (PreparedStatement pstmtID = con.prepareStatement(sqlID)) {
					ResultSet rs = pstmtID.executeQuery();
					if (rs.next()) {
						int idGenerado = rs.getInt(1);
						u.setId(idGenerado);
						System.out.println("[USUARIO] ID añadido correctamente al objeto usuario");
						System.out.format("[USUARIO] Se ha insertado correctamente el usuario con ID: %d\n", u.getId());
					}
				} catch (SQLException e) {
					// TODO: handle exception
					System.err.format("[USUARIO] Error al insertar el ID: %s\n", e.getMessage());
				}
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.format("[USUARIO] Error al insertar el usuario: %s\n", e.getMessage());
		}
	}
		//Al crear cuenta
	public boolean existeUsuario(String username) {
		String sql = "SELECT COUNT(*) FROM USUARIO WHERE USERNAME = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();
			
			return rs.next() && rs.getInt(1) > 0; //Si hay otro devuelve true si no lo hay false
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
		//Al iniciar sesion
	public boolean comprobarCredenciales(String usuario, String contraseña) {
	    String sql = "SELECT ID FROM USUARIO "
	               + "WHERE USERNAME = ? AND CONTRASEÑA = ?";

	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setString(1, usuario);
	        pstmt.setString(2, contraseña);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            return rs.next(); //Si existe usuario devuelve true si no false
	        }

	    } catch (SQLException e) {
	        System.err.println("[USUARIO] Error al comprobar credenciales: " + e.getMessage());
	        return false;
	    }
	}
	
	//METODOS DE LAS SOLICITUDES
	
	
	
	public static void main(String[] args) {
		//Al conectar por primera vez, SQLite crea el archivo de la base de datos.
		GestorDB gestorDB = new GestorDB();
		gestorDB.crearBBDD();
		Usuario u1 = new Usuario("Oier", "Unamunzaga", 20, "Oier.Unamunzaga", "oier.unamunzaga@gmail.com", "Oier2006");
		gestorDB.guardarUsuario(u1);
		Usuario u2 = new Usuario("Adrian", "Camara", 20, "Adrian.Camara", "Adrian.Camara@gmail.com", "Adrian");
		gestorDB.guardarUsuario(u2);
		Vehiculo v1 = new Vehiculo(Marca.BMW, "X6", 2022, 65000, Estado.EN_VENTA, Tipo.HIBRIDO, "1111AAA", 42000, u2);
		Vehiculo v2 = new Vehiculo(Marca.MASERATI, "Grecale", 2018, 90000, Estado.EN_VENTA, Tipo.HIBRIDO, "2222BBB", 67000, u1);
		Vehiculo v3 = new Vehiculo(Marca.MAZDA, "3", 2025, 20000, Estado.EN_VENTA, Tipo.GASOLINA, "3333CCC", 11000, u1);
		gestorDB.guardarVehiculo(v1);
		gestorDB.guardarVehiculo(v2);
		gestorDB.guardarVehiculo(v3);
	}

}
