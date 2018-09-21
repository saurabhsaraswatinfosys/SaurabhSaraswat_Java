
public class Position {

	private String instrument;
	private int account;
	private char accountType;
	private long quantity;
	private long delta;
	
	public long getDelta() {
		return delta;
	}
	public void setDelta(long delta) {
		this.delta = delta;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public char getAccountType() {
		return accountType;
	}
	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}
