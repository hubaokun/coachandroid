package xiaoba.coach.module;

public class HistoryTaskInfo extends TaskInfo {
	Studentscore studentscore;
	Coachscore coachscore;
	private int disagree;		//是否同意取消订单，1=不同意取消
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

	public int getDisagree() {
		return disagree;
	}

	public void setDisagree(int disagree) {
		this.disagree = disagree;
	}

	public class Studentscore {
		float score;		//综合评分<后台计算三个分的平均值>
		float score1;		//评分1<教学态度>
		float score2;		//评分2<教学质量>
		float score3;		//评分3<车容车貌>
		String content;		//评价内容
		
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
