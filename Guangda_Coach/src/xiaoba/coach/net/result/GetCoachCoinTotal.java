package xiaoba.coach.net.result;

import java.util.List;

public class GetCoachCoinTotal extends BaseResult {
	
	private List<coinAffiliationList > coinaffiliationlist;
	
	public class coinAffiliationList
	{
		private int coin;
		private int type;
		public int getCoin() {
			return coin;
		}
		public void setCoin(int coin) {
			this.coin = coin;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
	}

	public List<coinAffiliationList > getCoinaffiliationlist() {
		return coinaffiliationlist;
	}

	public void setCoinaffiliationlist(List<coinAffiliationList > coinaffiliationlist) {
		this.coinaffiliationlist = coinaffiliationlist;
	}

}
