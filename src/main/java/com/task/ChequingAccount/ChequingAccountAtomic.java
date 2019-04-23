package com.task.ChequingAccount;

import java.util.concurrent.atomic.*;

public class ChequingAccountAtomic {
	private int accountNum;
	private AtomicLong balance;

	/* CreditCard constructor */
	public ChequingAccountAtomic(int accountNum1) {
		this.accountNum = accountNum1;
		balance = new AtomicLong(0);
	}

	/* CreditCard constructor */
	public ChequingAccountAtomic(int accountNum2, AtomicLong amount) {
		this.accountNum = accountNum2;
		balance = amount;
	}

	/* The withdraw method is one of the chequing account activity */
	public void withdraw(long amount) {
		boolean flag = true;
		while (flag) {
			long existingBalance = getValue();
			if (existingBalance - amount >= 0) { // check if balance is sufficient
				long newBalance = existingBalance - amount;
				if (balance.compareAndSet(existingBalance, newBalance)) {
					//System.out.println("withdraw completed...");
					return;
				}
			}
			if (existingBalance - amount < 0)
			System.out.println("Balance Insufficient; waiting for deposit...");
		}

	}

	/* The withdraw method is one of the chequing account activity */
	public void deposit(long amount) {

		while (true) {
			long existingBalance = getValue();
			long newBalance = existingBalance + amount;
			if (balance.compareAndSet(existingBalance, newBalance)) {
				//System.out.println("Deposit complete successfully...");
				return;
			}
		}
	}

	/* displayDetails() method prints the details of a DebitCard instance */
	public void displayDetails() {
		// System.out.println("Account number is " + accountNum);
		System.out.println("Balance is " + balance);
	}

	/* getAccountNum() method returns the account number */
	public int getAccountNum() {
		return accountNum;
	}

	/* getAccountNum() method returns the account balance */
	public long getValue() {
		return balance.get();
	}

}
