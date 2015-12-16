package xiaoba.coach.net.result;

import java.util.List;

public class AddressResult extends BaseResult {
	List<AddressInfo> addresslist;		//地址列表

	public List<AddressInfo> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<AddressInfo> addresslist) {
		this.addresslist = addresslist;
	}

	public class AddressInfo {
		int addressid;		//地址ID
		String longitude;//经度
		String latitude;	//纬度
		int iscurrent;		//是否是当前使用地址 0.不是 1.是
		String area;		//市 区
		String detail;		//地址详细信息

		public int getAddressid() {
			return addressid;
		}

		public void setAddressid(int addressid) {
			this.addressid = addressid;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public int getIscurrent() {
			return iscurrent;
		}

		public void setIscurrent(int iscurrent) {
			this.iscurrent = iscurrent;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}
	}
}
