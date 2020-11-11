package tests;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Delivery;
import model.Intersection;
import model.Route;
import model.Section;

public class RouteTest {

	Intersection i0,i1,i2;
	Delivery d0, d1;
	Section s0, s1;
	Route r;
	
	@Before
	public void setUp(){
		i0=new Intersection(0, 0, 0);
		i1=new Intersection(10, 10, 1);
		i2=new Intersection(10, 0, 2);
		
		d0=new Delivery(i0, 10);
		d1=new Delivery(i1, 20);
		
		s0=new Section("a", 20, 10, i0, i1);
		s1=new Section("b", 10, 10, i1, i2);
		
		r=new Route(d0, d1);
	}
	
	@Test
	public void testAddSectionEmptyRoute() {
		assert(r.addSection(s0)==true);
	}
	
	@Test
	public void testAddSection() {
		ArrayList<Section> sections=new ArrayList<Section>();
		sections.add(s0);
		
		r=new Route(d0, d1, sections);
		assert(r.addSection(s1)==true);
	}
	
	@Test
	public void testGetRouteDuration(){
		//Duration of the browse through the section is 2 (20/10)
		ArrayList<Section> sections=new ArrayList<Section>();
		sections.add(s0);
		
		//Duration of the delivery of d1 is 20
		r=new Route(d0, d1, sections);
		
		assert(r.getRouteDuration()==20);
	}

}
