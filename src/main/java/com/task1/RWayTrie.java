package com.task1;

import java.util.*;

/**
 * Class, that represents trie-way to hold dictionary
 *
 * @author Dennis
 *
 * on 3/16/2016.
 */
public class RWayTrie implements Trie {

	/*
	 * Constants
	 */
	private final static int NODE_CAPACITY = 26;
	private final static int UNICODE_SHIFT = 97;

	/*
	 * Size of dictionary
	 */
	private int size;

	/*
	 * Variable to ensure data consistency
	 */
	private volatile int consistencyCount;

	/*
	 * Root node
	 */
	private final Node root = new Node();

	/**
	 * Add word - weight pair into dictionary
	 *
	 * @param tuple Word - weight pair
     */
	public void add(Tuple tuple) {
		if (tuple == null) {
			return;
		}

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
		consistencyCount++;
    }

	/*
	 * Finding node, after specified word
	 */
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

	/**
	 * Check, is the specified word present in dictionary
	 *
	 * @param word Keyword to find
	 * @return true if keyword presented, false otherwise
	 */
    public boolean contains(String word) {
		if (word == null) {
			return false;
		}
    	
    	Node node = findNode(word);

		return node != null && node.value != 0;
    }

	/**
	 * Delete word from dictionary
	 *
	 * @param word Word to delete
	 * @return Result of deletion
	 */
    public boolean delete(String word) {
		if (word == null) {
			return false;
		}

		Node node = findNode(word);

		if (node != null && node.value != 0) {
			node.value = 0;
			size--;
			consistencyCount++;
			return true;
		}

        return false;
    }

	/**
	 * List of all words in dictionary
	 *
	 * @return All words
     */
    public Iterable<String> words() {
        return findWords(root, "");
    }

	/**
	 * List of all words in dictionary with specified prefix
	 *
	 * @param prefix Prefix to filter words
	 * @return List of words with given prefix
     */
    public Iterable<String> wordsWithPrefix(String prefix) {
		if (prefix == null) {
			return Collections.emptyList();
		}

		Node node = findNode(prefix);

		return findWords(node, prefix);
    }

    //TODO lazy iterator
	/*
	 * Method to find words in subtree of specified node
	 */
	private Iterable<String> findWords(Node node, String prefix) {
		if (node == null) {
			return Collections.EMPTY_LIST;
		}

		return new Iterable<String>() {
			@Override
			public Iterator<String> iterator() {
				return new TrieIterator(node, prefix);
			}
		};
		
//		private Iterable<String> findWords(Node node) {
//
//			List<String> words = new ArrayList<>();
//
//			if (node == null) {
//				return words;
//			}
//
//			Queue<PrefixContainer> containers = new LinkedList<>();
//
//			containers.offer(new PrefixContainer("", node));
//
//			while (!containers.isEmpty()) {
//				PrefixContainer container = containers.poll();
//				String prefix = container.prefix;
//
//				for (int i = 0; i < NODE_CAPACITY; i++) {
//					Node n = container.node.next[i];
//
//					if (n != null) {
//						String pref = prefix + ((char) (i + UNICODE_SHIFT));
//
//						containers.offer(new PrefixContainer(pref, n));
//
//						if (n.value != 0) {
//							words.add(pref);
//						}
//					}
//				}
//			}
//
//			return words;
	}

	/**
	 * Amount of words in tree
	 *
	 * @return Tree size
     */
    public int size() {
        return size;
    }
    
    /*
     * Node representation
     */
    private static class Node {
    	private int value;
    	private Node[] next = new Node[NODE_CAPACITY];
    }

	/*
	 * Container class to hold Node and Prefix
	 */
	private static class PrefixContainer {
		private String prefix;
		private Node node;

		public PrefixContainer(String prefix, Node node) {
			this.prefix = prefix;
			this.node = node;
		}
	}

	private class TrieIterator implements Iterator<String> {
		private Node node;
		private final String prefix;
		private final int consistency;
		private final Queue<PrefixContainer> containers = new LinkedList<>();

		public TrieIterator(Node node, String prefix) {
			this.node = node;
			this.prefix = prefix;
			this.consistency = consistencyCount;

			containers.offer(new PrefixContainer(prefix, node));
		}

		@Override
		public boolean hasNext() {
			checkConsistency();

			return !containers.isEmpty();
		}

		@Override
		public String next() {
			checkConsistency();

			while (!containers.isEmpty()) {
				PrefixContainer container = containers.poll();
				String word = container.prefix;
				Node tempNode = container.node;

				for (int i = 0; i < NODE_CAPACITY; i++) {
					Node n = tempNode.next[i];

					if (n != null) {
						String pref = word + ((char) (i + UNICODE_SHIFT));

						containers.offer(new PrefixContainer(pref, n));
					}
				}

				if (tempNode.value != 0) {
					return word;
				}
			}

			return null;
		}

		private void checkConsistency() {
			if (consistency != consistencyCount) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
