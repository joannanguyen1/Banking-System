package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {
	CommandValidator commandValidator;
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		commandProcessor = new CommandProcessor(bank);
		Account savings1 = new Savings("12345678", 0.5);
		Account savings2 = new Savings("87654321", 3);
		Account checking1 = new Checking("11111111", 0.9);
		Account checking2 = new Checking("22222222", 0.9);
		Account cd = new CD("23456789", 0.9, 1000);
		bank.addAccount(savings1);
		savings1.addDeposit(500);
		bank.addAccount(savings2);
		savings2.addDeposit(1000);
		bank.addAccount(checking1);
		checking1.addDeposit(300);
		bank.addAccount(checking2);
		checking2.addDeposit(1000);
		bank.addAccount(cd);
	}

	@Test
	public void valid_transfer_command_from_savings_into_savings() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_into_savings_max_savings_withdraw_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 1000");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_into_savings_min_savings_withdraw_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 0");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_from_savings_into_savings_over_max_savings_withdraw_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 2000");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_into_savings_min_savings_deposit_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 0");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_from_savings_into_savings_over_savings_deposit_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 3000");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_into_checking() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111 100");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_into_checking_max_checking_deposit_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111 1000");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_into_checking_min_checking_deposit_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111 0");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_from_savings_into_checking_over_checking_deposit_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111 10000");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_from_checking_into_savings() {
		boolean actual = commandValidator.validate("transfer 11111111 12345678 160");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_checking_into_savings_max_checking_withdraw_amount() {
		boolean actual = commandValidator.validate("transfer 11111111 12345678 400");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_checking_into_savings_min_checking_withdraw_amount() {
		boolean actual = commandValidator.validate("transfer 11111111 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_from_checking_into_savings_over_max_checking_withdraw_amount() {
		boolean actual = commandValidator.validate("transfer 11111111 12345678 500");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_from_checking_into_checking() {
		boolean actual = commandValidator.validate("transfer 11111111 22222222 160");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_from_cd_into_checking() {
		boolean actual = commandValidator.validate("transfer 23456789 22222222 500");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_from_cd_into_savings() {
		boolean actual = commandValidator.validate("transfer 23456789 12345678 300");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_from_checking_into_cd() {
		boolean actual = commandValidator.validate("transfer 11111111 23456789 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_from_savings_into_cd() {
		boolean actual = commandValidator.validate("transfer 12345678 23456789 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_missing_parameters() {
		boolean actual = commandValidator.validate("transfer");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_missing_id_and_amount() {
		boolean actual = commandValidator.validate("transfer 12345678");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_missing_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_missing_ids() {
		boolean actual = commandValidator.validate("transfer 200");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_missing_second_id() {
		boolean actual = commandValidator.validate("transfer 12345678 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_typo_in_transfer() {
		boolean actual = commandValidator.validate("transfe 12345678 11111111 100");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_case_insensitive() {
		boolean actual = commandValidator.validate("Transfer 12345678 11111111 100");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_from_savings_into_nonexistent_account() {
		boolean actual = commandValidator.validate("transfer 12345678 00000000 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_from_checking_into_nonexistent_account() {
		boolean actual = commandValidator.validate("transfer 11111111 00000000 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_from_nonexistent_account_into_savings() {
		boolean actual = commandValidator.validate("transfer 00000000 12345678 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_from_nonexistent_account_into_checking() {
		boolean actual = commandValidator.validate("transfer 00000000 11111111 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_a_negative_first_id() {
		boolean actual = commandValidator.validate("transfer -12345678 11111111 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_a_negative_second_id() {
		boolean actual = commandValidator.validate("transfer 12345678 -11111111 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_a_string_first_id() {
		boolean actual = commandValidator.validate("transfer abcdefgh 11111111 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_a_string_second_id() {
		boolean actual = commandValidator.validate("transfer 11111111 abcdefgh 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_first_id_under_8_digits() {
		boolean actual = commandValidator.validate("transfer 1234567 11111111 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_second_id_under_8_digits() {
		boolean actual = commandValidator.validate("transfer 11111111 1234567 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_first_id_over_8_digits() {
		boolean actual = commandValidator.validate("transfer 123456789 11111111 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_second_id_over_8_digits() {
		boolean actual = commandValidator.validate("transfer 11111111 123456789 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_arguments_in_wrong_order_id_first() {
		boolean actual = commandValidator.validate("11111111 transfer 123456789 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_arguments_in_wrong_order_amount_first() {
		boolean actual = commandValidator.validate("200 transfer 123456789 11111111");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_arguments_in_wrong_order_ids_first() {
		boolean actual = commandValidator.validate("123456789 11111111 transfer 200");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_with_string_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111 abc");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_from_savings_that_has_already_transferred_month() {
		commandProcessor.processCommand("transfer 12345678 87654321 100");
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_from_savings_that_has_already_withdrawn_this_month() {
		commandProcessor.processCommand("withdraw 12345678 100");
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_to_savings_after_1_month_from_previous_transfer() {
		commandProcessor.processCommand("transfer 12345678 87654321 100");
		bank.pass(1);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_to_savings_after_1_month_from_previous_withdraw() {
		commandProcessor.processCommand("withdraw 12345678 100");
		bank.pass(1);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_to_savings_after_6_months_from_previous_transfer() {
		commandProcessor.processCommand("transfer 12345678 87654321 100");
		bank.pass(6);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_to_savings_after_6_months_from_previous_withdraw() {
		commandProcessor.processCommand("withdraw 12345678 100");
		bank.pass(6);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 100");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_with_balance_less_than_transfer_amount() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 600");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_checking_with_balance_less_than_transfer_amount() {
		boolean actual = commandValidator.validate("transfer 11111111 87654321 400");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command() {
		boolean actual = commandValidator.validate("Transfer 11111111 12345678 300");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_id1_less_than_8_digits() {
		boolean actual = commandValidator.validate("Transfer 123 12345678 300");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_id2_less_than_8_digits() {
		boolean actual = commandValidator.validate("Transfer 12345678 1 300");
		assertFalse(actual);
	}

	@Test
	public void invalid_transfer_command_string_ids() {
		boolean actual = commandValidator.validate("transfer abc efg 300");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_savings_transfer_amount_greater_than_balance() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111 800");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_savings_transfer_amount_is_max_deposit_amount() {
		boolean actual = commandValidator.validate("transfer 87654321 12345678 1000");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_savings_transfer_amount_is_2500_greater_than_valid_withdraws() {
		boolean actual = commandValidator.validate("transfer 87654321 12345678 2500");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_checking_transfer_amount_greater_than_balance() {
		boolean actual = commandValidator.validate("transfer 11111111 12345678 400");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_checking_transfer_amount_1000() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111 1000");
		assertTrue(actual);
	}

	@Test
	public void invalid_transfer_command_checking_transfer_amount_1500() {
		boolean actual = commandValidator.validate("transfer 12345678 11111111 1500");
		assertFalse(actual);
	}

	@Test
	public void valid_transfer_command_from_savings_into_savings_entire_savings_bal() {
		boolean actual = commandValidator.validate("transfer 12345678 87654321 500");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_checking_into_checking_entire_checking_bal() {
		boolean actual = commandValidator.validate("transfer 11111111 22222222 300");
		assertTrue(actual);
	}

	@Test
	public void valid_transfer_command_from_checking_into_checking_less_than_checking_bal() {
		boolean actual = commandValidator.validate("transfer 11111111 22222222 200");
		assertTrue(actual);
	}
}
