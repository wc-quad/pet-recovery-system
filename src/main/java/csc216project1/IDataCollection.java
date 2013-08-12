package csc216project1;

import java.util.Scanner;

/**
 * Data and behaviors that a sorted collection for chipped pets must support.
 * @author Jo P
 */
public interface IDataCollection {
	
	/**
	 * Remove the items corresponding to the given value from the collection.
	 * @param value determines which items to remove.
	 */
    void remove(String value);
    
    /**
     * Find a string representation of each item whose given
     *   attributes corresponds to the given value. 
     * @param value  value of the attribute to match (or partial match)
     * @param attribute attribute of the item to search on
     * @return a string representing all items satisfying the search criteria.
     */
    String search(String value, String attribute);
    
    /**
     * Save the data for the collection to a file.
     */
    void saveToFile(); 
    
    /**
     * Add a new item to the collection with the given attributes.
     * @param name value for the name attribute
     * @param phone value to create the phone number attribute
     * @param kind describes the kind of item 
     */
    void add(String name, String phone, String kind);
}