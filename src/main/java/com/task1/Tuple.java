package com.task1;
/**
 * Created by Dennis
 *
 * on 3/16/2016.
 */
public class Tuple {
    private String term;
    private int weight;
    
	public Tuple(String term) {
		super();
		this.term = term;
		this.weight = term.length();
	}
	
	public String getTerm() {
		return term;
	}
	public int getWeight() {
		return weight;
	}
    
    
}
