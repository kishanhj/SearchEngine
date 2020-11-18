package dataStructures;

import java.util.*;

/**
 * Main Compressed Trie data structure
 * 
 * @author Kishan Huliyar Jagadeesh
 *
 */
public class Trie {

	/**
	 * holds the current trie object to ensure singleton pattern
	 */
	private static Trie trie;
	
	/**
	 * Trie which holds stop words 
	 */
	private Trie stopTrie;
	
	/**
	 *  This variable points to the root of the trie
	 */
	private TrieNode root;
	
	/**
	 * This variable maintains the current index value 
	 */
	private int nextIndex;
	
	/**
	 * Holds the webpage currenty being parsed by the trie
	 */
	private WebPage webPage;
	
	/**
	 * Holds the reference to the Array of words;
	 */
	private WordArray wordArray;

	/**
	 * Private class which holds the structure of the Compressed Trie node
	 * @author kashh
	 *
	 */
	private class TrieNode {
		public int index;
		public List<Character> data;
		public Map<Character,TrieNode> children;

		public TrieNode(int index,List<Character> data) {
			this.index = index;
			this.data = data;
			this.children = new HashMap<Character, Trie.TrieNode>();
		}

		@Override
		public String toString() {
			return "TrieNode [index=" + index + ", data=" + data + ", children=" + children + "]";
		}
		

	}
	
	/**
	 * Returns the instance of the trie and ensures singleton pattren
	 * 
	 * @return Trie
	 */
	public static Trie getInstance() {
		if(null == trie)
			trie = new Trie();
		return trie;
	}

	/**
	 * Constructor 
	 */
	private Trie() {
		root = new TrieNode(-1,new ArrayList<Character>());
		nextIndex = 0;
		wordArray = WordArray.getInstance();
		buildStopTrie();
	}
	
	/**
	 * Builds the stop trie
	 */
	private void buildStopTrie() {
		stopTrie = new Trie(true);
		stopTrie = new DataUtils().buildStopWordsTrie(stopTrie);
	}

	/**
	 * Constructor for building stop trie
	 * @param st
	 */
	private Trie(boolean isStopTrie) {
		root = new TrieNode(-1,new ArrayList<Character>());
		nextIndex = 0;
	}

	/**
	 * Adds a word to the trie
	 * @param word
	 * @return boolean
	 */
	public boolean addWord(String word) {
		if(addWordRec(root, word.toLowerCase().toCharArray())) {
			if(null != wordArray)
			wordArray.addWord(nextIndex-1,word,webPage, 0);
			return true;
		}
		return false;
	}
	
	/**
	 * Recursively adds word
	 * @param node
	 * @param word
	 * @return boolean
	 */
	private boolean addWordRec(TrieNode node,char[] word) {
		
		if(node.index == -1) {
			char c = word[0];
			TrieNode curNode = node.children.get(c);
			if(null == curNode) {
				ArrayList<Character> nodeData = new ArrayList<Character>();
				for(char ch: word)
					nodeData.add(ch);
				TrieNode newNode = new TrieNode(nextIndex++, nodeData);
				node.children.put(c, newNode);
				return true;
			}
			return addWordRec(curNode, word);
		}
		
		int i=0,j=0;
		int nodeLen = node.data.size();
		int wordLen = word.length;
		
		while(i < nodeLen && j < wordLen) {
			if(word[i] != node.data.get(j)) {
				break;
			}
			i++;
			j++;
		}
		
		if(j == wordLen && i == nodeLen) {
			if(node.index == -2) {
				node.index = nextIndex++;
				return true;
			}
			//System.out.println("word already exist at index "+node.index+" "+node.data);
			if(null != wordArray)
			wordArray.updateWord(node.index, webPage, 0);
			return false;
		}
		
		if(i == nodeLen && j < wordLen) {
			char c = word[j];
			TrieNode curNode = node.children.get(c);
			if(null == curNode) {
				ArrayList<Character> nodeData = new ArrayList<Character>();
				char[] newword = Arrays.copyOfRange(word, j, wordLen);
				for(char ch: newword)
					nodeData.add(ch);
				TrieNode newNode = new TrieNode(nextIndex++, nodeData);
				node.children.put(c, newNode);
				return true;
			}
			
			char[] newword = Arrays.copyOfRange(word, j, wordLen);
			return addWordRec(curNode, newword);
		}
		
		if(i < nodeLen && j < wordLen) {
			List<Character> splitDataChild = node.data.subList(i, nodeLen);
			TrieNode splitDataChildNode = new TrieNode(node.index, splitDataChild);
			for(Character n:node.children.keySet()) {
				splitDataChildNode.children.put(n, node.children.get(n));
			}
			
			
			char[] newword = Arrays.copyOfRange(word, j, wordLen);
			ArrayList<Character> nodeData = new ArrayList<Character>();
			for(char ch: newword)
				nodeData.add(ch);
			TrieNode newNode = new TrieNode(nextIndex++, nodeData);
			
			List<Character> splitDataParent = node.data.subList(0, i);
			node.index = -2;
			node.data = splitDataParent;
			node.children.clear();
			node.children.put(splitDataChild.get(0), splitDataChildNode);
			node.children.put(newword[0], newNode);
			
			return true;
		}
		
		if(j == wordLen && i < nodeLen) {
			
			List<Character> splitDataChild = node.data.subList(i, nodeLen);
			TrieNode splitDataChildNode = new TrieNode(node.index, splitDataChild);
			for(Character n:node.children.keySet()) {
				splitDataChildNode.children.put(n, node.children.get(n));
			}
			
			List<Character> splitDataParent = node.data.subList(0, i);
			node.index = nextIndex++;
			node.data = splitDataParent;
			node.children.clear();
			node.children.put(splitDataChild.get(0), splitDataChildNode);
			
			return true;
		}
		
		
		
		return false;
	}
	
	/**
	 * Searches a word 
	 * @param word
	 * @return
	 */
	public WordIndex searchWordArray(String word) {
		int index =  searchRec(root,word.toLowerCase().toCharArray());
		if(index > -1 && null != wordArray)
			return wordArray.get(index);
		return null;
	}
	
	/**
	 * Searches a word in a trie
	 * @param word
	 * @return
	 */
	public int searchTrieIndex(String word) {
		return searchRec(root,word.toLowerCase().toCharArray());
	}
	
	/**
	 * Searches a word recursively
	 * @param node
	 * @param word
	 * @return
	 */
	private int searchRec(TrieNode node,char[] word) {
		int i=0,j=0;
		int nodeLen = node.data.size();
		int wordLen = word.length;
		
		while(i < nodeLen && j < wordLen) {
			if(word[i] != node.data.get(j)) {
				return -1;
			}
			i++;
			j++;
		}
		
		if(j == wordLen && i == nodeLen)
			return node.index;
		
		if(i == nodeLen) {
			char nextChar = word[j];
			TrieNode nextNode = node.children.get(nextChar);
			if(null == nextNode)
				return -1;
			
			char[] newword = Arrays.copyOfRange(word, j, wordLen);
			
			return searchRec(nextNode, newword);
		}
		
		return -1;
	}
	
	public void print() {
		printRec(root);
	}
	
	public void addWebPage(WebPage webpage,String[] words) {
		int i = 0;
		this.webPage = webpage;
		for(String s:words) {
			s = s.toLowerCase();
			if(stopTrie.searchTrieIndex(s) > -1)
				continue;
			
			if(addWord(s)) {
				//System.out.println(i+":"+s);
				i++;
			}
		}
		this.webPage = null;
	}
	
	
	private void printRec(TrieNode node) {
		System.out.println("--------------------------");
		System.out.println(node);
		System.out.println("--------------------------");
		
		node.children.values().forEach(e -> printRec(e));
	}
	



}
