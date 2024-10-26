package banking;

public class Savings extends Account {
	int withdrawalCount = 0;

	public Savings(String ID, double APR) {
		super(ID, APR);
	}

	@Override
	public boolean isSavings() {
		return true;
	}

	@Override
	public double subWithdraw(double withdrawAmount) {
		if (withdrawAmount >= balance) {
			recordWithdrawal();
			withdrawalCount++;
			balance = 0;
		} else {
			balance -= withdrawAmount;
			recordWithdrawal();
			withdrawalCount++;
		}
		return balance;
	}

	@Override
	public boolean canWithdraw() {
		int currentMonth = getCurrentMonth();
		int monthsSinceLastWithdrawal = currentMonth - getLastWithdrawalMonth();
		if (monthsSinceLastWithdrawal >= 1 || (currentMonth == 0 && getWithdrawalCount() == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public int getWithdrawalCount() {
		return withdrawalCount;
	}
}
