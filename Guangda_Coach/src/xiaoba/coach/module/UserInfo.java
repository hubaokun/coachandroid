package xiaoba.coach.module;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import xiaoba.coach.CoachApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfo {

	private static final String SP_NAME = "userinfo";
	int coachid;
	String password;
	String phone;
	String id_cardnum;
	String id_cardpicfurl;
	String id_cardpicburl;
	String id_cardexptime;
	String coach_cardnum;
	String coach_cardpicurl;
	String coach_card_creat;
	String coach_cardexptime;
	String drive_cardnum;
	String drive_cardpicurl;
	String drive_cardexptime;
	String car_cardnum;
	String car_cardpicfurl;
	String car_cardpicburl;
	String car_cardexptime;
	String realpicurl;
	String carmodel;
	String carlicense;
	String cancancel;
	String driveschool;
	String gender;
	String birthday;
	String realname;
	String urgent_person;
	String urgent_phone;
	String years;
	String avatar;
	String money;
	String money_frozen;
	String gmoney;
	String fmoney;
	String price;
	String state;
	private String locationname;
	String address;
	Float score;
	int totaltime;
	List<CarType> modellist;
	String selfeval;
	String addtime;
	int couponhour;
	String alipay_account;
	String avatarurl;
	private String teachcarmodel;
	private String defaultAddress;
	private String token;
	int cashtype;
	private String invitecode;
	private String cityid;
	private String provinceid;
	private String areaid;
	
	public int getCashtype() {
		return cashtype;
	}

	public void setCashtype(int cashtype) {
		this.cashtype = cashtype;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public String getGmoney() {
		return gmoney;
	}

	public void setGmoney(String gmoney) {
		this.gmoney = gmoney;
	}

	public String getFmoney() {
		return fmoney;
	}

	public void setFmoney(String fmoney) {
		this.fmoney = fmoney;
	}

	public int getCouponhour() {
		return couponhour;
	}

	public void setCouponhour(int couponhour) {
		this.couponhour = couponhour;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public int getCoachid() {
		return coachid;
	}

	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId_cardnum() {
		return id_cardnum;
	}

	public void setId_cardnum(String id_cardnum) {
		this.id_cardnum = id_cardnum;
	}

	public String getId_cardpicfurl() {
		return id_cardpicfurl;
	}

	public void setId_cardpicfurl(String id_cardpicfurl) {
		this.id_cardpicfurl = id_cardpicfurl;
	}

	public String getId_cardpicburl() {
		return id_cardpicburl;
	}

	public void setId_cardpicburl(String id_cardpicburl) {
		this.id_cardpicburl = id_cardpicburl;
	}

	public String getId_cardexptime() {
		return id_cardexptime;
	}

	public void setId_cardexptime(String id_cardexptime) {
		this.id_cardexptime = id_cardexptime;
	}

	public String getCoach_cardnum() {
		return coach_cardnum;
	}

	public void setCoach_cardnum(String coach_cardnum) {
		this.coach_cardnum = coach_cardnum;
	}

	public String getCoach_cardpicurl() {
		return coach_cardpicurl;
	}

	public void setCoach_cardpicurl(String coach_cardpicurl) {
		this.coach_cardpicurl = coach_cardpicurl;
	}

	public String getCoach_card_creat() {
		return coach_card_creat;
	}

	public void setCoach_card_creat(String coach_card_creat) {
		this.coach_card_creat = coach_card_creat;
	}

	public String getCoach_cardexptime() {
		return coach_cardexptime;
	}

	public void setCoach_cardexptime(String coach_cardexptime) {
		this.coach_cardexptime = coach_cardexptime;
	}

	public String getDrive_cardnum() {
		return drive_cardnum;
	}

	public void setDrive_cardnum(String drive_cardnum) {
		this.drive_cardnum = drive_cardnum;
	}

	public String getDrive_cardpicurl() {
		return drive_cardpicurl;
	}

	public void setDrive_cardpicurl(String drive_cardpicurl) {
		this.drive_cardpicurl = drive_cardpicurl;
	}

	public String getDrive_cardexptime() {
		return drive_cardexptime;
	}

	public void setDrive_cardexptime(String drive_cardexptime) {
		this.drive_cardexptime = drive_cardexptime;
	}

	public String getCar_cardnum() {
		return car_cardnum;
	}

	public void setCar_cardnum(String car_cardnum) {
		this.car_cardnum = car_cardnum;
	}

	public String getCar_cardpicfurl() {
		return car_cardpicfurl;
	}

	public void setCar_cardpicfurl(String car_cardpicfurl) {
		this.car_cardpicfurl = car_cardpicfurl;
	}

	public String getCar_cardpicburl() {
		return car_cardpicburl;
	}

	public void setCar_cardpicburl(String car_cardpicburl) {
		this.car_cardpicburl = car_cardpicburl;
	}

	public String getCar_cardexptime() {
		return car_cardexptime;
	}

	public void setCar_cardexptime(String car_cardexptime) {
		this.car_cardexptime = car_cardexptime;
	}

	public String getRealpicurl() {
		return realpicurl;
	}

	public void setRealpicurl(String realpicurl) {
		this.realpicurl = realpicurl;
	}

	public String getCarmodel() {
		return carmodel;
	}

	public void setCarmodel(String carmodel) {
		this.carmodel = carmodel;
	}

	public String getCarlicense() {
		return carlicense;
	}

	public void setCarlicense(String carlicense) {
		this.carlicense = carlicense;
	}

	public String getCancancel() {
		return cancancel;
	}

	public void setCancancel(String cancancel) {
		this.cancancel = cancancel;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUrgent_person() {
		return urgent_person;
	}

	public void setUrgent_person(String urgent_person) {
		this.urgent_person = urgent_person;
	}

	public String getUrgent_phone() {
		return urgent_phone;
	}

	public void setUrgent_phone(String urgent_phone) {
		this.urgent_phone = urgent_phone;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getMoney_frozen() {
		return money_frozen;
	}

	public void setMoney_frozen(String money_frozen) {
		this.money_frozen = money_frozen;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(int totaltime) {
		this.totaltime = totaltime;
	}

	public List<CarType> getModellist() {
		return modellist;
	}

	public void setModellist(List<CarType> modellist) {
		this.modellist = modellist;
	}

	public String getSelfeval() {
		return selfeval;
	}

	public void setSelfeval(String selfeval) {
		this.selfeval = selfeval;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getDriveschool() {
		return driveschool;
	}

	public void setDriveschool(String driveschool) {
		this.driveschool = driveschool;
	}
	
	public void setCoachInfo(UserInfo userinfo)
	{
		CoachApplication.mUserInfo = userinfo;
	}

	public void saveUserInfo(UserInfo userinfo, Context mContext) {
		CoachApplication.mUserInfo = userinfo;
		Field[] fields = userinfo.getClass().getDeclaredFields();
		SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			// Object value = getFieldValueByName(fields[i].getName(), userinfo);
			Object value = null;
			try {
				value = fields[i].get(userinfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (value == null)
				continue;
			if (value instanceof String) {
				editor.putString(fields[i].getName(), (String) value);
			} else if (value instanceof Integer) {
				editor.putInt(fields[i].getName(), (int) (Integer) value);
			} else if (value instanceof Float) {
				editor.putFloat(fields[i].getName(), (Float) value);
			} else if (value instanceof List<?>) {
				List<CarType> list = null;
				String valueName = fields[i].getName();
				try {
					list = (List<CarType>) value;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (list == null)
					return;
				for (int j = 0; j < list.size(); j++) {
					editor.putInt(valueName + "_id_" + j, list.get(j).getModelid());
					editor.putString(valueName + "_name_" + j, list.get(j).getModelname());
				}
			}
		}
		editor.commit();
	}

	public UserInfo loadUserInfo(Context mContext) {
		UserInfo userinfo = new UserInfo();
		Field[] fields = userinfo.getClass().getDeclaredFields();
		SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			if (fields[i].getType().toString().contains("String")) {
				userinfo.setStringByName(fields[i].getName(), preferences.getString(fields[i].getName(), ""), userinfo);
			} else if (fields[i].getType().toString().contains("int")) {
				userinfo.setIntByName(fields[i].getName(), preferences.getInt(fields[i].getName(), 0), userinfo);
			} else if (fields[i].getType().toString().contains("Float")) {
				userinfo.setFloatByName(fields[i].getName(), preferences.getFloat(fields[i].getName(), 0f), userinfo);
			} else if (fields[i].getType().toString().contains("List")) {
				List<CarType> list = new ArrayList<CarType>();
				String valueName = fields[i].getName();
				for (int j = 0; j < list.size(); j++) {
					CarType type = new CarType();
					type.setModelid(preferences.getInt(valueName + "_id_" + j, 0));
					type.setModelname(preferences.getString(valueName + "_name_" + j, ""));
					list.add(type);
				}
				userinfo.setModellist(list);
			}
		}
		return userinfo;
	}

	private Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, fieldName);
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	private void setStringByName(String fieldName, Object value, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String setter = "set" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(setter, String.class);
			method.invoke(o, value);
		} catch (Exception e) {
		}
	}

	private void setIntByName(String fieldName, Object value, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String setter = "set" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(setter, int.class);
			method.invoke(o, value);
		} catch (Exception e) {
		}
	}

	private void setFloatByName(String fieldName, Object value, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String setter = "set" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(setter, Float.class);
			method.invoke(o, value);
		} catch (Exception e) {
		}
	}

	private void setListByName(String fieldName, Object value, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String setter = "set" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(setter, List.class);
			method.invoke(o, value);
		} catch (Exception e) {
		}
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public void saveAvatarUrl(String avatarurl, Context mContext) {
		Editor editor = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
		editor.putString("avatarurl", avatarurl);
		editor.commit();
		CoachApplication.getInstance().getUserInfo().setAvatarurl(avatarurl);
	}
	
	public void saveCityId(String cityid,Context mContext)
	{
		Editor editor = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
		editor.putString("cityid", cityid);
		editor.commit();
		CoachApplication.getInstance().getUserInfo().setRealname(cityid);
	}

	public void saveRealName(String value, Context mContext) {
		Editor editor = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
		editor.putString("realname", value);
		editor.commit();
		CoachApplication.getInstance().getUserInfo().setRealname(value);
	}

	public void saveGender(String value, Context mContext) {
		Editor editor = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
		editor.putString("gender", value);
		editor.commit();
		CoachApplication.getInstance().getUserInfo().setGender(value);
	}

	public void savePhone(String value, Context mContext) {
		Editor editor = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
		editor.putString("phone", value);
		editor.commit();
		CoachApplication.getInstance().getUserInfo().setPhone(value);
	}

	public void saveDriveSchool(String value, Context mContext) {
		Editor editor = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
		editor.putString("drive_school", value);
		editor.commit();
		CoachApplication.getInstance().getUserInfo().setDriveschool(value);
	}
	


	public String getTeachcarmodel() {
		return teachcarmodel;
	}

	public void setTeachcarmodel(String teachcarmodel) {
		this.teachcarmodel = teachcarmodel;
	}

	public String getInvitecode() {
		return invitecode;
	}

	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
}
