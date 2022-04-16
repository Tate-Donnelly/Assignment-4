import converter.ElbonianArabicConverter;
import converter.exceptions.MalformedNumberException;
import converter.exceptions.ValueOutOfBoundsException;
import converter.tests.ConverterTests;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws MalformedNumberException, ValueOutOfBoundsException{
		//Result result = JUnitCore.runClasses(ConverterTests.class);
		//System.out.println(result.getFailureCount());
		
		Scanner keyboard = new Scanner(System.in);
		String input = keyboard.nextLine();
		ElbonianArabicConverter elbonianArabicConverter = new ElbonianArabicConverter(input);
		
		System.out.print(elbonianArabicConverter.toArabic());
		
		
	}
}
