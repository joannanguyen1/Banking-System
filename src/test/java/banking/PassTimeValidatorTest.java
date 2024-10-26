package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {

	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void valid_min_pass_command() {
		boolean actual = commandValidator.validate("pass 1");
		assertTrue(actual);
	}

	@Test
	public void valid_pass_command() {
		boolean actual = commandValidator.validate("pass 30");
		assertTrue(actual);
	}

	@Test
	public void valid_max_pass_command() {
		boolean actual = commandValidator.validate("pass 60");
		assertTrue(actual);
	}

	@Test
	public void invalid_pass_command_month_exceeds_max() {
		boolean actual = commandValidator.validate("pass 61");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_month_zero() {
		boolean actual = commandValidator.validate("pass 0");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_month_is_negative() {
		boolean actual = commandValidator.validate("pass -1");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_missing_pass() {
		boolean actual = commandValidator.validate("1");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_month() {
		boolean actual = commandValidator.validate("pass");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_month_is_a_letter() {
		boolean actual = commandValidator.validate("pass a");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_month_contains_a_letter() {
		boolean actual = commandValidator.validate("pass 1a");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_empty_string() {
		boolean actual = commandValidator.validate("");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_month_with_decimal() {
		boolean actual = commandValidator.validate("pass 1.2");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_with_space_before() {
		boolean actual = commandValidator.validate(" pass 1");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_with_2_spaces_before() {
		boolean actual = commandValidator.validate("  pass 1");
		assertFalse(actual);
	}

	@Test
	public void invalid_pass_command_with_2_spaces_in_middle() {
		boolean actual = commandValidator.validate("pass  1");
		assertFalse(actual);
	}

	@Test
	public void valid_pass_command_with_space_after() {
		boolean actual = commandValidator.validate("pass 1 ");
		assertTrue(actual);
	}

	@Test
	public void valid_pass_command_with_2_spaces_after() {
		boolean actual = commandValidator.validate("pass 1  ");
		assertTrue(actual);
	}
}
