/*
 * Author: Jordan Lam & Danny Nguyen
 * This is the BadConfigFormatException class *insert information about class*
 */

//todo
package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	/*
	 * short exeption message
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