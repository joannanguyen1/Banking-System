package banking;

public class CreateProcessor extends CommandProcessor {

	private Bank bank;

	public CreateProcessor(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	public void process(String[] parts) {
		String type = parts[1].toLowerCase();
		String id = parts[2];
		double apr = Double.parseDouble(parts[3]);

		if (bank.getAccountByID(id) == null) {
			if (type.equals("savings")) {
				Savings account = new Savings(id, apr);
				bank.addAccount(account);
			} else if (type.equals("checking")) {
				Checking account = new Checking(id, apr);
				bank.addAccount(account);
			} else if (type.equals("cd")) {
				double suppliedBal = Double.parseDouble(parts[4]);
				CD account = new CD(id, apr, suppliedBal);
				bank.addAccount(account);
			}
		}
	}
}
