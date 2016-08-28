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
	private float truePositive;
	private float falsePositive;
	private float falseNegative;

	public EvaluationMetrics(List<ScoreDTO> testingList, List<ScoreDTO> resultList) {
		this.testingList = testingList;
		this.resultList = resultList;
		this.truePositive = getTruePositive();
		this.falsePositive = getFalsePositive();
		this.falseNegative = getFalseNegative();
	}

	private int getTruePositive() {
		int count = 0;
		for (ScoreDTO resultDto : resultList) {
			if (resultDto.isRelevant()) {
				for (ScoreDTO testDto : testingList) {
					if (resultDto.compare(testDto) && testDto.isRelevant()) {
						count++;
						break;
					}
				}
			}
		}
		return count;
	}

	private int getFalsePositive() {
		int count = 0;
		for (ScoreDTO resultDto : resultList) {
			if (!resultDto.isRelevant()) {
				for (ScoreDTO testDto : testingList) {
					if (resultDto.compare(testDto) && !testDto.isRelevant()) {
						count++;
						break;
					}
				}
			}
		}
		return count;
	}

	private int getFalseNegative() {
		int count = 0;
		boolean isPredicted = false;
		for (ScoreDTO testDto : testingList) {
			if (testDto.isRelevant()) {
				for (ScoreDTO resultDto : resultList) {
					if (resultDto.compare(testDto)) {
						isPredicted = true;
						break;
					}
				}
				if (!isPredicted) {
					count++;
				} else {
					isPredicted = false;
				}
			}
		}
		return count;
	}

	public float calculatePrecision() {
		if (truePositive + falsePositive == 0) {
			return 0.0f;
		}
		float point = 0.0f;
		point = truePositive / (truePositive + falsePositive);
		return point;
	}

	public float calculateRecall() {
		if (truePositive + falseNegative == 0) {
			return 0.0f;
		}
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
				if (testDto.compare(trainDto)) {
					score += Math.abs(trainDto.getScore() - testDto.getScore());
					count++;
					break;
				}
			}
		}
		if (count == 0)
			return 0;
		score /= count;
		return score;
	}

	public float calculateRMSE() {
		float score = 0.0f;
		int count = 0;
		for (ScoreDTO testDto : testingList) {
			for (ScoreDTO trainDto : resultList) {
				if (testDto.compare(trainDto)) {
					score += Math.pow((trainDto.getScore() - testDto.getScore()), 2);
					count++;
				}
			}
		}
		if (count == 0)
			return 0;
		score /= count;
		return (float) Math.sqrt(score);
	}
}
