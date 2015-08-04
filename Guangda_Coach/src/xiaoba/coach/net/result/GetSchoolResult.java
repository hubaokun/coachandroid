package xiaoba.coach.net.result;

import java.util.List;

import xiaoba.coach.module.School;

public class GetSchoolResult extends BaseResult {
	
	private List<School> schoollist;
	

	public List<School> getSchoollist() {
		return schoollist;
	}

	public void setSchoollist(List<School> schoollist) {
		this.schoollist = schoollist;
	}
}
