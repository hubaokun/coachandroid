package xiaoba.coach.net.result;

import java.util.List;

public class GetAllSubjectResult extends BaseResult {
	List<SubjectInfo> subjectlist;

	public List<SubjectInfo> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<SubjectInfo> subjectlist) {
		this.subjectlist = subjectlist;
	}

	public class SubjectInfo {
		int subjectid;		//科目ID
		String subjectname;		//科目名称
		int isdefault;		//是否是教练的默认科目0.不是  1.是

		public int getSubjectid() {
			return subjectid;
		}

		public void setSubjectid(int subjectid) {
			this.subjectid = subjectid;
		}

		public String getSubjectname() {
			return subjectname;
		}

		public void setSubjectname(String subjectname) {
			this.subjectname = subjectname;
		}

		public int getIsdefault() {
			return isdefault;
		}

		public void setIsdefault(int isdefault) {
			this.isdefault = isdefault;
		}

	}
}
