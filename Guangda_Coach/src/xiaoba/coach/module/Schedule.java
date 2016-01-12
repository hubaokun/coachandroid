package xiaoba.coach.module;

import java.io.Serializable;

public class Schedule implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String date;		//日期<2015-03-01>
	int hour;		//设置时间点,1-24  如果是0则表示的是全天属性设置
	int state;		//全天状态 0开课  1休息
	int cancelstate;		//当天的订单是否可以取消 0.可以取消 1.不可以取消
	String price;		//单价
	int isrest;		//是否休息 0.不休息  1.休息
	int addressid;		//上车地点ID
	int subjectid;		//科目ID
	String subject;		//科目详细名称
	String addressdetail;		//地址详细
	int hasbooked;  //0未被预约，1被预约
	private int expire;   //是否过期 1=已过期
	private String bookedername;		//已预约学员的名字
	private String addtionalprice = "0";		//陪驾增加的费用
	private String cuseraddtionalprice;  
	private int isfreecourse;//0不是体验课，1是体验课
	private String isnew;  //1是刚点击的时间点

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCancelstate() {
		return cancelstate;
	}

	public void setCancelstate(int cancelstate) {
		this.cancelstate = cancelstate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getIsrest() {
		return isrest;
	}

	public void setIsrest(int isrest) {
		this.isrest = isrest;
	}

	public int getAddressid() {
		return addressid;
	}

	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}

	public int getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAddressdetail() {
		return addressdetail;
	}

	public void setAddressdetail(String addressdetail) {
		this.addressdetail = addressdetail;
	}

	public int getHasbooked() {
		return hasbooked;
	}

	public void setHasbooked(int hasbooked) {
		this.hasbooked = hasbooked;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public String getBookedername() {
		return bookedername;
	}

	public void setBookedername(String bookedername) {
		this.bookedername = bookedername;
	}

	public String getCuseraddtionalprice() {
		return cuseraddtionalprice;
	}

	public void setCuseraddtionalprice(String cuseraddtionalprice) {
		this.cuseraddtionalprice = cuseraddtionalprice;
	}

	public String getAddtionalprice() {
		return addtionalprice;
	}

	public void setAddtionalprice(String addtionalprice) {
		this.addtionalprice = addtionalprice;
	}

	public int getIsfreecourse() {
		return isfreecourse;
	}

	public void setIsfreecourse(int isfreecourse) {
		this.isfreecourse = isfreecourse;
	}

	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

}
