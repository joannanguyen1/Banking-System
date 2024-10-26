package banking;

public class CreateValidator extends CommandValidator {
	private Bank bank;

	public CreateValidator(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	@Override
	public boolean validate(String command) {
		String[] parts = command.split(" ");

		if (checkLength(parts) && checkAccountType(parts) && checkId(parts) && checkApr(parts)
				&& checkSuppliedBalance(parts)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkLength(String[] parts) {
		String accountType = parts[1].toLowerCase();
		if (parts.length < 4 || (parts.length < 5 && accountType.equals("cd"))) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkValidAccountType(String accountType) {
		if (!accountType.equals("savings") && !accountType.equals("checking") && !accountType.equals("cd")) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkLengthForSavingsAndChecking(String[] parts) {
		String accountType = parts[1].toLowerCase();
		if (((accountType.equals("savings") || accountType.equals("checking")) && parts.length > 4)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean checkAccountType(String[] parts) {
		String accountType = parts[1].toLowerCase();
		if (!checkValidAccountType(accountType)) {
			return false;
		}
		if (!checkLengthForSavingsAndChecking(parts)) {
			return false;
		}

		return true;
	}

	public boolean checkValidId(String[] parts) {
		try {
			int id = Integer.parseInt(parts[2]);
			if (id < 0 || bank.getAccountByID(parts[2]) != null) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean checkValidApr(String[] parts) {
		if (!checkNegativeOrStringAmount(parts, 3, 0, 10)) {
			return false;
		}
		return true;
	}

	public boolean checkApr(String[] parts) {
		int aprLength = parts[3].length();
		return aprLength <= 4 && checkValidApr(parts);
	}

	public boolean checkId(String[] parts) {
		int idLength = parts[2].length();
		return idLength == 8 && checkValidId(parts);
	}

	public boolean checkValidSuppliedBalance(String[] parts) {
		if (!checkNegativeOrStringAmount(parts, 4, 1000, 10000)) {
			return false;
		}
		return true;
	}

	public boolean checkSuppliedBalance(String[] parts) {
		String accountType = parts[1].toLowerCase();
		if ((accountType.equals("cd")) && (!checkValidSuppliedBalance(parts))) {
			return false;
		}
		return true;
	}
}
