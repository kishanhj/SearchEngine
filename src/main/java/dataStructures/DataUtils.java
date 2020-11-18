package dataStructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataUtils {

	public static Trie buildStopWordsTrie(Trie stopTrie) {
		try {
			File file = new File(config.stopWordLocation); 
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st = ""; 
			while ((st = br.readLine()) != null) 
				stopTrie.addWord(st);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stopTrie;
	}

	public String charArrayToString(char[] word) {
		String s ="";
		for(char c: word)
			s+=c;
		return s;
	}


}
