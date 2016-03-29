package com.task1;

import java.util.*;

/**
 * Class, that represents auto-fill by given prefix
 *
 * @author Denys Zvarych
 *
 * on 3/15/2016.
 */
public class PrefixMatches {

	/*
	 * Constants
	 */
	private final static String SPACE_LITERAL = " ";
	private final static int LENGTH_THRESHOLD = 2;
	private final static int DEFAULT_SEARCH_LENGTH = 3;

	/*
	 * Trie instance
	 */
    private Trie trie;

	/**
	 * Parametrized constructor
	 *
	 * @param trie Trie implementation object
     */
    public PrefixMatches(Trie trie) {
		this.trie = trie;
	}

    /**
	 * Default constructor with RWayTrie implementation
	 */
    public PrefixMatches() {
		trie = new RWayTrie();
	}

	/**
	 * Creates in-memory dictionary.
	 *
	 * @param strings Input strings. May be words, 
	 * or couple of words,
	 * white-space separated
	 *
	 * @return Amount of accepted word
     */
	public int add(String... strings) {
    	
    	int counter = 0;
    	
    	if (strings == null) {
    		return 0;
    	}
    	
    	for (String s : strings) {
    		
    		if (s == null) { continue; }
    		
    		if (s.contains(SPACE_LITERAL)) {
    			for (String str : s.split(SPACE_LITERAL)) {
    				if (str.length() > LENGTH_THRESHOLD) {
    					trie.add(new Tuple(str));
    					counter++;
    				}
    			}
    		} else if (s.length() > LENGTH_THRESHOLD) {
    			trie.add(new Tuple(s));
				counter++;
    		}
    	}
    	
        return counter;
    }

	/**
	 * Check, is the specified word present in dictionary
	 *
	 * @param word Keyword to find
	 * @return true if keyword presented, false otherwise
     */
    public boolean contains(String word) {
        return trie.contains(word);
    }

	/**
	 * Delete word from dictionary
	 *
	 * @param word Word to delete
	 * @return Result of deletion
     */
    public boolean delete(String word) {
        return trie.delete(word);
    }

	/**
	 * Size of dictionary
	 *
	 * @return Size
     */
    public int size() {
        return trie.size();
    }

	/**
	 * List of words with specified prefix, limited 
	 * by number of length sets.
	 *
	 * 3 returns words of 3 different lengths, 4 - 
	 * of 4 different lengths, and so on
	 *
	 * @param pref Prefix
	 * @param k Amount of lengths
     * @return List of words within given restrictions
     */
    public Iterable<String> wordsWithPrefix(String pref, int k) {

//		if (pref == null || k == 0) {
//			return Collections.emptyList();
//		}
//
//		List<String> result = new ArrayList<>();
//		Iterable<String> source = trie.wordsWithPrefix(pref);
//		int wordLength = 0;
//
//		Iterator<String> iterator = source.iterator();
//
//		if (iterator.hasNext()) {
//			String init = iterator.next();
//			wordLength = init.length();
//			result.add(init);
//		}
//
//		while (iterator.hasNext()) {
//			String word = iterator.next();
//
//			if (word.length() != wordLength) {
//				k--;
//				wordLength = word.length();
//			}
//
//			if (k == 0) {
//				break;
//			}
//
//			result.add(word);
//		}
//
//		return result;
    	
    	return new Iterable<String>() {
			@Override
			public Iterator<String> iterator() {
				return new PrefixIterator(pref, k);
			}
    		
    	};
    }

	/**
	 * List of words with specified prefix, limited by 3 length sets
	 *
	 * @param pref Prefix
	 * @return List of words within given restrictions
     */
    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, DEFAULT_SEARCH_LENGTH);
    }
    
    private class PrefixIterator implements Iterator<String> {
    	private boolean available;
    	private final Iterator<String> trieIterator;
    	private String cache;
    	private final int wordsLength;
    	private int length;
    	private int timesChanged;
    	
		public PrefixIterator(String prefix, int wordsLength) {
			this.trieIterator = trie.wordsWithPrefix(prefix).iterator();
			this.wordsLength = wordsLength;
			
			if (trieIterator.hasNext()) {
				available = true;
				cache = trieIterator.next();
				length = cache.length();
			}
		}

		@Override
		public boolean hasNext() {
			return available;
		}

		@Override
		public String next() {
			if (!available) {
				throw new NoSuchElementException();
			}
			
			String word = cache;
			
			if (!trieIterator.hasNext()) {
				available = false;
			} else {
				cache = trieIterator.next();
				
				if (cache.length() != length) {
					length = cache.length();
					timesChanged++;
				}
			}
			
			if (timesChanged == wordsLength) {
				available = false;
			}
			
			return word;
		}
    }
}
