package xiaoba.coach.net.result;

import java.util.List;

public class GetIncomeDetailResult extends BaseResult {
	float balance;		//账户余额
	float fmoney;  //冻结金
	float gmoney;		//保证金额
	
	List<RecordInfo>recordlist;		//余额变动记录List
	
	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getFmoney() {
		return fmoney;
	}

	public void setFmoney(float fmoney) {
		this.fmoney = fmoney;
	}



	public float getGmoney() {
		return gmoney;
	}



	public void setGmoney(float gmoney) {
		this.gmoney = gmoney;
	}



	public List<RecordInfo> getRecordlist() {
		return recordlist;
	}



	public void setRecordlist(List<RecordInfo> recordlist) {
		this.recordlist = recordlist;
	}



	public class RecordInfo {
		int type;		//1.学员支付,2.提现,3.充值
		String addtime;		//时间,提现的话记录的是提现申请通过的时间
		float amount;		//数量		
		float amount_out1;		//平台抽成
		float amount_out2;		//驾校抽成
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getAddtime() {
			return addtime;
		}
		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}
		public float getAmount() {
			return amount;
		}
		public void setAmount(float amount) {
			this.amount = amount;
		}
		public float getAmount_out1() {
			return amount_out1;
		}
		public void setAmount_out1(float amount_out1) {
			this.amount_out1 = amount_out1;
		}
		public float getAmount_out2() {
			return amount_out2;
		}
		public void setAmount_out2(float amount_out2) {
			this.amount_out2 = amount_out2;
		}
	}
}
