package SearchEngine.SearchEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import dataStructures.Trie;
import dataStructures.WebPage;
import dataStructures.WordIndex;
import dataStructures.config;
import datagen.WebpageParser;

/**
 * Hello world!
 *
 */
public class App 
{
	
	static PrintStream originalOut;
	
	public static void main( String[] args )
	{
		Trie tr = Trie.getInstance();
		WebpageParser.scrapeWebPage(config.webpageStart,true,tr);
		String outputLoc = config.outputLocation+"\\output.txt";
		
		try {
        originalOut = System.out;
        PrintStream fileOut = new PrintStream(outputLoc);
        System.setOut(fileOut);
		File output = new File(outputLoc);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		search(tr);

	}

	private static Scanner searchWordScanner =new Scanner(System.in);

	private static void search(Trie tr){
		String arg = null;
		String searchWord = null;

		originalOut.println("-------------------------------------------");
		originalOut.println("Enter a word and end it with  ;");
		
		System.out.println("-------------------------------------------");
		System.out.println("Enter a word and end it with  ;");
		while(searchWordScanner.hasNextLine() && !(arg=searchWordScanner.next()).equals(";") ){
			searchWord = arg;
		}
		
		System.out.println("Searched for "+searchWord);
		originalOut.println("Searched for "+searchWord);
		
		if(validate(searchWord)) {
		WordIndex wr = tr.searchWordArray(searchWord);
		printResult(wr);
		} else {
			originalOut.println("Invalid input.Please enter a word with only alphabets");
			System.out.println("Invalid input.Please enter a word with only alphabets");
		}
		
		
		search(tr);
	}

	private static void printResult(WordIndex wr) {
		if(null == wr) {
			System.out.println("No results found");
			originalOut.println("No results found");
		}
		else {
		System.out.println(wr);
		originalOut.println(wr);
		}
	}

	private static boolean validate(String arg) {
		if(null == arg || arg.length() == 0 || " ".equals(arg))
			return false;
		
		char[] wordArr = arg.toLowerCase().toCharArray();
		for(char alphabet: wordArr) {
			if(Character.isAlphabetic(alphabet) || Character.isDigit(alphabet))
				continue;
			return false;
		}
		
		return true;
	}
}
