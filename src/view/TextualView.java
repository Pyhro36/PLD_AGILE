package view;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Delivery;
import model.OMap;
import model.Route;
import model.VisitorDelivery;

@SuppressWarnings("serial")
public class TextualView extends JPanel implements Observer, VisitorDelivery{

	String text;
	private OMap omap;
	private DefaultListModel<Delivery> dlm;
	private JList<Delivery> list;
	private JScrollPane jsp;
	
	/**
	 * Create a textual view in the window
	 * @param omap
	 * @param w
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TextualView(OMap omap, Window w){
		super();
		setBorder(BorderFactory.createTitledBorder("Deliveries :"));
		this.setAlignmentX(TOP_ALIGNMENT);
		this.setAlignmentY(TOP_ALIGNMENT);
		//this.setVerticalTextPosition(TOP);
		//this.setVerticalAlignment(TOP);
		
		dlm=new DefaultListModel();
		list=new JList(dlm);
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jsp=new JScrollPane(list);
		list.setSize(1000, 1000);
		jsp.setSize(1000, 1000);
		
		//jsp.setBackground(Color.red);
		//list.setBackground(Color.gray);
		
		//To display a readable list of address
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Delivery) {
                    ((JLabel) renderer).setText(((Delivery) value).getAddress().toString()
                    		/*+ " : " + (((Delivery) value).getTimeWindowStart().getTimeString()
                    		+ " : " + ((Delivery) value).getTimeWindowEnd().getTimeString()*/
                    		+ " : " + ((Delivery) value).getArrivalTime().getTimeString()
                    		+ " : " + ((Delivery) value).getDeliveryTime().getTimeString());
                }
                return renderer;
            }
        });
        
        //To get the selected element on the list
        list.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent le) {
        		System.out.println("Selection of an element");
        		omap.setSelectedDelivery(list.getSelectedValue());
        		omap.setSelectedDeliveryIndex(list.getSelectedIndex() + 1);
            }
        });
			
		w.getContentPane().add(this);
		omap.addObserver(this);
		this.add(jsp);
		this.omap=omap;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("TextualView notified.");
		if(!omap.isTspStopped()) {
			clear();
		}
		
		if(omap.getDeliveryRound()!=null && dlm.isEmpty()){
			for(Route r : omap.getDeliveryRound().getJourney()){
				if(r.getDeliveryArrival().getAddress()!=omap.getDeliveryOrder().getWarehouseAddress()){
					r.getDeliveryArrival().displayDelivery(this);
				}else{
					//Back at the warehouse
				}
			}
		}
	}

	@Override
	public void displayDelivery(Delivery d) {
		// TODO Auto-generated method stub
		dlm.addElement(d);
	}
	
	/**
	 * this method clean the list of delivery in the textual view
	 */
	public void clear() {
		dlm.clear();
	}
}
