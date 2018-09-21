
public class Transaction {

	private long transactionId;
	private String instrument;
	private char transactionType;
	private long transactionQuantity;
	
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public char getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType.charAt(0);
	}
	public long getTransactionQuantity() {
		return transactionQuantity;
	}
	public void setTransactionQuantity(long transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}
	
	
}
