package banking;

public class DepositValidator extends CommandValidator {

	private Bank bank;

	public DepositValidator(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] parts = command.split(" ");

		if (checkLength(parts) && checkId(parts) && checkAccountType(parts) && checkDepositAmount(parts)) {
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

	public boolean checkAccountType(String[] parts) {
		String id = parts[1];
		Account account = bank.getAccountByID(id);
		if (account == null || account.isCD()) {
			return false;
		}
		return true;
	}

	public boolean checkSavingsDeposit(String[] parts) {
		if (!checkNegativeOrStringAmount(parts, 2, 0, 2500)) {
			return false;
		}
		return true;
	}

	public boolean checkCheckingDeposit(String[] parts) {
		if (!checkNegativeOrStringAmount(parts, 2, 0, 1000)) {
			return false;
		}
		return true;
	}

	public boolean checkDepositAmount(String[] parts) {
		String id = parts[1];
		Account account = bank.getAccountByID(id);

		if (account.isSavings() && (!checkSavingsDeposit(parts))) {
			return false;
		}

		if (account.isChecking() && (!checkCheckingDeposit(parts))) {
			return false;
		}

		return true;
	}
}
