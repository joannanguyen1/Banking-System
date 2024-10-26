package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

	private List<String> validCommands;
	private List<String> invalidCommands;
	private List<String> outputList;
	private Bank bank;

	public CommandStorage(Bank bank) {
		this.bank = bank;
		validCommands = new ArrayList<>();
		invalidCommands = new ArrayList<>();
		outputList = new ArrayList<>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}

	public List<String> getValidCommands() {
		return validCommands;
	}

	public void addValidCommand(String command) {
		String[] parts = command.split(" ");
		String commandName = parts[0].toLowerCase();
		if (commandName.equals("create") || commandName.equals("deposit") || commandName.equals("withdraw")
				|| commandName.equals("transfer")) {
			validCommands.add(command);
		}
	}

	private String getFormattedAccountState(String id) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		Account account = bank.getAccountByID(id);
		String accountType = "unknown";
		if (account.isCD()) {
			accountType = "Cd";
		} else if (account.isSavings()) {
			accountType = "Savings";
		} else if (account.isChecking()) {
			accountType = "Checking";
		}
		String balance = decimalFormat.format(account.getBalance());
		String apr = decimalFormat.format(account.getAPR());
		return accountType + " " + account.getID() + " " + balance + " " + apr;
	}

	public List<String> getOutputList() {
		for (String id : bank.getAccountOrder()) {
			outputList.add(getFormattedAccountState(id));
			for (String command : validCommands) {
				addValidCommandsToOutput(command, id);
			}
		}
		for (String invalidCommand : invalidCommands) {
			outputList.add(invalidCommand);
		}
		return outputList;
	}

	public void addDepositWithdrawAndTransferToOutput(String command, String commandName) {
		if (commandName.equals("deposit") || commandName.equals("withdraw") || commandName.equals("transfer")) {
			outputList.add(command);
		}
	}

	public void addValidCommandsToOutput(String command, String id) {
		String[] parts = command.split(" ");
		String commandName = parts[0].toLowerCase();
		String commandId = parts[1];
		String transferId = parts[2];
		if (id.equals(commandId) || id.equals(transferId)) {
			addDepositWithdrawAndTransferToOutput(command, commandName);
		}
	}
}
