package xiaoba.coach.net.result;

import java.util.List;

public class GetCarTypeResult extends BaseResult {
	
	private List<CarModel> modellist;
	
	public class CarModel
	{
		private int modelid;
		private String modelname;
		private String searchname;
		private String addtime;
		public int getModelid() {
			return modelid;
		}
		public void setModelid(int modelid) {
			this.modelid = modelid;
		}
		public String getModelname() {
			return modelname;
		}
		public void setModelname(String modelname) {
			this.modelname = modelname;
		}
		public String getSearchname() {
			return searchname;
		}
		public void setSearchname(String searchname) {
			this.searchname = searchname;
		}
		public String getAddtime() {
			return addtime;
		}
		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}
	}

	public List<CarModel> getModellist() {
		return modellist;
	}

	public void setModellist(List<CarModel> modellist) {
		this.modellist = modellist;
	}
}
