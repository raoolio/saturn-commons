package com.saturn.commons.utils.xml;

import com.saturn.commons.utils.rpn.RpnCalculator;
import com.saturn.commons.utils.string.toStringBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * Clase Utilitaria para convertir una parametrizacion basada en etiquetas XML a
 * su equivalente en Properties, de manera que se puedan leer los valores
 * utilizando la ruta completa a cada etiqueta.
 *
 * @author Raul del Cid Lopez
 */
public class XMLProps implements toStringBuilder {

    /** Atributos */
    private Map<String, Object> atributos = new HashMap<String, Object>();



    /**
     * Constructor a partir de una cadena XML
     *
     * @param xml Cadena con contenido XML.
     */
    public XMLProps(String xml) {
        Document doc = null;
        try {
            // Parseamos Documento XML
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new java.io.StringReader(xml)));
            parseNode("", doc.getDocumentElement(), null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            doc = null;
        }
    }



    /**
     * Constructor a partir de un archivo XML
     *
     * @param file
     */
    public XMLProps(File file) {
        Document doc = null;
        try {
            // Parseamos Documento XML
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            parseNode("", doc.getDocumentElement(), null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            doc = null;
        }
    }



    /**
     * Constructor a partir de un Nodo XML
     *
     * @param node Nodo XML a parsear
     */
    public XMLProps(Node node, XMLProps globals) {
        parseNode("", node, globals);
    }



    /**
     * Parsea el Elemento Dado...
     *
     * @param nodo
     */
    private final void parseNode(String parent, Node nodo, XMLProps globals) {
        // Nodo Elemento?
        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            NodeList hijos = nodo.getChildNodes();
            for (int i = 0; i < hijos.getLength(); i++) {
                Node hijo = hijos.item(i);
                parseNode(parent + (hijo.getNodeType() == Node.ELEMENT_NODE ? ((parent.length() > 0 ? "." : "") + hijo.getNodeName()) : ""), hijo, globals);
            }
        } else if (nodo.getNodeType() == Node.TEXT_NODE) {
            /**
             * Establecemos Atributo. 13/12/2013: Se cambio el metodo que
             * recupera el texto a getNodeValue() ya que el metodo anterior no
             * se encuentra en las dependencias de Activaciones Produccion.
             */
//            String valor= nodo.getTextContent();
            String valor = nodo.getNodeValue();

            // Viene algo?
            if (valor != null && valor.trim().length() > 0) {
                if (valor.toUpperCase().contains("OS.CORES")) {
                    try {
                        valor = Integer.toString((int) RpnCalculator.execute(valor));
                    } catch (Exception ex) {
                        System.err.println("Error parseando expresion[" + valor + "] -> " + ex);
                    }
                }
                if (globals != null && valor.startsWith("{") && valor.endsWith("}")) {
                    valor = globals.getString(valor.substring(1, valor.length() - 1));
                }

                addAttribute(atributos, parent, valor);
            }
        }
    }



    /**
     * Agrega el Atributo dado al HashMap dado, cuidando de no sobreescribir un
     * atributo existente, cuando un atributo ya existe, crea una lista para
     * agregar el atributo existente y el nuevo.
     *
     * @param dest HashMap destino
     * @param name Nombre del atributo
     * @param value Valor a Asignar
     */
    private final void addAttribute(Map dest, String name, String value) {
        Object oldValue = dest.get(name);
//        System.out.println("ATT: "+name+"="+value);

        // Atributo no existe?
        if (oldValue == null) {
            dest.put(name, value);
        } else {
            // Atributo es una Lista?
            if (oldValue instanceof List) {
                ((List) oldValue).add(value);
            } else {
                // Creamos lista para almacenar ambos valores...
                List<Object> obList = new LinkedList();
                obList.add(oldValue);
                obList.add(value);
                dest.put(name, obList);
            }
        }
    }



    /**
     * Devuelve el valor del atributo global dado
     *
     * @param tag Nombre completo del atributo
     * @return
     */
    public String getString(String tag) {
        return getString(tag, null);
    }



    /**
     * Devuelve el valor del atributo global dado
     *
     * @param tag Nombre completo del atributo
     * @param defaultValue Valor default si no existe el atributo...
     * @return
     */
    public String getString(String tag, String defaultValue) {
        Object ob = atributos.get(tag);
        if (ob == null) {
            return defaultValue;
        }

        if (ob instanceof List) {
            return (String) ((List) ob).get(0);
        } else {
            return (String) ob;
        }
    }



    /**
     * Devuelve el valor del atributo global dado
     *
     * @param tag Nombre completo del atributo
     * @return Lista con valores de atributo dado, o lista vacia si no tiene.
     */
    public List getList(String tag) {
        Object ob = atributos.get(tag);
        if (ob == null) {
            return new ArrayList(0);
        }

        if (ob instanceof List) {
            return (List) ob;
        } else {
            List list = new ArrayList(1);
            list.add(ob);
            return list;
        }
    }



    /**
     * Devuelve el valor del atributo global como un entero
     *
     * @param tag Nombre completo del atributo
     * @return
     */
    public int getInt(String tag) {
        return Integer.parseInt(getString(tag));
    }



    /**
     * Devuelve el valor del atributo global como un entero
     *
     * @param tag Nombre completo del atributo
     * @param defaultValue Valor default del atributo
     * @return
     */
    public int getInt(String tag, String defaultValue) {
        return Integer.parseInt(getString(tag, defaultValue));
    }



    /**
     * Devuelve el valor del atributo global como un long
     *
     * @param tag Nombre completo del atributo
     * @return
     */
    public long getLong(String tag) {
        return Long.parseLong(getString(tag));
    }



    /**
     * Devuelve el valor del atributo global como un long
     *
     * @param tag Nombre completo del atributo
     * @param defaultValue Valor default del atributo
     * @return
     */
    public long getLong(String tag, String defaultValue) {
        return Long.parseLong(getString(tag, defaultValue));
    }



    /**
     * Devuelve el valor del atributo global como un short
     *
     * @param tag Nombre completo del atributo
     * @return
     */
    public short getShort(String tag) {
        return Short.parseShort(getString(tag));
    }



    /**
     * Devuelve el valor del atributo global como un short
     *
     * @param tag Nombre completo del atributo
     * @param defaultValue Valor default del atributo
     * @return
     */
    public short getShort(String tag, String defaultValue) {
        return Short.parseShort(getString(tag, defaultValue));
    }



    /**
     * Devuelve el valor del atributo global como un float
     *
     * @param tag Nombre completo del atributo
     * @return
     */
    public float getFloat(String tag) {
        return Float.parseFloat(getString(tag));
    }



    /**
     * Devuelve el valor del atributo global como un float
     *
     * @param tag Nombre completo del atributo
     * @param defaultValue Valor default del atributo
     * @return
     */
    public float getFloat(String tag, String defaultValue) {
        return Float.parseFloat(getString(tag, defaultValue));
    }



    /**
     * Devuelve el valor del atributo global como un double
     *
     * @param tag Nombre completo del atributo
     * @return
     */
    public double getDouble(String tag) {
        return Double.parseDouble(getString(tag));
    }



    /**
     * Devuelve el valor del atributo global como un double
     *
     * @param tag Nombre completo del atributo
     * @param defaultValue Valor default del atributo
     * @return
     */
    public double getDouble(String tag, String defaultValue) {
        return Double.parseDouble(getString(tag, defaultValue));
    }



    /**
     * Devuelve el valor del atributo global como un booleano
     *
     * @param tag Nombre completo del atributo
     * @return
     */
    public boolean getBoolean(String tag) {
        return Boolean.parseBoolean(getString(tag));
    }



    /**
     * Devuelve el valor del atributo global como un booleano
     *
     * @param tag Nombre completo del atributo
     * @param defaultValue Valor default del atributo
     * @return
     */
    public boolean getBoolean(String tag, String defaultValue) {
        return Boolean.parseBoolean(getString(tag, defaultValue));
    }



    /**
     * Devuelve una lista de
     *
     * @param prefix Prefijo de etiquetas a buscar
     * @return Lista de Valores correspondientes a etiqueta padre
     */
    public List<String> getStrings(String prefix) {
        List<String> lista = new LinkedList<String>();

        Iterator<String> it = atributos.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();

            // Llave
            if (key.startsWith(prefix)) {
                Object ob = atributos.get(key);

                if (ob instanceof List) {
                    lista.add((String) ((List) ob).get(0));
                } else {
                    lista.add((String) ob);
                }
            }
        }

        return lista;
    }



    public int size() {
        return atributos.size();
    }



    /**
     * Devuelve la respuesta de Forma Legible
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder cad = new StringBuilder();
        toString(cad);
        return cad.toString();
    }



    /**
     *
     * @param buff
     */
    public void toString(StringBuilder buff) {
        toString(buff, atributos);
    }



    /**
     * Convierte el
     *
     * @param buff Buffer destino
     * @param atributos Atributos a imprimir
     * @param level Nivel
     */
    private final void toString(StringBuilder buff, Map<String, Object> atributos) {
        // Obtenemos llaves ordenadas...
        List<String> tags = new ArrayList<String>(atributos.keySet());
        Collections.sort(tags);

        for (String tag : tags) {
            buff.append('\t').append(tag).append("=").append(atributos.get(tag)).append("\n");
        }
    }

}
