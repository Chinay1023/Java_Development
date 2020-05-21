import java.util.ArrayList;

public class Stats {
	private ArrayList<ScoreInfo> scoreList = new ArrayList<>();

	public boolean record(int score) {
		boolean found = false;
		int i;
		for (i = 0; i < scoreList.size(); i++)
			if (scoreList.get(i).getScore() == score) {
				found = true;
				break;
			}
		if ((scoreList.size() == 0) || !found) {
			scoreList.add(new ScoreInfo(score));
			for (i = 0; i < scoreList.size() - 1; i++) {
				int minIndex = i;
				for (int j = i + 1; j < scoreList.size(); j++)
					if (scoreList.get(j).getScore() < scoreList.get(minIndex).getScore()) minIndex = j;
				var pH = scoreList.get(i);
				scoreList.set(i, scoreList.get(minIndex));
				scoreList.set(minIndex, pH);
			}
			return true;
		}
		scoreList.get(i).increment();
		return false;
	}

	public void recordScores(int[] stuScores) {
		for (int x : stuScores) record(x);
	}
}

class ScoreInfo {
	private int score;
	private int numStudents;

	public ScoreInfo(int aScore) {
		score = aScore;
		numStudents = 1;
	}

	public void increment() {
		numStudents++;
	}

	public int getScore() {
		return score;
	}

	public int getFrequency() {
		return numStudents;
	}
}