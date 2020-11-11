package xml;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

/**
 * Class to open an XML file
 * 
 * @author Joachim
 *
 */
public class XMLFileOpener extends FileFilter {// Singleton

	private static XMLFileOpener instance = null;

	private XMLFileOpener() {
	}

	/**
	 * Method to configure the instance of the XMLFileOpener
	 * 
	 * @return
	 */
	protected static XMLFileOpener getInstance() {
		if (instance == null)
			instance = new XMLFileOpener();
		return instance;
	}

	/**
	 * Method to open an XML file
	 * 
	 * @param reading
	 *            to know if we want to read the XML
	 * @return an XML file
	 * @throws ExceptionXML
	 */
	public File open(boolean reading) throws ExceptionXML {
		int returnValue;
		JFileChooser jFileChooserXML = new JFileChooser();
		jFileChooserXML.setFileFilter(this);
		jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (reading)
			returnValue = jFileChooserXML.showOpenDialog(null);
		else
			returnValue = jFileChooserXML.showSaveDialog(null);
		if (returnValue != JFileChooser.APPROVE_OPTION)
			throw new ExceptionXML("Problem with opening the file");
		return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
	}

	/**
	 * Method to test if the file is an XML file
	 */
	@Override
	public boolean accept(File f) {
		if (f == null)
			return false;
		if (f.isDirectory())
			return true;
		String extension = getExtension(f);
		if (extension == null)
			return false;
		return extension.contentEquals("xml");
	}

	/**
	 * Method to get the description of a file
	 */
	@Override
	public String getDescription() {
		return "File XML";
	}

	/**
	 * Method to know the type of class that we are opening
	 * 
	 * @param f
	 *            the file we want to know the type
	 * @return the extension of the file
	 */
	private String getExtension(File f) {
		String filename = f.getName();
		int i = filename.lastIndexOf('.');
		if (i > 0 && i < filename.length() - 1)
			return filename.substring(i + 1).toLowerCase();
		return null;
	}
}
