package xiaoba.coach.net.result;

import java.util.ArrayList;

public class GetCouponHistoryResult extends BaseResult {
	
	private ArrayList<RecordList> recordlist;
	
	private int hasmore;
	
	public class RecordList
	{
	private String usename;
	private String userphone;
	private int usecount;
	private String gettime;
	public String getGettime() {
		return gettime;
	}
	public void setGettime(String gettime) {
		this.gettime = gettime;
	}
	public int getUsecount() {
		return usecount;
	}
	public void setUsecount(int usecount) {
		this.usecount = usecount;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public String getUsename() {
		return usename;
	}
	public void setUsename(String usename) {
		this.usename = usename;
	}
	}

	public ArrayList<RecordList> getRecordlist() {
		return recordlist;
	}

	public void setRecordlist(ArrayList<RecordList> recordlist) {
		this.recordlist = recordlist;
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

}
