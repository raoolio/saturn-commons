package com.saturn.common.utils.string;

import com.saturn.common.utils.data.ListUtils;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Metodos para formateo de datos
 * @author raoolio
 */
public class StringUtils
{
    // Atributos

    // Constructor
    private StringUtils() {
    }



    /**
     * Append the object to the buffer
     * @param sb Destination buffer
     * @param v Object
     */
    private static StringBuilder append(StringBuilder sb,Object v) {
        if (v instanceof CharSequence)
            sb.append('"').append(v).append('"');
        else if (v instanceof Character)
            sb.append('\'').append(v).append('\'');
        else
            sb.append(v);
        return sb;
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
     * Realiza Right Fill
     * @param cad Cadena
     * @param length Longitud maxima
     * @param c Caracter de relleno
     * @return
     */
    public static final String rightFill(String cad,int length, char c) {
        StringBuilder buff= new StringBuilder(cad==null?"null":cad);
        int f= length - buff.length();

        if (f>0) {
            for (int i=f; --f>=0; )
                buff.append(c);
        }

        return buff.toString();
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

        Iterator<Entry> it=map.entrySet().iterator();
        while (it.hasNext()) {
            Entry e= it.next();
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

        Iterator<Entry> it=map.entrySet().iterator();
        while (it.hasNext()) {
            Entry e= it.next();
            sb.append(separator).append(e.getKey()).append('=');
            append(sb,e.getValue());
        }

        return sb.toString();
    }



    /**
     * Corta la cadena a la longitud dada
     * @param cad Cadena a cortar
     * @param length Longitud
     * @return
     */
    public static final String trimLength(String cad, int length) {
        return cad==null? null: (cad.length()>length? cad.substring(0, length) : cad);
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
                titles[p++] = camelCase2Title(id);

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
     * Converts a camel case string to title ready.<br>
     * Example: "camelCase" becomes "CAMEL CASE"
     * @param name
     * @return
     */
    private static String camelCase2Title(String name) {
        StringBuilder sb=new StringBuilder();
        boolean prevLow=isLowerCase(name.charAt(0));

        for (int i=0;i<name.length();i++) {
            char c= name.charAt(i);
            boolean isLow= isLowerCase(c);

            if (!isLow && prevLow)
                sb.append(" ");

            sb.append(c);
        }

        return sb.toString().toUpperCase();
    }



    /**
     * Tells if the given character is lower case.
     * @param c Character to validate
     * @return
     */
    public static boolean isLowerCase(char c) {
        return (c>= 'a' && c<='z') || c=='á' || c=='é' || c=='í' || c=='ó' || c=='ú' || c=='ñ';
    }



    /**
     * Tells if the given character is upper case.
     * @param c Character to validate
     * @return
     */
    public static boolean isUpperCase(char c) {
        return (c>= 'A' && c<='Z') || c=='Á' || c=='É' || c=='Í' || c=='Ó' || c=='Ú' || c=='Ñ' ;
    }



    /**
     * Aplica Trim a la cadena dada validando antes que no sea nula.
     * @param cad Cadena a la cual se aplica trim
     * @return Cadena sin espacios a los lados
     */
    public static String safeTrim(String cad) {
        return cad!=null? cad.trim(): cad;
    }



    /**
     * Encripta el texto dado usando el algoritmo SHA-256
     * @param base
     * @return
     */
    public static String sha256(String base) throws Exception {
        StringBuilder hexString = new StringBuilder();

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }



    /**
     * Stores the given string values into a Map
     *
     * Input:
     *    "objeto.par1,par2,par3"
     *
     * Output:
     * MAP(
     *     { "objeto" "par1" } -> "objeto.par1"
     *     { "par2" } -> "par2"
     *     { "par3" } -> "par3"
     * )
     *
     * @param ids String to split
     * @param sep Splitting char
     * @return
     */
    public static Map<LinkedList,String> toPathListMap(String ids,String sep) {
        return toPathListMap(null,ids,sep);
    }



    /**
     * Stores the given string values into a Map
     *
     * Input:
     *    "objeto.par1,par2,par3"
     *
     * Output:
     * MAP(
     *     { "objeto" "par1" } -> "objeto.par1"
     *     { "par2" } -> "par2"
     *     { "par3" } -> "par3"
     * )
     *
     * @param parent Parent
     * @param str String to split
     * @param sep Splitting char
     * @return
     */
    public static Map<LinkedList,String> toPathListMap(String parent,String str,String sep) {
        String[] paths= str==null ? new String[0] : str.split(sep);
        Map m= new LinkedHashMap();
        String[] parPath= parent==null? null : parent.split("\\.");

        for (String path: paths) {
            String[] ids= path.split("\\.");
            List l= new LinkedList();

            // Add Parent to path list ?
            if (parPath!=null) {
                for (String id: parPath) {
                    l.add(safeTrim(id));
                }
            }

            // Add attribute ids
            for (String id: ids) {
                l.add(safeTrim(id));
            }
            m.put(l, path);
        }

        return m;
    }



    /**
     * Decodes the given string if it contains URL-encoded chars. Trims the
     * string if it's length is greater than maxLength chars
     * @param s String to decode
     * @param maxLength Maximum string length
     * @return
     */
    public static String URLDecodeString(String s,int maxLength) throws Exception {

        StringBuilder sb= new StringBuilder(maxLength);
        boolean isEncode=false;
        int pars=0;
        StringBuilder enc= new StringBuilder();

        // loop through chars
        loop: for (int p=0; p<s.length(); p++) {
            char c= s.charAt(p);

            switch(c) {
                case '%':
                    isEncode= true;
                    break;
                case '(':
                    pars++;
                    break;
                case ')':
                    if (--pars==0) {
                        sb.append(c);
                        break loop;
                    }
                    break;

                default:
                    // encoded?
                    if (isEncode) {
                        // encode complete ? (multiple of 3)
                        if (enc.length()%3 == 0) {
                            sb.append(java.net.URLDecoder.decode(enc.toString(), "UTF-8"));
                            enc.delete(0, enc.length());
                            isEncode=false;
                        }  else if (! ((c>='0' && c<'9') || (c>='A' && c<='F'))) {
                            sb.append(enc.toString());
                            enc.delete(0, enc.length());
                            isEncode=false;
                        }
                    }
            }

            StringBuilder b= isEncode? enc : sb;
            b.append(c);

            if (sb.length()>maxLength)
                sb.delete(maxLength, sb.length());
        }//fin-for

        return sb.toString();
    }



    /**
     * <p>Returns a copy of the given string, replacing any parameter name with
     * it's corresponding value in the map.<p>
     * <p>If the parameter exists in the map, it's value is removed from the map.
     * If it doesn't exist, the parameter name is copied to the destination
     * buffer unchanged.</p>
     *
     * @param src Source string
     * @param params Parameter map
     * @return
     */
    public static String replaceAndCopy(String src,Map<String,Object> params) {
        StringBuilder sb= new StringBuilder();
        replaceAndCopy(sb,src,params);
        return sb.toString();
    }



    /**
     * <p>Copies the source string to the destination buffer, replacing any
     * parameter name with it's corresponding value in the map.<p>
     * <p>If the parameter exists in the map, it's value is removed from the map.
     * If it doesn't exist, the parameter name is copied to the destination
     * buffer unchanged.</p>
     *
     * @param dst Destination buffer
     * @param src Source string
     * @param params Parameter map
     */
    public static void replaceAndCopy(StringBuilder dst,String src,Map<String,Object> params) {

        boolean isKey=false;
        StringBuilder $key= new StringBuilder(25);

        // Loop through source chars
        for (int p=0; p<src.length(); p++) {
            char c= src.charAt(p);

            switch(c) {
                case '{':
                    if (isKey) {
                        dst.append('{').append($key);
                        $key.delete(0, $key.length());
                    } else {
                        isKey=true;
                    }
                    break;

                case '}':
                    if (isKey) {
                        isKey=false;
                        String key= $key.toString();
                        $key.delete(0, $key.length());
                        Object val= params.get(key);
                        if (val!=null)
                            dst.append(val);
                        else
                            dst.append('{').append(key).append('}');
                        break;
                    }

                default:
                    StringBuilder buf= isKey? $key : dst;
                    buf.append(c);
            }
        }
    }


}
