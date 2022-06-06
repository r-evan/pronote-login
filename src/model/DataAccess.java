package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataAccess {
	
	private Connection connect;
	
	/**
	 * Constructeur : instancie une connexion au SGBD et la mémorise 
	 * @throws SQLException
	 */
	public DataAccess() {
		String url = "jdbc:mysql://localhost:3306/users_accounts";
		String user = "root";
		String passwd = "";
		
		try {
			connect = DriverManager.getConnection(url, user, passwd);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Exécute une requête SQL qui renvoie un résultat simple de type String
	 * @param sql la requête à exécuter
	 * @return la valeur résultant de l'exécution de la requête. Retourne null s'il n'y a pas de résultat. 
	 * @throws SQLException
	 */
	public ArrayList<String> execSQL (String sql)  {
		
		ArrayList<String> result = new ArrayList<String>();
		Statement stmt;

		try {
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				result.add(rs.getString(1));
				result.add(rs.getString(2));

			}
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
public boolean insertSQL (String sql)  {
		
		Statement stmt;
		try {
			stmt = connect.createStatement();
			stmt.execute(sql);
			connect.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
