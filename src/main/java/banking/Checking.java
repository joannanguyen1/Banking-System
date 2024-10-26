package banking;

public class Checking extends Account {
	double balance;

	public Checking(String ID, double APR) {
		super(ID, APR);
		balance = 0;
	}

	@Override
	public boolean isChecking() {
		return true;
	}
}
