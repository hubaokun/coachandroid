package xiaoba.coach.net.result;

import xiaoba.coach.module.UserInfo;

public class GetCoachDetail extends BaseResult {
	
	private UserInfo coachinfo;

	public UserInfo getCoachinfo() {
		return coachinfo;
	}

	public void setCoachinfo(UserInfo coachinfo) {
		this.coachinfo = coachinfo;
	}

}
