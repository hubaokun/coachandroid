package xiaoba.coach.net.result;

import java.util.List;

public class getNoticeResult extends BaseResult {
	private int hasmore;
	private List<NoticeList> datalist;		//通知List

	public class NoticeList {
		private int noticeid;		//通知ID
		private String content;		//通知内容
		private String addtime;		//通知时间<分页排序字段>

		public int getNoticeid() {
			return noticeid;
		}

		public void setNoticeid(int noticeid) {
			this.noticeid = noticeid;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getAddtime() {
			return addtime;
		}

		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public List<NoticeList> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<NoticeList> datalist) {
		this.datalist = datalist;
	}
}
