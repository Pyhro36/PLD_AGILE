package view;

public class Point {
	private int x;
	private int y;

	/**
	 * Create a point with coordinates (x,y)
	 * @param x
	 * @param y
	 */
	public Point(int x, int y){ 
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the point (deltaX,deltaY) away from this point
	 * @param deltaX 
	 * @param deltaY 
	 */
	public Point move(int deltaX, int deltaY) {
		return new Point(x+deltaX, y+deltaY);
	}

	/**
	 * Calculate the distance between points this and p
	 * @param p
	 * @return the distance between this and p
	 */
	public int distance(Point p){
		return (int)(Math.sqrt((x-p.getX())*(x-p.getX()) + (y-p.getY())*(y-p.getY())));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public String toString(){
		String toBeReturned = "(" + Integer.toString(x) + "," + Integer.toString(y) + ")";
		return toBeReturned;
	}

}
