package com.saturn.commons.utils;

import java.security.MessageDigest;
import java.util.Map;


/**
 * Metodos para formateo de datos
 * @author raoolio
 */
public class StringUtils
{

    private StringUtils() {
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
     * Corta la cadena a la longitud dada
     * @param cad Cadena a cortar
     * @param length Longitud
     * @return
     */
    public static final String left(String cad, int length) {
        return cad==null? null: (cad.length()>length? cad.substring(0, length) : cad);
    }



    /**
     * Converts a camel case string to title ready.<br>
     * Example: "camelCase" becomes "CAMEL CASE"
     * @param name
     * @return
     */
    public static String camelCase2Title(String name) {
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



    /**
     * Returns the left side, from the string split using given character
     * @param s Source string
     * @param c Splitting char
     * @return
     */
    public static String getParent(String s,char c) {
        String parent="";

        int pos= s.lastIndexOf(c, s.length()-2);

        if (pos>0) {
            // get parent path...
            parent= s.substring(0, pos+1);
        }

        return parent;
    }



    /**
     * Append the given char if it's missing
     * @param s StringBuilder instance
     * @param c
     */
    public static void appendIfMissing(StringBuilder s,char c) {
        if (s.charAt(s.length()-1) != c)
            s.append(c);
    }

}
