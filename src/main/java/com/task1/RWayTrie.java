package com.task1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Dennis
 *
 * on 3/16/2016.
 */
public class RWayTrie implements Trie {
	
	private final static int NODE_CAPACITY = 26;
	private final static int UNICODE_SHIFT = 97;
	
	private int size;
	
	private final Node root = new Node();

	public void add(Tuple tuple) {
    	String word = tuple.getTerm();

    	Node node = root;

		for (int i = 0; i < word.length(); i++) {
			int index = word.charAt(i) - UNICODE_SHIFT;

			if (node.next[index] == null) {
				node.next[index] = new Node();
			}

			node = node.next[index];
		}

		node.value = tuple.getWeight();
    	
    	size++;
    }

	private Node findNode(String word) {
		Node node = root;

		for (int i = 0; i < word.length(); i++) {
			int index = word.charAt(i) - UNICODE_SHIFT;

			if (node.next[index] == null) {
				return null;
			}

			node = node.next[index];
		}

		return node;
	}

    public boolean contains(String word) {
    	
    	Node node = findNode(word);

		return node != null && node.value != 0;
    }

    public boolean delete(String word) {

		Node node = findNode(word);

		if (node != null && node.value != 0) {
			node.value = 0;
			size--;
			return true;
		}

        return false;
    }

    public Iterable<String> words() {
        return findWords(root);
    }

    public Iterable<String> wordsWithPrefix(String prefix) {
		Node node = findNode(prefix);

		List<String> words = (List<String>) findWords(node);

		if (contains(prefix)) {
			words.add(0, "");
		}

		for (int i = 0; i < words.size(); i++) {
			words.set(i, prefix + words.get(i));
		}

        return words;
    }

	private Iterable<String> findWords(Node node) {

		List<String> words = new ArrayList<>();

		if (node == null) {
			return words;
		}

		Queue<PrefixContainer> containers = new LinkedList<>();

		containers.offer(new PrefixContainer("", node));

		while (!containers.isEmpty()) {
			PrefixContainer container = containers.poll();
			String prefix = container.prefix;

			for (int i = 0; i < NODE_CAPACITY; i++) {
				Node n = container.node.next[i];

				if (n != null) {
					String pref = prefix + ((char) (i + UNICODE_SHIFT));

					containers.offer(new PrefixContainer(pref, n));

					if (n.value != 0) {
						words.add(pref);
					}
				}
			}
		}

		return words;
	}

    public int size() {
        return size;
    }
    
    /*
     * Node representation
     */
    private class Node {
    	private int value;
    	private Node[] next = new Node[NODE_CAPACITY];
    }

	private class PrefixContainer {
		private String prefix;
		private Node node;

		public PrefixContainer(String prefix, Node node) {
			this.prefix = prefix;
			this.node = node;
		}
	}
}
