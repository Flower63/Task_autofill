package com.task1;
/**
 * Created by Dennis
 *
 * on 3/16/2016.
 */
public class RWayTrie implements Trie {
	
	private final static int R = 26;
	
	private int size;
	
	private final Node root = new Node(' ');

	public void add(Tuple tuple) {
    	//String word = tuple.getTerm();
    	char[] letters = tuple.getTerm().toCharArray();
    	Node node = root;
    	
    	for (char ch : letters) {
    		for(int i = 0; i < node.next.length; i++) {
    			Node n = node.next[i];
    			
    			//Out of nodes
    			if (n == null) {
    				node.next[i] = new Node(ch);
    				node = node.next[i];
    				break;
    			}
    			
    			//Such node exists
    			if (n.key == ch) {
    				node = n;
    				break;
    			}
    		}
    	}
    	
    	size++;
    }

    public boolean contains(String word) {
    	
    	Node node = root;
    	char[] letters = word.toCharArray();
    	
    	for(int i = 0; i < letters.length; i++) {
    		
    		char ch = letters[i];
    		
    		for(int j = 0; j < node.next.length; j++) {
    			Node n = node.next[j];
    			
    			//Out of nodes
    			if (n == null) {
    				return false;
    			}
    			
    			//Such node exists
    			if (n.key == ch) {
    				node = n;
    				break;
    			}
    		}
    	}
    	
        return true;
    }

    public boolean delete(String word) {
        return false;
    }

    public Iterable<String> words() {
        return null;
    }

    public Iterable<String> wordsWithPrefix(String prefix) {
        return null;
    }

    public int size() {
        return size;
    }
    
    /*
     * Node representation
     */
    private class Node {
    	//private String key;
    	private char key;
    	private int value;
    	private Node[] next = new Node[R];
    	
    	Node(char val) {
    		this.key = val;
    	}
    }
}
