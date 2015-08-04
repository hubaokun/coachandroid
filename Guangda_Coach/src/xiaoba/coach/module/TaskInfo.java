package xiaoba.coach.module;

public class TaskInfo {
	int orderid;
	String creat_time;
	String date;
	String start_time;
	String end_time;
	String longitude;
	String latitude;
	String detail;
	int state;
	String total;
	StudentInfo studentinfo;
	private int agreecancel;
	private int paytype;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(String creat_time) {
		this.creat_time = creat_time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public StudentInfo getStudentinfo() {
		return studentinfo;
	}

	public void setStudentinfo(StudentInfo studentinfo) {
		this.studentinfo = studentinfo;
	}

	public int getAgreecancel() {
		return agreecancel;
	}

	public void setAgreecancel(int agreecancel) {
		this.agreecancel = agreecancel;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

}
