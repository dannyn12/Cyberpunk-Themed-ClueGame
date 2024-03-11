/*
 * Author: Jordan Lam & Danny Nguyen
 * This is the BadConfigFormatException class this class will give error messages and log them to explain why
 * the file was not properly initalized
 */

package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	/*
	 * short exception message
	 */
	public BadConfigFormatException() {
		super("Error: Bad Config Format Excpetion");
	}
	
	/*
	 * Exception message for reasoning
	 */
	public BadConfigFormatException(String reason) {
		super(reason);
		
		PrintWriter write;
		try {
			write = new PrintWriter("BadConfigLogFile.txt");
			write.println(reason);
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}