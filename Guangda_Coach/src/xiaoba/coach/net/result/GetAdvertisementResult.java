package xiaoba.coach.net.result;

public class GetAdvertisementResult extends BaseResult {
	private Config config;
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
	
	
	public class Config
	{
		private int advertisement_flag;
		private String advertisement_url;
		public int getAdvertisement_flag() {
			return advertisement_flag;
		}
		public void setAdvertisement_flag(int advertisement_flag) {
			this.advertisement_flag = advertisement_flag;
		}
		public String getAdvertisement_url() {
			return advertisement_url;
		}
		public void setAdvertisement_url(String advertisement_url) {
			this.advertisement_url = advertisement_url;
		}
	}
}
