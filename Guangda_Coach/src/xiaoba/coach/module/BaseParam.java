package xiaoba.coach.module;

import java.util.HashMap;

import xiaoba.coach.CoachApplication;

public class BaseParam extends HashMap<String, Object> {
	public BaseParam () {
		put("token", CoachApplication.getInstance().getUserInfo().getToken());
	}
}
