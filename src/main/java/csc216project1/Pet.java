package csc216project1;

/**
 * Contains data members for instances of Pets registered with Companion Animal Recovery
 * @author Gabe
 * @version 2.0	6/6/2010 **Correctly limits "Pet.kind" to 8 characters**
 *
 */
public class Pet {

// Instance variables
	private String kind;
	private String chip;
	private String phone;
	private String owner;
	private final int EIGHT = 8;
	private final int TEN = 10;
	
// Constructors
	/**
     * Constructor - generates a new record with a unique chipID
     * @param name pet's owner
     * @param phone owner's contact number
     * @param kind type of pet
     * @param chipID database key
     */
	public Pet( String name, String phone, String kind ) {
		owner = name;
		this.phone = phoneToString( phone );
		this.kind = eightLength( kind );		// **version 2.0 update**
		chip = ChipFactory.getNewChip( );
	}
	/**
     * Constructor - generates a new record with the given chipID
     * @param name pet's owner
     * @param phone owner's contact number
     * @param kind type of pet
     * @param chipID database key
     */
	public Pet( String name, String phone, String kind, String chipID ) {
		owner = name;
		this.phone = phoneToString( phone );
		this.kind = eightLength( kind );		// **version 2.0 update**
		chip = ChipFactory.registerChip( chipID );
	}
	
// Getters
	/**
	 * Getter - Gets a Pet's chip
	 * @return the chip
	 */
	public String getChip() {
		return chip;
	}
	/**
	 * Getter - Gets a Pet's owner
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * Getter - Gets a Pet's phone number
	 * @return the phone number
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * Getter - Gets a Pet's kind
	 * @return the kind
	 */
	public String getKind() {
		if ( kind.length() > 8 ) return kind.substring(0, 8);
		else return kind;
	}
	
// Additional Methods	
	/**
	 * Compares two Pet objects by name
	 * @param p Pet object to be compared to
	 * @return a integer representing if the input Pet is less than
	 * 			equal to, or greater than the calling Pet
	 */
	public int compareToByName( Pet p ) {
		// assign owner names to variables
		String value1 = getOwner(); 
		String value2 = p.getOwner();
				
		return value1.compareToIgnoreCase(value2);
	}
	
	/**
	 * Formats a phone number properly as "(" + areacode + ")" + prefix + "-" + suffix
	 * @param number phone number string to be formatted
	 * @return properly formatted phone number string
	 */
	public String phoneToString( String number ) {
		
		StringBuffer formatted = new StringBuffer( number );	// mutable phone number string
		String str = "";	// properly formatted phone number return string
		int length = formatted.length();	//	length of the number buffer
		
		// remove non-digit characters
		for (int i=0; i < length; i++){
			if( !Character.isDigit( formatted.charAt(i) ) ) {
				formatted.deleteCharAt(i);
				length = formatted.length();
				i--;
			}
		}
		
		// set return string to the ten properly formatted numbers from the buffer
		if ( formatted.length() >= TEN ) {
			str = "(" + formatted.substring( 0, 3) + ")" + formatted.substring(3, 6) + "-" + formatted.substring(6, 10);
		}
		
		// OR add additional 1s to the buffer until the number has ten digits and format it 
		else if (formatted.length() < TEN ) {
			// Handle error if less than 10 numbers present
			int numOfOnes = TEN - formatted.length();
			for ( int i = 0; i < numOfOnes; i++ ) {
				formatted.append("1");
			}
			// Format phone number
			str = "(" + formatted.substring( 0, 3) + ")" + formatted.substring(3, 6) + "-" + formatted.substring(6, 10);
		}
		return str; 	
	}

	/**
	 * Converts a Pet object to a string
	 */
	public String toString() {
		String output = String.format("%-8s\t%10s\t", kind, chip );
		output = output + phone + "\t" + owner.trim();
		return output;
	}
	
	/**
	 * **version 2.0 addition** Correctly limits input string to 8 characters
	 * 
	 * @param kind represents the string for the type of Pet
	 * @return a string 8 characters or less
	 */
	private String eightLength( String kind) {
		kind = kind.trim();
		StringBuffer eight = new StringBuffer( kind );
		int length = eight.length();
		
		if( length > EIGHT ) {
			for (int i = 1; i <= length - EIGHT ; i++) {
				// remove characters after index 7
				eight.deleteCharAt(i);
			}
		}
		return eight.toString();
	}
	/**
	 * Preform some preliminary testing of class methods
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println( ChipFactory.getNewChip() );
		Pet a = new Pet(" Locke, John", "7046510200", "Squirrle");
		System.out.println( a );
		
		Pet b = new Pet("Hawk, Tommy", "704.651.0200", "Dog");
		System.out.println( b );
		
		Pet c = new Pet("McClurkin, Donnovan", "(704)651-0200", "Cat", "1112");
		System.out.println( c );
		
		Pet d = new Pet("McDonald, Donnie", "(704) 651 - 0200", "LionTigerBear", "1112");
		System.out.println( d );
		
		System.out.println( a.compareToByName(b) );
		System.out.println( a.getPhone() );
		System.out.println( ChipFactory.registerChip("1114") );
	}

}
