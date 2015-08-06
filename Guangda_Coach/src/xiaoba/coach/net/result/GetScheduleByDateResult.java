package xiaoba.coach.net.result;

import java.io.Serializable;
import java.util.List;

import xiaoba.coach.module.Schedule;

public class GetScheduleByDateResult extends BaseResult implements Serializable {
	
	private int cancelpermission;
	
	private List<Schedule> datelist;

	public List<Schedule> getDatelist() {
		return datelist;
	}

	public void setDatelist(List<Schedule> datelist) {
		this.datelist = datelist;
	}

	public int getCancelpermission() {
		return cancelpermission;
	}

	public void setCancelpermission(int cancelpermission) {
		this.cancelpermission = cancelpermission;
	}
}
