/*
 * Author: Jordan Lam & Danny Nguyen
 * This is the BadConfigFormatException class *insert information about class*
 */

//todo
package clueGame;

<<<<<<< HEAD

=======
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
	
	
>>>>>>> 7709d7f88a29145671d9959e4272e46c03598910
}