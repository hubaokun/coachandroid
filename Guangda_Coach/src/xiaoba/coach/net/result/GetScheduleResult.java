package xiaoba.coach.net.result;

import java.io.Serializable;
import java.util.List;

import xiaoba.coach.module.Schedule;

public class GetScheduleResult extends BaseResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String today;
	int hour;
	int cancelpermission;
	List<Schedule> datelist;
	private int maxdays;

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
