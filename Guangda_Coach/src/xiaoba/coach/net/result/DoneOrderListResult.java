package xiaoba.coach.net.result;


import java.util.List;

import xiaoba.coach.module.HistoryTaskInfo;

public class DoneOrderListResult extends BaseResult {
	List<HistoryTaskInfo> tasklist;
	int hasmore;

	public List<HistoryTaskInfo> getTasklist() {
		return tasklist;
	}

	public void setTasklist(List<HistoryTaskInfo> tasklist) {
		this.tasklist = tasklist;
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

}
