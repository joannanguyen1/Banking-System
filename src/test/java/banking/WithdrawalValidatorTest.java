package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawalValidatorTest {
	CommandValidator commandValidator;
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		commandProcessor = new CommandProcessor(bank);
		Account savings = new Savings("12345678", 0.5);
		Account checking = new Checking("87654321", 0.9);
		Account cd = new CD("22222222", 1, 3000);
		bank.addAccount(savings);
		bank.addAccount(checking);
		bank.addAccount(cd);
	}

	@Test
	public void valid_withdraw_command_for_savings() {
		boolean actual = commandValidator.validate("withdraw 12345678 100");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_min_of_0_command_for_savings() {
		boolean actual = commandValidator.validate("withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_max_of_1000_command_for_savings() {
		boolean actual = commandValidator.validate("withdraw 12345678 1000");
		assertTrue(actual);
	}

	@Test
	public void invalid_withdraw_command_for_savings_one_month_has_not_passed() {
		commandProcessor.processCommand("withdraw 12345678 1000");
		boolean actual = commandValidator.validate("withdraw 12345678 1000");
		assertFalse(actual);
	}

	@Test
	public void valid_withdraw_command_for_savings_one_month_has_passed() {
		commandProcessor.processCommand("deposit 12345678 1000");
		commandProcessor.processCommand("withdraw 12345678 100");
		bank.pass(1);
		boolean actual = commandValidator.validate("withdraw 12345678 200");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_command_for_savings_6_months_has_passed() {
		commandProcessor.processCommand("deposit 12345678 1000");
		commandProcessor.processCommand("withdraw 12345678 100");
		bank.pass(6);
		boolean actual = commandValidator.validate("withdraw 12345678 200");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_command_for_checking() {
		boolean actual = commandValidator.validate("withdraw 87654321 100");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_min_of_0_command_for_checking() {
		boolean actual = commandValidator.validate("withdraw 87654321 0");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_max_of_400_command_for_checking() {
		boolean actual = commandValidator.validate("withdraw 87654321 400");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_command_for_cd() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw 22222222 3122.380311364995");
		assertTrue(actual);
	}

	@Test
	public void invalid_withdraw_command_for_cd_12_months_have_not_passed() {
		boolean actual = commandValidator.validate("withdraw 22222222 3000");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_cd_12_months_have_not_passed_6_months_have() {
		bank.pass(6);
		boolean actual = commandValidator.validate("withdraw 22222222 3060.5785293135323");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_under_balance_for_cd() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw 22222222 100");
		assertFalse(actual);
	}

	@Test
	public void valid_withdraw_command_over_balance_for_cd() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw 22222222 50000");
		assertTrue(actual);
	}

	@Test
	public void invalid_withdraw_command_for_nonexistent_account() {
		boolean actual = commandValidator.validate("withdraw 00000000 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_nonexistent_account_2() {
		boolean actual = commandValidator.validate("withdraw 56567878 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_savings_account_missing_withdraw_amount() {
		boolean actual = commandValidator.validate("withdraw 12345678");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_checking_account_missing_withdraw_amount() {
		boolean actual = commandValidator.validate("withdraw 87654321");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_cd_account_missing_withdraw_amount() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw 22222222");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_missing_ID() {
		boolean actual = commandValidator.validate("withdraw 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_with_an_ID_less_than_8_digits() {
		boolean actual = commandValidator.validate("withdraw 1234567 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_with_an_ID_greater_than_8_digits() {
		boolean actual = commandValidator.validate("withdraw 123456789 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_with_a_string_ID() {
		boolean actual = commandValidator.validate("withdraw abcdefgh 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_with_a_negative_ID() {
		boolean actual = commandValidator.validate("withdraw -12345678 2000");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_savings_with_negative_withdraw_amount() {
		boolean actual = commandValidator.validate("withdraw 12345678 -250");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_checking_with_negative_withdraw_amount() {
		boolean actual = commandValidator.validate("withdraw 87654321 -250");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_cd_with_negative_withdraw_amount() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw 22222222 -250");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_savings_with_a_withdraw_amount_over_the_max() {
		boolean actual = commandValidator.validate("withdraw 12345678 1050");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_checking_with_a_withdraw_amount_over_the_max() {
		boolean actual = commandValidator.validate("withdraw 87654321 500");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_missing_parameters() {
		boolean actual = commandValidator.validate("withdraw");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_with_arguments_in_wrong_order_for_savings() {
		boolean actual = commandValidator.validate("12345678 withdraw 200");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_with_arguments_in_wrong_order_for_checking() {
		boolean actual = commandValidator.validate("87654321 withdraw 200");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_with_arguments_in_wrong_order_for_cd() {
		boolean actual = commandValidator.validate("22222222 withdraw 200");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_savings_with_a_string_withdraw_amount() {
		boolean actual = commandValidator.validate("withdraw 12345678 abcd");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_checking_with_a_string_withdraw_amount() {
		boolean actual = commandValidator.validate("withdraw 87654321 abcd");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_cd_with_a_string_withdraw_amount() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw 22222222 abcd");
		assertFalse(actual);
	}

	@Test
	public void valid_withdraw_command_for_savings_with_space_after() {
		boolean actual = commandValidator.validate("withdraw 12345678 100 ");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_command_for_savings_with_2_spaces_after() {
		boolean actual = commandValidator.validate("withdraw 12345678 100  ");
		assertTrue(actual);
	}

	@Test
	public void invalid_withdraw_command_for_savings_with_space_before() {
		boolean actual = commandValidator.validate(" withdraw 12345678 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_savings_with_spaces_in_middle() {
		boolean actual = commandValidator.validate("withdraw  12345678 100");
		assertFalse(actual);
	}

	@Test
	public void valid_withdraw_command_for_checking_with_space_after() {
		boolean actual = commandValidator.validate("withdraw 87654321 100 ");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_command_for_checking_with_2_spaces_after() {
		boolean actual = commandValidator.validate("withdraw 87654321 100  ");
		assertTrue(actual);
	}

	@Test
	public void invalid_withdraw_command_for_checking_with_space_before() {
		boolean actual = commandValidator.validate(" withdraw 87654321 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_checking_with_spaces_in_middle() {
		boolean actual = commandValidator.validate("withdraw  87654321 100");
		assertFalse(actual);
	}

	@Test
	public void valid_withdraw_command_for_cd_with_space_after() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw 22222222 3122.380311364995 ");
		assertTrue(actual);
	}

	@Test
	public void valid_withdraw_command_for_cd_with_2_spaces_after() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw 22222222 3122.380311364995  ");
		assertTrue(actual);
	}

	@Test
	public void invalid_withdraw_command_for_cd_with_space_before() {
		bank.pass(12);
		boolean actual = commandValidator.validate(" withdraw 22222222 3122.380311364995");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_cd_with_space_in_middle() {
		bank.pass(12);
		boolean actual = commandValidator.validate("withdraw  22222222 3122.380311364995");
		assertFalse(actual);
	}

	@Test
	public void invalid_withdraw_command_for_savings_twice_in_one_month() {
		commandProcessor.processCommand("withdraw 12345678 100");
		boolean actual = commandValidator.validate("withdraw 12345678 100");
		assertFalse(actual);
	}
}
