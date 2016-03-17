package com.task1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PrefixMatchesTest {
	private PrefixMatches obj;
	private String[] words;
	
	@Before
	public void init() {
		obj = new PrefixMatches(new RWayTrie());
		
		words = new String[] {"one", "two", "to", "i", "anakonda"};
		
		obj.add(words);
	}

	@Test
	public void testAdd() {
		assertTrue(obj.add(words) == 3);
		
		assertTrue(obj.add(null) == 0);
	}

	@Test
	public void testContains() {
		assertTrue(obj.contains("one"));
		assertTrue(obj.contains("two"));
		assertTrue(obj.contains("anakonda"));
		assertFalse(obj.contains("to"));
		assertFalse(obj.contains("another"));
	}

	@Test
	public void testDelete() {
		
	}

	@Test
	public void testSize() {
		
	}

	@Test
	public void testWordsWithPrefixStringInt() {
		
	}

	@Test
	public void testWordsWithPrefixString() {
		
	}

}
