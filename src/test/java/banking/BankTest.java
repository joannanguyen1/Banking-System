package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	Bank bank;

	@BeforeEach
	public void setup() {
		bank = new Bank();
	}

	@Test
	public void when_bank_is_created_it_has_no_accounts() {
		double actual = bank.getNumberOfAccounts();

		assertEquals(0, actual);
	}

	@Test
	public void when_an_account_is_added_the_bank_has_one_account() {
		Account accountTest = new Savings("00000000", 10);
		bank.addAccount(accountTest);
		double actual = bank.getNumberOfAccounts();

		assertEquals(1, actual);
	}

	@Test
	public void when_an_account_is_added_the_bank_has_two_accounts() {
		Account accountTest = new Savings("00000000", 10);
		Account accountTest2 = new Savings("00000001", 5);
		bank.addAccount(accountTest);
		bank.addAccount(accountTest2);
		double actual = bank.getNumberOfAccounts();

		assertEquals(2, actual);
	}

	@Test
	public void when_retrieving_one_account_the_correct_account_is_retrieved() {
		Account accountTest = new Savings("12312312", 4.2);
		bank.addAccount(accountTest);
		Account foundAccount = bank.getAccountByID("12312312");

		assertEquals("12312312", foundAccount.getID());
	}

	@Test
	public void when_depositing_by_ID_the_correct_account_gets_the_money() {
		Account accountTest = new Savings("12312312", 4.2);
		bank.addAccount(accountTest);
		double actual = bank.deposit("12312312", 500);

		assertEquals(500, actual);
	}

	@Test
	public void when_withdrawing_by_ID_the_correct_account_gets_the_money() {
		Account accountTest = new Savings("12312312", 4.2);
		bank.addAccount(accountTest);
		bank.deposit("12312312", 500);
		double actual = bank.withdraw("12312312", 300);

		assertEquals(200, actual);
	}

	@Test
	public void depositing_twice_through_the_bank() {
		Account accountTest = new Savings("12312312", 4.2);
		bank.addAccount(accountTest);
		bank.deposit("12312312", 500);
		double actual = bank.deposit("12312312", 5000);

		assertEquals(5500, actual);
	}

	@Test
	public void withdrawing_twice_through_the_bank() {
		Account accountTest = new Savings("12312312", 4.2);
		bank.addAccount(accountTest);
		bank.deposit("12312312", 500);
		bank.withdraw("12312312", 100);
		double actual = bank.withdraw("12312312", 150);

		assertEquals(250, actual);
	}

}
