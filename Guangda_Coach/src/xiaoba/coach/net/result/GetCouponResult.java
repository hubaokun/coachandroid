package xiaoba.coach.net.result;

import java.util.List;

public class GetCouponResult extends BaseResult {
	
	private List<couponList> couponlist;
	
	public class couponList
	{
		private int recordid;
		private int couponid;
		private int coachid;
		private String gettime;
		private int value;
		private int ownertype;
		private int money_value;
		private int state;
		private int ownerid;
		public int getRecordid() {
			return recordid;
		}
		public void setRecordid(int recordid) {
			this.recordid = recordid;
		}
		public int getCouponid() {
			return couponid;
		}
		public void setCouponid(int couponid) {
			this.couponid = couponid;
		}
		public int getCoachid() {
			return coachid;
		}
		public void setCoachid(int coachid) {
			this.coachid = coachid;
		}
		public String getGettime() {
			return gettime;
		}
		public void setGettime(String gettime) {
			this.gettime = gettime;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getOwnertype() {
			return ownertype;
		}
		public void setOwnertype(int ownertype) {
			this.ownertype = ownertype;
		}
		public int getMoney_value() {
			return money_value;
		}
		public void setMoney_value(int money_value) {
			this.money_value = money_value;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getOwnerid() {
			return ownerid;
		}
		public void setOwnerid(int ownerid) {
			this.ownerid = ownerid;
		}
	}

	public List<couponList> getCouponlist() {
		return couponlist;
	}

	public void setCouponlist(List<couponList> couponlist) {
		this.couponlist = couponlist;
	}
}
