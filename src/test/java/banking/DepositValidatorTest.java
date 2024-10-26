package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		Account savings = new Savings("12345678", 0.5);
		Account checking = new Checking("87654321", 0.9);
		Account cd = new CD("22222222", 0.9, 1000);
		bank.addAccount(savings);
		bank.addAccount(checking);
		bank.addAccount(cd);
	}

	@Test
	public void valid_deposit_command_into_savings() {
		boolean actual = commandValidator.validate("deposit 12345678 2000");
		assertTrue(actual);
	}

	@Test
	public void valid_deposit_command_into_checking() {
		boolean actual = commandValidator.validate("deposit 87654321 500");
		assertTrue(actual);
	}

	@Test
	public void invalid_deposit_command_into_CD() {
		boolean actual = commandValidator.validate("deposit 22222222 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_nonexistent_account() {
		boolean actual = commandValidator.validate("deposit 00000000 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_savings_account_missing_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 12345678");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_checking_account_missing_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 87654321");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_missing_ID() {
		boolean actual = commandValidator.validate("deposit 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_with_an_ID_less_than_8_digits() {
		boolean actual = commandValidator.validate("deposit 1234567 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_with_an_ID_greater_than_8_digits() {
		boolean actual = commandValidator.validate("deposit 123456789 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_with_a_string_ID() {
		boolean actual = commandValidator.validate("deposit abcdefgh 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_with_a_negative_ID() {
		boolean actual = commandValidator.validate("deposit -12345678 2000");
		assertFalse(actual);
	}

	@Test
	public void valid_deposit_command_into_savings_with_minimum_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void valid_deposit_command_into_checking_with_minimum_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 87654321 0");
		assertTrue(actual);
	}

	@Test
	public void valid_deposit_command_into_savings_with_max_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 12345678 2500");
		assertTrue(actual);
	}

	@Test
	public void valid_deposit_command_into_checking_with_max_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 87654321 1000");
		assertTrue(actual);
	}

	@Test
	public void invalid_deposit_command_into_savings_with_negative_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 12345678 -250");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_checking_with_negative_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 87654321 -250");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_savings_with_a_deposit_amount_over_the_max() {
		boolean actual = commandValidator.validate("deposit 12345678 3000");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_checking_with_a_deposit_amount_over_the_max() {
		boolean actual = commandValidator.validate("deposit 87654321 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_missing_parameters() {
		boolean actual = commandValidator.validate("deposit");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_with_arguments_in_wrong_order_into_savings() {
		boolean actual = commandValidator.validate("12345678 deposit 200");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_with_arguments_in_wrong_order_into_checking() {
		boolean actual = commandValidator.validate("87654321 deposit 200");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_savings_with_a_string_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 12345678 abcd");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_checking_with_a_string_deposit_amount() {
		boolean actual = commandValidator.validate("deposit 87654321 abcd");
		assertFalse(actual);
	}

	@Test
	public void valid_deposit_command_into_savings_with_space_after() {
		boolean actual = commandValidator.validate("deposit 12345678 100 ");
		assertTrue(actual);
	}

	@Test
	public void valid_deposit_command_into_savings_with_2_spaces_after() {
		boolean actual = commandValidator.validate("deposit 12345678 100  ");
		assertTrue(actual);
	}

	@Test
	public void invalid_deposit_command_into_savings_with_space_before() {
		boolean actual = commandValidator.validate(" deposit 12345678 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_savings_with_space_in_middle() {
		boolean actual = commandValidator.validate("deposit  12345678 100");
		assertFalse(actual);
	}

	@Test
	public void valid_deposit_command_into_checking_with_space_after() {
		boolean actual = commandValidator.validate("deposit 87654321 100 ");
		assertTrue(actual);
	}

	@Test
	public void valid_deposit_command_into_checking_with_2_spaces_after() {
		boolean actual = commandValidator.validate("deposit 87654321 100  ");
		assertTrue(actual);
	}

	@Test
	public void invalid_deposit_command_into_checking_with_space_before() {
		boolean actual = commandValidator.validate(" deposit 87654321 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_into_checking_with_space_in_middle() {
		boolean actual = commandValidator.validate("deposit  87654321 100");
		assertFalse(actual);
	}

	@Test
	public void valid_deposit_command_into_checking_master_test() {
		Account accountTest = new Checking("98765432", 0.01);
		bank.addAccount(accountTest);
		boolean actual = commandValidator.validate("Deposit 98765432 300");
		assertTrue(actual);
	}
}
