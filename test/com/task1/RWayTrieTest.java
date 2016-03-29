package com.task1;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        List<String> content = Arrays.asList("abc", "def", "link", "privacy", "group", "the");

        for (String s : content) {
            trie.add(new Tuple(s));
        }

        for (String s : trie.words()) {
            assertTrue(content.contains(s));
        }

        trie.delete("abc");

        assertFalse(trie.contains("abc"));
    }

    @Test
    public void testWordsWithPrefix() throws Exception {
        String prefix = "abc";
        Trie trie = new RWayTrie();
        List<String> content = new LinkedList<>();

        //Must contain itself
        trie.add(new Tuple(prefix));
        content.add(prefix);

        //Doesn't match, shouldn't be in
        trie.add(new Tuple("abf"));

        //Match
        trie.add(new Tuple("abcde"));
        content.add("abcde");

        //Match
        trie.add(new Tuple("abcxxxx"));
        content.add("abcxxxx");

        //Doesn't match
        trie.add(new Tuple("adcf"));
        trie.add(new Tuple("group"));
        trie.add(new Tuple("the"));

        Iterable<String> iter = trie.wordsWithPrefix(prefix);

        int counter = 0;

        for (String s : iter) {
            assertTrue(content.contains(s));
            counter++;
        }

        assertEquals(content.size(), counter);
    }
}