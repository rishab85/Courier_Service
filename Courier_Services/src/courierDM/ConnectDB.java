package courierDM;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.persistence.Entity;

@Entity
public class ConnectDB {
	
	public static Connection getConnection() throws Exception{
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/courier";
			String username = "root";
			String password = "";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url, username, password);
			System.out.println("connected");
			return con;
		}catch(Exception e){System.out.println("Sorry");}
		
		return null;
		
		
	}
	
}
