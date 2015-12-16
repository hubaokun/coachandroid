package xiaoba.coach.net.result;

import java.util.ArrayList;

public class GetAdvertisementByParamResult extends BaseResult {
	
	
	private ArrayList<AdvertisementList> AdvertiesementList;
	
	public class AdvertisementList
	{
	private int adimgid;
	private int adid;
	private String title;
	private String adtype;
	private String content;
	private int opentype;
	private int openaction;
	private String openurl;
	private String begintime;
	private String endtime;
	private String imgurl;
	private int height;
	private int width;
	private int devicetype;
	public int getAdimgid() {
		return adimgid;
	}
	public void setAdimgid(int adimgid) {
		this.adimgid = adimgid;
	}
	public int getAdid() {
		return adid;
	}
	public void setAdid(int adid) {
		this.adid = adid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAdtype() {
		return adtype;
	}
	public void setAdtype(String adtype) {
		this.adtype = adtype;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getOpentype() {
		return opentype;
	}
	public void setOpentype(int opentype) {
		this.opentype = opentype;
	}
	public int getOpenaction() {
		return openaction;
	}
	public void setOpenaction(int openaction) {
		this.openaction = openaction;
	}
	public String getOpenurl() {
		return openurl;
	}
	public void setOpenurl(String openurl) {
		this.openurl = openurl;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(int devicetype) {
		this.devicetype = devicetype;
	}
	}


	public ArrayList<AdvertisementList> getAdvertiesementList() {
		return AdvertiesementList;
	}


	public void setAdvertiesementList(ArrayList<AdvertisementList> advertiesementList) {
		AdvertiesementList = advertiesementList;
	}
}
