package com.saturn.commons.http.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saturn.commons.http.HttpContentType;
import com.saturn.commons.http.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Param replacement methods
 * @author raoolio
 */
public class HttpParamUtil {


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
        replaceAndCopy(sb,src,params,null);
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
     * @param charset Charset encoding
     */
    public static void replaceAndCopy(StringBuilder dst,String src,Map<String,Object> params,String charset) {

        boolean isKey=false;
        boolean isSize=false;
        StringBuilder $key= new StringBuilder(25);
        StringBuilder $size= new StringBuilder(3);

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

                case ':':
                    if (isKey) {
                        isSize=true;
                    } else
                        dst.append(':');
                    break;

                case '}':
                    if (isKey) {
                        isKey=false;
                        String key= $key.toString();
                        $key.delete(0, $key.length());
                        Object val= params.remove(key);

                        // Value found?
                        if (val!=null) {

                            //<editor-fold defaultstate="collapsed" desc=" Get size? ">
                            // Value size & cut direction
                            int size=0;     // default is full length
                            char dir='R';   // default is Right

                            if (isSize) {
                                isSize=false;

                                // Get direction char (Left,Right)
                                char dc= $size.charAt(0);
                                if (!CharUtils.isAsciiNumeric(dc)) {
                                    dir= dc;
                                    $size.deleteCharAt(0);
                                }

                                size= Integer.valueOf($size.toString());
                                $size.delete(0, $size.length());
                            }
                            //</editor-fold>

                            String $val= String.valueOf(val);
                            if (size>0)
                                $val= dir=='L'? StringUtils.left($val, size) : StringUtils.right($val, size);

                            if (charset==null) {
                                dst.append($val);
                            } else {
                                try {
                                    String encPar=URLEncoder.encode($val, charset);
                                    dst.append(encPar);
                                } catch (Exception e) {
                                    dst.append('{').append(key).append('}');
                                }
                            }
                        }
                        else
                            dst.append('{').append(key).append('}');
                    } else
                        dst.append('}');
                    break;

                default:
                    StringBuilder buf= isKey? (isSize? $size : $key) : dst;
                    buf.append(c);
            }
        }
    }



    //<editor-fold defaultstate="collapsed" desc=" Parameter encoding methods ">


    /**
     * Encode the sendRequest parameters
     * @param pars Parameter map
     * @param type Content type
     * @param charset Charset encoding
     * @return
     * @throws java.lang.Exception
     */
    public static String encodeParams(Map pars, HttpContentType type,String charset) throws Exception {
        StringBuilder sb= new StringBuilder();
        encodeParams(sb,pars,type,charset);
        return sb.toString();
    }



    /**
     * Encode the sendRequest parameters
     * @param sb Destination StringBuilder for the parameters
     * @param pars Parameter map
     * @param type Content type
     * @param charset Charset encoding
     * @return
     * @throws java.lang.Exception
     */
    public static StringBuilder encodeParams(StringBuilder sb,Map pars, HttpContentType type,String charset) throws Exception {

        switch(type) {
            case APPLICATION_JSON: params2Json(sb,pars); break;
            case APPLICATION_FORM_URLENCODED: params2UrlEncoded(sb,pars,charset); break;
//            case MULTIPART_FORM_DATA: pars2FormData(sb,req); break;
//            case TEXT_PLAIN: pars2Plain(sb,req); break;
            default: throw new Exception("ContentType "+type+" not supported yet.");
        }
        return sb;
    }



    /**
     * Encodes the parameters in JSON format
     * @param sb Destination buffer
     * @param pars Parameter map
     */
    public static void params2Json(StringBuilder sb,Map pars) throws JsonProcessingException {
        ObjectMapper mapper= new ObjectMapper();
        String json= mapper.writeValueAsString(pars);
        sb.append( json );
    }



    /**
     * Encodes the parameters in URL format
     * @param sb Destination buffer
     * @param pars Parameter map
     * @param charset Charset encoding
     * @throws UnsupportedEncodingException
     */
    public static void params2UrlEncoded(StringBuilder sb,Map pars, String charset) throws UnsupportedEncodingException {
        Iterator<String> keys= pars.keySet().iterator();
        int n=0;

        // Append parameters
        while (keys.hasNext()) {
            String id = keys.next();
            Object ob= pars.get(id);

            if (n>0)
                sb.append('&');
            sb.append(URLEncoder.encode(id, charset) );
            sb.append('=');
            sb.append(URLEncoder.encode(String.valueOf(ob), charset));
            n++;
        }
    }

    // TODO: Implement FormData param encoding
    static void pars2FormData(StringBuilder sb,HttpRequest req) {
    }

    // TODO: Implement PlainText param encoding
    static void pars2Plain(StringBuilder sb,HttpRequest req) {
    }

    //</editor-fold>



}
