package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.Controller;

import view.Point;

public class MouseListener extends MouseAdapter {

	private Controller controller;
	private GraphicalView graphicalView;
	private Window window;

	public MouseListener(Controller controller, GraphicalView graphicalView, Window window){
		this.controller = controller;
		this.graphicalView = graphicalView;
		this.window = window;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		switch (evt.getButton()){
		case MouseEvent.BUTTON1: 
			Point p = coordinates(evt);
			System.out.print(Integer.toString(p.getX()) + "   ");
			System.out.println(Integer.toString(p.getY()));
			controller.findDelivery(p);
			break;
		default:
		}
	}
		
	/**
	 * Finds the coordinates corresponding to the MouseEvent
	 * @param evt	MouseEvent e.g. A click
	 * @return	a point with coordinates corresponding to evt
	 */
	private Point coordinates(MouseEvent evt){
		MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, graphicalView);
		int x = Math.round((float)e.getX()/(float) GraphicalView.getScale());
		int y = Math.round((float)e.getY()/(float) GraphicalView.getScale());
		Point point = new Point(x,y);
		return point;
	}


}
