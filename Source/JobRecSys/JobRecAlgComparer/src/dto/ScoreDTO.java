package dto;

public class ScoreDTO {

  private int jobId;
  private int userId;
  private float score;
  private boolean relevant;

  public boolean isRelevant() {
    return relevant;
  }

  public void setRelevant(boolean relevant) {
    this.relevant = relevant;
  }

  public int getJobId() {
    return jobId;
  }

  public void setJobId(int jobId) {
    this.jobId = jobId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

  public boolean compare(ScoreDTO dto) {
    if (this.userId == dto.getUserId() && this.jobId == dto.getJobId() && this.relevant == dto.isRelevant()) {
      return true;
    } else {
      return false;
    }
  }
}
