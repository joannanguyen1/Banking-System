package banking;

public class TransferValidator extends CommandValidator {

	private Bank bank;

	public TransferValidator(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] parts = command.split(" ");
		if (checkLength(parts) && checkId(parts) && checkTransferAmount(parts) && checkWithdrawalAccount(parts)
				&& checkDepositAccount(parts)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkLength(String[] parts) {
		if (parts.length < 4) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkTransferAmount(String[] parts) {
		if (!checkAmountGreaterThanZero(parts, 3)) {
			return false;
		}
		return true;
	}

	public boolean checkIdLength(String id1, String id2) {
		if (id1.length() != 8 || id2.length() != 8) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkValidId(String id1, String id2) {
		try {
			int intId1 = Integer.parseInt(id1);
			if (intId1 < 0 || bank.getAccountByID(id1) == null) {
				return false;
			}
			int intId2 = Integer.parseInt(id2);
			if (intId2 < 0 || bank.getAccountByID(id2) == null) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkId(String[] parts) {
		String id1 = parts[1];
		String id2 = parts[2];

		if (!checkIdLength(id1, id2) || (!checkValidId(id1, id2))) {
			return false;
		}

		return true;
	}

	public boolean checkNullOrCd(Account account) {
		if (account == null || account.isCD()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkSavingsWithdraw(Account firstAccount, double transferAmount) {
		if (!firstAccount.canWithdraw() || transferAmount > 1000) {
			return false;
		}
		return true;
	}

	public boolean checkWithdrawAccount(Account firstAccount, String[] parts) {
		double transferAmount = Double.parseDouble(parts[3]);

		if (!checkNullOrCd(firstAccount)) {
			return false;
		}
		if (firstAccount.isSavings() && (!checkSavingsWithdraw(firstAccount, transferAmount))) {
			return false;
		}
		if (firstAccount.isChecking() && transferAmount > 400) {
			return false;
		}

		return true;
	}

	public boolean checkWithdrawalAccount(String[] parts) {
		String id1 = parts[1];
		Account firstAccount = bank.getAccountByID(id1);
		if (!checkWithdrawAccount(firstAccount, parts)) {
			return false;
		}

		return true;
	}

	public boolean checkDepositAccount(String[] parts) {
		String id2 = parts[2];
		Account secondAccount = bank.getAccountByID(id2);
		int transferAmount = Integer.parseInt(parts[3]);
		if (!checkNullOrCd(secondAccount)) {
			return false;
		}
		if (secondAccount.isSavings() && transferAmount > 2500) {
			return false;
		}
		if (secondAccount.isChecking() && transferAmount > 1000) {
			return false;
		}

		return true;
	}
}
