package xiaoba.coach.module;

public class HistoryTaskInfo extends TaskInfo {
	Studentscore studentscore;
	Coachscore coachscore;

	public Studentscore getStudentscore() {
		return studentscore;
	}

	public void setStudentscore(Studentscore studentscore) {
		this.studentscore = studentscore;
	}

	public Coachscore getCoachscore() {
		return coachscore;
	}

	public void setCoachscore(Coachscore coachscore) {
		this.coachscore = coachscore;
	}

	public class Studentscore {
		float score;
		float score1;
		float score2;
		float score3;
		String content;
		
		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

		public float getScore1() {
			return score1;
		}

		public void setScore1(float score1) {
			this.score1 = score1;
		}

		public float getScore2() {
			return score2;
		}

		public void setScore2(float score2) {
			this.score2 = score2;
		}

		public float getScore3() {
			return score3;
		}

		public void setScore3(float score3) {
			this.score3 = score3;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}

}
