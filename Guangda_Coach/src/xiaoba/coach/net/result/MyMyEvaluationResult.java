package xiaoba.coach.net.result;

import java.util.List;

public class MyMyEvaluationResult extends BaseResult {
	private int hasmore;
	private List<EvaluationResult> evaluationlist;		//评价列表

	public class EvaluationResult {
		private int studentid;		//评价学员ID
		private String studentavatar;		//评价学员头像<small>
		private String name;		//评价学员真实姓名
		private float score;		//评分
		private String phone;		//学员电话号码
		private String studentcardnum;		//学员证号
		private String starttime;		//任务开始时间
		private String endtime;		//任务结束时间
		private String content;		//评价详情

		public int getStudentid() {
			return studentid;
		}

		public void setStudentid(int studentid) {
			this.studentid = studentid;
		}

		public String getStudentavatar() {
			return studentavatar;
		}

		public void setStudentavatar(String studentavatar) {
			this.studentavatar = studentavatar;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getStudentcardnum() {
			return studentcardnum;
		}

		public void setStudentcardnum(String studentcardnum) {
			this.studentcardnum = studentcardnum;
		}

		public String getStarttime() {
			return starttime;
		}

		public void setStarttime(String starttime) {
			this.starttime = starttime;
		}

		public String getEndtime() {
			return endtime;
		}

		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public List<EvaluationResult> getEvaluationlist() {
		return evaluationlist;
	}

	public void setEvaluationlist(List<EvaluationResult> evaluationlist) {
		this.evaluationlist = evaluationlist;
	}

}
