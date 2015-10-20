package xiaoba.coach.net.result;

public class GetMaxPriceResult extends BaseResult {
	private int minprice;
	private int maxprice;
	private int attachcarmaxprice;
	private int attachcarminprice;
	public int getMaxprice() {
		return maxprice;
	}
	public void setMaxprice(int maxprice) {
		this.maxprice = maxprice;
	}
	public int getMinprice() {
		return minprice;
	}
	public void setMinprice(int minprice) {
		this.minprice = minprice;
	}
	public int getAttachcarmaxprice() {
		return attachcarmaxprice;
	}
	public void setAttachcarmaxprice(int attachcarmaxprice) {
		this.attachcarmaxprice = attachcarmaxprice;
	}
	public int getAttachcarminprice() {
		return attachcarminprice;
	}
	public void setAttachcarminprice(int attachcarminprice) {
		this.attachcarminprice = attachcarminprice;
	}
}
