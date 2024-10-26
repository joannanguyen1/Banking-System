package banking;

public class CommandValidator {

	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		if (parts.length > 0) {
			String commandName = parts[0].toLowerCase();

			switch (commandName) {
				case "create" :
					return validateCreate(command);
				case "deposit" :
					return validateDeposit(command);
				case "pass" :
					return validatePassTime(command);
				case "withdraw" :
					return validateWithdraw(command);
				case "transfer" :
					return validateTransfer(command);
				default :
					break;
			}
		}

		return false;
	}

	public boolean validateCreate(String command) {
		return new CreateValidator(bank).validate(command);
	}

	public boolean validateDeposit(String command) {
		return new DepositValidator(bank).validate(command);
	}

	public boolean validatePassTime(String command) {
		return new PassTimeValidator(bank).validate(command);
	}

	public boolean validateWithdraw(String command) {
		return new WithdrawalValidator(bank).validate(command);
	}

	public boolean validateTransfer(String command) {
		return new TransferValidator(bank).validate(command);
	}

	public boolean checkNegativeOrStringAmount(String[] parts, int index, double min, double max) {
		try {
			double amount = Double.parseDouble(parts[index]);
			if (amount < min || amount > max) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean checkId(String[] parts) {
		String id = parts[1];
		if (id.length() != 8) {
			return false;
		} else {
			if (!checkValidId(id)) {
				return false;
			}
		}

		return true;
	}

	public boolean checkValidId(String id) {
		try {
			int intId = Integer.parseInt(id);
			if (intId < 0 || bank.getAccountByID(id) == null) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public boolean checkAmountGreaterThanZero(String[] parts, int index) {
		try {
			double amount = Double.parseDouble(parts[index]);

			if (amount < 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}
