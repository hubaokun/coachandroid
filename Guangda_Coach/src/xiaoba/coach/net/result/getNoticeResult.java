package xiaoba.coach.net.result;

import java.util.List;

public class getNoticeResult extends BaseResult {
	private int hasmore;
	private List<NoticeList> datalist;

	public class NoticeList {
		private int noticeid;
		private String content;
		private String addtime;

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
