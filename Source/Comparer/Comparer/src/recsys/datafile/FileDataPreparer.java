package recsys.datafile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import dto.CvDTO;
import dto.JobDTO;
import dto.ScoreDTO;

public class FileDataPreparer {

	public static int toInt(String data) {
		try {
			return Integer.parseInt(data);
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static float toFloat(String data) {
		try {
			return Float.parseFloat(data);
		} catch (Exception e) {
			return -1.0f;
		}
	}

	public static ArrayList<CvDTO> loadCvFile(String file_name) throws IOException {
		ArrayList<CvDTO> list = new ArrayList<CvDTO>();
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file_name), "UTF-8"));
		String data = null;
		while ((data = buf.readLine()) != null) {
			CvDTO dto = new CvDTO();
			Scanner scan = new Scanner(data);
			scan.useDelimiter("\t");			
			dto.setAccountId(toInt(scan.next()));
			dto.setResumeId(toInt(scan.next()));
			dto.setUserName(scan.next());
			dto.setJobName(scan.next());
			dto.setAddress(scan.next());
			dto.setExpectedSalary(scan.next());
			dto.setCategory(scan.next());
			dto.setEducation(scan.next());
			dto.setLanguage(scan.next());
			dto.setSkill(scan.next());		
			list.add(dto);
		}
		buf.close();
		return list;
	}
		
	public static ArrayList<ScoreDTO> loadScoreFile(String file_name) throws IOException
	{
		ArrayList<ScoreDTO> list = new ArrayList<ScoreDTO>();
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file_name), "UTF-8"));
		String data = null;
		while ((data = buf.readLine()) != null) {
			ScoreDTO dto = new ScoreDTO();
			Scanner scan = new Scanner(data);
			scan.useDelimiter("\t");	
			dto.setUserId(toInt(scan.next()));
			dto.setJobId(toInt(scan.next()));
			dto.setScore(toFloat(scan.next()));			
			list.add(dto);
		}
		return list;
	}
		
	public static ArrayList<JobDTO> loadJobFile(String file_name) throws IOException {
		ArrayList<JobDTO> list = new ArrayList<JobDTO>();
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file_name), "UTF-8"));
		String data = null;
		while ((data = buf.readLine()) != null) {
			JobDTO dto = new JobDTO();
			Scanner scan = new Scanner(data);
			scan.useDelimiter("\t");			
			dto.setJobId(toInt(scan.next()));
			dto.setJobName(scan.next());
			dto.setLocation(scan.next());			
			dto.setSalary(scan.next());
			dto.setCategory(scan.next());
			dto.setRequirement(scan.next());			
			dto.setTags(scan.next());
			dto.setDescription(scan.next());
			list.add(dto);
		}
		buf.close();
		return list;
	}
		
}

