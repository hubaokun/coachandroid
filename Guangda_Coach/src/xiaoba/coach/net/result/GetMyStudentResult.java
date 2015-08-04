package xiaoba.coach.net.result;

import java.util.List;

public class GetMyStudentResult extends BaseResult {
	private int hasmore;
	private List<myStudent> studentList;
	
	
	public class myStudent
	{
		private int studentid;
		private String avatar;
		private String phone = "";
		private String student_cardnum = "";
		private String realname = "";
		private int coachstate;
		private int learntime;
		private int learnmytime;
		private float money;
		public float getMoney() {
			return money;
		}
		public void setMoney(float money) {
			this.money = money;
		}
		public int getLearnmytime() {
			return learnmytime;
		}
		public void setLearnmytime(int learnmytime) {
			this.learnmytime = learnmytime;
		}
		public int getLearntime() {
			return learntime;
		}
		public void setLearntime(int learntime) {
			this.learntime = learntime;
		}
		public int getCoachstate() {
			return coachstate;
		}
		public void setCoachstate(int coachstate) {
			this.coachstate = coachstate;
		}
		public String getRealname() {
			return realname;
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public String getStudent_cardnum() {
			return student_cardnum;
		}
		public void setStudent_cardnum(String student_cardnum) {
			this.student_cardnum = student_cardnum;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public int getStudentid() {
			return studentid;
		}
		public void setStudentid(int studentid) {
			this.studentid = studentid;
		}
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public List<myStudent> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<myStudent> studentList) {
		this.studentList = studentList;
	}
}
