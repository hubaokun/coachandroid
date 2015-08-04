package xiaoba.coach.net.result;

import java.util.List;

public class GetCarModelResult extends BaseResult {
	List<Model> modellist;

	public List<Model> getModellist() {
		return modellist;
	}

	public void setModellist(List<Model> modellist) {
		this.modellist = modellist;
	}

	public class Model {
		private int modelid;
		private String modelname;

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

	}
}
