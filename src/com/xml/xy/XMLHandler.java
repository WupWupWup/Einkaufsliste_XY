package com.xml.xy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import com.database.xy.Product;

@SuppressLint("SdCardPath")
public class XMLHandler extends Activity
{
	private ArrayList<Product> list;
	private String filename;
	
	public XMLHandler(String name, ArrayList<Product> productList)
	{
		list = productList;
		filename = name;
	    File myNewFolder = new File("/data/data/com.example.xy/Einkaufslisten/");
	    myNewFolder.mkdir();
	}
	
	public XMLHandler(){}
	
	@SuppressLint("SdCardPath")
	public void generateXMLFile()
	{
		try 
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			/*Hauptelement wird erzeugt*/
			Element rootElement = doc.createElement("Einkaufsliste");
			doc.appendChild(rootElement);
			
			for(int b = 0; b < list.size(); b++) 
			{	
				/*Unterelement wird angehängt*/
				Element product = doc.createElement("Produkt");
				rootElement.appendChild(product);
				
				/*Attribute des zu erkennenden nodes wird gesetzt*/
				Attr attr = doc.createAttribute("Name");
				attr.setValue(list.get(b).getProduct_name());
				product.setAttributeNode(attr);
				
				/*Tags werden an Node mit Beschreibungen angehängt*/
				Element description = doc.createElement("Beschreibung");
				description.appendChild(doc.createTextNode(list.get(b).getProduct_description()));
				product.appendChild(description);
				
				Element price = doc.createElement("Preis");
				price.appendChild(doc.createTextNode(Double.toString(list.get(b).getProduct_price())));
				product.appendChild(price);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("/data/data/com.example.xy/Einkaufslisten/" + filename));
		 
			transformer.transform(source, result);
	 
		} 
		catch (ParserConfigurationException pce) 
		{
				pce.printStackTrace();
		} 
		catch (TransformerException tfe) 
		{
				tfe.printStackTrace();
		}
	}
	
	@SuppressLint("SdCardPath")
	public List<Product> readXMLFile(String file)
	{
		final String xmlFilePath = "/data/data/com.example.xy/Einkaufslisten/"+file;
		List<Product> productList = new ArrayList<Product>();
			try 
			{
				File xmlFile = new File(xmlFilePath);

				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				
				Document doc = documentBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();
			
				/*Name der Liste wird ausgegeben*/
				System.out.println(doc.getDocumentElement().getNodeName());

				NodeList nodeList = doc.getElementsByTagName("Produkt");

				for (int itr = 0; itr < nodeList.getLength(); itr++) 
				{
					Node node = nodeList.item(itr);

					if (node.getNodeType() == Node.ELEMENT_NODE) 
					{
						Element eElement = (Element) node;
						productList.add(new Product(eElement.getAttribute("Name"),
								eElement.getElementsByTagName("Beschreibung").item(0).getTextContent(),
								Double.parseDouble(eElement.getElementsByTagName("Preis")
								.item(0).getTextContent())));
						
						System.out.println("Produkt: "
								+ eElement.getAttribute("Name"));
						
						System.out.println("Beschreibung: "
								+ eElement.getElementsByTagName("Beschreibung")
								.item(0).getTextContent());
						System.out.println("Preis: "
								+ eElement.getElementsByTagName("Preis")
								.item(0).getTextContent());
					}
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			return productList;
	}
	
	@SuppressLint("SdCardPath")
	public String[] getFileNames()
	{
		/*
		 * Gibt alle Dateinamen innerhalb des "Einkaufslisten" Ordners zurück.
		 */
		String[] filenames;
		File fileDirectory = new File("/data/data/com.example.xy/Einkaufslisten/");
		filenames = fileDirectory.list();
		return filenames;
	}
}
