package banking;

public class WithdrawalValidator extends CommandValidator {

	private Bank bank;

	public WithdrawalValidator(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] parts = command.split(" ");

		if (checkLength(parts) && checkId(parts) && checkAccount(parts) && checkWithdrawAmount(parts)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkLength(String[] parts) {
		if (parts.length < 3) {
			return false;
		}
		return true;
	}

	public boolean checkAccount(String[] parts) {
		String id = parts[1];
		Account account = bank.getAccountByID(id);
		return account != null;
	}

	public boolean checkWithdrawNegativeOrString(String[] parts) {
		if (!checkAmountGreaterThanZero(parts, 2)) {
			return false;
		}
		return true;
	}

	public boolean checkSavingsWithdraw(Account account, double withdrawalAmount) {
		if (!account.canWithdraw() || (withdrawalAmount < 0 || withdrawalAmount > 1000)) {
			return false;
		}
		return true;
	}

	public boolean checkCheckingWithdraw(double withdrawalAmount) {
		if (withdrawalAmount < 0 || withdrawalAmount > 400) {
			return false;
		}
		return true;
	}

	public boolean checkCdWithdraw(Account account, double withdrawalAmount) {
		double balance = account.getBalance();
		if (!account.canWithdraw() || (withdrawalAmount < balance)) {
			return false;
		}
		return true;
	}

	public boolean checkWithdrawAmount(String[] parts) {
		String id = parts[1];
		Account account = bank.getAccountByID(id);

		if (!checkWithdrawNegativeOrString(parts)) {
			return false;
		}

		double withdrawalAmount = Double.parseDouble(parts[2]);
		if (account.isSavings()) {
			return checkSavingsWithdraw(account, withdrawalAmount);
		} else if (account.isChecking()) {
			return checkCheckingWithdraw(withdrawalAmount);
		} else if (account.isCD()) {
			return checkCdWithdraw(account, withdrawalAmount);
		}

		return false;
	}
}