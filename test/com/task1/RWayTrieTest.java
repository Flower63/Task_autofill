package com.task1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dennis
 *
 * on 3/16/2016.
 */
public class RWayTrieTest {

    @Test
    public void testAdd() throws Exception {
        Trie trie = new RWayTrie();

        assertTrue(trie.size() == 0);

        trie.add(new Tuple("abc"));
        trie.add(new Tuple("def"));

        assertTrue(trie.contains("abc"));

        assertTrue(!trie.contains("abcd"));

        assertTrue(trie.size() == 2);
    }

    @Test
    public void testContains() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        Trie trie = new RWayTrie();

        trie.add(new Tuple("abc"));
        trie.add(new Tuple("def"));

        assertTrue(trie.delete("abc"));

        assertFalse(trie.contains("abc"));

        assertFalse(trie.delete("abc"));

        assertTrue(trie.size() == 1);
    }

    @Test
    public void testWords() throws Exception {
        Trie trie = new RWayTrie();

        trie.add(new Tuple("abc"));
        trie.add(new Tuple("def"));
        trie.add(new Tuple("link"));
        trie.add(new Tuple("privacy"));
        trie.add(new Tuple("group"));
        trie.add(new Tuple("the"));

        for (String s : trie.words()) {
            System.out.println(s);
        }

        System.out.println();

        trie.delete("abc");

        for (String s : trie.words()) {
            System.out.println(s);
        }
    }

    @Test
    public void testWordsWithPrefix() throws Exception {
        Trie trie = new RWayTrie();

        trie.add(new Tuple("abc"));
        trie.add(new Tuple("abf"));
        trie.add(new Tuple("abcde"));
        trie.add(new Tuple("abcxxxx"));
        trie.add(new Tuple("adcf"));
        trie.add(new Tuple("group"));
        trie.add(new Tuple("the"));

        for (String s : trie.wordsWithPrefix("abc")) {
            System.out.println(s);
        }
    }

    @Test
    public void testSize() throws Exception {

    }
}