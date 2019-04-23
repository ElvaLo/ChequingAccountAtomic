package com.task.App;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.task.ChequingAccount.*;

public class AppAtomic extends Thread{
	public static void main(String args[]) {

		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./file1.txt"))){
			for (int i=1; i<=1000; i++) {
				bw.write(String.valueOf(1));
				bw.newLine();
			}
			
		}catch(IOException e) {
			System.out.println(e.getClass().getSimpleName() + "-" + e.getMessage());
		}
		
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./file2.txt"))){
			for (int i=1; i<=1000; i++) {
				bw.write(String.valueOf(-1));
				bw.newLine();
			}
			
		}catch(IOException e) {
			System.out.println(e.getClass().getSimpleName() + "-" + e.getMessage());
		}
		
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./file3.txt"))){
			for (int i=1; i<=1000; i++) {
				bw.write(String.valueOf(1));
				bw.newLine();
			}
			
		}catch(IOException e) {
			System.out.println(e.getClass().getSimpleName() + "-" + e.getMessage());
		}
		
		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./file4.txt"))){
			for (int i=1; i<=1000; i++) {
				bw.write(String.valueOf(1));
				bw.newLine();
			}
			
		}catch(IOException e) {
			System.out.println(e.getClass().getSimpleName() + "-" + e.getMessage());
		}

		String[] inFiles = { "./file2.txt", "./file1.txt", "./file4.txt", "./file3.txt" };
		ChequingAccountAtomic account = new ChequingAccountAtomic(12345, new AtomicLong(5000));

		Thread[] threads = new Thread[inFiles.length];

		//final long startTime = System.currentTimeMillis();
		long start = System.nanoTime();
		
		for (int i = 0; i < inFiles.length; i++) {
			TransactionAtomic transaction = new TransactionAtomic(inFiles[i], account);
			threads[i] = new Thread(transaction);
			threads[i].start();
		}
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} 
		  
		
		long time = System.nanoTime() - start;
		System.out.printf("Each took an average of %.1f ns%n", (double) time/4000);
		account.displayDetails();

	}

}
