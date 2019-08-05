package com.saturn.commons.utils.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Clase Util para la extraccion de Atributos y Valores XML
 * @author Raul del Cid Lopez
 */
public class XMLUtils
{
    private static TransformerFactory transFactory=TransformerFactory.newInstance();
    private static final Logger log= LogManager.getLogger(XMLUtils.class);
    private static XMLProps globals;

    // Apagamos Loggin...
//    static {
//        log.setLevel(Level.OFF);
//    }

    private XMLUtils() {
    }



    /**
     * Configura las opciones globales para busqueda automatica de atributos
     * en Tags globales.
     * @param props
     */
    public static final void setXmlProps(XMLProps props) {
        globals= props;
    }


    /**
     * Devuelve la configuracion global
     * @return
     */
    public static XMLProps getGlobals() {
        return globals;
    }



    /**
     * Permite parsear la cadena en formato XML dada
     * @param xml Cadena XML a parsear
     * @return Instancia al elemento root del XML
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Document parseXmlString(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder=null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setCoalescing(true);
        factory.setIgnoringComments(true);
        builder = factory.newDocumentBuilder();
        Document root = null;
        root = builder.parse(new ByteArrayInputStream(xml.getBytes()));
        return root;
    }





    /**
     * Si es un atributo {comodin} busca el atributo en las variables globales
     * y devuelve su valor.
     * @param id Identificador comodin a buscar
     * @return Valor recuperado de globalOptions si es comodin y existe. Mismo
     * valor en caso contrario.
     */
    private static final String fetchValue(String id) {

        if (globals!=null && id!=null && id.length()>2) {
            // Es comodin?
            if (id.charAt(0)=='{' && id.charAt(id.length()-1)=='}') {
                String gid= id.substring(1, id.length()-1);
                return globals.getString(gid, id);
            }
            else
                return id;
        }
        else
            return id;
    }



    /**
     * Reemplaza cada atributo {comodin} por su valor correspondiente de las
     * variables globales.
     * @param val Texto con {comodines} a reemplazar
     * @return Valor recuperado de globalOptions si es comodin y existe. Mismo
     * valor en caso contrario.
     */
    private static final String fetchAllValues(String val) {
        String outVal=val;

        if (globals!=null && val!=null && val.length()>2) {
            StringBuilder out=new StringBuilder();
            StringBuilder gid= new StringBuilder();
            boolean key=false;

            for (int p=0; p<val.length(); p++) {
                char c=val.charAt(p);

                switch (c) {
                    case '{':
                        key=true;
                        break;

                    case '}':
                        if (key) {
                            key=false;
                            String comodin= gid.toString();
                            gid.delete(0, gid.length());

                            String gVal= globals.getString(comodin);
                            if (gVal!=null)
                                out.append(gVal);
                            else
                                out.append('{').append(comodin).append('}');
                        }
                        else
                            out.append(c);
                        break;

                    default:
                        if (key)
                            gid.append(c);
                        else
                            out.append(c);
                }
            }
            outVal= out.toString();
        }
//        System.out.println("VAL["+val+"] -> ["+outVal+"]");

        return outVal;
    }



    /**
     * Busca el primer Hijo con el nombre dado en el nodo Padre dado.
     * @param parent Nodo Padre donde buscar
     * @param name Nombre del Nodo Hijo a buscar...
     * @return
     */
    public static final Node getChild(Node parent,String name) {

        Node child=null;
        boolean found = false;
        NodeList children= parent.getChildNodes();

        for (int i=children.getLength(); --i>=0; ) {
            child= children.item(i);
            if (child.getNodeType()==Node.ELEMENT_NODE && child.getNodeName().equals(name) ){
                found = true;
                break;
            }
        }
        if(!found)
            child=null;

        log.trace("PARENT[{}] CHILD[{}] -> [{}]",parent.getNodeName(),name,child);

        return child;
    }



    /**
     * Obtiene el Contenido de una Etiqueta hija del nodo dado
     * @param parent Nodo donde se buscara
     * @param tag Nombre de etiqueta hija.
     * @return Contenido de esa etiqueta si existe, Null caso contrario.
     */
    public static String getChildValue(Node parent,String tag) {
        return getChildValue(parent,tag,null);
    }



    /**
     * Obtiene el Contenido de una Etiqueta hija del nodo dado
     * @param parent Nodo donde se buscara
     * @param tag Nombre de etiqueta hija.
     * @param defaultValue Valor default
     * @return Contenido de esa etiqueta si existe, defaultValue caso contrario.
     */
    public static String getChildValue(Node parent,String tag,String defaultValue) {
        String value=defaultValue;

        NodeList hijos=parent.getChildNodes();

        for (int i=hijos.getLength(); --i>=0; ) {
            Node hijo= hijos.item(i);

            if (hijo.getNodeType()==Node.ELEMENT_NODE && hijo.getNodeName().equals(tag) ) {
                value= getNodeValue(hijo,defaultValue);
                break;
            }
        }

        log.trace("PARENT[{}] CHILD[{}] -> [{}]",parent.getNodeName(),tag,value);

        return value;
    }




    /**
     * Recupera el valor del primer Tag que coincida con el ID dado
     * @param parent Nodo raiz
     * @param id Identificador del tag buscado
     * @return
     */
    public static final String getFirstTagValue(Element parent,String id) {
        return getFirstTagValue(parent,id,null);
    }



    /**
     * Recupera el valor del primer Tag que coincida con el ID dado
     * @param parent Nodo raiz
     * @param id Identificador del tag buscado
     * @param defaultValue Valor default a devolver si no se encuentra el tag
     * @return
     */
    public static final String getFirstTagValue(Element parent,String id,String defaultValue) {
        String val= defaultValue;

        // Busca los tags que hacen match con ID dado
        NodeList l=parent.getElementsByTagName(id);

        // Si encontro algo, recupera el valor del primer elemento
        if (l!=null && l.getLength()>0) {
            val= l.item(0).getTextContent();
        }

        return val;
    }



    /**
     * Extrae el Valor del atributo dado
     * @param map Mapa de atributos
     * @param name Nombre del atributo a extraer
     * @return Valor del atributo si existe, null caso contrario
     */
    public static final String getAtributeValue(NamedNodeMap map, String name) {
        return getAtributeValue(map,name,null);
    }



    /**
     * Extrae el Valor del atributo dado
     * @param map Mapa de atributos
     * @param name Nombre del atributo a extraer
     * @return Valor del atributo si existe, valor default caso contrario
     */
    public static final String getAtributeValue(NamedNodeMap map, String name, String defaultValue) {
        String value=defaultValue;
        String[] names= name.split("\\|");

        for (String $name: names) {
            Node att=map.getNamedItem($name);
            if (att!=null) {
                String $val=att.getNodeValue();
                if ($val!=null)
                    value=fetchAllValues($val);
                break;
            }
        }

        log.trace("ATT[{}] -> [{}]",name,value);

        return value;
    }



    /**
     * Devuelve el contenido de texto del nodo dado
     * @param node Nodo objetivo
     * @return
     */
    public static final String getNodeValue(Node node) {
        return getNodeValue(node,null);
    }



    /**
     * Devuelve el contenido de texto del nodo dado
     * @param node Nodo objetivo
     * @param defaultValue Valor default a retornar si no hay contenido
     * @return
     */
    public static final String getNodeValue(Node node, String defaultValue) {
        String value=defaultValue;

        if (node!=null) {
            String $val=node.getTextContent();
            value= $val==null || $val.length()==0 ? defaultValue : fetchValue($val);
            log.trace("NODE[{}] -> [{}]",node.getNodeName(),value);
        }

        return value;
    }



    /**
     * Devuelve lista de Nodos hijos del padre dado con el nombre dado
     * @param parent Nodo padre donde buscar
     * @param name Nombre de los nodos hijos a buscar.
     * @return
     */
    public static final List<Node> getChilds(Node parent,String name) {
        List<Node> lista= new LinkedList<Node>();
        NodeList childs= parent.getChildNodes();

        for (int i=childs.getLength(); --i>=0; ) {
            Node child= childs.item(i);
            if (child.getNodeType()== Node.ELEMENT_NODE && child.getNodeName().equals(name))
                lista.add(child);
        }

        return lista;
    }




    /**
     * Devuelve el Valor del atributo identificado por el path dado
     * @param parent Nodo Raiz del documento XML
     * @param path Ruta al nodo. Cada nodo debe ser separado por '|'
     * @param condition Condicion a Evaluar en caso existan varios Nodos iguales...
     * @return
     */
    public static final String getAtributePathValue(Node parent, String path, String condition) {
        String value=null;
        List<Node> parentList= new ArrayList<Node>(1);
        parentList.add(parent);

        String[] nodes= path.split("/");
        boolean found=false;
        String name=null;
        String att=null;
//        String matchField=null;
//        String[] matchValue=null;
        List<String[]> matchFields=new LinkedList<String[]>();

        int lastIndex= nodes.length-1;
        name= nodes[lastIndex];
        int p= name.indexOf('.');
        if (p > 0) {
            att = name.substring(p + 1);
            name = name.substring(0, p);
        }

        // Viene Condicion?
        if (condition!=null) {
            String[] vals= condition.split(",");
            for (int i=0; i<vals.length; i++) {
                matchFields.add(vals[i].split("="));
            }
        }

        // Buscamos Padre de Nodo objetivo...
        for (int i=0; i< lastIndex; i++) {
            // Siguiente Nodo de la ruta...
            List<Node> childs=getChilds(parentList.get(0) ,nodes[i]);
            found = !childs.isEmpty();

            if (found)
                parentList=childs;
            else
                break;
        }

        // Encontrado?
        if (found) {
            // Barremos Padres "identificacionSujeto" (si es que hay mas de uno...)
            padres:
            for (Node parentNode : parentList) {

                // Hay que evaluar Condicion?
                if (matchFields.size()>0) {

                    for (String[] cond: matchFields) {

                        String matchField= cond[0];
                        String matchValue= cond[1];

                        Node matchNode = getChild(parentNode,matchField);

                        if (matchNode!=null) {
                            // Si no se cumple, continuamos...
                            if (!matchNode.getTextContent().matches(matchValue))
                                continue padres;
                        }
                    }
                }

                // Obtenemos Valor del Nodo Solicitado
                Node targetNode= getChild(parentNode,name);
                if (targetNode!=null) {
                    // Buscamos Atributo?
                    if (att != null)
                        value = getAtributeValue(targetNode.getAttributes(), att);
                    else
                        value = targetNode.getTextContent();
                }
                break;
            }
        }

        return value;
    }



    /**
     * Convierte el Documento a su representacion en XML String
     * @param doc Documento a transformar...
     * @return
     */
    public static final String dom2String(Document doc,boolean indent) {
        Transformer transformer = null;
        String s="";
        try {
            transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            if (indent)
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        Source source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        try {
            transformer.transform(source, result);
            s = writer.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return s;
    }



    public static void main(String[] args)
    {
        String v="id|juan";
        String[] vals= v.split("|");

        for (String c: vals) {
            System.out.println("\t"+c);

        }

    }

}
