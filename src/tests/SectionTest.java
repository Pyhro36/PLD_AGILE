package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Intersection;
import model.Section;

public class SectionTest {
	
	Section s;
	Intersection i0, i1;
	
	@Before
	public void setUp(){
		i0=new Intersection(20, 20, 0);
		i1=new Intersection(40, 40, 1);
		s=new Section("a", 1320, 60, i0, i1);
	}
	
	@Test
	public void testPassageDuration(){
		assertEquals(22, s.getPassageDuration());
	}
	
	@Test
	public void testGetName() {
		assertEquals("a", s.getName());
	}
	
	@Test
	public void testGetIntersectionStart() {
		assertEquals(i0, s.getIntersectionStart());
	}
	
	@Test
	public void testGetIntersectionEnd() {
		assertEquals(i1, s.getIntersectionEnd());
	}	
}
