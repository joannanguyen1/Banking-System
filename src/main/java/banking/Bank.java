package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {

	private Map<String, Account> accounts;
	private List<String> accountOrder;
	private List<String> accountClose;

	public Bank() {
		accounts = new HashMap<>();
		accountOrder = new ArrayList<>();
		accountClose = new ArrayList<>();
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public void addAccount(Account account) {
		String accountId = account.getID();
		accounts.put(accountId, account);
		accountOrder.add(accountId);
	}

	public Account getAccountByID(String ID) {
		return accounts.get(ID);
	}

	public double deposit(String ID, double depositToAdd) {
		Account account = accounts.get(ID);
		if (account != null) {
			account.addDeposit(depositToAdd);
			return account.getBalance();
		}
		return 0;
	}

	public double withdraw(String ID, double withdrawAmount) {
		Account account = accounts.get(ID);
		if (account != null) {
			account.subWithdraw(withdrawAmount);
			return account.getBalance();
		}
		return 0;
	}

	public void transfer(String id1, String id2, double transferAmount) {
		Account account1 = getAccountByID(id1);
		double balance = account1.getBalance();
		if (balance <= transferAmount) {
			withdraw(id1, balance);
			deposit(id2, balance);
		} else {
			withdraw(id1, transferAmount);
			deposit(id2, transferAmount);
		}
	}

	public void pass(int months) {
		for (String ID : accounts.keySet()) {
			Account account = accounts.get(ID);
			checkAndUpdateBalance(account, ID);
			account.calculateApr(months);
		}

		for (String ID : accountClose) {
			accounts.remove(ID);
			accountOrder.remove(ID);
		}
	}

	public void checkAndUpdateBalance(Account account, String ID) {
		if (account.balance < 100 && account.balance > 0) {
			account.balance -= 25;
		} else if (account.balance <= 0) {
			account.balance = 0;
			accountClose.add(ID);
		}
	}

	public List<String> getClosedAccounts() {
		return accountClose;
	}

	public List<String> getAccountOrder() {
		return accountOrder;
	}
}
