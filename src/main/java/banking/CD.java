package banking;

public class CD extends Account {

	public CD(String ID, double APR, double suppliedBalance) {
		super(ID, APR);
		this.balance = suppliedBalance;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public boolean isCD() {
		return true;
	}

	@Override
	public boolean canWithdraw() {
		int maturity = getCurrentMonth();
		if (maturity >= 12) {
			return true;
		} else {
			return false;
		}
	}
}
