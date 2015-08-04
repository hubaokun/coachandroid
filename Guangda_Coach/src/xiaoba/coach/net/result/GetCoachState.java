package xiaoba.coach.net.result;

public class GetCoachState extends BaseResult {
	
	private coachInfo coachinfo;
	
	public class coachInfo
	{
		private int state;

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}
	}

	public coachInfo getCoachinfo() {
		return coachinfo;
	}

	public void setCoachinfo(coachInfo coachinfo) {
		this.coachinfo = coachinfo;
	}
}
