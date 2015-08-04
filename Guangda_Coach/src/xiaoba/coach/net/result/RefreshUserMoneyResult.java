package xiaoba.coach.net.result;

public class RefreshUserMoneyResult extends BaseResult {
	private String money;
	private String fmoney;
	private String gmoney;
	private int couponhour;
	private int coinnum;
	
	

	public int getCoinnum() {
		return coinnum;
	}

	public void setCoinnum(int coinnum) {
		this.coinnum = coinnum;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getFmoney() {
		return fmoney;
	}

	public void setFmoney(String fmoney) {
		this.fmoney = fmoney;
	}

	public String getGmoney() {
		return gmoney;
	}

	public void setGmoney(String gmoney) {
		this.gmoney = gmoney;
	}

	public int getCouponhour() {
		return couponhour;
	}

	public void setCouponhour(int couponhour) {
		this.couponhour = couponhour;
	}

}
