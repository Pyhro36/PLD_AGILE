package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.OMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class to read an XML file and to create the objects from the deserialization
 * 
 * @author VChapelle
 *
 */
public class Deserializer {

	/**
	 * Load an XML and save the informations on an omap
	 * 
	 * @param omap
	 *            omap where the XML will be saved
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ExceptionXML
	 */
	public static void load(OMap omap) throws ParserConfigurationException, SAXException, IOException, ExceptionXML {
		File xml = XMLFileOpener.getInstance().open(true);
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = docBuilder.parse(xml);
		Element racine = document.getDocumentElement();

		if (racine.getNodeName().equals("reseau")) {
			buildMap(racine, omap);
		} else if (racine.getNodeName().equals("demandeDeLivraisons"))
			buildDeliveryOrder(racine, omap);
		else
			throw new ExceptionXML("Document non conforme");
	}

	/**
	 * Method to build the map of an omap, using get the information from an XML
	 * file
	 * 
	 * @param nodeDOMRacine
	 * @param omap
	 * @throws ExceptionXML
	 * @throws NumberFormatException
	 */
	private static void buildMap(Element nodeDOMRacine, OMap omap) throws ExceptionXML, NumberFormatException {

		NodeList intersectionList = nodeDOMRacine.getElementsByTagName("noeud");
		for (int i = 0; i < intersectionList.getLength(); i++) {
			int id = Integer.parseInt(((Element) intersectionList.item(i)).getAttribute("id"));
			int x = Integer.parseInt(((Element) intersectionList.item(i)).getAttribute("x"));
			int y = Integer.parseInt(((Element) intersectionList.item(i)).getAttribute("y"));
			omap.addIntersection(x, y, id);
		}

		NodeList sectionList = nodeDOMRacine.getElementsByTagName("troncon");
		for (int i = 0; i < sectionList.getLength(); i++) {
			int start = Integer.parseInt(((Element) sectionList.item(i)).getAttribute("origine"));
			int end = Integer.parseInt(((Element) sectionList.item(i)).getAttribute("destination"));
			int length = Integer.parseInt(((Element) sectionList.item(i)).getAttribute("longueur"));
			int averageSpeed = Integer.parseInt(((Element) sectionList.item(i)).getAttribute("vitesse"));
			String name = (String) ((Element) sectionList.item(i)).getAttribute("nomRue");
			omap.addSection(start, end, length, averageSpeed, name);
		}
	}

	/**
	 * Method to build the delivery order of an omap, using get the information
	 * from a XML file
	 * 
	 * @param nodeDOMRacine
	 * @param omap
	 * @throws ExceptionXML
	 * @throws NumberFormatException
	 */
	private static void buildDeliveryOrder(Element nodeDOMRacine, OMap omap) throws ExceptionXML, NumberFormatException {
		int warehouseAddress = Integer
				.parseInt(((Element) nodeDOMRacine.getElementsByTagName("entrepot").item(0)).getAttribute("adresse"));
		String warehouseDepartureTime = (String) ((Element) nodeDOMRacine.getElementsByTagName("entrepot").item(0))
				.getAttribute("heureDepart");

		NodeList listDelivery = nodeDOMRacine.getElementsByTagName("livraison");

		int listDeliveries[][] = new int[listDelivery.getLength()][2];
		String listTimeWindow[][] = new String[listDelivery.getLength()][2];
		for (int i = 0; i < listDelivery.getLength(); i++) {
			listDeliveries[i][0] = Integer.parseInt(((Element) listDelivery.item(i)).getAttribute("adresse"));
			listDeliveries[i][1] = Integer.parseInt(((Element) listDelivery.item(i)).getAttribute("duree"));

			NamedNodeMap attributes = listDelivery.item(i).getAttributes();
			int length = attributes.getLength();

			if (length == 4) {
				listTimeWindow[i][0] = String.valueOf(((Element) listDelivery.item(i)).getAttribute("debutPlage"));
				listTimeWindow[i][1] = String.valueOf(((Element) listDelivery.item(i)).getAttribute("finPlage"));
			} else {
				listTimeWindow[i][0] = "";
				listTimeWindow[i][1] = "";
			}

		}

		omap.buildeDeliveryOrder(warehouseAddress, warehouseDepartureTime, listDeliveries, listTimeWindow);

	}
}
