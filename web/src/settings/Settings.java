package settings;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import jxmlable.DontObfuscate;
import jxmlable.XmlGenerator;
import jxmlable.XmlParser;

public class Settings implements DontObfuscate {
	public int m_version;
	public String m_account;
	public long m_lastModifiedTime;
	public String m_status; // used for checking if this is allowed to be changed
	
	public ListOfAttributes m_attributes;
	
	private Settings() {} // needed for xml tool
	
	/** @return the whole setting object as xml string */
	public String toXmlString() {
		try {
			return XmlGenerator.getXml(this);
		}
		catch(Exception e) {
			System.out.println("toXmlStrin failed " + e);
		}
		
		return "";
	}
	
	/** @return Settings object from xml string */
	public static Settings restore(String rawXml) {
		try {
			Settings settings = new Settings();
		    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        
		   Document d = documentBuilder.parse(
	                  new BufferedInputStream( new ByteArrayInputStream( rawXml.getBytes() ) ) );

           XmlParser.setFromXml(settings, d.getDocumentElement());  
           
           return settings;
		}  catch(Exception e) {
			System.out.println("cannot restore settings");
		}
				
		return null;
	}
	
	
	public static void main(String args[]) {
		Settings test = new Settings();
		test.m_account = "max100";
		test.m_lastModifiedTime = System.currentTimeMillis();
		test.m_version = 2014018;
		test.m_status = "TESTING";
		
		test.m_attributes = new ListOfAttributes();
		
		Attribute a1 = new Attribute();
		Attribute a2 = new Attribute();
		a1.m_repeatType = "WEEK/WED";
		a1.m_actionLink = "wechat";
		a1.m_actionParameters = "message/How are you?";
		a1.m_startTime = "09:10";
		a1.m_endTime = "09:12";
		
		test.m_attributes.add(a1);
		
		a2.m_repeatType = "ONCE";
		a2.m_actionLink = "browser";
		a2.m_actionParameters = "www.creaders.net";
		a2.m_startTime = "09:15";
		
		test.m_attributes.add(a2);
		
		String xmlStr = test.toXmlString();
		
		System.out.print(xmlStr);
		
		Settings test2 = Settings.restore(xmlStr);
		System.out.print(test2.toXmlString());
	}
}

