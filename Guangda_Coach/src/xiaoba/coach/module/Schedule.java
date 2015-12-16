package xiaoba.coach.module;

import java.io.Serializable;

public class Schedule implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String date;
	int hour;
	int state;
	int cancelstate;
	String price;
	int isrest;
	int addressid;
	int subjectid;
	String subject;
	String addressdetail;
	int hasbooked;  //0未被预约，1被预约
	private int expire;
	private String bookedername;
	private String addtionalprice = "0";
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
