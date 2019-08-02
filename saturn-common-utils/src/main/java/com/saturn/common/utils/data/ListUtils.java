package com.saturn.common.utils.data;



import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Fecha: 6/02/2014
 */
public class ListUtils
{

    /**
     * Constructor
     */
    private ListUtils() {
    }



    /**
     * Convierte una lista a su representacion en Map, respetando el tipo y utilizando
     * el valor del campo dado como llave en el mapa.
     * @param lista Lista a convertir
     * @param keyFieldId Nombre del atributo de los objetos que servira como llave
     * @return
     */
    public static final <T>Map<String,T> list2Map(List<T> lista,String keyFieldId) {
        Map<String,T> m= new LinkedHashMap<String,T>();

        for (T o: lista) {
            String key=String.valueOf(getFieldValue(o,keyFieldId));
            m.put(key, o);
        }

        return m;
    }



    /**
     * Convierte una lista a su representacion en Map donde existen varios objetos
     * que corresponden con una misma llave.
     *
     * @param lista Lista a convertir
     * @param keyFieldId Nombre del atributo de los objetos que servira como llave
     * @return
     */
    public static final <T>Map<String,List<T>> list2MapList(List<T> lista,String keyFieldId) {
        Map<String,List<T>> m= new LinkedHashMap<String,List<T>>();

        for (T o: lista) {
            String key=String.valueOf(getFieldValue(o,keyFieldId));

            List<T> obList=m.get(key);
            if (obList==null) {
                obList= new LinkedList<T>();
                m.put(key, obList);
            }

            obList.add(o);
        }

        return m;
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



    /**
     * Recupera el valor del atributo dado por reflexion...
     * @param o Objeto origen
     * @param fieldId Nombre del atributo a recuperar
     * @return
     */
    public static final Object getFieldValue(Object o, String fieldId) {
        Object val=null;
        try {
            Object curOb=o;
            String[] fieldIds= fieldId.split("\\.");

            for (String id: fieldIds) {
                Class c=curOb.getClass();
                Field f= c.getDeclaredField(id);
                f.setAccessible(true);
                curOb= f.get(curOb);
            }

            // Asignamos valor solo si lo encontramos!
            if (curOb!=o)
                val= curOb;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return val;
    }



    /**
     * Testing method
     */
    public static void main(String[] args) {
        String id="servicio.codigo";

        String[] ids= id.split("\\.");

        for (String i:ids) {
            System.out.println(i);
        }
    }




}
