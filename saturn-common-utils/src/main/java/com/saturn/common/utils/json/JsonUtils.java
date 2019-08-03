package com.saturn.common.utils.json;

import com.saturn.common.utils.string.StringFunction;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.saturn.common.utils.PathUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Metodos especiales para parseo de objetos en formato JSON utilizando la
 * libreria de Jackson v2.7.3 o superior.
 * @author rdelcid
 */
public class JsonUtils {

    /**
     * Constructor privado (no puede instanciarse)
     */
    private JsonUtils() {
    }



    /**
     * Genera una lista con el valor del atributo dado del arreglo de objetos JSON.
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param attrId ID de atributos a recuperar
     * @return
     * @throws IOException
     */
    public static final List<String> getListById(String jsonArray,String attrId) throws IOException {
        return getListById(jsonArray,attrId,"|",1);
    }



    /**
     * Genera una lista con el valor del atributo dado del arreglo de objetos JSON.
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param ids ID de atributos a recuperar
     * @param separator Attribute separator char
     * @param level Level of attributes to include
     * @return
     * @throws IOException
     */
    public static final List<String> getListById(String jsonArray,String ids,final String separator,int level) throws IOException {

        final List<String> lst= new LinkedList<String>();
        final StringBuilder sb=new StringBuilder();

        parseJson(jsonArray,ids,new JsonListener(){
            @Override
            public void objectFound(Map<String,String> object) {
                Iterator<String> it=object.values().iterator();
                sb.delete(0, sb.length());

                while (it.hasNext()) {
                    if (sb.length()>0)
                        sb.append(separator);

                    sb.append( it.next() );
                }
                lst.add(sb.toString());
            }
        });

        return lst;
    }


    /**
     * Parsea el arreglo de objetos JSON y genera una lista donde cada elemento
     * es un Map con los atributos solicitados.
     *
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param parentId Parent ID
     * @return
     * @throws IOException
     */
    public static final Map<String,String> getAllObjectAttributes(String jsonArray,String parentId) throws IOException {
        return getObjectAsMap(jsonArray,parentId,null);
    }



    /**
     * Parsea el arreglo de objetos JSON y genera una lista donde cada elemento
     * es un Map con los atributos solicitados.
     *
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param parentId Parent ID
     * @param ids Attribute ids
     * @return
     * @throws IOException
     */
    public static final Map<String,String> getObjectAttributes(String jsonArray,String parentId, String ids) throws IOException {
        return getObjectAsMap(jsonArray,parentId,ids);
    }



    /**
     * Parsea el arreglo de objetos JSON y genera una lista donde cada elemento
     * es un Map con los atributos solicitados.
     *
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param ids ID de atributos a recuperar
     * @return
     * @throws IOException
     */
    public static final Map<String,String> getObjectAttributes(String jsonArray,String ids) throws IOException {
        return getObjectAsMap(jsonArray,null,ids);
    }



    /**
     * Parsea el arreglo de objetos JSON y genera una lista donde cada elemento
     * es un Map con los atributos solicitados.
     *
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param parent Parent name
     * @param ids ID de atributos a recuperar
     * @return
     * @throws IOException
     */
    private static Map<String,String> getObjectAsMap(String jsonArray,String parent,String ids) throws IOException {
        final Map<String,String> ob= new LinkedHashMap<String,String>();
        if (parent==null && ids==null)
            throw new IllegalArgumentException("Must provide parent or attribute names");

        parseJson(jsonArray,parent,ids,new JsonListener(){
            @Override
            public void objectFound(Map object) {
                ob.putAll(object);
            }
        },1);

        return ob;
    }



    /**
     * Parsea el arreglo de objetos JSON y genera una lista donde cada elemento
     * es un Map con los atributos solicitados.
     *
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param ids ID de atributos a recuperar
     * @return
     * @throws IOException
     */
    public static final List<Map<String,String>> getListAsMap(String jsonArray,String ids) throws IOException {
        final List<Map<String,String>> lst= new LinkedList<Map<String,String>>();

        parseJson(jsonArray,ids,new JsonListener(){
            @Override
            public void objectFound(Map object) {
                lst.add(object);
            }
        });

        return lst;
    }



    /**
     * Genera un mapa con el valor del atributo dado del arreglo de objetos JSON.
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param attrId Id de atributo que servira de llave en el mapa.
     * @param valId Id de atributo que servira de valor en el mapa.
     * @param valFunc Funcion aplicable al valor antes de agregarlo al mapa.
     * @return
     * @throws IOException
     */
    public static final Map<String,String> getMapByIdAndValue(String jsonArray,String attrId,String valId,StringFunction valFunc) throws IOException {
        return getMapByIdAndValue(jsonArray,attrId,valId,valFunc,1);
    }



    /**
     * Genera un mapa con el valor del atributo dado del arreglo de objetos JSON.
     * @param jsonArray Arreglo de objetos en formato JSON
     * @param attrId Id de atributo que servira de llave en el mapa.
     * @param valId Id de atributo que servira de valor en el mapa.
     * @param valFunc Funcion aplicable al valor antes de agregarlo al mapa.
     * @param level Attribute and value levels
     * @return
     * @throws IOException
     */
    public static final Map<String,String> getMapByIdAndValue(String jsonArray,final String attrId,final String valId,StringFunction valFunc,int level) throws IOException {
        final Map<String,String> map= new HashMap<String,String>();

        // Join attributes
        String atts= attrId+","+valId;

        parseJson(jsonArray,atts,new JsonListener(){
            @Override
            public void objectFound(Map<String,String> object) {
                String id= object.get(attrId);
                String val= object.get(valId);

                map.put(id,val);
            }
        });

        return map;
    }



    /**
     * Generic JSON traversal engine
     * @param jsonArray JSON String array to parse
     * @param ids Comma separated ids to retrieve from JSON Array
     * @param listener Listener to call for each completed object
     * @throws IOException
     */
    public static final void parseJson(String jsonArray,String ids,JsonListener listener) throws IOException {
        parseJson(jsonArray,null,ids,listener,1000);
    }



    /**
     * Generic JSON traversal engine
     * @param jsonArray JSON String array to parse
     * @param parentId Parent ID
     * @param ids Comma separated ids to retrieve from JSON Array
     * @param listener Listener to call for each completed object
     * @param maxObjects Maximum number of objects to parse
     * @throws IOException
     */
    public static final void parseJson(String jsonArray,String parentId,String ids,JsonListener listener,int maxObjects) throws IOException {
        JsonFactory f = new JsonFactory();
        JsonParser jp = f.createJsonParser(jsonArray);

        // Temporary vars
        Map<LinkedList,String> values= new HashMap<LinkedList,String>();
        Map<LinkedList,String> parPaths= PathUtils.toPathListMap(parentId,ids, ",");
        LinkedList curPath= new LinkedList();

        try {
            JsonToken token;
            int arLevel=0;
            int obLevel=0;
            boolean append=false;
            int obCount=0;

            // Barremos JSON
            loop: while ((token=jp.nextToken()) !=null ) {

                switch (token) {

                    case START_ARRAY:
                        arLevel++; break;

                    case START_OBJECT:
                        // Add current attribute to path
                        append=true;
                        obLevel++;
                        break;

                    case FIELD_NAME:
                        //<editor-fold defaultstate="collapsed" desc=" Path Name keeper ">
                        if (append) {
                            curPath.add( jp.getCurrentName() );
                            append=false;
                        } else {
                            curPath.pollLast();
                            curPath.add( jp.getCurrentName() );
                        }
                        break;
                        //</editor-fold>

                    case END_OBJECT:
                        //<editor-fold defaultstate="collapsed" desc=" Object Parser ">

                        // Target level? -> generate output string
                        if (obLevel==1) {
                            // Has values ?
                            if (!values.isEmpty()) {

                                Map ob= buildObject(parPaths,values);

                                if (!ob.isEmpty()) {
                                    listener.objectFound(ob);

                                    // Object count reached?
                                    if (++obCount >= maxObjects )
                                        break loop;
                                }
                            }
                            values.clear();
                        }

                        // Up one level...
                        curPath.pollLast();
                        --obLevel;
                        break;
                        //</editor-fold>

                    case END_ARRAY:
                        --arLevel; break;

                    default:
                        //<editor-fold defaultstate="collapsed" desc=" Value Processor ">

                        // Add All values ?
                        if (parPaths.isEmpty()) {
                            values.put((LinkedList)curPath.clone(), jp.getText());
                        } else {
                            // Barremos lista de parametros
                            Iterator<LinkedList> it=parPaths.keySet().iterator();

                            while (it.hasNext()) {
                                LinkedList parPath= it.next();

                                // This parameter ?
                                if (parPath.equals(curPath)) {
    //                                System.out.println("Parameter found!: "+parPath+" -> "+jp.getText());

                                    values.put(parPath, jp.getText());
                                    break;
                                }
                            }
                        }
                        //</editor-fold>
                } // end-switch
            } // end-while

        } finally {
            jp.close();
            jp=null;
        }
    }



    /**
     * Builds map of attributes
     * @param parPaths Parameter paths (can be empty)
     * @param values (Parameter values)
     * @return
     */
    private static Map buildObject(Map<LinkedList,String> parPaths,Map<LinkedList,String> values) {
        Map ob= new LinkedHashMap(values.size());

        Iterator<LinkedList> it = (parPaths.isEmpty() ? values : parPaths).keySet().iterator();

        while (it.hasNext()) {

            /**
             * Each parPaths entry has: { key, value }
             * Where:
             * key =  { "ob1", "attr1" } (Path Name)
             * value=  "ob1.attr1"       (Short Name)
             */
            LinkedList pathName = it.next();
            String parValue = values.get(pathName);

            if (parValue != null) {
                String shortName = parPaths.get(pathName);
                ob.put(shortName == null ? pathName.getLast() : shortName, parValue);
            }
        }

        return ob;
    }



    /**
     * Obtiene el valor correspondiente al atributo dado del objeto JSON.
     * @param jsonOb Objeto en formato JSON
     * @param attrId Id del atributo a recuperar
     * @return Valor del atributo si existe, NULL en caso contrario.
     * @throws IOException
     */
    public static final String getIdValue(String jsonOb,String attrId) throws IOException {
        String value=null;
        JsonFactory f = new JsonFactory();
        JsonParser jp = f.createJsonParser(jsonOb);

        try {
            JsonToken token=null;

            // Barremos atributos del Json
            while ((token=jp.nextToken()) != null) {

                // Name Field ?
                if (token == JsonToken.FIELD_NAME) {

                    // Get field name and advance pointer to value
                    String attName= jp.getCurrentName();
                    jp.nextToken();

                    // Extraemos valor de atributo
                    if (attrId.equals(attName)) {
                        value= jp.getText();
                        break;
                    }
                }
            }
        } finally {
            jp.close();
            jp=null;
        }

        return value;
    }

}
