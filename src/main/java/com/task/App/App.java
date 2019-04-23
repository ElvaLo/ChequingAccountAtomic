package com.task.App;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.task.ChequingAccount.*;
//import java.io.IOException;

public class App extends Thread {
	public static void main(String args[]) {
/*
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./file1.txt"))){
			for (int i=1; i<=1000; i++) {
				bw.write(String.valueOf(-i));
				bw.newLine();
			}
			
		}catch(IOException e) {
			System.out.println(e.getClass().getSimpleName() + "-" + e.getMessage());
		}
		
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./file2.txt"))){
			for (int i=1; i<=999; i++) {
				bw.write(String.valueOf(i));
				bw.newLine();
			}
			
		}catch(IOException e) {
			System.out.println(e.getClass().getSimpleName() + "-" + e.getMessage());
		}
*/
		String[] inFiles = { "./file2.txt", "./file1.txt", "./file4.txt", "./file3.txt"};
		ChequingAccount account = new ChequingAccount(12345, 5000);

		Thread[] threads = new Thread[inFiles.length];
		
		//final long startTime = System.currentTimeMillis();
		long start = System.nanoTime();
		for (int i = 0; i < inFiles.length; i++) {
			Transaction transaction = new Transaction(inFiles[i], account);
			threads[i] = new Thread(transaction);
			threads[i].start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//final long endTime = System.currentTimeMillis();
		//System.out.println("Total execution time: " + (endTime - startTime));
		long time = System.nanoTime() - start;
		System.out.printf("Each took an average of %.1f ns%n", (double) time/4000);
		account.displayDetails();

	}
}