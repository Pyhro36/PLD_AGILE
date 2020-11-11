package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Delivery;
import model.Intersection;

public class DeliveryTest {

	Intersection testIntersection;
	Delivery testDelivery;
	
	@Before
	/**
	 * Creating necessary objects
	 */
	public void setUp() {
		Intersection testIntersection = new Intersection(5,5,0);
		testDelivery = new Delivery(testIntersection, 3);
	}
	
	@Test
	/**
	 * Ensures the getDeliveryDuration() method works and
	 * returns the correct value
	 */
	public void testGetterDuration(){
		assertEquals(3, testDelivery.getDeliveryDuration());
	}
	
	@Test
	/**
	 * Ensures the getAddress() method works and 
	 * returns the correct value 
	 */
	public void testGetterIntersection(){
		assertEquals(testIntersection,testDelivery.getAddress());
	}
}
	
	
	
	
	


