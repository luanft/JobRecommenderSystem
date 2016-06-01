package recsys.datapreparer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataPreparer {

	protected MysqlConnection connection;

	public DataPreparer() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("config/dbConfig.properties"));
			connection = new MysqlConnection(props.getProperty("MYSQL_DB_URL"), props.getProperty("MYSQL_DB_USERNAME"),
					props.getProperty("MYSQL_DB_PASSWORD"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}