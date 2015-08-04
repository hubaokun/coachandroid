package xiaoba.coach.net.result;

import java.util.List;

public class getMyCoinsListResult extends BaseResult{
	List<CoinList> recordlist;
	private int hasmore;
	public List<CoinList> getRecordlist() {
		return recordlist;
	}

	public void setRecordlist(List<CoinList> recordlist) {
		this.recordlist = recordlist;
	}



	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}



	public  class CoinList{
		public int payerid;
		public int payertype;
		public int receiverid;
		public int receivertype;
		public int coinnum;
		public int ownerid;
		public String addtime;
		private String payername;
		private int coinrecordid;
		public int getPayerid() {
			return payerid;
		}
		public void setPayerid(int payerid) {
			this.payerid = payerid;
		}
		public int getPayertype() {
			return payertype;
		}
		public void setPayertype(int payertype) {
			this.payertype = payertype;
		}
		public int getReceiverid() {
			return receiverid;
		}
		public void setReceiverid(int receiverid) {
			this.receiverid = receiverid;
		}
		public int getReceivertype() {
			return receivertype;
		}
		public void setReceivertype(int receivertype) {
			this.receivertype = receivertype;
		}
		public int getCoinnum() {
			return coinnum;
		}
		public void setCoinnum(int coinnum) {
			this.coinnum = coinnum;
		}
		public int getOwnerid() {
			return ownerid;
		}
		public void setOwnerid(int ownerid) {
			this.ownerid = ownerid;
		}
		public String getAddtime() {
			return addtime;
		}
		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}
		public String getPayername() {
			return payername;
		}
		public void setPayername(String payername) {
			this.payername = payername;
		}
		public int getCoinrecordid() {
			return coinrecordid;
		}
		public void setCoinrecordid(int coinrecordid) {
			this.coinrecordid = coinrecordid;
		}
		
		
	}

}
