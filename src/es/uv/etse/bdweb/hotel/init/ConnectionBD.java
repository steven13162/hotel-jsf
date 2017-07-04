package es.uv.etse.bdweb.hotel.init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionBD {

	private Connection conn = null;
	private String user = "isaw";
	private String pass = "isaw";
	String connectionURL = "jdbc:mysql://localhost:3306/bdweb_hotel?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver JDBC creado!");
		} catch (ClassNotFoundException e) {
			System.out.println("No existe el driver JDBC!");
			e.printStackTrace();
		}

		try {
			// Se crea la conexión
			conn = DriverManager.getConnection(connectionURL, user, pass);
			// Deshabilitamos que se haga commit por defecto
			conn.setAutoCommit(false);
			System.out.println("Driver JDBC connectado!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

// el método connect para la versión 6 del MySQL diriver
//	public Connection connect() {
//
//		try {
//			
//			// El Jar del driver debe ser accesible en el classpath
//			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//			// Se crea la conexión
//			conn = DriverManager.getConnection(connectionURL, user, pass);
//			// Deshabilitamos que se haga commit por defecto
//			conn.setAutoCommit(false);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return conn;
//	}
	
	public void insertToBD(String sqlQuery) {
		Statement stmt = null;
		
		try {
		
			stmt = this.conn.createStatement();
			stmt.execute(sqlQuery);
			this.conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
