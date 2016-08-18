package recsys.evaluate;

import java.util.List;

import dto.ScoreDTO;

/**
 * Created with IntelliJ IDEA. User: tuynguye Date: 8/12/16 Time: 5:06 PM To
 * change this template use File | Settings | File Templates.
 */
public class EvaluationMetrics {

	private List<ScoreDTO> testingList;
	private List<ScoreDTO> resultList;
	private int truePositive;
	private int falsePositive;
	private int falseNegative;

	public EvaluationMetrics(List<ScoreDTO> testingList,
			List<ScoreDTO> resultList) {
		this.testingList = testingList;
		this.resultList = resultList;
		this.truePositive = getTruePositive();
		this.falsePositive = getFalsePositive();
		this.falseNegative = getFalseNegative();
	}

	private int getTruePositive() {
		int count = 0;
		for (ScoreDTO resultDto : resultList) {
			for (ScoreDTO testDto : testingList) {
				if (testDto.isRelevant() && resultDto.compare(testDto)) {
					count++;
					break;
				}
			}
		}
		return count;
	}

	private int getFalsePositive() {
		int count = 0;
		for (ScoreDTO resultDto : resultList) {
			for (ScoreDTO testDto : testingList) {
				if (resultDto.isRelevant() && !resultDto.compare(testDto)) {
					count++;
					break;
				}
			}
		}
		return count;
	}

	private int getFalseNegative() {
		int count = 0;
		for (ScoreDTO resultDto : resultList) {
			for (ScoreDTO testDto : testingList) {
				if (!resultDto.isRelevant() && !resultDto.compare(testDto)) {
					count++;
					break;
				}
			}
		}
		return count;
	}

	public float calculatePrecision() {
		float point = 0.0f;
		point = truePositive / (truePositive + falsePositive);
		return point;
	}

	public float calculateRecall() {
		float point = 0.0f;
		point = truePositive / (truePositive + falseNegative);
		return point;
	}

	public float calculateF1(float precision, float recall) {
		if (precision + recall == 0)
			return 0;
		return (2 * precision * recall) / (precision + recall);
	}

	public float calculateMAE() {
		float score = 0.0f;
		int count = 0;
		for (ScoreDTO testDto : testingList) {
			for (ScoreDTO trainDto : resultList) {
				if (testDto.getUserId() == trainDto.getUserId()
						&& testDto.getJobId() == trainDto.getJobId()) {
					score += Math.abs(testDto.getScore() - trainDto.getScore());
					count++;
					break;
				}
			}
		}
		score /= count;
		return score;
	}

	public float calculateRMSE() {
		float score = 0.0f;
		int count = 0;
		for (ScoreDTO testDto : testingList) {
			for (ScoreDTO trainDto : resultList) {
				if (testDto.getUserId() == trainDto.getUserId()
						&& testDto.getJobId() == trainDto.getJobId()) {
					score += Math.pow(
							(testDto.getScore() - trainDto.getScore()), 2);
					count++;
				}
			}
		}
		score /= count;
		return (float) Math.sqrt(score);
	}
}
