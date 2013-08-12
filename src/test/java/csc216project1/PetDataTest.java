package csc216project1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the add() and remove() methods of PetData.java
 * @author Gabe
 * @version 1.0 6/1/10
 */
public class PetDataTest {
	private PetData a;
	private PetData b;
	private PetData c;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		a = new PetData();					// empty datafile
		b = new PetData("petdata.txt");		// datafile with 17 Pet records
		c = new PetData("petdata500.txt");	// datafile with 500 Pet records
	}
	
// Add	
	/**
	 * Test method for {@link csc216project1.PetData#add(java.lang.String, java.lang.String, java.lang.String)}.
	 * Case1: Adding to an empty list
	 */
	@Test
	public final void testAdd1() {
		a.add("Police Dept.", "911", "K9");
		assertEquals( "", a.petAt(0).toString(), a.search("", "").trim() );
	}
	/**
	 * Test method for {@link csc216project1.PetData#add(java.lang.String, java.lang.String, java.lang.String)}.
	 * Case2: Adding to the middle of a list 
	 */
	@Test
	public final void testAdd2() {
		b.add("Police Dept.", "911", "K9");
		assertEquals("", b.petAt(10).toString(), b.search("Police Dept.", PetData.SEARCH_FIELD[1]).trim() );
	}
	/**
	 * Test method for {@link csc216project1.PetData#add(java.lang.String, java.lang.String, java.lang.String)}.
	 * Case3: Adding to a full list (size=500)
	 */
	@Test
	public final void testAdd3() {
		c.add("Police Dept.", "911", "K9");
		assertTrue("The array size is " + c.getSize(), c.getSize() == 500);
	}
	
// Remove
	/**
	 * Test method for {@link csc216project1.PetData#remove(java.lang.String)}.
	 * Case1: Removing from the beginning of a list
	 */
	@Test
	public final void testRemove1() {
		b.remove( b.petAt(0).toString() );
		assertTrue("", b.petAt(0).toString() != "Dog     	      5078	(987)654-6789	Adams, Ralph");
	}
	/**
	 * Test method for {@link csc216project1.PetData#remove(java.lang.String)}.
	 * Case2: Removing from the middle of a list
	 */
	@Test
	public final void testRemove2() {
		b.remove( b.petAt(5).toString() );
		assertTrue("", b.petAt(5).toString() != "Cat     	      1567	(677)512-2345	Matthews, Chris");
	}
	/**
	 * Test method for {@link csc216project1.PetData#remove(java.lang.String)}.
	 * Case3: Removing from the end of a list (size=500)
	 */
	@Test
	public final void testRemove3() {
		c.remove( c.petAt(PetData.BUFFER_SIZE - 1).toString() );
		assertTrue("", c.petAt(PetData.BUFFER_SIZE - 1).toString() != "Dog     	      1161	(613)307-8976	Williams, Jimmy");
	}
	/**
	 * Test method for {@link csc216project1.PetData#remove(java.lang.String)}.
	 * Case4: Forgetting to select a value OR attempting to remove from an empty database
	 */
	@Test
	public final void testRemove4() {
		b.remove(null);
		assertEquals("", 17, b.getSize() );
	}
}
