package banking;

public class DepositProcessor extends CommandProcessor {

	private Bank bank;

	public DepositProcessor(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	public void process(String[] parts) {
		String id = parts[1];
		double amount = Double.parseDouble(parts[2]);

		Account account = bank.getAccountByID(id);
		account.addDeposit(amount);
	}
}
