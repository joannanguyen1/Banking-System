package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void valid_savings_command() {
		boolean actual = commandValidator.validate("create savings 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void valid_checking_command() {
		boolean actual = commandValidator.validate("create checking 12345678 0.6");
		assertTrue(actual);
	}

	@Test
	public void valid_CD_command() {
		boolean actual = commandValidator.validate("create cd 12345678 0.6 1000");
		assertTrue(actual);
	}

	@Test
	public void invalid_savings_command_with_missing_parameters() {
		boolean actual = commandValidator.validate("create savings");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_missing_parameters() {
		boolean actual = commandValidator.validate("create checking");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_missing_parameters() {
		boolean actual = commandValidator.validate("create cd");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_missing_ID() {
		boolean actual = commandValidator.validate("create savings 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_missing_ID() {
		boolean actual = commandValidator.validate("create checking 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_missing_ID() {
		boolean actual = commandValidator.validate("create cd 0.5 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_missing_APR() {
		boolean actual = commandValidator.validate("create savings 12345678");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_missing_APR() {
		boolean actual = commandValidator.validate("create checking 12345678");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_missing_APR() {
		boolean actual = commandValidator.validate("create cd 12345678 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_missing_supplied_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_command_with_missing_account_type() {
		boolean actual = commandValidator.validate("create 12345678 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_command_with_wrong_account_type() {
		boolean actual = commandValidator.validate("create HAHA 12345678 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_typo_in_account_type() {
		boolean actual = commandValidator.validate("Create savins 12345678 0.7");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_typo_in_account_type() {
		boolean actual = commandValidator.validate("Create checkin 12345678 0.7");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_typo_in_account_type() {
		boolean actual = commandValidator.validate("Create C 12345678 0.7 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_less_than_an_8_digit_ID() {
		boolean actual = commandValidator.validate("Create savings 1234567 0.7");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_less_than_an_8_digit_ID() {
		boolean actual = commandValidator.validate("Create checking 1234567 0.7");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_less_than_an_8_digit_ID() {
		boolean actual = commandValidator.validate("Create cd 1234567 0.7 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_greater_than_an_8_digit_ID() {
		boolean actual = commandValidator.validate("Create savings 123456789 0.7");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_greater_than_an_8_digit_ID() {
		boolean actual = commandValidator.validate("Create checking 123456789 0.7");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_greater_than_an_8_digit_ID() {
		boolean actual = commandValidator.validate("Create cd 123456789 0.7 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_arguments_in_the_wrong_order() {
		boolean actual = commandValidator.validate("Create 12345678 3.5 savings");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_arguments_in_the_wrong_order() {
		boolean actual = commandValidator.validate("Create 12345678 3.5 checking");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_arguments_in_the_wrong_order() {
		boolean actual = commandValidator.validate("Create 12345678 3.5 500 cd");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_a_supplied_balance() {
		boolean actual = commandValidator.validate("Create savings 12345678 0.6 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_a_supplied_balance() {
		boolean actual = commandValidator.validate("Create checking 12345678 0.6 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_a_negative_ID() {
		boolean actual = commandValidator.validate("create savings -12345678 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_a_negative_ID() {
		boolean actual = commandValidator.validate("create checking -12345678 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_a_negative_ID() {
		boolean actual = commandValidator.validate("create cd -12345678 0.5 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_a_string_ID() {
		boolean actual = commandValidator.validate("create savings abcdefgh 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_a_string_ID() {
		boolean actual = commandValidator.validate("create checking abcdefgh 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_a_string_ID() {
		boolean actual = commandValidator.validate("create cd abcdefgh 0.5 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_as_account_already_exists() {
		Account accountTest = new Savings("12345678", 4.2);
		bank.addAccount(accountTest);
		boolean actual = commandValidator.validate("create savings 12345678 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_as_account_already_exists() {
		Account accountTest = new Checking("12345678", 4.2);
		bank.addAccount(accountTest);
		boolean actual = commandValidator.validate("create checking 12345678 0.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_as_account_already_exists() {
		Account accountTest = new CD("12345678", 4.2, 500);
		bank.addAccount(accountTest);
		boolean actual = commandValidator.validate("create cd 12345678 0.5 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_a_negative_APR() {
		boolean actual = commandValidator.validate("create savings 12345678 -5.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_a_negative_APR() {
		boolean actual = commandValidator.validate("create checking 12345678 -5");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_a_negative_APR() {
		boolean actual = commandValidator.validate("create cd 12345678 -3 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_a_string_APR() {
		boolean actual = commandValidator.validate("create savings 12345678 abc");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_a_string_APR() {
		boolean actual = commandValidator.validate("create checking 12345678 abc");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_a_string_APR() {
		boolean actual = commandValidator.validate("create cd 12345678 abc 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_an_APR_greater_than_max_APR_value() {
		boolean actual = commandValidator.validate("create savings 12345678 20");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_an_APR_greater_than_max_APR_value() {
		boolean actual = commandValidator.validate("create checking 12345678 25.5");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_an_APR_greater_than_max_APR_value() {
		boolean actual = commandValidator.validate("create cd 12345678 17 1000");
		assertFalse(actual);
	}

	@Test
	public void valid_savings_command_with_the_minimum_APR_value() {
		boolean actual = commandValidator.validate("create savings 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void valid_checking_command_with_the_minimum_APR_value() {
		boolean actual = commandValidator.validate("create checking 12345678 0");
		assertTrue(actual);
	}

	@Test
	public void valid_CD_command_with_the_minimum_APR_value() {
		boolean actual = commandValidator.validate("create cd 12345678 0 1000");
		assertTrue(actual);
	}

	@Test
	public void valid_savings_command_with_the_max_APR_value() {
		boolean actual = commandValidator.validate("create savings 12345678 10");
		assertTrue(actual);
	}

	@Test
	public void valid_checking_command_with_the_max_APR_value() {
		boolean actual = commandValidator.validate("create checking 12345678 10");
		assertTrue(actual);
	}

	@Test
	public void valid_CD_command_with_the_max_APR_value() {
		boolean actual = commandValidator.validate("create cd 12345678 10 1000");
		assertTrue(actual);
	}

	@Test
	public void invalid_CD_command_with_a_negative_supplied_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 10 -500");
		assertFalse(actual);
	}

	@Test
	public void invalid_CD_command_with_a_string_supplied_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 10 abc");
		assertFalse(actual);
	}

	@Test
	public void valid_CD_command_with_the_minimum_supplied_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 0.6 1000");
		assertTrue(actual);
	}

	@Test
	public void valid_CD_command_with_the_maximum_supplied_balance() {
		boolean actual = commandValidator.validate("create cd 12345678 0.6 10000");
		assertTrue(actual);
	}

	@Test
	public void valid_savings_command_with_space_after() {
		boolean actual = commandValidator.validate("create savings 12345678 0.6 ");
		assertTrue(actual);
	}

	@Test
	public void valid_savings_command_with_2_spaces_after() {
		boolean actual = commandValidator.validate("create savings 12345678 0.6  ");
		assertTrue(actual);
	}

	@Test
	public void invalid_savings_command_with_space_before() {
		boolean actual = commandValidator.validate(" create savings 12345678 0.6 ");
		assertFalse(actual);
	}

	@Test
	public void invalid_savings_command_with_space_in_middle() {
		boolean actual = commandValidator.validate("create  savings 12345678 0.6 ");
		assertFalse(actual);
	}

	@Test
	public void valid_checking_command_with_space_after() {
		boolean actual = commandValidator.validate("create checking 12345678 0.6 ");
		assertTrue(actual);
	}

	@Test
	public void valid_checking_command_with_2_spaces_after() {
		boolean actual = commandValidator.validate("create checking 12345678 0.6  ");
		assertTrue(actual);
	}

	@Test
	public void invalid_checking_command_with_space_before() {
		boolean actual = commandValidator.validate(" create checking 12345678 0.6");
		assertFalse(actual);
	}

	@Test
	public void invalid_checking_command_with_space_in_middle() {
		boolean actual = commandValidator.validate("create  checking 12345678 0.6");
		assertFalse(actual);
	}

	@Test
	public void valid_cd_command_with_space_after() {
		boolean actual = commandValidator.validate("create cd 12345678 0.6 1000 ");
		assertTrue(actual);
	}

	@Test
	public void valid_cd_command_with_2_spaces_after() {
		boolean actual = commandValidator.validate("create cd 12345678 0.6 1000  ");
		assertTrue(actual);
	}

	@Test
	public void invalid_cd_command_with_space_before() {
		boolean actual = commandValidator.validate(" create cd 12345678 0.6 1000");
		assertFalse(actual);
	}

	@Test
	public void invalid_cd_command_with_space_in_middle() {
		boolean actual = commandValidator.validate("create  cd 12345678 0.6 1000");
		assertFalse(actual);
	}

	@Test
	public void valid_create_command_for_checking_case_insensitive() {
		boolean actual = commandValidator.validate("creAte cHecKing 98765432 0.01");
		assertTrue(actual);
	}

	@Test
	public void valid_create_command_for_savings_case_insensitive() {
		boolean actual = commandValidator.validate("create sAVINGs 98765432 0.01");
		assertTrue(actual);
	}

	@Test
	public void valid_create_command_for_cd_case_insensitive() {
		boolean actual = commandValidator.validate("create cD 98765432 0.01 1000");
		assertTrue(actual);
	}

	@Test
	public void valid_create_command_0_id() {
		boolean actual = commandValidator.validate("create savings 00000000 0.01");
		assertTrue(actual);
	}
}
