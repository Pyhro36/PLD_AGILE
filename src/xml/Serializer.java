package xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Delivery;
import model.DeliveryRound;

/**
 * Class to get an XML file describing the delivery round
 * 
 * @author VChapelle
 *
 */

public class Serializer {

	private static Element rootDeliveryRound;
	private static Document document;
	private static Serializer instance = null;

	private Serializer() {
	}

	public static Serializer getInstance() {
		if (instance == null)
			instance = new Serializer();
		return instance;
	}

	/**
	 * Open an XML file and write a description of the delivery round
	 * 
	 * @param plan
	 * @throws ParserConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws ExceptionXML
	 */
	public static void save(DeliveryRound deliveryRound) throws ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException, ExceptionXML {
		File xml = XMLFileOpener.getInstance().open(false);
		StreamResult result = new StreamResult(xml);
		document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		document.appendChild(createDeliveryRound(deliveryRound));
		DOMSource source = new DOMSource(document);
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.setOutputProperty(OutputKeys.INDENT, "yes");
		xformer.transform(source, result);

	}

	/**
	 * Create the nodes of the delivery round for the XML file
	 * 
	 * @param deliveryRound
	 */
	private static Element createDeliveryRound(DeliveryRound deliveryRound) {
		Element root = document.createElement("ListeDesLivraisons");
		rootDeliveryRound = document.createElement("Entrepôt");
		createAttribute(rootDeliveryRound, "adresse",
				Integer.toString(deliveryRound.getJourney().get(0).getDeliveryDeparture().getAddress().getId()));
		createAttribute(rootDeliveryRound, "heureDépart", deliveryRound.getDepartureTime().getTimeString());
		createAttribute(rootDeliveryRound, "heureArrivée", deliveryRound.getArrivalTime().getTimeString());

		root.appendChild(rootDeliveryRound);

		for (int i = 1; i < deliveryRound.getJourney().size(); i++) {
			Element livraison = displayD(deliveryRound.getJourney().get(i).getDeliveryDeparture());
			root.appendChild(livraison);

			for (int j = 0; j < deliveryRound.getJourney().get(i).getSection().size(); j++) {
				Element intersection = displayS(deliveryRound, i, j);
				livraison.appendChild(intersection);
			}

			root.appendChild(rootDeliveryRound);

		}

		return root;
	}

	/**
	 * 
	 * @param root
	 * @param name
	 * @param value
	 */

	private static void createAttribute(Element root, String name, String value) {
		Attr attribut = document.createAttribute(name);
		root.setAttributeNode(attribut);
		attribut.setValue(value);
	}

	/**
	 * 
	 * @param delivery
	 */

	public static Element displayD(Delivery delivery) {
		rootDeliveryRound = document.createElement("Livraison");
		createAttribute(rootDeliveryRound, "adresse", Integer.toString(delivery.getAddress().getId()));
		return rootDeliveryRound;
	}

	public static Element displayS(DeliveryRound deliveryRound, int i, int j) {
		Element intersection = document.createElement("Intersection");
		createAttribute(intersection, "arrivée",
				Integer.toString(deliveryRound.getJourney().get(i).getSection().get(j).getIntersectionEnd().getId()));
		createAttribute(intersection, "départ",
				Integer.toString(deliveryRound.getJourney().get(i).getSection().get(j).getIntersectionStart().getId()));

		return intersection;
	}

}
