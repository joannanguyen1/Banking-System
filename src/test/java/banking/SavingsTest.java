package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {

	Savings savings;

	@BeforeEach
	public void setup() {
		savings = new Savings("12312312", 3.5);
	}

	@Test
	public void savings_account_created_with_a_starting_balance_of_0() {
		double actual = savings.getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void withdrawal_records_should_increment_with_subWithdraw() {
		savings.subWithdraw(50);
		int actualWithdrawalCount = savings.getWithdrawalCount();

		assertEquals(1, actualWithdrawalCount);
	}

	@Test
	public void withdrawal_records_right_month_over_balance() {
		savings.subWithdraw(50);
		int actual = savings.getLastWithdrawalMonth();

		assertEquals(0, actual);
	}

	@Test
	public void withdrawal_records_right_month_under_balance() {
		savings.addDeposit(200);
		savings.subWithdraw(50);
		int actualMonth = savings.getLastWithdrawalMonth();
		double actualBalance = savings.getBalance();

		assertEquals(0, actualMonth);
		assertEquals(150, actualBalance);
	}

}
