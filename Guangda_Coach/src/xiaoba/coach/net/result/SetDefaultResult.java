package xiaoba.coach.net.result;

import java.util.List;

import xiaoba.coach.module.Schedule;

public class SetDefaultResult extends BaseResult {
	List<Schedule>datelist;
	int cancelpermission;

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
