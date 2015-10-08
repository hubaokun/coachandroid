package xiaoba.coach.net.result;

public class GetStudentRestCouponResult extends BaseResult {
	private int total;
	private int rest;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRest() {
		return rest;
	}
	public void setRest(int rest) {
		this.rest = rest;
	}
}
