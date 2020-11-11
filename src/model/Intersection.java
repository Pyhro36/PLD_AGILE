package model;

/**
 * The class of an intersection
 * @author Joachim
 *
 */
public class Intersection {
	private int x;
	private int y;
	private int id;
	
	/**
	 * Constructor of the class
	 * @param x
	 * @param y
	 * @param id
	 */
	public Intersection(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	/**
	 * Get the longitude (x) of the intersection
	 * @return An int, corresponding to the longitude of an intersection
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the latitude (y) of the intersection
	 * @return An int, corresponding to the longitude of an intersection
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get the id of an intersection.
	 * @return An int, corresponding to the id of an intersection
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the String of the intersection
	 */
	public String toString(){
		return "Intersection " + id;
	}
}
