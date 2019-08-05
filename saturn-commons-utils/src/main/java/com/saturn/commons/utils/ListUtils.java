package com.saturn.commons.utils;



import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ListUtils methods
 */
public class ListUtils extends BufferUtils
{

    /**
     * Constructor
     */
    private ListUtils() {
    }



    /**
     * Convierte el arreglo dado a su representacion en String.
     * @param data Lista de datos
     * @return
     */
    public static final String collection2String(Collection data) {
        StringBuilder cad= new StringBuilder();

        if (data==null)
            cad.append("null");
        else {
            cad.append("{");
            for (Object o: data) {
                if (cad.length()>1) cad.append(",");
                append(cad,o);
            }
            cad.append("}");
        }

        return cad.toString();
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
     * Convierte la lista a una representacion en forma tabular.
     * @param list Lista a convertir
     * @return
     */
    public static final String list2PrettyString(List list) {
        return list2PrettyString(list,null);
    }



    /**
     * Convierte la lista a una representacion en forma tabular.
     * @param list Lista a convertir
     * @param ids IDs de campos a tabular
     * @return
     */
    public static final String list2PrettyString(List list,String[] ids) {
        StringBuilder b= new StringBuilder();
        list2PrettyString(list,ids,b,null);
        return b.toString();
    }



    /**
     * Convierte la lista a una representacion en forma tabular.
     * @param list Lista a convertir
     * @param ids IDs de campos a tabular
     * @param buff Destino de la tabla
     * @return
     */
    public static final String list2PrettyString(List list,String[] ids, StringBuilder buff) {
        return list2PrettyString(list,ids,buff,null);
    }



    /**
     * Convierte la lista a una representacion en forma tabular.
     * @param list Lista de objetos
     * @param ids IDs de campos a tabular
     * @param buff Destino de la tabla
     * @param comp Comparador para ordenar elementos de la lista
     * @return
     */
    public static final String list2PrettyString(List list,String[] ids,StringBuilder buff,Comparator comp) {
        int rows= list==null?0:list.size();
        buff.append("[").append(rows).append("] ");

        if(rows>0) {
            Class clz = list.get(0).getClass();
            buff.append(clz.getSimpleName()).append(":\n");

            // <editor-fold defaultstate="collapsed" desc=" Recuperamos IDs? ">
            if (ids == null || ids.length == 0) {
                Field fields[] = clz.getDeclaredFields();
                ids = new String[fields.length];
                int p = 0;

                for (Field f : fields)
                    ids[p++] = f.getName();
            }

            // </editor-fold>

            int cols=ids.length;

            // <editor-fold defaultstate="collapsed" desc=" Preparamos titulos ">
            String[] titles = new String[cols];
            int p = 0;
            for (String id : ids)
                titles[p++] = StringUtils.camelCase2Title(id);

            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc=" Recuperamos Valores y Longitudes ">
            // Preparamos tamanos iniciales
            int[] sizes = new int[cols];
            for (int i = 0; i < cols; i++)
                sizes[i] = titles[i].length();

            // Recuperamos valores y Obtenemos la longitud mayor de cada columna...
            String[][] vals = new String[rows][cols];

            for (int r = 0; r < rows; r++)
            {
                Object o = list.get(r);

                for (int c = 0; c < cols; c++)
                {
                    String id = ids[c];
                    String val = String.valueOf(ListUtils.getFieldValue(o, id));
                    vals[r][c] = val;
                    int curSize = val.length();

                    if (sizes[c] < curSize)
                        sizes[c] = curSize;
                }
            }

            // </editor-fold>

            // Agregamos titulos...
            for (int i=0;i<titles.length; i++) {
                // Adicionamos 1 (espacio en blanco)
                sizes[i]+=1;
                buff.append(StringUtils.rightFill(titles[i], sizes[i], ' '));
            }
            buff.append("\n");

            // Agregamos Linea bajo titulo...
            for (int i=0;i<titles.length; i++)
                buff.append(StringUtils.rightFill("", sizes[i]-1, '-')).append(' ');
            buff.append("\n");

            // Recuperamos y ordenamos parametros por nombre...
            if (comp!=null)
                Collections.sort(list,comp);

            // Agregamos parametros
            for (int r=0; r<rows; r++) {
                for (int c=0; c<cols; c++) {
                    String value = vals[r][c];
                    buff.append(StringUtils.rightFill(value, sizes[c], ' '));
                }
                buff.append("\n");
            }
            buff.append("\n");
        } else
            buff.append(" items");

        return buff.toString();
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
