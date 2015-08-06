package xiaoba.coach.net.result;

import java.util.List;

public class GetRecommendResult<T> extends BaseResult {
	
	private int totalreward;
	private int total;
	private int hasmore;
	private int rflag;
	private List<Recommend> RecommendList;
	
	public class Recommend
	{
		private String invitedpeoplename;
		private String invitedpeopletelphone;
		private int ischecked;
		private int isOrder;
		private int reward;
		private String addtime;
		
		public int getReward() {
			return reward;
		}
		public void setReward(int reward) {
			this.reward = reward;
		}
		public int getIsOrder() {
			return isOrder;
		}
		public void setIsOrder(int isOrder) {
			this.isOrder = isOrder;
		}
		public int getIschecked() {
			return ischecked;
		}
		public void setIschecked(int ischecked) {
			this.ischecked = ischecked;
		}
		public String getInvitedpeoplename() {
			return invitedpeoplename;
		}
		public void setInvitedpeoplename(String invitedpeoplename) {
			this.invitedpeoplename = invitedpeoplename;
		}
		public String getAddtime() {
			return addtime;
		}
		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}
		public String getInvitedpeopletelphone() {
			return invitedpeopletelphone;
		}
		public void setInvitedpeopletelphone(String invitedpeopletelphone) {
			this.invitedpeopletelphone = invitedpeopletelphone;
		}
	}

	public List<Recommend> getRecommendList() {
		return RecommendList;
	}

	public void setRecommendList(List<Recommend> recommendList) {
		RecommendList = recommendList;
	}

	public int getRflag() {
		return rflag;
	}

	public void setRflag(int rflag) {
		this.rflag = rflag;
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalreward() {
		return totalreward;
	}

	public void setTotalreward(int totalreward) {
		this.totalreward = totalreward;
	}
}
