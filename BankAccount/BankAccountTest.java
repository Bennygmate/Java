import static org.junit.Assert.*;

import org.junit.Test;

public class BankAccountTest {
	@Test
	public void testBankAccount() {
		BankAccount b = new BankAccount();
		assertEquals(b.getBalance(), 0);
	}
	@Test
	public void testDeposit() {
		BankAccount b = new BankAccount();
		b.deposit(100);
		assertEquals(b.getBalance(), 100);
	}

	@Test
	public void testWithdraw() {
		fail("Not yet implemented");
	}

}
