package banking;

public class PassTimeProcessor {
	private Bank bank;

	public PassTimeProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] parts) {
		int months = Integer.parseInt(parts[1]);
		bank.pass(months);
	}
}
