package dataStructures;

import java.util.ArrayList;

/**
 * This class holds the indexes of a word in a specific webpage
 * @author Kishan Huliyar Jagadeesh
 *
 */
public class PageIndexes {

	/**
	 * holds indexes in page title tag
	 */
	ArrayList<Integer> titleIndexes;
	
	/**
	 * holds indexes in body tag
	 */
	ArrayList<Integer> bodyIndexes;
	
	public PageIndexes() {
		titleIndexes = new ArrayList<Integer>();
		bodyIndexes = new ArrayList<Integer>();
	}
	
	public boolean add(int index,boolean isTitle) {
		if(isTitle)
			return addTitleIndex(index);
		return addBodyIndex(index);
	}
	
	private boolean addTitleIndex(int index) {
		return titleIndexes.add(index);
	}
	
	private boolean addBodyIndex(int index) {
		return bodyIndexes.add(index);
	}

	@Override
	public String toString() {
		return "PageIndexes [titleIndexes=" + titleIndexes + ", bodyIndexes=" + bodyIndexes + "]";
	}
	
	
}
