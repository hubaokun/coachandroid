package xiaoba.coach.net.result;

public class GetMsgCountResult extends BaseResult {
	int noticecount;		//GetMessageCount
	int allnoticecount;		//通知总数量
	int complaint1;		//投诉我的<未处理数量>
	int complaint2;		//我投诉的<未处理数量>
	int evaluation1;		//评论我的<累计数量>
	int evaluation2;		//我评论的<累计数量>
	int studentcount;		//我的学员数量<预订过我的学员>

	public int getNoticecount() {
		return noticecount;
	}

	public void setNoticecount(int noticecount) {
		this.noticecount = noticecount;
	}

	public int getAllnoticecount() {
		return allnoticecount;
	}

	public void setAllnoticecount(int allnoticecount) {
		this.allnoticecount = allnoticecount;
	}

	public int getComplaint1() {
		return complaint1;
	}

	public void setComplaint1(int complaint1) {
		this.complaint1 = complaint1;
	}

	public int getComplaint2() {
		return complaint2;
	}

	public void setComplaint2(int complaint2) {
		this.complaint2 = complaint2;
	}

	public int getEvaluation1() {
		return evaluation1;
	}

	public void setEvaluation1(int evaluation1) {
		this.evaluation1 = evaluation1;
	}

	public int getEvaluation2() {
		return evaluation2;
	}

	public void setEvaluation2(int evaluation2) {
		this.evaluation2 = evaluation2;
	}

	public int getStudentcount() {
		return studentcount;
	}

	public void setStudentcount(int studentcount) {
		this.studentcount = studentcount;
	}

}
