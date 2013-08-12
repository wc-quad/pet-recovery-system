package csc216project1;

import java.util.*;
import java.io.*;

/**
 * Contains an array-based list of Pets maintained in increasing order of names (ignoring case)
 * @author Gabe
 * @version 1.0 6/1/2010
 */
public class PetData implements IDataCollection {

// Public variables
	public final static String[] SEARCH_FIELD = { "Chip", "Owner", "Phone" };
	public final static int BUFFER_SIZE = 500;

// Instance variables
	private Pet[] petRecord = new Pet[ BUFFER_SIZE ];
	private int size = 0;
	private String fileName;
	
// Constructors
	/**
	 * Constructor - creates an empty Pet array
	 */
	public PetData() {
		fileName = "pets.txt";
	}
	/**
	 * Constructor - fills the pet array with info from input file
	 * @param fileName name of the input file
	 */
	public PetData( String filename ) throws FileNotFoundException {		
		
		// Create the file scanner
		File f = new File( filename );
		Scanner scanner = new Scanner( f );
		
		// Save the file name to use it later.
		fileName = filename;
			
		// Read the contents of the file
		try {
			readFromFile( scanner );
		}
		finally {
			scanner.close();
		}
	}	
	
// User-created methods
	/**
	 * Populate the Pet array and sort it alphabetically
	 * @param fileScanner reads the file
	 */
	private void readFromFile( Scanner fileScanner) {
		while ( fileScanner.hasNextLine() ) {
			String line = fileScanner.nextLine();
			
			try {
				// Create a line scanner
				Scanner lineScanner = new Scanner( line ).useDelimiter( "\t" ); // Attribute data are separated by tabs.
				
				// Now pick up each attribute for the Pet of this line,
				String kind = lineScanner.next();
				String chip = lineScanner.next();
				String phone = lineScanner.next();
				String owner = lineScanner.next();
				
				//	Create a new Pet with attributes, add it to the list, and increment the size.
				petRecord[ size++ ] = new Pet(owner, phone, kind, chip);
			}
			catch ( Exception e ) {
				System.out.println("------------------------ERROR!!!---------------------\n" + 
						"There was an error reading from the input file!");
			}
		}
	
		// Do any kind of cleanup (sorting, etc) you need here.
		System.out.println( "size = " + size );
		if (size > 1) selectionSort( petRecord );
		
	}

	/**
	 * Performs the selection sort algorithm on a Pet array
	 * @param record an array of Pet objects
	 */
    public void selectionSort( Pet[] record ) {
		// has to have at least 2 objects to sort
    	if (size >= 2) {
			
    		// Put the appropriate item into k-th position.
			for (int k = 0; k < size - 1; k++) {

				int small = k; // Subscript of smallest item found 
				// so far among k through record.length - 1
				// Look for smallest item
				for (int j = k + 1; j < size; j++)
					if (record[small].compareToByName(record[j]) > 0)
						small = j;
				// Exchange the smallest item and the k-th item
				Pet temp = record[k];
				record[k] = record[small];
				record[small] = temp;
			}
		}
    }
	
    /**
     * Removes all non-digit characters from a string
     * 		(to be used with searching chip and phone# strings)
     * @param str a string with mixed character types
     * @return the input string with non-numeric characters removed
     */
    public String removeNonDigitChars( String str) {
		StringBuffer formatted = new StringBuffer( str );	// mutable string buffer containing input string
		int length = formatted.length();	// length of string buffer above
		
		// remove all non-numeric characters in the buffer
		for (int i=0; i < length; i++){
			if( !Character.isDigit( formatted.charAt(i) ) ) {
				formatted.deleteCharAt(i);
				length = formatted.length();
				i--;
			}
		}
		return formatted.toString();
    }
    
/**
	 * Returns the Pet at position k in the list
	 * @param k index of the Pet list
	 * @return Pet at position k
	 */
	public Pet petAt( int k ) {
		return petRecord[ k ];
	}
	
	/**
	 * Getter - returns number of pets in the database
	 * @return size of the Pet array buffer
	 */
	public int getSize() {
		return size;
	}

	// IDataCollection methods
    /**
	 * Finds a string representation of each item whose given
	 * attributes corresponds to the given value. 
	 * @param value  value of the attribute to match (or partial match)
	 * @param attribute attribute of the item to search on
	 * @return a string representing all items satisfying the search criteria.
	 */
	public String search(String value, String attribute) {
		StringBuffer results = new StringBuffer();
		
		// search string was null: return the entire database
		if ( value.equals("") && attribute.equals("") && size > 0 ) {
			// add each element in petRecords to the results string
			for (int i=0; i < size; i++) {
				
					results.append( petRecord[i] );
					results.append("\n");
			}
		}
		
		// Search a owner string
		else if ( attribute.equalsIgnoreCase( SEARCH_FIELD[1] )  && size > 0) {
			value = value.toLowerCase();
			
			for (int i=0; i < size; i++) {
				
				String pet = petRecord[i].getOwner();
				pet = pet.toLowerCase();
				
				// check if the name starts with the search value
				if ( pet.startsWith(value) ) {
					results.append( petRecord[i].toString() );
					results.append("\n");
				}				
			}
		}
		
		// Search a phone string
		else if ( attribute.equalsIgnoreCase( SEARCH_FIELD[2] ) && size > 0) {
			value = removeNonDigitChars( value );
			
			
			for (int i=0; i < size; i++) {
				String number = petRecord[i].getPhone();
				String item = removeNonDigitChars(number);
				
				// check if the phone number starts with the search value
				if (item.startsWith(value)) {
					results.append(petRecord[i].toString());
					results.append("\n");
				}
			}
		}
		
		// Search a chip string
		else if ( attribute.equalsIgnoreCase( SEARCH_FIELD[0] ) && size > 0) {
			value = removeNonDigitChars( value );
			
			
			for (int i=0; i < size; i++) {				
				String chip = petRecord[i].getChip();
				
				// check if chipID starts with the search value
				if (chip.startsWith(value)) {
					results.append(petRecord[i].toString());
					results.append("\n");
				}
			}
		}
		
		return results.toString();
	}

	/**
	 * Add a new item to the collection with the given attributes.
	 * @param name value for the name attribute
	 * @param phone value to create the phone number attribute
	 * @param kind describes the kind of item 
	 */
	public void add( String name, String phone, String kind ) {
		if (size == BUFFER_SIZE) {
		// Dont add more pets than the list allows
			System.out.println("The database contains " + BUFFER_SIZE + " records and cannot hold anymore!");
			return;
		}
		else petRecord[ size++ ] = new Pet( name.trim(), phone, kind );	// add a new record to the database
		
		// Sort the list if it contains more than 1 record
		if (size > 1) selectionSort( petRecord );
	}

	/**
	 * Remove the items corresponding to the given value from the collection.
	 * @param value determines which items to remove.
	 */
    public void remove(String value) {
    	if ( size == 0 ) return;
    	
    	if ( value == null ) {
    		System.out.println( "Please select a value to remove OR add one to the list if it is emoty" );
    		return;
    	}
    	
    	int delete = BUFFER_SIZE + 1;
    	
    	System.out.println( "Remove: Input parameter = " + value );
    	Scanner valueScanner = new Scanner( value );
    	valueScanner.next();
    	value = valueScanner.next();
    	
    	System.out.println( "ChipId to be removed: " + value );
    	
    	// Find the record to delete
    	for (int i=0; i<size; i++) {
    		if( petRecord[i].getChip().equals(value) ) {
    			delete = i;
    		}
    	}
    	
    	if (size > delete ) {
    		// The list has a record after the one to be deleted
    	
    		// Shift the tail of the list to the left
    		for (int k = delete; k < size - 1; k++) {
    			petRecord[k] = petRecord[k+1];
    		}
    		
    		// Decrement the size
    		size--;
    	}
    	selectionSort( petRecord );
    	search("", "");
    	System.out.println("new_size = " + size);
    }
    
    /**
     * Save the data for the collection to a file.
     */
    public void saveToFile() {
		try {
			String outputFileName = fileName.substring(0, fileName.length() - 4) + "-1" + 
												fileName.substring(fileName.length() - 4);
			System.out.println( outputFileName );
			FileWriter fw = new FileWriter(outputFileName);
			BufferedWriter buffer = new BufferedWriter(fw);
			PrintWriter outfile = new PrintWriter(buffer);
			
			// Now write data for each Pet to the file, using tab delimiters, like this: 
			//    For each Pet:  outfile.println(line of data here)
			for (int i=0; i < size; i++) {
				outfile.println( petRecord[i] );
				System.out.println( petRecord[i] );
			}
			
			outfile.close();   
		}
		catch (Exception e)   {
		}
    }
    
    /**
	 * Preform some preliminary testing of class methods
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "petdata.txt";
		PetData a = null;
		try {
			a = new PetData( filename );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println( a.search("","") );
		System.out.println(a.search("9", "chip") );

	}

}
