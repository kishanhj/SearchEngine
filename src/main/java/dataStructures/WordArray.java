package dataStructures;

import java.util.ArrayList;

/**
 * Word array class stores word index class
 * @author kishan huliyar jagadeesh
 *
 */
public class WordArray {

	
	private static WordArray wordArray;
	private ArrayList<WordIndex> data;
	
	public static WordArray getInstance() {
		if(null == wordArray)
			wordArray = new WordArray();
		return wordArray;
	}
	
	private WordArray() {
		data = new ArrayList<WordIndex>();
	}
	
	public WordIndex get(int index) {
		if(null == data || index < 0 || index >= data.size())
			throw new IndexOutOfBoundsException();
		
		return data.get(index); 
	}
	
	public boolean addWord(int index,String word,WebPage webPage,int wordIndex) {
		 data.add(index, new WordIndex(word, webPage, wordIndex, false));
		 return true;
	}
	
	public boolean updateWord(int index,WebPage webPage,int wordIndex) {
		 WordIndex wordIndexObj = data.get(index);
		 wordIndexObj.addPage(webPage, wordIndex, false);
		 return true;
	}
	
	
	
}
