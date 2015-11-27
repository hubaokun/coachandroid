package xiaoba.coach.net.result;
import xiaoba.coach.module.UserInfo;
public class GetPriceRangeResult {
	private String message;
	private int code;
	private UserInfo UserInfo;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public UserInfo getUserInfo() {
		return UserInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		UserInfo = userInfo;
	}
}
