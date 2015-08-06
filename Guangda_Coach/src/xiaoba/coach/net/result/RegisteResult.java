package xiaoba.coach.net.result;

import xiaoba.coach.module.UserInfo;

public class RegisteResult extends BaseResult {
	
	private int isInvited;
	private int crewardamount;
	private int orewardamount;
	UserInfo UserInfo;

	public UserInfo getUserInfo() {
		return UserInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		UserInfo = userInfo;
	}

	public int getIsInvited() {
		return isInvited;
	}

	public void setIsInvited(int isInvited) {
		this.isInvited = isInvited;
	}

	public int getCrewardamount() {
		return crewardamount;
	}

	public void setCrewardamount(int crewardamount) {
		this.crewardamount = crewardamount;
	}

	public int getOrewardamount() {
		return orewardamount;
	}

	public void setOrewardamount(int orewardamount) {
		this.orewardamount = orewardamount;
	}

}
