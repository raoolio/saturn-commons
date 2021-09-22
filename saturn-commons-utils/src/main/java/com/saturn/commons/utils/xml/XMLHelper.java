package com.saturn.commons.utils.xml;

import com.saturn.commons.utils.string.StringUtils;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * New XML parsing utils based on JDK's STAX
 * @author Raul
 */
public class XMLHelper {

    /** Logger */
    private static Logger LOG=LogManager.getLogger(XMLHelper.class);


    /**
     *
     * @param curPath
     * @param target
     * @return
     */
    private static boolean pathMatches(List curPath,List target) {
        boolean eq=true;

        for (int i=0; i<=target.size(); i++) {
            Object o1= target.get(i);
            Object o2= curPath.get(i);

            if (o1.equals(o2)) {
                continue;
            } else {
                eq=false;
                break;
            }
        }

        return eq;
    }

    
    public static List<String> getValues2(String xml, String xmlPath) {
        List<String> l= new LinkedList<>();
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
//            ParseCustomer parseEventsHandler=new ParseCustomer(xmlPath);
//            sp.parse( new ByteArrayInputStream(xml.getBytes()) ivyHttp.getInputStream(),parseEventsHandler);        
        } catch (Exception e) {
        }
        
        return l;
    }
    
    
    

    public static List<String> getValues(String xml, String xmlPath) {
        List<String> l= new LinkedList<>();

        // Extract attribute ID
        String attrId=null;
        int p= xmlPath.indexOf(".");
        if (p>0) {
            attrId= xmlPath.substring(p+1);
            xmlPath= xmlPath.substring(0, p);
        }

        /**
         * Converts XML tag to list representation:
         * "one/two/three" -> { "three", "two", "one" }
         */
        List targetPath= Arrays.asList(xmlPath.split("/"));
        Collections.reverse(targetPath);

        LinkedList curPath= new LinkedList();

        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader reader = xif.createXMLStreamReader( new StringReader(xml));

            boolean readAttr=false;

            while (reader.hasNext()) {
                int type= reader.next();

                switch (type) {
                    case XMLStreamConstants.START_ELEMENT:
                        String xmlTag= reader.getLocalName();
                        curPath.push(xmlTag);

                        // found target XML path?
                        if (pathMatches(curPath,targetPath) ) {
                            // fetch attribute ?
                            if (!StringUtils.isBlank(xml)) {
                                readAttr=true;
                            } else {
                                reader.nextTag();
                                l.add(reader.getElementText());
                            }
                        }
                        break;

                    case XMLStreamConstants.ATTRIBUTE:
                        if (readAttr) {
                            String curAttrId= reader.getLocalName();
                            if (curAttrId.equals(attrId)) {
                                reader.nextTag();
                                l.add(reader.getElementText());
                                readAttr=false;
                            }
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        xmlTag= reader.getLocalName();
                        // tag is last element in path ?
                        if (xmlTag.equals( curPath.getLast() )) {
                            curPath.pop();
                            readAttr=false;
                        }
                        break;
                }//switch
            }//while

        } catch (Exception e) {
            LOG.error("Error parsing XML for path["+xmlPath+"]",e);
        }

        return l;
    }





}
