package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Intersection;

public class IntersectionTest {
	
	Intersection i;
	
	@Before
	/**
	 * Setting the tests
	 */
	public void setUp(){
		i=new Intersection(20, 20, 1);
	}
	
	@Test
	/**
	 * Test of the method getX() from Intersection
	 */
	public void testGetterLongitude() {
		assertEquals(20, i.getX());
	}
	
	@Test
	/**
	 * Test of the method getY() from Intersection
	 */
	public void testGetterLatitude() {
		assertEquals(20,i.getY());
	}
	
	@Test
	/**
	 * Test of the method getId() from Intersection
	 */
	public void testGetterId() {
		assertEquals(1,i.getId());
	}
}
