package com.saturn.common.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Array Utils methods
 */
public class ArrayUtils extends BufferUtils {


    private ArrayUtils() {
    }
    
    
    
    /**
     * Convierte el arreglo dado a su representacion en String.
     * @param arry Arreglo de datos
     * @return
     */
    public static final String array2String(Object[] arry) {
        StringBuilder cad= new StringBuilder();

        if (arry==null)
            cad.append("null");
        else {
            cad.append("[");
            for (int i=0; i<arry.length; i++) {
                if (i>0)
                    cad.append(",");
                append(cad,arry[i]);
            }
            cad.append("]");
        }

        return cad.toString();
    }



    



    /**
     * Convierte la lista de arreglos a su representacion en Map, utilizando
     * el valor correspondiente al indice dado como llave de los objetos.
     * @param lista Lista a convertir
     * @param keyIndex Indice del atributo del objeto que servira como llave
     * @return
     */
    public static final Map<String,Object[]> arrayList2Map(List<Object[]> lista,int keyIndex) {
        Map<String,Object[]> m= new LinkedHashMap<String,Object[]>();

        for (Object[] oa: lista) {
            String key= String.valueOf(oa[keyIndex]);
            m.put(key, oa);
        }

        return m;
    }



    /**
     * Convierte la lista de arreglos a su representacion en Map, utilizando
     * el valor correspondiente al indice dado como llave de los objetos.
     * @param array Lista a convertir
     * @param keyIndex Nombre del atributo de los objetos que servira como llave
     * @return
     */
    public static final Map<String,Object> array2Map(Object[] array,int keyIndex) {
        Map<String,Object> m= new LinkedHashMap<String,Object>();

        for (Object ob: array) {
            String key= String.valueOf(ob);
            m.put(key, " ");
        }

        return m;
    }



}
