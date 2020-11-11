package model;

/**
 * Class of a section, corresponding to the path between two intersections
 * @author Joachim
 *
 */
public class Section {
	private String name;
	private int length;
	private int averageSpeed;
	private Intersection intersectionStart;
	private Intersection intersectionEnd;
	
	/**
	 * Constructor of a section
	 * @param name				name of a section
	 * @param length			length of a section (in decimeters)
	 * @param averageSpeed		average speed on the section (in decimeters/second)
	 * @param intersectionStart	intersection of the beginning of the section
	 * @param intersectionEnd	intersection of the ending of the section
	 */
	public Section(String name, int length, int averageSpeed, Intersection intersectionStart, Intersection intersectionEnd) {
		this.name = name;
		this.length = length;
		this.averageSpeed = averageSpeed;
		this.intersectionStart = intersectionStart;
		this.intersectionEnd = intersectionEnd;
	}
	
	/**
	 * Method to know duration that the delivery man will take on the section
	 * @return an int, corresponding to the run through the section in seconds 
	 */
	public int getPassageDuration() {
		int passageDuration = length / averageSpeed;	
		return passageDuration;
	}
	
	/**
	 * Getter to know the name of the section
	 * @return the name of the section
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to get the starting intersection of the section
	 * @return an intersection
	 */
	public Intersection getIntersectionStart() {
		return intersectionStart;
	}
	
	/**
	 * Method to get the ending intersection of the section
	 * @return an intersection
	 */
	public Intersection getIntersectionEnd() {
		return intersectionEnd;
	}

	/**
	 * Method to display the name of a section
	 */
	public String toString(){
		return null;
	}
}
