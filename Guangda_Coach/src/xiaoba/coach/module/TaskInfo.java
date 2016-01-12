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
	int state;		//0:距离开始时间超过一个小时.1:距离开始时间少于一个小时.且教练当前没有其它的进行中任务.前端处理:任务的时间显示为红色.可以确认上车.2:距离开始时间少于一个小时.但教练当前还有其它的进行中任务.前端处理:任务的时间显示为红色.不可以确认上车.3:前端处理:显示练车中,且可以确认下车.
	String total;
	StudentInfo studentinfo;
	private int agreecancel;		//0是订单要取消，1是不取消
	private int paytype;		//学员支付的方式1=余额，2=小巴券，3=小巴币，4=混合支付，0=没有支付
	private String subjectname;			//只有在陪驾的情况下，subjectname有值 
	private int attachcar;		//陪练是否教练带车1=教练带车，2=学员带车 
	private int coursetype; 		//课程类型1=科目二，2=科目三，3=考场练习，4=陪驾，5=体验课

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

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public int getAttachcar() {
		return attachcar;
	}

	public void setAttachcar(int attachcar) {
		this.attachcar = attachcar;
	}

	public int getCoursetype() {
		return coursetype;
	}

	public void setCoursetype(int coursetype) {
		this.coursetype = coursetype;
	}

}
