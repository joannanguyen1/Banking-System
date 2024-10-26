package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {

	CD cd;

	@BeforeEach
	public void setup() {
		cd = new CD("12312312", 10, 1000);
	}

	@Test
	public void CD_when_created_its_starting_balance_is_the_supplied_balance() {
		double actual = cd.getBalance();

		assertEquals(1000, actual);
	}
}
