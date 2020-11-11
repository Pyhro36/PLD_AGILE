package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Delivery;
import model.Intersection;
import model.Route;
import model.Section;
import model.DeliveryRound;
import model.Hour;

public class DeliveryRoundTest {

	Intersection testIntersection1,testIntersection2,testIntersection3,testIntersection4;
	Delivery testDelivery1, testDelivery2, testDelivery4;
	List <Section> sections1,sections2;
	Route testRoute1,testRoute2;
	Section s1,s2,s3;
	List <Route> testJourney;
	DeliveryRound testDeliveryRound;
	Hour testDepartureTime;
	
	@Before
	/**
	 * Create the necessary objects
	 */
	public void setUp(){
		testIntersection1 = new Intersection(5,5,0);
		testIntersection2 = new Intersection(10,10,1);
		testIntersection3 = new Intersection(6,6,2);
		testIntersection4 = new Intersection(9,9,3);
		testDelivery1 = new Delivery(testIntersection1, 3);
		testDelivery2 = new Delivery(testIntersection2, 5);
		testDelivery4 = new Delivery(testIntersection4, 5);
		sections1 = new LinkedList<Section>();
		sections2 = new LinkedList<Section>();
		testRoute1 = new Route(testDelivery1,testDelivery2,sections1);
		testRoute2 = new Route(testDelivery2,testDelivery4,sections2);
		testDepartureTime = new Hour("08:00:00");
		
		
		
		s1 = new Section("s1", 2, 1, testIntersection1 , testIntersection2);
		s2 = new Section("s2", 3, 1, testIntersection2,testIntersection3);
		s3 = new Section("s3", 4, 1, testIntersection3, testIntersection4);
		
		testRoute1.addSection(s1);
		testRoute2.addSection(s2);
		testRoute2.addSection(s3);
		
		testJourney = new LinkedList<Route>();
		testJourney.add(testRoute1);
		testJourney.add(testRoute2);
		
		testDeliveryRound = new DeliveryRound(testDepartureTime,testJourney);
	}
	
	@Test
	/**
	 * Ensure the correct DeliveryRoundDuration is returned
	 * duration should be duration of s1 + testDelivery2 + s2 + s3 + test Delivery3
	 */
	public void testGetterDeliveryRoundDuration() {
		assertEquals(19,testDeliveryRound.getDeliveryRoundDuration());
	}
	
	@Test
	public void testGetArrivalTime() {
		assertEquals("08:00:19",testDeliveryRound.getArrivalTime().getTimeString());
	}

}
