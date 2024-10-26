package banking;

public class TransferProcessor extends CommandProcessor {

	private Bank bank;

	public TransferProcessor(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	public void process(String[] parts) {
		String id1 = parts[1];
		String id2 = parts[2];
		double amount = Double.parseDouble(parts[3]);

		bank.transfer(id1, id2, amount);
	}
}
