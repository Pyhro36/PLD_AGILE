package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import javafx.util.Pair;
import model.Delivery;
import model.Intersection;
import model.OMap;
import model.Route;
import model.Section;

/**
 * Class of the graphical view of the software
 * @author Joachim
 *
 */
@SuppressWarnings("serial")
public class GraphicalView extends JPanel implements Observer{
	
	//private int scale;
	private int viewHeight;
	private int viewWidth;
	private static float scale;
	private OMap omap;
	
	/**
	 * Construction of the view
	 * @param omap	omap containing all the informations useful 
	 * @param s		scale to display the omap in the best conditions available
	 * @param w		window containing the view
	 */
	public GraphicalView (OMap omap, int s, Window w){
		super();
		omap.addObserver(this); //this observe omap
		this.omap=omap;
		viewHeight=500/*omap.getHeight()*e*/;
		viewWidth=500/*omap.getWidth()*e */;
		setLayout(null);
		setBackground(Color.white);
		setSize(viewHeight, viewWidth);
		w.getContentPane().add(this);
	}
	
	/**
	 * Method to display the elements on the view
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.gray);
		scale=500/Math.max(1, (Math.max(omap.getMaxXIntersection(), omap.getMaxYIntersection())));
		
		//System.out.println("X : " + omap.getMaxXIntersection() + " | Y : "+omap.getMaxYIntersection());
		//System.out.println("Scale : "+scale);
		//System.out.println("X/scale : " + omap.getMaxXIntersection()*scale + " | Y/scale : "+omap.getMaxYIntersection()*scale);
		
		//Adding the intersections
		if(omap.getIntersections()!=null){
			for(Map.Entry<Integer, Intersection> entry : omap.getIntersections().entrySet()){
				g.fillOval((Math.round(entry.getValue().getX()*scale))-5, Math.round(entry.getValue().getY()*scale)-5, 10, 10);
			}
		}

		//Adding the differents sections
		if(omap.getSections()!=null){
			for(Map.Entry<Pair<Integer, Integer>, Section> entry : omap.getSections().entrySet() ){
				int Xorigin=Math.round(entry.getValue().getIntersectionStart().getX()*scale);
				int Yorigin=Math.round(entry.getValue().getIntersectionStart().getY()*scale);
				int Xdestination=Math.round(entry.getValue().getIntersectionEnd().getX()*scale);
				int Ydestination=Math.round(entry.getValue().getIntersectionEnd().getY()*scale);
				
				g.drawLine(Xorigin, Yorigin, Xdestination, Ydestination);
				
			}
		}
		
		if(omap.getDeliveryOrder()!=null){
			g.setColor(Color.blue);
			g.fillOval(Math.round(omap.getDeliveryOrder().getWarehouseAddress().getX()*scale)-10, 
					Math.round(omap.getDeliveryOrder().getWarehouseAddress().getY()*scale)-10, 20, 20);			
			g.setColor(Color.red);
			for(Delivery d : omap.getDeliveryOrder().getDeliveries()){
				g.fillOval(Math.round(d.getAddress().getX()*scale)-5, Math.round(d.getAddress().getY()*scale)-5, 10, 10);
			}
		}
		
		/**if(omap.getDeliveryRound()!=null){
			g.setColor(Color.red);
			for(Route r : omap.getDeliveryRound().getJourney()){
				if(r.getSection()!=null){
					for(Section s : r.getSection()){
						int Xorigin=Math.round(s.getIntersectionStart().getX()*scale);
						int Yorigin=Math.round(s.getIntersectionStart().getY()*scale);
						int Xdestination=Math.round(s.getIntersectionEnd().getX()*scale);
						int Ydestionation=Math.round(s.getIntersectionEnd().getY()*scale);
						
						g.drawLine(Xorigin, Yorigin, Xdestination, Ydestionation);
					}
				}
			}
		}*/
		
		//Add arrows
		if(omap.getDeliveryRound()!=null){
			g.setColor(Color.red);
			for(Route r : omap.getDeliveryRound().getJourney()){
				if(r.getSection()!=null){
					for(Section s : r.getSection()){
						g.setColor(Color.red);
						int Xorigin=s.getIntersectionStart().getX();
						int Yorigin=s.getIntersectionStart().getY();
						int Xdestination=s.getIntersectionEnd().getX();
						int Ydestination=s.getIntersectionEnd().getY();
						
						g.drawLine((int)(Xorigin*scale), (int)(Yorigin*scale), (int)(Xdestination*scale), (int)(Ydestination*scale));
						
						double Xori = Xorigin;
						double Yori = Yorigin;
						double Xdest = Xdestination;
						double Ydest = Ydestination;
						
						Point origin = new Point(Xorigin,Yorigin);
						Point destination = new Point(Xdestination,Ydestination);
						
						double midX = (Xori*scale + Xdest*scale)/2;
						double midY = (Yori*scale + Ydest*scale)/2;
						Point c = new Point((int)midX,(int)midY);
						double slope = (Ydest*scale - Yori*scale)/(Xdest*scale - Xori*scale);
						
						destination.distance(origin);
						int xOffset = 0;
						double yOffset = 0;
						do
						{
							xOffset += 1;
							yOffset += 1/slope;
						}while(Math.abs(yOffset) < 5 && xOffset < 5);
						c.move(xOffset, (int)(-yOffset));
						c.move(-xOffset, (int)(yOffset));
						g.setColor(Color.orange);
						int [] x = new int[] {(int) (destination.getX()*scale),40, 40/*pUp.getX(),pDown.getX()*/};
						int [] y = new int[] {(int) (destination.getY()*scale),40, 40/*pUp.getY(),pDown.getY()*/};
						
						g.fillPolygon(x,y,3);
					}
				}
			}
		}
		
		if(omap.getSelectedDelivery()!=null){
			g.setColor(Color.GREEN);
			g.fillOval(Math.round(omap.getSelectedDelivery().getAddress().getX()*scale)-15, 
					Math.round(omap.getSelectedDelivery().getAddress().getY()*scale)-15, 30, 30);
		}
		
		if(omap.getSelectedIntersection()!=null){
			g.setColor(Color.MAGENTA);
			g.fillOval(Math.round(omap.getSelectedIntersection().getX()*scale)-10, 
					Math.round(omap.getSelectedIntersection().getY()*scale)-10, 20, 20);
		}
	}
	
	/**
	 * Get the width of the graphical view
	 */
	public int getWidth() {
		return viewWidth;
	}
	
	/**
	 * Get the height of the graphical view
	 */
	public int getHeight(){
		return viewHeight;
	}

	public static float getScale(){
		return scale;
	}
	/**
	 * Method to update the view when new informations are added to the omap
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		repaint();
	}
}
