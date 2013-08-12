package csc216project1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the methods in Pet.java
 * @author Gabe
 *
 */
public class PetTest {
	private Pet a;
	private Pet b;
	private Pet c;
	private Pet d;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		a = new Pet(" Locke, John", "1234567890", "Squirrle");
		b = new Pet("Hawk, Tommy", "", "Dog");
		c = new Pet("McClurkin, Donnovan", "687.898.9087", "Cat", "1112");
		d = new Pet("McDonald, Donnie", "000111222233333", "LionTigerBear", "1112");
	}

	/**
	 * Test method for {@link csc216project1.Pet#phoneToString(java.lang.String)}.
	 * Case1: phone# has 10 digits
	 */
	@Test
	public final void testPhoneToString1() {
		a.phoneToString( a.getPhone() );
		assertEquals("", "(123)456-7890", a.getPhone() );
	}
	/**
	 * Test method for {@link csc216project1.Pet#phoneToString(java.lang.String)}.
	 * Case2: phone# has 0 digits
	 */
	@Test
	public final void testPhoneToString2() {
		b.phoneToString( b.getPhone() );
		assertEquals("", "(111)111-1111", b.getPhone() );
	}
	/**
	 * Test method for {@link csc216project1.Pet#phoneToString(java.lang.String)}.
	 * Case3: phone# has non-digit characters
	 */
	@Test
	public final void testPhoneToString3() {
		c.phoneToString( c.getPhone() );
		assertEquals("", "(687)898-9087", c.getPhone() );
	}
	/**
	 * Test method for {@link csc216project1.Pet#phoneToString(java.lang.String)}.
	 * Case4: phone# has 10+ digits
	 */
	@Test
	public final void testPhoneToString4() {
		d.phoneToString( d.getPhone() );
		assertEquals("", "(000)111-2222", d.getPhone() );
	}

}
