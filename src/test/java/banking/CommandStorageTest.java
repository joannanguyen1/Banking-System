package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	CommandStorage commandStorage;
	Bank bank;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage(bank);
	}

	@Test
	public void command_storage_has_no_commands() {
		List<String> allCommands = commandStorage.getInvalidCommands();

		assertEquals(0, allCommands.size());
	}

	@Test
	public void command_storage_storing_an_invalid_command() {
		commandStorage.addInvalidCommand("deposit 100 12345678");
		List<String> allCommands = commandStorage.getInvalidCommands();

		assertEquals(1, allCommands.size());
		assertTrue(allCommands.contains("deposit 100 12345678"));
	}

	@Test
	public void command_storage_storing_an_invalid_command_account_type() {
		commandStorage.addInvalidCommand("unknown 100 12345678");
		List<String> allCommands = commandStorage.getInvalidCommands();

		assertEquals(1, allCommands.size());
		assertTrue(allCommands.contains("unknown 100 12345678"));
	}

	@Test
	public void command_storage_storing_two_invalid_commands() {
		commandStorage.addInvalidCommand("deposit 100 12345678");
		commandStorage.addInvalidCommand("creat savings 12345678 0.5");
		List<String> allCommands = commandStorage.getInvalidCommands();

		assertEquals(2, allCommands.size());
		assertTrue(allCommands.contains("deposit 100 12345678"));
		assertTrue(allCommands.contains("creat savings 12345678 0.5"));
	}

	@Test
	public void command_storage_storing_multiple_invalid_commands() {
		commandStorage.addInvalidCommand("deposit 100 12345678");
		commandStorage.addInvalidCommand("creat savings 12345678 0.5");
		commandStorage.addInvalidCommand("create 12345678 checking 0.5");
		commandStorage.addInvalidCommand("deposi 12345678 500");
		List<String> allCommands = commandStorage.getInvalidCommands();

		assertEquals(4, allCommands.size());
		assertTrue(allCommands.contains("deposit 100 12345678"));
		assertTrue(allCommands.contains("creat savings 12345678 0.5"));
		assertTrue(allCommands.contains("create 12345678 checking 0.5"));
		assertTrue(allCommands.contains("deposi 12345678 500"));
	}

	@Test
	public void command_storage_storing_a_valid_command() {
		commandStorage.addValidCommand("deposit 12345678 100");
		List<String> allCommands = commandStorage.getValidCommands();

		assertEquals(1, allCommands.size());
		assertTrue(allCommands.contains("deposit 12345678 100"));
	}

	@Test
	public void command_storage_storing_2_valid_commands() {
		commandStorage.addValidCommand("deposit 12345678 100");
		commandStorage.addValidCommand("transfer 11111111 12345678 100");
		List<String> allCommands = commandStorage.getValidCommands();

		assertEquals(2, allCommands.size());
		assertTrue(allCommands.contains("deposit 12345678 100"));
		assertTrue(allCommands.contains("transfer 11111111 12345678 100"));
	}

	@Test
	public void command_storage_storing_multiple_valid_commands() {
		commandStorage.addValidCommand("deposit 12345678 100");
		commandStorage.addValidCommand("transfer 11111111 12345678 100");
		commandStorage.addValidCommand("withdraw 87654321 100");
		List<String> allCommands = commandStorage.getValidCommands();

		assertEquals(3, allCommands.size());
		assertTrue(allCommands.contains("deposit 12345678 100"));
		assertTrue(allCommands.contains("transfer 11111111 12345678 100"));
		assertTrue(allCommands.contains("withdraw 87654321 100"));
	}

	@Test
	public void command_storage_storing_a_valid_and_an_invalid_command() {
		commandStorage.addValidCommand("deposit 12345678 100");
		commandStorage.addInvalidCommand("depo 11111111 600");
		List<String> allCommands = commandStorage.getValidCommands();

		assertEquals(1, allCommands.size());
		assertTrue(allCommands.contains("deposit 12345678 100"));
	}
}
