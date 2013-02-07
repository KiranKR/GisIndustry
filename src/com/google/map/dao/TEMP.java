package com.google.map.dao;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class TEMP {
	
	public static void main(String[] args) {
		try {
			 
			File fXmlFile = new File("C:\\Documents and Settings\\Ample\\Desktop\\finalWater.kml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = (Document) dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("Placemark");
		 
			System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 //System.out.println(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					//System.out.println("line : " + eElement.getAttribute("name"));
					//System.out.println("insert into property (P_NAME,P_ROW_STATUS,P_MRKR_ID,P_TYPE) VALUES ('"+eElement.getElementsByTagName("name").item(0).getTextContent().trim()+"',0,0,0);" );
					String co = eElement.getElementsByTagName("coordinates").item(0).getTextContent().trim();
					String[] a = co.split(",0");
					for(int i = 0; i< a.length;i++){
						System.out.println("insert into latlang (LL_LANG,LL_LAT,LL_P_ID) VALUES ("+a[i].trim()+","+(temp+1)+");");
					}
					//System.out.println("Co-ordinates : " + eElement.getElementsByTagName("coordinates").item(0).getTextContent().trim());
					
		 
				}
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		  }
		 
	}
	
