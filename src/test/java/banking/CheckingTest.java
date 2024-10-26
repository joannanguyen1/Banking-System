package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {

	Checking checking;

	@BeforeEach
	public void setup() {
		checking = new Checking("12312312", 3.5);
	}

	@Test
	public void checking_account_created_with_a_starting_balance_of_0() {
		double actual = checking.getBalance();

		assertEquals(0, actual);
	}
}
