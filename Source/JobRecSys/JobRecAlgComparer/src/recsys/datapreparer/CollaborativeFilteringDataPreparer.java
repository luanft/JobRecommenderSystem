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
    dataReader = new DataSetReader(dir);
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
    List<ScoreDTO> fullSet = getAllScores();
    List<ScoreDTO> testingSet = new ArrayList<ScoreDTO>();
    List<ScoreDTO> trainingSet = new ArrayList<ScoreDTO>();
    int fullSize = fullSet.size();
    int testingSize = 0; 
    if(proportionOfTest * fullSize % 100 > 5)
    	testingSize = (int) (proportionOfTest * fullSize / 100 + 1); 

    for (int i = 0; i < testingSize; i++) {
      ScoreDTO dto = getAnyScore(fullSize, fullSet);
      while (testingSet.contains(dto)) {
        dto = getAnyScore(fullSize, fullSet);
      }
      testingSet.add(dto);
    }
    if (fullSet.removeAll(testingSet)) {
      trainingSet = fullSet;
    }
    writeScore(outputDir + "training\\", "Score.txt", trainingSet);
    writeScore(outputDir + "testing\\", "Score.txt", testingSet);
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
