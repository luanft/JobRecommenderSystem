package recsys.datapreparer;

import dto.CvDTO;
import dto.JobDTO;
import dto.ScoreDTO;

import java.util.ArrayList;
import java.util.List;

public class DataPreparer {
  protected DataSetReader dataReader = null;

  public DataPreparer(String dir) {
    dataReader = new DataSetReader(dir);
  }


  public List<ScoreDTO> getAllScores() {
    List<ScoreDTO> scoreDTOList = new ArrayList<ScoreDTO>();
    ScoreDTO dto = null;
    while ((dto = dataReader.nextScore()) != null) {
      scoreDTOList.add(dto);
    }
    return scoreDTOList;
  }

  public List<ScoreDTO> getAllEvaluateScores(String source) {
    List<ScoreDTO> scoreDTOList = new ArrayList<ScoreDTO>();
    ScoreDTO dto = null;
    dataReader.setSource(source);
    dataReader.open(DataSetType.Score);
    while ((dto = dataReader.nextScore()) != null) {
      if (dto.getScore() <= 3) {
        dto.setRelevant(false);
      } else {
        dto.setRelevant(true);
      }
      scoreDTOList.add(dto);
    }
    return scoreDTOList;
  }

  public List<CvDTO> getAllCVs() {
    List<CvDTO> cvDTOList = new ArrayList<CvDTO>();
    CvDTO dto = null;
    while ((dto = dataReader.nextCv()) != null) {
      cvDTOList.add(dto);
    }
    return cvDTOList;
  }

  public List<JobDTO> getAllJobs() {
    List<JobDTO> jobDTOList = new ArrayList<JobDTO>();
    JobDTO dto = null;
    while ((dto = dataReader.nextJob()) != null) {
      jobDTOList.add(dto);
    }
    return jobDTOList;
  }
}