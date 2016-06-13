package uit.se.recsys.DataPreparation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataPreparer {

	protected MysqlConnection connection;
	public DataPreparer() {
		Properties props = new Properties();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("config/dbConfig.properties").getFile());
			props.load(new FileInputStream(file));
			connection = new MysqlConnection(props.getProperty("MYSQL_DB_URL"), props.getProperty("MYSQL_DB_USERNAME"),
					props.getProperty("MYSQL_DB_PASSWORD"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}