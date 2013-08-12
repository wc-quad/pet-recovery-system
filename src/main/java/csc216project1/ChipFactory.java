package csc216project1;

import java.util.HashSet;

/**
 * A factory for generating pet microchips. Each chip is unique.
 * @author Jo P
 */
public class ChipFactory {
	
    private static HashSet<String> chips = new HashSet<String>(); // All chips generated
    private static int nextChip = 1111; // Next chip in the sequence
 
    /**
     * Constructor - private so the class cannot be instantiated.
     */
    private ChipFactory() { }
    
    /**
     * Records the given chip as used, or generates a new chip if this is a duplicate
     * of an existing chip.
     * @param c Chip to be registered
     * @return Chip that is actually registered
     */
    public static String registerChip(String c) {
        if (!chips.contains(c)) {
        	chips.add(c);      
            return c;
        }
        return getNewChip();
    }

    /**
     * Get a new chip number.
     * @return The chip number
     */
    public static String getNewChip() {
    	String value = nextChip + "";
    	while  (chips.contains(value))
    		value = ++nextChip + "";
    	chips.add(value);
        return value;
    }
}
