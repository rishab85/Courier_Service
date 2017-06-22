package courierDM;

public class senderReceiver implements java.io.Serializable {
	private Integer packageId;
	private Integer sourceStreet;
	private char sourceAve;
	private Integer destStreet;
	private char destAve;
	public Integer getPackageId() {
		return packageId;
	}


	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}


	public Integer getSourceStreet() {
		return sourceStreet;
	}


	public void setSourceStreet(Integer sourceStreet) {
		this.sourceStreet = sourceStreet;
	}


	public char getSourceAve() {
		return sourceAve;
	}


	public void setSourceAve(char sourceAve) {
		this.sourceAve = sourceAve;
	}


	public Integer getDestStreet() {
		return destStreet;
	}


	public void setDestStreet(Integer destStreet) {
		this.destStreet = destStreet;
	}


	public char getDestAve() {
		return destAve;
	}


	public void setDestAve(char destAve) {
		this.destAve = destAve;
	}


	public Integer getTotalBlock() {
		return totalBlock;
	}


	public void setTotalBlock(Integer totalBlock) {
		this.totalBlock = totalBlock;
	}


	private Integer totalBlock;
	
	
	public senderReceiver() {
		// TODO Auto-generated constructor stub
		
	}
	
}
