package dataStructures;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Is the main data holds the indexes of a word
 * @author kishan huliyar jagadeesh
 *
 */
public class WordIndex {
	
	/**
	 * 
	 */
	String word;
	HashMap<WebPage, PageIndexes> maps = new HashMap<WebPage, PageIndexes>();
	
	/**
	 * Constructor for word inex
	 * @param word
	 * @param webPage
	 * @param index
	 * @param isTitle
	 */
	public WordIndex(String word,WebPage webPage,int index,boolean isTitle) {
		this.word = word;
		PageIndexes pageIndex = new PageIndexes();
		pageIndex.add(index, isTitle);
		maps.put(webPage,pageIndex);
	}
	
	/**
	 * Adds a page
	 * 
	 * @param webPage
	 * @param index
	 * @param isTitle
	 * @return
	 */
	public boolean addPage(WebPage webPage,int index,boolean isTitle) {
		if(maps.keySet().contains(webPage)) {
			PageIndexes pageIndex = maps.get(webPage);
			pageIndex.add(index, isTitle);
			return true;
		} else {
			PageIndexes pageIndex = new PageIndexes();
			pageIndex.add(index, isTitle);
			maps.put(webPage, pageIndex);
			return true;
		}
	}

	@Override
	public String toString() {
		String s = word+" found in \n";
		for(Entry<WebPage, PageIndexes> entry : maps.entrySet()) {
			s += entry.getKey()+"\n";
		}
		return s;
	}

	

}
