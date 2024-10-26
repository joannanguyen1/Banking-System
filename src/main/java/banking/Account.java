package banking;

public abstract class Account {
	public double balance;
	private double APR;
	private String ID;
	private int lastWithdrawalMonth;
	private int monthsPassed = 0;

	protected Account(String ID, double APR) {
		this.APR = APR;
		this.ID = ID;
	}

	public double getAPR() {
		return APR;
	}

	public void addDeposit(double depositToAdd) {
		balance += depositToAdd;
	}

	public double getBalance() {
		return balance;
	}

	public double subWithdraw(double withdrawAmount) {
		if (withdrawAmount > balance) {
			balance = 0;
		} else {
			balance -= withdrawAmount;
		}
		return balance;
	}

	public String getID() {
		return ID;
	}

	public boolean isCD() {
		return false;
	}

	public boolean isSavings() {
		return false;
	}

	public boolean isChecking() {
		return false;
	}

	public boolean canWithdraw() {
		return true;
	}

	public int getLastWithdrawalMonth() {
		return lastWithdrawalMonth;
	}

	public int getCurrentMonth() {
		return monthsPassed;
	}

	public void recordWithdrawal() {
		lastWithdrawalMonth = getCurrentMonth();
	}

	public void calculateApr(int months) {
		monthsPassed += months;
		for (int month = 1; month <= months; month++) {
			if (this.isSavings() || this.isChecking()) {
				calculateInterestForSavingsOrChecking();
			} else if (this.isCD()) {
				calculateInterestForCd();
			}
		}
	}

	public void calculateInterestForSavingsOrChecking() {
		double apr = this.getAPR();
		double accountBalance = this.getBalance();
		double interest = ((apr / 100) / 12) * accountBalance;
		this.addDeposit(interest);
	}

	public void calculateInterestForCd() {
		double apr = this.getAPR();
		double accountBalance = this.getBalance();
		for (int i = 1; i <= 4; i++) {
			double interest = ((apr / 100) / 12) * accountBalance;
			this.addDeposit(interest);
			accountBalance += interest;
		}
	}
}
