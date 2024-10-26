package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

	Savings account;

	@BeforeEach
	public void setup() {
		account = new Savings("12312312", 10);
	}

	@Test
	public void account_created_with_supplied_APR() {
		double actual = account.getAPR();

		assertEquals(10, actual);
	}

	@Test
	public void account_when_depositing_the_balance_increases_by_the_amount_deposited() {
		account.addDeposit(100);
		double actual = account.getBalance();

		assertEquals(100, actual);
	}

	@Test
	public void account_when_withdrawing_the_balance_decreases_by_the_amount_withdrawn() {
		account.addDeposit(100);
		account.subWithdraw(10);
		double actual = account.getBalance();

		assertEquals(90, actual);
	}

	@Test
	public void account_when_withdrawing_the_balance_can_not_go_below_0() {
		account.subWithdraw(100);
		double actual = account.getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void account_depositing_twice() {
		account.addDeposit(100);
		account.addDeposit(100);
		double actual = account.getBalance();

		assertEquals(200, actual);
	}

	@Test
	public void account_withdrawing_twice() {
		account.addDeposit(500);
		account.subWithdraw(100);
		account.subWithdraw(100);
		double actual = account.getBalance();

		assertEquals(300, actual);
	}

	@Test
	public void can_withdraw_should_return_true_by_default() {
		assertTrue(account.canWithdraw());
	}

	@Test
	public void can_withdraw_should_return_false_after_withdrawing_once_a_month() {
		account.addDeposit(50);
		account.subWithdraw(20);

		assertFalse(account.canWithdraw());
	}

	@Test
	public void get_last_withdrawal_month_should_return_zero_initially() {
		assertEquals(0, account.getLastWithdrawalMonth());
	}

	@Test
	public void get_last_withdrawal_month_should_return_correct_value_after_withdrawal() {
		account.addDeposit(100);
		account.subWithdraw(50);

		assertEquals(account.getCurrentMonth(), account.getLastWithdrawalMonth());
	}

	@Test
	public void get_last_withdrawal_month_should_return_zero_if_no_withdrawal_occurred() {
		assertEquals(0, account.getLastWithdrawalMonth());
	}

	@Test
	public void withdrawing_less_than_balance_should_return_updated_balance() {
		account.addDeposit(100);
		account.subWithdraw(50);

		assertEquals(50, account.getBalance());
	}

}
