package uit.se.recsys.DataPreparation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlConnection {
	public String mysqlHost = "";	
	public String userName = "";
	public String password = "";
	private Connection connection = null;
	private PreparedStatement preStatement = null;

	public MysqlConnection() {
		
	}

	public MysqlConnection(String host, String user, String pass) {
		this.userName = user;
		this.password = pass;
		this.mysqlHost = host + "?useUnicode=true&characterEncoding=UTF-8";
	}

	public Connection getConnection() {
		return connection;
	}

	public PreparedStatement getPrepareStatement() {
		return preStatement;
	}

	public void setPrepareStatement(PreparedStatement pre) {
		this.preStatement = pre;
	}

	public Boolean connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(mysqlHost, userName, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ResultSet read(String sql) {
		ResultSet data = null;
		try {
			java.sql.Statement cmd = connection.createStatement();
			data = cmd.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO Auto-generated catch block
		}
		return data;
	}

	public ResultSet readStream(String sql) {
		ResultSet data = null;
		try {
			java.sql.Statement cmd = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			cmd.setFetchSize(Integer.MIN_VALUE);
			data = cmd.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO Auto-generated catch block
		}
		return data;
	}

	public ResultSet readSecure() {
		ResultSet data = null;
		try {
			data = preStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public Boolean write(String sql) {
		try {
			java.sql.Statement cmd = connection.createStatement();
			return cmd.execute(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public Boolean writeSecure() {
		try {
			this.preStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}