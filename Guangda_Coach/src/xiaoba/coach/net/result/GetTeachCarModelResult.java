package xiaoba.coach.net.result;

import java.util.List;

public class GetTeachCarModelResult extends BaseResult {
	private List<TeachCar> teachcarlist;
	public class TeachCar
	{
		private int modelid;
		private String modelname;
		private String addtime;
		public String getAddtime() {
			return addtime;
		}
		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}
		public String getModelname() {
			return modelname;
		}
		public void setModelname(String modelname) {
			this.modelname = modelname;
		}
		public int getModelid() {
			return modelid;
		}
		public void setModelid(int modelid) {
			this.modelid = modelid;
		}
	}
	public List<TeachCar> getTeachcarlist() {
		return teachcarlist;
	}
	public void setTeachcarlist(List<TeachCar> teachcarlist) {
		this.teachcarlist = teachcarlist;
	}
}
