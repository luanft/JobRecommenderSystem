package uit.se.recsys.DataPreparation;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataPreparerCollaborativeFiltering extends DataPreparer{
	public void InitData(String outputFileName) {		
		if (connection.connect()) {
			String sql = "SELECT account.AccountId, xx.JobId, COALESCE(xx.Rating, 0) as Rating FROM account JOIN ( SELECT job.JobId, job_recommended.Rating FROM job LEFT JOIN job_recommended ON job.JobId = job_recommended.JobId) as xx where Rating > 0 order by account.AccountId asc";
			ResultSet rs = connection.readStream(sql);
			try {
				FileWriter writer = new FileWriter(outputFileName);
				while (rs.next()) {
					writer.append(rs.getString("AccountId"));
					writer.append('\t');
					writer.append(rs.getString("JobId"));
					writer.append('\t');
					writer.append(rs.getString("Rating"));
					writer.append('\n');
				}
				rs.close();
				writer.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void CreateFileData(String outputFileName) {
		if (connection.connect()) {
			String sql = "SELECT AccountId, JobId, Rating FROM job_recommended WHERE Rating > 0";
			ResultSet rs = connection.readStream(sql);
			try {
				FileWriter writer = new FileWriter(outputFileName);
				while (rs.next()) {
					writer.append(rs.getString("AccountId"));
					writer.append(',');
					writer.append(rs.getString("JobId"));
					writer.append(',');
					writer.append(rs.getString("Rating"));
					writer.append('\n');
				}
				rs.close();
				writer.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void CreateFileFromDataBase(String outputFileName, String sql) {
		if (connection.connect()) {
			ResultSet rs = connection.readStream(sql);
			try {
				FileWriter writer = new FileWriter(outputFileName);
				while (rs.next()) {
					writer.append(rs.getString("AccountId"));
					writer.append(',');
					writer.append(rs.getString("JobId"));
					writer.append(',');
					writer.append(rs.getString("Rating"));
					writer.append('\n');
				}
				rs.close();
				writer.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
