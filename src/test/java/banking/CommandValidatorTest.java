package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	CreateValidator createValidator;
	DepositValidator depositValidator;
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		createValidator = new CreateValidator(bank);
		depositValidator = new DepositValidator(bank);
	}

	@Test
	public void valid_create_command() {
		boolean actual = commandValidator.validate("create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void invalid_create_command_typo() {
		boolean actual = commandValidator.validate("creat savings 12345678 0.6");
		assertFalse(actual);
	}

	@Test
	public void invalid_create_command_missing_create() {
		boolean actual = commandValidator.validate("savings 12345678 0.6");
		assertFalse(actual);
	}

	@Test
	public void valid_create_command_case_insensitive() {
		boolean actual = commandValidator.validate("Create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void valid_deposit_command_into_savings() {
		Account accountTest = new Savings("12345678", 4.2);
		bank.addAccount(accountTest);
		boolean actual = commandValidator.validate("deposit 12345678 100");
		assertTrue(actual);
	}

	@Test
	public void valid_deposit_command_into_checking() {
		Account accountTest = new Checking("87654321", 4.2);
		bank.addAccount(accountTest);
		boolean actual = commandValidator.validate("deposit 87654321 700");
		assertTrue(actual);
	}

	@Test
	public void invalid_deposit_command_with_typo() {
		boolean actual = commandValidator.validate("deposi 87654321 700");
		assertFalse(actual);
	}

	@Test
	public void valid_deposit_command_with_case_insensitive() {
		Account accountTest = new Savings("12345678", 4.2);
		bank.addAccount(accountTest);
		boolean actual = commandValidator.validate("Deposit 12345678 200");
		assertTrue(actual);
	}

	@Test
	public void invalid_deposit_command_missing_deposit() {
		boolean actual = commandValidator.validate("12345678 500");
		assertFalse(actual);
	}

	@Test
	public void valid_deposit_command() {
		Account accountTest = new Savings("98765432", 4.2);
		bank.addAccount(accountTest);
		boolean actual = commandValidator.validate("Deposit 98765432 300");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command() {
		Account accountTest1 = new Savings("98765432", 4.2);
		bank.addAccount(accountTest1);
		Account accountTest2 = new Savings("12345678", 4.2);
		bank.addAccount(accountTest2);
		boolean actual = commandValidator.validate("Transfer 98765432 12345678 300");
		assertTrue(actual);
	}

	@Test
	public void invalid_command_just_a_space() {
		boolean actual = commandValidator.validate(" ");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_id_less_than_8_digits() {
		boolean actual = commandValidator.validate("deposit 1234567 500");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_id_is_a_string() {
		boolean actual = commandValidator.validate("deposit abcdefgh 500");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_id_negative() {
		boolean actual = commandValidator.validate("deposit -12345678 500");
		assertFalse(actual);
	}

	@Test
	public void invalid_deposit_command_amount_negative() {
		boolean actual = commandValidator.validate("deposit 12345678 -500");
		assertFalse(actual);
	}

	@Test
	public void valid_id_length_eight() {
		Account account = new Checking("12345678", 0.5);
		bank.addAccount(account);
		assertTrue(commandValidator.checkId("deposit 12345678 100".split(" ")));
	}

	@Test
	public void invalid_id_length_seven() {
		assertFalse(commandValidator.checkId("deposit 1234567 100".split(" ")));
	}

	@Test
	public void invalid_id_length_nine() {
		assertFalse(commandValidator.checkId("deposit 123456789 100".split(" ")));
	}

	@Test
	public void invalid_id_null() {
		assertFalse(commandValidator.checkId("deposit 12345678 100".split(" ")));
	}

	@Test
	public void invalid_id_non_numeric_characters() {
		assertFalse(commandValidator.checkId("deposit abcdefgh 100".split(" ")));
	}

	@Test
	public void invalid_id_mixed_characters_and_numbers() {
		assertFalse(commandValidator.checkId("deposit 12abcd34 100".split(" ")));
	}

	@Test
	public void invalid_id_negative_integer() {
		assertFalse(commandValidator.checkValidId("-12345678"));
	}

	@Test
	public void invalid_id_zero() {
		assertFalse(commandValidator.checkValidId("0"));
	}

	@Test
	public void invalid_id_non_existent_account() {
		assertFalse(commandValidator.checkValidId("12345678"));
	}

	@Test
	public void invalid_non_numeric_id() {
		assertFalse(commandValidator.checkValidId("abcdefgh"));
	}

	@Test
	public void invalid_mixed_characters_and_numbers_id() {
		assertFalse(commandValidator.checkValidId("12abcd34"));
	}

	@Test
	public void invalid_null_id() {
		assertFalse(commandValidator.checkValidId(null));
	}

	@Test
	public void invalid_empty_string_id() {
		assertFalse(commandValidator.checkValidId(""));
	}

	@Test
	public void valid_positive_amount() {
		assertTrue(commandValidator.checkAmountGreaterThanZero(new String[]{"command", "100"}, 1));
	}

	@Test
	public void invalid_negative_amount() {
		assertFalse(commandValidator.checkAmountGreaterThanZero(new String[]{"command", "-50"}, 1));
	}

	@Test
	public void invalid_zero_amount() {
		assertTrue(commandValidator.checkAmountGreaterThanZero(new String[]{"command", "0"}, 1));
	}

	@Test
	public void invalid_non_numeric_amount() {
		assertFalse(commandValidator.checkAmountGreaterThanZero(new String[]{"command", "abc"}, 1));
	}

	@Test
	public void invalid_empty_string_amount() {
		assertFalse(commandValidator.checkAmountGreaterThanZero(new String[]{"command", ""}, 1));
	}

}
