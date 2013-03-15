package pedro.don.te.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcSqlServer {

	public JdbcSqlServer(String server, String dbName, String userName,
			String psswd) throws Exception{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
					.newInstance();
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://"
					+ server + ":1433;databaseName=" + dbName + ";user=" + userName
					+ ";password=" + psswd + ";");
			System.out.println("Ok");
			
			conn.close();
			
			throw new Exception("Conexion establecida!!!");
			
		} catch (Exception e) {
			throw e;
		}
	}

}
