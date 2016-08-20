package recsys.datapreparer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dto.ScoreDTO;

public class CollaborativeFilteringDataPreparer extends DataPreparer {

	List<Integer> listUserIds;

	public CollaborativeFilteringDataPreparer(String dir) {
		super(dir);		
	}

	public List<Integer> getListUserId() {
		listUserIds = new ArrayList<Integer>();
		dataReader.open(DataSetType.Score);
		ScoreDTO score_dto = null;
		while ((score_dto = dataReader.nextScore()) != null) {
			int userId = score_dto.getUserId();
			if (!isOverlap(userId)) {
				listUserIds.add(userId);
			}
		}
		return listUserIds;
	}

	private boolean isOverlap(int userId) {
		for (Integer i : listUserIds) {
			if (userId == i) {
				return true;
			}
		}
		return false;
	}

	public void splitDataSet(int proportionOfTest, String outputDir) {

		dataReader.open(DataSetType.Score);
		List<ScoreDTO> scoreDataSet = getAllScores();
		List<ScoreDTO> scoreTestingSet = new ArrayList<ScoreDTO>();
		List<ScoreDTO> scoreTrainingSet = new ArrayList<ScoreDTO>();
		int fullSize = scoreDataSet.size();
		int testingSize = 0;
		if (proportionOfTest * fullSize % 100 > 5)
			testingSize = (int) (proportionOfTest * fullSize / 100 + 1);

		for (int i = 0; i < testingSize; i++) {
			ScoreDTO dto = getAnyScore(fullSize, scoreDataSet);
			while (scoreTestingSet.contains(dto)) {
				dto = getAnyScore(fullSize, scoreDataSet);
			}
			scoreTestingSet.add(dto);
		}
		if (scoreDataSet.removeAll(scoreTestingSet)) {
			scoreTrainingSet = scoreDataSet;
		}

		writeScore(outputDir + "training\\", "Score.txt", scoreTrainingSet);
		writeScore(outputDir + "testing\\", "Score.txt", scoreTestingSet);
	}

	private ScoreDTO getAnyScore(int maxRange, List<ScoreDTO> fullSet) {
		int index = new Random().nextInt(maxRange);
		return fullSet.get(index);
	}

	private void writeScore(String destination, String fileName, List<ScoreDTO> dataSet) {
		FileWriter fwr;
		try {
			File out = new File(destination);
			if (!out.exists()) {
				out.mkdirs();
			}
			File fileOut = new File(out.getAbsolutePath() + File.separator + fileName);

			fwr = new FileWriter(fileOut, true);
			fwr.write("");
			BufferedWriter wr = new BufferedWriter(fwr);

			for (ScoreDTO dto : dataSet) {
				wr.write(dto.getUserId() + "\t" + dto.getJobId() + "\t" + dto.getScore());
				wr.newLine();
			}

			wr.close();
			fwr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
