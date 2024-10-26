package banking;

public class CommandProcessor {

	private Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
		String action = parts[0].toLowerCase();

		switch (action) {
			case "create" :
				new CreateProcessor(bank).process(parts);
				break;
			case "deposit" :
				new DepositProcessor(bank).process(parts);
				break;
			case "pass" :
				new PassTimeProcessor(bank).process(parts);
				break;
			case "withdraw" :
				new WithdrawalProcessor(bank).process(parts);
				break;
			case "transfer" :
				new TransferProcessor(bank).process(parts);
				break;
			default :
				break;
		}
	}
}