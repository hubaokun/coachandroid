package xiaoba.coach.net.result;

import java.util.List;

public class GetCouponResult extends BaseResult {
	
	private List<couponList> couponlist;
	
	public class couponList
	{
		private int recordid;		//记录ID
		private int couponid;		//小巴币id
		private int coachid;		//教练id
		private String gettime;		//获得时间
		private int value;		//面值<一定是时间券>
		private int ownertype;		//发布者类型
		private int money_value;		//小巴券可以套现的金额(时间券独有)
		private int state;		//小巴券类型<暂时不用,只获取未提现的小巴券>
		private int ownerid;		//发布者id
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
