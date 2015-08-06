package xiaoba.coach.net.result;


import java.util.List;

import xiaoba.coach.module.TaskInfo;

public class UndoneOrderListResult extends BaseResult {
	int hasmore;
	List<TaskInfo> tasklist;

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public List<TaskInfo> getTasklist() {
		return tasklist;
	}

	public void setTasklist(List<TaskInfo> tasklist) {
		this.tasklist = tasklist;
	}

}
