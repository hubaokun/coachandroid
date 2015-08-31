package xiaoba.coach.net.result;

import java.util.List;

public class GetDefaultScheduleResult extends BaseResult{
	private List<DefaultSchedule> DefaultSchedule;
	public class DefaultSchedule
	{
		private int price;
		private String addressdetail;
		private String subject;
		private int hour;
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getAddressdetail() {
			return addressdetail;
		}
		public void setAddressdetail(String addressdetail) {
			this.addressdetail = addressdetail;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public int getHour() {
			return hour;
		}
		public void setHour(int hour) {
			this.hour = hour;
		}
	}
	public List<DefaultSchedule> getDefaultSchedule() {
		return DefaultSchedule;
	}
	public void setDefaultSchedule(List<DefaultSchedule> defaultSchedule) {
		DefaultSchedule = defaultSchedule;
	}
}
