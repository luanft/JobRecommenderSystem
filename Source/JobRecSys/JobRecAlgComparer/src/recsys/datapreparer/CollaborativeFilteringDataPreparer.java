package recsys.datapreparer;

import java.util.ArrayList;
import java.util.List;

import dto.ScoreDTO;

public class CollaborativeFilteringDataPreparer extends DataPreparer {

	DataSetReader dataReader;
	List<Integer> listUserIds;

	public CollaborativeFilteringDataPreparer(String dir) {
		super(dir);
		dataReader = new DataSetReader(dir);
	}

	public List<Integer> getListUserId() {
		listUserIds = new ArrayList<>();
		dataReader.open(DataSetType.Score);
		ScoreDTO score_dto = null;
		while ((score_dto = dataReader.nextScore()) != null) {
			int userId = score_dto.getUserId();
			if (!isOverlap(userId))
				listUserIds.add(userId);
		}
		return listUserIds;
	}

	private boolean isOverlap(int userId) {
		for (Integer i : listUserIds) {
			if (userId == i)
				return true;
		}
		return false;
	}
}
