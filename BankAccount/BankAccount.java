import java.util.*;

/**
 * Simple class to implement a bank account
 * @author Ben
 *
 */
public class BankAccount {
	
	private int balance;
	public BankAccount()
	{
		this.balance = 0;
	}
/**
 * deposit amount into bank account
 * @param amount
 * @pre amount > 0 - sets up a contract between someone writing code and someone using code, if pre is satisfied post is satisfied
 * @post 
 */
	public void deposit(int amount)
	{ // if(amount <= 0) throw new BankAccountBalance() --> this would be a new contract
		balance += amount; // Don't need if statement (already pre condition hold)
	}
	
	/**
	 * withdraw amount from bank account
	 * @param account
	 * @pre (amount > 0) and (balance >= amount)
	 */
	public boolean withdraw(int amount)
	{
			balance -= amount;
	}
	public int getBalance() {
		return balance;
	}
}
