package xiaoba.coach.net.result;

import java.util.ArrayList;

public class GetChoseStudentResult extends BaseResult {
	private ArrayList<StudentList> studentlist;
	public class StudentList
	{
		private int studentid;
		private String realname;
		private String phone;
		public int getStudentid() {
			return studentid;
		}
		public void setStudentid(int studentid) {
			this.studentid = studentid;
		}
		public String getRealname() {
			return realname;
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
	}
	public ArrayList<StudentList> getStudentlist() {
		return studentlist;
	}
	public void setStudentlist(ArrayList<StudentList> studentlist) {
		this.studentlist = studentlist;
	}

}
