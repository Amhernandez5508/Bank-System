package hernandez.alejandro.productosbancarios.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import hernandez.alejandro.productosbancarios.dao.DAO;
import hernandez.alejandro.productosbancarios.datasource.ConnectionDB;
import hernandez.alejandro.productosbancarios.entity.Log;

public class LogDTO implements DAO<Log, Integer>{

	@Override
	public void insert(Log entity) {
		try {
			String sqlQueryTemp = "INSERT INTO log (Fecha, Clase, Producto, Descripcion) VALUES (?, ?, ?, ?)";
			
			PreparedStatement sqlQueryPrep = ConnectionDB.connect().prepareStatement(sqlQueryTemp);
			sqlQueryPrep.setObject(1, entity.getFecha());
			sqlQueryPrep.setString(2, entity.getClase());
			sqlQueryPrep.setString(3, entity.getProducto());
			sqlQueryPrep.setString(4, entity.getDescripcion());
			
			sqlQueryPrep.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void select() {
		try {
			String sqlQueryTemp = "SELECT * FROM log";
			
			PreparedStatement sqlQueryPrep = ConnectionDB.connect().prepareStatement(sqlQueryTemp);
			
			ResultSet replySQL = sqlQueryPrep.executeQuery();
			
			while(replySQL.next()) {
				System.out.println(String.format("ID: %d || Fecha: %s || Clase: %s || Producto: %s || Descripcion: %s", replySQL.getInt("ID"), replySQL.getObject("Fecha").toString(), replySQL.getString("Clase"), replySQL.getString("Producto"), replySQL.getString("Descripcion")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Log selectLastRegistry() {
		Log log = null;
		try {
			String sqlQueryTemp = "SELECT * FROM log WHERE ID = (SELECT MAX(ID) FROM log)";
			
			PreparedStatement sqlQueryPrep = ConnectionDB.connect().prepareStatement(sqlQueryTemp);
			
			ResultSet replySQL = sqlQueryPrep.executeQuery();
				
			while (replySQL.next()) {
				int auxID = replySQL.getInt("ID");
				LocalDateTime auxFecha = (LocalDateTime) replySQL.getObject("Fecha");
				String auxClase = replySQL.getString("Clase");
				String auxProducto = replySQL.getString("Producto");
			    String auxDescripcion = replySQL.getString("Descripcion");
			    
			    log = new Log(auxID, auxFecha, auxClase, auxProducto, auxDescripcion);
			}
		    return log;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return log;
		}
	}

	
}
