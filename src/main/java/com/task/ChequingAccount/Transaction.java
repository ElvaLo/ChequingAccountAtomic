package com.task.ChequingAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.task.ChequingAccount.ChequingAccount;

public class Transaction implements Runnable{
	
	private String inFile;
	private ChequingAccount chequing;
	
	public Transaction(String inFile, ChequingAccount account) {
		this.inFile = inFile;
		this.chequing = account;
	}
	
	public void action() throws IOException {
		String line = null;
		try(BufferedReader reader = Files.newBufferedReader(Paths.get(inFile))){
			while((line = reader.readLine()) != null) {
				double amount = Double.parseDouble(line);
				if (amount < 0) {
					amount = Math.abs(amount);
					chequing.withdraw(amount);
				}
				else if (amount > 0){
					chequing.deposit(amount);
				}
			}
		}		
	}

	
	public void run()
	{
		try {
			action();
		}catch(IOException e) {
			System.out.println(e.getClass().getSimpleName() + "-" + e.getMessage());
		}
	}
}
