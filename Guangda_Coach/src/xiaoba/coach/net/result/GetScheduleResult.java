package xiaoba.coach.net.result;

import java.io.Serializable;
import java.util.List;

import xiaoba.coach.module.Schedule;

public class GetScheduleResult extends BaseResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String today;		//当前日期<2015-03-01>,前端的时间需要根据返回的时间来定
	int hour;		//当前小时
	int cancelpermission;		//是否有设置当天订单可否取消的权限 0.可以设置  1.不可以设置
	List<Schedule> datelist;
	private int maxdays;		//可以设置的最大天数

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getCancelpermission() {
		return cancelpermission;
	}

	public void setCancelpermission(int cancelpermission) {
		this.cancelpermission = cancelpermission;
	}

	public List<Schedule> getDatelist() {
		return datelist;
	}

	public void setDatelist(List<Schedule> datelist) {
		this.datelist = datelist;
	}

	public int getMaxdays() {
		return maxdays;
	}

	public void setMaxdays(int maxdays) {
		this.maxdays = maxdays;
	}

	
}
