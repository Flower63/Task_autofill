package com.task1;

/**
 * Created by Dennis
 *
 * on 3/15/2016.
 */
public class PrefixMatches {
	private static final String SPACE_LITERAL = " ";
	private static final int LENGTH_THRESHOLD = 2;
	
    private Trie trie;

    public PrefixMatches(Trie trie) {
		this.trie = trie;
	}
    
    public PrefixMatches() {}

	public int add(String... strings) {
    	
    	int counter = 0;
    	
    	if(strings == null) {
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

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public int size() {
        return trie.size();
    }

    //TODO
    public Iterable<String> wordsWithPrefix(String pref, int k) {
        return null;
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }
}
