package xiaoba.coach.net.result;


import java.io.Serializable;
import java.util.List;

import xiaoba.coach.module.Schedule;


public class SetDateTimeResult extends BaseResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Schedule> datelist;

	public List<Schedule> getDatelist() {
		return datelist;
	}

	public void setDatelist(List<Schedule> datelist) {
		this.datelist = datelist;
	}

}
