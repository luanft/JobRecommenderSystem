package uit.se.recsys;

import java.io.FileOutputStream;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;


public class App {
	public static String getLanguage(String resumeId) throws Exception {
		DbConfig conf = DbConfig.load("config/config.txt");
		if (conf == null)
			throw new Exception("Khong doc duoc db");

		MysqlDBConnection connection = new MysqlDBConnection(conf);	
		String languages = "language: ";
		if (connection.connect()) {
			ResultSet rs = connection.read("select * from language where ResumeId = " + resumeId);
			try {
				while (rs.next()) {
					languages += rs.getString("Name") + " ";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.close();
		}
		return languages;
	}
	
	public static String getSkill(String resumeId) throws Exception {
		DbConfig conf = DbConfig.load("config/config.txt");
		if (conf == null)
			throw new Exception("Khong doc duoc db");

		MysqlDBConnection connection = new MysqlDBConnection(conf);			
		String skill = "skill: ";
		if (connection.connect()) {
			ResultSet rs = connection.read("select * from skill where ResumeId = " + resumeId);
			try {
				while (rs.next()) {
					skill += rs.getString("Name") + ", ";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.close();
		}
		return skill;
	}
	
	public static String getEducation(String resumeId) throws Exception {
		DbConfig conf = DbConfig.load("config/config.txt");
		if (conf == null)
			throw new Exception("Khong doc duoc db");

		MysqlDBConnection connection = new MysqlDBConnection(conf);		
		String data = "education:  ";
		if (connection.connect()) {
			ResultSet rs = connection.read("select * from education where ResumeId = " + resumeId);
			try {
				while (rs.next()) {
					data += rs.getString("EducationLevel") + " ";
					data += rs.getString("EducationMajor") + " ";
					data += rs.getString("EducationDescription") + ", ";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.close();
		}
		return data;
	}
	
	public static void WriteJobFile(String file_name) throws Exception {
		DbConfig conf = DbConfig.load("config/config.txt");
		if (conf == null)
			throw new Exception("Khong doc duoc db");

		MysqlDBConnection connection = new MysqlDBConnection(conf);
		if (connection.connect()) {
			ResultSet rs = connection.read(
					"SELECT JobId,JobName,Location,Salary,job.Description,Tags, Requirement,Benifit, category.Description as Category FROM job, category WHERE job.CategoryId = category.CategoryId");
			String data = "";
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file_name), "UTF-8"));

			while (rs.next()) {
				data = eliminate(rs.getString("JobId")) + "\t";
				data += eliminate(rs.getString("JobName")) + "\t";
				data += eliminate(rs.getString("Location")) + "\t";
				data += eliminate(rs.getString("Salary")) + "\t";
				data += eliminate(rs.getString("Category")) + "\t";				
				data += eliminate(rs.getString("Requirement")) + "\t";				
				data += eliminate(rs.getString("Tags")) + "\t";
				data += eliminate(rs.getString("Description"));				
				writer.println(data);
			}
			writer.close();
			connection.close();
		}
	}

	public static void WriteRatingFile(String file_name) throws Exception
	{	
        String sql = "SELECT			AccountId,"
		             + "				JobId,"
		             + " 				Rating "
		             + "FROM 			JOB_RECOMMENDED "
		             + "WHERE 			Rating > 0";
        
		DbConfig conf = DbConfig.load("config/config.txt");
		if (conf == null)
			throw new Exception("Khong doc duoc db");
		MysqlDBConnection connection = new MysqlDBConnection(conf);
		if (connection.connect()) {
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file_name), "UTF-8"));
			ResultSet rs = connection.read(sql);
			while(rs.next())
			{
				String data = eliminate(rs.getString("AccountId")) + '\t';
				data += eliminate(rs.getString("JobId"))+ '\t';
				data += eliminate(rs.getString("Rating"));																								
				writer.println(data);
			}
			connection.close();
			writer.close();
		}
	}
	// and job.JobId not in(select JobId from job_recommended where Rating > 0)
	public static void WriteCVFile(String file_name) throws Exception
	{	
        String sql = "SELECT    CV.AccountId, " +
                "               CV.ResumeId, " +
                "               CV.Title, CV.Name,  " +
                "               CV.Address, " +
                "               CV.ExpectedSalary, " +
                "               category.Description as Category,                 " +
                "               CV.CareerObjective, " +
                "               CV.AccountId, " +
                "               CV.AccountId " +
                "                 " +
                " " +
                "FROM " +
                "		( " +
                "			SELECT 			resume.AccountId, " +
                "							resume.ResumeId, " +
                "							resume.Title, Resume.Name, 										 " +
                "							coalesce(DesireSalary,'Negotiate, thương lượng') as ExpectedSalary, " +
                "							coalesce(career_objective.DesireWorkLocation,resume.Address,'Hồ chí minh') as Address, " +
                "							coalesce(career_objective.CareerObjective,'Thăng tiến trong sự nghiệp') as CareerObjective " +
                "			FROM 			resume " +
                "			LEFT JOIN		career_objective " +
                "			ON 				resume.ResumeId = career_objective.ResumeId " +
                "        ) as CV, " +
                "        care, " +
                "        category " +
                "         " +
                "WHERE	care.AccountId = CV.AccountId " +
                "AND		care.CategoryId = category.CategoryId  ";
        
		DbConfig conf = DbConfig.load("config/config.txt");
		if (conf == null)
			throw new Exception("Khong doc duoc db");
		MysqlDBConnection connection = new MysqlDBConnection(conf);
		if (connection.connect()) {
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file_name), "UTF-8"));
			ResultSet rs = connection.read(sql);
			while(rs.next())
			{
				String data = eliminate(rs.getString("AccountId")) + '\t';
				data += eliminate(rs.getString("ResumeId"))+ '\t';
				data += eliminate(rs.getString("Name"))+ '\t';
				data += eliminate(rs.getString("Title"))+ '\t';
				data += eliminate(rs.getString("Address"))+ '\t';
				data += eliminate(rs.getString("ExpectedSalary"))+ '\t';
				data += eliminate(rs.getString("Category"))+ '\t';
				data += eliminate(getEducation(rs.getString("ResumeId")))+ '\t';
				data += eliminate(getLanguage(rs.getString("ResumeId")))+ '\t';
				data += eliminate(getSkill(rs.getString("ResumeId")))+ '\t';
//				data += eliminate(getLanguage(rs.getString("ResumeId")))+ '\t';
				String objective = eliminate(rs.getString("CareerObjective")).trim();
				data += objective == "" ? "Thăng tiến trong sự nghiệp" + '\t' : objective +  '\t';		
				writer.println(data);
			}
			connection.close();
			writer.close();
		}
	}
		
	public static String eliminate(String str) {
		boolean next = false;
		String buf = "";
		char[] array = str.toCharArray();
		for (int i = 0; i < str.length(); i++) {
			if (array[i] == '\t' || array[i] == '\r' || array[i] == '\n' || array[i] == ' ') {
				next = true;
			} else {
				if (next)
				{
					buf += " ";
					next = false;
				}
				buf += array[i];
			}
		}
		return buf;
	}

	public static void main(String[] args) throws Exception {

		WriteCVFile("Cv.txt");		
		WriteJobFile("Job.txt");
		WriteRatingFile("Score.txt");
	}

}
