package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void valid_create_savings_account() {
		String input = "create savings 12345678 0.5";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("12345678");
		assertNotNull(account);
		boolean type = account.isSavings();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(0.5, apr);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_create_checking_account() {
		String input = "create checking 87654321 5.6";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("87654321");
		assertNotNull(account);
		boolean type = account.isChecking();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(5.6, apr);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_create_cd_account() {
		String input = "create cd 22222222 4.5 1500";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("22222222");
		assertNotNull(account);
		boolean type = account.isCD();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(4.5, apr);
		double balance = account.getBalance();
		assertEquals(1500, balance);
	}

	@Test
	public void valid_create_cd_account_with_minimum_supplied_balance() {
		String input = "create cd 22222222 4.5 1000";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("22222222");
		assertNotNull(account);
		boolean type = account.isCD();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(4.5, apr);
		double balance = account.getBalance();
		assertEquals(1000, balance);
	}

	@Test
	public void valid_create_cd_account_with_max_supplied_balance() {
		String input = "create cd 22222222 4.5 10000";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("22222222");
		assertNotNull(account);
		boolean type = account.isCD();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(4.5, apr);
		double balance = account.getBalance();
		assertEquals(10000, balance);
	}

	@Test
	public void valid_create_savings_account_with_minimum_APR() {
		String input = "create savings 12345678 0";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("12345678");
		assertNotNull(account);
		boolean type = account.isSavings();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(0, apr);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_create_savings_account_with_max_APR() {
		String input = "create savings 12345678 10";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("12345678");
		assertNotNull(account);
		boolean type = account.isSavings();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(10, apr);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_create_checking_account_with_minimum_APR() {
		String input = "create checking 87654321 0";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("87654321");
		assertNotNull(account);
		boolean type = account.isChecking();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(0, apr);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_create_checking_account_with_maximum_APR() {
		String input = "create checking 87654321 10";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("87654321");
		assertNotNull(account);
		boolean type = account.isChecking();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(10, apr);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_create_cd_account_with_minimum_APR() {
		String input = "create cd 22222222 0 1500";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("22222222");
		assertNotNull(account);
		boolean type = account.isCD();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(0, apr);
		double balance = account.getBalance();
		assertEquals(1500, balance);
	}

	@Test
	public void valid_create_cd_account_with_max_APR() {
		String input = "create cd 22222222 10 1500";
		commandProcessor.processCommand(input);

		Account account = bank.getAccountByID("22222222");
		assertNotNull(account);
		boolean type = account.isCD();
		assertTrue(type);
		double apr = account.getAPR();
		assertEquals(10, apr);
		double balance = account.getBalance();
		assertEquals(1500, balance);
	}

	@Test
	public void valid_deposit_into_new_savings_account() {
		Savings account = new Savings("12345678", 0.5);
		bank.addAccount(account);

		String input = "deposit 12345678 250";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(250, balance);
	}

	@Test
	public void valid_deposit_into_existing_savings_account_with_a_balance() {
		Savings account = new Savings("12345678", 0.5);
		bank.addAccount(account);
		account.addDeposit(500);

		String input = "deposit 12345678 500";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(1000, balance);
	}

	@Test
	public void valid_deposit_into_new_checking_account() {
		Checking account = new Checking("87654321", 0.5);
		bank.addAccount(account);

		String input = "deposit 87654321 600";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(600, balance);
	}

	@Test
	public void valid_deposit_into_existing_checking_account_with_a_balance() {
		Checking account = new Checking("87654321", 0.5);
		bank.addAccount(account);
		account.addDeposit(1500);

		String input = "deposit 87654321 500";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(2000, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_1_month_with_savings_account() {
		Savings account = new Savings("12345678", 3);
		bank.addAccount(account);
		account.addDeposit(1000);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(1002.5, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_1_month_with_checking_account() {
		Checking account = new Checking("12345678", 0.60);
		bank.addAccount(account);
		account.addDeposit(5000);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(5002.5, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_1_month_with_cd_account() {
		CD account = new CD("12345678", 2.1, 2000);
		bank.addAccount(account);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(2014.0367928937578, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_12_months_with_savings_account() {
		Savings account = new Savings("12345678", 3);
		bank.addAccount(account);
		account.addDeposit(1000);

		String input = "pass 12";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(1030.4159569135074, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_12_months_with_checking_account() {
		Checking account = new Checking("12345678", 1);
		bank.addAccount(account);
		account.addDeposit(5000);

		String input = "pass 12";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(5050.2298044359095, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_12_months_with_cd_account() {
		CD account = new CD("12345678", 10, 2000);
		bank.addAccount(account);

		String input = "pass 12";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(2978.708197214328, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_60_months_with_savings_account() {
		Savings account = new Savings("12345678", 5);
		bank.addAccount(account);
		account.addDeposit(1000);

		String input = "pass 60";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(1283.358678503513, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_60_months_with_checking_account() {
		Checking account = new Checking("12345678", 1);
		bank.addAccount(account);
		account.addDeposit(5000);

		String input = "pass 60";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(5256.246036413126, balance);
	}

	@Test
	public void valid_pass_time_apr_calculation_60_months_with_cd_account() {
		CD account = new CD("12345678", 1, 3000);
		bank.addAccount(account);

		String input = "pass 60";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(3663.9031060327543, balance);
	}

	@Test
	public void valid_bank_with_1_account_after_pass_time_with_account_with_0_balance() {
		Savings account = new Savings("12345678", 5);
		bank.addAccount(account);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		assertEquals(0, bank.getNumberOfAccounts());
	}

	@Test
	public void valid_bank_with_2_accounts_after_pass_time_with_1_account_with_0_balance() {
		Savings account = new Savings("12345678", 5);
		bank.addAccount(account);
		Checking account2 = new Checking("12345678", 1);
		bank.addAccount(account2);
		account2.addDeposit(500);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		assertEquals(1, bank.getNumberOfAccounts());
	}

	@Test
	public void valid_pass_time_with_savings_account_with_0_balance_account_gets_closed() {
		Savings account = new Savings("12345678", 5);
		bank.addAccount(account);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		assertTrue(bank.getClosedAccounts().contains("12345678"));
		assertEquals(0, bank.getNumberOfAccounts());
	}

	@Test
	public void valid_pass_time_with_checking_account_with_0_balance_account_gets_closed() {
		Checking account = new Checking("22222222", 5);
		bank.addAccount(account);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		assertTrue(bank.getClosedAccounts().contains("22222222"));
		assertEquals(0, bank.getNumberOfAccounts());
	}

	@Test
	public void valid_pass_time_with_cd_account_with_0_balance_account_gets_closed() {
		CD account = new CD("87654321", 1, 3000);
		bank.addAccount(account);

		String input = "pass 12";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		account.subWithdraw(balance);
		bank.pass(1);
		assertTrue(bank.getClosedAccounts().contains("87654321"));
		assertEquals(0, bank.getNumberOfAccounts());
	}

	@Test
	public void valid_pass_time_with_savings_account_below_100_balance_gets_deducted_25() {
		Savings account = new Savings("12345678", 3);
		bank.addAccount(account);
		account.addDeposit(50);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(25.0625, balance);
	}

	@Test
	public void valid_pass_time_with_savings_account_100_balance_does_not_get_deducted_25() {
		Savings account = new Savings("12345678", 3);
		bank.addAccount(account);
		account.addDeposit(100);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(100.25, balance);
	}

	@Test
	public void valid_pass_time_with_checking_account_below_100_balance_gets_deducted_25() {
		Checking account = new Checking("22222222", 3);
		bank.addAccount(account);
		account.addDeposit(90);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(65.1625, balance);
	}

	@Test
	public void valid_pass_time_with_checking_account_100_balance_does_not_get_deducted_25() {
		Checking account = new Checking("22222222", 3);
		bank.addAccount(account);
		account.addDeposit(100);

		String input = "pass 1";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(100.25, balance);
	}

	@Test
	public void valid_withdraw_with_checking_account() {
		Checking account = new Checking("87654321", 3);
		bank.addAccount(account);
		account.addDeposit(500);

		String input = "withdraw 87654321 100";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(400, balance);
	}

	@Test
	public void valid_withdraw_twice_with_checking_account() {
		Checking account = new Checking("87654321", 3);
		bank.addAccount(account);
		account.addDeposit(500);
		account.subWithdraw(100);

		String input = "withdraw 87654321 100";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(300, balance);
	}

	@Test
	public void valid_withdraw_with_savings_account() {
		Savings account = new Savings("12345678", 3);
		bank.addAccount(account);
		account.addDeposit(500);

		String input = "withdraw 12345678 400";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(100, balance);
	}

	@Test
	public void valid_withdraw_with_savings_account_over_bal() {
		Savings account = new Savings("12345678", 3);
		bank.addAccount(account);
		account.addDeposit(500);

		String input = "withdraw 12345678 600";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_withdraw_with_checking_account_over_bal() {
		Checking account = new Checking("87654321", 3);
		bank.addAccount(account);
		account.addDeposit(500);

		String input = "withdraw 87654321 600";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_withdraw_after_a_month_with_savings_account() {
		Savings account = new Savings("12345678", 3);
		bank.addAccount(account);
		account.addDeposit(500);
		account.subWithdraw(50);
		bank.pass(1);

		String input = "withdraw 12345678 100";
		commandProcessor.processCommand(input);
		double balance1 = account.getBalance();
		assertEquals(351.125, balance1);
	}

	@Test
	public void valid_withdraw_after_3_months_with_savings_account() {
		Savings account = new Savings("12345678", 3);
		bank.addAccount(account);
		account.addDeposit(500);
		account.subWithdraw(100);
		bank.pass(3);

		String input = "withdraw 12345678 100";
		commandProcessor.processCommand(input);
		double balance1 = account.getBalance();
		assertEquals(303.00750625, balance1);
	}

	@Test
	public void valid_withdraw_with_cd_account_after_12_months() {
		CD account = new CD("22222222", 1, 3000);
		bank.addAccount(account);
		bank.pass(1);
		bank.pass(11);

		String input = "withdraw 22222222 3122.38031136499512";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_withdraw_with_cd_account_after_12_months_over_balance() {
		CD account = new CD("22222222", 1, 3000);
		bank.addAccount(account);
		bank.pass(12);

		String input = "withdraw 22222222 50000";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_withdraw_with_cd_account_after_20_months() {
		CD account = new CD("22222222", 1, 3000);
		bank.addAccount(account);
		bank.pass(20);

		String input = "withdraw 22222222 3206.72828967575242";
		commandProcessor.processCommand(input);
		double balance = account.getBalance();
		assertEquals(0, balance);
	}

	@Test
	public void valid_transfer_from_savings_to_savings() {
		Savings account1 = new Savings("12345678", 3);
		bank.addAccount(account1);
		account1.addDeposit(500);
		Savings account2 = new Savings("87654321", 3);
		bank.addAccount(account2);

		String input = "transfer 12345678 87654321 100";
		commandProcessor.processCommand(input);
		double balanceOfAccount1 = account1.getBalance();
		double balanceOfAccount2 = account2.getBalance();
		assertEquals(400, balanceOfAccount1);
		assertEquals(100, balanceOfAccount2);
	}

	@Test
	public void valid_transfer_from_savings_to_checking() {
		Savings account1 = new Savings("12345678", 3);
		bank.addAccount(account1);
		account1.addDeposit(1000);
		Checking account2 = new Checking("11111111", 3);
		bank.addAccount(account2);

		String input = "transfer 12345678 11111111 900";
		commandProcessor.processCommand(input);
		double balanceOfAccount1 = account1.getBalance();
		double balanceOfAccount2 = account2.getBalance();
		assertEquals(100, balanceOfAccount1);
		assertEquals(900, balanceOfAccount2);
	}

	@Test
	public void valid_transfer_from_checking_to_savings() {
		Checking account1 = new Checking("11111111", 3);
		bank.addAccount(account1);
		account1.addDeposit(1000);
		Savings account2 = new Savings("12345678", 3);
		bank.addAccount(account2);
		account2.addDeposit(100);

		String input = "transfer 11111111 12345678 100";
		commandProcessor.processCommand(input);
		double balanceOfAccount1 = account1.getBalance();
		double balanceOfAccount2 = account2.getBalance();
		assertEquals(900, balanceOfAccount1);
		assertEquals(200, balanceOfAccount2);
	}

	@Test
	public void valid_transfer_from_checking_to_checking() {
		Checking account1 = new Checking("11111111", 3);
		bank.addAccount(account1);
		account1.addDeposit(400);
		Checking account2 = new Checking("22222222", 3);
		bank.addAccount(account2);

		String input = "transfer 11111111 22222222 400";
		commandProcessor.processCommand(input);
		double balanceOfAccount1 = account1.getBalance();
		double balanceOfAccount2 = account2.getBalance();
		assertEquals(0, balanceOfAccount1);
		assertEquals(400, balanceOfAccount2);
	}

	@Test
	public void valid_transfer_from_savings_to_savings_after_1_month_has_passed_from_last_withdraw() {
		Savings account1 = new Savings("12345678", 3);
		bank.addAccount(account1);
		account1.addDeposit(500);
		account1.subWithdraw(100);
		Savings account2 = new Savings("87654321", 3);
		bank.addAccount(account2);
		account2.addDeposit(400);
		bank.pass(1);

		String input = "transfer 12345678 87654321 100";
		commandProcessor.processCommand(input);
		double balanceOfAccount1 = account1.getBalance();
		double balanceOfAccount2 = account2.getBalance();
		assertEquals(301, balanceOfAccount1);
		assertEquals(501, balanceOfAccount2);
	}

	@Test
	public void valid_transfer_from_savings_to_savings_after_1_month_has_passed_from_last_transfer() {
		Savings account1 = new Savings("12345678", 3);
		bank.addAccount(account1);
		account1.addDeposit(500);
		Savings account2 = new Savings("87654321", 3);
		bank.addAccount(account2);
		account2.addDeposit(400);
		String input1 = "transfer 12345678 87654321 100";
		commandProcessor.processCommand(input1);
		bank.pass(1);

		String input2 = "transfer 12345678 87654321 100";
		commandProcessor.processCommand(input2);
		double balanceOfAccount1 = account1.getBalance();
		double balanceOfAccount2 = account2.getBalance();
		assertEquals(301, balanceOfAccount1);
		assertEquals(601.25, balanceOfAccount2);
	}

	@Test
	public void valid_transfer_from_savings_with_balance_less_than_transfer_amount() {
		Savings account1 = new Savings("12345678", 3);
		bank.addAccount(account1);
		account1.addDeposit(500);
		Savings account2 = new Savings("87654321", 3);
		bank.addAccount(account2);

		String input = "transfer 12345678 87654321 600";
		commandProcessor.processCommand(input);
		double balanceOfAccount1 = account1.getBalance();
		double balanceOfAccount2 = account2.getBalance();
		assertEquals(0, balanceOfAccount1);
		assertEquals(500, balanceOfAccount2);
	}

	@Test
	public void valid_transfer_from_checking_with_balance_less_than_transfer_amount() {
		Checking account1 = new Checking("11111111", 3);
		bank.addAccount(account1);
		account1.addDeposit(200);
		Checking account2 = new Checking("22222222", 3);
		bank.addAccount(account2);

		String input = "transfer 11111111 22222222 400";
		commandProcessor.processCommand(input);
		double balanceOfAccount1 = account1.getBalance();
		double balanceOfAccount2 = account2.getBalance();
		assertEquals(0, balanceOfAccount1);
		assertEquals(200, balanceOfAccount2);
	}
}