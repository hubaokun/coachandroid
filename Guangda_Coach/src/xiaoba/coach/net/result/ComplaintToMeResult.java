package xiaoba.coach.net.result;

import java.util.List;

public class ComplaintToMeResult extends BaseResult {
	private int hasmore;
	private List<ComplaintList> complaintlist;		//投诉列表

	public class ComplaintList {
		private int studentid;		//投诉学员ID
		private String studentavatar;		//投诉学员头像
		private String name;		//投诉学员真实姓名
		private float score;		//评分
		private String phone;		//学员电话号码
		private String studentcardnum;		//学员证号
		private String starttime;		//任务开始时间
		private String endtime;		//任务结束时间
		private List<ContentList> contentlist;		//投诉详情列表

		/**
		 * @return the studentid
		 */
		public int getStudentid() {
			return studentid;
		}

		/**
		 * @param studentid
		 *            the studentid to set
		 */
		public void setStudentid(int studentid) {
			this.studentid = studentid;
		}

		/**
		 * @return the studentavatar
		 */
		public String getStudentavatar() {
			return studentavatar;
		}

		/**
		 * @param studentavatar
		 *            the studentavatar to set
		 */
		public void setStudentavatar(String studentavatar) {
			this.studentavatar = studentavatar;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the score
		 */
		public float getScore() {
			return score;
		}

		/**
		 * @param score
		 *            the score to set
		 */
		public void setScore(float score) {
			this.score = score;
		}

		/**
		 * @return the phone
		 */
		public String getPhone() {
			return phone;
		}

		/**
		 * @param phone
		 *            the phone to set
		 */
		public void setPhone(String phone) {
			this.phone = phone;
		}

		/**
		 * @return the studentcardnum
		 */
		public String getStudentcardnum() {
			return studentcardnum;
		}

		/**
		 * @param studentcardnum
		 *            the studentcardnum to set
		 */
		public void setStudentcardnum(String studentcardnum) {
			this.studentcardnum = studentcardnum;
		}

		/**
		 * @return the starttime
		 */
		public String getStarttime() {
			return starttime;
		}

		/**
		 * @param starttime
		 *            the starttime to set
		 */
		public void setStarttime(String starttime) {
			this.starttime = starttime;
		}

		/**
		 * @return the endtime
		 */
		public String getEndtime() {
			return endtime;
		}

		/**
		 * @param endtime
		 *            the endtime to set
		 */
		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}

		/**
		 * @return the contentlist
		 */
		public List<ContentList> getContentlist() {
			return contentlist;
		}

		/**
		 * @param contentlist
		 *            the contentlist to set
		 */
		public void setContentlist(List<ContentList> contentlist) {
			this.contentlist = contentlist;
		}

	}

	public class ContentList {
		private int complaintid;		//投诉ID
		private int order_id;		//订单ID
		private String set;		//投诉原因
		private String content;		//投诉内容
		private String addtime;		//投诉时间
		private int state;		//投诉状态0.未解决1.已解决

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public int getComplaintid() {
			return complaintid;
		}

		public void setComplaintid(int complaintid) {
			this.complaintid = complaintid;
		}

		public int getOrder_id() {
			return order_id;
		}

		public void setOrder_id(int order_id) {
			this.order_id = order_id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getSet() {
			return set;
		}

		public void setSet(String set) {
			this.set = set;
		}

		public String getAddtime() {
			return addtime;
		}

		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}
	}

	/**
	 * @return the hasmore
	 */
	public int getHasmore() {
		return hasmore;
	}

	/**
	 * @param hasmore
	 *            the hasmore to set
	 */
	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	/**
	 * @return the complaintlist
	 */
	public List<ComplaintList> getComplaintlist() {
		return complaintlist;
	}

	/**
	 * @param complaintlist
	 *            the complaintlist to set
	 */
	public void setComplaintlist(List<ComplaintList> complaintlist) {
		this.complaintlist = complaintlist;
	}

}
