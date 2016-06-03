package recsys.algorithms;

import java.util.ArrayList;
import java.util.List;

public class HybirdRecommeder extends RecommendationAlgorithm {

	public List<UserProfile> users = new ArrayList();

	public void prepareUserProfile() {
		String sql = "select  resume.AccountId,Title,Gender,Address,"
				+ "COALESCE(career_objective.CareerObjective,\"Thăng tiến, promotion\") as Objective,"
				+ "COALESCE(career_objective.DesireSalary,\"Thương lượng, negotiate, cao\") as Salary,"
				+ "COALESCE(career_objective.DesireWorkLocation, \"Thành phố, city, tỉnh, quận, district, province\") as Location"
				+ "from (resume left join		career_objective on	resume.ResumeId = career_objective.CareerObjective)";
	}

	public void contentBasedFiltering()
	{
		
	}
	
}
