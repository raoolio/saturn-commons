package com.saturn.commons.utils;

import com.saturn.commons.utils.string.StringUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Map handling utils
 */
public class MapUtils extends BufferUtils {


    private MapUtils() {
    }




    /**
     * Returns a new map with the "id" attribute used as key
     * @param <T>
     * @param m Map
     * @param fieldId
     * @return
     */
    public static final <T>Map<String,T> mapValuesToField(Map<String,T> m,String fieldId) {
        Map<String,T> mapById= new HashMap<>();

        Iterator<T> it= m.values().iterator();
        while (it.hasNext()) {
            T o= it.next();
            String key= String.valueOf( ListUtils.getFieldValue(o, fieldId) );
            mapById.put(key, o);
        }

        return mapById;
    }



    /**
     * Extracts first value of array
     * @param source Source map
     * @return
     */
    public static Map<String,String> valueArray2Map(Map<String,String[]> source) {
        Map<String,String> m= new HashMap();

        Iterator<String> it=source.keySet().iterator();
        while (it.hasNext()) {
            String key= it.next();
            String[] val=source.get(key);
            m.put(key, val[0]);
        }

        return m;
    }



    /**
     * Convierte el mapa dado a su representacion en String
     * @param map Map de datos
     * @param title Titulo
     * @return
     */
    public static final String map2String(Map map,String title) {
        StringBuilder buff=new StringBuilder();
        buff.append("[").append(map.size()).append("] ").append(title).append(":\n");

        Iterator<Map.Entry> it=map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e= it.next();
            buff.append("ID[").append(e.getKey()).append("]=");
            append(buff,e.getValue()).append('\n');
        }

        return buff.toString();
    }



    /**
     * Convierte el mapa dado a su representacion en String
     * @param map Map de datos
     * @return
     */
    public static final String map2PlainString(Map map) {
        return map2PlainString(map,null," ");
    }



    /**
     * Convierte el mapa dado a su representacion en String
     * @param map Map de datos
     * @param title Titulo
     * @return
     */
    public static final String map2PlainString(Map map,String title) {
        return map2PlainString(map,title," ");
    }



    /**
     * Convierte el mapa dado a su representacion en String
     * @param map Map de datos
     * @param title Titulo
     * @param separator Char element separator
     * @return
     */
    public static final String map2PlainString(Map map,String title,String separator){
        StringBuilder sb=new StringBuilder();
        sb.append("[").append(map.size()).append("]");
        if (title!=null)
            sb.append(' ').append(title);
        sb.append(":");

        Iterator<Map.Entry> it=map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e= it.next();
            sb.append(separator).append(e.getKey()).append('=');
            append(sb,e.getValue());
        }

        return sb.toString();
    }



    /**
     * Convierte el mapa dado a una representacion en forma tabular.
     * @param map Mapa a representar
     * @return
     */
    public static final String map2PrettyString(Map map) {
        StringBuilder sb= new StringBuilder();
        map2PrettyString(map,sb);
        return sb.toString();
    }



    /**
     * Convierte el mapa dado a una representacion en forma tabular.
     * @param map Map de datos
     * @param title Titulo
     * @return
     */
    public static final String map2PrettyString(Map map,String title) {
        if (map==null)
            return "null";
        else {
            StringBuilder buff= new StringBuilder();
            map2PrettyString(map,buff,title);
            return buff.toString();
        }
    }



    /**
     * Convierte el mapa dado a una representacion en forma tabular.
     * @param map Mapa a representar
     * @param sb Buffer a utilizar para la salida de la tabla
     */
    public static final void map2PrettyString(Map map,StringBuilder sb) {
        map2PrettyString(map,sb,null,null);
    }



    /**
     * Convierte el mapa dado a una representacion en forma tabular.
     * @param map Mapa a representar
     * @param buff Buffer a utilizar para la salida de la tabla
     * @param title Titulo a mostrar
     */
    public static final void map2PrettyString(Map map,StringBuilder buff,String title) {
        map2PrettyString(map,buff,null,title);
    }



    /**
     * Convierte el mapa dado a una representacion en forma tabular.
     * @param map Mapa a representar
     * @param comp Comparador usado para ordenar los IDs
     * @return
     */
    public static final String map2PrettyString(Map map,Comparator<String> comp) {
        StringBuilder buff= new StringBuilder();
        map2PrettyString(map,buff,comp,null);
        return buff.toString();
    }




    /**
     * Convierte el mapa dado a una representacion en forma tabular.
     * @param map Mapa a representar
     * @param sb Buffer a utilizar para la salida de la tabla
     * @param comp Comparador usado para ordenar los IDs
     */
    public static final void map2PrettyString(Map map, StringBuilder sb,Comparator<String> comp,String title) {
        sb.append("[").append(map==null?"0": map.size()).append("] ").append(title==null?"Params":title).append(":\n");

        if(map!=null && !map.isEmpty()) {
            // Preparamos titulos
//            String[] titles="ID,VALUE".split(",");
            int[] sizes=new int[2];
            String[] vals=new String[2];

            // Establecemos Longitud inicial
            for (int i=0;i<2; i++)
                sizes[i]=0;

            // Obtenemos la longitud mayor de cada columna...
            Iterator it = map.keySet().iterator();
            while(it.hasNext()) {
                Object key= it.next();
                vals[0] = String.valueOf(key);
                vals[1] = String.valueOf(map.get(key));

                for (int i=0; i<2; i++) {
                    String val=vals[i];
                    int curSize= val==null? 4 : val.length()+1;

                    if (sizes[i] < curSize )
                        sizes[i]= curSize;
                }
            }

//            // Agregamos titulos...
//            for (int i=0;i<titles.length; i++) {
//                sizes[i]+=1;
//                sb.append(rightFill(titles[i], sizes[i], ' '));
//            }
//            sb.append("\n");
//
//            // Agregamos Linea bajo titulo...
//            for (int i=0;i<titles.length; i++) {
//                sb.append(rightFill("", sizes[i]-1, '-')).append(' ');
//            }
//            sb.append("\n");

            // Recuperamos y ordenamos parametros por nombre...
            List ids=new LinkedList();
            Iterator is=map.keySet().iterator();
            while (is.hasNext()) {
                Object id=is.next();
                ids.add(id);
            }

            if (comp==null)
                Collections.sort(ids);
            else
                Collections.sort(ids,comp);

            // Agregamos parametros
            for (Object id: ids) {
                String value = String.valueOf("null".equals(id)? map.get(null) :  map.get(id));
                sb.append(StringUtils.rightFill(String.valueOf(id), sizes[0], ' '));
                sb.append('[').append(value).append(']');
                sb.append("\n");
            }
        }
    }



    /**
     * Returns the first value found for the given list of ids
     * @param m Map
     * @param ids Ids to look
     * @return
     */
    public static Object getFirstValue(Map m,String... ids) {
        for (String id:ids) {
            Object v= m.get(id);
            if (v!=null) {
                return v;
            }
        }
        return null;
    }


}
